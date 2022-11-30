package com.example.shiftstestapplication.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shiftstestapplication.data.db.entities.ShiftItems
import kotlinx.coroutines.flow.Flow

@Dao
interface ShiftsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(item: ShiftItems)

    @Delete
    fun delete(item: ShiftItems)

    @Query("SELECT * FROM ShiftItems")
    fun getAllShiftItems(): List<ShiftItems>

}