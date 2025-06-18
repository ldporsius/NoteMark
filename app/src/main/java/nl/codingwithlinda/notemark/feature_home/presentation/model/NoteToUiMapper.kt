package nl.codingwithlinda.notemark.feature_home.presentation.model

import nl.codingwithlinda.core.domain.model.Note
import nl.codingwithlinda.notemark.feature_home.data.local.NoteCreator

fun Note.toUi(
    now: org.threeten.bp.ZonedDateTime
): NoteUi {
    val dateInstant = NoteCreator.iso8601ToInstant(dateCreated)

    val dateFormatted = {
        val zoneId = org.threeten.bp.ZoneId.systemDefault()
        val zdt = org.threeten.bp.ZonedDateTime.ofInstant(dateInstant, zoneId)

        val isInCurrentYear = zdt.year == now.year
        if (isInCurrentYear){
            zdt.dateWithoutYear()
        }
        else{
            zdt.dateWithYear()
        }
    }

    return NoteUi(
        date = dateFormatted(),
        title = title,
        content = content
    )
}

fun org.threeten.bp.ZonedDateTime.dateWithYear(): String {
    val formatter = org.threeten.bp.format.DateTimeFormatter.ofLocalizedDate(org.threeten.bp.format.FormatStyle.MEDIUM)
    return this.format(formatter)

}

fun org.threeten.bp.ZonedDateTime.dateWithoutYear(): String {
    val formatter = org.threeten.bp.format.DateTimeFormatter.ofPattern("dd MMM")
    return this.format(formatter)
}


fun NoteUi.limitContent(maxCharacters: Int, ): NoteUi{
    return this.copy(
        content = content.take(maxCharacters)
    )
}
