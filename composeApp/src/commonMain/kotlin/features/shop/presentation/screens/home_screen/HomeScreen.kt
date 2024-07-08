package features.shop.presentation.screens.home_screen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import features.shop.presentation.components.CategoryItem
import presentation.components.ShoeList


@Composable
fun HomeScreen(viewModel: HomeScreenViewModel) {

    val uiState by viewModel.homeScreenState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.fillMaxHeight(0.3f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            uiState.categories.forEach { category ->
                CategoryItem(
                    category = category,
                    isSelected = category == uiState.selectedCategory,
                    onClick = {
                        viewModel.selectedCategory(category)
                    }
                )
            }

        }
        when(uiState.shoesState){
            is HomeScreenShoesState.Error -> {
                Text(
                    text = (uiState.shoesState as HomeScreenShoesState.Error).errorMessage,
                )
            }
            is HomeScreenShoesState.Loading -> {
                CircularProgressIndicator()
            }
            is HomeScreenShoesState.Success -> {
                val shoes = (uiState.shoesState as HomeScreenShoesState.Success).shoes
                ShoeList(
                    shoes = shoes,
                    onClick = {}
                )
            }

            is HomeScreenShoesState.Idle -> {
                Text(
                    "Idle"
                )
            }
        }

    }


//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//    ) {
//        Button(
//            onClick = {
//                viewModel.logout()
//            },
//            modifier = Modifier.align(Alignment.TopEnd)
//        ){
//            Text(text = "Log out")
//        }
//
//        Column(
//            modifier = Modifier.align(Alignment.Center)
//        ){
//            Button(
//                onClick = {
//                    viewModel.getShoes()
//                },
//
//            ){
//                Text(text = "Get shoes")
//            }
//            Spacer(modifier = Modifier.height(24.dp))
//            when(uiState.shoesState){
//                is HomeScreenShoesState.Error -> {
//                    Text(
//                        text = (uiState.shoesState as HomeScreenShoesState.Error).errorMessage,
//                    )
//                }
//                is HomeScreenShoesState.Loading -> {
//                    CircularProgressIndicator()
//                }
//                is HomeScreenShoesState.Success -> {
//                    Text(
//                        text = (uiState.shoesState as HomeScreenShoesState.Success).shoes.toString()
//                    )
//                }
//
//                is HomeScreenShoesState.Idle -> {}
//            }
//        }
//    }
}

