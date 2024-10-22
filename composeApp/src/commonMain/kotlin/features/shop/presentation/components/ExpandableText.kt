package features.shop.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle


@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    text: String
){
    var expanded by remember { mutableStateOf(false) }
    val displayText = if (expanded) {
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colors.onBackground
                )
            ){
                append(text)
            }
            append(" ")
            pushStringAnnotation(tag = "SEE_LESS", annotation = "See Less")
            withStyle(style = SpanStyle(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground
            )) {
                append("See Less")
            }
            pop()
        }
    } else {
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colors.onBackground
                )
            ){
                append(text.take(100) + "...")
            }
            append(" ")
            pushStringAnnotation(tag = "SEE_MORE", annotation = "See More")
            withStyle(style = SpanStyle(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground
            )) {
                append("See More")
            }
            pop()
        }
    }
    Column {
        ClickableText(
            text = displayText,
            onClick = { offset ->
                displayText.getStringAnnotations(tag = if (expanded) "SEE_LESS" else "SEE_MORE", start = offset, end = offset)
                    .firstOrNull()?.let {
                        expanded = !expanded
                    }
            },
            style = MaterialTheme.typography.body2,
            maxLines = if (expanded) Int.MAX_VALUE else 3,
            overflow = TextOverflow.Ellipsis,
        )
    }
}