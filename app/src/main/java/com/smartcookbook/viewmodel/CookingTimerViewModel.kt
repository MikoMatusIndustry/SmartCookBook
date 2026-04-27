package com.smartcookbook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CookingTimerViewModel : ViewModel() {

    private val _selectedMinutes = MutableStateFlow(15)
    val selectedMinutes: StateFlow<Int> = _selectedMinutes

    private val _timeLeftSeconds = MutableStateFlow(15 * 60)
    val timeLeftSeconds: StateFlow<Int> = _timeLeftSeconds

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    private val _isFinished = MutableStateFlow(false)
    val isFinished: StateFlow<Boolean> = _isFinished

    private var timerJob: Job? = null

    fun setMinutes(mins: Int) {
        if (_isRunning.value) return
        _selectedMinutes.value = mins.coerceIn(1, 99)
        _timeLeftSeconds.value = _selectedMinutes.value * 60
        _isFinished.value = false
    }

    fun adjustMinutes(delta: Int) = setMinutes(_selectedMinutes.value + delta)

    fun toggleTimer() {
        if (_isFinished.value) return
        if (_isRunning.value) {
            timerJob?.cancel()
            _isRunning.value = false
        } else {
            _isRunning.value = true
            timerJob = viewModelScope.launch {
                while (_timeLeftSeconds.value > 0) {
                    delay(1000L)
                    _timeLeftSeconds.value -= 1
                }
                _isRunning.value = false
                _isFinished.value = true
            }
        }
    }

    fun reset() {
        timerJob?.cancel()
        _isRunning.value = false
        _isFinished.value = false
        _timeLeftSeconds.value = _selectedMinutes.value * 60
    }
}
