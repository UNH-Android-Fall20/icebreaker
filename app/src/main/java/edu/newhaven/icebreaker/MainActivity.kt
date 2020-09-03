package edu.newhaven.icebreaker

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = javaClass.name

    private val db = FirebaseFirestore.getInstance()

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
        lblQuestion.setText(questionBank.random().textResId)

        // setup event to generate a new question on demand
        btnGetNewQuestion.setOnClickListener {
            lblQuestion.setText(questionBank.random().textResId)
        }

        btnSubmit.setOnClickListener {
            // Add a new document with a generated id.
            val student = hashMapOf(
                "firstname" to txtFirstName.text.toString(),
                "lastname" to txtLastName.text.toString(),
                "question" to lblQuestion.text,
                "answer" to txtAnswer.text.toString()
            )

            db.collection("students")
                .add(student)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }
    }
}