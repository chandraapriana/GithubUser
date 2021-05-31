package com.chandra.github.ui.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.text.Html
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.chandra.github.R
import com.chandra.github.backgroundprocess.AlarmReceiver



@Suppress("DEPRECATION")
class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var reminderKey: String
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var switchPreferenceCompat: SwitchPreferenceCompat
    private lateinit var languagePreference: Preference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        alarmReceiver = AlarmReceiver()

        setupReminder()
        initSharedPreference()
        setLanguage()
        changeColor()


    }

    private fun initSharedPreference() {
        val sharedPreference = preferenceManager.sharedPreferences
        switchPreferenceCompat.isChecked = sharedPreference.getBoolean(reminderKey, false)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun setupReminder() {
        reminderKey = resources.getString(R.string.key_alarm)
        switchPreferenceCompat =
            findPreference<SwitchPreferenceCompat>(reminderKey) as SwitchPreferenceCompat
    }


    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == reminderKey) {
            if (sharedPreferences != null) {
                switchPreferenceCompat.isChecked =
                    sharedPreferences.getBoolean(reminderKey, false)
            }
        }

        val state =
            PreferenceManager.getDefaultSharedPreferences(context).getBoolean(reminderKey, false)
        setReminder(state)
    }

    private fun setReminder(state: Boolean) {
        if (state) {
            context?.let { alarmReceiver.setRepeatingAlarm(it) }
        } else {
            context?.let { alarmReceiver.cancelAlarm(it) }
        }
    }

    private fun setLanguage() {
        languagePreference = findPreference<Preference>("language") as Preference
        languagePreference.setOnPreferenceClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
            true
        }
    }

    private fun changeColor() {
        val labelColor = resources.getColor(R.color.white)
        val сolorString = String.format("%X", labelColor).substring(2)

        switchPreferenceCompat.title = Html.fromHtml(
            String.format(
                "<font color=\"#%s\">${switchPreferenceCompat.title}</font>",
                сolorString
            )
        )
        languagePreference.title = Html.fromHtml(
            String.format(
                "<font color=\"#%s\">${languagePreference.title}</font>",
                сolorString
            )
        )
        val languageSummary: CharSequence = languagePreference.summary.toString() + ' '

        languagePreference.summary = Html.fromHtml(
            "<font color='#C4C4C4'>$languageSummary</font>"
        )
    }


}