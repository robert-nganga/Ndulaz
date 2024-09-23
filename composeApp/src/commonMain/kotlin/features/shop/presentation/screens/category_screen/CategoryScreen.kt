package features.shop.presentation.screens.category_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState
import features.shop.domain.models.Category
import features.shop.presentation.components.ShoesVerticalGrid
import features.shop.presentation.screens.most_popular_screen.MostPopularScreenEvents
import features.shop.presentation.sheets.SortAndFilterBottomSheet
import kotlinx.coroutines.launch


@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel,
    onNavigateBack: () -> Unit,
    category: Category
){

    val state by viewModel.categoryScreenState.collectAsState()

    val sortAndFilterSheetState = rememberFlexibleBottomSheetState(
        isModal = true,
        skipIntermediatelyExpanded = false,
        skipSlightlyExpanded = true
    )

    var showSortAndFilterSheet by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit){
        val currentOptions = state.filterOptions
        viewModel.updateFilterOptions(
            filterOptions = currentOptions.copy(category = category.name)
        )
        viewModel.fetchAllBrands()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        category.name,
                        style = MaterialTheme.typography.h6.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                elevation = 0.dp,
                backgroundColor = MaterialTheme.colors.background,
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack
                    ){
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBackIos,
                            contentDescription = ""
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            showSortAndFilterSheet = true
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
        },
        backgroundColor = MaterialTheme.colors.onBackground.copy(
            alpha = 0.035f
        )
    ){
        if (showSortAndFilterSheet){
            SortAndFilterBottomSheet(
                modifier = Modifier,
                filterOptions = state.filterOptions,
                onDismiss = {
                    scope.launch {
                        sortAndFilterSheetState.hide()
                    }.invokeOnCompletion {
                        showSortAndFilterSheet = false
                    }
                },
                sheetState = sortAndFilterSheetState,
                onApply = { options ->
                    scope.launch {
                        sortAndFilterSheetState.hide()
                    }.invokeOnCompletion {
                        showSortAndFilterSheet = false
                        viewModel.updateFilterOptions(options)
                    }
                },
                brands = state.brands.ifEmpty { null },
            )
        }
        when(state.categoryScreenShoesState){
            is CategoryScreenShoesState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = (state.categoryScreenShoesState as CategoryScreenShoesState.Error).message,
                        color = MaterialTheme.colors.error
                    )
                }
            }
            is CategoryScreenShoesState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is CategoryScreenShoesState.Success -> {
                val shoes = (state.categoryScreenShoesState as CategoryScreenShoesState.Success).shoes
                ShoesVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    shoes = shoes,
                    onClick = {},
                    onWishListClicked = {}
                )
            }
        }

    }

}