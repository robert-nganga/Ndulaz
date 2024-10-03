package features.shop.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun OrderTracking(
    modifier: Modifier = Modifier,
    stages: List<OrderStage>
) {
    Column(modifier = modifier.padding(16.dp)) {
        stages.forEachIndexed { index, stage ->
            OrderTrackingItem(
                stage = stage,
                isLastItem = index == stages.lastIndex
            )
        }
    }
}

@Composable
fun OrderTrackingItem(stage: OrderStage, isLastItem: Boolean) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier
    ) {
        val completedColor = MaterialTheme.colors.onBackground
        val unCompletedColor = MaterialTheme.colors.onBackground.copy(
            alpha = 0.2f
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Canvas(modifier = Modifier.size(30.dp)) { // Smaller size
                drawCircle(
                    color = if (stage.completed) completedColor else unCompletedColor,
                    radius = size.minDimension / 4 // Inner circle
                )
                drawCircle(
                    color = if (stage.completed) completedColor else unCompletedColor,
                    radius = size.minDimension / 2, // Outer ring
                    style = Stroke(width = 2.dp.toPx()) // Adjusted stroke width
                )
            }

            if (!isLastItem) {
                Spacer(modifier = Modifier.height(4.dp))
                val lineColor = if (stage.completed) completedColor else unCompletedColor
                val pathEffect = if (!stage.completed) PathEffect.dashPathEffect(floatArrayOf(25f, 25f), 0f) else null

                Canvas(
                    modifier = Modifier
                        .height(30.dp)
                        .width(4.dp) // Start from the bottom of the circle
                ) {
                    drawLine(
                        color = lineColor,
                        start = Offset(0f, 0f),
                        end = Offset(0f, size.height),
                        strokeWidth = 4.dp.toPx(),
                        pathEffect = pathEffect
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Display stage text
        Column(modifier = Modifier.weight(1f)) {
            Text(text = stage.status, fontWeight = FontWeight.Bold)
            Text(text = stage.message, style = MaterialTheme.typography.body2, color = Color.Gray)
        }
    }
}


data class OrderStage(val status: String, val message: String, val completed: Boolean)



