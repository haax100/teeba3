package com.hax.teeba3


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hax.teeba3.ui.InflationDeviceCard
import com.hax.teeba3.ui.theme.Teeba3Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShunmeiInflationDeviceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teeba3Theme {
                ShunmeiInflationDeviceScreen()
            }
        }
    }
}

@Composable
fun ShunmeiInflationDeviceScreen() {
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
                text = "اختر جهاز التضخم - شونمي",
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
                items(3) { index ->
                    when (index) {
                        0 -> InflationDeviceCard(
                            name = "Normal type",
                            imageResId = R.drawable.shunmei_normal_type_image,
                            onClick = {
                                context.startActivity(
                                    Intent(context, ShunmeiInflationDeviceDetailsActivity::class.java)
                                        .putExtra("deviceName", "Normal type")
                                )
                            },
                            isTablet = isTablet
                        )
                        1 -> InflationDeviceCard(
                            name = "Gun type",
                            imageResId = R.drawable.shunmei_gun_type_image,
                            onClick = {
                                context.startActivity(
                                    Intent(context, ShunmeiInflationDeviceDetailsActivity::class.java)
                                        .putExtra("deviceName", "Gun type")
                                )
                            },
                            isTablet = isTablet
                        )
                        2 -> InflationDeviceCard(
                            name = "Semi-gun type",
                            imageResId = R.drawable.shunmei_semi_gun_type_image,
                            onClick = {
                                context.startActivity(
                                    Intent(context, ShunmeiInflationDeviceDetailsActivity::class.java)
                                        .putExtra("deviceName", "Semi-gun type")
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
