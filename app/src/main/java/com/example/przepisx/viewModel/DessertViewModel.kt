package com.example.przepisx.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.przepisx.data.model.Dessert
import com.example.przepisx.data.repository.DessertRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DessertViewModel(private val repository: DessertRepository = DessertRepository()) : ViewModel() {
    private val _addRecipeState = MutableStateFlow<AddRecipeState>(AddRecipeState.Idle)
    val addRecipeState: StateFlow<AddRecipeState> = _addRecipeState
    private val _dessertsState = MutableStateFlow<DessertsState>(DessertsState.Idle)
    val dessertsState: StateFlow<DessertsState> = _dessertsState
    private val _dessertDetailsState = MutableStateFlow<DessertDetailsState>(DessertDetailsState.Idle)
    val dessertDetailsState: StateFlow<DessertDetailsState> = _dessertDetailsState

    fun addRecipe(dessert: Dessert) {
        viewModelScope.launch {
            _addRecipeState.value = AddRecipeState.Loading
            val result = repository.addDessert(dessert)
            if (result.isSuccess) {
                _addRecipeState.value = AddRecipeState.Success
            } else {
                _addRecipeState.value = AddRecipeState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    fun loadDesserts() {
        viewModelScope.launch {
            _dessertsState.value = DessertsState.Loading
            val result = repository.getDesserts()
            _dessertsState.value = if (result.isSuccess) {
                DessertsState.Success(result.getOrThrow())
            } else {
                DessertsState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    fun loadDessertById(recipeId: String) {
        viewModelScope.launch {
            _dessertDetailsState.value = DessertDetailsState.Loading
            val result = repository.getDessertById(recipeId)
            _dessertDetailsState.value = if (result.isSuccess) {
                val dessert = result.getOrThrow()
                if (dessert != null) {
                    DessertDetailsState.Success(dessert)
                } else {
                    DessertDetailsState.Error("Dessert not found")
                }
            } else {
                DessertDetailsState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    fun updateDessert(recipeId: String, updatedDessert: Dessert) {
        viewModelScope.launch {
            try {
                repository.updateDessert(recipeId, updatedDessert)
                _addRecipeState.value = AddRecipeState.Success
            } catch (e: Exception) {
                _addRecipeState.value = AddRecipeState.Error(e.message ?: "Nieznany błąd")
            }
        }
    }


    suspend fun deleteDessert(dessertId: String) {
        repository.deleteDessert(dessertId)
    }

}

sealed class AddRecipeState {
    object Idle : AddRecipeState()
    object Loading : AddRecipeState()
    object Success : AddRecipeState()
    data class Error(val message: String) : AddRecipeState()
}
sealed class DessertsState {
    object Idle : DessertsState()
    object Loading : DessertsState()
    data class Success(val desserts: List<Dessert>) : DessertsState()
    data class Error(val message: String) : DessertsState()
}

sealed class DessertDetailsState {
    object Idle : DessertDetailsState()
    object Loading : DessertDetailsState()
    data class Success(val dessert: Dessert) : DessertDetailsState()
    data class Error(val message: String) : DessertDetailsState()
}
