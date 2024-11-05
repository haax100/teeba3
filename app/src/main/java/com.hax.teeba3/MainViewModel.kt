package com.hax.teeba3

import androidx.lifecycle.ViewModel
import com.hax.teeba3.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    // متغير يحتوي على قائمة شعارات العلامات التجارية كمصدر بيانات لتحديث الواجهة
    private val _brands = MutableStateFlow(listOf(
        R.drawable.brand_logo_1,
        R.drawable.brand_logo_2,
        R.drawable.brand_logo_3,
        R.drawable.brand_logo_4,
        R.drawable.brand_logo_5,
        R.drawable.brand_logo_6
    ))
    val brands: StateFlow<List<Int>> = _brands // تحديد قائمة العلامات التجارية كمصدر نهائي

    // دالة لتحديث قائمة العلامات التجارية
    fun updateBrands(newBrands: List<Int>) {
        _brands.value = newBrands
    }

    // متغير يحتوي على قائمة المنتجات المميزة لعرضها في قسم خاص بالواجهة
    private val _featuredProducts = MutableStateFlow(listOf(
        Product("1", "منتج مميز 1", "وصف المنتج 1", R.drawable.brand_logo_1, "https://example.com/image1.jpg"),
        Product("2", "منتج مميز 2", "وصف المنتج 2", R.drawable.brand_logo_2, "https://example.com/image2.jpg"),
        Product("3", "منتج مميز 3", "وصف المنتج 3", R.drawable.brand_logo_3, "https://example.com/image3.jpg")
    ))
    val featuredProducts: StateFlow<List<Product>> = _featuredProducts // تحديد المنتجات المميزة كمصدر نهائي

    // دالة لتحديث المنتجات المميزة
    fun updateFeaturedProducts(newProducts: List<Product>) {
        _featuredProducts.value = newProducts
    }
}
