import android.graphics.PointF
import android.util.Log
import android.view.HapticFeedbackConstants
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toIntRect
import androidx.compose.ui.unit.toSize
import com.example.cryptoapp.domain.model.CoinGraphModel
import com.example.cryptoapp.presentation.coindetail.ChartHistoryRange
import com.example.cryptoapp.ui.theme.AppColors
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@OptIn(ExperimentalTextApi::class)
@Composable
fun SmoothLineGraph(data: List<CoinGraphModel>, range: ChartHistoryRange) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(AppColors.LightGray, RoundedCornerShape(24.dp))
            .fillMaxWidth()
            .height(400.dp)
    ) {
        val animationProgress = remember { Animatable(0f) }
        var highlightedWeek by remember { mutableStateOf<Int?>(null) }
        val localView = LocalView.current

        LaunchedEffect(highlightedWeek) {
            if (highlightedWeek != null) {
                localView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            }
        }

        LaunchedEffect(key1 = data, block = {
            animationProgress.animateTo(1f, tween(3000))
            Log.e("CHARTDATA", data.toString())
        })

        val coroutineScope = rememberCoroutineScope()
        val textMeasurer = rememberTextMeasurer()
        val labelTextStyle = MaterialTheme.typography.labelSmall
        var containerSize by remember { mutableStateOf(IntSize(350, 350)) }

        val graphSize by remember(containerSize, data) {
            derivedStateOf {
                calculateGraphSizeByData(
                    xIndicators = data.map { it.time.toString() },
                    yIndicators = data.map { it.close.toString() },
                    containerSize = containerSize,
                    textMeasurer = textMeasurer
                ).toSize()
            }
        }

        Spacer(
            modifier = Modifier
                .padding(8.dp)
                .aspectRatio(4 / 3f)
                .fillMaxSize()
                .align(Alignment.Center)
                .onGloballyPositioned {
                    containerSize = IntSize(it.size.width, it.size.height)
                }
                .pointerInput(data) {
                    detectTapGestures {
                        coroutineScope.launch {
                            animationProgress.snapTo(0f)
                            animationProgress.animateTo(1f, tween(3000))
                        }
                    }
                }
                .pointerInput(data) {
                    detectDragGesturesAfterLongPress(
                        onDragStart = { offset ->
                            highlightedWeek = (offset.x / (graphSize.width / (data.size - 1)))
                                .roundToInt()
                                .coerceIn(0, data.lastIndex)
                        },
                        onDragEnd = { highlightedWeek = null },
                        onDragCancel = { highlightedWeek = null },
                        onDrag = { change, _ ->
                            highlightedWeek =
                                (change.position.x / (graphSize.width / (data.size - 1)))
                                    .roundToInt()
                                    .coerceIn(0, data.lastIndex)
                        }
                    )
                }
                .drawWithCache {
                    val path = generateSmoothPath(data, graphSize)
                    val filledPath = Path()
                    filledPath.addPath(path)
                    filledPath.relativeLineTo(0f, graphSize.height)
                    filledPath.lineTo(0f, graphSize.height)
                    filledPath.close()

                    onDrawBehind {
                        val barWidthPx = 1.dp.toPx()

                        if (data.size >= range.limit) {
                            val rangedData = range.getRangedData(data)

                            repeat(range.xSteps) { i ->
                                val startX = (graphSize.width / range.xSteps) * (i)
                                Log.e("index", i.toString())
                                drawLine(
                                    AppColors.Gray2,
                                    start = Offset(startX, 0f),
                                    end = Offset(startX, graphSize.height),
                                    strokeWidth = barWidthPx
                                )

                                drawText(
                                    textMeasurer = textMeasurer,
                                    text = range.getEpochMillisLabel(rangedData[i].time),
                                    topLeft = Offset(
                                        startX,
                                        graphSize.height + 2f,
                                    )
                                )
                            }
                        }
                        drawLine(
                            AppColors.Gray2,
                            start = Offset(0f, 0f),
                            end = Offset(0f, graphSize.height),
                            strokeWidth = barWidthPx
                        )

                        drawLine(
                            AppColors.Gray2,
                            start = Offset(0f, graphSize.height),
                            end = Offset(graphSize.width, graphSize.height),
                            strokeWidth = barWidthPx
                        )

                        if (data.size >= range.limit) {
                            val sortedData = data.sortedByDescending { it.close }

                            repeat(range.xSteps) { i ->
                                val startY = (graphSize.height / range.xSteps) * i
                                val itemIndex = (sortedData.size * i) / range.xSteps

                                drawLine(
                                    AppColors.Gray2,
                                    start = Offset(0f, startY),
                                    end = Offset(graphSize.width, startY),
                                    strokeWidth = barWidthPx
                                )

                                drawText(
                                    textMeasurer = textMeasurer,
                                    text = sortedData[itemIndex].close.toString(),
                                    topLeft = Offset(graphSize.width, startY),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        clipRect(right = graphSize.width * animationProgress.value) {
                            drawPath(path, AppColors.SelectedBlue, style = Stroke(2.dp.toPx()))

                            drawPath(
                                filledPath,
                                brush = Brush.verticalGradient(
                                    listOf(
                                        AppColors.SelectedBlue.copy(alpha = 0.4f),
                                        Color.Transparent
                                    )
                                ),
                                style = Fill
                            )
                        }

                        // draw highlight if user is dragging
                        if (highlightedWeek != null) {
                            drawHighlight(
                                highlightedWeek!!,
                                data,
                                textMeasurer,
                                labelTextStyle,
                                graphSize
                            )
                        }
                    }
                })
    }
}

private val ChartHistoryRange.xSteps: Int
    get() {
        return when (this) {
            ChartHistoryRange.ONE_DAY -> 4
            ChartHistoryRange.ONE_WEEK -> 7
            ChartHistoryRange.ONE_MONTH -> 5
            ChartHistoryRange.THREE_MONTH -> 4
            ChartHistoryRange.SIX_MONTH -> 7
            ChartHistoryRange.ONE_YEAR -> 6
            ChartHistoryRange.ALL -> 5
        }
    }

private fun ChartHistoryRange.getRangedData(data: List<CoinGraphModel>): List<CoinGraphModel> {
    return when (this) {
        ChartHistoryRange.ONE_DAY -> data.slice(0..limit step 6)
        ChartHistoryRange.ONE_WEEK -> data
        ChartHistoryRange.ONE_MONTH -> data.slice(0..limit step 7)
        ChartHistoryRange.THREE_MONTH -> data.slice(0..limit step 30)
        ChartHistoryRange.SIX_MONTH -> data.slice(0..limit step 30)
        ChartHistoryRange.ONE_YEAR -> data.slice(0..limit step 60)
        ChartHistoryRange.ALL -> data.slice(0..limit step 365)
    }
}

private fun ChartHistoryRange.getEpochMillisLabel(epochTimeMillis: Long): String {
    return when (this) {
        ChartHistoryRange.ONE_DAY -> getHour(epochTimeMillis)
        ChartHistoryRange.ONE_WEEK -> getDay(epochTimeMillis)
        ChartHistoryRange.ONE_MONTH -> getDay(epochTimeMillis)
        ChartHistoryRange.THREE_MONTH -> getMonth(epochTimeMillis)
        ChartHistoryRange.SIX_MONTH -> getMonth(epochTimeMillis)
        ChartHistoryRange.ONE_YEAR -> getMonth(epochTimeMillis)
        ChartHistoryRange.ALL -> getYear(epochTimeMillis)
    }
}

@OptIn(ExperimentalTextApi::class)
private fun calculateGraphSizeByData(
    xIndicators: List<String>,
    yIndicators: List<String>,
    containerSize: IntSize,
    textMeasurer: TextMeasurer
): IntSize {
    if (xIndicators.isEmpty() || yIndicators.isEmpty()) {
        val defaultSize = textMeasurer.measure(text = "").size

        return IntSize(
            width = containerSize.width - defaultSize.width,
            height = containerSize.height - defaultSize.height
        )
    }

    val tallestInXIndicators =
        xIndicators.map { textMeasurer.measure(text = it).size.height }.maxBy { it }
    val longestInYIndicators =
        yIndicators.map { textMeasurer.measure(text = it).size.width }.maxBy { it }

    return IntSize(
        width = containerSize.width - longestInYIndicators,
        height = containerSize.height - tallestInXIndicators
    )
}

val HighlightColor = Color.White.copy(alpha = 0.7f)


fun generateSmoothPath(data: List<CoinGraphModel>, size: Size): Path {
    val path = Path()

    if (data.isEmpty()) return path

    val numberEntries = data.size - 1
    val weekWidth = size.width / numberEntries

    val max = data.maxBy { it.close }
    val min = data.minBy { it.close } // will map to x= 0, y = height
    val range = max.close - min.close
    val heightPxPerAmount = size.height / range.toFloat()

    var previousBalanceX = 0f
    var previousBalanceY = size.height
    data.forEachIndexed { i, balance ->
        if (i == 0) {
            path.moveTo(
                0f,
                size.height - (balance.close - min.close).toFloat() *
                        heightPxPerAmount
            )
        }

        val balanceX = i * weekWidth
        val balanceY = size.height - (balance.close - min.close).toFloat() *
                heightPxPerAmount
        // to do smooth curve graph - we use cubicTo, uncomment section below for non-curve
        val controlPoint1 = PointF((balanceX + previousBalanceX) / 2f, previousBalanceY)
        val controlPoint2 = PointF((balanceX + previousBalanceX) / 2f, balanceY)
        path.cubicTo(
            controlPoint1.x, controlPoint1.y, controlPoint2.x, controlPoint2.y,
            balanceX, balanceY
        )

        previousBalanceX = balanceX
        previousBalanceY = balanceY
    }
    return path
}

@OptIn(ExperimentalTextApi::class)
fun DrawScope.drawHighlight(
    highlightedWeek: Int,
    graphData: List<CoinGraphModel>,
    textMeasurer: TextMeasurer,
    labelTextStyle: TextStyle,
    graphSize: Size
) {
    val amount = graphData[highlightedWeek].close
    val minAmount = graphData.minBy { it.close }.close
    val range = graphData.maxBy { it.close }.close - minAmount
    val percentageHeight = ((amount - minAmount).toFloat() / range.toFloat())
    val pointY = graphSize.height - (graphSize.height * percentageHeight)
    // draw vertical line on week
    val x = highlightedWeek * (graphSize.width / (graphData.size - 1))
    drawLine(
        HighlightColor,
        start = Offset(x, 0f),
        end = Offset(x, graphSize.height),
        strokeWidth = 2.dp.toPx(),
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
    )

    // draw hit circle on graph
    drawCircle(
        AppColors.SelectedBlue,
        radius = 4.dp.toPx(),
        center = Offset(x, pointY)
    )

    // draw info box
    val textLayoutResult = textMeasurer.measure("$amount", style = labelTextStyle)
    val highlightContainerSize = (textLayoutResult.size).toIntRect().inflate(4.dp.roundToPx()).size
    val boxTopLeft = (x - (highlightContainerSize.width / 2f))
        .coerceIn(0f, graphSize.width - highlightContainerSize.width)
    drawRoundRect(
        Color.White,
        topLeft = Offset(boxTopLeft, 0f),
        size = highlightContainerSize.toSize(),
        cornerRadius = CornerRadius(8.dp.toPx())
    )
    drawText(
        textLayoutResult,
        color = Color.Black,
        topLeft = Offset(boxTopLeft + 4.dp.toPx(), 4.dp.toPx())
    )
}

private fun getHour(epochTimeSec: Long): String {
    val localDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTimeSec), ZoneId.systemDefault())

    val formatter = DateTimeFormatter.ofPattern("hh a")

    return localDateTime.format(formatter)
}

private fun getMonth(epochTimeMillis: Long): String {
    val localDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTimeMillis), ZoneId.systemDefault())

    val formatter = DateTimeFormatter.ofPattern("MMM")

    return localDateTime.format(formatter)
}

private fun getYear(epochTimeMillis: Long): String {
    val localDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTimeMillis), ZoneId.systemDefault())

    val formatter = DateTimeFormatter.ofPattern("YYYY")

    return localDateTime.format(formatter)
}

private fun getDay(epochTimeSec: Long): String {
    val localDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTimeSec), ZoneId.systemDefault())

    val formatter = DateTimeFormatter.ofPattern("dd")

    return localDateTime.format(formatter)
}