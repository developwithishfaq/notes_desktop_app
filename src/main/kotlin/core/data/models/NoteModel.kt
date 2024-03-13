package core.data.models

import kotlinx.serialization.Serializable

@Serializable
data class NoteModel(
    val title: String,
    val content: String,
    val filePath: String? = null,
)
