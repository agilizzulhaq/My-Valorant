package com.agilizzulhaq.submissionakhiraplikasiandroid.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agilizzulhaq.submissionakhiraplikasiandroid.data.AgentRepository
import com.agilizzulhaq.submissionakhiraplikasiandroid.model.Agent
import com.agilizzulhaq.submissionakhiraplikasiandroid.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: AgentRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Agent>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Agent>>>
        get() = _uiState

    fun getFavoriteAgent() = viewModelScope.launch {
        repository.getFavoriteAgent()
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updateAgent(id: Int, newState: Boolean) {
        repository.updateAgent(id, newState)
        getFavoriteAgent()
    }
}