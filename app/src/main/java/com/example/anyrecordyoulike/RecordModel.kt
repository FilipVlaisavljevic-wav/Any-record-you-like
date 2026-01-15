package com.example.anyrecordyoulike

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.firestore

data class Song(
    @DocumentId
    var id: String = "",
    val title: String = "",
    val artist: String = "",
    val description: String = "",
    val wishlisted: Boolean = false,
    val favorited: Boolean = false,
    val imageUrl: String = ""
)

enum class TabFilter {
    FAVORITES, COLLECTION, WISHLIST
}

class RecordModel : ViewModel() {
    val db = Firebase.firestore
    var records = listOf<Song>()
    var recOnDisplay = mutableStateListOf<Song>()
    var selectedTab = TabFilter.COLLECTION
    var selectedFilter = ""
    init {
        db.collection("Records").addSnapshotListener { snapshot, e ->
            if (e != null || snapshot == null) return@addSnapshotListener

            val fetched = snapshot.documents.mapNotNull { doc ->
                val song = doc.toObject(Song::class.java)
                song?.id = doc.id
                song
            }

            records = fetched
            filter(selectedFilter, selectedTab)
        }
    }
    fun bought(song : Song){
        db.collection("Records").document(song.id).update("wishlisted", false)
    }
    fun addRecord(title: String, artist: String, imageUrl: String, wishlisted: Boolean) {
        val newSong = Song(
            title = title,
            artist = artist,
            imageUrl = imageUrl,
            wishlisted = wishlisted
        )
        db.collection("Records").add(newSong)
    }

    fun removeRecord(id: String) {
        db.collection("Records").document(id).delete()
    }

    fun toggleFavorite(song: Song) {
        db.collection("Records").document(song.id)
            .update("favorited", !song.favorited)
    }

    fun filter(filterText: String, tab: TabFilter) {
        selectedFilter = filterText
        selectedTab = tab

        var filtered = when (tab) {
            TabFilter.FAVORITES -> records.filter { it.favorited }
            TabFilter.COLLECTION -> records.filter { !it.wishlisted }
            TabFilter.WISHLIST -> records.filter { it.wishlisted }
        }

        if (filterText.isNotBlank()) {
            filtered = filtered.filter {
                it.title.lowercase().contains(filterText.lowercase()) ||
                        it.artist.lowercase().contains(filterText.lowercase())
            }
        }

        recOnDisplay.clear()
        recOnDisplay.addAll(filtered)
    }
    fun sort(sorter : Boolean){
        val sorted = if(sorter){
            recOnDisplay.sortedBy { it.artist }
        }
        else{
            recOnDisplay.sortedBy { it.title }
        }
        recOnDisplay.clear()
        recOnDisplay.addAll(sorted)
    }
}