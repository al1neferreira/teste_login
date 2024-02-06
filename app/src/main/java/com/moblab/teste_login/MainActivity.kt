package com.moblab.teste_login

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.moblab.teste_login.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //.auth é o método de autenticação
        auth = Firebase.auth

        //val textBotaoGoogle = binding.btnGoogle.getChildAt(0) as TextView
        //textBotaoGoogle.text = "Login com Google"

        binding.btnLogin.setOnClickListener {

            //mudar de custom token para sign in with e-mail and password
            // (.signInWithEmailAndPassword)

            auth.signInWithEmailAndPassword(
                binding.editTexUsuario.text.toString(),
                binding.editTextSenha.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCustomToken:success")
                        val user = auth.currentUser
                        //updateUI(user)
                        Toast.makeText(
                            baseContext, "Autenticação efetuada com sucesso!",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCustomToken:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Erro de autenticação!",
                            Toast.LENGTH_SHORT
                        ).show()
                        //updateUI(null)
                    }
                }

        }
    }
//método onStart - executado quando uma interface é incializada
// a nivel do onCreate

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        //val currentUser = auth.currentUser
        //updateUI(currentUser)
    }
}

//Parei no 5'20