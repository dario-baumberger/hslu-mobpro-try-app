package com.example.tryapp.business.music

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import com.example.tryapp.ui.components.MusicPlayerApi

class MusicPlayerConnection : ServiceConnection {
    private var musicPlayerApi: MusicPlayerApi? = null

    override fun onServiceConnected(name: ComponentName, service: IBinder) {
        musicPlayerApi = service as MusicPlayerApi
    }

    override fun onServiceDisconnected(name: ComponentName) {
        musicPlayerApi = null
    }

    fun getMusicPlayerApi(): MusicPlayerApi? {
        return musicPlayerApi
    }

}