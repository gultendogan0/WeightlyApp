package com.gultendogan.weightlyapp.ui

import android.app.Application
import com.gultendogan.weightlyapp.BuildConfig
import com.orhanobut.hawk.Hawk
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.google.GoogleEmojiProvider
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application(){

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupHawk()
        setupEmojiManager()
    }

    private fun setupEmojiManager(){
        EmojiManager.install(GoogleEmojiProvider());
    }

    private fun setupHawk(){
        Hawk.init(this).build()
    }


    private fun setupTimber(){
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}