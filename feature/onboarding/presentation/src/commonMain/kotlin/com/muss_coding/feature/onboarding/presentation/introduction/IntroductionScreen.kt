package com.muss_coding.feature.onboarding.presentation.introduction

import com.muss_coding.feature.onboarding.presentation.introduction.components.ProjectToTasksAnimationWithText
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.muss_coding.core.presentation.resource_sharing.ResourceHelper
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource

@Composable
fun IntroductionScreen(
    modifier: Modifier = Modifier,
    onNextClick: () -> Unit
) {
    var animationKey by remember { mutableStateOf(true) }

    val numberOfAnimations = 4

    val visibilityAnimation = remember {
        List(numberOfAnimations) { Animatable(0f) }
    }

    LaunchedEffect(animationKey) {
        visibilityAnimation.forEach { it.snapTo(0f) }

        visibilityAnimation.forEachIndexed {index, animatable ->
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000)
            )
            delay(if (index == 2) 3000 else 500)
        }
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(ResourceHelper.string.organize_your_world),
                style = MaterialTheme.typography.displayLarge.copy(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.onBackground
                        )
                    )
                ),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(16.dp).graphicsLayer {
                    alpha = visibilityAnimation[0].value
                }
            )

            Text(
                text = stringResource(ResourceHelper.string.second_desc),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth(.85f).padding(16.dp).graphicsLayer {
                    alpha = visibilityAnimation[1].value
                }.align(Alignment.End),
                textAlign = TextAlign.End
            )

            if (visibilityAnimation[2].value == 1f)
                ProjectToTasksAnimationWithText()

        }

        OutlinedButton(
            modifier = Modifier.padding(end = 16.dp).graphicsLayer {
                alpha = visibilityAnimation[3].value
            }
                .align(Alignment.End),
            onClick = {
                onNextClick()
            }
        ) {
            Text(
                text = stringResource(ResourceHelper.string.next),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
            )
        }
    }

}