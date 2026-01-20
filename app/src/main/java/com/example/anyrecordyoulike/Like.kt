package com.example.anyrecordyoulike

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.example.anyrecordyoulike.data.Vinyl
import com.example.anyrecordyoulike.ui.theme.PrimaryActionCol

@Composable
fun Like(onFavorite: () -> Unit, vinyl: Vinyl, modifier: androidx.compose.ui.Modifier){
    IconButton(onClick = onFavorite, modifier = modifier) {
        Icon(
            imageVector = if (vinyl.favorited) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Favorite",
            tint = PrimaryActionCol
        )
    }
}