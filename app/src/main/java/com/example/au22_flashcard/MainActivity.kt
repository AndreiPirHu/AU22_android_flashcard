package com.example.au22_flashcard

import android.content.ClipData.Item
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    lateinit var wordView : TextView
    lateinit var  addNewWordButton: Button
    var currentWord : Word? = null
    val wordList = WordList()
    lateinit var job: Job
    lateinit var db: AppDatabase

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = AppDatabase.getInstance(this)
        job = Job()


        wordView = findViewById(R.id.wordTextView)
        addNewWordButton = findViewById(R.id.button3)

        showNewWord()

        wordView.setOnClickListener {
            revealTranslation()
        }
        addNewWordButton.setOnClickListener {
            val intent = Intent(this, AddWordActivity::class.java)
            startActivity(intent)
        }





    }

    override fun onResume() {
        super.onResume()
            wordList.clearWordList()

            launch {
                val list = getAllWords()
                val databaseWordList = list.await()

                for(word in databaseWordList){
                    Log.d("!!!","item: $word")
                    wordList.addWordToList(word)
                }
            }

    }


    fun getAllWords(): Deferred<List<Word>> =
        async(Dispatchers.IO){
            db.wordDao.getAll()
        }



    fun revealTranslation() {
        wordView.text = currentWord?.english
    }


    fun showNewWord() {

        currentWord = wordList.getNewWord()
        wordView.text = currentWord?.swedish
    }

    fun loadAllItems() : Deferred<List<Word>> =
        async(Dispatchers.IO){
            db.wordDao.getAll()
        }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event?.action == MotionEvent.ACTION_UP) {
            showNewWord()
        }

        return true
    }


    fun saveWord(word: Word){
        launch(Dispatchers.IO){
            db.wordDao.insert(word)
        }




    }

    fun removeWord(word: Word){
        launch(Dispatchers.IO){
            db.wordDao.delete(word)
        }
    }





}