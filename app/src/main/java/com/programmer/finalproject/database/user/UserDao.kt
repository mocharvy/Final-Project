package com.programmer.finalproject.database.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userDetail: User)

    @Query("SELECT * FROM user_table2 WHERE id= :id")
    fun readUserDetail(id: String): Flow<List<User>>
}