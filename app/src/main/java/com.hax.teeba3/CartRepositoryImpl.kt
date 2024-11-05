// CartRepositoryImpl.kt
package com.hax.teeba3

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hax.teeba3.model.CartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : CartRepository {

    private val _cartItems = MutableStateFlow<List<CartItem>>(loadCartItems())
    override val cartItems: Flow<List<CartItem>> = _cartItems.asStateFlow()

    private fun loadCartItems(): List<CartItem> {
        val json = sharedPreferences.getString("cart_items", "[]") ?: "[]"
        val type = object : TypeToken<List<CartItem>>() {}.type
        return gson.fromJson(json, type)
    }

    private fun saveCartItems() {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(_cartItems.value)
        editor.putString("cart_items", json)
        editor.apply()
    }

    override suspend fun addToCart(item: CartItem) {
        _cartItems.update { currentItems ->
            val updatedItems = currentItems.toMutableList().apply {
                val existingItemIndex = indexOfFirst { it.productId == item.productId }
                if (existingItemIndex != -1) {
                    val existingItem = this[existingItemIndex]
                    val updatedItem = existingItem.copy(
                        quantity = existingItem.quantity + item.quantity,
                        notes = item.notes ?: existingItem.notes // تحديث الملاحظات إذا كانت متوفرة
                    )
                    set(existingItemIndex, updatedItem)
                } else {
                    add(item)
                }
            }
            updatedItems
        }
        saveCartItems()
    }

    override suspend fun removeFromCart(item: CartItem) {
        _cartItems.update { currentItems ->
            val updatedItems = currentItems.toMutableList().apply {
                removeIf { it.productId == item.productId }
            }
            updatedItems
        }
        saveCartItems()
    }

    override suspend fun updateQuantity(item: CartItem, newQuantity: Int) {
        _cartItems.update { currentItems ->
            val updatedItems = currentItems.toMutableList().apply {
                val index = indexOfFirst { it.productId == item.productId }
                if (index != -1) {
                    val updatedItem = this[index].copy(quantity = newQuantity)
                    set(index, updatedItem)
                }
            }
            updatedItems
        }
        saveCartItems()
    }

    override suspend fun updateNotes(item: CartItem, newNotes: String) {
        _cartItems.update { currentItems ->
            val updatedItems = currentItems.toMutableList().apply {
                val index = indexOfFirst { it.productId == item.productId }
                if (index != -1) {
                    val updatedItem = this[index].copy(notes = newNotes)
                    set(index, updatedItem)
                }
            }
            updatedItems
        }
        saveCartItems()
    }

    override suspend fun clearCart() {
        _cartItems.update { emptyList() }
        saveCartItems()
    }
}
