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
data class Notebook(
    val title: String,
    val user: String,
)


val test = Notebook("example", "example")

data class NotebooksUiState(
    //maxId int
    val notebooks: MutableList<Notebook> = mutableListOf<Notebook>(test, test),
    val isLoading: Boolean = false,
    val showCreate: Boolean = false,
    val showDelete: Boolean = false,
    val notebookName: String = ""
)

class NotebooksViewModel(): ViewModel(){
    private val tag = "NotebooksViewModel"
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
                currentState.copy(notebooks = mutableListOf(supabase.from("Notebooks").select().decodeAs<Notebook>()))
            }

        }
    }

}