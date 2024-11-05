package com.hax.teeba3

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(cartViewModel: CartViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        FeaturedProductSection(cartViewModel)
        Spacer(modifier = Modifier.height(16.dp))
        TrustedBrandsSection()
        Spacer(modifier = Modifier.height(16.dp))
        AboutUsSection()
        Spacer(modifier = Modifier.height(16.dp))
        FooterSection()
    }
}

@Composable
fun FeaturedProductSection(cartViewModel: CartViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.hero_image),
            contentDescription = "Featured Product",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun TrustedBrandsSection() {
    val listState = rememberLazyListState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF03A9F4),
                        Color(0xFF1976D2)
                    )
                )
            )
            .padding(vertical = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "العلامات التجارية التي يمكنك الوثوق بها",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF7C1515)
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(brandLogos.size * 1) { index ->
                    val logo = brandLogos[index % brandLogos.size]
                    Image(
                        painter = painterResource(id = logo),
                        contentDescription = "شعار العلامة التجارية",
                        modifier = Modifier
                            .height(80.dp)
                            .aspectRatio(2f)
                            .clip(RoundedCornerShape(5.dp))
                    )
                }
            }

            LaunchedEffect(Unit) {
                while (true) {
                    delay(3000L)
                    val nextIndex = (listState.firstVisibleItemIndex + 1) % brandLogos.size
                    listState.animateScrollToItem(index = nextIndex)
                }
            }
        }
    }
}

val brandLogos = listOf(
    R.drawable.brand_logo_1,
    R.drawable.brand_logo_2,
    R.drawable.brand_logo_3,
    R.drawable.brand_logo_4,
    R.drawable.brand_logo_5,
    R.drawable.brand_logo_6,
    R.drawable.brand_logo_7,
    R.drawable.brand_logo_8,
    R.drawable.brand_logo_9,
    R.drawable.brand_logo_10,
)

@Composable
fun AboutUsSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        Text(
            text = "حول الشركة",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Teeba Medical Supplies هي شركة رائدة في توفير المعدات الطبية والخدمات...",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun FooterSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
    ) {
        Text(
            text = "تواصل معنا",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Spacer(modifier = Modifier.height(8.dp))
        ContactRow(Icons.Default.Phone, "رقم الهاتف: +963 991 018 411")
        Spacer(modifier = Modifier.height(16.dp))
        ContactRow(Icons.Default.Email, "البريد الإلكتروني: teebamedicalsyria@gmail.com")
    }
}
