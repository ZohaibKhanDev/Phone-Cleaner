package com.example.phonecleaner.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.phonecleaner.domain.repository.Repository
import com.example.phonecleaner.domain.usecase.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _allStorageCleaner = MutableStateFlow<ResultState<Boolean>>(ResultState.Loading)
    val allStorageCleaner: StateFlow<ResultState<Boolean>> = _allStorageCleaner.asStateFlow()

    
    suspend fun getStorageCleaner() {
        _allStorageCleaner.value = ResultState.Loading
        try {
            val response = repository.cleanupStorage()
            _allStorageCleaner.value = ResultState.Success(response)
        } catch (e: Exception) {
            _allStorageCleaner.value = ResultState.Error(e)
        }
    }
}
