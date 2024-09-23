package features.shop.presentation.screens.brand_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.outlined.FilterList
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
import androidx.compose.ui.unit.dp
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState
import features.shop.domain.models.Brand
import features.shop.domain.models.Shoe
import features.shop.presentation.components.BrandItem
import features.shop.presentation.components.ShoesVerticalGrid
import features.shop.presentation.sheets.SortAndFilterBottomSheet
import features.shop.presentation.utils.NavigationUtils
import kotlinx.coroutines.launch

@Composable
fun BrandScreen(
    viewModel: BrandScreenViewModel,
    onNavigateBack: () -> Unit,
    onShoeClick: (Shoe) -> Unit,
    brand: Brand = NavigationUtils.brand
){
    val uiState by viewModel.brandScreenState.collectAsState()

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
        val currentOptions = uiState.filterOptions
        viewModel.updateFilterOptions(currentOptions.copy(brand = brand.name))
        viewModel.fetchAllCategories()
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Brand")
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack
                    ){
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBackIos,
                            contentDescription = "Back icon"
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
                            Icons.Outlined.FilterList,
                            contentDescription = "Back icon"
                        )
                    }
                }
            )
        }
    ){
        if (showSortAndFilterSheet){
            SortAndFilterBottomSheet(
                modifier = Modifier,
                filterOptions = uiState.filterOptions,
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
                categories = uiState.categories.ifEmpty { null }
            )
        }

        BrandScreenContent(
            modifier = Modifier.padding(it),
            brandState = uiState,
            brand = brand,
            onShoeClick = onShoeClick
        )
    }
}

@Composable
fun BrandScreenContent(
    modifier: Modifier = Modifier,
    brandState: BrandScreenState,
    brand: Brand,
    onShoeClick: (Shoe) -> Unit
){
    Column(
        modifier = modifier.fillMaxSize()
    ){
        Spacer(modifier = Modifier.height(16.dp))
        BrandItem(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            brand = brand,
            onBrandClick = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        ShoeListSection(
            modifier = Modifier.weight(1f),
            shoeListState = brandState.brandShoeListState,
            onShoeClick = onShoeClick
        )
    }
}

@Composable
fun ShoeListSection(
    modifier: Modifier = Modifier,
    shoeListState: BrandShoeListState,
    onShoeClick: (Shoe) -> Unit
){
    when(shoeListState){
        is BrandShoeListState.Failure -> {
            Box(
                modifier = modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    shoeListState.message,
                )
            }
        }
        is BrandShoeListState.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        is BrandShoeListState.Success -> {
            ShoesVerticalGrid(
                modifier = modifier,
                shoes = shoeListState.shoes,
                onClick = onShoeClick,
                onWishListClicked = {}
            )
        }
    }
}
