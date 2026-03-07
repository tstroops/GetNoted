package com.example.getnoted.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getnoted.data.supabase
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val name: String,
    val info: String,
    val nbId: Long,
)

data class NotesUiState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val showCreate: Boolean = false,
    val showDelete: Boolean = false,
    val noteName: String = ""
)

class NotesViewModel(): ViewModel() {
    private val tag = "NotesViewModel"
    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

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
                currentState.copy(notes = listOf(supabase.from("Notes").select().decodeAs<Note>()))
            }
        }
    }

    fun updateName(name: String){
        _uiState.update { currentState ->
            currentState.copy(noteName = name)
        }
    }
}