package nl.codingwithlinda.notemark.feature_home.presentation.detail.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.notemark.R
import nl.codingwithlinda.notemark.feature_home.presentation.detail.state.NoteDetailAction

@Composable
fun NoteDetailTopBar(
    isSaving: Boolean = false,
    onAction: (NoteDetailAction) -> Unit,
    modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                onAction(NoteDetailAction.ConfirmCancelDialog)
            }
        ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }

        val infiniteTransition = rememberInfiniteTransition()
        val scale by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = .5f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(1000),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = StartOffset(50)
                )
        )
        AnimatedContent(isSaving) {isSaving ->
            if (isSaving) {
                //an animation with an image that get larger and smaller continuously
                Image(painter = painterResource(R.drawable.files),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp * scale)
                    )

            }
            else {
                TextButton(
                    onClick = {
                        onAction(NoteDetailAction.SaveAction)
                    }
                ) {
                    Text(
                        text = "Save note".uppercase(),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

    }
}