package com.example.better.entity

import com.youth.banner.Banner
import kotlinx.serialization.Serializable

/**
 * Create by SunnyDay /01/31 21:15:40
 */

@Serializable
data class HomeEntity(
    val banners: List<String> = emptyList()
)