// CartItem.kt
package com.hax.teeba3.model

// فئة بيانات تمثل عنصرًا في السلة.
data class CartItem(
    val productId: String,    // معرف فريد للمنتج
    var productName: String,  // اسم المنتج بالكامل
    var companyName: String,  // اسم الشركة المصنعة للمنتج
    var size: String,         // القياس أو المواصفة للمنتج
    var quantity: Int,        // كمية المنتج المضافة إلى السلة
    var imageUrl: String?,    // رابط لصورة المنتج
    var notes: String? = null // ملاحظات على العنصر
)
