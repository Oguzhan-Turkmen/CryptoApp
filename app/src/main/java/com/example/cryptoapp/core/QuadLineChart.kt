package com.example.cryptoapp.core

import android.graphics.Paint
import android.graphics.Typeface
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptoapp.ui.theme.AppColors
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.round
import kotlin.math.roundToInt

@Composable
fun QuadLineChart(
    modifier: Modifier = Modifier,
    data: List<Pair<Int, Double>>
) {
    val spacing = 100f
    val graphColor = Color.Red
    val transparentGraphColor = remember { graphColor.copy(alpha = 0.5f) }
    val upperValue = (data.maxOfOrNull { it.second }?.plus(1))?.roundToInt() ?: 0
    val lowerValue = data.minOfOrNull { it.second }?.toInt() ?: 0
    val density = LocalDensity.current
    var longestYValueWidth by remember { mutableStateOf(0.dp) }

    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
            typeface = Typeface.DEFAULT
        }
    }
    Canvas(
        modifier = modifier
            .padding(0.dp)
            .background(color = AppColors.LightGray, shape = RoundedCornerShape(32.dp))
    ) {
        val spacePerHour = (size.width - spacing) / data.size
        (data.indices step 4).forEach { i ->
            val time = getDay(data[i].first.toLong()).toString()
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    time,
                    spacing + i * spacePerHour,
                    size.height,
                    textPaint
                )
            }
        }

        val priceStep = (upperValue - lowerValue) / 5f
        (0..4).forEach { i ->
            val yValue = lowerValue + priceStep * i
            Log.e("YVALUE", yValue.toString())
            val yValueWidth = textPaint.measureText(round(yValue).toString())
            Log.e("YVALUEWIDTHPX", yValueWidth.toString())
            val itemWidthDp = with(density) { yValueWidth.toDp() }
            if (itemWidthDp > longestYValueWidth) longestYValueWidth = itemWidthDp

            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    round(yValue).toString(),
                    10f,
                    size.height - spacing - i * size.height / 5f,
                    textPaint
                )
            }
        }

        var medX: Float
        var medY: Float
        val strokePath = Path().apply {
            val height = size.height
            data.indices.forEach { i ->
                val nextInfo = data.getOrNull(i + 1) ?: data.last()
                val firstRatio = (data[i].second - lowerValue) / (upperValue - lowerValue)
                val secondRatio = (nextInfo.second - lowerValue) / (upperValue - lowerValue)

                val x1 = spacing + i * spacePerHour
                val y1 = height - spacing - (firstRatio * height).toFloat()
                val x2 = spacing + (i + 1) * spacePerHour
                val y2 = height - spacing - (secondRatio * height).toFloat()
                if (i == 0) {
                    moveTo(x1, y1)
                } else {
                    medX = (x1 + x2) / 2f
                    medY = (y1 + y2) / 2f
                    quadraticBezierTo(x1 = x1, y1 = y1, x2 = medX, y2 = medY)
                }
            }
        }

        drawPath(
            path = strokePath,
            color = Color.Red,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        )
        val fillPath = android.graphics.Path(strokePath.asAndroidPath()).asComposePath().apply {
            lineTo(size.width - spacePerHour, size.height - spacing)
            lineTo(spacing, size.height - spacing)
            close()
        }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    transparentGraphColor,
                    Color.Transparent
                ),
                endY = size.height - spacing
            )
        )
    }
}

private fun getMonth(epochTimeMillis: Long): String {
    val localDateTime =
        Instant.ofEpochMilli(epochTimeMillis).atZone(ZoneId.systemDefault()).toLocalDateTime()

    val formatter = DateTimeFormatter.ofPattern("MMM")

    return localDateTime.format(formatter)
}

fun getDay(epochTimeSec: Long): Int {
    val localDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTimeSec), ZoneId.systemDefault())

    val formatter = DateTimeFormatter.ofPattern("dd")

    return localDateTime.format(formatter).toInt()
}