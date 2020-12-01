package com.example.hesproject
/*
Daniel Harrington
HES Android Audition
October 30, 2020
 */

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.example.hesproject.ui.FavoritesFragment
import com.example.hesproject.ui.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val favoritesFragment = FavoritesFragment()
    lateinit var menuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun initComponents(savedInstanceState: Bundle?) {
        supportActionBar!!.title = "Things"
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, SearchFragment())
                .commit()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_overflow, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        menuItem = item
        if(menuItem.title == "Favorites") {
            Log.d("onOptionsItemSelected", "Favorites item")
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, FavoritesFragment()).commit()
            menuItem.title = "Search"
        } else {
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, SearchFragment()).commit()
            menuItem.title = "Favorites"
        }

        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (favoritesFragment.isResumed) {
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, SearchFragment()).commit()
            menuItem.title = "Favorites"
        }
    }
}