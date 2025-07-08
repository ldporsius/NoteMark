package nl.codingwithlinda.notemark.feature_home.presentation.model

import nl.codingwithlinda.core.domain.model.Note
import nl.codingwithlinda.notemark.core.navigation.dto.EditNoteDto
import nl.codingwithlinda.notemark.feature_home.data.local.NoteCreator
import org.threeten.bp.Instant
import org.threeten.bp.ZonedDateTime
import java.util.Locale

fun Note.toUi(
    now: org.threeten.bp.ZonedDateTime = org.threeten.bp.ZonedDateTime.now()
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
        id = id,
        date = dateFormatted(),
        lastEdited = "TODO",
        title = title,
        content = content
    )
}

fun Note.toUiDetail(
    now: org.threeten.bp.ZonedDateTime = org.threeten.bp.ZonedDateTime.now()
): NoteUi {
    //date created format: dd MMM yyyy, HH:mm
    //if date is less then 5 minutes old show "just now"

    val dateInstant = NoteCreator.iso8601ToInstant(dateCreated)

    val dateFormatted = formatDateForNoteDetails(dateInstant, now)

    val lastEditedFormatted = formatDateForNoteDetails(NoteCreator.iso8601ToInstant(dateLastUpdated), now)
    return NoteUi(
        id = id,
        date = dateFormatted,
        lastEdited = lastEditedFormatted,
        title = title,
        content = content
    )
}

fun formatDateForNoteDetails(dateInstant: Instant, now: ZonedDateTime):String{
    val zoneId = org.threeten.bp.ZoneId.systemDefault()
    val zdt = org.threeten.bp.ZonedDateTime.ofInstant(dateInstant, zoneId)

    val isYoungerThanFiveMinutes = org.threeten.bp.Duration.between(zdt, now).toMinutes() < 5
    return if (isYoungerThanFiveMinutes){
        "Just now"
    }
    else{
        zdt.fullDateTime()
    }
}

fun org.threeten.bp.ZonedDateTime.dateWithYear(): String {
    val formatter = org.threeten.bp.format.DateTimeFormatter.ofLocalizedDate(org.threeten.bp.format.FormatStyle.MEDIUM)
    return this.format(formatter).filterNot { it == '.' }
}

fun org.threeten.bp.ZonedDateTime.dateWithoutYear(): String {
    val formatter = org.threeten.bp.format.DateTimeFormatter.ofPattern("dd MMM")
    return this.format(formatter).filterNot { it == '.' }
}

fun ZonedDateTime.fullDateTime(): String{
    val formatter = org.threeten.bp.format.DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm", Locale.getDefault())
    return this.format(formatter)
}


fun NoteUi.limitContent(maxCharacters: Int, ): NoteUi{
    if (this.content.length <= maxCharacters) return this
    val wholeWords = this.content.split(" ")
    var limitedWords = ""
        for (word in wholeWords){
            limitedWords = limitedWords.plus("$word ")
            if (limitedWords.length + word.length > maxCharacters) break
        }

    val limitByCharacters = limitedWords.take(maxCharacters)
    return this.copy(
        content = "$limitByCharacters..."
    )
}

fun Note.toEditNoteUi(): EditNoteDto {
    return EditNoteDto(
        id = id,
        title = title,
        content = content,
        dateCreated = dateCreated
    )
}
