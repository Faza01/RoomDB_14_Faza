package com.crud.roomdb_14_faza.room

import android.provider.ContactsContract
import androidx.room.*

@Dao
interface MovieDao {

    @Insert
    suspend fun addMovie(movie: Movie)

    @Update
    suspend fun updateMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("SELECT * FROM movie ORDER BY id DESC")
    suspend fun getMovies() : List<Movie>

    @Query("SELECT * FROM movie WHERE id=:movie_id")
    suspend fun getMovie(movie_id: Int) : List<Movie>
}