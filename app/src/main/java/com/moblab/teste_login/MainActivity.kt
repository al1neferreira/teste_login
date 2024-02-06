package com.moblab.teste_login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        auth = Firebase.auth

        binding.btnLogin.setOnClickListener {


            if (binding.editTexUsuario.text.isNullOrEmpty()) {

                Toast.makeText(
                    baseContext, "Insira os dados do usuário",
                    Toast.LENGTH_SHORT
                ).show()

            } else if (binding.editTextSenha.text.isNullOrEmpty()) {

                Toast.makeText(
                    baseContext, "Digite a sua senha",
                    Toast.LENGTH_SHORT
                ).show()

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

        Toast.makeText(
            baseContext, "Autenticação efetuada com sucesso!",
            Toast.LENGTH_SHORT
        ).show()

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

    }
}

