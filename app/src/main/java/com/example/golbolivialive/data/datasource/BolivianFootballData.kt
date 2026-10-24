package com.example.golbolivialive.data.datasource

import com.example.golbolivialive.data.model.*

object BolivianFootballData {

    val CLUBS: Map<String, Club> = mapOf(
        "bolivar" to Club(
            id = "bolivar",
            name = "Club Bolívar",
            shortName = "Bolívar",
            city = "La Paz",
            primaryColorHex = "#0284c7",
            secondaryColorHex = "#ffffff",
            badgeEmoji = "⚡",
            stadium = "Estadio Hernando Siles",
            altitudeMeters = 3637
        ),
        "strongest" to Club(
            id = "strongest",
            name = "The Strongest",
            shortName = "Strongest",
            city = "La Paz",
            primaryColorHex = "#eab308",
            secondaryColorHex = "#0f172a",
            badgeEmoji = "🐯",
            stadium = "Estadio Rafael Mendoza / Siles",
            altitudeMeters = 3637
        ),
        "wilstermann" to Club(
            id = "wilstermann",
            name = "C.A. Jorge Wilstermann",
            shortName = "Wilstermann",
            city = "Cochabamba",
            primaryColorHex = "#dc2626",
            secondaryColorHex = "#1e3a8a",
            badgeEmoji = "✈️",
            stadium = "Estadio Félix Capriles",
            altitudeMeters = 2558
        ),
        "oriente" to Club(
            id = "oriente",
            name = "Oriente Petrolero",
            shortName = "Oriente",
            city = "Santa Cruz",
            primaryColorHex = "#16a34a",
            secondaryColorHex = "#ffffff",
            badgeEmoji = "🛢️",
            stadium = "Estadio Ramón Tahuichi Aguilera",
            altitudeMeters = 416
        ),
        "blooming" to Club(
            id = "blooming",
            name = "Club Blooming",
            shortName = "Blooming",
            city = "Santa Cruz",
            primaryColorHex = "#38bdf8",
            secondaryColorHex = "#1e3a8a",
            badgeEmoji = "🦅",
            stadium = "Estadio Ramón Tahuichi Aguilera",
            altitudeMeters = 416
        ),
        "always" to Club(
            id = "always",
            name = "Always Ready",
            shortName = "Always Ready",
            city = "El Alto",
            primaryColorHex = "#e11d48",
            secondaryColorHex = "#ffffff",
            badgeEmoji = "🔴",
            stadium = "Estadio Municipal de Villa Ingenio",
            altitudeMeters = 4083
        ),
        "nacional" to Club(
            id = "nacional",
            name = "Nacional Potosí",
            shortName = "Nacional Potosí",
            city = "Potosí",
            primaryColorHex = "#991b1b",
            secondaryColorHex = "#ffffff",
            badgeEmoji = "🎸",
            stadium = "Estadio Víctor Agustín Ugarte",
            altitudeMeters = 3900
        ),
        "aurora" to Club(
            id = "aurora",
            name = "Club Deportivo Aurora",
            shortName = "Aurora",
            city = "Cochabamba",
            primaryColorHex = "#38bdf8",
            secondaryColorHex = "#ffffff",
            badgeEmoji = "⭐",
            stadium = "Estadio Félix Capriles",
            altitudeMeters = 2558
        ),
        "san_antonio" to Club(
            id = "san_antonio",
            name = "San Antonio Bulo Bulo",
            shortName = "San Antonio",
            city = "Entre Ríos",
            primaryColorHex = "#059669",
            secondaryColorHex = "#ffffff",
            badgeEmoji = "🌴",
            stadium = "Estadio Carlos Villegas",
            altitudeMeters = 250
        ),
        "tomayapo" to Club(
            id = "tomayapo",
            name = "Real Tomayapo",
            shortName = "Real Tomayapo",
            city = "Tarija",
            primaryColorHex = "#0284c7",
            secondaryColorHex = "#16a34a",
            badgeEmoji = "🍇",
            stadium = "Estadio IV Centenario",
            altitudeMeters = 1854
        ),
        "guabira" to Club(
            id = "guabira",
            name = "Club Deportivo Guabirá",
            shortName = "Guabirá",
            city = "Montero",
            primaryColorHex = "#dc2626",
            secondaryColorHex = "#ffffff",
            badgeEmoji = "🎋",
            stadium = "Estadio Gilberto Parada",
            altitudeMeters = 298
        ),
        "independiente" to Club(
            id = "independiente",
            name = "Independiente Petrolero",
            shortName = "Independiente",
            city = "Sucre",
            primaryColorHex = "#dc2626",
            secondaryColorHex = "#ffffff",
            badgeEmoji = "🏛️",
            stadium = "Estadio Olímpico Patria",
            altitudeMeters = 2810
        )
    )

    fun getDefaultLiveMatch(): LiveMatch {
        val home = CLUBS["bolivar"]!!
        val away = CLUBS["strongest"]!!

        return LiveMatch(
            id = "clásico-paceño-2026",
            title = "Clásico Paceño: Bolívar vs The Strongest",
            tournamentName = "División Profesional de Bolivia — Torneo Clausura",
            homeClub = home,
            awayClub = away,
            homeScore = 2,
            awayScore = 1,
            matchMinute = 78,
            period = "2T",
            isLive = true,
            stadiumName = home.stadium,
            altitudeMeters = home.altitudeMeters,
            officialAnnouncement = "⚡ Gran ambientazo en el Siles con más de 38,000 espectadores. Gol de Bolívar al min 72.",
            activeSignal = SignalSource.PRINCIPAL_HD,
            // Highly reliable live HLS streams
            hlsUrlPrincipal = "https://live-clarosports-linear.amagi.tv/playlist.m3u8",
            hlsUrlBackup = "https://rbmn-live.akamaized.net/hls/live/590964/BoRB-AT/master.m3u8",
            hlsUrlDemo = "https://amg01269-amg01269c1-fifa-intl-4066.playouts.now.amagi.tv/playlist/amg01269-fifaplusfast-fifaplus-fifaplusintl/playlist.m3u8",
            stats = MatchStats(
                possessionHome = 54,
                possessionAway = 46,
                shotsHome = 15,
                shotsAway = 11,
                shotsOnTargetHome = 7,
                shotsOnTargetAway = 4,
                cornersHome = 6,
                cornersAway = 3,
                foulsHome = 12,
                foulsAway = 14,
                yellowCardsHome = 2,
                yellowCardsAway = 3,
                redCardsHome = 0,
                redCardsAway = 0,
                passAccuracyHome = 85,
                passAccuracyAway = 81
            ),
            events = listOf(
                MatchEvent("ev1", 18, EventType.GOAL, "strongest", "Enrique Triverio", "¡GOL DE THE STRONGEST! Cabezazo certero tras centro desde la derecha.", "0 - 1"),
                MatchEvent("ev2", 34, EventType.YELLOW_CARD, "bolivar", "Leonel Justiniano", "Falta táctica en el mediocampo."),
                MatchEvent("ev3", 42, EventType.GOAL, "bolivar", "Ramiro Vaca", "¡GOLAZO DE BOLÍVAR! Tiro libre magistral al ángulo superior derecho.", "1 - 1"),
                MatchEvent("ev4", 59, EventType.VAR, "strongest", null, "Revisión VAR por posible penal. Decisión: Tiro de esquina."),
                MatchEvent("ev5", 65, EventType.SUBSTITUTION, "bolivar", "Pato Rodríguez", "Ingresa Patricio Rodríguez por Bruno Sávio."),
                MatchEvent("ev6", 72, EventType.GOAL, "bolivar", "Carmelo Algarañaz", "¡GOOOOL DE BOLÍVAR! Definición cruzada para desatar la fiesta celeste.", "2 - 1")
            ),
            connectedFans = 2415
        )
    }

    fun getRoundMatches(): List<LiveMatch> {
        val defaultMatch = getDefaultLiveMatch()

        val match2 = LiveMatch(
            id = "blooming_vs_oriente",
            title = "Clásico Cruceño: Blooming vs Oriente Petrolero",
            tournamentName = "División Profesional — Torneo Clausura",
            homeClub = CLUBS["blooming"]!!,
            awayClub = CLUBS["oriente"]!!,
            homeScore = 1,
            awayScore = 1,
            matchMinute = 38,
            period = "1T",
            isLive = true,
            stadiumName = "Estadio Ramón Tahuichi Aguilera",
            altitudeMeters = 416,
            officialAnnouncement = "🔥 Intenso clásico en Santa Cruz con estadio lleno y goles tempraneros.",
            activeSignal = SignalSource.PRINCIPAL_HD,
            hlsUrlPrincipal = "https://live-clarosports-linear.amagi.tv/playlist.m3u8",
            hlsUrlBackup = "https://rbmn-live.akamaized.net/hls/live/590964/BoRB-AT/master.m3u8",
            hlsUrlDemo = "https://amg01269-amg01269c1-fifa-intl-4066.playouts.now.amagi.tv/playlist/amg01269-fifaplusfast-fifaplus-fifaplusintl/playlist.m3u8",
            connectedFans = 1890
        )

        val match3 = LiveMatch(
            id = "wilstermann_vs_aurora",
            title = "Clásico Cochabambino: Wilstermann vs Aurora",
            tournamentName = "División Profesional — Torneo Clausura",
            homeClub = CLUBS["wilstermann"]!!,
            awayClub = CLUBS["aurora"]!!,
            homeScore = 0,
            awayScore = 0,
            matchMinute = 15,
            period = "1T",
            isLive = true,
            stadiumName = "Estadio Félix Capriles",
            altitudeMeters = 2558,
            officialAnnouncement = "⚔️ Wilstermann y Aurora disputan el dominio del Félix Capriles.",
            activeSignal = SignalSource.PRINCIPAL_HD,
            hlsUrlPrincipal = "https://live-clarosports-linear.amagi.tv/playlist.m3u8",
            hlsUrlBackup = "https://rbmn-live.akamaized.net/hls/live/590964/BoRB-AT/master.m3u8",
            hlsUrlDemo = "https://amg01269-amg01269c1-fifa-intl-4066.playouts.now.amagi.tv/playlist/amg01269-fifaplusfast-fifaplus-fifaplusintl/playlist.m3u8",
            connectedFans = 1430
        )

        val match4 = LiveMatch(
            id = "always_vs_nacional",
            title = "Duelo de la Altura: Always Ready vs Nacional Potosí",
            tournamentName = "División Profesional — Fecha 22",
            homeClub = CLUBS["always"]!!,
            awayClub = CLUBS["nacional"]!!,
            homeScore = 0,
            awayScore = 0,
            matchMinute = 0,
            period = "Hoy 19:30",
            isLive = false,
            stadiumName = "Estadio Municipal de Villa Ingenio (El Alto)",
            altitudeMeters = 4083,
            officialAnnouncement = "🏔️ Fútbol a más de 4,000 metros de altura en Villa Ingenio.",
            activeSignal = SignalSource.PRINCIPAL_HD,
            hlsUrlPrincipal = "https://live-clarosports-linear.amagi.tv/playlist.m3u8",
            hlsUrlBackup = "https://rbmn-live.akamaized.net/hls/live/590964/BoRB-AT/master.m3u8",
            hlsUrlDemo = "https://amg01269-amg01269c1-fifa-intl-4066.playouts.now.amagi.tv/playlist/amg01269-fifaplusfast-fifaplus-fifaplusintl/playlist.m3u8",
            connectedFans = 870
        )

        val match5 = LiveMatch(
            id = "san_antonio_vs_tomayapo",
            title = "San Antonio Bulo Bulo vs Real Tomayapo",
            tournamentName = "División Profesional — Fecha 22",
            homeClub = CLUBS["san_antonio"]!!,
            awayClub = CLUBS["tomayapo"]!!,
            homeScore = 0,
            awayScore = 0,
            matchMinute = 0,
            period = "Mañana 15:00",
            isLive = false,
            stadiumName = "Estadio Carlos Villegas (Entre Ríos)",
            altitudeMeters = 250,
            officialAnnouncement = "🌴 San Antonio busca afianzarse en el trópico cochabambino.",
            activeSignal = SignalSource.PRINCIPAL_HD,
            hlsUrlPrincipal = "https://live-clarosports-linear.amagi.tv/playlist.m3u8",
            hlsUrlBackup = "https://rbmn-live.akamaized.net/hls/live/590964/BoRB-AT/master.m3u8",
            hlsUrlDemo = "https://amg01269-amg01269c1-fifa-intl-4066.playouts.now.amagi.tv/playlist/amg01269-fifaplusfast-fifaplus-fifaplusintl/playlist.m3u8",
            connectedFans = 620
        )

        return listOf(defaultMatch, match2, match3, match4, match5)
    }

    val INITIAL_CHAT_MESSAGES = listOf(
        ChatMessage("m1", "Carlos_LaPaz", "bolivar", "¡Qué golazo de Ramiro Vaca por favor! De tiro libre es letal 🔥", "18:42", isVip = true),
        ChatMessage("m2", "TigreDeCorazon", "strongest", "Todavía falta un montón, vamos The Strongest con garra 🐯💪", "18:43"),
        ChatMessage("m3", "Relator_Oficial", "bolivar", "⚽ Minuto 78: Se calienta el partido, tiro de esquina para el Tigre.", "18:44", isOfficialRelator = true),
        ChatMessage("m4", "Willy_Cochala", "wilstermann", "Saludos desde Cochabamba mirando el clásico paceño en GolBolivia 🇧🇴", "18:44"),
        ChatMessage("m5", "CelesteDePuraCepa", "bolivar", "¡Grande Bolívar! A mantener el resultado muchachos ⚡⚡", "18:45", superChatAmount = 20)
    )

    val DEFAULT_POLL = LivePoll(
        id = "poll_1",
        question = "¿Quién se lleva el Clásico Paceño en el Siles?",
        options = listOf(
            PollOption("o1", "Club Bolívar ⚡", 182),
            PollOption("o2", "The Strongest 🐯", 124),
            PollOption("o3", "Empate 🤝", 38)
        ),
        totalVotes = 344
    )

    val STANDINGS = listOf(
        StandingTeam(1, "bolivar", "Bolívar", 14, 10, 2, 2, 19, 32),
        StandingTeam(2, "strongest", "The Strongest", 14, 9, 3, 2, 15, 30),
        StandingTeam(3, "always", "Always Ready", 14, 8, 3, 3, 11, 27),
        StandingTeam(4, "aurora", "Aurora", 14, 7, 4, 3, 8, 25),
        StandingTeam(5, "wilstermann", "Wilstermann", 14, 6, 4, 4, 5, 22),
        StandingTeam(6, "blooming", "Blooming", 14, 6, 2, 6, 1, 20),
        StandingTeam(7, "oriente", "Oriente Petrolero", 14, 5, 4, 5, -2, 19),
        StandingTeam(8, "nacional", "Nacional Potosí", 14, 5, 3, 6, 0, 18),
        StandingTeam(9, "san_antonio", "San Antonio BB", 14, 5, 2, 7, -3, 17),
        StandingTeam(10, "tomayapo", "Real Tomayapo", 14, 4, 3, 7, -7, 15)
    )

    val EXCLUSIVE_CONTENT = listOf(
        ExclusiveContent("ex1", "Intimidad del Camerino Celeste previo al Clásico", "bolivar", "Camerino", "08:14", 4200, false, "Charla técnica motivacional de los jugadores antes de pisar el Siles."),
        ExclusiveContent("ex2", "Entrenamiento táctico a 3,637m de altura", "strongest", "Entrenamiento", "12:30", 3100, false, "Preparación física del Tigre en Achumani afinando jugadas a balón parado."),
        ExclusiveContent("ex3", "Conferencia de Prensa post-victoria en Villa Ingenio", "always", "Prensa", "15:45", 2800, false, "Declaraciones del cuerpo técnico tras la goleada a 4,083m sobre el nivel del mar."),
        ExclusiveContent("ex4", "Mano a mano con el goleador histórico del Rojo", "wilstermann", "Entrevista", "18:20", 3500, true, "Entrevista exclusiva para suscriptores VIP y miembros del club.")
    )
}
