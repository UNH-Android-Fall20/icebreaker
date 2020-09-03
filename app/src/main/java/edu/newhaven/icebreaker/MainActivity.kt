package edu.newhaven.icebreaker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val questionBank = listOf(
        Question(R.string.question_dream_job),
        Question(R.string.question_favorite_book),
        Question(R.string.question_favorite_food),
        Question(R.string.question_favorite_movie),
        Question(R.string.question_favorite_book),
        Question(R.string.question_thrones)
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set the initial question
        txtQuestion.setText(questionBank.random().textResId)

        // setup event to generate a new question on demand
        btnGetNewQuestion.setOnClickListener {
            txtQuestion.setText(questionBank.random().textResId)
        }
    }
}