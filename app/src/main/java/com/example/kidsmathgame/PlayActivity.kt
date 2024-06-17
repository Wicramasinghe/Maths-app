package com.example.kidsmathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class PlayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        val addition = findViewById<Button>(R.id.addition)
        val sub = findViewById<Button>(R.id.sub)
        val multiply = findViewById<Button>(R.id.multiply)
        val divider = findViewById<Button>(R.id.divider)

        addition.setOnClickListener{
            val calInt = Intent(this@PlayActivity,
                MainActivity::class.java)

            calInt.putExtra("cals","+")
            startActivity(calInt)
        }
        sub.setOnClickListener{
            val calInt = Intent(this@PlayActivity,
                MainActivity::class.java)

            calInt.putExtra("cals","-")
            startActivity(calInt)
        }
        multiply.setOnClickListener{
            val calInt = Intent(this@PlayActivity,
                MainActivity::class.java)

            calInt.putExtra("cals","*")
            startActivity(calInt)
        }
        divider.setOnClickListener{
            val calInt = Intent(this@PlayActivity,
                MainActivity::class.java)

            calInt.putExtra("cals","/")
            startActivity(calInt)
        }
    }
}