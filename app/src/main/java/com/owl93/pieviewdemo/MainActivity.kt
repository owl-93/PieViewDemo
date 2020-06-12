package com.owl93.pieviewdemo

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.owl93.pieview.PieView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainViewModel : ViewModel() {
    //Pie Options
    val startingAngle = MutableLiveData(0)
    val strokeWidth= MutableLiveData(20f)
    val strokeEnd = MutableLiveData(Paint.Cap.BUTT)
    val dividersEnabled = MutableLiveData(true)
    val dividerSize = MutableLiveData(0)
    val drawTrack = MutableLiveData(false)
    val trackWidth = MutableLiveData(22f)
    val trackAlpha = MutableLiveData(100)
    val trackColor = MutableLiveData(Color.WHITE)
    //Legend Options

}


class MainActivity : AppCompatActivity() {
    private val vm: MainViewModel by viewModels()
    private var defStartingAngle = 0f
    private var defStrokeWidth= 0f
    private var defStrokeEnd = Paint.Cap.BUTT
    private var defDividersEnabled = true
    private var defDividerSize = 0
    private var defDrawTrack = false
    private var defTrackWidth = 0f
    private var defTrackAlpha = 0
    private var defTrackColor = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView : BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        pie_view.legend = pie_view_legend
        pie_view.components = sampleComponents(this)

        initDefaultValues()
        resetViewModel()
        observe()
    }
    
    private fun initDefaultValues() {
        defStartingAngle = pie_view.startAngle
        defStrokeWidth = pie_view.strokeWidth
        defStrokeEnd = pie_view.strokeEnd
        defDividersEnabled = pie_view.showDividers
        defDividerSize = pie_view.dividerWidth
        defDrawTrack = pie_view.drawTrack
        defTrackWidth = pie_view.trackWidth
        defTrackAlpha = pie_view.trackAlpha
        defTrackColor = pie_view.trackColor
    }

    private fun resetViewModel() {
        //Pie Options
        vm.startingAngle.value = defStartingAngle.toInt()
        vm.strokeWidth.value = defStrokeWidth
        vm.strokeEnd.value = defStrokeEnd
        vm.dividersEnabled.value = defDividersEnabled
        vm.dividerSize.value = defDividerSize
        vm.drawTrack.value = defDrawTrack
        vm.trackWidth.value = defTrackWidth
        vm.trackAlpha.value = defTrackAlpha
        vm.trackColor.value = defTrackColor
        //Legend Options

    }


    private fun observe() {
        vm.startingAngle.observe(this, Observer { pie_view.startAngle = it.toFloat() })
        vm.strokeWidth.observe(this, Observer { pie_view.strokeWidth = it })
        vm.strokeEnd.observe(this, Observer { pie_view.strokeEnd = it })
        vm.dividersEnabled.observe(this, Observer { pie_view.showDividers = it })
        vm.dividerSize.observe(this, Observer { pie_view.dividerWidth = it })
        vm.drawTrack.observe(this, Observer { pie_view.drawTrack = it })
        vm.trackWidth.observe(this, Observer { pie_view.trackWidth = it })
        vm.trackAlpha.observe(this, Observer { pie_view.trackAlpha = it })
        vm.trackColor.observe(this, Observer { pie_view.trackColor = it })
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
        pie_view.components = sampleComponents(this)
        resetViewModel()
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