package hu.kertesi.habittracker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hu.kertesi.habittracker.data.AppReferences
import hu.kertesi.habittracker.data.HabitData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier, fontScale: Float, navController: NavController) {
    val context = LocalContext.current

    var habit by remember { mutableStateOf(AppReferences.loadHabit(context)) }

    var completed by remember { mutableStateOf(AppReferences.loadHabit(context).completed) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Habit Tracker") },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate("edit")
                        }
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "edit habit")
                    }
                    IconButton(
                        onClick = {
                            navController.navigate("settings")
                        }
                    ) {
                        Icon(Icons.Default.Settings, contentDescription = "settings")
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        if (habit.name.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(start = 24.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Szokás: ${habit.name}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp * fontScale,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    IconButton(
                        onClick = {
                            AppReferences.clearHabit(context)
                            habit = HabitData("", "", false)
                            completed = false
                        }
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = "clear habit")
                    }
                }
                Text(
                    text = "Cél: ${habit.goal}",
                    modifier = Modifier.padding(top = 8.dp),
                    fontSize = 20.sp * fontScale
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = completed,
                        onCheckedChange = {
                            completed = it
                            AppReferences.saveHabit(
                                context,
                                HabitData(habit.name, habit.goal, completed)
                            )
                        }
                    )
                    Text(
                        text = if (completed) "Ma már teljesítve ✅" else "Ma még nem teljesítve",
                        fontSize = 16.sp * fontScale
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp),
            ) {
                Text("Nincs beállított szokás.", fontSize = 20.sp * fontScale)
                Spacer(
                    modifier = Modifier.padding(8.dp)
                )
                Button(
                    onClick = {
                        navController.navigate("edit")
                    }
                ) {
                    Text("Szokás beállítása", fontSize = 16.sp * fontScale)
                }
            }
        }

    }
}