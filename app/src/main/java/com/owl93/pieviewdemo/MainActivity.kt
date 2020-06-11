package com.owl93.pieviewdemo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.owl93.pieview.PieView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

enum class Page {
    PIE,
    LEGEND,
    COMPONENT
}

class MainViewModel : ViewModel() {
    var page = MutableLiveData(Page.PIE)
}


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView : BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        pie_view.legend = pie_view_legend
        pie_view.components = sampleComponents(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.reset_button -> reset()
            else -> Log.d(TAG, "unkown options menu item click")
        }
        return true
    }


    private fun reset() {
        newCount = 0
//        pie_view.components = sampleComponents(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    companion object {
        const val TAG = "MainActivity"
        private val r = Random()
        var newCount = 1
        private val colors = listOf(
            R.color.purple,
            R.color.dark_purple,
            R.color.deep_purple,
            R.color.teal,
            R.color.dark_teal,
            R.color.pink,
            R.color.lime,
            R.color.light_green,
            R.color.blue
        )

        private fun sampleComponents(context: Context) = mutableListOf(
            PieView.Component("Cat Food", 60f, ContextCompat.getColor(context, R.color.purple)),
            PieView.Component("Rent", 300f, ContextCompat.getColor(context, R.color.blue)),
            PieView.Component("Insurance", 255f, ContextCompat.getColor(context, R.color.teal)),
            PieView.Component("Gas", 100f, ContextCompat.getColor(context, R.color.green))
        )

        private fun addComponents(context: Context, components: List<PieView.Component>) =
            components.toMutableList().apply {
                add(PieView.Component("New ${newCount++}", 50f * r.nextFloat() , ContextCompat.getColor(context, colors.random())))
            }

        private fun removeComponents(components: List<PieView.Component>): List<PieView.Component> {
            return components.toMutableList().apply { removeAt(Random().nextInt(components.size)) }
        }

        private fun swapComponents(context: Context) = sampleComponents(context).toMutableList().also {
            it[2] = PieView.Component("replace1", 29f, ContextCompat.getColor(context, R.color.lime))
            it[3] = PieView.Component("replace2", 59f, ContextCompat.getColor(context, R.color.light_green))
        }

        private fun shiftSampleComponents(old: List<PieView.Component>): List<PieView.Component> {
            return  old.map { it.copy(value = it.value * r.nextFloat()) }
        }

    }
}