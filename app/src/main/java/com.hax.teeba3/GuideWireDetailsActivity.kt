// الملف: GuideWireDetailsActivity.kt

package com.hax.teeba3

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
class GuideWireDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teeba3Theme {
                val guideWireName = intent.getStringExtra("guideWireName") ?: "Unknown"
                val guideWireDescription = intent.getStringExtra("guideWireDescription") ?: ""
                val guideWireImageRes = intent.getIntExtra("guideWireImageRes", 0)
                val cartViewModel: CartViewModel = viewModel()

                GuideWireDetailsScreen(
                    name = guideWireName,
                    description = guideWireDescription,
                    imageRes = guideWireImageRes,
                    cartViewModel = cartViewModel
                )
            }
        }
    }
}

@Composable
fun GuideWireDetailsScreen(
    name: String,
    description: String,
    imageRes: Int,
    cartViewModel: CartViewModel
) {
    var quantity by remember { mutableStateOf(1) }
    val showMoreInfo = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(topBar = {}) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // عرض شعار طيبة في الأعلى
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "شعار طيبة",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // كارت يحتوي على صورة المنتج
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // نص العنوان
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // عرض/إخفاء المعلومات الإضافية باستخدام Crossfade
            Crossfade(targetState = showMoreInfo.value) { isVisible ->
                if (isVisible) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = description,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 18.sp
                                ),
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            GuideWirePropertiesTable(
                                distalCoreMaterial = "Elastinite Nitinol",
                                coverTypeOrCoils = "Bare Coils",
                                tipStyle = "Core-to-tip",
                                distalCoatings = "Hydrophilic",
                                tipLoad = "0.8g"
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // أزرار الزيادة والنقصان للكمية
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { if (quantity > 1) quantity-- }) {
                    Icon(Icons.Default.Remove, contentDescription = "تقليل الكمية")
                }
                Text(
                    text = "$quantity",
                    fontSize = 26.sp,
                    modifier = Modifier.padding(horizontal = 26.dp)
                )
                IconButton(onClick = { quantity++ }) {
                    Icon(Icons.Default.Add, contentDescription = "زيادة الكمية")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // زر إضافة إلى السلة
            Button(
                onClick = {
                    val cartItem = CartItem(
                        productId = name,
                        productName = name,
                        companyName = "Boston",
                        size = "Standard",
                        quantity = quantity,
                        imageUrl = "https://example.com/image.png"
                    )
                    cartViewModel.addToCart(cartItem)
                    Toast.makeText(context, "تمت الإضافة إلى السلة", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "إضافة إلى السلة", color = Color.White, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // زر طباعة الطلبية
            Button(
                onClick = {
                    val selectedSizes = listOf(
                        SelectedSize(
                            productName = name,
                            size = "Standard",
                            quantity = quantity,
                            companyName = "Boston",
                            notes = null
                        )
                    )
                    printOrder(context, name, selectedSizes)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(text = "طباعة الطلب", color = Color.White, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // زر عرض أو إخفاء المعلومات الإضافية
            Button(
                onClick = { showMoreInfo.value = !showMoreInfo.value },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(text = if (showMoreInfo.value) "إخفاء المعلومات" else "تفاصيل المنتج")
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun GuideWirePropertiesTable(
    distalCoreMaterial: String,
    coverTypeOrCoils: String,
    tipStyle: String,
    distalCoatings: String,
    tipLoad: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.End
        ) {
            GuideWirePropertyRow("Distal Core Material", distalCoreMaterial)
            GuideWirePropertyRow("Cover Type or Coils", coverTypeOrCoils)
            GuideWirePropertyRow("Tip Style", tipStyle)
            GuideWirePropertyRow("Distal Coatings", distalCoatings)
            GuideWirePropertyRow("Tip Load", tipLoad)
        }
    }
}

@Composable
fun GuideWirePropertyRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
