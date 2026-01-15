package com.example.anyrecordyoulike

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.example.anyrecordyoulike.ui.theme.PrimaryActionCol

@Composable
fun Like(onFavorite: () -> Unit, song: Song){
    IconButton(onClick = onFavorite) {
        Icon(
            imageVector = if (song.favorited) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Favorite",
            tint = PrimaryActionCol
        )
    }
}