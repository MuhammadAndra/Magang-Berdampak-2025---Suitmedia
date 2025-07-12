package com.example.magangberdampak2025_suitmedia.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.magangberdampak2025_suitmedia.R

class SecondScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_screen)

        val resultLauncher: ActivityResultLauncher<Intent>
        val firstScreenName = intent.getStringExtra("EXTRA_NAME")
//        val thirdScreenName = intent.getStringExtra("SELECTED_NAME")
        val tvFirstScreenName = findViewById<TextView>(R.id.tvFirstScreenName)
        val tvThirdScreenName = findViewById<TextView>(R.id.tvThirdScreenName)
        val btChooseUser = findViewById<Button>(R.id.btChooseAUser)
        val tbSecondScreen = findViewById<Toolbar>(R.id.tbSecondScreen)

        tvFirstScreenName.text = firstScreenName
//        tvThirdScreenName.text = thirdScreenName ?: "Selected User Name"

        setSupportActionBar(tbSecondScreen)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        tbSecondScreen.setNavigationOnClickListener {
            finish()
        }

        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedName = result.data?.getStringExtra("SELECTED_NAME")
                tvThirdScreenName.text = selectedName ?: "Selected User Name"
            }
        }

        // ketika tombol ke ThirdScreen diklik
        btChooseUser.setOnClickListener{
            val intent = Intent(this, ThirdScreen::class.java)
//            startActivity(intent)
            resultLauncher.launch(intent)
        }
    }
}