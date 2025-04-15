package com.example.tryapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tryapp.ui.components.Title
import com.example.tryapp.ui.viewmodel.UserViewModel

@Composable
fun UsersScreen(
    navController: NavController,

    modifier: Modifier = Modifier,
    viewModel: UserViewModel = hiltViewModel(),
) {
    val userName by viewModel.userName.collectAsState()
    val userAge by viewModel.userAge.collectAsState()
    val userAuthorized by viewModel.userAuthorized.collectAsState()

    var isVisible by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var age by remember { mutableIntStateOf(0) }
    var isActive by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true

    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300)),
        exit = slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(300)),
        modifier = modifier.fillMaxSize()
    ) {
        Column(verticalArrangement = Arrangement.SpaceBetween) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Title("Users")

                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.padding(top = 16.dp)
                )

                TextField(
                    value = age.toString(),
                    onValueChange = { age = it.toIntOrNull() ?: 0 },
                    label = { Text("Alter") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.padding(top = 16.dp)
                )

                Switch(
                    checked = isActive,
                    onCheckedChange = { isActive = it }
                )

                Button(
                    onClick = { viewModel.saveUser(name, age, isActive) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text("Save")
                }

                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Text("Saved name: $userName")
                    Text("Saved age: $userAge")
                    Text("Authorized: $userAuthorized")
                }
            }
        }
    }
}
