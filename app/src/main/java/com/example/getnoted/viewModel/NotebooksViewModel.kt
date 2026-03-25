package com.example.getnoted.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getnoted.data.NotesRepository.addNotebook
import com.example.getnoted.data.NotesRepository.getNotebooksByUser
import com.example.getnoted.data.NotesRepository.deleteNotebook
import com.example.getnoted.data.NotesRepository.createNotebookID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class Notebook(
    val title: String,
    val userId: String,
    val id: Long
)

data class NotebooksUiState(
    val notebooks: List<Notebook> = listOf(),
    val showCreate: Boolean = false,
    val showDelete: Boolean = false,
    val notebookName: String = ""
)

class NotebooksViewModel: ViewModel(){

    private val authState = AuthUiState()

    private val _uiState = MutableStateFlow(NotebooksUiState())
    val uiState: StateFlow<NotebooksUiState> = _uiState.asStateFlow()

    fun updateName(name: String){
        _uiState.update { currentState ->
            currentState.copy(notebookName = name)
        }
    }

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

    fun getNotebooks(){
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(notebooks = getNotebooksByUser(authState.email))
            }

        }
    }

    fun createNotebook(){
        viewModelScope.launch {
            addNotebook(
                Notebook(
                    title = _uiState.value.notebookName,
                    userId = authState.email,
                    id = createNotebookID()
                )
            )
        }
    }

    fun deleteNotebook(){
        viewModelScope.launch {
            deleteNotebook(notebookId = -1)
        }
    }

    fun getNbId(index: Int): Long{
        return _uiState.value.notebooks[index].id
    }

}