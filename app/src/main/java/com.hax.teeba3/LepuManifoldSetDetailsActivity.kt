package com.hax.teeba3


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hax.teeba3.ui.theme.Teeba3Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class LepuManifoldSetDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // استرجاع البيانات من intent
        val deviceName = intent.getStringExtra("device_name") ?: "Unknown Device"

        setContent {
            Teeba3Theme {
                LepuManifoldSetSDetailscreen(deviceName)
            }
        }
    }
}

@Composable
fun LepuManifoldSetSDetailscreen(deviceName: String) {
    var quantity by remember { mutableStateOf(1) }
    val context = LocalContext.current

    // Scrollable layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // عرض صورة المنتج
        Image(
            painter = painterResource(id = R.drawable.lepu_manifold_image), // تأكد من وجود الصورة
            contentDescription = "Product Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // عرض اسم الجهاز
        Text(
            text = deviceName,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // عرض الكمية مع أزرار الإضافة والطرح
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { if (quantity > 1) quantity-- },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(Icons.Filled.Remove, contentDescription = "Remove")
            }

            Text(text = "$quantity", fontSize = 20.sp, modifier = Modifier.padding(horizontal = 16.dp))

            IconButton(
                onClick = { quantity++ },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // زر الإضافة إلى السلة
        Button(
            onClick = {
                Toast.makeText(context, "$deviceName added to cart", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Add to Cart")
        }
    }
}
