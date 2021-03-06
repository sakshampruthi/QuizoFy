package com.example.quizofy

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_quiz_category1.*
import kotlinx.android.synthetic.main.activity_quiz_category1.textView3
import kotlinx.android.synthetic.main.activity_quiz_category10.*
import kotlinx.android.synthetic.main.activity_quiz_category3.*
import kotlinx.android.synthetic.main.activity_quiz_category4.*
import kotlinx.android.synthetic.main.activity_quiz_category5.*
import kotlinx.android.synthetic.main.activity_quiz_category5.textView5
import kotlinx.android.synthetic.main.activity_quiz_category9.*
import kotlinx.android.synthetic.main.activity_result.*

class QuizCategory5 : AppCompatActivity() {
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
        setContentView(R.layout.activity_quiz_category5)
        val ab: ActionBar?=supportActionBar
        if (ab!=null){
            ab.setBackgroundDrawable(getDrawable(R.drawable.actionbargrag))
        }
        Log.d("QuizCategory5","Quiz 5 oncreate called")
        qlist.add("Ques1")
        qlist.add("Ques2")
        qlist.add("Ques3")
        qlist.add("Ques4")
        qlist.add("Ques5")
        Toast.makeText(this,"Press Start Button",Toast.LENGTH_SHORT).show()
        radioButton51.isEnabled=false
        radioButton52.isEnabled=false
        radioButton53.isEnabled=false
        radioButton54.isEnabled=false
        next5.visibility=View.INVISIBLE

        databaseRef=FirebaseDatabase.getInstance().getReference("CATEGORIES")
        submitButton5.visibility=View.INVISIBLE

        startTime5.setOnClickListener {
            startClicked()
            textView3.setTextColor(Color.GREEN)
            getQuestion()
            Log.d("QuizCategory5","get question called and timer started")
            radioButton51.isEnabled=true
            radioButton52.isEnabled=true
            radioButton53.isEnabled=true
            radioButton54.isEnabled=true
            next5.visibility=View.VISIBLE
            startTime5.visibility=View.INVISIBLE
            total++
        }

        next5.setOnClickListener {
            if (iterator<5) {
                radioButton51.isEnabled = true
                radioButton52.isEnabled = true
                radioButton53.isEnabled = true
                radioButton54.isEnabled = true
                radioButton51.setBackgroundColor(Color.TRANSPARENT)
                radioButton52.setBackgroundColor(Color.TRANSPARENT)
                radioButton53.setBackgroundColor(Color.TRANSPARENT)
                radioButton54.setBackgroundColor(Color.TRANSPARENT)
                textView3.setTextColor(Color.GREEN)
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

        submitButton5.setOnClickListener {
            resultKaIntent()
            Toast.makeText(this, "Your Result", Toast.LENGTH_SHORT).show()
        }
    }


    fun startClicked() {
        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("Time Left:"+"00:" + (1 + (milisUntilFinished / 1000)))
                if ((1 + (milisUntilFinished / 1000))<=3){
                    textView3.setTextColor(Color.RED)
                }
                if (radioButton51.isChecked || radioButton52.isChecked || radioButton53.isChecked || radioButton54.isChecked){
                    radioButton51.isEnabled=false
                    radioButton52.isEnabled=false
                    radioButton53.isEnabled=false
                    radioButton54.isEnabled=false
                    checkCorrectOrWrongForQues()
                }

            }
            override fun onFinish() {
                textView3.text = "Time Over!!"
                radioButton51.isEnabled = false
                radioButton52.isEnabled = false
                radioButton53.isEnabled = false
                radioButton54.isEnabled = false
            }
        }.start()

    }

    fun resetTimer(){
        timeCount.start()
    }

    fun optionupdate() {
        if (radioButton51.isChecked || radioButton52.isChecked || radioButton53.isChecked || radioButton54.isChecked) {
            resetoptions()
        } else {
            resetTimer()
        }
    }

    fun resetoptions(){
        if(radioButton51.isChecked){
            radioButton51.isChecked=false
        }
        else if (radioButton52.isChecked){
            radioButton52.isChecked=false
        }
        else if (radioButton53.isChecked){
            radioButton53.isChecked=false
        }
        else {
            radioButton54.isChecked=false
        }
    }
    fun resultKaIntent(){
        val intent =Intent(this,ResultActivity::class.java)
        intent.putExtra("correct",correct.toString())
        intent.putExtra("Wrong",wrong.toString())
        intent.putExtra("score",score.toString())
        startActivity(intent)
    }


    fun getQuestion(){
        if (total >=5) {
            submitButton5.visibility=View.VISIBLE
            val anim2=AnimationUtils.loadAnimation(this,R.anim.fadein)
            submitButton5.startAnimation(anim2)
            next5.visibility=View.INVISIBLE
            radioButton51.isEnabled=false
            radioButton52.isEnabled=false
            radioButton53.isEnabled=false
            radioButton54.isEnabled=false
            textView3.visibility=View.INVISIBLE
            Toast.makeText(this,"Press Submit button to view the result",Toast.LENGTH_SHORT).show()
            submitButton5.setOnClickListener {
                resultKaIntent()
            }
        } else
        {
            ref = databaseRef.child("Technology")
                .child(qlist.get(iterator))
            Log.d("QuizCategory5","firebase data get" )
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("QuizCategory5","on data change for started"+dataSnapshot.children.toString())
                    val option1Text=dataSnapshot.child("option1").getValue().toString()
                    val option2Text=dataSnapshot.child("option2").getValue().toString()
                    val option3Text=dataSnapshot.child("option3").getValue().toString()
                    val option4Text=dataSnapshot.child("option4").getValue().toString()
                    val questionText=dataSnapshot.child("question").getValue().toString()
                    val quesNo=total.toString()
                    textView5.setText(quesNo+". "+questionText)
                    radioButton51.setText(option1Text)
                    radioButton52.setText(option2Text)
                    radioButton53.setText(option3Text)
                    radioButton54.setText(option4Text)

                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("QuizCategory5","Something Went Wrong!!")
                }
            })
        }
    }


    fun checkCorrectOrWrongForQues(){
        ref = databaseRef.child("Technology").child(qlist.get(iterator))
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory5", "Something went Wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val correctAns = dataSnapshot.child("correct").getValue().toString()
                if (radioButton51.isChecked ) {
                    if (radioButton51.text.toString().equals(correctAns)){
                        correct++
                        score++
                        radioButton51.setBackgroundColor(Color.GREEN)
                        radioButton51.isChecked=false
                    } else {
                        wrong=total-correct
                        radioButton51.setBackgroundColor(Color.RED)
                        radioButton51.isChecked=false
                        Toast.makeText(this@QuizCategory5,"Correct Ans:"+correctAns,Toast.LENGTH_SHORT).show()

                    }
                }

                if (radioButton52.isChecked) {
                    if (radioButton52.text.toString().equals(correctAns)) {
                        correct++
                        radioButton52.setBackgroundColor(Color.GREEN)
                        score++
                        radioButton52.isChecked=false
                    } else {
                        wrong=total-correct
                        radioButton52.setBackgroundColor(Color.RED)
                        radioButton53.isChecked=false
                        Toast.makeText(this@QuizCategory5,"Correct Ans:"+correctAns,Toast.LENGTH_SHORT).show()

                    }
                }

                if (radioButton53.isChecked ) {
                    if (radioButton53.text.toString().equals(correctAns)) {
                        correct++
                        radioButton53.setBackgroundColor(Color.GREEN)
                        score++
                        radioButton53.isChecked=false
                    }
                    else {
                        wrong=total-correct
                        radioButton53.setBackgroundColor(Color.RED)
                        radioButton53.isChecked=false
                        Toast.makeText(this@QuizCategory5,"Correct Ans:"+correctAns,Toast.LENGTH_SHORT).show()

                    }
                }

                if (radioButton54.isChecked ) {
                    if (radioButton54.text.toString().equals(correctAns)) {
                        correct++
                        radioButton54.setBackgroundColor(Color.GREEN)
                        score++
                        radioButton54.isChecked=false
                    } else {
                        wrong=total-correct
                        radioButton54.setBackgroundColor(Color.RED)
                        radioButton54.isChecked=false
                        Toast.makeText(this@QuizCategory5,"Correct Ans:"+correctAns,Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}