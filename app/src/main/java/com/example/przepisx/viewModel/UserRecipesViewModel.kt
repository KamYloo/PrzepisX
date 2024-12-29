package com.example.przepisx.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.przepisx.data.model.Dessert
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserRecipesViewModel : ViewModel() {
    private val _userRecipesState = MutableStateFlow<UserRecipesState>(UserRecipesState.Loading)
    val userRecipesState: StateFlow<UserRecipesState> = _userRecipesState

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun loadUserRecipes() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            _userRecipesState.value = UserRecipesState.Error("Nie jesteś zalogowany.")
            return
        }

        viewModelScope.launch {
            _userRecipesState.value = UserRecipesState.Loading

            firestore.collection("desserts")
                .whereEqualTo("authorId", userId)
                .get()
                .addOnSuccessListener { snapshot ->
                    val recipes = snapshot.documents.mapNotNull { document ->
                        document.toObject(Dessert::class.java)?.copy(id = document.id)
                    }
                    _userRecipesState.value = UserRecipesState.Success(recipes)
                }
                .addOnFailureListener { exception ->
                    _userRecipesState.value = UserRecipesState.Error(exception.message ?: "Błąd pobierania przepisów.")
                }
        }
    }
}

sealed class UserRecipesState {
    object Loading : UserRecipesState()
    data class Success(val recipes: List<Dessert>) : UserRecipesState()
    data class Error(val message: String) : UserRecipesState()
}
