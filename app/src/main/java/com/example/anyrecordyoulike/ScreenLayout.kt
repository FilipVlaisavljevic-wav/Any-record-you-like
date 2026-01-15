package com.example.anyrecordyoulike

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.anyrecordyoulike.ui.theme.PrimaryActionCol
import com.example.anyrecordyoulike.ui.theme.PrimaryTextCol
import com.example.anyrecordyoulike.ui.theme.SecondaryTextCol
import com.example.anyrecordyoulike.ui.theme.SurfaceCol

@Composable
fun ScreenLayout(viewModel: RecordModel) {
    var searchBy by remember { mutableStateOf("") }
    var showAdd by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(TabFilter.COLLECTION) }
    var songId by remember { mutableStateOf<String?>(null) }

    val selectedSong = songId?.let { id ->
        viewModel.recOnDisplay.find { it.id == id }
    }
    if (selectedSong != null) {
        RecordDetailView(
            song = selectedSong,
            onDismiss = { songId = null },
            onDelete = {
                viewModel.removeRecord(selectedSong.id)
                songId = null
            },
            onFavorite = {
                viewModel.toggleFavorite(selectedSong)
            },
            onBought = {
                viewModel.bought(selectedSong)
                songId = null
            }
        )
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Search Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = searchBy,
                        onValueChange = {
                            searchBy = it
                            viewModel.filter(it, selectedTab)
                        },
                        placeholder = { Text("Search Collection...", color = SecondaryTextCol) },
                        leadingIcon = { Icon(Icons.Default.Search, null, tint = PrimaryTextCol) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, SurfaceCol, RoundedCornerShape(50))
                    )
                }

                // Tab Row
                TabRow(
                    selectedTab = selectedTab,
                    onTabSelected = {
                        selectedTab = it
                        viewModel.filter(searchBy, it)
                    }
                )

                // List
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (viewModel.recOnDisplay.isEmpty()) {
                        item {
                            Text(
                                text = when(selectedTab) {
                                    TabFilter.FAVORITES -> "No favorites yet."
                                    TabFilter.COLLECTION -> "No records in collection."
                                    TabFilter.WISHLIST -> "No items in wishlist."
                                },
                                color = SecondaryTextCol,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                    items(viewModel.recOnDisplay) { song ->
                        SongBlock(
                            song = song,
                            onClick = { songId = song.id },
                            onDelete = { viewModel.removeRecord(song.id) },
                            onFavorite = { viewModel.toggleFavorite(song) }
                        )
                    }
                }
            }

            FloatingActionButton(
                onClick = { viewModel.sort(sorter = true) },
                containerColor = PrimaryActionCol,
                contentColor = Color.White,
                modifier = Modifier
                    .padding(24.dp)
                    .align(Alignment.BottomStart)
            ) {
                Icon(
                    Icons.AutoMirrored.Rounded.Sort,
                    contentDescription = "Sort",
                    tint = Color.White
                )
            }

            FloatingActionButton(
                onClick = { showAdd = true },
                containerColor = PrimaryActionCol,
                contentColor = Color.White,
                modifier = Modifier
                    .padding(24.dp)
                    .align(Alignment.BottomEnd)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Record")
            }

            if (showAdd) {
                AddRecord(
                    onDismiss = { showAdd = false },
                    onConfirm = { title, artist, url, description, wishlisted ->
                        viewModel.addRecord(title, artist, url, wishlisted, description = description)
                        showAdd = false
                    }
                )
            }
        }
    }
}