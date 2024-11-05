// الملف: OrderPrintAdapter.kt

package com.hax.teeba3

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderPrintAdapter(
    private val context: Context,
    private val stentName: String,
    private val selectedSizes: List<SelectedSize>
) : PrintDocumentAdapter() {

    private var pdfDocument: PdfDocument? = null
    private val pageWidth = 595 // عرض A4 بوحدة النقاط
    private val pageHeight = 842 // ارتفاع A4 بوحدة النقاط

    override fun onLayout(
        oldAttributes: PrintAttributes,
        newAttributes: PrintAttributes,
        cancellationSignal: CancellationSignal,
        callback: LayoutResultCallback,
        extras: Bundle?
    ) {
        if (cancellationSignal.isCanceled) {
            callback.onLayoutCancelled()
            return
        }

        val info = PrintDocumentInfo.Builder("order_${System.currentTimeMillis()}.pdf")
            .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
            .setPageCount(1)
            .build()

        callback.onLayoutFinished(info, true)
    }

    override fun onWrite(
        pageRanges: Array<out PageRange>,
        destination: ParcelFileDescriptor,
        cancellationSignal: CancellationSignal,
        callback: WriteResultCallback
    ) {
        pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        val page = pdfDocument!!.startPage(pageInfo)

        if (cancellationSignal.isCanceled) {
            callback.onWriteCancelled()
            pdfDocument!!.close()
            pdfDocument = null
            return
        }

        drawPage(page.canvas)

        pdfDocument!!.finishPage(page)

        try {
            pdfDocument!!.writeTo(FileOutputStream(destination.fileDescriptor))
        } catch (e: IOException) {
            callback.onWriteFailed(e.toString())
            return
        } finally {
            pdfDocument!!.close()
            pdfDocument = null
        }

        callback.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
    }

    private fun drawPage(canvas: Canvas) {
        val paint = Paint()
        val textPaint = Paint()
        textPaint.color = Color.BLACK
        textPaint.textSize = 12f
        textPaint.textAlign = Paint.Align.CENTER

        // رسم الشعار
        val logo: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.logo)
        val logoScaled = Bitmap.createScaledBitmap(logo, 150, 70, false)
        canvas.drawBitmap(logoScaled, (pageWidth / 2f) - (logoScaled.width / 2), 80f, paint)

        // كتابة العناوين الرئيسية
        paint.typeface = Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD)
        paint.textSize = 16f
        paint.color = Color.BLACK
        paint.textAlign = Paint.Align.LEFT
        canvas.drawText("Teeba Medical Supplies", 40f, 110f, paint)
        canvas.drawText("Date: ${getCurrentDate()}", 40f, 130f, paint)

        paint.textAlign = Paint.Align.RIGHT
        canvas.drawText("طيبة للتجهيزات الطبية", pageWidth - 40f, 110f, paint)
        canvas.drawText("التاريخ: ${getCurrentDate()}", pageWidth - 40f, 130f, paint)

        // إعداد الجدول
        val startX = 20f
        val startY = 170f
        val tableWidth = pageWidth - 40f
        val columnWidths = floatArrayOf(
            30f,  // #
            110f, // المنتج
            100f, // الشركة
            120f,  // القياس
            50f,  // الكمية
            180f  // ملاحظات
        )
        val headers = arrayOf("#", "المنتج", "الشركة", "القياس", "الكمية", "ملاحظات")

        // رسم رؤوس الجدول
        var xPosition = startX
        for (i in headers.indices) {
            drawTableCell(
                canvas, headers[i], xPosition, startY, columnWidths[i], 40f, textPaint, true
            )
            xPosition += columnWidths[i]
        }

        // رسم البيانات
        var yPosition = startY + 40f
        for ((index, size) in selectedSizes.withIndex()) {
            xPosition = startX
            val rowData = arrayOf(
                (index + 1).toString(),
                size.productName,
                size.companyName,
                size.size,
                size.quantity.toString(),
                size.notes ?: ""
            )

            for (i in rowData.indices) {
                drawTableCell(
                    canvas, rowData[i], xPosition, yPosition, columnWidths[i], 40f, textPaint, false
                )
                xPosition += columnWidths[i]
            }
            yPosition += 40f
        }

        // رسم حدود الجدول
        drawTableBorders(canvas, startX, startY, tableWidth, yPosition - startY, columnWidths)
    }

    private fun drawTableCell(
        canvas: Canvas,
        text: String,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        paint: Paint,
        isHeader: Boolean
    ) {
        val cellPaint = Paint()
        cellPaint.style = Paint.Style.FILL
        cellPaint.color = if (isHeader) Color.LTGRAY else Color.WHITE
        canvas.drawRect(x, y, x + width, y + height, cellPaint)

        cellPaint.style = Paint.Style.STROKE
        cellPaint.color = Color.BLACK
        canvas.drawRect(x, y, x + width, y + height, cellPaint)

        val textX = x + width / 2
        val textY = y + height / 2 - (paint.descent() + paint.ascent()) / 2
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText(text, textX, textY, paint)
    }

    private fun drawTableBorders(
        canvas: Canvas,
        startX: Float,
        startY: Float,
        tableWidth: Float,
        tableHeight: Float,
        columnWidths: FloatArray
    ) {
        val borderPaint = Paint()
        borderPaint.style = Paint.Style.STROKE
        borderPaint.color = Color.BLACK
        borderPaint.strokeWidth = 2f

        // رسم الإطار الخارجي
        canvas.drawRect(startX, startY, startX + tableWidth, startY + tableHeight, borderPaint)

        // رسم الخطوط الرأسية
        var xPosition = startX
        for (width in columnWidths) {
            xPosition += width
            canvas.drawLine(xPosition, startY, xPosition, startY + tableHeight, borderPaint)
        }

        // رسم الخطوط الأفقية
        val numberOfRows = selectedSizes.size + 1 // عدد الصفوف بما في ذلك الرؤوس
        for (i in 0..numberOfRows) {
            val yPosition = startY + i * 40f
            canvas.drawLine(startX, yPosition, startX + tableWidth, yPosition, borderPaint)
        }
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(Date())
    }
}
