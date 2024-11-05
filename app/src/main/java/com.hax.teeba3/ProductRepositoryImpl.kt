package com.hax.teeba3

import com.hax.teeba3.model.Product
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor() : ProductRepository {
    override suspend fun getProducts(): List<Product> {
        return DataProvider.coronaryProducts.mapIndexed { index, productName ->
            Product(
                id = index.toString(), // Assign a unique ID, e.g., index
                name = productName,
                description = "الوصف الخاص بـ $productName",
                logo = R.drawable.ic_product_placeholder,
                imageUrl = "https://example.com/image$index.jpg" // Replace with actual URLs
            )
        }
    }
}
