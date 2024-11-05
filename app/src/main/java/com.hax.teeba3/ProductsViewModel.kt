// ProductsViewModel.kt
package com.hax.teeba3

import androidx.lifecycle.ViewModel
import com.hax.teeba3.model.Company

// بيانات المنتج
data class Product(val name: String, val description: String, val logo: Int)

class ProductsViewModel : ViewModel() {

    // جلب قائمة الشركات المتاحة من DataProvider
    fun getCompanies(): List<Company> {
        return DataProvider.companies
    }

    // جلب المنتجات بناءً على الشركة والقسم المختار
    fun getProductsByCompanyAndSection(company: Company, section: String): List<Product> {
        // تحويل قائمة أسماء المنتجات إلى كائنات Product
        val products = company.products.map { productName ->
            Product(
                name = productName,
                description = "Description of $productName",
                logo = R.drawable.ic_product_placeholder // استخدم الصورة المناسبة للمنتج
            )
        }

        // إذا كان لديك منطق لتصفية المنتجات بناءً على القسم، يمكنك تطبيقه هنا
        return products
    }
}
