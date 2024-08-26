package com.example.phonecleaner.data.repository

interface ApiClient {
     suspend fun cleanupStorage(): Boolean

}