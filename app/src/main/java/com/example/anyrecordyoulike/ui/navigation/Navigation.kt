package com.example.anyrecordyoulike.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.anyrecordyoulike.data.model.RecordModel
import com.example.anyrecordyoulike.ui.EditDetailView
import com.example.anyrecordyoulike.ui.RecordDetailView
import com.example.anyrecordyoulike.ui.ScreenLayout

@Composable
fun Navigation(viewModel: RecordModel, navController: NavHostController) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            ScreenLayout(viewModel = viewModel, navController = navController)
        }
        composable("detail/{recordId}") { backStackEntry ->
            val recordId = backStackEntry.arguments?.getString("recordId")
            val record = viewModel.recOnDisplay.find { it.id == recordId }
            if (record != null) {
                RecordDetailView(
                    vinyl = record,
                    onDismiss = { navController.popBackStack() },
                    onDelete = {
                        viewModel.removeRecord(record.id)
                        navController.popBackStack()
                    },
                    onFavorite = { viewModel.toggleFavorite(record) },
                    onBought = {
                        viewModel.bought(record)
                        navController.popBackStack()
                    },
                    onEdit = { navController.navigate("edit/${recordId}") }
                )
            }
        }
        composable("edit/{recordId}") {backStackEntry -> val recordId = backStackEntry.arguments?.getString("recordId")
            val record = viewModel.recOnDisplay.find { it.id == recordId }
            if (record != null) {
                EditDetailView(
                    vinyl = record, onDismiss = { navController.popBackStack() },
                    onSave = { updatedRecord ->
                        viewModel.update(updatedRecord)
                        navController.popBackStack()
                    })
            }
        }
    }
}
