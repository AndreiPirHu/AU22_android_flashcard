package com.example.au22_flashcard

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="word_table")
data class Word(@ColumnInfo(name = "english") var english : String,
                @ColumnInfo(name = "swedish") var swedish : String,
                @PrimaryKey(autoGenerate = true) val id: Int? = null) {
}