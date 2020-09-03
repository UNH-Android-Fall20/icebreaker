package edu.newhaven.icebreaker

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = javaClass.name

    private val db = FirebaseFirestore.getInstance()

    private lateinit var questionBank: MutableList<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get the list of questions from Firebase
        db.collection("questions")
            .get()
            .addOnSuccessListener { documents ->
                questionBank = mutableListOf()
                for (document in documents) {
                    val question = document.toObject(Question::class.java)
                    questionBank.add(question)
                    Log.d(TAG, "${document.id} => ${document.data}")
                }

                // set the initial question
                lblQuestion.setText(questionBank.random().text)

                // setup event to generate a new question on demand
                btnGetNewQuestion.setOnClickListener {
                    lblQuestion.setText(questionBank.random().text)
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
                            Log.d(
                                TAG,
                                "DocumentSnapshot written with ID: ${documentReference.id}"
                            )
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }
}