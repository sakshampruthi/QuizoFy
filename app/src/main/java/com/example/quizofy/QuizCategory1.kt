package com.example.quizofy

import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import kotlinx.android.synthetic.main.activity_quiz_category1.*
import kotlinx.android.synthetic.main.activity_quiz_category1.textView3
import kotlinx.android.synthetic.main.activity_quiz_category10.*
import kotlinx.android.synthetic.main.activity_quiz_category2.*
import java.util.*

class QuizCategory1 : AppCompatActivity() {
    lateinit var timeCount :CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category1)


        startTime.setOnClickListener {
                startTime.visibility=View.INVISIBLE
                startClicked1()
        }
    }

    fun startClicked1() {

        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("00:" + (1+(milisUntilFinished / 1000)))

                if (radioButton11.isChecked||radioButton12.isChecked||radioButton13.isChecked||radioButton14.isChecked) {
                    optionupdate()
                }
                else {
                    next1.setOnClickListener {
                        next1.visibility=View.INVISIBLE
                        resetTimer()
                        optionupdate()
                        next1.visibility=View.VISIBLE
                    }

                }
            }
            override fun onFinish() {
                textView3.text = "Time Over!!"

                next1.setOnClickListener {
                    optionupdate()

                }
            }
        }.start()
    }

    fun resetTimer(){
        timeCount.start()
    }


    fun optionupdate() {
        if (radioButton11.isChecked || radioButton12.isChecked || radioButton13.isChecked || radioButton14.isChecked) {
            radioButton11.isEnabled = false
            radioButton12.isEnabled = false
            radioButton13.isEnabled = false
            radioButton14.isEnabled = false
            next1.setOnClickListener {
                resetTimer()
                resetoptions()
                radioButton11.isEnabled = true
                radioButton12.isEnabled = true
                radioButton13.isEnabled = true
                radioButton14.isEnabled = true
            }

        } else {
            next1.setOnClickListener {
                resetTimer()
            }
        }
    }

    fun resetoptions(){
        if(radioButton11.isChecked){
            radioButton11.isChecked=false
        }
        else if (radioButton12.isChecked){
            radioButton12.isChecked=false
        }
        else if (radioButton13.isChecked){
            radioButton13.isChecked=false
        }
        else {
            radioButton14.isChecked=false
        }
    }


}
