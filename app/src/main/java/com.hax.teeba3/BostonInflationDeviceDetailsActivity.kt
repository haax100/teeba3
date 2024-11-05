package com.hax.teeba3

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
class BostonInflationDeviceDetailsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teeba3Theme {
                val deviceName = intent.getStringExtra("deviceName") ?: "Unknown"
                val cartViewModel: CartViewModel = viewModel()
                BostonInflationDeviceDetailsScreen(deviceName, cartViewModel)
            }
        }
    }
}

@Composable
fun BostonInflationDeviceDetailsScreen(deviceName: String, cartViewModel: CartViewModel) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    val specifications = when (deviceName) {
        "Encore 26" -> listOf(
            "Pressure Range: 0-26 atm",
            "Volume Capacity: 20 mL",
            "Weight: 150 grams"
        )
        "CRE SteriFlate" -> listOf(
            "Pressure Range: 0-20 atm",
            "Volume Capacity: 25 mL",
            "Weight: 180 grams"
        )
        else -> listOf("Unknown specifications")
    }

    val imageResId = when (deviceName) {
        "Encore 26" -> R.drawable.encore_26_image
        "CRE SteriFlate" -> R.drawable.cre_steriflate_image
        else -> null
    }

    var quantity by remember { mutableStateOf(0) } // تم تصحيح mutableIntStateOf إلى mutableStateOf
    val context = LocalContext.current

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
                            .fillMaxWidth(0.7f)  // تعديل عرض الصورة
                            .aspectRatio(1.0f)  // الحفاظ على نسبة العرض إلى الارتفاع
                            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    )
                } ?: run {
                    Text(
                        text = "No image available",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = deviceName,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    fontSize = 20.sp,  // نفس حجم النص للتلفون والتابلت
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                specifications.forEach { spec ->
                    Text(
                        text = spec,
                        style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                        fontSize = 14.sp,  // نفس حجم الخط للتلفون والتابلت
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
                    productId = "$deviceName-${System.currentTimeMillis()}",
                    productName = deviceName,
                    size = "Standard",   // تمرير القيمة المناسبة للـ size
                    imageUrl = null      // تمرير null إذا لم يكن هناك صورة
                )
            }
        }
    }
}

@Composable
fun AnimatedAddToCartButton(
    quantity: Int,
    context: Context,
    cartViewModel: CartViewModel,
    productId: String,
    productName: String,
    size: String,         // البارامتر size
    imageUrl: String?     // البارامتر imageUrl
) {
    Button(
        onClick = {
            if (quantity > 0) {
                val cartItem = CartItem(
                    productId = productId,
                    productName = productName,
                    companyName = "Boston",
                    size = size,               // تمرير size هنا
                    quantity = quantity,
                    imageUrl = imageUrl        // استخدام imageUrl الممرر
                )
                cartViewModel.addToCart(cartItem)
                Toast.makeText(
                    context,
                    "$quantity x $productName تم إضافتها إلى السلة",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "الرجاء تحديد الكمية",
                    Toast.LENGTH_SHORT
                ).show()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = "إضافة إلى السلة",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun QuantityControl(quantity: Int, onIncrease: () -> Unit, onDecrease: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        IconButton(
            onClick = onDecrease,
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "تقليل الكمية",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.width(40.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(
            onClick = onIncrease,
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "زيادة الكمية",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}