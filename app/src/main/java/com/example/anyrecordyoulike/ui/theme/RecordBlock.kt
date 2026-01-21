package com.example.anyrecordyoulike.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.anyrecordyoulike.ui.Like
import com.example.anyrecordyoulike.data.Vinyl

@Composable
fun RecordBlock(record: Vinyl, onClick: () -> Unit, onDelete: () -> Unit, onFavorite: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceCol),
                contentAlignment = Alignment.Center
            ) {
                if (record.imageUrl.isNotEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(record.imageUrl),
                        contentDescription = "Cover",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(Icons.Default.MusicNote, null, tint = PrimaryActionCol)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = record.title,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryTextCol,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = record.artist,
                    color = SecondaryTextCol,
                    style = MaterialTheme.typography.bodyMedium
                )
                if (record.wishlisted) {
                    Text(
                        "WISH LIST",
                        color = PrimaryActionCol,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Like(onFavorite = onFavorite, vinyl = record, modifier = Modifier)
            }
        }
    }
}
