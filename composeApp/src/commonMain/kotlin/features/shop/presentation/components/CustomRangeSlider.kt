package features.shop.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun CustomRangeSliders(
    modifier: Modifier = Modifier,
    initialRange: ClosedFloatingPointRange<Float> = 0f..100f,
    initialValues: ClosedFloatingPointRange<Float> = 40f..80f,
    onRangeChange: (ClosedFloatingPointRange<Float>) -> Unit
) {
    var sliderWidth by remember { mutableStateOf(1f) }
    var leftThumbValue by remember { mutableStateOf(initialValues.start) }
    var rightThumbValue by remember { mutableStateOf(initialValues.endInclusive) }

    // Track the positions of the thumbs in pixels
    var leftThumbPos by remember { mutableStateOf(0f) }
    var rightThumbPos by remember { mutableStateOf(0f) }

    // Update the values based on the thumb positions
    val totalRange = initialRange.endInclusive - initialRange.start
    val leftValueToPosition: (Float) -> Float = { value ->
        (value - initialRange.start) / totalRange * sliderWidth
    }
    val positionToLeftValue: (Float) -> Float = { pos ->
        (pos / sliderWidth) * totalRange + initialRange.start
    }

    val rightValueToPosition: (Float) -> Float = { value ->
        (value - initialRange.start) / totalRange * sliderWidth
    }
    val positionToRightValue: (Float) -> Float = { pos ->
        (pos / sliderWidth) * totalRange + initialRange.start
    }
    val baseColor = MaterialTheme.colors.primary.copy(alpha = 0.2f)
    val activeColor = MaterialTheme.colors.primary
    val onActiveColor = MaterialTheme.colors.onPrimary

    // Range Slider UI
    Box(
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned { layoutCoordinates ->
                sliderWidth = layoutCoordinates.size.width.toFloat()
                leftThumbPos = leftValueToPosition(leftThumbValue)
                rightThumbPos = rightValueToPosition(rightThumbValue)
            }
    ) {

        // Track line (background line)
        Canvas(modifier = Modifier.fillMaxWidth().height(6.dp)) {
            drawLine(
                color = baseColor,
                start = Offset(0f, size.height / 2),
                end = Offset(size.width, size.height / 2),
                strokeWidth = 18f
            )

            drawLine(
                color = activeColor,
                start = Offset(leftThumbPos, size.height / 2),
                end = Offset(rightThumbPos, size.height / 2),
                strokeWidth = 18f
            )
        }

        // Left thumb (draggable), centered vertically
        Box(
            modifier = Modifier
                .offset { IntOffset(leftThumbPos.roundToInt() - 2, (-8).dp.toPx().toInt()) }
                .size(24.dp)
                .background(activeColor, CircleShape)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        val newPosition = (leftThumbPos + dragAmount.x).coerceIn(0f, rightThumbPos)
                        leftThumbPos = newPosition
                        leftThumbValue = positionToLeftValue(newPosition)
                        onRangeChange(leftThumbValue..rightThumbValue)
                        change.consume()
                    }
                },
            contentAlignment = Alignment.Center
        ){
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(onActiveColor, CircleShape)
            )
        }

        // Right thumb (draggable), centered vertically
        Box(
            modifier = Modifier
                .offset { IntOffset(rightThumbPos.roundToInt() - 2, (-8).dp.toPx().toInt()) }
                .size(24.dp)
                .background(activeColor, CircleShape)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        val newPosition = (rightThumbPos + dragAmount.x).coerceIn(leftThumbPos, sliderWidth)
                        rightThumbPos = newPosition
                        rightThumbValue = positionToRightValue(newPosition)
                        onRangeChange(leftThumbValue..rightThumbValue)
                        change.consume()
                    }
                },
            contentAlignment = Alignment.Center
        ){
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(onActiveColor, CircleShape)
            )
        }

        // Left label directly below the left thumb
        Text(
            text = "${leftThumbValue.roundToInt()}",
            modifier = Modifier
                .offset { IntOffset(leftThumbPos.roundToInt() - 12, 40) },
            color = activeColor
        )

        // Right label directly below the right thumb
        Text(
            text = "${rightThumbValue.roundToInt()}",
            modifier = Modifier
                .offset { IntOffset(rightThumbPos.roundToInt() - 12, 40) },
            color = activeColor
        )
    }
}