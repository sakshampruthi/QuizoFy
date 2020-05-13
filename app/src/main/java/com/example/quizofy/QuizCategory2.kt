package com.example.quizofy

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_quiz_category1.*
import kotlinx.android.synthetic.main.activity_quiz_category1.textView3
import kotlinx.android.synthetic.main.activity_quiz_category2.*
import kotlinx.android.synthetic.main.activity_quiz_category2.textView5
import kotlinx.android.synthetic.main.activity_result.*

class QuizCategory2 : AppCompatActivity() {
    var correct=0
    var wrong=0
    var score=0
    var total=0
    val qlist= ArrayList<String>()
    var iterator = 0
    lateinit var ref:DatabaseReference
    private lateinit var databaseRef:DatabaseReference
    lateinit var timeCount :CountDownTimer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category2)
        Log.d("QuizCategory2","Quiz 2 oncreate called")
        qlist.add("Ques1")
        qlist.add("Ques2")
        qlist.add("Ques3")
        qlist.add("Ques4")
        qlist.add("Ques5")
        Toast.makeText(this,"Press Start Button",Toast.LENGTH_SHORT).show()
        radioButton21.isEnabled=false
        radioButton22.isEnabled=false
        radioButton23.isEnabled=false
        radioButton24.isEnabled=false
        databaseRef=FirebaseDatabase.getInstance().getReference("CATEGORIES")
        startTime2.setOnClickListener {
            startClicked2()
            getQuestion()
            Log.d("QuizCategory2","get question called and timer started")
            radioButton21.isEnabled=true
            radioButton22.isEnabled=true
            radioButton23.isEnabled=true
            radioButton24.isEnabled=true
            startTime2.visibility=View.INVISIBLE
            total++
        }
        next2.setOnClickListener {
            if (iterator<5) {
                radioButton21.isEnabled = true
                radioButton22.isEnabled = true
                radioButton23.isEnabled = true
                radioButton24.isEnabled = true
                radioButton21.setBackgroundColor(Color.TRANSPARENT)
                radioButton22.setBackgroundColor(Color.TRANSPARENT)
                radioButton23.setBackgroundColor(Color.TRANSPARENT)
                radioButton24.setBackgroundColor(Color.TRANSPARENT)
                iterator++
                getQuestion()
                resetoptions()
                optionupdate()
                resetTimer()
                total++
            }
            else{
                resultKaIntent()
            }

        }
        submitButton2.setOnClickListener {
            resultKaIntent()
            Toast.makeText(this, "Your Result", Toast.LENGTH_SHORT).show()
        }
    }
    fun startClicked2() {
        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("00:" + (1 + (milisUntilFinished / 1000)))
                if (radioButton21.isChecked || radioButton22.isChecked || radioButton23.isChecked || radioButton24.isChecked){
                    radioButton21.isEnabled=false
                    radioButton22.isEnabled=false
                    radioButton23.isEnabled=false
                    radioButton24.isEnabled=false
                    checkCorrectOrWrongForQues()
                }
            }
            override fun onFinish() {
                textView3.text = "Time Over!!"
                radioButton21.isEnabled = false
                radioButton22.isEnabled = false
                radioButton23.isEnabled = false
                radioButton24.isEnabled = false
            }
        }.start()

    }
    fun resetTimer(){
        timeCount.start()
    }
    fun optionupdate() {
        if (radioButton21.isChecked || radioButton22.isChecked || radioButton23.isChecked || radioButton24.isChecked) {
            resetoptions()
        } else {
            resetTimer()
        }
    }
    fun resetoptions(){
        if(radioButton21.isChecked){
            radioButton21.isChecked=false
        }
        else if (radioButton22.isChecked){
            radioButton22.isChecked=false
        }
        else if (radioButton23.isChecked){
            radioButton23.isChecked=false
        }
        else {
            radioButton24.isChecked=false
        }
    }
    fun resultKaIntent(){
        val intent =Intent(this,ResultActivity::class.java)
        intent.putExtra("correct",correct)
        intent.putExtra("Wrong",wrong)
        intent.putExtra("score",score)
        startActivity(intent)
//        correctAns.setText(correct)
//        wrongAns.setText(wrong)
//        ScoreTotal.setText(score)
    }
    fun getQuestion(){
        if (total > 4) {
            resultKaIntent()
        } else
        {
            ref = databaseRef.child("Geography")
                .child(qlist.get(iterator))
            Log.d("QuizCategory2","firebase data get" )
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("QuizCategory2","on data change for started"+dataSnapshot.children.toString())
                    val option1Text=dataSnapshot.child("option1").getValue().toString()
                    val option2Text=dataSnapshot.child("option2").getValue().toString()
                    val option3Text=dataSnapshot.child("option3").getValue().toString()
                    val option4Text=dataSnapshot.child("option4").getValue().toString()
                    val questionText=dataSnapshot.child("question").getValue().toString()
                    textView5.setText(questionText)
                    radioButton21.setText(option1Text)
                    radioButton22.setText(option2Text)
                    radioButton23.setText(option3Text)
                    radioButton24.setText(option4Text)

                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("QuizCategory2","Something Went Wrong!!")
                }
            })
        }
    }
    fun checkCorrectOrWrongForQues(){
        ref = databaseRef.child("Geography").child(qlist.get(iterator))
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory2", "Something went Wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val correctAns = dataSnapshot.child("correct").getValue().toString()
                if (radioButton21.isChecked  && radioButton21.text.toString().equals(correctAns))
                {
                    correct++
                    score++
                    radioButton21.setBackgroundColor(Color.GREEN)
                } else {
                    wrong++
                    radioButton21.setBackgroundColor(Color.RED)
                }

                if (radioButton22.isChecked && radioButton22.text.toString().equals(correctAns)) {
                    correct++
                    radioButton22.setBackgroundColor(Color.GREEN)
                    score++
                } else {
                    wrong++
                    radioButton22.setBackgroundColor(Color.RED)
                }


                if (radioButton23.isChecked && radioButton23.text.toString().equals(correctAns)) {
                    correct++
                    radioButton23.setBackgroundColor(Color.GREEN)
                    score++
                } else {
                    wrong++
                    radioButton23.setBackgroundColor(Color.RED)
                }

                if (radioButton24.isChecked && radioButton24.text.toString().equals(correctAns)) {
                    correct++
                    radioButton24.setBackgroundColor(Color.GREEN)
                    score++
                } else {
                    wrong++
                    radioButton24.setBackgroundColor(Color.RED)
                }
            }
        })
    }
}
