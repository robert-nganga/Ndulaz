package features.shop.presentation.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import features.shop.domain.models.FilterOptions
import features.shop.presentation.components.CustomRangeSliders


@Composable
fun SortAndFilterBottomSheet(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    brands: List<String>? =null,
    categories: List<String>? = null,
    onApply: (FilterOptions) -> Unit,
    filterOptions: FilterOptions
){

    val sortOptions = listOf(
        "Most Recent",
        "Rating: Low to High",
        "Rating: High to Low",
        "Price: Low to High",
        "Price: High to Low",
    )
    var sliderPosition by remember {
        val first = filterOptions.minPrice?.toFloat() ?: 0f
        val second = filterOptions.maxPrice?.toFloat() ?: 10000f
        mutableStateOf(first..second)
    }
    var selectedBrand by remember { mutableStateOf(filterOptions.brand ?: "All") }
    var selectedCategory by remember { mutableStateOf(filterOptions.category ?: "All") }
    var selectedSortOption by remember {
        val sortOption = getSortOption(sortBy = filterOptions.sortBy, sortOrder = filterOptions.sortOrder)
        mutableStateOf(sortOption)
    }
    LaunchedEffect(Unit){
        println("Bottom sheet options $filterOptions")
    }

    Column(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
                vertical = 24.dp
            )
    ){
        Text(
            "Sort & Filter",
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider(
            color = MaterialTheme.colors.onBackground.copy(
                alpha = 0.2f
            ),
            thickness = 1.dp
        )
        if (brands != null){
            Spacer(modifier = Modifier.height(16.dp))
            OptionsSection(
                title = "Brands",
                selectedOption = selectedBrand,
                options = brands,
                onOptionSelected = {
                    selectedBrand = it
                }
            )
        }
        if (categories != null){
            Spacer(modifier = Modifier.height(32.dp))
            OptionsSection(
                title = "Categories",
                selectedOption = selectedCategory,
                options = categories,
                onOptionSelected = {
                    selectedCategory = it
                }
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Price Range",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomRangeSliders(
            initialRange = 0f..12000f,
            initialValues = sliderPosition,
            onRangeChange = { sliderPosition = it }
        )
        Spacer(modifier = Modifier.height(32.dp))
        OptionsSection(
            title = "Sort By",
            selectedOption = selectedSortOption,
            options = sortOptions,
            onOptionSelected = {
                selectedSortOption = it
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ){
            Button(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(32.dp),
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary.copy(
                        alpha = 0.04f
                    )
                )
            ){
                Text(
                    "Cancel",
                    style = MaterialTheme.typography.button.copy(
                        color = MaterialTheme.colors.primary
                    ),
                    modifier = Modifier.padding(10.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                shape = RoundedCornerShape(32.dp),
                onClick = {
                    val (sortBy, sortOrder) = getSortByAndSortOrder(selectedSortOption)
                    val options = filterOptions.copy(
                        minPrice = if (sliderPosition.start == 0f) null else sliderPosition.start.toDouble(),
                        maxPrice = if (sliderPosition.endInclusive == 10000f) null else sliderPosition.endInclusive.toDouble(),
                        brand = if (selectedBrand == "All") null else selectedBrand,
                        category = if (selectedCategory == "All") null else selectedCategory,
                        sortBy = sortBy,
                        sortOrder = sortOrder
                    )
                    onApply(options)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor =  MaterialTheme.colors.primary
                ),
                modifier = Modifier.weight(1f),
            ){
                Text(
                    "Apply",
                    style = MaterialTheme.typography.button.copy(
                        color = MaterialTheme.colors.onPrimary
                    ),
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

fun getSortOption(sortBy: String, sortOrder: String): String{
    return when(sortBy){
        "price" -> {
            if (sortOrder == "asc"){
                "Price: Low to High"
            }else{
                "Price: High to Low"
            }
        }
        "rating" -> {
            if (sortOrder == "asc"){
                "Rating: Low to High"
            }else{
                "Rating: High to Low"
            }
        }
        "recency" -> {
            "Most Recent"
        }
        else -> {
            "Most Recent"
        }
    }
}

fun getSortByAndSortOrder(sortOption: String): Pair<String, String> {
    when(sortOption){
        "Most Recent" -> {
            return "recency" to "desc"
        }
        "Rating: Low to High" -> {
            return "rating" to "asc"
        }
        "Rating: High to Low" -> {
            return "rating" to "desc"
        }
        "Price: Low to High" -> {
            return "price" to "asc"
            }
        "Price: High to Low" -> {
            return "price" to "desc"
        }
        else -> {
            return "recency" to "desc"
        }
    }
}


@Composable
fun OptionsSection(
    modifier: Modifier = Modifier,
    title: String,
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
){
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(8.dp))
        FilterOptions(
            selectedOption = selectedOption,
            options = options,
            onOptionSelected = onOptionSelected
        )
    }
}

@Composable
fun FilterOptions(
    modifier: Modifier = Modifier,
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        options.forEach { option ->
            FilterOption(
                option = option,
                isSelected = option == selectedOption,
                onClick = { onOptionSelected(option) }
            )
        }
    }
}

@Composable
fun FilterOption(
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
        Text(
            text = option,
            color = if (isSelected) MaterialTheme.colors.background else MaterialTheme.colors.onBackground,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}



