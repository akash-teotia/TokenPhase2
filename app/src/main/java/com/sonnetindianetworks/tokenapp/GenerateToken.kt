package com.sonnetindianetworks.tokenapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.hbb20.CountryCodePicker
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

        val issuedBy = issuedByGenerateToken.text.toString()
        val date = dateGenerateToken.text.toString()
        val tokenNo = tokenNoGenerateToken.text.toString()
        val ccp: CountryCodePicker = findViewById(R.id.ccp)
        val dataMobile = "+" + ccp.fullNumberWithPlus + mobileGenerateToken.text.toString()
        Toast.makeText(this, dataMobile, Toast.LENGTH_SHORT).show()


        if (issuedBy.isEmpty() || date.isEmpty() || tokenNo.isEmpty() || name.isEmpty() || dataMobile.isEmpty()) {

            Toast.makeText(this, "Enter Valid Details", Toast.LENGTH_SHORT).show()
        } else {
            val token = TokenGenerate(tokenNo, issuedBy, dataMobile, name, date)

            db.collection("UserDetails").document(dataMobile).collection("IssuedToken")
                .document(issuedBy)
                .set(token, SetOptions.merge())
           // Toast.makeText(this, "Token Successfully Issued", Toast.LENGTH_SHORT).show()


        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_signOut -> {
                signOut()
            }

        }


        return super.onOptionsItemSelected(item)


    }

    private fun signOut() {
        auth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show()
    }

}
data class TokenGenerate(
    val tokenNo: String? = null,
    val issuedBy: String? = null,
    val mobile: String? = null,
    val name: String? = null,
    val date: String? = null

)
