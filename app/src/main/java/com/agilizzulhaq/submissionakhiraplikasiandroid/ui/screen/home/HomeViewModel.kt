package com.agilizzulhaq.submissionakhiraplikasiandroid.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agilizzulhaq.submissionakhiraplikasiandroid.data.AgentRepository
import com.agilizzulhaq.submissionakhiraplikasiandroid.model.Agent
import com.agilizzulhaq.submissionakhiraplikasiandroid.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: AgentRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Agent>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Agent>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun searchAgent(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        repository.searchAgent(_query.value)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updateAgent(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateAgent(id, newState)
            .collect { isUpdated ->
                if (isUpdated) searchAgent(_query.value)
            }
    }
}