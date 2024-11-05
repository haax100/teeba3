package com.hax.teeba3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hax.teeba3.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _homeUiState = MutableStateFlow<List<Product>>(emptyList())
    val homeUiState: StateFlow<List<Product>> = _homeUiState

    init {
        loadHomeProducts()
    }

    private fun loadHomeProducts() {
        viewModelScope.launch {
            try {
                _homeUiState.value = repository.getProducts()
            } catch (e: Exception) {
                _homeUiState.value = emptyList()
            }
        }
    }
}
