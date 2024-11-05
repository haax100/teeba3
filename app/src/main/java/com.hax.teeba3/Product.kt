// Product.kt
package com.hax.teeba3.model

import java.io.Serializable

// تعريف كائن المنتج مع خصائصه الأساسية
data class Product(
    val id: String,               // معرف المنتج الفريد
    val name: String,             // اسم المنتج
    val description: String,      // وصف المنتج
    val logo: Int,                // معرف الصورة (Resource ID)
    val imageUrl: String? = null  // رابط الصورة (اختياري)
) : Serializable
