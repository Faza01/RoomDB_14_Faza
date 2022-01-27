package com.crud.roomdb_14_faza

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.SyncStateContract
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.crud.roomdb_14_faza.room.Constant
import com.crud.roomdb_14_faza.room.Movie
import com.crud.roomdb_14_faza.room.MovieDb
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_movie.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    val db by lazy { MovieDb(this) }
    lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            movieAdapter.setData(db.movieDao().getMovies())
            withContext(Dispatchers.Main) {
                movieAdapter.notifyDataSetChanged()
            }
        }
    }

    fun setupListener(){
        add_movie.setOnClickListener{
            intentCreate(Constant.TYPE_CREATE,0)
        }

    }

    private fun intentEdit(intent_type: Int, movie_id: Int) {
        startActivity(
            Intent(this, EditActivity::class.java)
                .putExtra("intent_type", intent_type)
                .putExtra("movie_id", movie_id)
        )

    }

    private fun intentCreate(intent_type: Int, movie_id: Int) {
        startActivity(
            Intent(this, AddActivity::class.java)
                .putExtra("intent_type", intent_type)
                .putExtra("movie_id", movie_id)
        )

    }

    private fun setupRecyclerView () {

        movieAdapter = MovieAdapter(
            arrayListOf(),
            object : MovieAdapter.OnAdapterListener {
                override fun onClick(movie: Movie) {
                    intentEdit(Constant.TYPE_READ, movie.id)
                }

                override fun onUpdate(movie: Movie) {
                    intentEdit(Constant.TYPE_UPDATE, movie.id)
                }

                override fun onDelete(movie: Movie) {
                    deleteAlert(movie)
                }

            })

        rv_movie.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = movieAdapter
        }

    }

    private fun deleteAlert(movie: Movie){
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Konfirmasi Hapus")
            setMessage("Yakin hapus ${movie.title}?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.movieDao().deleteMovie(movie)
                    dialogInterface.dismiss()
                    loadData()
                }
            }
        }

        dialog.show()
    }
}