package com.example.rustorecourse.data.source.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rustorecourse.data.source.local.entity.AppEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDetailsDao {
    @Query("SELECT * FROM app_details WHERE id = :id")
    fun getAppDetails(id: String): Flow<AppEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAppDetails(appDetails: AppEntity)

    @Query("UPDATE app_details SET isInWishList = :isInWishList WHERE id = :id")
    suspend fun updateWishListStatus(id: String, isInWishList: Boolean)

    @Query("SELECT isInWishList FROM app_details where id = :id")
    suspend fun receiveWishList(id: String): Boolean
}