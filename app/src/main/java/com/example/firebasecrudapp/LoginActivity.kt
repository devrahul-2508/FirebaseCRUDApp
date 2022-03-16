package com.example.firebasecrudapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.firebasecrudapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth
    private val TAG="1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth= FirebaseAuth.getInstance()

        binding.idTVNewUser.setOnClickListener {
            val intent=Intent(this,RegistrationActivity::class.java)
            startActivity(intent)

        }
        binding.idBtnLogin.setOnClickListener {
            binding.idPBLoading.visibility= View.VISIBLE
            val userName=binding.idEdtUserName.text.toString()
            val pwd=binding.idEdtPassword.text.toString()
            if(TextUtils.isEmpty(userName) && TextUtils.isEmpty(pwd)){
            Toast.makeText(this,"Please add credentials", Toast.LENGTH_SHORT).show()
            }
            else{
                mAuth.signInWithEmailAndPassword(userName, pwd)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            binding.idPBLoading.visibility=View.GONE
                            val intent=Intent(this,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            binding.idPBLoading.visibility=View.GONE

                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        if(currentUser != null){
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}