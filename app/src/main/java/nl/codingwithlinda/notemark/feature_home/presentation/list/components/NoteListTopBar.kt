package nl.codingwithlinda.notemark.feature_home.presentation.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.notemark.R
import nl.codingwithlinda.notemark.design_system.ui.theme.NoteMarkTheme
import nl.codingwithlinda.notemark.design_system.ui.theme.surfaceLowest
import nl.codingwithlinda.notemark.feature_home.presentation.list.state.NoteListAction

@Composable
fun NoteListTopBar(
    userAvatar: String,
    onAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(surfaceLowest)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(stringResource(R.string.app_name)
        ,style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = { onAction() }
        ) {
            Icon(painter = painterResource(id = R.drawable.settings),
                contentDescription = "settings",
                Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        UserAvatar(userAvatar = userAvatar)
    }
}

@Preview
@Composable
private fun NoteListTopBarPreview() {
    NoteMarkTheme {
        NoteListTopBar(
            userAvatar = "LP",
            onAction = {}
        )

    }
}