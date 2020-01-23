package com.theapache64.republic2020

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.math.cos
import kotlin.math.sin

class IndianFlag(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    companion object {

        private const val SPIKE_COUNT = 24
        private const val LINE_WIDTH = 10f
        private const val FLAG_WIDTH_PERC = 0.8f
        private const val FLAG_BORDER_WIDTH = 1f
        private const val FLAG_COLOR_SECTION_HEIGHT = 150f

        // Line
        private val towerPaint = Paint().apply {
            color = Color.parseColor("#36281F") // dark brown
            flags = Paint.ANTI_ALIAS_FLAG
            style = Paint.Style.FILL_AND_STROKE
        }

        // Saffron
        private val saffronPaint = Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
        }

        // White
        private val whitePaint = Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
        }

        // Green
        private val greenPaint = Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
        }

    }


    // Blue
    private val bluePaint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        color = ContextCompat.getColor(this@IndianFlag.context, R.color.flag_blue)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // Finding page dimens
        val pageWidth = measuredWidth
        val pageHeight = measuredHeight

        // Tower cords
        val lineLeft = pageWidth * 0.25f
        val lineTop = pageHeight * 0.10f
        val lineRight = lineLeft + LINE_WIDTH
        val lineBottom = pageHeight.toFloat()

        // Saffron cords
        val safLeft = lineLeft + LINE_WIDTH
        val safTop = lineTop + (LINE_WIDTH * 2)
        val safRight = pageWidth * FLAG_WIDTH_PERC
        val safBottom = safTop + FLAG_COLOR_SECTION_HEIGHT

        // White cords
        val whiteLeft = lineLeft + LINE_WIDTH
        val whiteTop = safTop + FLAG_COLOR_SECTION_HEIGHT
        val whiteRight = pageWidth * FLAG_WIDTH_PERC
        val whiteBottom = whiteTop + FLAG_COLOR_SECTION_HEIGHT

        // green cords
        val greenLeft = lineLeft + LINE_WIDTH
        val greenTop = whiteTop + FLAG_COLOR_SECTION_HEIGHT
        val greenRight = pageWidth * FLAG_WIDTH_PERC
        val greenBottom = greenTop + FLAG_COLOR_SECTION_HEIGHT

        // circle cords
        val whiteWidth = whiteRight - whiteLeft
        val whiteHeight = whiteBottom - whiteTop
        val whiteX = whiteLeft + (whiteWidth / 2)
        val whiteY = whiteTop + (whiteHeight / 2)

        /**
         * 40% of flag color section
         */
        val outerCircleRadius = FLAG_COLOR_SECTION_HEIGHT * 0.4f


        // Finally drawing
        canvas!!.apply {

            // Background
            drawColor(Color.TRANSPARENT)

            // Line
            drawRect(lineLeft, lineTop, lineRight, lineBottom, towerPaint)

            drawSaffron(safLeft, safTop, safRight, safBottom)
            drawWhite(whiteLeft, whiteTop, whiteRight, whiteBottom)
            drawGreen(greenLeft, greenTop, greenRight, greenBottom)

            // Drawing outer circle
            drawOuterCircle(whiteX, whiteY, outerCircleRadius)

            // Drawing spikes
            drawSpikes(whiteX, outerCircleRadius, whiteY)

            // Drawing smallest circle
            drawInnerCircle(whiteX, whiteY)
        }
    }

    private fun Canvas.drawInnerCircle(whiteX: Float, whiteY: Float) {
        // Draw small circle
        bluePaint.style = Paint.Style.FILL
        drawCircle(whiteX, whiteY, 2f, bluePaint)
    }

    private fun Canvas.drawSpikes(
        whiteX: Float,
        outerCircleRadius: Float,
        whiteY: Float
    ) {
        // Draw ashoka chakra
        var degree: Double
        var x2: Double
        var y2: Double
        var d = 0.0
        bluePaint.strokeWidth = 2f
        bluePaint.style = Paint.Style.FILL_AND_STROKE
        for (i in 1..SPIKE_COUNT) {

            // Converting degree to radian
            degree = d * Math.PI / 180

            // Finding spike's end point
            x2 = whiteX + outerCircleRadius * cos(degree)
            y2 = whiteY + outerCircleRadius * sin(degree)

            // Drawing line
            drawLine(whiteX, whiteY, x2.toFloat(), y2.toFloat(), bluePaint)
            d += (360 / SPIKE_COUNT)
        }
    }

    private fun Canvas.drawOuterCircle(
        whiteX: Float,
        whiteY: Float,
        outerCircleRadius: Float
    ) {
        bluePaint.style = Paint.Style.STROKE
        bluePaint.strokeWidth = 3f
        drawCircle(whiteX, whiteY, outerCircleRadius, bluePaint)
    }


    private fun Canvas.drawGreen(
        greenLeft: Float,
        greenTop: Float,
        greenRight: Float,
        greenBottom: Float
    ) {
        // Draw green
        greenPaint.style = Paint.Style.FILL
        greenPaint.color = ContextCompat.getColor(context, R.color.flag_green)
        drawRect(greenLeft, greenTop, greenRight, greenBottom, greenPaint)

        // green stroke
        greenPaint.strokeWidth = FLAG_BORDER_WIDTH
        greenPaint.style = Paint.Style.STROKE
        greenPaint.color = Color.BLACK
        drawRect(greenLeft, greenTop, greenRight, greenBottom, greenPaint)
    }

    private fun Canvas.drawWhite(
        whiteLeft: Float,
        whiteTop: Float,
        whiteRight: Float,
        whiteBottom: Float
    ) {
        // Draw white
        whitePaint.style = Paint.Style.FILL
        whitePaint.color = Color.WHITE
        drawRect(whiteLeft, whiteTop, whiteRight, whiteBottom, whitePaint)

        // White stroke
        whitePaint.strokeWidth = FLAG_BORDER_WIDTH
        whitePaint.style = Paint.Style.STROKE
        whitePaint.color = Color.BLACK
        drawRect(whiteLeft, whiteTop, whiteRight, whiteBottom, whitePaint)
    }

    private fun Canvas.drawSaffron(
        safLeft: Float,
        safTop: Float,
        safRight: Float,
        safBottom: Float
    ) {
        // Saffron
        saffronPaint.style = Paint.Style.FILL
        saffronPaint.color =
            ContextCompat.getColor(context, R.color.flag_saffron)
        drawRect(safLeft, safTop, safRight, safBottom, saffronPaint)

        // Saffron stroke
        saffronPaint.strokeWidth = FLAG_BORDER_WIDTH
        saffronPaint.style = Paint.Style.STROKE
        saffronPaint.color = Color.BLACK
        drawRect(safLeft, safTop, safRight, safBottom, saffronPaint)
    }
}