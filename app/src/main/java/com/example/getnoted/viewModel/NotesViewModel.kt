package com.example.getnoted.viewModel

import android.R.attr.tag
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.getnoted.data.NotesRepository.addNote
import com.example.getnoted.data.NotesRepository.deleteNote
import com.example.getnoted.data.NotesRepository.getNotesByNotebook
import com.example.getnoted.data.NotesRepository.updateNote  // add this import
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Long = 0,
    @SerialName("note_name") val name: String = "",
    val info: String? = null,
    @SerialName("notebook_id") val nbId: Long = 0,
    @SerialName("created_at") val createdAt: String = ""
)

data class NotesUiState(
    val notes: List<Note> = listOf(),
    val showCreate: Boolean = false,
    val showDelete: Boolean = false,
    val noteName: String = "",
    val currentNotebookId: Long = 0,
    val selectedNote: Note? = null,
    val currentNoteText: String = ""
)

class NotesViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    private val tag = "NotesViewModel"

    fun setNotebookId(id: Long) {
        _uiState.update { it.copy(currentNotebookId = id) }
    }

    fun selectNote(note: Note) {
        _uiState.update { it.copy(
            selectedNote = note,
            currentNoteText = note.info ?: ""
        )}
    }

    fun toggleCreate(){
        _uiState.update { it.copy(showCreate = !it.showCreate) }
    }

    fun toggleDelete(){
        _uiState.update { it.copy(showDelete = !it.showDelete) }
    }

    fun cancelRequest(){
        _uiState.update { it.copy(showCreate = false, showDelete = false) }
    }

    fun getNotes(){
        viewModelScope.launch {
            val nbId = _uiState.value.currentNotebookId
            _uiState.update { it.copy(notes = getNotesByNotebook(nbId)) }
        }
    }

    fun updateName(name: String){
        _uiState.update { it.copy(noteName = name) }
    }

    fun updateNoteText(text: String) {
        _uiState.update { it.copy(currentNoteText = text) }
    }

    fun createNote() {
        viewModelScope.launch {
            val nbId = _uiState.value.currentNotebookId
            addNote(_uiState.value.noteName, nbId)
            getNotes()
            cancelRequest()
        }
    }

    fun deleteNote(){
        viewModelScope.launch {
            val noteId = _uiState.value.selectedNote?.id ?: return@launch
            deleteNote(noteId.toInt())
            getNotes()
            cancelRequest()
        }
    }

    fun saveNote() {
        viewModelScope.launch {
            val noteId = _uiState.value.selectedNote?.id ?: return@launch
            val text = _uiState.value.currentNoteText
            try {
                updateNote("info", text, noteId.toInt())
                getNotes()
                Log.d(tag,"Note Saved Success!")
            } catch (e: Exception) {
            Log.d(tag, "Save Failed", e)
        }

        }
    }
}