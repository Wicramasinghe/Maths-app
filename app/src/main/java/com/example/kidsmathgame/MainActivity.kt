package com.example.kidsmathgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import java.lang.Exception
import java.util.ArrayList
import java.util.Random
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate


class MainActivity : AppCompatActivity() {

    //chat
    private lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var switchTheme: Switch

    var QuestionText : TextView? = null
    var TimeTextView : TextView? = null
    var AlertTextView : TextView? = null
    var scoreTextView : TextView? = null
    var FinalScoreTextView : TextView? = null
    var button0 : Button? = null
    var button1 : Button? = null
    var button2 : Button? = null
    var button3 : Button? = null

    var countDownTimer : CountDownTimer? = null
    var random :Random = Random()
    var a = 0
    var b = 0
    var indexOfCorrectAnswer =0
    var answers = ArrayList<Int>()
    var points = 0
    var totalQuestions = 0
    var cals = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPrefManager = SharedPrefManager(this)
        switchTheme = findViewById(R.id.switch1) // Replace with your switch ID

        val isDarkMode = sharedPrefManager.isDarkMode()
        delegate.localNightMode = if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO

        switchTheme.isChecked = isDarkMode

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            sharedPrefManager.setDarkMode(isChecked)
            delegate.localNightMode = if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            recreate() // Recreate activity to apply new theme
        }

        val calInt = intent.getStringExtra("cals")
        cals = calInt!!

        TimeTextView = findViewById(R.id.TimeTextView)
        QuestionText = findViewById(R.id.QuestionText)
        AlertTextView = findViewById(R.id.AlertTextView)
        scoreTextView = findViewById(R.id.scoreTextView)

        button0 = findViewById(R.id.button0)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)

        start()
    }
    fun NextQuestion(cal:String){
        a = random.nextInt(10)
        b = random.nextInt(10)
        QuestionText!!.text = "$a $cal $b"
        indexOfCorrectAnswer = random.nextInt(4)

        answers.clear()

        for (i in 0..3){
            if (indexOfCorrectAnswer==i){
                when(cal){
                    "+"->{answers.add(a+b)}
                    "-"->{answers.add(a-b)}
                    "*"->{answers.add(a*b)}
                    "/"-> {
                        try {
                            answers.add(a / b)
                        }
                        catch (e:Exception){
                            e.printStackTrace()
                        }
                    }
                }
            }
            else{
                var wrongAnswer = random.nextInt(20)
                try {
                    while (
                        wrongAnswer==a+b
                        || wrongAnswer == a-b
                        || wrongAnswer == a*b
                        || wrongAnswer == a/b
                    ){
                        wrongAnswer = random.nextInt(20)
                    }
                    answers.add(wrongAnswer)
                }
                catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
        try {
            button0!!.text = "${answers[0]}"
            button1!!.text = "${answers[1]}"
            button2!!.text = "${answers[2]}"
            button3!!.text = "${answers[3]}"

        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun optionSelect(view:View?){
        totalQuestions++
        if (indexOfCorrectAnswer.toString()==view!!.tag.toString()){
            points++
            AlertTextView!!.text="Correct"
        }
        else{
            AlertTextView!!.text="Wrong"
        }
        scoreTextView!!.text="$points/$totalQuestions"
        NextQuestion(cals)
    }

    fun PlaAgain(view: View?){
        points = 0
        totalQuestions = 0
        scoreTextView!!.text="$points/$totalQuestions"
        countDownTimer!!.start()
    }

    private fun start(){
        NextQuestion(cals)
        countDownTimer = object : CountDownTimer(30000,1000){
            override fun  onTick(p0:Long){
                TimeTextView!!.text = (p0/1000).toString()+"s"

            }

            override fun onFinish() {
                TimeTextView!!.text = "Time Up!"
                openDilog()
            }
        }.start()
    }

    private fun openDilog(){
        val inflate = LayoutInflater.from(this)
        val winDialog = inflate.inflate(R.layout.win_layout,null)
        FinalScoreTextView = winDialog.findViewById(R.id.FinalScoreTextView)
        val btnPlayAgain = winDialog.findViewById<Button>(R.id.buttonPlayAgain)
        val btnBack = winDialog.findViewById<Button>(R.id.btnBack)
        val dialog = AlertDialog.Builder(this)
        dialog.setCancelable(false)
        dialog.setView(winDialog)
        FinalScoreTextView!!.text="$points/$totalQuestions"
        btnPlayAgain.setOnClickListener{PlaAgain(it)}
        btnBack.setOnClickListener{onBackPressed()}
        val showDialog = dialog.create()
        showDialog.show()
    }
}