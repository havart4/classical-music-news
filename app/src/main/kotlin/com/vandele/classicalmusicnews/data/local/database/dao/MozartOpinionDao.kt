package com.vandele.classicalmusicnews.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vandele.classicalmusicnews.data.local.database.entity.MOZART_OPINION_TABLE_NAME
import com.vandele.classicalmusicnews.data.local.database.entity.MozartOpinionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MozartOpinionDao {
    @Query("SELECT * FROM $MOZART_OPINION_TABLE_NAME WHERE articleLink = :articleLink")
    fun getMozartOpinion(articleLink: String): Flow<MozartOpinionEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMozartOpinion(mozartOpinion: MozartOpinionEntity)

    @Query("DELETE FROM $MOZART_OPINION_TABLE_NAME WHERE articleLink = :articleLink")
    suspend fun deleteMozartOpinion(articleLink: String)
}
