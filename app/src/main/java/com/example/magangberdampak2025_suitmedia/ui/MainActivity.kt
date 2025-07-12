package com.example.magangberdampak2025_suitmedia.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.magangberdampak2025_suitmedia.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etName = findViewById<EditText>(R.id.etName)
        val etPalindrome = findViewById<EditText>(R.id.etPalindrome)
        val btnCheck = findViewById<Button>(R.id.btnCheck)
        val btnNext = findViewById<Button>(R.id.btnNext)

        btnCheck.setOnClickListener {
            if (etPalindrome.text.isBlank()){
                showDialog("field palindrome cannot be empty")
            }else{
                val text = etPalindrome.text.toString()
                if (checkPalindrome(text)) {
                    showDialog("isPalindrome")
                } else {
                    showDialog("not palindrome")
                }
            }
        }

        btnNext.setOnClickListener{
            if (etName.text.isBlank()){
                showDialog("field name cannot be empty")
            }else{
                val intent = Intent(this, SecondScreen::class.java)
                intent.putExtra("EXTRA_NAME", etName.text.toString())
                startActivity(intent)
            }
        }
    }
    private fun checkPalindrome(text: String): Boolean{
        val clean = text.replace("\\s".toRegex(), "").lowercase()
        return clean == clean.reversed()
    }

    private fun showDialog(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}