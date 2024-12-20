package com.example.fetch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetch.repo.FetchRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FetchListViewModel(private val fetchRepo: FetchRepo): ViewModel() {
    private val _uiState = MutableStateFlow(FetchUiState(isLoading = true))
    val uiState: StateFlow<FetchUiState>
        get() = _uiState

    init {
        loadList()
    }

    private fun loadList() {
        viewModelScope.launch {
            fetchRepo.fetchList()
                .catch { exception ->
                    _uiState.update {
                        it.copy(errorMessage = exception.message, isLoading = false)
                    }
                }
                .collect { list ->
                    _uiState.update { uiState ->
                        uiState.copy(
                            list = list.filter { !it.name.isNullOrBlank() }
                                .map {
                                    FetchItem(id = it.id, listId = it.listId, name = it.name!!)
                                }
                                .sortedWith(compareBy<FetchItem> { it.listId }.thenBy { it.name }),
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
        }
    }
}

data class FetchItem(val id: Int, val listId: Int, val name: String)

data class FetchUiState (
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val list: List<FetchItem> = emptyList()
)