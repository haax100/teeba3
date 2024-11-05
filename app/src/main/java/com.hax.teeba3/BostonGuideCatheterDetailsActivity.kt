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
class BostonGuideCatheterDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teeba3Theme {
                val catheterName = intent.getStringExtra("catheterName") ?: "Unknown Catheter"
                val cartViewModel: CartViewModel = viewModel()
                BostonGuideCatheterDetailsScreen(catheterName, cartViewModel)
            }
        }
    }
}

@Composable
fun BostonGuideCatheterDetailsScreen(catheterName: String, cartViewModel: CartViewModel) {
    val context = LocalContext.current

    // تحديد المقاسات بناءً على نوع الـ Guide Catheter
    val sizes = when (catheterName) {
        "MACH 1" -> listOf(
            "FL3", "FL3.5", "FL4", "FL4.5", "FL5", "FL6", "FL3.5ST", "FL4ST", "FL4.5ST", "FL5ST",
            "FCL3.5", "FCL4", "JL3", "JL3.5", "JL4", "JL4.5", "JL5", "JL6", "JL3.5ST", "JL4ST",
            "AL75", "AL1", "AL1.5", "AL2", "AL3", "CLS3", "CLS3.5", "CLS4", "CLS4.5",
            "KL3", "KL4", "Q3.5", "Q4", "Q4.5", "Q5", "VL3", "VL3.5", "VL4", "VL4.5", "VL5"
        )
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
            text = "Guide Catheter: $catheterName",
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
                    onQuantityChange = { newQty ->
                        sizeQuantities[size] = newQty
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val selectedSizes = sizeQuantities.filter { it.value > 0 }
                if (selectedSizes.isNotEmpty()) {
                    selectedSizes.forEach { (size, qty) ->
                        val cartItem = CartItem(
                            productId = "$catheterName-$size", // معرف فريد
                            productName = "$catheterName ",
                            companyName = "Boston",
                            size = size,
                            imageUrl = "https://example.com/image.png", // تأكد من توفير imageUrl المناسب
                            quantity = qty
                        )
                        cartViewModel.addToCart(cartItem) // تم إصلاح الاستدعاء هنا
                    }
                    // عرض رسالة تأكيد
                    Toast.makeText(context, "تمت إضافة المنتجات إلى السلة", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "يرجى اختيار كمية على الأقل", Toast.LENGTH_SHORT).show()
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
    }
}
