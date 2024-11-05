// الملف: BostonDiagnosticCatheterDetailsActivity.kt

package com.hax.teeba3

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hax.teeba3.model.CartItem
import com.hax.teeba3.ui.theme.Teeba3Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class BostonDiagnosticCatheterDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teeba3Theme {
                val deviceName = intent.getStringExtra("deviceName") ?: "Unknown Device"
                val imageUrl = intent.getStringExtra("imageUrl") ?: ""
                val cartViewModel: CartViewModel = viewModel()
                BostonDiagnosticCatheterDetailsScreen(deviceName, imageUrl, cartViewModel)
            }
        }
    }
}

@Composable
fun BostonDiagnosticCatheterDetailsScreen(
    deviceName: String,
    imageUrl: String,
    cartViewModel: CartViewModel
) {
    val context = LocalContext.current
    val specifications = when (deviceName) {
        "EXPO 6F Selective" -> listOf(
            "FL3.5 100", "FL4 100", "FL4 125", "FL4.5 100", "FL5 100", "FL5 125", "FL6 100",
            "FR3.5 100", "FR4 100", "FR4 125", "FR5 100", "FR5 125", "AL1 100", "AL2 100", "AL3 100",
            "AR1 100", "AR2 100", "AR MOR 100", "WR 100", "WRP 100", "MPA1 100", "MPA2 100", "MPA2 125",
            "MPB1 100", "MPB2 100", "LCB 100", "RCB 100", "IM 100", "IM 125"
        )
        "EXPO 6F Brachial" -> listOf(
            "PIG 100", "PIG 125", "PIG 145° 100", "PIG 155° 100", "TIG4 100", "TIG4.5 100",
            "CAS1 100", "CAS2 100", "KIMNY 100", "IMC 100", "PIG 110"
        )
        else -> listOf("Unknown specifications")
    }

    val imageResId = when (deviceName) {
        "EXPO 6F Selective" -> R.drawable.boston_expo_selective_image
        "EXPO 6F Brachial" -> R.drawable.boston_expo_selective_image
        else -> null
    }

    val specQuantities = remember { mutableStateMapOf<String, Int>() }
    specifications.forEach { spec -> if (!specQuantities.containsKey(spec)) specQuantities[spec] = 0 }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        imageResId?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = deviceName,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 16.dp)
            )
        }

        Text(
            text = "Device: $deviceName",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            specifications.forEach { spec ->
                SpecQuantityCard(
                    spec = spec,
                    quantity = specQuantities[spec] ?: 0,
                    onQuantityChange = { newQty ->
                        specQuantities[spec] = newQty
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val selectedSpecs = specQuantities.filter { it.value > 0 }
                if (selectedSpecs.isNotEmpty()) {
                    selectedSpecs.forEach { (spec, qty) ->
                        val cartItem = CartItem(
                            productId = "$deviceName-$spec",
                            productName = deviceName,
                            companyName = "Boston",
                            size = spec,
                            quantity = qty,
                            imageUrl = imageUrl
                        )
                        cartViewModel.addToCart(cartItem)
                    }
                    Toast.makeText(context, "تمت الإضافة إلى السلة", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "يرجى تحديد كمية على الأقل لمواصفة واحدة", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                "إضافة إلى السلة",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = {
                val selectedSpecs = specQuantities.filter { it.value > 0 }.map { (spec, qty) ->
                    SelectedSize(
                        productName = deviceName,
                        size = spec,
                        quantity = qty,
                        companyName = "Boston",
                        notes = null
                    )
                }
                if (selectedSpecs.isNotEmpty()) {
                    printOrder(context, deviceName, selectedSpecs)
                    Toast.makeText(context, "جاري طباعة الطلبية", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "يرجى تحديد كمية على الأقل لمواصفة واحدة لطباعة الطلبية", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text(
                "طباعة الطلب",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SpecQuantityCard(
    spec: String,
    quantity: Int,
    onQuantityChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = spec,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
        )
        QuantitySelector(quantity = quantity, onQuantityChange = onQuantityChange)
    }
}

@Composable
fun QuantitySelector(quantity: Int, onQuantityChange: (Int) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { if (quantity > 0) onQuantityChange(quantity - 1) },
            modifier = Modifier.size(36.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("-", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
        Text(
            text = "$quantity",
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        Button(
            onClick = { onQuantityChange(quantity + 1) },
            modifier = Modifier.size(36.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("+", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}
