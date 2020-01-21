package com.example.quizofy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import kotlinx.android.synthetic.main.activity_quiz_category1.*
import kotlinx.android.synthetic.main.activity_quiz_category1.textView3
import kotlinx.android.synthetic.main.activity_quiz_category2.*
import kotlinx.android.synthetic.main.activity_quiz_category7.*
import kotlinx.android.synthetic.main.activity_quiz_category8.*

class QuizCategory8 : AppCompatActivity() {
lateinit var timeCount:CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category8)

        startTime8.setOnClickListener {
            startTime8.visibility=View.INVISIBLE
            startClicked8()
        }

        submitButton8.setOnClickListener {
            resultKaIntent8()
        }
    }
    fun startClicked8() {

        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("00:" + (1+(milisUntilFinished / 1000)))

                if (radioButton81.isChecked||radioButton82.isChecked||radioButton83.isChecked||radioButton84.isChecked) {
                    optionupdate()
                }
                else {
                    next8.setOnClickListener {
                        next8.visibility=View.INVISIBLE
                        resetTimer()
                        optionupdate()
                        next8.visibility=View.VISIBLE
                    }

                }
            }
            override fun onFinish() {
                textView3.text = "Time Over!!"

                next8.setOnClickListener {
                    optionupdate()

                }
            }
        }.start()
    }

    fun resetTimer(){
        timeCount.start()
    }


    fun optionupdate() {
        if (radioButton81.isChecked || radioButton82.isChecked || radioButton83.isChecked || radioButton84.isChecked) {
                radioButton81.isEnabled = false
                radioButton82.isEnabled = false
                radioButton83.isEnabled = false
                radioButton84.isEnabled = false
            next8.setOnClickListener {
                resetTimer()
                resetoptions()
                radioButton81.isEnabled = true
                radioButton82.isEnabled = true
                radioButton83.isEnabled = true
                radioButton84.isEnabled = true
            }

        } else {
            next8.setOnClickListener {
                resetTimer()
            }
        }
    }

    fun resetoptions(){
        if(radioButton81.isChecked){
            radioButton81.isChecked=false
        }
        else if (radioButton82.isChecked){
            radioButton82.isChecked=false
        }
        else if (radioButton83.isChecked){
            radioButton83.isChecked=false
        }
        else {
            radioButton84.isChecked=false
        }
    }

    fun resultKaIntent8(){
        val intent = Intent(this,ResultActivity::class.java)
        startActivity(intent)
    }

}
