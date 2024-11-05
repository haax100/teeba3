// CompanySelectionActivity.kt
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hax.teeba3.model.Company
import com.hax.teeba3.ui.theme.Teeba3Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompanySelectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teeba3Theme {
                val productName = intent.getStringExtra("productName") ?: "Unknown Product"
                CompanyListScreen(productName)
            }
        }
    }
}

@Composable
fun CompanyListScreen(productName: String) {
    val context = LocalContext.current

    // فلترة الشركات بناءً على المنتج المحدد
    val filteredCompanies = DataProvider.companies.filter { company ->
        company.products.contains(productName)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Select Manufacturer for $productName",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 24.dp),
            textAlign = TextAlign.Center
        )

        if (filteredCompanies.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(filteredCompanies.size) { index ->
                    val company = filteredCompanies[index]
                    CompanyCard(company, productName)
                }
            }
        } else {
            Text(
                text = "No companies available for this product.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun CompanyCard(company: Company, productName: String) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                when (company.name) {
                    "Boston Scientific" -> {
                        when (productName) {
                            "Balloon" -> {
                                val intent = Intent(context, BalloonSelectionActivity::class.java)
                                context.startActivity(intent)
                            }
                            "Stent" -> {
                                val intent = Intent(context, BostonStentSelectionActivity::class.java)
                                context.startActivity(intent)
                            }
                            "Guide wire" -> {
                                val intent = Intent(context, BostonGuideWireSelectionActivity::class.java)
                                context.startActivity(intent)
                            }
                            "Guide Catheter" -> {
                                val intent = Intent(context, BostonGuideCatheterSelectionActivity::class.java)
                                context.startActivity(intent)
                            }
                            "Inflation Device" -> {
                                val intent = Intent(context, BostonInflationDeviceActivity::class.java)
                                context.startActivity(intent)
                            }
                            "Introducer Set" -> {
                                val intent = Intent(context, BostonIntroducerSetActivity::class.java)
                                context.startActivity(intent)
                            }
                            "Wire J" -> {
                                val intent = Intent(context, BostonGuideWireSelectionActivity::class.java)
                                context.startActivity(intent)
                            }
                            "Diagnostic Catheter" -> {
                                val intent = Intent(context, BostonDiagnosticCatheterSelectionActivity::class.java)
                                context.startActivity(intent)
                            }
                            "Y Connector" -> {
                                val intent = Intent(context, BostonYConnectorActivity::class.java)
                                context.startActivity(intent)
                            }
                            "Manifold Set" -> {
                                val intent = Intent(context, BostonManifoldSetActivity::class.java)
                                context.startActivity(intent)
                            }
                        }
                    }
                    "Medtronic" -> {
                        when (productName) {
                            "Stent" -> {
                                val intent = Intent(context, MedtronicStentSelectionActivity::class.java)
                                context.startActivity(intent)
                            }
                        }
                    }
                    "Lepu Medical" -> {
                        when (productName) {
                            "Inflation Device" -> {
                                val intent = Intent(context, LepuInflationDeviceActivity::class.java)
                                context.startActivity(intent)
                            }
                            "Introducer Set" -> {
                                val intent = Intent(context, LepuIntroducerSetActivity::class.java)
                                context.startActivity(intent)
                            }
                            "Wire J" -> {
                                val intent = Intent(context, LepuWireJActivity::class.java)
                                context.startActivity(intent)
                            }
                            "Manifold Set" -> {
                                val intent = Intent(context, LepuManifoldSetActivity::class.java)
                                context.startActivity(intent)
                            }
                        }
                    }
                    "Shunmei" -> {
                        when (productName) {
                            "Inflation Device" -> {
                                val intent = Intent(context, ShunmeiInflationDeviceActivity::class.java)
                                context.startActivity(intent)
                            }
                            "Introducer Set" -> {
                                val intent = Intent(context, ShunmeiIntroducerSetActivity::class.java)
                                context.startActivity(intent)
                            }
                            "Wire J" -> {
                                val intent = Intent(context, ShunmeiWireJActivity::class.java)
                                context.startActivity(intent)
                            }
                            "Guide Catheter" -> {
                                val intent = Intent(context, ShunmeiGuideCatheterActivity::class.java)
                                context.startActivity(intent)
                            }
                        }
                    }
                    "Abbott" -> {
                        when (productName) {
                            "Stent" -> {
                                val intent = Intent(context, AbbottStentSelectionActivity::class.java)
                                context.startActivity(intent)
                            }
                            "Balloon" -> {
                                val intent = Intent(context, AbbottBalloonSelectionActivity::class.java)
                                context.startActivity(intent)
                            }
                            "Guide wire" -> {
                                val intent = Intent(context, AbbottGuideWireActivity::class.java)
                                context.startActivity(intent)
                            }
                            "Guide Catheter" -> {
                                val intent = Intent(context, AbbottGuideCatheterActivity::class.java)
                                context.startActivity(intent)
                            }
                        }
                    }
                    "Terumo" -> {
                        when (productName) {
                            "Stent" -> {
                                val intent = Intent(context, TerumoStentSelectionActivity::class.java)
                                context.startActivity(intent)
                            }
                            "Introducer Set" -> {
                                val intent = Intent(context, TerumoIntroducerSetActivity::class.java)
                                context.startActivity(intent)
                            }
                            "Wire J" -> {
                                val intent = Intent(context, TerumoWireJActivity::class.java)
                                context.startActivity(intent)
                            }
                            "Balloon" -> {
                                val intent = Intent(context, TerumoBalloonSelectionActivity::class.java)
                                context.startActivity(intent)
                            }
                        }
                    }
                    "Biotronik" -> {
                        when (productName) {
                            "Stent" -> {
                                val intent = Intent(context, BiotronikStentSelectionActivity::class.java)
                                context.startActivity(intent)
                            }
                        }
                    }
                    "SCW" -> {
                        when (productName) {
                            "Inflation Device" -> {
                                val intent = Intent(context, ScwInflationDeviceActivity::class.java)
                                context.startActivity(intent)
                            }
                        }
                    }
                    else -> {
                        // لا يوجد شيء
                    }
                }
            },
        elevation = CardDefaults.cardElevation(8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = company.logoResId),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = company.name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

