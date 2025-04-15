package com.example.tryapp.business.bands

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tryapp.domain.band.BandCode
import com.example.tryapp.domain.band.BandInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BandsViewModel @Inject constructor(
    private val repository: BandsRepositoryImpl
) : ViewModel() {

    private val _bandsFlow: MutableStateFlow<List<BandCode>> = MutableStateFlow(emptyList())
    val bandsFlow: StateFlow<List<BandCode>> = _bandsFlow

    private val _currentBand: MutableSharedFlow<BandInfo?> = MutableSharedFlow()
    val currentBand: Flow<BandInfo?> = _currentBand

    private val _loadError: MutableStateFlow<String?> = MutableStateFlow(null)
    val loadError: StateFlow<String?> = _loadError

    init {
        requestBandCodesFromServer()
    }

    fun requestBandCodesFromServer() {
        viewModelScope.launch {
            try {
                val result = repository.getBands()
                _bandsFlow.value = result
                _loadError.value = null
            } catch (e: Exception) {
                Log.e("BandsViewModel", "Failed to fetch bands", e)
                _loadError.value = e.message ?: "Unknown error"
            }
        }
    }

    fun requestBandInfoFromServer(code: String) {
        viewModelScope.launch {
            try {
                val band = repository.getBandInfo(code)
                _currentBand.emit(band)
            } catch (e: Exception) {
                Log.e("BandsViewModel", "Failed to fetch band info for $code", e)
            }
        }
    }
}
