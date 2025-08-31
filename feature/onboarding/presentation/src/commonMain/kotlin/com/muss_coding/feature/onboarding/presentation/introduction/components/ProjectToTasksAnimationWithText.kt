package com.muss_coding.feature.onboarding.presentation.introduction.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProjectToTasksAnimationWithText(modifier: Modifier = Modifier) {
    var animationKey by remember { mutableStateOf(true) }
    val projectRadius = 250f // Adjusted for better screen fit
    val taskRadius = 125f   // Adjusted for better screen fit
    val numberOfTasks = 3

    val commonTaskNames = remember {
        listOf(
            "Code Review", "Daily Standup", "Email Inbox", "Plan Day",
            "Research", "Write Report", "Client Call"
        )
    }
    val commonProjectsNames = remember {
        listOf(
            "Project Nexus", "Quantum Leap", "Operation Apex", "Odyssey Project",
            "Vanguard", "Project Phoenix", "Momentum Drive", "Keystone"
        )
    }

    val taskTexts = remember { mutableStateListOf<String>() }
    var projectName by remember { mutableStateOf("") }

    // --- STATE FOR ANIMATIONS ---
    val taskPositions = remember {
        List(numberOfTasks) { Animatable(Offset.Zero, Offset.VectorConverter) }
    }
    val taskAlphas = remember {
        List(numberOfTasks) { Animatable(0f) }
    }
    // KEY CHANGE 1: Dedicated Animatable for the project name's alpha
    val projectNameAlpha = remember { Animatable(0f) }


    LaunchedEffect(animationKey) {
        // --- 1. RESET STATE ---
        taskTexts.clear()
        repeat(numberOfTasks) { taskTexts.add(commonTaskNames.random()) }
        projectName = commonProjectsNames.random()

        val startPosition = Offset.Zero
        projectNameAlpha.snapTo(0f) // Reset project name to be invisible
        taskPositions.forEach { it.snapTo(startPosition) }
        taskAlphas.forEach { it.snapTo(0f) }

        // --- 2. START TASK ANIMATIONS CONCURRENTLY ---
        val taskAnimationJobs = mutableListOf<Job>()
        taskPositions.forEachIndexed { index, animatable ->
            val job = launch {
                delay(index * 200L) // Stagger the start

                // Animate alpha (fade-in)
                launch {
                    taskAlphas[index].animateTo(targetValue = 1f, animationSpec = spring(stiffness = 50f))
                }

                // Animate position
                val offset = when (index) {
                    0 -> Offset(x = projectRadius + 100f, y = -(projectRadius + 50f))
                    1 -> Offset(x = projectRadius + 150f, y = 0f)
                    2 -> Offset(x = projectRadius + 100f, y = projectRadius + 50f)
                    else -> Offset.Zero
                }

                animatable.animateTo(
                    targetValue = offset,
                    animationSpec = spring(dampingRatio = 0.6f, stiffness = 100f)
                )
            }
            taskAnimationJobs.add(job)
        }

        // KEY CHANGE 2: Wait for ALL task animations to finish
        taskAnimationJobs.joinAll()

        // KEY CHANGE 3: Animate the project name AFTER tasks are done
        projectNameAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500)
        )
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.0f), // Use a square aspect ratio for simplicity
            contentAlignment = Alignment.Center
        ) {
             // The center of the Box

            Canvas(modifier = Modifier.fillMaxSize()) {
                val projectCenter = this.center
                // Draw the large, translucent "Project" circle
                drawCircle(
                    color = Color.Blue,
                    radius = projectRadius,
                    center = projectCenter,
                    alpha = 0.3f
                )

                // Draw each of the smaller "Task" circles
                taskPositions.forEachIndexed { index, positionAnimatable ->
                    drawCircle(
                        color = Color.Blue,
                        radius = taskRadius,
                        center = projectCenter + positionAnimatable.value,
                        alpha = taskAlphas[index].value
                    )
                }
            }
            val density = LocalDensity.current.density

            // --- Text for the "Project" circle ---
            Text(
                text = projectName,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .widthIn(max = (projectRadius * 2 / density).dp - 24.dp) // Prevent text overflow
                    .align(Alignment.Center)
                    .graphicsLayer {
                        // Use the new dedicated animatable for alpha
                        alpha = projectNameAlpha.value
                    }
            )

            // --- Texts for the "Task" circles ---
            taskPositions.forEachIndexed { index, positionAnimatable ->
                if (taskTexts.size > index) {
                    val animatedOffset = positionAnimatable.value
                    val animatedOffsetX = (animatedOffset.x / density).dp
                    val animatedOffsetY = (animatedOffset.y / density).dp
                    val alpha = taskAlphas[index].value

                    Text(
                        text = taskTexts[index],
                        color = Color.White.copy(alpha = alpha),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center) // Align to the center of the Box first
                            .offset(x = animatedOffsetX, y = animatedOffsetY) // Then apply the animated offset
                            .width((taskRadius * 2 / density).dp - 14.dp) // Constrain width
                    )
                }
            }
        }

//        Button(
//            onClick = { animationKey = !animationKey },
//            modifier = Modifier.padding(top = 24.dp)
//        ) {
//            Text("Run Animation Again")
//        }
    }
}

@Preview()
@Composable
fun ProjectToTasksAnimationWithTextPreview() {
    ProjectToTasksAnimationWithText()
}