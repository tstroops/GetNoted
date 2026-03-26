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
//used to decode information from supabase
data class Notebook(
    val id: Long = 0,
    @SerialName("Title") val title: String = "",
    @SerialName("user") val userId: String = "",
    @SerialName("created_at") val createdAt: String = ""
)

//values used to handle the Notebooks page
data class NotebooksUiState(
    val notebooks: List<Notebook> = listOf(),
    val showCreate: Boolean = false,
    val showDelete: Boolean = false,
    val notebookName: String = "",
    val selectedNotebook: Notebook? = null
)

class NotebooksViewModel: ViewModel(){

    //makes data class unwritable to outside classes
    private val _uiState = MutableStateFlow(NotebooksUiState())
    val uiState: StateFlow<NotebooksUiState> = _uiState.asStateFlow()

    //creates/updates the names of notebooks
    fun updateName(name: String){
        _uiState.update { currentState ->
            currentState.copy(notebookName = name)
        }
    }

    //handles the dialog for notebook creation
    fun toggleCreate(){
        _uiState.update { currentState ->
            currentState.copy(showCreate = !currentState.showCreate)
        }
    }

    //handles the dialog for notebook deletion
    fun toggleDelete(){
        _uiState.update {currentState ->
            currentState.copy(showDelete = !currentState.showDelete)
        }

    }

    //cancels all dialogs
    fun cancelRequest(){
        _uiState.update {currentState ->
            currentState.copy(showCreate = false, showDelete = false)
        }
    }

    //gets the notebooks from supabase
    fun getNotebooks(){
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(notebooks = getNotebooksByUser())
            }

        }
    }

    //creates a new notebook to send to supabase
    fun createNotebook(){
        viewModelScope.launch {
            addNotebook(_uiState.value.notebookName)
            getNotebooks()
            cancelRequest()
        }
    }

    //deletes a notebook from supabase
    fun deleteNotebook(){
        viewModelScope.launch {
            val notebookId = _uiState.value.selectedNotebook?.id ?: return@launch
            deleteNotebook(notebookId.toInt())
            getNotebooks()
            cancelRequest()
        }
    }

    //selects the notebook for deletion
    fun selectNotebook(notebook: Notebook) {
        _uiState.update { it.copy(selectedNotebook = notebook) }
    }

}