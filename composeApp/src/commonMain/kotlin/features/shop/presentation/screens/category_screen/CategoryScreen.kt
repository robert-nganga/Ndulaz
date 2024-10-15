package features.shop.presentation.screens.category_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import features.shop.domain.models.Category
import features.shop.presentation.components.ShoesVerticalGrid
import features.shop.presentation.sheets.SortAndFilterBottomSheet
import kotlinx.coroutines.launch


@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel,
    onNavigateBack: () -> Unit,
    category: Category
){

    val state by viewModel.categoryScreenState.collectAsState()

    val sortAndFilterSheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit){
        val currentOptions = state.filterOptions
        viewModel.updateFilterOptions(
            filterOptions = currentOptions.copy(category = category.name)
        )
        viewModel.fetchAllBrands()
    }

    ModalBottomSheetLayout(
        sheetContent = {
            SortAndFilterBottomSheet(
                modifier = Modifier,
                filterOptions = state.filterOptions,
                onDismiss = {
                    scope.launch {
                        sortAndFilterSheetState.hide()
                    }
                },
                onApply = { options ->
                    scope.launch {
                        sortAndFilterSheetState.hide()
                    }.invokeOnCompletion {
                        viewModel.updateFilterOptions(options.copy(category = category.name))
                    }
                },
                brands = state.brands.ifEmpty { null },
            )
        },
        scrimColor = Color.Black.copy(alpha = 0.2f),
        sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        sheetState = sortAndFilterSheetState,
        sheetBackgroundColor = MaterialTheme.colors.background,
    ){
        Scaffold(
            topBar = {
                CategoryScreenTopAppBar(
                    title = category.name,
                    onNavigateBack = onNavigateBack,
                    onFilterClicked = {
                        scope.launch {
                            sortAndFilterSheetState.show()
                        }
                    }
                )
            },
            backgroundColor = MaterialTheme.colors.onBackground.copy(
                alpha = 0.035f
            )
        ){
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
}

@Composable
fun CategoryScreenTopAppBar(
    title: String,
    onNavigateBack: () -> Unit,
    onFilterClicked: () -> Unit

){
    TopAppBar(
        title = {
            Text(
                title,
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
                onClick = onFilterClicked
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