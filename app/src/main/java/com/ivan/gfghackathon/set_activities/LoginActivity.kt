package com.ivan.gfghackathon.set_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.ivan.gfghackathon.MainActivity
import com.ivan.gfghackathon.R
import com.ivan.gfghackathon.databinding.ActivityLoginBinding
import com.ivan.gfghackathon.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var mgoogleSignInClient: GoogleSignInClient

    override fun onStart() {
        super.onStart()

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseAuth.getInstance().addAuthStateListener {fAuth->

            if(fAuth.currentUser!=null){
                val intent = Intent(this@LoginActivity,HomeActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }

        val actionBarInst = supportActionBar
        actionBarInst!!.hide()


        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mgoogleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions)

        binding.loginBtn.setOnClickListener{
            if(!binding.lsignEtEmail.text.toString().isBlank() && !binding.signEtPassword.text.toString().isBlank()){
                Toast.makeText(this,"Please wait..", Toast.LENGTH_SHORT).show()
                auth.signInWithEmailAndPassword(binding.lsignEtEmail.text.toString().trim(),binding.signEtPassword.text.toString().trim())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val main_intent = Intent(this, HomeActivity::class.java)
                            main_intent.putExtra("username", auth.currentUser!!.displayName)
                            startActivity(main_intent)
                            finish()
                        } else {
                            Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(this,"Please enter all the fields.", Toast.LENGTH_SHORT ).show()
            }
        }
        binding.googleBtn.setOnClickListener{v->

        }

        binding.facebookBtn.setOnClickListener{v->

        }

        binding.SignInIntent.setOnClickListener{v->
            val signInIntent = Intent(this,MainActivity::class.java)
            Log.e("tag", "LoginActivity->main", )
            startActivity(signInIntent)
            finish()
        }

    }


    val RC_SIGN_IN:Int = 69
    private fun signIn() {

        val gIntent = mgoogleSignInClient.signInIntent
        startActivityForResult(gIntent,RC_SIGN_IN)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==RC_SIGN_IN){
            val task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken)
            }catch(e: ApiException){
                Log.e("tag", e.message.toString() )
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential : AuthCredential = GoogleAuthProvider.getCredential(idToken,null)
        Toast.makeText(this,"Loading..",Toast.LENGTH_SHORT)
            .show()

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this){task->
                if(task.isSuccessful){
                    Toast.makeText(this,"Welcome",Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this,HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this,task.exception?.message,Toast.LENGTH_SHORT).show()
                }
            }
    }
}