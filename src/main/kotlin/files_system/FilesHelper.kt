package files_system

import core.data.models.NoteModel
import core.getCurrentUsername
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.parameter.parameterSetOf
import java.io.File

class FilesHelper {

    private val _notes = MutableStateFlow<List<NoteModel>>(emptyList())
    val notes = _notes.asStateFlow()

    init {
        loadAllNotes()
    }

//    private val mainDirectory = "C:/Users/${getCurrentUsername()}/MyApp"


    fun saveNote(noteModel: NoteModel) {
        val mainDirectory = "C:/Users/${getCurrentUsername()}/MyApp"
        val filePath = File("$mainDirectory/${System.currentTimeMillis()}.txt")
        if (filePath.exists().not()) {
            filePath.createNewFile()
        }
        val text = Json.encodeToString(noteModel)
        filePath.writeText(text)
        addNoteInList(
            noteModel.copy(
                filePath = filePath.path
            )
        )
    }

    private fun addNoteInList(noteModel: NoteModel) {
        val list = notes.value.toMutableList()
        list.add(noteModel)
        _notes.update {
            list
        }
    }

    private fun loadAllNotes() {
        val mainDirectory = "C:/Users/${getCurrentUsername()}/MyApp"
        CoroutineScope(Dispatchers.IO).launch {
            println("cvv :::::: " + mainDirectory)
            val itemsList = File(mainDirectory).listFiles()
            val notesList = mutableListOf<Deferred<NoteModel>>()
            itemsList?.forEach { file ->
                notesList.add(
                    async {
                        Json.decodeFromString<NoteModel>(file.readText()).copy(
                            filePath = file.path
                        )
                    }
                )
            }

            _notes.update {
                notesList.awaitAll()
            }

        }
    }

    fun deleteItem(model: NoteModel) {
        model.filePath?.let { path ->
            File(path).delete()
            val list = notes.value.toMutableList()
            val index = list.indexOfFirst {
                it.filePath == model.filePath
            }
            if (index != -1) {
                list.removeAt(index)
            }
            _notes.update {
                list
            }
        }
    }

}