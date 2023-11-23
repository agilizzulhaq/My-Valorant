package com.agilizzulhaq.submissionakhiraplikasiandroid.data

import com.agilizzulhaq.submissionakhiraplikasiandroid.model.Agent
import com.agilizzulhaq.submissionakhiraplikasiandroid.model.AgentData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class AgentRepository {
    private val dummyAgent = mutableListOf<Agent>()

    init {
        if (dummyAgent.isEmpty()) {
            AgentData.dummyAgents.forEach {
                dummyAgent.add(it)
            }
        }
    }

    fun getAgentById(agentId: Int): Agent {
        return dummyAgent.first {
            it.id == agentId
        }
    }

    fun getFavoriteAgent(): Flow<List<Agent>> {
        return flowOf(dummyAgent.filter { it.isFavorite })
    }

    fun searchAgent(query: String) = flow {
        val data = dummyAgent.filter {
            it.name.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    fun updateAgent(agentId: Int, newState: Boolean): Flow<Boolean> {
        val index = dummyAgent.indexOfFirst { it.id == agentId }
        val result = if (index >= 0) {
            val agent = dummyAgent[index]
            dummyAgent[index] = agent.copy(isFavorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: AgentRepository? = null
        fun getInstance(): AgentRepository = instance ?: synchronized(this) {
            AgentRepository().apply {
                instance = this
            }
        }
    }
}