package nl.codingwithlinda.notemark.feature_home.presentation.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun UserAvatar(
    userAvatar: String,
    modifier: Modifier = Modifier) {

    Box(modifier =
        modifier.size(50.dp)
            .background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
            .clip(MaterialTheme.shapes.small)
        ,
        contentAlignment = Alignment.Center
    ){
        Text(text = userAvatar)
    }
}