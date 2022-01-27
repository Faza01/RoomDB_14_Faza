package com.crud.roomdb_14_faza

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.crud.roomdb_14_faza.room.Constant
import com.crud.roomdb_14_faza.room.Movie
import com.crud.roomdb_14_faza.room.MovieDb
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {
    val db by lazy { MovieDb(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setupView()
        setupListener()
    }

    private fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        when (intentType()) {
            Constant.TYPE_CREATE -> {
                supportActionBar!!.title = "BUAT BARU"
                btn_save.visibility = View.VISIBLE
            }
        }
    }

    fun setupListener(){
        btn_save.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch{
                db.movieDao().addMovie(
                    Movie(0, et_title.text.toString(),
                        et_description.text.toString())
                )

                finish()
            }
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