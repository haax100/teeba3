// الملف: CartScreen.kt

package com.hax.teeba3

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hax.teeba3.model.CartItem
import kotlinx.coroutines.launch

@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    onPrintOrder: (List<SelectedSize>) -> Unit
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (cartItems.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(cartItems) { item ->
                        CartItemRow(item, cartViewModel)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { cartViewModel.completePurchase() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text(text = "إتمام الشراء")
                }

                Button(
                    onClick = {
                        val selectedSizes = cartItems.map { item ->
                            SelectedSize(
                                productName = item.productName,
                                companyName = item.companyName,
                                size = item.size ?: "Standard",
                                quantity = item.quantity,
                                notes = item.notes
                            )
                        }
                        if (selectedSizes.isNotEmpty()) {
                            onPrintOrder(selectedSizes)
                        } else {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("السلة فارغة، لا يوجد شيء للطباعة")
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text(text = "طباعة الطلبية")
                }
            } else {
                Text(
                    text = "السلة فارغة",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    cartViewModel: CartViewModel
) {
    var showRemoveDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var notes by remember { mutableStateOf(item.notes ?: "") }

    if (showRemoveDialog) {
        AlertDialog(
            onDismissRequest = { showRemoveDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    cartViewModel.removeItemFromCart(item)
                    showRemoveDialog = false
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("تمت إزالة العنصر من السلة")
                    }
                }) {
                    Text("إزالة")
                }
            },
            dismissButton = {
                TextButton(onClick = { showRemoveDialog = false }) {
                    Text("إلغاء")
                }
            },
            title = { Text("تأكيد الإزالة") },
            text = { Text("هل تريد إزالة ${item.productName} من السلة؟") }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = item.productName, style = MaterialTheme.typography.bodyLarge)
                    Text(text = "الشركة: ${item.companyName}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "الكمية: ${item.quantity}", style = MaterialTheme.typography.bodyMedium)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { cartViewModel.updateQuantity(item, item.quantity + 1) }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "زيادة الكمية")
                    }

                    Text(
                        text = item.quantity.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    IconButton(onClick = {
                        if (item.quantity > 1) {
                            cartViewModel.updateQuantity(item, item.quantity - 1)
                        } else {
                            showRemoveDialog = true
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Remove, contentDescription = "تقليل الكمية")
                    }
                }

                IconButton(onClick = { showRemoveDialog = true }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "إزالة العنصر", tint = MaterialTheme.colorScheme.error)
                }
            }

            OutlinedTextField(
                value = notes,
                onValueChange = { newNotes ->
                    notes = newNotes
                    cartViewModel.updateNotes(item, newNotes)
                },
                label = { Text("ملاحظات") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }
    }
}
