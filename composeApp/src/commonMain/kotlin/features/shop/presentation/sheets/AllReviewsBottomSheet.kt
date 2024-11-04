package features.shop.presentation.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skydoves.flexible.bottomsheet.material.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetState
import features.shop.domain.models.ReviewFilterOptions
import features.shop.presentation.components.ReviewItem
import features.shop.presentation.screens.product_details_screen.AllReviewsState
import io.ktor.util.PlatformUtils


@Composable
fun AllReviewsBottomSheet(
    modifier: Modifier = Modifier,
    state: AllReviewsState,
    sheetState: FlexibleSheetState,
    filterOptions: ReviewFilterOptions,
    onDismiss: () -> Unit,
    onFilterOptionsChange: (ReviewFilterOptions) -> Unit
){

//    var selectedOption by remember(filterOptions) {
//        mutableStateOf(if (filterOptions.rating == null) "All" else "${filterOptions.rating.toInt()}")
//    }

    FlexibleBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier,
        sheetState = sheetState,
        containerColor = MaterialTheme.colors.surface,
        scrimColor = MaterialTheme.colors.onSurface.copy(
            alpha = 0.2f
        ),
        dragHandle = null,
        shape = RectangleShape,
        windowInsets = WindowInsets(left = 0.dp, right = 0.dp, top = 0.dp, bottom = 0.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = if(PlatformUtils.IS_NATIVE) 80.dp else 0.dp)
        ){
            AllReviewsTopBar(
                title = "All Reviews",
                modifier = Modifier.padding(
                    horizontal = 8.dp
                ),
                onDismiss = onDismiss
            )
            AllReviewsFilterOptions(
                selectedOption = if (filterOptions.rating == null) "All" else "${filterOptions.rating.toInt()}",
                onOptionSelected = { option ->
                    val rating = if(option == "All") null else option.toDouble()
                    onFilterOptionsChange(filterOptions.copy(rating = rating))
                }
            )

            when(state) {
                is AllReviewsState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = state.message,
                            modifier = Modifier
                        )
                    }
                }
                is AllReviewsState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator()
                    }
                }
                is AllReviewsState.Success -> {
                    val reviews = state.reviews
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentPadding = PaddingValues(
                            vertical = 16.dp
                        )
                    ) {
                        items(
                            count = reviews.size,
                            key = { index -> reviews[index].id.toString() }
                        ) { index ->
                            val review = reviews[index]
                            Column {
                                ReviewItem(
                                    review = review,
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 8.dp
                                    )
                                )
                                if (index < reviews.lastIndex) {
                                    Divider(
                                        modifier = Modifier.fillMaxWidth(),
                                        color = MaterialTheme.colors.primary.copy(
                                            alpha = 0.15f
                                        ),
                                        thickness = 0.8.dp
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun AllReviewsTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onDismiss: () -> Unit
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 20.dp,
                bottom = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(
            onClick = onDismiss
        ){
            Icon(
                Icons.Default.Clear,
                contentDescription = "",
                modifier = Modifier.size(35.dp),
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun AllReviewsFilterOptions(
    modifier: Modifier = Modifier,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        (6 downTo  1).forEach { index ->
            val rating = if (index == 6) "All" else "$index"
            ReviewsFilterOption(
                option = rating,
                isSelected = selectedOption == rating,
                onClick = {
                    onOptionSelected(rating)
                }
            )
        }
    }
}

@Composable
fun ReviewsFilterOption(
    modifier: Modifier = Modifier,
    option: String,
    isSelected: Boolean,
    onClick: () -> Unit
){
    Box(
        modifier = modifier
            .border(
                width = 1.5.dp,
                color = if (isSelected) MaterialTheme.colors.onBackground else MaterialTheme.colors.onBackground.copy(
                    alpha = 0.2f
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(
                color = if (isSelected) MaterialTheme.colors.onBackground else Color.Transparent
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ){
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = option,
                color = if (isSelected) MaterialTheme.colors.background else MaterialTheme.colors.onBackground,
            )
            if (option != "All"){
                Icon(
                    Icons.Filled.Star,
                    contentDescription = "",
                    tint = if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}