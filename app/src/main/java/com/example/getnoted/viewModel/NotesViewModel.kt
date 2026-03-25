package com.example.getnoted.viewModel

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
import com.example.getnoted.data.NotesRepository.createNoteID
import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val name: String,
    val info: String,
    val nbId: Long,
    val id: Long
)

data class NotesUiState(
    val notes: List<Note> = listOf(),
    val showCreate: Boolean = false,
    val showDelete: Boolean = false,
    val noteName: String = "",
)

class NotesViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()
    private val nbState = NotebooksUiState()

    fun toggleCreate(){
        _uiState.update { currentState ->
            currentState.copy(showCreate = !currentState.showCreate)
        }
    }

    fun toggleDelete(){
        _uiState.update {currentState ->
            currentState.copy(showDelete = !currentState.showDelete)
        }
    }

    fun cancelRequest(){
        _uiState.update {currentState ->
            currentState.copy(showCreate = false, showDelete = false)
        }
    }

    fun getNotes(){
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(notes = getNotesByNotebook(-1))
            }
        }
    }

    fun updateName(name: String){
        _uiState.update { currentState ->
            currentState.copy(noteName = name)
        }
    }

    fun createNote(){
        viewModelScope.launch {
            addNote(Note(
                name = _uiState.value.noteName,
                info = "example",
                nbId = 1,
                id = createNoteID()
            ))
        }
    }

    fun deleteNote(){
        viewModelScope.launch {
            deleteNote(noteId = -1)
        }
    }

}