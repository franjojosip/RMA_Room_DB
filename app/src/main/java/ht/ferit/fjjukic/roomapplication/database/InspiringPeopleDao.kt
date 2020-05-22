package ht.ferit.fjjukic.roomapplication.database

import androidx.room.*
import ht.ferit.fjjukic.roomapplication.models.InspiringPerson

@Dao
interface InspiringPeopleDao {
    @Insert
    suspend fun insert(person: InspiringPerson)

    @Query("SELECT * FROM inspiring_person ORDER BY id ASC")
    suspend fun getAll(): MutableList<InspiringPerson>

    @Query("SELECT * FROM inspiring_person WHERE id = :id")
    suspend fun get(id: Int): InspiringPerson

    @Update
    suspend fun update(person: InspiringPerson)

    @Delete
    suspend fun delete(person: InspiringPerson)
}