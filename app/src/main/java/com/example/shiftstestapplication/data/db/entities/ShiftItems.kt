package com.example.shiftstestapplication.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
open class ShiftItems(
    var name: String,
    var role: String,
    var startDate: String,
    var endDate: String,
    var color: String,
){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}

