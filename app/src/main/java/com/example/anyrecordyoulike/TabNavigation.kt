package com.example.anyrecordyoulike

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anyrecordyoulike.ui.theme.PrimaryActionCol
import com.example.anyrecordyoulike.ui.theme.SecondaryTextCol
import com.example.anyrecordyoulike.ui.theme.SurfaceCol

@Composable
fun TabRow(selectedTab: TabFilter, onTabSelected: (TabFilter) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TabButton(
            text = "Favorites",
            selected = selectedTab == TabFilter.FAVORITES,
            onClick = { onTabSelected(TabFilter.FAVORITES) },
            modifier = Modifier.weight(1f)
        )
        TabButton(
            text = "Collection",
            selected = selectedTab == TabFilter.COLLECTION,
            onClick = { onTabSelected(TabFilter.COLLECTION) },
            modifier = Modifier.weight(1f)
        )
        TabButton(
            text = "Wishlist",
            selected = selectedTab == TabFilter.WISHLIST,
            onClick = { onTabSelected(TabFilter.WISHLIST) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun TabButton(text: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (selected) PrimaryActionCol else Color.White)
            .border(
                width = 1.dp,
                color = if (selected) PrimaryActionCol else SurfaceCol,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) Color.White else SecondaryTextCol,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}