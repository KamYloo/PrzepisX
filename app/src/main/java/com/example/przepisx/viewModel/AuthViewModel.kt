package com.example.przepisx.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthViewModel : ViewModel() {
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState
    private val _userData = MutableLiveData<Map<String, String>>()
    val userData: LiveData<Map<String, String>> get() = _userData

    val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    init {
        checkAuthStatus()
    }

    fun fetchUserData(userId: String) {
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val data = document.data?.mapValues { it.value.toString() } ?: emptyMap()
                    _userData.postValue(data)
                } else {
                    _userData.postValue(emptyMap())
                }
            }
            .addOnFailureListener {
                _userData.postValue(emptyMap())
            }
    }

    fun checkAuthStatus() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            val userId = currentUser.uid
            _authState.value = AuthState.Authenticated(userId)
        }
    }

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password can't be empty!")
            return
        }

        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: ""
                    _authState.value = AuthState.Authenticated(userId)
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }


    fun register(email: String, password: String, firstName: String, lastName: String) {
        if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            _authState.value = AuthState.Error("All fields must be filled!")
            return
        }

        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: return@addOnCompleteListener

                    val user = hashMapOf(
                        "firstName" to firstName,
                        "lastName" to lastName,
                        "email" to email
                    )

                    FirebaseFirestore.getInstance().collection("users").document(userId)
                        .set(user)
                        .addOnCompleteListener { firestoreTask ->
                            if (firestoreTask.isSuccessful) {
                                _authState.value = AuthState.Authenticated(userId)
                            } else {
                                _authState.value = AuthState.Error(
                                    firestoreTask.exception?.message ?: "Failed to save user data"
                                )
                            }
                        }
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }



    fun signout() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }
}

sealed class AuthState {
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Authenticated(val userId: String) : AuthState()
    data class Error(val message: String) : AuthState()
}
