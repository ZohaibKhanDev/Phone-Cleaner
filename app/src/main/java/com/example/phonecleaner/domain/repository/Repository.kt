package com.example.phonecleaner.domain.repository

import com.example.phonecleaner.data.remote.CleanerApiClient
import com.example.phonecleaner.data.repository.ApiClient

class Repository : ApiClient {
    override suspend fun cleanupStorage(): Boolean {
       return CleanerApiClient.cleanupStorage()
    }
}