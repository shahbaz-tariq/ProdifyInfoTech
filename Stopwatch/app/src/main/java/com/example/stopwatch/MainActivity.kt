package com.example.stopwatch

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.Chronometer

class MainActivity : AppCompatActivity() {

    private var pulseAnimation: ValueAnimator? = null
    private lateinit var chronometer: Chronometer
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var resetButton: Button

    private var isRunning = false
    private var elapsedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chronometer = findViewById(R.id.chronometer)
        startButton = findViewById(R.id.startButton)
        stopButton = findViewById(R.id.stopButton)
        resetButton = findViewById(R.id.resetButton)

        startButton.setOnClickListener { startChronometer(it) }
        stopButton.setOnClickListener { stopChronometer(it) }
        resetButton.setOnClickListener { resetChronometer(it) }


    }

    fun startChronometer(view: View) {
        if (!isRunning) {
            chronometer.base = SystemClock.elapsedRealtime() - elapsedTime
            chronometer.start()
            isRunning = true
            // Start the pulsating animation
            pulseAnimation = ValueAnimator.ofFloat(0.5f, 1.0f)
            pulseAnimation?.duration = 500L // Adjust duration as needed
            pulseAnimation?.repeatMode = ValueAnimator.REVERSE
            pulseAnimation?.repeatCount = ValueAnimator.INFINITE
            pulseAnimation?.addUpdateListener { animation ->
                chronometer.alpha = animation.animatedValue as Float
            }
            pulseAnimation?.start()
        }
    }

    fun stopChronometer(view: View) {
        if (isRunning) {
            chronometer.stop()
            elapsedTime = SystemClock.elapsedRealtime() - chronometer.base
            isRunning = false
            // Stop the pulsating animation
            pulseAnimation?.cancel()
            pulseAnimation = null // Clear the reference
        }
    }

    fun resetChronometer(view: View) {
        chronometer.base = SystemClock.elapsedRealtime()
        elapsedTime = 0
        if (isRunning) {
            chronometer.stop()
            isRunning = false
            pulseAnimation?.cancel()
            pulseAnimation = null // Clear the reference
        }
    }
}