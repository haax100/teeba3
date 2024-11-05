package com.hax.teeba3


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hax.teeba3.ui.theme.Teeba3Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BostonGuideWireSelectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teeba3Theme {
                GuideWireSelectionScreen()
            }
        }
    }
}

@Composable
fun GuideWireSelectionScreen() {
    val context = LocalContext.current
    val guideWireTypes = listOf(
        "CHOICE Floppy", "CHOICE PT Floppy", "CHOICE Standard", "CHOICE Intermediate",
        "CHOICE PT Extra Support", "CHOICE Extra Support", "PT GRAPHIX Super Support",
        "PT GRAPHIX Intermediate", "PT GRAPHIX Standard", "PT2 MS", "PT2 LS", "MAILMAN",
        "MARVEL Wire", "SAMURAI Wire", "SAMURAI RC Wire", "FIGHTER Wire", "JUDO 1 Wire",
        "JUDO 3 Wire", "JUDO 6 Wire", "HORNET 10 Wire", "HORNET 14 Wire", "LUGE Moderate"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Select Guide Wire Type",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(guideWireTypes.size) { index ->
                GuideWireCard(guideWireTypes[index], context)
            }
        }
    }
}

@Composable
fun GuideWireCard(name: String, context: android.content.Context) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                val intent = Intent(context, BostonGuideWireDetailsActivity::class.java)
                intent.putExtra("guideWireName", name)
                context.startActivity(intent)
            },
        elevation = CardDefaults.cardElevation(8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
        }
    }
}
