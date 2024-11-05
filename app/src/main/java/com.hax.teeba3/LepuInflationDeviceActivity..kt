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
class LepuInflationDeviceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teeba3Theme {
                LepuInflationDeviceScreen()
            }
        }
    }
}

@Composable
fun LepuInflationDeviceScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Select Inflation Device - Lepu Medical",
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
                    name = "30atm",
                    imageResId = R.drawable.lepu_30atm_image,
                    onClick = {
                        context.startActivity(
                            Intent(context, LepuInflationDeviceDetailsActivity::class.java)
                                .putExtra("deviceName", "30atm")
                        )
                    },
                    isTablet = false
                )
            }
            item {
                InflationDeviceCard(
                    name = "Angiopower",
                    imageResId = R.drawable.lepu_angiopower_image,
                    onClick = {
                        context.startActivity(
                            Intent(context, LepuInflationDeviceDetailsActivity::class.java)
                                .putExtra("deviceName", "Angiopower")
                        )
                    },
                    isTablet = false
                )
            }
        }
    }
}
