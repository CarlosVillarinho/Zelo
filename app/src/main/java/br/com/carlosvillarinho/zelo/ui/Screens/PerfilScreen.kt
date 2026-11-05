package br.com.carlosvillarinho.zelo.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.carlosvillarinho.zelo.ui.components.RelatoCard
import br.com.carlosvillarinho.zelo.viewmodel.PerfilUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    perfil: PerfilUi,
    onRelatoClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    onApoiarClick: (Int) -> Unit,
    bottomBar: @Composable () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Perfil") }) },
        bottomBar = bottomBar
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Foto do perfil",
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text("Morador Zelo", style = MaterialTheme.typography.titleLarge)
                        Text(
                            text = "Cidadão colaborador",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatCard(perfil.meusRelatos.size, "Relatos criados", Modifier.weight(1f))
                    StatCard(perfil.totalFavoritos, "Favoritos", Modifier.weight(1f))
                    StatCard(perfil.apoiosDados, "Apoios dados", Modifier.weight(1f))
                }
            }

            item {
                Text("Meus relatos", style = MaterialTheme.typography.titleMedium)
            }

            if (perfil.meusRelatos.isEmpty()) {
                item {
                    Text(
                        text = "Você ainda não publicou nenhum relato. Toque no + da tela inicial para criar o primeiro.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                items(perfil.meusRelatos, key = { it.id }) { relato ->
                    RelatoCard(
                        relato = relato,
                        onClick = { onRelatoClick(relato.id) },
                        onFavoriteClick = { onFavoriteClick(relato.id) },
                        onApoiarClick = { onApoiarClick(relato.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun StatCard(
    value: Int,
    label: String,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value.toString(), style = MaterialTheme.typography.headlineMedium)
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}