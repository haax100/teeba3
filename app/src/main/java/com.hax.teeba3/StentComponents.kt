// File: StentComponents.kt
package com.hax.teeba3

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun StentSizeCard(
    size: String,
    quantity: Int,
    onQuantityChange: (Int) -> Unit
) {
    // تهيئة الأنيميشن لتكبير الكارت عند التفاعل
    val cardScale = remember { Animatable(1f) }
    val cardElevation = remember { Animatable(4f) }
    val cardColor = remember { Animatable(1f) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(quantity) {
        cardColor.animateTo(if (quantity > 0) 0.8f else 1f)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .graphicsLayer {
                scaleX = cardScale.value
                scaleY = cardScale.value
            }
            .clickable {
                coroutineScope.launch {
                    cardScale.animateTo(1.05f, animationSpec = tween(150))
                    cardElevation.animateTo(10f, animationSpec = tween(150))
                    cardScale.animateTo(1f, animationSpec = tween(150))
                    cardElevation.animateTo(4f, animationSpec = tween(150))
                }
            },
        elevation = CardDefaults.elevatedCardElevation(cardElevation.value.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // زر ناقص
            IconButton(
                onClick = { if (quantity > 0) onQuantityChange(quantity - 1) },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "تقليل الكمية",
                    tint = Color.Red
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // عرض القياس مع عرض ثابت لضمان التناسق
            Text(
                text = size,
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .width(150.dp) // تحديد عرض ثابت، يمكن تعديله حسب الحاجة
            )

            Spacer(modifier = Modifier.weight(1f)) // يستخدم لملء المساحة المتبقية ودفع العناصر الأخرى نحو اليمين

            // عرض الكمية مع التحكم في اللون
            Text(
                text = quantity.toString(),
                color = if (quantity > 0) Color.Red else Color.Black, // تغير اللون بناءً على الكمية
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(end = 25.dp) // إضافة مسافة صغيرة بعد الكمية
            )

            // زر زائد
            IconButton(
                onClick = { onQuantityChange(quantity + 1) },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "زيادة الكمية",
                    tint = Color.Green
                )
            }
        }
    }
}
