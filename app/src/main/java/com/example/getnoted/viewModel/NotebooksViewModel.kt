package com.example.getnoted.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getnoted.data.NotesRepository.addNotebook
import com.example.getnoted.data.NotesRepository.getNotebooksByUser
import com.example.getnoted.data.NotesRepository.deleteNotebook
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Notebook(
    val id: Long = 0,
    @SerialName("Title") val title: String = "",
    @SerialName("user") val userId: String = "",
    @SerialName("created_at") val createdAt: String = ""
)

data class NotebooksUiState(
    val notebooks: List<Notebook> = listOf(),
    val showCreate: Boolean = false,
    val showDelete: Boolean = false,
    val notebookName: String = "",
    val selectedNotebook: Notebook? = null
)

class NotebooksViewModel: ViewModel(){

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
                currentState.copy(notebooks = getNotebooksByUser())
            }

        }
    }

    fun createNotebook(){
        viewModelScope.launch {
            addNotebook(_uiState.value.notebookName,)
            getNotebooks()
            cancelRequest()
        }
    }

    fun deleteNotebook(){
        viewModelScope.launch {
            val notebookId = _uiState.value.selectedNotebook?.id ?: return@launch
            deleteNotebook(notebookId.toInt())
            getNotebooks()
            cancelRequest()
        }
    }

    fun selectNotebook(notebook: Notebook) {
        _uiState.update { it.copy(selectedNotebook = notebook) }
    }

    fun getNbId(index: Int): Long{
        return _uiState.value.notebooks[index].id
    }

}