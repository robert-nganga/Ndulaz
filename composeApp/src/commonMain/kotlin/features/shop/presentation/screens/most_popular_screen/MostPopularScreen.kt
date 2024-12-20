package features.shop.presentation.screens.most_popular_screen

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import features.shop.domain.models.Shoe
import features.shop.presentation.components.CategoryItem
import features.shop.presentation.components.ShoesVerticalGrid
import features.shop.presentation.screens.home_screen.PopularShoesState
import features.shop.presentation.sheets.SortAndFilterBottomSheet
import kotlinx.coroutines.launch

@Composable
fun MostPopularScreen(
    viewModel: MostPopularScreenViewModel,
    onShoeClick: (Shoe) -> Unit,
    onNavigateBack: () -> Unit
){

    val uiState by viewModel.mostPopularScreenState.collectAsState()

    val sortAndFilterSheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (uiState.categories.isEmpty()){
            viewModel.onEvent(MostPopularScreenEvents.OnFetchCategories)
        }
        if (uiState.brands.isEmpty()){
            viewModel.onEvent(MostPopularScreenEvents.OnFetchBrands)
        }
        viewModel.onEvent(MostPopularScreenEvents.OnFilterOptionsChange(uiState.filterOptions))
    }

    ModalBottomSheetLayout(
        sheetContent = {
            SortAndFilterBottomSheet(
                modifier = Modifier,
                filterOptions = uiState.filterOptions,
                onDismiss = {
                    scope.launch {
                        sortAndFilterSheetState.hide()
                    }.invokeOnCompletion {
                        //showSortAndFilterSheet = false
                    }
                },
                onApply = { options ->
                    scope.launch {
                        sortAndFilterSheetState.hide()
                    }.invokeOnCompletion {
                        //showSortAndFilterSheet = false
                        viewModel.onEvent(MostPopularScreenEvents.OnFilterOptionsChange(options))
                    }
                },
                brands = uiState.brands.ifEmpty { null },
            )
        },
        scrimColor = Color.Black.copy(alpha = 0.2f),
        sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        sheetState = sortAndFilterSheetState,
        sheetBackgroundColor = MaterialTheme.colors.background,
    ){
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Most Popular",
                            style = MaterialTheme.typography.h5
                        )
                    },
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,
                    navigationIcon = {
                        IconButton(
                            onClick = onNavigateBack
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                                contentDescription = "Back"
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    sortAndFilterSheetState.show()
                                }
                            }
                        ){
                            Icon(
                                Icons.Rounded.Tune,
                                contentDescription = "Filter icon",
                                tint = MaterialTheme.colors.onBackground
                            )
                        }
                    }
                )
            }
        ){ paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ){
                Spacer(modifier = Modifier.height(10.dp))
                MostPopularCategoriesSection(
                    categories = uiState.categories,
                    selectedCategory = uiState.filterOptions.category ?: "All",
                    onCategorySelected = {
                        val filterOptions = uiState.filterOptions.copy(
                            category = if (it == "All") null else it
                        )
                        viewModel.onEvent(MostPopularScreenEvents.OnFilterOptionsChange(filterOptions))
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                ShoesListSection(
                    popularShoesState = uiState.popularShoesState,
                    onShoeClick = onShoeClick
                )
            }
        }
    }

}

@Composable
fun MostPopularCategoriesSection(
    modifier: Modifier = Modifier,
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        categories.forEach { category ->
            CategoryItem(
                category = category,
                isSelected = category == selectedCategory,
                onClick = {
                    onCategorySelected(category)
                }
            )
        }
    }
}

@Composable
fun ShoesListSection(
    modifier: Modifier = Modifier,
    popularShoesState: PopularShoesState,
    onShoeClick: (Shoe) -> Unit
){
    when(popularShoesState){
        is PopularShoesState.Error -> {
            val errorMessage = popularShoesState.errorMessage
            Box(
                modifier = modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    errorMessage,
                )
            }
        }
        is PopularShoesState.Idle -> {}
        is PopularShoesState.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        is PopularShoesState.Success -> {
            ShoesVerticalGrid(
                modifier = modifier,
                shoes = popularShoesState.shoes,
                onClick = onShoeClick,
                onWishListClicked = {}
            )
        }
    }
}