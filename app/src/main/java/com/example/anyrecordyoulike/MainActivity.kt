package com.example.anyrecordyoulike
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.anyrecordyoulike.data.model.RecordModel
import com.example.anyrecordyoulike.ui.navigation.Navigation
import com.example.anyrecordyoulike.ui.theme.BackgroundCol

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel by viewModels<RecordModel>()
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = BackgroundCol
            ) {
                val navController = rememberNavController()
                Navigation(viewModel, navController)
            }
        }
    }
}
