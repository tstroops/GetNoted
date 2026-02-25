package com.example.getnoted.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getnoted.data.supabase
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class Notebook(
    val id: Int = 0,
    val name: String = "",
    val userId: String = ""
)

data class NotebooksUiState(
    val notebooks: List<Notebook> = emptyList(),
    val isLoading: Boolean = false,
)

class NotebooksViewModel(): ViewModel(){
    private val tag = "NotebooksViewModel"

    private val _uiState = MutableStateFlow(NotebooksUiState())
    val uiState: StateFlow<NotebooksUiState> = _uiState.asStateFlow()

    init {
        fetchNotebooks()
    }

    fun fetchNotebooks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val notebooks = supabase.postgrest["Notebooks"]
                .select()
                .decodeList<Notebook>()

            _uiState.update {
                it.copy(
                    notebooks = notebooks,
                    isLoading = false
                    )
                }
        }
    }
}