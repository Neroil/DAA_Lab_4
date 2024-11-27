# Rapport DAA Lab04 MVVM
auteurs: Dunant Guillaume, Häffner Edwin, Junod Arthur
## Implémentation

### ViewModel

Le ViewModel à été implémenté comme conseillé dans les slides du cours. Il ne fait référence en aucun cas à des éléments du cycle de vie de l'application, utilise une factory afin de recevoir le repository en paramètre lors de sa création et n'expose qu'une version privée de ses LiveData à l'extérieur de sa classe.

## Réponse questions
> Quelle est la meilleure approche pour sauver, même après la fermeture de l’app, le choix de l’option de tri de la liste des notes ? Vous justifierez votre réponse et l’illustrez en présentant le code mettant en œuvre votre approche.

Quand l'utilisateur fait ce genre de choix nous pouvons le stocker dans une SharedPreference qui nous permettra de le retrouver facilement même après l'interruption de l'application. Cela se justifie par le fait que le choix de l'option de tri est une petite donnée à gérer qui ne justifie pas la gestion manuelle d'ouverture et d'écriture de fichier ou de l'utilisation d'une DB.

Pour cela, il faudrait intégrer la récupération et la sauvegarde de cette donnée dans le cycle de vie de l'activité.

MainActivity.kt
```kotlin
  override fun onCreate(savedInstanceState: Bundle?) {
    ...
    val prefs : SharedPreferences = getPreferences(Context.MODE_PRIVATE)
    noteViewModel.setSortType(prefs.getString("sortType"))
  }

  ...

  override fun onDestroy() {
    ...
    val prefs : SharedPreferences = getPreferences(Context.MODE_PRIVATE)
    prefs.edit().putString("sortType", noteViewModel.getSortType()).apply()
  }  
```

On admet dans l'exemple que les getter / setter existent avec la conversion de `String` vers l'enum `SortType` dans notre classe.

> L’accès à la liste des notes issues de la base de données Room se fait avec une LiveData. Est-ce que cette solution présente des limites ? Si oui, quelles sont-elles ? Voyez-vous une autre approche plus adaptée ?

Oui plusieurs limites sont présentes:
- La requête sur les LiveDatas observées par l'UI (ici le RecyclerView) va être exécutée à chaque fois qu'il y a un changement même si la note n'est pas actuellement affichée, ce qui peut ne pas être voulu si nous avons des problèmes de performance. Utiliser la transformation `distinctUntilChanged()` sur la LiveData nous permet de régler ce problème.
- Si notre liste de notes devient très volumineuse alors la charger en mémoire entièrement au travers de notre LiveData peut ne pas être le comportement désiré. Pour régler ce problème, on peut utiliser des Flow, qui sont des types de données qui peuvent émettre des valeurs séquentiellement afin de ne pas afficher directement tout un dataset, ou on peut utiliser le système de Paging. Le système de Paging fera que nous aurons un PageSource dans notre Repository qui sera utilisé par un objet Pager dans le ViewModel. Le Pager pourra ensuite utiliser un Flow pour émettre les données à l'UI qui utilisera un adaptateur pour les afficher.

> Les notes affichées dans la RecyclerView ne sont pas sélectionnables ni cliquables. Comment procéderiez-vous si vous souhaitiez proposer une interface permettant de sélectionner une note pour l’éditer ?

Il y a différente possibilité si l'on veut appliquer la même fonction à tous les éléments de la RecyclerView alors on peut utiliser un `RecyclerItemClickListener` qui sera utilisé en paramètre dans la fonction `RecyclerView.addOnItemTouchListener()` avec un override des fonctions `onItemClick()` et `onLongItemClick()` et qui aura comme code:

```kotlin
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
  private OnItemClickListener mListener;

  public interface OnItemClickListener {
    public void onItemClick(View view, int position);

    public void onLongItemClick(View view, int position);
  }

  GestureDetector mGestureDetector;

  public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
    mListener = listener;
    mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null && mListener != null) {
                mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
            }
        }
    });
}

  @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
    View childView = view.findChildViewUnder(e.getX(), e.getY());
    if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
      mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
      return true;
    }
    return false;
  }

  @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }

  @Override
  public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){}
}
```

L'autre possibilité est d'implémenter un `OnClickListener` et de l'utiliser dans le `OnCreateViewHolder()` de l'adaptateur en utilisant `setOnClickListener` sur la vue créé dans cette fonction.

