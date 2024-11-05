package com.hax.teeba3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class LoginViewModel : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun login() {
        val emailValue = email.value ?: ""
        val passwordValue = password.value ?: ""

        if (isEmailValid(emailValue) && isPasswordValid(passwordValue)) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(emailValue, passwordValue)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _loginResult.value = true
                    } else {
                        val exception = task.exception
                        _errorMessage.value = when (exception) {
                            is FirebaseAuthException -> "فشل التحقق: ${exception.message}"
                            else -> "فشل تسجيل الدخول. يرجى المحاولة مجدداً."
                        }
                        _loginResult.value = false
                    }
                }
        } else {
            _errorMessage.value = "صيغة البريد الإلكتروني أو كلمة المرور غير صحيحة."
            _loginResult.value = false
        }
    }

    fun updateErrorMessage(message: String) {
        _errorMessage.value = message
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }
}
