package com.example.au22_flashcard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class AddWordActivity : AppCompatActivity(), CoroutineScope {

    lateinit var addNewWordSwedishTextView: EditText
    lateinit var addNewWordEnglishTextView: EditText
    lateinit var addNewWordToDatabaseButton: Button
    lateinit var goBackButton: Button
    lateinit var job: Job
    lateinit var db: AppDatabase

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)
        job = Job()
        db = AppDatabase.getInstance(this)

        addNewWordSwedishTextView = findViewById(R.id.addNewWordSwedishTextView)
        addNewWordEnglishTextView = findViewById(R.id.addNewWordEnglishTextView)
        addNewWordToDatabaseButton = findViewById(R.id.addNewWordToDatabaseButton)
        goBackButton = findViewById(R.id.goBackButton)

        goBackButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        addNewWordToDatabaseButton.setOnClickListener {
                addNewWord()


        }

    }

    fun addNewWord(){
        if(addNewWordSwedishTextView.text.isEmpty() || addNewWordEnglishTextView.text.isEmpty()){
            Toast.makeText(this, "One of the necessary fields are empty", Toast.LENGTH_SHORT).show()
        } else {
            var newSwedishWord =  addNewWordSwedishTextView.text.toString()
            var newEnglishWord = addNewWordEnglishTextView.text.toString()

            var addWordToDatabase = Word(newSwedishWord,newEnglishWord)

            db.wordDao.insert(addWordToDatabase)

            addNewWordSwedishTextView.text.clear()
            addNewWordEnglishTextView.text.clear()
        }

    }

}