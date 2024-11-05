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
class ShunmeiInflationDeviceDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teeba3Theme {
                val deviceName = intent.getStringExtra("deviceName") ?: "Unknown"
                ShunmeiInflationDeviceDetailsScreen(deviceName)
            }
        }
    }
}

@Composable
fun ShunmeiInflationDeviceDetailsScreen(deviceName: String) {
    val specifications = when (deviceName) {
        "Normal type" -> listOf(
            "Pressure Range: 0-20 atm",
            "Volume Capacity: 15 mL",
            "Weight: 100 grams"
        )
        "Gun type" -> listOf(
            "Pressure Range: 0-25 atm",
            "Volume Capacity: 18 mL",
            "Weight: 130 grams"
        )
        "Semi-gun type" -> listOf(
            "Pressure Range: 0-22 atm",
            "Volume Capacity: 17 mL",
            "Weight: 120 grams"
        )
        else -> listOf("Unknown specifications")
    }

    val imageResId = when (deviceName) {
        "Normal type" -> R.drawable.shunmei_normal_type_image
        "Gun type" -> R.drawable.shunmei_gun_type_image
        "Semi-gun type" -> R.drawable.shunmei_semi_gun_type_image
        else -> null
    }

    val cartViewModel: CartViewModel = viewModel()

    // استدعاء InflationDeviceDetailsScreen مع تمرير البيانات المناسبة
    InflationDeviceDetailsScreen(
        deviceName = deviceName,
        specifications = specifications,
        imageResId = imageResId,
        cartViewModel = cartViewModel
    )
}
