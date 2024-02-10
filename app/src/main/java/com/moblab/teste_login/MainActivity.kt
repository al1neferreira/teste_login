package com.moblab.teste_login


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.moblab.teste_login.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth

        val googleSignInOption = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(
                "cliente1")
            .requestEmail()
            .build()

              googleSignInClient = GoogleSignIn.getClient(this, googleSignInOption)


        binding.btnLogin.setOnClickListener {


            if (TextUtils.isEmpty(binding.editTexUsuario.text)) {
                binding.editTexUsuario.error = "O campo Usuário não pode ficar em branco"

            } else if (TextUtils.isEmpty(binding.editTextSenha.text)) {
                binding.editTextSenha.error = "O campo Senha não pode ficar em branco"

            } else {
                loginUsuarioSenha(
                    binding.editTexUsuario.text.toString(),
                    binding.editTextSenha.text.toString()
                )
            }
        }

        binding.btnGoogle.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val intent = googleSignInClient.signInIntent
        abreActivity.launch(intent)
    }

    private var abreActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        if (result.resultCode == RESULT_OK) {
            val intent = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)

            try {
                val conta = task.getResult(ApiException::class.java)
                loginComGoogle(conta.idToken!!)

            } catch (_: ApiException) {

            }
        }
    }

    private fun loginUsuarioSenha(usuario: String, senha: String) {
        auth.signInWithEmailAndPassword(
            usuario,
            senha
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext, "Autenticação efetuada com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()
                    abrirHome()
                } else {
                    Toast.makeText(
                        baseContext, "Erro de autenticação!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun loginComGoogle(token: String) {
        val credencial = GoogleAuthProvider.getCredential(token, null)
        auth.signInWithCredential(credencial)
            .addOnCompleteListener(this) { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext, "Autenticação efetuada com o Google!",
                        Toast.LENGTH_SHORT
                    ).show()
                    abrirHome()
                } else {
                    Toast.makeText(
                        baseContext, "Erro de autenticação com o Google!",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }

    private fun abrirHome() {
        binding.editTexUsuario.text.clear()
        binding.editTextSenha.text.clear()
        val intent = Intent(
            this,
            HomeActivity::class.java
        )
        startActivity(intent)
        finish()
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        //updateUI(currentUser)
        if (currentUser != null) {
            if (currentUser.email?.isNotEmpty() == true) {
                Toast.makeText(
                    baseContext, "Usuario " + currentUser.email + " logado",
                    Toast.LENGTH_SHORT
                ).show()
                abrirHome()
            }
        }
    }
}

