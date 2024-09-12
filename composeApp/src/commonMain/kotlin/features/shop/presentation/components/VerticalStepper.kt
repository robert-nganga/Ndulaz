package features.shop.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import features.shop.domain.models.Step
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


@Composable
fun VerticalStepper(
    steps: List<Step>,
    currentStep: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        steps.forEachIndexed { index, step ->
            StepItem(
                step = step,
                isCompleted = index < currentStep,
                isFirst = index == 0,
                isLast = index == steps.lastIndex,
                currentStep = currentStep
            )
        }
    }
}

@Composable
fun StepItem(
    step: Step,
    isCompleted: Boolean,
    isFirst: Boolean,
    isLast: Boolean,
    currentStep: Int
) {
    val color = if (isCompleted) MaterialTheme.colors.primary else MaterialTheme.colors.primary.copy( alpha = 0.5f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.Top
    ) {
        // Step indicator and line
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(IntrinsicSize.Min),
            contentAlignment = Alignment.TopCenter
        ) {
            // Step indicator circle
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.TopCenter),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(24.dp)) {
                    drawCircle(color = color)
                }
                if (isCompleted) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Completed",
                        tint = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Vertical line
            if (!isLast) {
                val lineColor = if (isCompleted) MaterialTheme.colors.primary else Color.Gray
                val pathEffect = if (!isCompleted) PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f) else null

                Canvas(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(4.dp)
                        .align(Alignment.TopCenter)
                        .offset(y = 32.dp) // Start from the bottom of the circle
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

        // Step content
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, top = 4.dp, end = 16.dp, bottom = if (isLast) 0.dp else 16.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(step.image),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(color = color)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = step.title,
                    style = MaterialTheme.typography.subtitle1,
                    color = color
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            if(isCompleted){
                Text(
                    text = step.description,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                )
            } else {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}


@Composable
fun OrderTracking() {
    val stages = listOf(
        OrderStage("Packing", "2336 Jack Warren Rd, Delta Junction, Alaska 99737", completed = true),
        OrderStage("Picked", "2417 Tongass Ave #111, Ketchikan, Alaska 99901", completed = true),
        OrderStage("In Transit", "16 Rr 2, Ketchikan, Alaska 99901", completed = false),
        OrderStage("Delivered", "925 S Chugach St #APT 10, Alaska 99645", completed = false)
    )

    Column(modifier = Modifier.padding(16.dp)) {
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
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Adjusted size for the circle with an outer ring
            Canvas(modifier = Modifier.size(30.dp)) { // Smaller size
                drawCircle(
                    color = if (stage.completed) Color.Black else Color.Gray,
                    radius = size.minDimension / 3 // Inner circle
                )
                drawCircle(
                    color = if (stage.completed) Color.Black else Color.Gray.copy(alpha = 0.3f),
                    radius = size.minDimension / 2, // Outer ring
                    style = Stroke(width = 3.dp.toPx()) // Adjusted stroke width
                )
            }

            if (!isLastItem) {
                // Dynamically sized dotted line with adjusted stroke width
                Spacer(modifier = Modifier.height(4.dp))
                val lineColor = if (stage.completed) MaterialTheme.colors.primary else Color.Gray
                val pathEffect = if (!stage.completed) PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f) else null

                Canvas(
                    modifier = Modifier
                        .height(IntrinsicSize.Max)
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
            Text(text = stage.address, style = MaterialTheme.typography.body2, color = Color.Gray)
        }
    }
}

@Composable
fun DottedLine(color: Color, height: Modifier) {
    Canvas(modifier = Modifier
        .width(2.dp)
        .then(height)) {
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f) // Adjusted dash effect
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(0f, size.height),
            pathEffect = pathEffect,
            strokeWidth = 4.dp.toPx() // Increased stroke width for better visibility
        )
    }
}

// Data class to represent each stage
data class OrderStage(val status: String, val address: String, val completed: Boolean)

