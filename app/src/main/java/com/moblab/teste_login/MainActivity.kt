package com.moblab.teste_login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
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
    }

    private fun loginUsuarioSenha(usuario: String, senha: String) {
        auth.signInWithEmailAndPassword(
            usuario,
            senha

        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
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

    fun abrirHome() {

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
            if(currentUser.email?.isNotEmpty() == true){
                Toast.makeText(
                    baseContext, "Usuario " + currentUser.email + " logado",
                    Toast.LENGTH_SHORT
                ).show()
                abrirHome()
            }
        }
    }
}

