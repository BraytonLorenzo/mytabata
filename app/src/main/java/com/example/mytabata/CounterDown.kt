package com.example.mytabata

import android.os.CountDownTimer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class CounterDown(
    var workTime: Int,
    var restTime: Int,
    var numSeries: Int,
    var onTick: (Long, Boolean) -> Unit,
    var onFinish: () -> Unit
) {
    private var isRunning = false
    private var currentSeries = 0
    private var isWorkTime = true
    private var timer: CountDownTimer? = null

    fun start() {
        if (!isRunning) {
            isRunning = true
            currentSeries = 1
            isWorkTime = true
            startTimer(workTime)
        }
    }

    fun pause() {
        if (isRunning) {
            isRunning = false
            timer?.cancel()
        }
    }

    private fun startTimer(duration: Int) {
        timer = object : CountDownTimer(duration * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                onTick(millisUntilFinished / 1000, isWorkTime)
            }

            override fun onFinish() {
                if (isWorkTime) {
                    isWorkTime = false
                    startTimer(restTime)
                } else {
                    currentSeries++
                    if (currentSeries <= numSeries) {
                        isWorkTime = true
                        startTimer(workTime)
                    } else {
                        isRunning = false
                        onFinish()
                    }
                }
            }
        }.start()
    }
}