package com.ivan.gfghackathon.Model

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun InternetObserver(): Flow<Status>

    enum class Status{
        Available,Unavailable,Losing,Lost
    }
}