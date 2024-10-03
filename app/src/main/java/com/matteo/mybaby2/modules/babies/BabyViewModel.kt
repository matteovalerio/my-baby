package com.matteo.mybaby2.modules.babies

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matteo.mybaby2.modules.babies.repositories.IBabyRepository
import com.matteo.mybaby2.modules.babies.repositories.MockedBabyRepository
import com.matteo.mybaby2.modules.babies.schemas.BabyRead
import kotlinx.coroutines.launch

class BabyViewModel(val babyRepository: IBabyRepository = MockedBabyRepository()):
    ViewModel() {
    var babies = mutableStateOf<List<BabyRead>>(listOf())
        private set

    init {
        getAllBabies()
    }

    fun getAllBabies() {
        viewModelScope.launch{
            babies.value = babyRepository.getAll();
        }
    }
}