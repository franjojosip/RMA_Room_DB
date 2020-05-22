package ht.ferit.fjjukic.roomapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ht.ferit.fjjukic.roomapplication.models.InspiringPerson

@Database(entities = [InspiringPerson::class], version = 1, exportSchema = false)
abstract class InspiringPeopleDatabase : RoomDatabase() {

    abstract fun inspiringPeopleDao(): InspiringPeopleDao

    companion object {
        private var INSTANCE: InspiringPeopleDatabase? = null
        private const val NAME = "inspiring_people_db"

        fun getDatabase(
            context: Context
        ): InspiringPeopleDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    InspiringPeopleDatabase::class.java,
                    NAME
                )
                    .build()
                    .also{ INSTANCE = it}
            }
        }
    }
}