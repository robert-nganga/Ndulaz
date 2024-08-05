package features.shop.presentation.screens.all_brands_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import features.shop.domain.models.Brand
import features.shop.presentation.components.BrandItem

@Composable
fun AllBrandsScreen(
    viewModel: AllBrandsViewModel,
    onBrandClick: (Brand) -> Unit,
    onNavigateBack: () -> Unit
){
    val uiState by viewModel.allBrandsState.collectAsState()

    LaunchedEffect(Unit){
        viewModel.fetchAllBrands()
    }

    Scaffold(
        topBar ={
            TopAppBar(
                title = {
                    Text("All brands")
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
                }
            )
        }
    ){
        when(uiState){
            is AllBrandsState.Failure -> {
                Box(
                    modifier = Modifier
                        .padding(it)
                        .padding(16.dp)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Text(text = "Something went wrong")
                }
            }
            is AllBrandsState.Loading -> {
                Box(
                    modifier = Modifier
                        .padding(it)
                        .padding(16.dp)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator()
                }
            }
            is AllBrandsState.Success -> {
                val brands = (uiState as AllBrandsState.Success).brands
                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize(),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(11.dp)
                ){
                    items(brands.size) { index ->
                        BrandItem(
                            brand = brands[index],
                            onBrandClick = onBrandClick
                        )
                    }
                }
            }
        }
    }

}