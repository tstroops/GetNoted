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
    val id: Int,
    val createdAt: String,
    val title: String,
    val user: String,
    val number: Short,
)

data class NotebooksUiState(
    //maxId int
    val notebooks: List<Notebook> = emptyList(),
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

    fun getNotebooks(){
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(notebooks = listOf(supabase.from("Notebooks").select().decodeAs<Notebook>()))
            }

        }
    }

}