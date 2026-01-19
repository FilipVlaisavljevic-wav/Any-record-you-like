package com.example.anyrecordyoulike.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.anyrecordyoulike.ui.theme.BackgroundCol
import com.example.anyrecordyoulike.ui.theme.PrimaryActionCol
import com.example.anyrecordyoulike.ui.theme.PrimaryTextCol
import com.example.anyrecordyoulike.ui.theme.SecondaryTextCol

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