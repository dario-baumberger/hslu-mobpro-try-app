package com.example.tryapp.business.music

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.tryapp.data.MusicPlayerService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MusicServiceViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    fun startMusic() {
        val intent = Intent(context, MusicPlayerService::class.java)
        ContextCompat.startForegroundService(context, intent)
        _isPlaying.value = true
    }

    fun stopMusic() {
        val intent = Intent(context, MusicPlayerService::class.java)
        context.stopService(intent)
        _isPlaying.value = false
    }

    fun toggleMusic() {
        if (_isPlaying.value) stopMusic() else startMusic()
    }
}
