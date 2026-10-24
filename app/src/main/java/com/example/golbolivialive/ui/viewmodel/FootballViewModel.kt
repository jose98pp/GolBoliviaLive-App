package com.example.golbolivialive.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golbolivialive.data.datasource.BolivianFootballData
import com.example.golbolivialive.data.model.*
import com.example.golbolivialive.data.repository.FootballRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FootballViewModel(
    private val repository: FootballRepository = FootballRepository()
) : ViewModel() {

    val allMatches: StateFlow<List<LiveMatch>> = repository.allMatches
    val liveMatch: StateFlow<LiveMatch> = repository.liveMatch
    val isSyncingFirestore: StateFlow<Boolean> = repository.isSyncingFirestore
    val chatMessages: StateFlow<List<ChatMessage>> = repository.chatMessages
    val livePoll: StateFlow<LivePoll> = repository.livePoll
    val iptvChannels: StateFlow<List<IptvChannel>> = repository.iptvChannels
    val activeIptvChannel: StateFlow<IptvChannel?> = repository.activeIptvChannel
    val favoriteChannelIds: StateFlow<Set<String>> = repository.favoriteChannelIds

    init {
        syncFirestore()
    }

    fun syncFirestore() {
        viewModelScope.launch {
            repository.syncFirestore()
        }
    }

    fun selectMatch(match: LiveMatch) {
        repository.selectMatch(match)
    }

    private val _selectedIptvCountry = MutableStateFlow("ALL")
    val selectedIptvCountry: StateFlow<String> = _selectedIptvCountry.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedClubId = MutableStateFlow("bolivar")
    val selectedClubId: StateFlow<String> = _selectedClubId.asStateFlow()

    private val _isVipModalOpen = MutableStateFlow(false)
    val isVipModalOpen: StateFlow<Boolean> = _isVipModalOpen.asStateFlow()

    private val _isAudienceModalOpen = MutableStateFlow(false)
    val isAudienceModalOpen: StateFlow<Boolean> = _isAudienceModalOpen.asStateFlow()

    private val _isReloadingSignal = MutableStateFlow(false)
    val isReloadingSignal: StateFlow<Boolean> = _isReloadingSignal.asStateFlow()

    // Filtered IPTV channels based on country and search
    val filteredChannels: StateFlow<List<IptvChannel>> = combine(
        repository.iptvChannels,
        _selectedIptvCountry,
        _searchQuery,
        repository.favoriteChannelIds
    ) { channels, country, query, favs ->
        channels.map { it.copy(isFavorite = favs.contains(it.id)) }
            .filter { ch ->
                val matchesCountry = country == "ALL" || ch.countryCode == country
                val matchesQuery = query.isBlank() || ch.name.contains(query, ignoreCase = true) ||
                        ch.group.contains(query, ignoreCase = true)
                matchesCountry && matchesQuery
            }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setIptvCountry(countryCode: String) {
        _selectedIptvCountry.value = countryCode
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectClub(clubId: String) {
        _selectedClubId.value = clubId
    }

    fun switchMatchSignal(signal: SignalSource) {
        repository.switchMatchSignal(signal)
    }

    fun reloadSignal() {
        viewModelScope.launch {
            _isReloadingSignal.value = true
            repository.reloadSignal()
            delay(500)
            _isReloadingSignal.value = false
        }
    }

    fun sendChatMessage(sender: String, clubId: String, text: String, superChatAmount: Int? = null) {
        repository.sendChatMessage(sender, clubId, text, superChatAmount)
    }

    fun votePoll(optionId: String) {
        repository.votePoll(optionId)
    }

    fun toggleFavorite(channelId: String) {
        repository.toggleFavorite(channelId)
    }

    fun playIptvChannel(channel: IptvChannel) {
        repository.selectIptvChannel(channel)
    }

    fun closeIptvChannel() {
        repository.selectIptvChannel(null)
    }

    fun openVipModal() { _isVipModalOpen.value = true }
    fun closeVipModal() { _isVipModalOpen.value = false }

    fun openAudienceModal() { _isAudienceModalOpen.value = true }
    fun closeAudienceModal() { _isAudienceModalOpen.value = false }
}
