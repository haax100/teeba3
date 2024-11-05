package com.hax.teeba3


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hax.teeba3.ui.theme.Teeba3Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class LepuWireJDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teeba3Theme {
                LepuWireJDetailsScreen(intent.getStringExtra("deviceName") ?: "Unknown")
            }
        }
    }
}

@Composable
fun LepuWireJDetailsScreen(deviceName: String) {
    val specifications = when (deviceName) {
        "Shoocin™ J-tip Guidewire" -> listOf(
            "Length: 150cm, 180cm",
            "Coating: Hydrophilic",
            "Size: 0.035 inch"
        )
        else -> listOf("Unknown specifications")
    }

    val imageResId = when (deviceName) {
        "Shoocin™ J-tip Guidewire" -> R.drawable.lepu_shoocin_image
        else -> null
    }

    var quantity by remember { mutableStateOf(0) }
    val context = LocalContext.current
    val cartViewModel: CartViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .scale(1f)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                imageResId?.let {
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = deviceName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = deviceName,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(12.dp))
                specifications.forEach { spec ->
                    Text(
                        text = spec,
                        style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                QuantityControl(
                    quantity = quantity,
                    onIncrease = { quantity++ },
                    onDecrease = { if (quantity > 0) quantity-- }
                )
                Spacer(modifier = Modifier.height(20.dp))
                AnimatedAddToCartButton(
                    quantity = quantity,
                    context = context,
                    cartViewModel = cartViewModel,
                    productId = deviceName,
                    productName = deviceName,
                    imageUrl = null,
                    size = "size"
                )
            }
        }
    }
}
