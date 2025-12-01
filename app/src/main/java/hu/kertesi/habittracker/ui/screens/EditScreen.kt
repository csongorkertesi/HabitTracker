package hu.kertesi.habittracker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hu.kertesi.habittracker.data.AppReferences
import hu.kertesi.habittracker.data.HabitData
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(modifier: Modifier, fontScale: Float, navController: NavController) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var goal by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Szokás szerkesztése") }
            )
        },
        snackbarHost = { SnackbarHostState() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Szokás neve") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            TextField(
                value = goal,
                onValueChange = { goal = it },
                label = { Text("Cél") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            Button(
                onClick = {
                    if(name.isBlank() || goal.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Kérlek tölts ki minden mezőt!")
                        }
                        return@Button
                    }
                    AppReferences.saveHabit(
                        context,
                        HabitData(
                            name = name,
                            goal = goal,
                            completed = false
                        )
                    )
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mentés")
            }
        }
    }
}