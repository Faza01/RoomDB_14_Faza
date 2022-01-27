package com.crud.roomdb_14_faza

import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.crud.roomdb_14_faza.room.Constant
import com.crud.roomdb_14_faza.room.Movie
import com.crud.roomdb_14_faza.room.MovieDb
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    val db by lazy { MovieDb(this) }
    private var movieId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupView()
        setupListener()
    }

    private fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        when (intentType()) {
            Constant.TYPE_READ -> {
                supportActionBar!!.title = "BACA"
                btn_update.visibility = View.GONE
                getMovie()
            }
            Constant.TYPE_UPDATE -> {
                supportActionBar!!.title = "EDIT"
                btn_update.visibility = View.VISIBLE
                getMovie()
            }
        }
    }

    private fun setupListener(){
        btn_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.movieDao().updateMovie(
                    Movie(movieId, edit_title.text.toString(),
                        edit_description.text.toString())
                )
                finish()
            }
        }
    }

    private fun getMovie(){
        movieId = intent.getIntExtra("movie_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val movies = db.movieDao().getMovie(movieId).get(0)
            edit_title.setText( movies.title )
            edit_description.setText( movies.desc )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun intentType(): Int {
        return intent.getIntExtra("intent_type", 0)
    }

}