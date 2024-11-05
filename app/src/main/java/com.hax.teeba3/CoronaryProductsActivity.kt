// app/src/main/java/com/hax/teeba3/CoronaryProductsActivity.kt
package com.hax.teeba3


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hax.teeba3.ui.theme.Teeba3Theme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CoronaryProductsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teeba3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CoronaryProductsScreen()
                }
            }
        }
    }
}

@Composable
fun CoronaryProductsScreen() {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    // تحديد عدد الأعمدة بناءً على عرض الشاشة
    val columns = when {
        screenWidth < 600 -> 2
        screenWidth < 840 -> 3
        else -> 4
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "منتجات التدخل التاجي",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 38.sp
            ),
            modifier = Modifier.padding(bottom = 28.dp),
            color = MaterialTheme.colorScheme.primary
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            contentPadding = PaddingValues(18.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(productList.size) { index ->
                ProductCard(product = productList[index], context = context)
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, context: android.content.Context) {
    Card(
        modifier = Modifier
            .height(220.dp)
            .fillMaxWidth()
            .clickable {
                // الانتقال إلى صفحة قائمة الشركات عند النقر على منتج
                val intent = Intent(context, CompanySelectionActivity::class.java)
                intent.putExtra("productName", product.name)
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = product.logo),
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(50)) // جعل الصورة دائرية
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// قائمة المنتجات
val productList = listOf(
    Product("Stent", "دعامة قلبية", R.drawable.ic_stents),
    Product("Balloon", "بالون تدخل طبي", R.drawable.ic_balloon),
    Product("Guide wire", "سلك توجيهي", R.drawable.ic_guide_wire),
    Product("Guide Catheter", "قسطرة توجيه", R.drawable.ic_catheter),
    Product("Diagnostic Catheter", "قسطرة تشخيصية", R.drawable.ic_diagnostic_catheter),
    Product("Wire J", "سلك J", R.drawable.ic_wire_j),
    Product("Y Connector", "موصل Y", R.drawable.ic_y_connector),
    Product("Inflation Device", "جهاز تضخم", R.drawable.ic_inflation_device),
    Product("Introducer Set", "مجموعة مدخل", R.drawable.ic_introducer_set),
    Product("Manifold Set", "مجموعة مانيفولد", R.drawable.ic_manifold_set)
)
