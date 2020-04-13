package com.example.naada.view

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.example.naada.R
import kotlinx.android.synthetic.main.activity_night_mode.*
import java.lang.Exception

class NightMode : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_night_mode)
        val appSettingPrefs: SharedPreferences = getSharedPreferences("AppSettingPrefs", 0)
        val sharedPrefsEdit: SharedPreferences.Editor = appSettingPrefs.edit()
        val isNightModeOn: Boolean = appSettingPrefs.getBoolean("NightMode", false)
        if(isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            try {
                switch_btn.text = "Disable Dark Mode"
            }catch (e:Exception){}
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            try {
                switch_btn.text = "Enable Dark Mode"
            }catch (e:Exception){

            }
        }
        try {
            switch_btn.setOnClickListener(View.OnClickListener {
                if (isNightModeOn) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    sharedPrefsEdit.putBoolean("NightMode", false)
                    sharedPrefsEdit.apply()
                    try {
                        switch_btn.text = "Enable Dark Mode"
                    } catch (e: Exception) {
                    }
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    sharedPrefsEdit.putBoolean("NightMode", true)
                    sharedPrefsEdit.apply()
                    try {
                        switch_btn.text = "Disable Dark Mode"
                    } catch (e: Exception) {
                    }
                }
            })
        }catch (e:Exception){}
    }
}
