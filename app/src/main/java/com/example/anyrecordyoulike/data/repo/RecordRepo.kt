package com.example.anyrecordyoulike.data.repo

import com.example.anyrecordyoulike.data.Vinyl


import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore



enum class TabFilter {
    FAVORITES, COLLECTION, WISHLIST
}

class RecordRepo {

     val db = Firebase.firestore

    fun listenToRecords(onUpdate: (List<Vinyl>) -> Unit) {
        db.collection("Records").addSnapshotListener { snapshot, e ->
            if (e != null || snapshot == null) return@addSnapshotListener

            val records = snapshot.documents.mapNotNull { doc ->
                val vinyl = doc.toObject(Vinyl::class.java)
                vinyl?.id = doc.id
                vinyl
            }

            onUpdate(records)
        }
    }

    fun addRecord(vinyl: Vinyl) {
        db.collection("Records").add(vinyl)
    }

    fun removeRecord(id: String) {
        db.collection("Records").document(id).delete()
    }

    fun toggleFavorite(vinyl: Vinyl) {
        db.collection("Records").document(vinyl.id)
            .update("favorited", !vinyl.favorited)
    }

    fun bought(vinyl: Vinyl) {
        db.collection("Records").document(vinyl.id)
            .update("wishlisted", false)
    }
}