import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.LoadedFontFamily
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProjectToTasksAnimation() {
    // 1. STATE MANAGEMENT
    // A key to relaunch the animation
    var animationKey by remember { mutableStateOf(true) }

    val projectRadius = 250.0f
    val taskRadius = 125f
    val numberOfTasks = 3

    // Store Animatable objects for each task's position and alpha
    // We use 'remember' so they persist across recompositions
    val taskPositions = remember {
        List(numberOfTasks) { Animatable(Offset.Zero, Offset.VectorConverter) }
    }
    val taskAlphas = remember {
        List(numberOfTasks) { Animatable(0f) }
    }

    // 2. ANIMATION TRIGGER (The Brains 🧠)
    // This effect runs whenever the `animationKey` changes.
    LaunchedEffect(animationKey) {
        // Define the starting position for all tasks (center of the project circle)
        val startPosition = Offset.Zero // Canvas center is (0,0)

        // Reset all tasks to their initial state before starting
        taskPositions.forEach { it.snapTo(startPosition) }
        taskAlphas.forEach { it.snapTo(0f) }

        // Animate each task one by one
        taskPositions.forEachIndexed { index, animatable ->
            launch {
                // Stagger the start of each animation
                delay(index * 200L)

                // Animate alpha to fade in
                taskAlphas[index].animateTo(
                    targetValue = 1f,
                    animationSpec = spring(stiffness = 50f)
                )

                val offset = when(index) {
                    0 -> Offset(
                        x = (projectRadius * 2),
                        y = (projectRadius * 2)
                    )
                    1 -> Offset(
                        x = (projectRadius * 2),
                        y = 0f
                    )
                    2 -> Offset(
                        x = (projectRadius * 2),
                        y = -(projectRadius * 2)
                    )
                    else -> Offset(0f, 0f)
                }

                //old offset
                /*
                Offset(
                        x = (projectRadius * 2) + (index * (taskRadius * 2 + 20f)),
                        y = 0f
                    )
                 */
                // Animate position to move to the final spot in the line
                animatable.animateTo(
                    targetValue = offset,
                    animationSpec = spring(
                        dampingRatio = 0.6f,
                        stiffness = 100f
                    )
                )
            }
        }
    }


    // 3. UI & DRAWING
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        var textPosition by remember {
            mutableStateOf(Pair(0f, 0f))
        }
        Canvas(modifier = Modifier.fillMaxSize(.5f)) {
            val projectCenter = this.center
            textPosition = Pair(projectCenter.x, projectCenter.y)
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
                    // The animatable's value is its current position on each frame
                    center = projectCenter + positionAnimatable.value,
                    alpha = taskAlphas[index].value
                )
            }
        }

        Button(
            onClick = { animationKey = !animationKey }, // Toggling the key restarts the LaunchedEffect
            modifier = Modifier.padding(top = 24.dp).align(Alignment.CenterHorizontally)
        ) {
            Text("Run Animation Again")
        }
    }
}

@Preview()
@Composable
fun AnimationPreview() {
    ProjectToTasksAnimation()
}