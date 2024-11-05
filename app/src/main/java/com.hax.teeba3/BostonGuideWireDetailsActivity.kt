package com.hax.teeba3


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hax.teeba3.model.CartItem
import com.hax.teeba3.ui.theme.Teeba3Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BostonGuideWireDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teeba3Theme {
                val guideWireName = intent.getStringExtra("guideWireName") ?: "Unknown Guide Wire"
                val cartViewModel: CartViewModel = viewModel()
                BostonGuideWireDetailsScreen(guideWireName, cartViewModel)
            }
        }
    }
}

@Composable
fun BostonGuideWireDetailsScreen(guideWireName: String, cartViewModel: CartViewModel) {
    val context = LocalContext.current

    // تحديد المقاسات بناءً على نوع الـ Guide Wire
    val sizes = when (guideWireName) {
        "CHOICE Floppy" -> listOf("182 Straight (0.014)", "182 J (0.014)", "300 Straight (0.014)", "300 J (0.014)")
        "CHOICE PT Floppy" -> listOf("185 Straight (0.014)", "185 J (0.014)", "300 Straight (0.014)", "300 J (0.014)")
        "CHOICE™ Standard" -> listOf("182 Straight (0.014)", "182 J (0.014)", "300 Straight (0.014)")
        else -> listOf("Unknown size")
    }

    // حالة لتخزين الكميات لكل مقاس
    val sizeQuantities = remember { mutableStateMapOf<String, Int>() }
    sizes.forEach { size -> if (!sizeQuantities.containsKey(size)) sizeQuantities[size] = 0 }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Guide Wire: $guideWireName",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // عرض المقاسات
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            sizes.forEach { size ->
                StentSizeCard(
                    size = size,
                    quantity = sizeQuantities[size] ?: 0,
                    onQuantityChange = { newQty -> sizeQuantities[size] = newQty }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // زر إضافة إلى السلة
        Button(
            onClick = {
                val selectedSizes = sizeQuantities.filter { it.value > 0 }
                if (selectedSizes.isNotEmpty()) {
                    selectedSizes.forEach { (size, qty) ->
                        val cartItem = CartItem(
                            productId = "$guideWireName-$size", // معرف فريد
                            productName = "$guideWireName",
                            companyName = "Boston",
                            size = size,
                            imageUrl = "https://example.com/image.png", // تأكد من توفير imageUrl المناسب
                            quantity = qty
                        )
                        cartViewModel.addToCart(cartItem) // Fixing the parameter
                    }
                    Toast.makeText(context, "تمت إضافة المنتجات إلى السلة", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "يرجى اختيار كمية على الأقل", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                "إضافة إلى السلة",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold

            )
        }
    }
}
