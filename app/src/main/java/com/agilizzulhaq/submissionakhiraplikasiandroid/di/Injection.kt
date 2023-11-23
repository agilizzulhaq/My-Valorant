package com.agilizzulhaq.submissionakhiraplikasiandroid.di

import com.agilizzulhaq.submissionakhiraplikasiandroid.data.AgentRepository

object Injection {
    fun provideRepository(): AgentRepository {
        return AgentRepository.getInstance()
    }
}