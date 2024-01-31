package com.example.better.helper

import kotlinx.serialization.json.Json

/**
 * Create by SunnyDay /01/31 21:43:17
 */
object SerializationHelper {

    val serialization = Json {
        prettyPrint = true
        encodeDefaults = true
        ignoreUnknownKeys = true
        isLenient = true
        allowStructuredMapKeys = true
    }
}