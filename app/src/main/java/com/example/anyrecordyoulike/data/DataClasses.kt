package com.example.anyrecordyoulike.data

import com.google.firebase.firestore.DocumentId

data class Vinyl(
    @DocumentId
    var id: String = "",
    val title: String = "",
    val artist: String = "",
    val description: String = "",
    val wishlisted: Boolean = false,
    val favorited: Boolean = false,
    val imageUrl: String = ""
)