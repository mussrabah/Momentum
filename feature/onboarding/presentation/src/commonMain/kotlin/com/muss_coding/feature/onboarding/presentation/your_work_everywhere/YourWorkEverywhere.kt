package com.muss_coding.feature.onboarding.presentation.your_work_everywhere

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.muss_coding.core.presentation.resource_sharing.ResourceHelper
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

// This is your "Sunrise Coral" color.
val SunriseCoral = Color(0xFFF99A8A)

@Composable
fun YourWorkEverywhereScreen(
    modifier: Modifier = Modifier,
    onCreateAccountClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    // --- State for Animations ---
    val textAlpha = remember { Animatable(0f) }
    val animationProgress = remember { Animatable(0f) }
    val buttonsAlpha = remember { Animatable(0f) }

    // --- Animation Logic ---

    // Animate the text alpha from 0f to 1f when the screen appears
    LaunchedEffect(Unit) {
        // 1. Fade in text
        textAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 800)
        )

        // 2. Wait a beat, then play the sync animation
        kotlinx.coroutines.delay(200)
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
        )

        // 3. Fade in the buttons
        buttonsAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500)
        )
    }

    // --- UI Layout ---

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        // Use SpaceBetween to push text to top and buttons to bottom
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // --- 1. Headline ---
            Text(
                text = stringResource(ResourceHelper.string.stay_in_sync),
                style = MaterialTheme.typography.displayLarge.copy(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.onBackground
                        )
                    )
                ),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp).graphicsLayer {
                    alpha = textAlpha.value
                }
            )

            // --- 2. Body Text ---
            Text(
                text = stringResource(ResourceHelper.string.your_projects_synced),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth(.85f)
                    .padding(16.dp)
                    .graphicsLayer {
                        alpha = textAlpha.value
                    }
                    .align(Alignment.End),
                textAlign = TextAlign.End
            )

            Spacer(Modifier.height(32.dp))
        }


        // --- 3. Animation "Stage" ---
        SyncAnimation(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            progress = animationProgress.value
        )

        // --- 4. CTAs (Call to Action) ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
                .graphicsLayer {
                    alpha = buttonsAlpha.value
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Primary Button
            Button(
                onClick = onCreateAccountClick,
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SunriseCoral,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = stringResource(ResourceHelper.string.create_account),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(Modifier.height(8.dp))

            // Secondary Link
            TextButton(onClick = onLoginClick) {
                Text(
                    text = stringResource(ResourceHelper.string.i_already_have_an_account),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary, // Midnight Blue
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}

/**
 * The animation showing the sync across devices.
 */
@Composable
private fun SyncAnimation(
    modifier: Modifier = Modifier,
    progress: Float
) {
    val path = remember { Path() }
    val pathMeasure = remember { PathMeasure() }
    val iconColor = MaterialTheme.colorScheme.onBackground

    Box(modifier = modifier) {
        // --- 1. Icons ---
        Icon(
            painter = painterResource(ResourceHelper.drawable.desktopMac),
            contentDescription = "Desktop",
            tint = iconColor,
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(48.dp)
                .padding(start = 16.dp)
        )
        Icon(
            painter = painterResource(ResourceHelper.drawable.tabletMac),
            contentDescription = "Tablet",
            tint = iconColor,
            modifier = Modifier
                .align(Alignment.Center)
                .size(40.dp)
                .padding(end = 40.dp) // Offset a bit
        )
        Icon(
            painter = painterResource(ResourceHelper.drawable.phoneAndroid),
            contentDescription = "Phone",
            tint = iconColor,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(32.dp)
                .padding(end = 16.dp)
        )
        val surface = MaterialTheme.colorScheme.surfaceVariant
        // --- 2. Canvas for Path & Orb ---
        Canvas(modifier = Modifier.fillMaxSize()) {
            val iconSize = 48.dp.toPx()

            // Define start, mid, and end points for the path
            val startPos = Offset(iconSize / 2 + 16.dp.toPx(), iconSize / 2)
            val midPos = Offset(size.width / 2 - 20.dp.toPx(), size.height / 2)
            val endPos = Offset(size.width - iconSize / 2 - 8.dp.toPx(), size.height - iconSize / 2)

            // Build a curved path
            path.reset()
            path.moveTo(startPos.x, startPos.y)
            path.cubicTo(
                x1 = startPos.x, y1 = size.height / 2, // Control 1
                x2 = midPos.x, y2 = size.height / 2,     // Control 2
                x3 = midPos.x, y3 = midPos.y             // Midpoint
            )
            path.cubicTo(
                x1 = midPos.x, y1 = size.height / 2,     // Control 3
                x2 = endPos.x, y2 = size.height / 2,       // Control 4
                x3 = endPos.x, y3 = endPos.y               // Endpoint
            )

            // Draw the "track"
            drawPath(
                path = path,
                color = surface,
                style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
            )

            // Get the position of the orb based on progress
            pathMeasure.setPath(path, false)
            val distance = pathMeasure.length * progress
            val orbPosition = pathMeasure.getPosition(distance)

            if (orbPosition != Offset.Unspecified) {
                // Draw the "glowing orb"
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.White, SunriseCoral, SunriseCoral.copy(alpha = 0.3f)),
                        center = orbPosition,
                        radius = 12.dp.toPx()
                    ),
                    radius = 12.dp.toPx(),
                    center = orbPosition
                )
            }
        }
    }
}