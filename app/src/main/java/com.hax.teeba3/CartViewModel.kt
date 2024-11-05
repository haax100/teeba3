// CartViewModel.kt
package com.hax.teeba3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hax.teeba3.model.CartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    private val _purchaseComplete = MutableStateFlow(false)
    val purchaseComplete: StateFlow<Boolean> = _purchaseComplete

    init {
        getCartItems()
    }

    private fun getCartItems() {
        viewModelScope.launch {
            cartRepository.cartItems.collect { items ->
                _cartItems.value = items
            }
        }
    }

    fun addToCart(item: CartItem) {
        viewModelScope.launch {
            cartRepository.addToCart(item)
        }
    }

    fun removeItemFromCart(item: CartItem) {
        viewModelScope.launch {
            cartRepository.removeFromCart(item)
        }
    }

    fun updateQuantity(item: CartItem, newQuantity: Int) {
        viewModelScope.launch {
            cartRepository.updateQuantity(item, newQuantity)
        }
    }

    fun updateNotes(item: CartItem, newNotes: String) {
        viewModelScope.launch {
            cartRepository.updateNotes(item, newNotes)
        }
    }

    fun completePurchase() {
        viewModelScope.launch {
            cartRepository.clearCart()
            _purchaseComplete.value = true
        }
    }

    fun resetPurchaseState() {
        _purchaseComplete.value = false
    }
}
