package com.example.pruebatecnicaingesoftii.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pruebatecnicaingesoftii.presentation.viewmodel.HomeViewModel
import com.example.pruebatecnicaingesoftii.presentation.viewmodel.LocalidadesViewModel
import com.example.pruebatecnicaingesoftii.presentation.viewmodel.TablasViewModel

@Composable
fun HomeScreen(
    onNavigateToTablas: () -> Unit,
    onNavigateToLocalidades: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        state.versionMessage?.let {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(it, modifier = Modifier.padding(8.dp))
            }
        }

        state.loginMessage?.let {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(it, modifier = Modifier.padding(8.dp), color = Color.Red)
            }
        }

        Text(text = "Informacion de Usuario", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        if (state.isLoading) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
        }

        state.user?.let {
            Text("Nombre: ${it.nombre}")
            Text("Usuario: ${it.usuario}")
            Text("Identificacion: ${it.identificacion}")
        } ?: Text("No hay sesion activa")

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onNavigateToTablas, modifier = Modifier.fillMaxWidth(), enabled = !state.isLoading) {
            Text("Tablas")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNavigateToLocalidades, modifier = Modifier.fillMaxWidth(), enabled = !state.isLoading) {
            Text("Localidades")
        }
    }
}

@Composable
fun TablasScreen(viewModel: TablasViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Tablas del Esquema", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        LazyColumn {
            items(state.tables) { table ->
                ListItem(
                    headlineContent = { Text(table.tableName) },
                    supportingContent = { Text(table.description ?: "Sin descripcion") }
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun LocalidadesScreen(viewModel: LocalidadesViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Localidades Recogidas", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        state.errorMessage?.let {
            Text(text = it, color = Color.Red, modifier = Modifier.padding(bottom = 8.dp))
            Button(onClick = { viewModel.loadLocalities() }) {
                Text("Reintentar")
            }
        }

        LazyColumn {
            items(state.localities) { locality ->
                ListItem(
                    headlineContent = { Text(locality.abreviacionCiudad ?: "N/A") },
                    supportingContent = { Text(locality.nombreCompleto ?: "N/A") }
                )
                HorizontalDivider()
            }
        }
    }
}
