import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a5046.R
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SignUpViewModel: ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _signInResult = Channel<Result<String>>()
    private val _signUpResult = MutableStateFlow<Result<String>>(Result.success(""))
    val signUpResult: StateFlow<Result<String>> = _signUpResult.asStateFlow()

    val signInResult: Flow<Result<String>> = _signInResult.receiveAsFlow()


    fun signUpUser(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _signUpResult.value = Result.success("User created successfully")
                    } else {
                        _signUpResult.value = Result.failure(task.exception ?: Exception("Unknown error"))
                    }
                }
        } else {
            _signUpResult.value = Result.failure(Exception("Email or password cannot be blank"))
        }
    }

    fun signInUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                result.user?.let {
                    _signInResult.send(Result.success("Login successful"))
                } ?: run {
                    _signInResult.send(Result.failure(Throwable("Login failed")))
                }
            } catch (e: Exception) {
                _signInResult.send(Result.failure(Throwable(e.message ?: "Login failed")))
            }
        }
    }
    fun signInWithGoogle(credential: AuthCredential) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val authResult = FirebaseAuth.getInstance().signInWithCredential(credential).await()
                _signInResult.send(Result.success("Google sign-in successful"))
                // Optionally update UI or navigate
            } catch (e: FirebaseAuthException) {
                _signInResult.send(Result.failure(Throwable("Google sign-in failed: ${e.message}")))
            }
        }
    }




}