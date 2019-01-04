package ru.barabo.learn.groupie.groupietest

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val prefs = Prefs[this]

        val showBoundsContainer = findViewById(R.id.show_bounds) as ViewGroup
        val showBoundsSwitch = showBoundsContainer.findViewById(R.id.the_switch) as SwitchCompat
        val showBoundsText = showBoundsContainer.findViewById<View>(R.id.text) as TextView

        val showOffsetsContainer = findViewById(R.id.show_offsets) as ViewGroup
        val showOffsetsSwitch = showOffsetsContainer.findViewById(R.id.the_switch) as SwitchCompat
        val showOffsetsText = showOffsetsContainer.findViewById<View>(R.id.text) as TextView

        showBoundsText.setText(R.string.show_bounds)
        showOffsetsText.setText(R.string.show_offsets)
        showBoundsSwitch.isChecked = prefs!!.getShowBounds()
        showOffsetsSwitch.isChecked = prefs.getShowOffsets()

        showBoundsSwitch.setOnCheckedChangeListener { _, showBounds -> prefs.setShowBounds(showBounds) }

        showOffsetsSwitch.setOnCheckedChangeListener { _, showOffsets -> prefs.setShowOffsets(showOffsets) }
    }
}