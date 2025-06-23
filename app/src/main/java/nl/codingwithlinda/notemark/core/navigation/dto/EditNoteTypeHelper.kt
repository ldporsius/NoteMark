package nl.codingwithlinda.notemark.core.navigation.dto

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.json.Json

val EditNoteDtoType: NavType<EditNoteDto?> = object : NavType<EditNoteDto?>(isNullableAllowed = true) {
    override fun get(bundle: Bundle, key: String): EditNoteDto? {
        val json = bundle.getString(key)
        return  Json.decodeFromString<EditNoteDto>(json ?: return null)
    }

    override fun parseValue(value: String): EditNoteDto {
        val json = Uri.decode(value)
        val dto = Json.decodeFromString<EditNoteDto>(json)
        return dto
    }

    override fun serializeAsValue(value: EditNoteDto?): String {
        return Uri.encode(Json.encodeToString(value))
    }

    override fun put(bundle: Bundle, key: String, value: EditNoteDto?) {
        bundle.putString(key, value?.let { Json.encodeToString(it) })
    }

    override val name: String = "EditNoteDto" // Add a name
}