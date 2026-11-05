package br.com.carlosvillarinho.zelo.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.carlosvillarinho.zelo.model.Relato

@Composable
fun RelatoCard(
    relato: Relato,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onApoiarClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = relato.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = relato.body,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = if (relato.favorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (relato.favorito) "Remover dos favoritos" else "Favoritar",
                        tint = if (relato.favorito) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            ApoioButton(relato = relato, onClick = onApoiarClick)
        }
    }
}

@Composable
fun ApoioButton(
    relato: Relato,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val label = if (relato.apoiado) "Apoiado · ${relato.apoios}" else "Apoiar · ${relato.apoios}"
    val content: @Composable RowScope.() -> Unit = {
        Icon(
            imageVector = Icons.Default.ThumbUp,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(label)
    }

    if (relato.apoiado) {
        FilledTonalButton(onClick = onClick, modifier = modifier, content = content)
    } else {
        OutlinedButton(onClick = onClick, modifier = modifier, content = content)
    }
}