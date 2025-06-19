package nl.codingwithlinda.notemark.tests.dummy_content

import nl.codingwithlinda.notemark.core.navigation.dto.EditNoteDto
import nl.codingwithlinda.notemark.feature_home.data.local.NoteCreator
import nl.codingwithlinda.notemark.feature_home.presentation.model.NoteUi

val longDummyText = "The quiet hum of the morning air was broken only by the soft rustle of leaves dancing in the breeze. A cup of coffee steamed gently in hand, warmth seeping into chilled fingers. Thoughts flowed freely, unbothered by structure or form—just fragments of memory and hope stitched together. In this stillness, inspiration felt close, like a familiar friend waiting to be acknowledged and welcomed in. Pages remained blank, yet full of potential. Each pause between thoughts was its own kind of music. The world outside hadn’t changed, but the lens through which it was viewed felt freshly cleaned—clearer, softer. Clouds moved lazily across the sky, painting slow-moving stories above. A breeze swept in, carrying with it the faint scent of earth and something that reminded one of home. The clinking of a spoon against a ceramic mug, the distant bark of a dog, a laugh from a neighbor’s open window—each moment ordinary, yet profound. And somehow, all of it seemed to whisper: write, because this matters."
val tempNotes = List(10){
    NoteCreator.newNote(title = "Note $it", content = "This is note $it")
}
val olderNotes = tempNotes.map {
    it.copy(
        dateCreated = "2022-01-01T00:00:00.000Z",
        content = longDummyText
    )
}

val dummyUiNotes = List(10){
    NoteUi(
        id = it.toString(),
        date = "14 jul",
        title = "My first note",
        content = longDummyText
    )
}
val dummyNoteNoWhiteSpace = NoteCreator.newNote(
    title = "No white space",
    content = longDummyText.filterNot{it.isWhitespace()}
)

