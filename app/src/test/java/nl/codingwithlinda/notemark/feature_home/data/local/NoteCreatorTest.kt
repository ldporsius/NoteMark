package nl.codingwithlinda.notemark.feature_home.data.local

import org.junit.Test
import assertk.*
import assertk.assertions.hasLength

class NoteCreatorTest {

//    Examples:
//    •2023-10-27T10:30:00Z (October 27, 2023, at 10:30:00 AM UTC)
//    •2023-11-15T14:45:30.123Z (November 15, 2023, at 2:45:30.123 PM UTC)
//    •2024-01-05T00:00:00+02:00 (January 5, 2024, at midnight in a time zone that is 2 hours ahead of UTC)
    val noteCreator = NoteCreator
    @Test
    fun `newNote basic creation`() {
        // Verify that a Note object is successfully created with the provided title and content.
       val n = noteCreator.newNote("Test Title", "Test Content")
        assert(n.title == "Test Title")
        assert(n.content == "Test Content")
        assertThat(n.id).hasLength(32)
        // assert dateCreated is a valid ISO 8601 string representation of an Instant.
        println("**********dateCreated: " + n.dateCreated)


    }

    @Test
    fun `newNote title correctness`() {
        // Ensure the 'title' field of the created Note matches the input title.
        // TODO implement test
    }

    @Test
    fun `newNote content correctness`() {
        // Ensure the 'content' field of the created Note matches the input content.
        // TODO implement test
    }

    @Test
    fun `newNote ID uniqueness`() {
        // Create multiple notes and assert that each generated 'id' is unique.
        // TODO implement test
    }

    @Test
    fun `newNote ID format`() {
        // Verify the generated 'id' is a valid hexadecimal string representation of a UUID.
        // TODO implement test
    }

    @Test
    fun `newNote dateCreated format`() {
        // Check if 'dateCreated' is a valid ISO 8601 string representation of an Instant.
        // TODO implement test
    }

    @Test
    fun `newNote dateLastUpdated equals dateCreated`() {
        // Confirm that 'dateLastUpdated' is initially the same as 'dateCreated'.
        // TODO implement test
    }

    @Test
    fun `newNote empty title`() {
        // Test note creation with an empty string for the title.
        // TODO implement test
    }

    @Test
    fun `newNote empty content`() {
        // Test note creation with an empty string for the content.
        // TODO implement test
    }

    @Test
    fun `newNote empty title and content`() {
        // Test note creation with empty strings for both title and content.
        // TODO implement test
    }

    @Test
    fun `newNote long title`() {
        // Test note creation with a very long string for the title to check for potential truncation or errors.
        // TODO implement test
    }

    @Test
    fun `newNote long content`() {
        // Test note creation with a very long string for the content to check for potential truncation or errors.
        // TODO implement test
    }

    @Test
    fun `newNote title with special characters`() {
        // Test note creation with a title containing various special characters (e.g., Unicode, symbols).
        // TODO implement test
    }

    @Test
    fun `newNote content with special characters`() {
        // Test note creation with content containing various special characters (e.g., Unicode, symbols, newlines).
        // TODO implement test
    }

    @Test
    fun `newNote title with leading trailing whitespace`() {
        // Test if leading/trailing whitespace in the title is preserved or trimmed as expected.
        // TODO implement test
    }

    @Test
    fun `newNote content with leading trailing whitespace`() {
        // Test if leading/trailing whitespace in the content is preserved or trimmed as expected.
        // TODO implement test
    }

    @Test
    fun `newNote timestamp monotonicity`() {
        // Create two notes in quick succession and verify that the 'dateCreated' of the second note is greater than or equal to the first.
        // TODO implement test
    }

    @Test
    fun `newNote stability across time zones`() {
        // (If applicable and mockable) Test if the dateCreated is consistent regardless of the system's current time zone, as Instant should be UTC based.
        // TODO implement test
    }

    @Test
    fun `newNote Uuid random collision  theoretical `() {
        // While highly improbable, acknowledge the theoretical possibility of Uuid collision, though direct testing is impractical. 
        // The primary test is uniqueness across a reasonable number of creations.
        // TODO implement test
    }

}