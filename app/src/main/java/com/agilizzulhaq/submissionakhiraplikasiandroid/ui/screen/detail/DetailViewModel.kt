package com.agilizzulhaq.submissionakhiraplikasiandroid.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agilizzulhaq.submissionakhiraplikasiandroid.data.AgentRepository
import com.agilizzulhaq.submissionakhiraplikasiandroid.model.Agent
import com.agilizzulhaq.submissionakhiraplikasiandroid.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: AgentRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Agent>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Agent>>
        get() = _uiState

    fun getAgentById(id: Int) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(repository.getAgentById(id))
    }


    fun updateAgent(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateAgent(id, !newState)
            .collect { isUpdated ->
                if (isUpdated) getAgentById(id)
            }
    }

}