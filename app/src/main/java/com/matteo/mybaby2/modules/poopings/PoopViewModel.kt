package com.matteo.mybaby2.modules.poopings

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matteo.mybaby2.common.schemas.UiState
import com.matteo.mybaby2.modules.poopings.repositories.IPoopingRepository
import com.matteo.mybaby2.modules.poopings.schemas.PoopingRead
import com.matteo.mybaby2.modules.poopings.schemas.PoopingUpsert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class PoopUiState {
    object Loading : PoopUiState()
    object Success: PoopUiState()
    data class Error(val exception: Throwable) : PoopUiState()
}

class PoopViewModel(private val repository: IPoopingRepository) : ViewModel() {
    var id = mutableStateOf<Int?>(null)
        private set
    var hasPoop = mutableStateOf(false)
        private set
    var hasPiss = mutableStateOf(false)
        private set
    var notes = mutableStateOf("")
        private set
    var date = mutableStateOf<Long?>(null)
        private set
    var uiState = mutableStateOf<PoopUiState>(PoopUiState.Success)
        private set
    var poopings = mutableStateOf<List<PoopingRead>>(emptyList())

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
    fun updateId(id: Int) {
        this.id.value = id
    }

    fun submit() {
        uiState.value = PoopUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if(date.value == null){
                    // TODO dirty hack, add validations!
                    date.value = System.currentTimeMillis()
                }

                repository.upsertPooping(
                    PoopingUpsert(
                        id.value,
                        hasPoop.value,
                        hasPiss.value,
                        notes.value,
                        date.value!!
                    )
                )
                uiState.value = PoopUiState.Success
            } catch (exception: Exception) {
                uiState.value = PoopUiState.Error(exception)
            }
        }
    }

    fun getAllPoopings() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.getAll()
                poopings.value = result
            } catch (exception: Exception) {
                // TODO error handling
            }
        }
    }

}