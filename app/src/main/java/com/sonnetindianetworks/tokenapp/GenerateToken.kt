package com.sonnetindianetworks.tokenapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_generate_token.*
import java.util.*

class GenerateToken : AppCompatActivity() {

    lateinit var tokenNoGenerateToken: EditText

    lateinit var mobileGenerateToken: EditText

    lateinit var nameGenerateToken: EditText
    lateinit var dateGenerateToken: EditText
    lateinit var issuedByGenerateToken: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_token)

        tokenNoGenerateToken = findViewById(R.id.tokenNo_GenerateTokenActivity)
        mobileGenerateToken = findViewById(R.id.editText_mobile_GenerateTokenActivity)
        nameGenerateToken = findViewById(R.id.editText_name_GenerateTokenActivity)
        dateGenerateToken = findViewById(R.id.date_GenerateTokenActivity)
        issuedByGenerateToken = findViewById(R.id.editText_issuedBy_GenerateTokenActivity)

        button_sendToken_GenerateTokenActivity.setOnClickListener {
            generateToken()
        }

    }

    private fun generateToken() {
        val db = FirebaseFirestore.getInstance()
        val name = nameGenerateToken.text.toString().trim()
        val mobile = "+61" + mobileGenerateToken.text.toString()
        val issuedBy = issuedByGenerateToken.text.toString()
        val date = dateGenerateToken.text.toString()
        val tokenNo = tokenNoGenerateToken.text.toString()

        if (issuedBy.isEmpty() || date.isEmpty() || tokenNo.isEmpty() || name.isEmpty() || mobile.isEmpty()) {

            Toast.makeText(this, "Enter Valid Details", Toast.LENGTH_SHORT).show()
        } else {
            val token = TokenGenerate(tokenNo, issuedBy, mobile, name, date)

            db.collection("UserDetails").document(mobile).collection("IssuedToken")
                .document(issuedBy)
                .set(token, SetOptions.merge())


        }


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu , menu)
        return super.onCreateOptionsMenu(menu)

    }
}

data class TokenGenerate(
    val tokenNo: String? = null,
    val issuedBy: String? = null,
    val mobile: String? = null,
    val name: String? = null,
    val date: String? = null

)
