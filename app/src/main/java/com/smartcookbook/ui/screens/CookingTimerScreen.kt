package com.smartcookbook.ui.screens

import android.media.AudioAttributes
import android.media.ToneGenerator
import android.media.AudioManager
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartcookbook.ui.theme.*
import com.smartcookbook.viewmodel.CookingTimerViewModel
import androidx.compose.ui.graphics.graphicsLayer

val TIMER_PRESETS = listOf(5, 10, 15, 20, 30)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CookingTimerScreen(
    vm: CookingTimerViewModel,
    onBack: () -> Unit
) {
    val selectedMinutes by vm.selectedMinutes.collectAsState()
    val timeLeft by vm.timeLeftSeconds.collectAsState()
    val isRunning by vm.isRunning.collectAsState()
    val isFinished by vm.isFinished.collectAsState()

    // Play beep when timer finishes
    LaunchedEffect(isFinished) {
        if (isFinished) {
            try {
                val toneGen = ToneGenerator(AudioManager.STREAM_ALARM, 80)
                toneGen.startTone(ToneGenerator.TONE_PROP_BEEP2, 1500)
                kotlinx.coroutines.delay(1600)
                toneGen.release()
            } catch (e: Exception) { /* ignore if audio unavailable */ }
        }
    }

    val totalSeconds = selectedMinutes * 60
    val progress = if (totalSeconds > 0) timeLeft.toFloat() / totalSeconds else 0f
    val minutes = timeLeft / 60
    val seconds = timeLeft % 60

    // Pulse animation when finished
    val infiniteTransition = rememberInfiniteTransition(label = "done_pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 0.4f,
        animationSpec = infiniteRepeatable(tween(700), RepeatMode.Reverse),
        label = "pulse"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cooking Timer", style = MaterialTheme.typography.headlineSmall, color = Gray900) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, "Back", tint = Gray900)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = White)
            )
        },
        containerColor = White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Preset chips
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TIMER_PRESETS.forEach { mins ->
                    FilterChip(
                        selected = selectedMinutes == mins && !isRunning,
                        onClick = { vm.setMinutes(mins) },
                        label = { Text("${mins}m") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Orange500,
                            selectedLabelColor = White,
                            containerColor = Gray100,
                            labelColor = Gray600
                        )
                    )
                }
            }

            // Circular progress
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(260.dp)) {
                CircularProgressIndicator(
                    progress = { 1f },
                    modifier = Modifier.fillMaxSize(),
                    color = if (isFinished) Red100 else Orange100,
                    strokeWidth = 14.dp,
                    strokeCap = StrokeCap.Round
                )
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxSize(),
                    color = if (isFinished) Red500 else Orange500,
                    strokeWidth = 14.dp,
                    strokeCap = StrokeCap.Round
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (isFinished) {
                        Text(
                            "Done! 🎉",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Red500,
                            modifier = Modifier.graphicsLayer { alpha = pulseAlpha }
                        )
                    } else {
                        Text(
                            text = "%02d:%02d".format(minutes, seconds),
                            fontSize = 56.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Light,
                            color = Gray900
                        )
                        Text(
                            text = when {
                                isRunning  -> "cooking..."
                                isFinished -> "time's up!"
                                else       -> "ready"
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = Gray400
                        )
                    }
                }
            }

            // Manual minute adjust
            if (!isRunning && !isFinished) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { vm.adjustMinutes(-1) },
                        modifier = Modifier.size(40.dp).background(Gray100, CircleShape)
                    ) { Icon(Icons.Filled.Remove, null, tint = Gray600) }
                    Text("$selectedMinutes min",
                        style = MaterialTheme.typography.titleMedium, color = Gray700)
                    IconButton(
                        onClick = { vm.adjustMinutes(1) },
                        modifier = Modifier.size(40.dp).background(Gray100, CircleShape)
                    ) { Icon(Icons.Filled.Add, null, tint = Gray600) }
                }
            }

            // Control buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Reset
                IconButton(
                    onClick = { vm.reset() },
                    modifier = Modifier
                        .size(64.dp)
                        .background(Gray100, CircleShape)
                ) {
                    Icon(Icons.Filled.Refresh, "Reset", tint = Gray600,
                        modifier = Modifier.size(28.dp))
                }

                // Play / Pause
                IconButton(
                    onClick = { vm.toggleTimer() },
                    enabled = !isFinished,
                    modifier = Modifier
                        .size(96.dp)
                        .background(
                            color = if (isRunning) Red500 else Orange500,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = if (isRunning) Icons.Filled.Stop else Icons.Filled.PlayArrow,
                        contentDescription = if (isRunning) "Pause" else "Play",
                        tint = White,
                        modifier = Modifier.size(44.dp)
                    )
                }
            }
        }
    }
}

private val Gray600 = androidx.compose.ui.graphics.Color(0xFF4B5563)


