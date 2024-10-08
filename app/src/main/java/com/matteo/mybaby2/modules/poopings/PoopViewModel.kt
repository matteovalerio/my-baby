package com.matteo.mybaby2.modules.poopings

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class PoopViewModel : ViewModel() {
    var hasPoop = mutableStateOf(false)
        private set
    var hasPiss = mutableStateOf(false)
        private set
    var notes = mutableStateOf("")
        private set
    var date = mutableStateOf<Long?>(null)
        private set

    fun updateHasPoop(hasPoop: Boolean) {
        this.hasPoop.value = hasPoop
    }

    fun updateHasPiss(hasPiss: Boolean) {
        this.hasPiss.value = hasPiss
    }

    fun updateDate(date: Long?) {
        this.date.value = date
    }

    fun updateNotes(notes: String) {
        this.notes.value = notes
    }

    fun submit() {
        // TODO submit data

    }

}