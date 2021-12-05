package com.example.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRegistrar

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var editPassword2: EditText
    private lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        init()
        registerListeners()
    }

    private fun init() {

        editTextEmail = findViewById(R.id.editTextEmail)
        editPassword = findViewById(R.id.editPassword)
        editPassword2 = findViewById(R.id.editTextPassword2)
        buttonRegister = findViewById(R.id.buttonRegister)

    }

    private fun registerListeners() {

        buttonRegister.setOnClickListener {

            if (checkForInformation()){

            }
            val email = editTextEmail.text.toString()
            val password1 = editPassword.text.toString()
            val password2 = editPassword2.text.toString()

            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password1)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, ProfileActivity::class.java)
                        startActivity(intent)
                        checkForInformation()
                        finish()
                    } else {
                        Toast.makeText(this, "Error ! You can't register", Toast.LENGTH_SHORT)
                            .show()

                    }

                }
        }
    } fun checkForInformation(): Boolean {

        if (editTextEmail.text.toString().isEmpty() || editPassword.text.toString().isEmpty() || editPassword2.text.toString().isEmpty()) {
            Toast.makeText(this, "Text is empty!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!editPassword.text.toString().equals(editPassword2.text.toString())){
            Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show()
            return false
        }
        if (editPassword.text.length < 9) {
            Toast.makeText(this, "Password must contain at least 9 characters", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!E_mailValid(editTextEmail.text.toString())) {
            Toast.makeText(this, "Please, enter valid E-mail", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!validPassword(editPassword.text.toString())) {
            Toast.makeText(this, "Password must have atleast one special character among @#\$% and atleast one number", Toast.LENGTH_SHORT).show()
            return false
        }
        return true






    } fun E_mailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
} fun validPassword(password: String): Boolean {
    password.let {
        val passwordPattern = "(?=.*[a-z])(?=.*[0-9])"
        val passwordMatcher = Regex(passwordPattern)
        return passwordMatcher.find(password) != null
    }
    return false

}