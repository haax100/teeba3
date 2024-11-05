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
class ScwInflationDeviceDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teeba3Theme {
                val deviceName = intent.getStringExtra("deviceName") ?: "Unknown"
                ScwInflationDeviceDetailsScreen(deviceName)
            }
        }
    }
}

@Composable
fun ScwInflationDeviceDetailsScreen(deviceName: String) {
    val specifications = when (deviceName) {
        "MED2000" -> listOf(
            "Pressure Range: 0-30 atm",
            "Volume Capacity: 25 mL",
            "Weight: 180 grams"
        )
        "MED4000" -> listOf(
            "Pressure Range: 0-25 atm",
            "Volume Capacity: 30 mL",
            "Weight: 200 grams"
        )
        "MED6000" -> listOf(
            "Pressure Range: 0-40 atm",
            "Volume Capacity: 40 mL",
            "Weight: 220 grams"
        )
        else -> listOf("Unknown specifications")
    }

    val imageResId = when (deviceName) {
        "MED2000" -> R.drawable.med2000_image  // استخدم صورة مناسبة
        "MED4000" -> R.drawable.med4000_image  // استخدم صورة مناسبة
        "MED6000" -> R.drawable.med4000_image  // استخدم صورة مناسبة
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
