package com.hax.teeba3


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hax.teeba3.ui.InflationDeviceDetailsScreen
import com.hax.teeba3.ui.theme.Teeba3Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LepuInflationDeviceDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teeba3Theme {
                val deviceName = intent.getStringExtra("deviceName") ?: "Unknown"
                LepuInflationDeviceDetailsScreen(deviceName)
            }
        }
    }
}

@Composable
fun LepuInflationDeviceDetailsScreen(deviceName: String) {
    val specifications = when (deviceName) {
        "30atm" -> listOf(
            "Pressure Range: 0-30 atm",
            "Volume Capacity: 25 mL",
            "Weight: 180 grams"
        )
        "Angiopower" -> listOf(
            "Pressure Range: 0-25 atm",
            "Volume Capacity: 30 mL",
            "Weight: 200 grams"
        )
        else -> listOf("Unknown specifications")
    }

    val imageResId = when (deviceName) {
        "30atm" -> R.drawable.lepu_30atm_image
        "Angiopower" -> R.drawable.lepu_angiopower_image
        else -> null
    }

    val cartViewModel: CartViewModel = viewModel()
    InflationDeviceDetailsScreen(
        deviceName = deviceName,
        specifications = specifications,
        imageResId = imageResId,
        cartViewModel = cartViewModel
    )
}
