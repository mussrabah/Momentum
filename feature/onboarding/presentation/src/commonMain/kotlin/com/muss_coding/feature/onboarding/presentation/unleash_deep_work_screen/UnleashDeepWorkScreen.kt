package com.muss_coding.feature.onboarding.presentation.unleash_deep_work_screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.muss_coding.core.presentation.resource_sharing.ResourceHelper
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

// This is your "Sunrise Coral" color. Adjust as needed.
val SunriseCoral = Color(0xFFF99A8A)

@Composable
fun UnleashDeepWorkScreen(
    modifier: Modifier = Modifier,
    onNextClick: () -> Unit
) {
    // --- State for Animations ---
    val textAlpha = remember { Animatable(0f) }
    var showTimer by remember { mutableStateOf(false) }
    val timerProgress = remember { Animatable(0f) }
    val buttonAlpha = remember { Animatable(0f) }

    // --- Animation Logic ---

    LaunchedEffect(Unit) {
        // 1. Fade in text
        textAlpha.animateTo(1f, tween(800))

        // 2. Wait a beat, then trigger the transform
        kotlinx.coroutines.delay(200)
        showTimer = true
    }

    LaunchedEffect(showTimer) {
        if (showTimer) {
            // Wait for the transform animation (fadeIn + scale) to finish
            kotlinx.coroutines.delay(600)

            // 3. Fill the timer
            timerProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 2000)
            )

            // 4. Reveal the Next button
            buttonAlpha.animateTo(1f, tween(500))
        }
    }

    // --- UI Layout ---
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Headline
        Text(
            text = stringResource(ResourceHelper.string.master_your_focus),
            style = MaterialTheme.typography.displayLarge.copy(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.onBackground
                    )
                )
            ),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp).graphicsLayer { alpha = textAlpha.value }
        )

        // Body
        Text(
            text = stringResource(ResourceHelper.string.focus_timer_desc),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .fillMaxWidth(.9f)
                .padding(horizontal = 16.dp)
                .graphicsLayer { alpha = textAlpha.value }
                .align(Alignment.End),
            textAlign = TextAlign.End
        )

        Spacer(Modifier.height(24.dp))

        // --- Animation Stage ---
        Box(
            modifier = Modifier
                .weight(1f) // Takes up available space
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                targetState = showTimer,
                label = "TimerTransformer",
                transitionSpec = {
                    // Adding scaleIn makes the timer 'pop' out of the card
                    (fadeIn(tween(600)) + scaleIn(initialScale = 0.8f, animationSpec = tween(600)))
                        .togetherWith(fadeOut(tween(400)))
                        .using(
                            // Smoothly animate the size change
                            SizeTransform(
                                clip = false,
                                sizeAnimationSpec = { _, _ -> spring(stiffness = Spring.StiffnessLow) }
                            )
                        )
                }
            ) { isTimerViewVisible ->
                if (isTimerViewVisible) {
                    TimerView(progress = timerProgress.value)
                } else {
                    // VISUAL ANCHOR: This simple card exists just to be morphed FROM.
                    // It is not clickable, it just sits there for 200ms to provide a start state.
                    CompactTaskCard()
                }
            }
        }

        // Next Button
        OutlinedButton(
            modifier = Modifier
                .padding(16.dp)
                .graphicsLayer { alpha = buttonAlpha.value }
                .align(Alignment.End),
            onClick = onNextClick,
            enabled = buttonAlpha.value > 0.5f // Prevent accidental clicks before visible
        ) {
            Text(
                text = stringResource(ResourceHelper.string.next),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

/**
 * A smaller, static card that serves as the "Seed" for the animation.
 */
@Composable
fun CompactTaskCard() {
    Surface(
        modifier = Modifier
            .width(200.dp)
            .height(80.dp),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(ResourceHelper.drawable.checkCircle),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "Deep Work Task",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun TimerView(progress: Float) {
    Box(
        modifier = Modifier
            .size(240.dp)
            .background(MaterialTheme.colorScheme.surface, CircleShape), // Circular background
        contentAlignment = Alignment.Center
    ) {
        // Track
        CircularProgressIndicator(
            progress = { 1f },
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surfaceVariant, // Gray track
            strokeWidth = 16.dp,
        )

        // Progress
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxSize(),
            color = SunriseCoral,
            strokeWidth = 16.dp,
        )

        Text(
            text = "Focus",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.graphicsLayer {
                alpha = (progress * 3).coerceAtMost(1f) // Text fades in quickly
            }
        )
    }
}