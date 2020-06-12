package com.owl93.pieviewdemo

import android.content.res.ColorStateList
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.owl93.pieviewdemo.common.ColorPickerDialog
import com.owl93.pieviewdemo.common.SeekbarListener
import com.owl93.pieviewdemo.databinding.FragmentPieOptionsBinding
import kotlinx.android.synthetic.main.fragment_pie_options.*

class PieOptionsFragment : Fragment() {
    private val vm : MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentPieOptionsBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.vm = vm
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStrokeControls()
        initTrackControls()
    }

    private fun initStrokeControls() {
        stroke_width_seekbar.apply {
            //progress = vm.strokeWidth.value?.toInt() ?: 0
            setOnSeekBarChangeListener(SeekbarListener { vm.strokeWidth.value = it.toFloat() })
        }
        draw_spacing_switch.setOnCheckedChangeListener {_, checked ->  vm.dividersEnabled.value = checked }
        spacing_size_seekbar.setOnSeekBarChangeListener(SeekbarListener { vm.dividerSize.value = it })
        starting_angle_seekbar.setOnSeekBarChangeListener(SeekbarListener {vm.startingAngle.value = it })
        stroke_end_radiogroup.setOnCheckedChangeListener { _, id ->
            vm.strokeEnd.value = when(id) {
                R.id.stroke_end_round -> Paint.Cap.ROUND
                else ->  Paint.Cap.BUTT
            }
        }
        //stroke_end_switch.setOnCheckedChangeListener {_, checked -> vm.strokeEnd.value = if(checked) Paint.Cap.ROUND else Paint.Cap.BUTT }
    }

    private fun initTrackControls() {
        track_width_seekbar.apply {
            //progress = vm.trackWidth.value?.toInt() ?: 0
            setOnSeekBarChangeListener(SeekbarListener { vm.trackWidth.value = it.toFloat() })
        }
        track_alpha_seekbar.apply {
            //progress = vm.trackAlpha.value!!
            setOnSeekBarChangeListener(SeekbarListener { vm.trackAlpha.value = it})
        }
        draw_track_switch.setOnCheckedChangeListener { v, checked ->
            vm.drawTrack.value = checked
        }
        track_color_button.setOnClickListener { showColorPickerDialog(vm.trackColor)}
        vm.trackColor.observe(viewLifecycleOwner, Observer {
            track_color_button.imageTintList = ColorStateList.valueOf(it)
        })
    }

    private fun showColorPickerDialog(field: MutableLiveData<Int>) {
        ColorPickerDialog.newInstance(field, false).show(parentFragmentManager, ColorPickerDialog.TAG)
    }


}

