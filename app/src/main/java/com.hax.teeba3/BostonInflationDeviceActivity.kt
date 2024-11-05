package com.hax.teeba3


import javax.inject.Inject
import dagger.hilt.android.AndroidEntryPoint

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hax.teeba3.ui.theme.Teeba3Theme
@AndroidEntryPoint
class BostonInflationDeviceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teeba3Theme {
                BostonInflationDeviceScreen()
            }
        }
    }
}

@Composable
fun BostonInflationDeviceScreen() {
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp > 600

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.background)
                )
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "اختر جهاز التضخم - بوسطن",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = if (isTablet) 30.sp else 20.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            LazyVerticalGrid(
                columns = if (isTablet) GridCells.Fixed(2) else GridCells.Fixed(1),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(2) { index ->
                    when (index) {
                        0 -> InflationDeviceCard(
                            name = "Encore 26",
                            imageResId = R.drawable.encore_26_image,
                            onClick = {
                                context.startActivity(
                                    Intent(context, BostonInflationDeviceDetailsActivity::class.java)
                                        .putExtra("deviceName", "Encore 26")
                                )
                            },
                            isTablet = isTablet
                        )
                        1 -> InflationDeviceCard(
                            name = "CRE SteriFlate",
                            imageResId = R.drawable.cre_steriflate_image,
                            onClick = {
                                context.startActivity(
                                    Intent(context, BostonInflationDeviceDetailsActivity::class.java)
                                        .putExtra("deviceName", "CRE SteriFlate")
                                )
                            },
                            isTablet = isTablet
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InflationDeviceCard(name: String, imageResId: Int, onClick: () -> Unit, isTablet: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(380.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(40.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(40.dp))
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 20f)
                            )
                        )
                    )
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}

