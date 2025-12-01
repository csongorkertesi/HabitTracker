package hu.kertesi.habittracker.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hu.kertesi.habittracker.data.AppReferences
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(modifier: Modifier, fontScale: Float, navController: NavController) {
    val context = LocalContext.current
    val (savedEmail, remembered) = AppReferences.loadLogin(context)

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    LaunchedEffect(savedEmail, remembered) {
        if (remembered && savedEmail.isNotEmpty()) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = modifier.padding(padding).padding(24.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Bejelentkezés", fontSize = 24.sp * fontScale)

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(
                modifier = Modifier.height(12.dp)
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Jelszó") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it }
                )
                Text("Emlékezz rám", fontSize = 16.sp * fontScale, modifier=Modifier.clickable {
                    rememberMe = !rememberMe
                })
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (!email.contains("@")) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Érvénytelen email cím")
                        }
                        return@Button
                    }

                    AppReferences.saveLogin(context, email, rememberMe)

                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            ) {
                Text("Bejelentkezés", fontSize = 16.sp * fontScale)
            }
        }
    }
}