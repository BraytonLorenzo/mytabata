package com.example.mytabata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mytabata.ui.theme.MytabataTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MytabataTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TabataCounter(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TabataCounter(modifier: Modifier = Modifier) {
    var timeLeft by remember { mutableStateOf(0L) }
    var isWorkTime by remember { mutableStateOf(true) }
    var isRunning by remember { mutableStateOf(false) }

    val counterDown = remember {
        CounterDown(
            workTime = 20, // Tiempo de trabajo en segundos
            restTime = 10, // Tiempo de descanso en segundos
            numSeries = 8, // Número de series
            onTick = { time, work ->
                timeLeft = time
                isWorkTime = work
            },
            onFinish = {
                isRunning = false
            }
        )
    }

    Column {
        Text(
            text = if (isRunning) {
                if (isWorkTime) "Trabajo: $timeLeft" else "Descanso: $timeLeft"
            } else {
                "Presiona para iniciar"
            },
            modifier = modifier
        )
        Button(onClick = {
            if (isRunning) {
                counterDown.pause()
                isRunning = false
            } else {
                counterDown.start()
                isRunning = true
            }
        }) {
            Text(text = if (isRunning) "Pausar" else "Iniciar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MytabataTheme {
        TabataCounter()
    }
}