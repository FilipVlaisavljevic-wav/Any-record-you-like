package com.example.anyrecordyoulike.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.anyrecordyoulike.data.Vinyl
import com.example.anyrecordyoulike.ui.theme.BackgroundCol
import com.example.anyrecordyoulike.ui.theme.PrimaryActionCol
import com.example.anyrecordyoulike.ui.theme.PrimaryTextCol
import com.example.anyrecordyoulike.ui.theme.SecondaryTextCol

@Composable
fun EditDetailView(
    vinyl: Vinyl,
    onDismiss: () -> Unit,
    onSave: (Vinyl) -> Unit
) {
    var title by remember { mutableStateOf(vinyl.title) }
    var artist by remember { mutableStateOf(vinyl.artist) }
    var description by remember { mutableStateOf(vinyl.description) }
    var imageUrl by remember { mutableStateOf(vinyl.imageUrl) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCol)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            IconButton(
                onClick = onDismiss,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = PrimaryTextCol
                )
            }

            Text(
                text = "Edit Record",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryTextCol,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Title",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = PrimaryTextCol,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = { Text("Enter album title") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryActionCol,
                unfocusedBorderColor = SecondaryTextCol,
                focusedTextColor = PrimaryTextCol,
                unfocusedTextColor = PrimaryTextCol
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = "Artist",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = PrimaryTextCol,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = artist,
            onValueChange = { artist = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = { Text("Enter artist name") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryActionCol,
                unfocusedBorderColor = SecondaryTextCol,
                focusedTextColor = PrimaryTextCol,
                unfocusedTextColor = PrimaryTextCol
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Image URL",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = PrimaryTextCol,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = { Text("Enter image URL (optional)") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryActionCol,
                unfocusedBorderColor = SecondaryTextCol,
                focusedTextColor = PrimaryTextCol,
                unfocusedTextColor = PrimaryTextCol
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Description",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = PrimaryTextCol,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(150.dp),
            placeholder = { Text("Enter description (optional)") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryActionCol,
                unfocusedBorderColor = SecondaryTextCol,
                focusedTextColor = PrimaryTextCol,
                unfocusedTextColor = PrimaryTextCol
            ),
            shape = RoundedCornerShape(8.dp),
            maxLines = 6
        )

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val updatedVinyl = vinyl.copy(
                    title = title,
                    artist = artist,
                    description = description,
                    imageUrl = imageUrl
                )
                onSave(updatedVinyl)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryActionCol
            ),
            shape = RoundedCornerShape(8.dp),
            enabled = title.isNotBlank() && artist.isNotBlank()
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Save Changes",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}