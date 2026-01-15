package com.example.anyrecordyoulike
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.anyrecordyoulike.ui.theme.BackgroundCol
import com.example.anyrecordyoulike.ui.theme.PrimaryActionCol
import com.example.anyrecordyoulike.ui.theme.PrimaryTextCol
import com.example.anyrecordyoulike.ui.theme.SecondaryTextCol
import com.example.anyrecordyoulike.ui.theme.SurfaceCol

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel by viewModels<RecordModel>()
        setContent {
            ScreenLayout(viewModel)
        }
    }
}

@Composable
fun SongBlock(song: Song, onClick: () -> Unit, onDelete: () -> Unit, onFavoriteToggle: () -> Unit) {
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
                if (song.imageUrl.isNotEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(song.imageUrl),
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
                    text = song.title,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryTextCol,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = song.artist,
                    color = SecondaryTextCol,
                    style = MaterialTheme.typography.bodyMedium
                )
                if (song.wishlisted) {
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
                IconButton(onClick = { onFavoriteToggle() }) {
                    Icon(
                        imageVector = if (song.favorited) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = PrimaryActionCol
                    )
                }
                IconButton(onClick = { onDelete() }) {
                    Icon(Icons.Default.Delete, "Delete", tint = SecondaryTextCol)
                }
            }
        }
    }
}

@Composable
fun RecordDetailView(
    song: Song,
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    onFavorite: () -> Unit,
    onBought: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCol)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(
                        Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back",
                        tint = PrimaryTextCol
                    )
                }
                Text(
                    text = "Record Details",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryTextCol,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onFavorite) {
                    Icon(
                        imageVector = if (song.favorited) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = PrimaryActionCol
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    // Album Cover
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(SurfaceCol),
                        contentAlignment = Alignment.Center
                    ) {
                        if (song.imageUrl.isNotEmpty()) {
                            Image(
                                painter = rememberAsyncImagePainter(song.imageUrl),
                                contentDescription = "Album Cover",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Icon(
                                Icons.Default.MusicNote,
                                contentDescription = null,
                                tint = PrimaryActionCol,
                                modifier = Modifier.size(80.dp)
                            )
                        }
                    }
                }

                item {
                    // Title and Artist
                    Column {
                        Text(
                            text = song.title,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryTextCol
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = song.artist,
                            style = MaterialTheme.typography.titleLarge,
                            color = SecondaryTextCol
                        )
                    }
                }

                item {
                    // Status Badge
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (song.wishlisted) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(PrimaryActionCol.copy(alpha = 0.1f))
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "WISHLIST",
                                    color = PrimaryActionCol,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF4CAF50).copy(alpha = 0.1f))
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "IN COLLECTION",
                                    color = Color(0xFF4CAF50),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }

                item {
                    // Description
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Description",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryTextCol
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = song.description.ifEmpty { "No description available." },
                                style = MaterialTheme.typography.bodyMedium,
                                color = SecondaryTextCol,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }

            // Bottom Action Buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (song.wishlisted) {
                    Button(
                        onClick = onBought,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Mark as Bought", fontWeight = FontWeight.Bold)
                    }
                }

                Button(
                    onClick = onDelete,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF5252)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Delete Record", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun AddRecord(onDismiss: () -> Unit, onConfirm:(String, String, String, String, Boolean) -> Unit){
    var title by remember { mutableStateOf("") }
    var artist by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var wishlisted by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Vinyl", color = PrimaryTextCol, fontWeight = FontWeight.Bold) },
        text = {
            Column {
                OutlinedTextField(
                    value = artist,
                    onValueChange = { artist = it },
                    label = { Text("Artist") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryActionCol, focusedLabelColor = PrimaryActionCol
                    )
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Album Title") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryActionCol, focusedLabelColor = PrimaryActionCol
                    )
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description (optional)") },
                    minLines = 2,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryActionCol, focusedLabelColor = PrimaryActionCol
                    )
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = url,
                    onValueChange = { url = it },
                    label = { Text("Image URL") },
                    placeholder = { Text("https://...") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryActionCol, focusedLabelColor = PrimaryActionCol
                    )
                )
                Spacer(Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = wishlisted,
                        onCheckedChange = { wishlisted = it },
                        colors = CheckboxDefaults.colors(checkedColor = PrimaryActionCol)
                    )
                    Text("Add to Wishlist", color = SecondaryTextCol)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(title, artist, url, description, wishlisted) },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryActionCol)
            ) { Text("Add", color = Color.White) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel", color = SecondaryTextCol) }
        },
        containerColor = BackgroundCol
    )
}

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
                            onFavoriteToggle = { viewModel.toggleFavorite(song) }
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
                        viewModel.addRecord(title, artist, url, wishlisted)
                        showAdd = false
                    }
                )
            }
        }
    }
}