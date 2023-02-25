package ru.mazdorov.geomain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"

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

    private var answers = mutableListOf<Answer>()

    private lateinit var AnswerButtonsShown:Array<Boolean>

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        previewButton = findViewById(R.id.preview_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
            hideAnswerButtons(currentIndex)
        }

        falseButton.setOnClickListener{ view: View ->
            checkAnswer(false)
            hideAnswerButtons(currentIndex)
        }
        nextButton.setOnClickListener{
            nextAction()
        }
        previewButton.setOnClickListener{
            previewAction()
        }
        questionTextView.setOnClickListener{view: View ->
            nextAction()
        }
        AnswerButtonsShown = Array(questionBank.size) { i -> true }

        updateQuestion()
    }

    private fun hideAnswerButtons(index:Int) {
        AnswerButtonsShown.set(index, false)
        setEnabledAnswerButtons(index)
    }
    private fun setEnabledAnswerButtons(index:Int){
        trueButton.setEnabled(AnswerButtonsShown[index])
        falseButton.setEnabled(AnswerButtonsShown[index])
    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun nextAction() {
        currentIndex = (currentIndex + 1) % questionBank.size
        updateQuestion()
    }

    private fun previewAction() {
        currentIndex = if (currentIndex == 0)
            questionBank.size - 1
        else
            currentIndex - 1
        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
        setEnabledAnswerButtons(currentIndex)
    }

    private fun checkAnswer(userAnswer: Boolean){
        val isAnswerCorrect: Boolean =  isAnswerCorrect(userAnswer)
        rememberAnswer(currentIndex, isAnswerCorrect)
        showAnswer(isAnswerCorrect)
    }

    private fun showAnswer(answerCorrect: Boolean) {
        val messageResId = if (answerCorrect){
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

        if(questionBank.count() == answers.count())
        {
            showPercent()
        }
    }

    private fun isAnswerCorrect(userAnswer:Boolean):Boolean {
        val currentAnswer = questionBank[currentIndex].answer
        return userAnswer == currentAnswer
    }

    private fun rememberAnswer(QuestionIndex: Int, isAnswerCorrect: Boolean) {
        if(questionBank.count() > answers.count())
        {
            answers.add(Answer(QuestionIndex, isAnswerCorrect))
        }
    }

    private fun showPercent() {
        val correctAnswersCount = answers.filter { answer -> answer.CorrectAnswer }.size
        val correctAnswersPercent: Double = (100.toDouble() / answers.size.toDouble()) * correctAnswersCount.toDouble()
        val message = String.format( "Correct answers %d / %d  %.2f percent ", correctAnswersCount, answers.size, correctAnswersPercent )

        val toast = android.widget.Toast.makeText(
            this,
            message,
            Toast.LENGTH_LONG
        )
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()
    }
}