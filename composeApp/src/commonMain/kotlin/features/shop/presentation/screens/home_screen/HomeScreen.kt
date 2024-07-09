package features.shop.presentation.screens.home_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import features.shop.domain.models.Shoe
import features.shop.presentation.components.CategoryItem
import ndula.composeapp.generated.resources.Res
import ndula.composeapp.generated.resources.banner1
import ndula.composeapp.generated.resources.banner2
import ndula.composeapp.generated.resources.banner3
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import presentation.components.ShoeList


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    onProductClick: (Shoe) -> Unit,
) {

    val uiState by viewModel.homeScreenState.collectAsState()

    val pagerState = rememberPagerState{
        3
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        HomeScreenAppBar(
            onSearchClick = {},
            onNotificationClick = {
                viewModel.logout()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        BannersSection(
            banners = listOf(
                Res.drawable.banner3,
                Res.drawable.banner2,
                Res.drawable.banner1
            ),
            pagerState = pagerState,
            onBannerClick = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
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
                    onClick = {
                        onProductClick(it)
                    }
                )
            }

            is HomeScreenShoesState.Idle -> {
                Text(
                    "Idle"
                )
            }
        }

    }

}

@Composable
fun HomeScreenAppBar(
    modifier: Modifier = Modifier,
    onSearchClick: ()-> Unit,
    onNotificationClick: ()-> Unit
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp
            )
    ){
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    color = MaterialTheme.colors.primary.copy(alpha = 0.2f)
                )
                .clickable { onSearchClick() }
        ){
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    )
            ){
                Icon(
                    Icons.Outlined.Search,
                    contentDescription = "Search Icon"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    "Search"
                )
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(
                    color = MaterialTheme.colors.primary.copy(alpha = 0.2f)
                )
                .clickable { onNotificationClick() }
        ){
            Icon(
                Icons.Outlined.Notifications,
                contentDescription = "Search Icon",
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    )
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannersSection(
    modifier: Modifier = Modifier,
    banners: List<DrawableResource> = emptyList(),
    pagerState: PagerState,
    onBannerClick: () -> Unit
){
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        HorizontalPager(
            state = pagerState,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(160.dp)
                .clip(RoundedCornerShape(24.dp)),
            pageSpacing = 10.dp
        ){ index ->
            Image(
                painter = painterResource(resource = banners[index]),
                contentDescription = "Banner",
                modifier = modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp))
                    .clickable { onBannerClick() },
                contentScale = ContentScale.Crop,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        BannerPagerIndicator(
            pageCount = banners.size,
            currentPage = pagerState.currentPage
        )
    }


}

@Composable
fun BannerPagerIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int,
    currentPage: Int
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ){
        (0..<pageCount).forEach {
            Box(
                modifier = Modifier
                    .width(24.dp)
                    .height(8.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        color = if (it == currentPage) MaterialTheme.colors.primary else MaterialTheme.colors.primary.copy(alpha = 0.2f)
                    )
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }

}

