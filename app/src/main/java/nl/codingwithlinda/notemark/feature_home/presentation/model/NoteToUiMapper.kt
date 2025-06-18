package nl.codingwithlinda.notemark.feature_home.presentation.model

import android.os.Build
import androidx.annotation.RequiresApi
import nl.codingwithlinda.core.domain.model.Note
import nl.codingwithlinda.notemark.feature_home.data.local.NoteCreator
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.toJavaInstant

@OptIn(ExperimentalTime::class)
fun Note.toUi(): NoteUi {

    val dateFormatted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val dateInstant = NoteCreator.iso8601ToInstant(dateCreated).toJavaInstant()
        val zdt = ZonedDateTime.ofInstant(dateInstant, ZoneId.systemDefault())
         zdt.toLocalDate().let {
            "${it.dayOfMonth} ${it.month}"
        }
    }
    else{
        dateCreated //todo parse date
    }

    return NoteUi(
        date = dateFormatted,
        title = title,
        content = content
    )
}

fun NoteUi.limitContent(maxCharacters: Int, ): NoteUi{
    return this.copy(
        content = content.take(maxCharacters)
    )
}
