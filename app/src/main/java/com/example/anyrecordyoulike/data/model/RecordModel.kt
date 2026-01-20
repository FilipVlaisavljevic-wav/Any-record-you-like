package com.example.anyrecordyoulike.data.model

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anyrecordyoulike.data.Vinyl
import com.example.anyrecordyoulike.data.repo.RecordRepo
import com.example.anyrecordyoulike.data.repo.TabFilter
import kotlinx.coroutines.launch

class RecordModel(
    private val repository: RecordRepo = RecordRepo()
) : ViewModel() {

    private var records = listOf<Vinyl>()
    val recOnDisplay = mutableStateListOf<Vinyl>()

    var selectedTab = TabFilter.COLLECTION
        private set

    var selectedFilter = ""
        private set

    init {
        repository.listenToRecords { fetched ->
            records = fetched
            filter(selectedFilter, selectedTab)
        }
    }

    fun addRecord(
        title: String,
        artist: String,
        imageUrl: String,
        wishlisted: Boolean,
        description: String
    ) {
        repository.addRecord(
            Vinyl(
                title = title,
                artist = artist,
                imageUrl = imageUrl,
                wishlisted = wishlisted,
                description = description
            )
        )
    }

    fun removeRecord(id: String) =
        repository.removeRecord(id)

    fun toggleFavorite(vinyl: Vinyl) =
        repository.toggleFavorite(vinyl)

    fun bought(vinyl: Vinyl) =
        repository.bought(vinyl)

    fun filter(filterText: String, tab: TabFilter) {
        selectedFilter = filterText
        selectedTab = tab

        var filtered = when (tab) {
            TabFilter.FAVORITES -> records.filter { it.favorited }
            TabFilter.COLLECTION -> records.filter { !it.wishlisted }
            TabFilter.WISHLIST -> records.filter { it.wishlisted }
        }

        if (filterText.isNotBlank()) {
            val query = filterText.lowercase()
            filtered = filtered.filter {
                it.title.lowercase().contains(query) ||
                        it.artist.lowercase().contains(query)
            }
        }

        recOnDisplay.clear()
        recOnDisplay.addAll(filtered)
    }

    fun sort(sorter: Boolean) {
        val sorted = if (sorter) {
            recOnDisplay.sortedBy { it.artist }
        } else {
            recOnDisplay.sortedBy { it.title }
        }

        recOnDisplay.clear()
        recOnDisplay.addAll(sorted)
    }
}
