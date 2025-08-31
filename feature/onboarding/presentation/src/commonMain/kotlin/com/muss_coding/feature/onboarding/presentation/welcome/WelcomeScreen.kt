package com.muss_coding.feature.onboarding.presentation.welcome

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.muss_coding.core.presentation.resource_sharing.ResourceHelper
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onNextClick: () -> Unit
) {
    var showFirstImage by remember { mutableStateOf(true) }
    var imageIndex by remember { mutableIntStateOf(0) }
    val progressAnim = remember { Animatable(50f) }

    LaunchedEffect(showFirstImage) {
        progressAnim.snapTo(if (imageIndex == 0) 50f else 0f)
        progressAnim.animateTo(
            targetValue = 90f,
            animationSpec = tween(durationMillis = 7000)
        )
        if (imageIndex == 0)
            progressAnim.snapTo(0f)
        imageIndex = 1  // switch to the next image when done
    }

    val progress2 = progressAnim.value


    val headerProgress by animateFloatAsState(
        targetValue = if (progress2 == 50f) .3f else 1f,
        animationSpec = tween(8000),
        label = ""
    )


    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(ResourceHelper.string.find_your_flow_state),
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
                alpha = headerProgress
            }
        )

        Text(
            text = stringResource(ResourceHelper.string.momentum_desc),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.fillMaxWidth(.85f).padding(16.dp).graphicsLayer {
                alpha = headerProgress
            }.align(Alignment.End),
            textAlign = TextAlign.End
        )


        if (imageIndex == 0) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(CircleShape)
                    .aspectRatio(
                        ratio = 0.3f,
                        //matchHeightConstraintsFirst = true
                    )
                    .graphicsLayer {
//                    rotationX = (100f - progress) * 30f
                        rotationY = (progress2)// * 30f
//                    rotationZ = (100f - progress) * 30f
                        val scale = 0.8f + (0.1f * progress2 / 100)
                        //alpha = (.85f - progress/100) * -1
                        alpha = if (progress2 < 0) 1f + progress2 / 100 else 1f - progress2 / 100
                        scaleX = scale
                        scaleY = scale
                    },
                painter = painterResource(ResourceHelper.drawable.paper_focus),
                contentDescription = stringResource(ResourceHelper.string.momentum_logo),
                colorFilter = ColorFilter
                    .tint(
                        MaterialTheme.colorScheme.primary,
                        blendMode = BlendMode.Screen
                    )
            )
        }
        else {
            showFirstImage = false
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(CircleShape)
                    .graphicsLayer {
                        val scale = 0.1f + progress2/100
                        alpha = progress2 / 100 - 0.3f
                        scaleX = scale
                        scaleY = scale
                    }
                    .shadow(elevation = 10.dp),
                imageVector = vectorResource(ResourceHelper.drawable.flow),
                contentDescription = stringResource(ResourceHelper.string.momentum_logo),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter
                    .tint(
                        MaterialTheme.colorScheme.primary,
                        blendMode = BlendMode.Color
                    )
            )
        }

        OutlinedButton(
            modifier = Modifier.padding(end = 16.dp).graphicsLayer {
                alpha = headerProgress
            }
                .align(Alignment.End),
            onClick = {
                onNextClick()
            }
        ) {
            Text(
                text = stringResource(ResourceHelper.string.lets_go),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
            )
        }
    }
}