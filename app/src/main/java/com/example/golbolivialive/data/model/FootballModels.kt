package com.example.golbolivialive.data.model

data class Club(
    val id: String = "",
    val name: String,
    val shortName: String,
    val city: String,
    val primaryColorHex: String,
    val secondaryColorHex: String,
    val badgeEmoji: String,
    val stadium: String,
    val altitudeMeters: Int,
    val league: String = "Bolivia (División Profesional)",
    val country: String = "Bolivia"
)

enum class EventType {
    GOAL, YELLOW_CARD, RED_CARD, SUBSTITUTION, VAR, COMMENTARY, CORNER
}

data class MatchEvent(
    val id: String,
    val minute: Int,
    val type: EventType,
    val clubId: String,
    val player: String? = null,
    val description: String,
    val scoreAfter: String? = null
)

data class MatchStats(
    val possessionHome: Int = 52,
    val possessionAway: Int = 48,
    val shotsHome: Int = 14,
    val shotsAway: Int = 9,
    val shotsOnTargetHome: Int = 6,
    val shotsOnTargetAway: Int = 3,
    val cornersHome: Int = 7,
    val cornersAway: Int = 4,
    val foulsHome: Int = 11,
    val foulsAway: Int = 15,
    val yellowCardsHome: Int = 2,
    val yellowCardsAway: Int = 3,
    val redCardsHome: Int = 0,
    val redCardsAway: Int = 0,
    val passAccuracyHome: Int = 84,
    val passAccuracyAway: Int = 79
)

data class ChatMessage(
    val id: String,
    val sender: String,
    val clubId: String,
    val text: String,
    val timestamp: String,
    val isVip: Boolean = false,
    val isModerator: Boolean = false,
    val isOfficialRelator: Boolean = false,
    val superChatAmount: Int? = null
)

data class PollOption(
    val id: String,
    val text: String,
    val votes: Int
)

data class LivePoll(
    val id: String,
    val question: String,
    val options: List<PollOption>,
    val totalVotes: Int,
    val userVotedId: String? = null
)

data class ExclusiveContent(
    val id: String,
    val title: String,
    val clubId: String,
    val category: String, // Camerino, Entrenamiento, Prensa, Entrevista
    val duration: String,
    val views: Int,
    val isLocked: Boolean,
    val description: String
)

data class StandingTeam(
    val position: Int,
    val clubId: String,
    val name: String,
    val played: Int,
    val won: Int,
    val drawn: Int,
    val lost: Int,
    val goalDiff: Int,
    val points: Int
)

enum class SignalSource(val label: String, val badge: String) {
    PRINCIPAL_HD("Señal Principal HD", "1080p 60fps"),
    BACKUP("Señal de Respaldo", "720p HD"),
    RADIO_DEMO("Radio / Demo Oficial", "360p Móvil")
}

data class LiveMatch(
    val id: String,
    val title: String,
    val tournamentName: String,
    val homeClub: Club,
    val awayClub: Club,
    val homeScore: Int,
    val awayScore: Int,
    val matchMinute: Int,
    val period: String, // "1T", "Descanso", "2T", "Finalizado"
    val isLive: Boolean,
    val stadiumName: String,
    val altitudeMeters: Int,
    val officialAnnouncement: String = "",
    val activeSignal: SignalSource = SignalSource.PRINCIPAL_HD,
    val hlsUrlPrincipal: String,
    val hlsUrlBackup: String,
    val hlsUrlDemo: String,
    val stats: MatchStats = MatchStats(),
    val events: List<MatchEvent> = emptyList(),
    val connectedFans: Int = 1248
)

data class DonationInfo(
    val bankName: String = "Banco Unión / BNB",
    val accountHolder: String = "Transmisión Oficial GolBolivia",
    val qrCodeNotice: String = "Compatible con QR Simple y Tigo Money Bolivia",
    val tigoNumber: String = "+591 78901234"
)
