package com.example.phonecleaner.data.remote

import android.net.http.HttpResponseCache.install
import com.example.phonecleaner.utlis.Constant.BEARER_TOKEN
import com.example.phonecleaner.utlis.Constant.TIMEOUT
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object CleanerApiClient {
    @OptIn(ExperimentalSerializationApi::class)
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                }
            )
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
        }


        install(HttpTimeout) {
            requestTimeoutMillis = TIMEOUT
            socketTimeoutMillis = TIMEOUT
            connectTimeoutMillis = TIMEOUT
        }
    }

    suspend fun cleanupStorage(): Boolean {
        val endpointUrl = "https://api.apify.com/v2/actor-runs/T8hRZglgbiFfikTL3/resurrect"

        try {
            val response: HttpResponse = client.get(endpointUrl) {
                headers {
                    append("Authorization", "Bearer $BEARER_TOKEN")
                }
            }

            return if (response.status.isSuccess()) {
                println("Storage cleanup succeeded.")
                true
            } else {
                println("Storage cleanup failed. Status: ${response.status}")
                false
            }
        } catch (e: Exception) {
            println("Exception occurred during storage cleanup: ${e.message}")
            return false
        }
    }
}