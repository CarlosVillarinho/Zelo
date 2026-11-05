package br.com.carlosvillarinho.zelo.ui.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.carlosvillarinho.zelo.model.Relato
import br.com.carlosvillarinho.zelo.ui.components.RelatoCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioridadesScreen(
    prioridades: List<Relato>,
    onRelatoClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    onApoiarClick: (Int) -> Unit,
    bottomBar: @Composable () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Prioridades") }) },
        bottomBar = bottomBar
    ) { innerPadding ->
        if (prioridades.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Nenhum relato disponível ainda.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = "Os relatos mais apoiados pela comunidade aparecem primeiro.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                itemsIndexed(prioridades, key = { _, relato -> relato.id }) { index, relato ->
                    Column {
                        Text(
                            text = if (index < 3) "#${index + 1} · Prioridade alta" else "#${index + 1}",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = if (index < 3) {
                                MaterialTheme.colorScheme.error
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                        Spacer(Modifier.height(4.dp))
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
}