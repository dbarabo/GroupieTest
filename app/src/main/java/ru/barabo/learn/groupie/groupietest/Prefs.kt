package ru.barabo.learn.groupie.groupietest

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager


class Prefs private constructor(context: Context) {
    private val prefs: SharedPreferences

    private var showColor: Boolean = false
    private var showBounds: Boolean = false
    private var showOffsets: Boolean = false

    init {
        prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext())
        showColor = prefs.getBoolean(KEY_COLOR, false)
        showBounds = prefs.getBoolean(KEY_BOUNDS, false)
        showOffsets = prefs.getBoolean(KEY_OFFSETS, false)
    }

    fun registerListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        prefs.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        prefs.unregisterOnSharedPreferenceChangeListener(listener)
    }

    fun getShowBounds(): Boolean {
        return showBounds
    }

    fun getShowOffsets(): Boolean {
        return showOffsets
    }

    fun getShowColor(): Boolean {
        return showColor
    }

    fun setShowColor(showColor: Boolean) {
        this.showColor = showColor
        prefs.edit().putBoolean(KEY_COLOR, showColor).apply()
    }

    fun setShowOffsets(showOffsets: Boolean) {
        this.showOffsets = showOffsets
        prefs.edit().putBoolean(KEY_OFFSETS, showOffsets).apply()
    }

    fun setShowBounds(showBounds: Boolean) {
        this.showBounds = showBounds
        prefs.edit().putBoolean(KEY_BOUNDS, showBounds).apply()
    }

    companion object {

        private val KEY_COLOR = "KEY_COLOR"
        private val KEY_BOUNDS = "KEY_BOUNDS"
        private val KEY_OFFSETS = "KEY_OFFSETS"

        @Volatile
        private var singleton: Prefs? = null

        operator fun get(context: Context): Prefs? {
            if (singleton == null) {
                synchronized(Prefs::class.java) {
                    singleton = Prefs(context)
                }
            }
            return singleton
        }
    }

}