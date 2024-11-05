package com.hax.teeba3


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AbbottGuideWireActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AbbottGuideWireScreen()
        }
    }
}

@Composable
fun AbbottGuideWireScreen() {
    val guideWires = listOf(
        GuideWire("HI-TORQUE TURNTRAC", "Responsease Parabolic Core Grind", R.drawable.ic_guide_wire1),
        GuideWire("HI-TORQUE TURNTRAC FLEX Guide Wire", "Responsease Parabolic Core Grind", R.drawable.ic_guide_wire2)
    )

    val context = LocalContext.current  // يجب استدعاء LocalContext داخل @Composable

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Nitinol Workhorse Guide Wires",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        guideWires.forEach { guideWire ->
            GuideWireCard(guideWire = guideWire) {
                // الانتقال إلى واجهة تفاصيل المنتج
                val intent = Intent(context, GuideWireDetailsActivity::class.java).apply {
                    putExtra("guideWireName", guideWire.name)
                    putExtra("guideWireDescription", guideWire.description)
                    putExtra("guideWireImageRes", guideWire.imageRes)
                }
                context.startActivity(intent)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun GuideWireCard(guideWire: GuideWire, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .shadow(8.dp, shape = MaterialTheme.shapes.large)
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // عرض الصورة بشكل دائري
            Image(
                painter = painterResource(id = guideWire.imageRes),
                contentDescription = guideWire.name,
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape) // جعل الصورة دائرية
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // عرض النصوص
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = guideWire.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = guideWire.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

data class GuideWire(
    val name: String,
    val description: String,
    val imageRes: Int
)
