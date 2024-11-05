//  Utils.kt

package com.hax.teeba3

import android.content.Context
import android.print.PrintManager

// دالة الطباعة لتجهيز الطلب
fun printOrder(context: Context, stentName: String, selectedSizes: List<SelectedSize>) {
    val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
    val printAdapter = OrderPrintAdapter(context, stentName, selectedSizes)
    printManager.print("Order for $stentName", printAdapter, null)
}
