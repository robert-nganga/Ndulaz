package features.shop.presentation.screens.wish_list_screen

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
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import features.shop.domain.models.Shoe
import features.shop.presentation.components.ConfirmDialog
import features.shop.presentation.components.ShoesVerticalGrid


@Composable
fun WishListScreen(
    viewModel: WishListViewModel,
    onShoeClick: (Shoe) -> Unit
){
    val uiState by viewModel.wishListState.collectAsState()

    var showClearWishListDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit){
//        if(uiState is WishListScreenState.Failure){
//            viewModel.fetchMyWishList()
//        }
        viewModel.fetchMyWishList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text( "My Wish List")
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                actions = {
                    IconButton(
                        onClick = {
                            showClearWishListDialog = true
                        }
                    ) {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    ){
        if (showClearWishListDialog){
            ConfirmDialog(
                title = "Clear wishlist",
                message = "Are you sure you want to clear the wishlist",
                confirmButtonText = "Yes",
                dismissButtonText = "No",
                onDismiss = {
                    showClearWishListDialog = false
                },
                onConfirm = {
                    viewModel.clearMyWishList()
                    showClearWishListDialog = false
                }
            )
        }
        WishListScreenContent(
            wishListState = uiState,
            onShoeClick = onShoeClick,
            modifier = Modifier.padding(it)
        )
    }

}

@Composable
fun WishListScreenContent(
    wishListState: WishListScreenState,
    modifier: Modifier = Modifier,
    onShoeClick: (Shoe) -> Unit
){
    when(wishListState){
        is WishListScreenState.Failure -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    wishListState.message
                )
            }
        }
        is WishListScreenState.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        is WishListScreenState.Success -> {
            ShoesVerticalGrid(
                modifier = modifier,
                shoes = wishListState.items.map { it.shoe },
                onClick = onShoeClick,
                onWishListClicked = {}
            )
        }

        is WishListScreenState.Empty -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
                ){
                Text(
                    "Your wishlist is empty"
                )
            }
        }
    }
}