package features.shop.presentation.screens.brand_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import features.shop.domain.models.Shoe
import features.shop.presentation.components.ShoesVerticalGrid

@Composable
fun BrandScreen(
    viewModel: BrandScreenViewModel,
    onNavigateBack: () -> Unit,
    onShoeClick: (Shoe) -> Unit,
    brandName: String?
){
    val uiState by viewModel.brandScreenState.collectAsState()

    LaunchedEffect(brandName){
        println("Brand screen $brandName")
        if (brandName != null){
            println("Update brand called with brand $brandName")
            viewModel.onEvent(BrandScreenEvent.OnUpdateBrand(brandName))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(brandName ?: "")
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
                            viewModel.onEvent(BrandScreenEvent.OnUpdateBrand(brandName ?: ""))
                        }
                    ) {
                        Icon(
                            Icons.Rounded.Tune,
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    ){
        ShoeListSection(
            modifier = Modifier.padding(it),
            shoeListState = uiState.brandShoeListState,
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
                onClick = onShoeClick
            )
        }
    }
}
