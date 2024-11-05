// SelectedSize.kt
package com.hax.teeba3

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectedSize(
    val productName: String,
    val companyName: String,
    val size: String,
    val quantity: Int,
    val notes: String? = null
) : Parcelable {
    init {
        require(quantity > 0) { "Quantity must be greater than 0" }
    }
}
