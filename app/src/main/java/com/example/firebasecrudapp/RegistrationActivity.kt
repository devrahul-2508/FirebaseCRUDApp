package com.example.firebasecrudapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.firebasecrudapp.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : AppCompatActivity() {
    lateinit var binding:ActivityRegistrationBinding
    private lateinit var mAuth:FirebaseAuth
    private val TAG="1234"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth= FirebaseAuth.getInstance()

        binding.idTVLoginUser.setOnClickListener {
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)

        }
        binding.idBtnRegister.setOnClickListener {
            binding.idPBLoading.visibility=View.VISIBLE
            val userName=binding.edtUserName.text.toString()
            val pwd=binding.edtPassword.text.toString()
            val cnfPwd=binding.edtCNFPassword.text.toString()
            if(pwd != cnfPwd){
                Toast.makeText(this,"Please check both passwords",Toast.LENGTH_SHORT).show()
            }
            else if(TextUtils.isEmpty(userName) && TextUtils.isEmpty(pwd) && TextUtils.isEmpty(cnfPwd)){
                Toast.makeText(this,"Please add credentials",Toast.LENGTH_SHORT).show()

            }
            else{
                mAuth.createUserWithEmailAndPassword(userName, pwd)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            binding.idPBLoading.visibility=View.GONE
                            val intent=Intent(this,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                            Log.d(TAG, "createUserWithEmail:success")

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
}