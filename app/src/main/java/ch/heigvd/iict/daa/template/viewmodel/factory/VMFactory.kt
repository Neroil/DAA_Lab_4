package ch.heigvd.iict.daa.template.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ch.heigvd.iict.daa.template.viewmodel.NoteViewModel

class VMFactory(/*Repository*/) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass : Class<T>): T {
        if(modelClass.isAssignableFrom(NoteViewModel::class.java)){
            return NoteViewModel(/*Repository*/) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}