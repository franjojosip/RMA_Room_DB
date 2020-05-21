package ht.ferit.fjjukic.roomapplication.database

import androidx.room.*
import ht.ferit.fjjukic.roomapplication.models.InspiringPerson

@Dao
interface InspiringPeopleDao {
    @Insert
    fun insert(person: InspiringPerson)

    @Query("SELECT * FROM inspiring_person ORDER BY id ASC")
    fun getAll(): MutableList<InspiringPerson>

    @Query("SELECT * FROM inspiring_person WHERE id = :id")
    fun get(id: Int): InspiringPerson

    @Update
    fun update(person: InspiringPerson)

    @Delete
    fun delete(person: InspiringPerson)
}