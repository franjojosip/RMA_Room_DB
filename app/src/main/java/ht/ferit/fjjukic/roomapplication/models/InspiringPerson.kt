package ht.ferit.fjjukic.roomapplication.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inspiring_person")
class InspiringPerson(
    @ColumnInfo(name="birth_date")
    val dateOfBirth: String,
    @ColumnInfo(name="description")
    val shortDescription: String,
    @ColumnInfo(name="quote")
    val quotes: String,
    @ColumnInfo(name="image_path")
    val imagePath: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}