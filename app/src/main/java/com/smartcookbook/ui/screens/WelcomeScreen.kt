package com.smartcookbook.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.smartcookbook.ui.theme.*

@Composable
fun WelcomeScreen(onContinue: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "welcome_pulse")
    val iconScale by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ), label = "icon_scale"
    )

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    val alphaAnim by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(700), label = "alpha"
    )
    val slideAnim by animateDpAsState(
        targetValue = if (visible) 0.dp else 32.dp,
        animationSpec = tween(600, easing = EaseOut), label = "slide"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        contentAlignment = Alignment.Center
    ) {
        // Decorative background blob
        Box(
            modifier = Modifier
                .size(340.dp)
                .align(Alignment.TopEnd)
                .offset(x = 80.dp, y = (-80).dp)
                .background(
                    brush = Brush.radialGradient(listOf(Orange100, White)),
                    shape = RoundedCornerShape(50)
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon
            Surface(
                modifier = Modifier
                    .size(112.dp)
                    .offset(y = -slideAnim),
                shape = RoundedCornerShape(40.dp),
                color = Orange50,
                shadowElevation = 4.dp
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Icon(
                        imageVector = Icons.Filled.Restaurant,
                        contentDescription = "Chef hat",
                        tint = Orange500,
                        modifier = Modifier
                            .size(56.dp)
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            Text(
                text = "Smart Cookbook",
                style = MaterialTheme.typography.displaySmall,
                color = Gray900,
                modifier = Modifier.offset(y = -slideAnim)
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Your personal culinary assistant to discover, cook, and organize delicious recipes.",
                style = MaterialTheme.typography.bodyMedium,
                color = Gray500,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .widthIn(max = 280.dp)
                    .offset(y = -slideAnim)
            )

            Spacer(Modifier.height(56.dp))

            // Google button
            OutlinedButton(
                onClick = onContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Gray700)
            ) {
                // Google G colours as text indicator
                Text("G", color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleMedium)
                Text("o", color = Amber400, style = MaterialTheme.typography.titleMedium)
                Text("o", color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleMedium)
                Text("g", color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium)
                Text("l", color = Green500, style = MaterialTheme.typography.titleMedium)
                Text("e  ", color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.width(4.dp))
                Text("Continue with Google",
                    style = MaterialTheme.typography.titleMedium, color = Gray700)
            }
        }

        // Bottom label
        Text(
            text = "SECURE YOUR RECIPES ACROSS ALL DEVICES",
            style = MaterialTheme.typography.labelSmall,
            color = Gray400,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        )
    }
}
