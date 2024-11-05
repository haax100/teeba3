package com.hax.teeba3


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hax.teeba3.ui.InflationDeviceCard
import com.hax.teeba3.ui.theme.Teeba3Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScwInflationDeviceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teeba3Theme {
                ScwInflationDeviceScreen()
            }
        }
    }
}

@Composable
fun ScwInflationDeviceScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Select Inflation Device - SCW",
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 29.sp
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),  // شبكة بعمودين
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                InflationDeviceCard(
                    name = "MED2000",
                    imageResId = R.drawable.med2000_image,  // استخدم صورة مناسبة
                    onClick = {
                        context.startActivity(
                            Intent(context, ScwInflationDeviceDetailsActivity::class.java)
                                .putExtra("deviceName", "MED2000")
                        )
                    },
                    isTablet = false
                )
            }
            item {
                InflationDeviceCard(
                    name = "MED4000",
                    imageResId = R.drawable.med4000_image,  // استخدم صورة مناسبة
                    onClick = {
                        context.startActivity(
                            Intent(context, ScwInflationDeviceDetailsActivity::class.java)
                                .putExtra("deviceName", "MED4000")
                        )
                    },
                    isTablet = false
                )
            }
            item {
                InflationDeviceCard(
                    name = "MED6000",
                    imageResId = R.drawable.med4000_image,  // استخدم صورة مناسبة
                    onClick = {
                        context.startActivity(
                            Intent(context, ScwInflationDeviceDetailsActivity::class.java)
                                .putExtra("deviceName", "MED6000")
                        )
                    },
                    isTablet = false
                )
            }
        }
    }
}
