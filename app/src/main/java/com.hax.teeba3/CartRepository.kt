// CartRepository.kt
package com.hax.teeba3

import com.hax.teeba3.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    val cartItems: Flow<List<CartItem>>

    suspend fun addToCart(item: CartItem)
    suspend fun removeFromCart(item: CartItem)
    suspend fun updateQuantity(item: CartItem, newQuantity: Int)
    suspend fun updateNotes(item: CartItem, newNotes: String) // إضافة هذه الدالة
    suspend fun clearCart()
}
