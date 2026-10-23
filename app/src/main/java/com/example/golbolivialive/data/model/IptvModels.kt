package com.example.golbolivialive.data.model

data class IptvChannel(
    val id: String,
    val name: String,
    val logoUrl: String,
    val group: String,
    val countryCode: String, // "ALL", "BO", "AR", "CO", "MX", "ES", "INT"
    val countryName: String,
    val streamUrl: String,
    val resolution: String = "1080p",
    val isFavorite: Boolean = false,
    val notes: String = ""
)

data class IptvCountry(
    val code: String,
    val label: String,
    val flagEmoji: String
)

val IPTV_COUNTRIES_LIST = listOf(
    IptvCountry("ALL", "Todos los Deportes", "🌍"),
    IptvCountry("BO", "Bolivia", "🇧🇴"),
    IptvCountry("AR", "Argentina", "🇦🇷"),
    IptvCountry("CO", "Colombia", "🇨🇴"),
    IptvCountry("MX", "México", "🇲🇽"),
    IptvCountry("ES", "España", "🇪🇸"),
    IptvCountry("INT", "Internacional", "🌐")
)
