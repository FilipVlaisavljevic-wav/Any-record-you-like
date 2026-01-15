package com.example.anyrecordyoulike

import com.google.firebase.firestore.DocumentId

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