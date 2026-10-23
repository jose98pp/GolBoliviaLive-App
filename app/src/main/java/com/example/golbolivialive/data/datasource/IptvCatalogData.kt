package com.example.golbolivialive.data.datasource

import com.example.golbolivialive.data.model.IptvChannel

object IptvCatalogData {

    val CHANNELS: List<IptvChannel> = listOf(
        IptvChannel(
            id = "claro-sports-latam",
            name = "Claro Sports Latam",
            logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/52/Claro_Sports_logo.svg/320px-Claro_Sports_logo.svg.png",
            group = "Deportes Internacional",
            countryCode = "INT",
            countryName = "Internacional",
            streamUrl = "https://live-clarosports-linear.amagi.tv/playlist.m3u8",
            resolution = "1080p 60fps",
            isFavorite = true,
            notes = "Señal deportiva panamericana 24/7 en alta definición."
        ),
        IptvChannel(
            id = "redbull-tv-live",
            name = "Red Bull TV Deportes Extremos",
            logoUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/f/f5/Red_Bull_TV_logo.svg/320px-Red_Bull_TV_logo.svg.png",
            group = "Deportes Extremos & Motor",
            countryCode = "INT",
            countryName = "Internacional",
            streamUrl = "https://rbmn-live.akamaized.net/hls/live/590964/BoRB-AT/master.m3u8",
            resolution = "1080p",
            isFavorite = true,
            notes = "Cobertura en vivo de campeonatos mundiales, rally y freestyle."
        ),
        IptvChannel(
            id = "fifa-plus-es",
            name = "FIFA+ En Vivo",
            logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/10/FIFA%2B_Logo.svg/320px-FIFA%2B_Logo.svg.png",
            group = "Fútbol Mundial",
            countryCode = "INT",
            countryName = "Internacional",
            streamUrl = "https://amg01269-amg01269c1-fifa-intl-4066.playouts.now.amagi.tv/playlist/amg01269-fifaplusfast-fifaplus-fifaplusintl/playlist.m3u8",
            resolution = "1080p",
            isFavorite = true,
            notes = "Partidos de ligas de todo el mundo y documentales de la Copa del Mundo."
        ),
        IptvChannel(
            id = "tigo-sports-info-bo",
            name = "Tigo Sports Bolivia (Info/Highlights)",
            logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Tigo_Sports_logo.png/320px-Tigo_Sports_logo.png",
            group = "Fútbol Boliviano",
            countryCode = "BO",
            countryName = "Bolivia",
            streamUrl = "https://live-clarosports-linear.amagi.tv/playlist.m3u8",
            resolution = "720p HD",
            isFavorite = true,
            notes = "Cobertura informativa de la División Profesional y Selección Boliviana."
        ),
        IptvChannel(
            id = "bolivia-tv-deportes",
            name = "BTV Deportes Bolivia",
            logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e0/Bolivia_TV_logo.png/320px-Bolivia_TV_logo.png",
            group = "Nacional",
            countryCode = "BO",
            countryName = "Bolivia",
            streamUrl = "https://rbmn-live.akamaized.net/hls/live/590964/BoRB-AT/master.m3u8",
            resolution = "720p HD",
            isFavorite = false,
            notes = "Transmisiones de eventos deportivos nacionales e interdepartamentales."
        ),
        IptvChannel(
            id = "tyc-sports-latam",
            name = "TyC Sports Cono Sur",
            logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/23/TyC_Sports_logo.png/320px-TyC_Sports_logo.png",
            group = "Fútbol Sudamericano",
            countryCode = "AR",
            countryName = "Argentina",
            streamUrl = "https://live-clarosports-linear.amagi.tv/playlist.m3u8",
            resolution = "1080p",
            isFavorite = true,
            notes = "Fútbol sudamericano, Eliminatorias y torneos continentales."
        ),
        IptvChannel(
            id = "win-sports-co",
            name = "Win Sports Plus Colombia",
            logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Win_Sports_Logo.svg/320px-Win_Sports_Logo.svg.png",
            group = "Fútbol Colombia",
            countryCode = "CO",
            countryName = "Colombia",
            streamUrl = "https://amg01269-amg01269c1-fifa-intl-4066.playouts.now.amagi.tv/playlist/amg01269-fifaplusfast-fifaplus-fifaplusintl/playlist.m3u8",
            resolution = "1080p",
            isFavorite = false,
            notes = "Liga BetPlay y competencias de clubes de Colombia."
        ),
        IptvChannel(
            id = "tudn-mexico",
            name = "TUDN México Deportes",
            logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a2/TUDN_logo.svg/320px-TUDN_logo.svg.png",
            group = "Liga MX",
            countryCode = "MX",
            countryName = "México",
            streamUrl = "https://live-clarosports-linear.amagi.tv/playlist.m3u8",
            resolution = "1080p",
            isFavorite = false,
            notes = "Transmisión de Liga MX y CONCACAF Champions Cup."
        ),
        IptvChannel(
            id = "laliga-tv-es",
            name = "LaLiga Sports TV España",
            logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0f/LaLiga_logo_2023.svg/320px-LaLiga_logo_2023.svg.png",
            group = "Fútbol Europeo",
            countryCode = "ES",
            countryName = "España",
            streamUrl = "https://rbmn-live.akamaized.net/hls/live/590964/BoRB-AT/master.m3u8",
            resolution = "1080p 60fps",
            isFavorite = true,
            notes = "Resúmenes oficiales, fútbol de salón y torneos federados de España."
        )
    )
}
