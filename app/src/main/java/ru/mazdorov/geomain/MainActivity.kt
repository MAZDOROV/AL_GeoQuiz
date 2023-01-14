package ru.mazdorov.geomain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var previewButton: ImageButton
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_oceans, true),
        Question(R.string.question_russia, true),
        Question(R.string.question_africa, false),
        Question(R.string.question_mideast, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        previewButton = findViewById(R.id.preview_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }

        falseButton.setOnClickListener{ view: View ->
            checkAnswer(false)
        }
        nextButton.setOnClickListener{
            nextAction()
        }
        previewButton.setOnClickListener{
            currentIndex = if(currentIndex == 0)
                questionBank.size - 1
            else
                currentIndex - 1
            updateQuestion()
        }
        questionTextView.setOnClickListener{view: View ->
            nextAction()
        }

        updateQuestion()
    }

    private fun nextAction() {
        currentIndex = (currentIndex + 1) % questionBank.size
        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        val currentAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == currentAnswer){
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        val toast = Toast.makeText(
            this,
            messageResId,
            Toast.LENGTH_SHORT
        )
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()
    }
}