package com.example.przepisx.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.przepisx.data.model.Dessert
import com.example.przepisx.data.repository.RecipeRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipeRepository = RecipeRepository()) : ViewModel() {
    private val _addRecipeState = MutableStateFlow<AddRecipeState>(AddRecipeState.Idle)
    val addRecipeState: StateFlow<AddRecipeState> = _addRecipeState

    fun addRecipe(dessert: Dessert) {
        viewModelScope.launch {
            _addRecipeState.value = AddRecipeState.Loading
            val result = repository.addRecipe(dessert)
            if (result.isSuccess) {
                _addRecipeState.value = AddRecipeState.Success
            } else {
                _addRecipeState.value = AddRecipeState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }
}

sealed class AddRecipeState {
    object Idle : AddRecipeState()
    object Loading : AddRecipeState()
    object Success : AddRecipeState()
    data class Error(val message: String) : AddRecipeState()
}