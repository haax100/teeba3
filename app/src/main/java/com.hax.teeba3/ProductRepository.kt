// ProductRepository.kt
package com.hax.teeba3

import com.hax.teeba3.model.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
}
