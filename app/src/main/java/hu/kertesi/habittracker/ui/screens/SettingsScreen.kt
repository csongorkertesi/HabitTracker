package hu.kertesi.habittracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hu.kertesi.habittracker.data.AppReferences

@Composable
fun SettingsScreen(navController: NavController) {
    val context = LocalContext.current
    val initial = AppReferences.loadSettings(context)

    var darkMode by remember { mutableStateOf(initial.first) }
    var fontScale by remember { mutableStateOf(initial.second) }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text("Beállítások", style = MaterialTheme.typography.headlineSmall)

            Spacer(Modifier.height(16.dp))

            Row {
                Switch(
                    checked = darkMode,
                    onCheckedChange = { darkMode = it }
                )
                Spacer(Modifier.width(8.dp))
                Text("Dark Mode")
            }

            Spacer(Modifier.height(24.dp))

            Text("Betűméret: ${"%.1f".format(fontScale)}x")
            Slider(
                value = fontScale,
                onValueChange = { fontScale = it },
                valueRange = 0.8f..1.4f
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    AppReferences.saveSettings(context, darkMode = darkMode, fontScale = fontScale)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mentés")
            }
        }
    }
}