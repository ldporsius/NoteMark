package nl.codingwithlinda.persistence_room.database

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase

class DataAccess(
   private val application: Application
){
    val db = Room.databaseBuilder(
        application,
        NoteDatabase::class.java,
        "note_db"
    ).build()

}