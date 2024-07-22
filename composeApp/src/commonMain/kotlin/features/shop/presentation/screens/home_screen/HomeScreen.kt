package features.shop.presentation.screens.home_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import features.profile.domain.models.User
import features.shop.domain.models.Brand
import features.shop.domain.models.Shoe
import features.shop.presentation.components.CategoryItem
import features.shop.presentation.components.HomeScreenBrandItem
import features.shop.presentation.utils.getFirstName
import features.shop.presentation.utils.getGreetings
import features.shop.presentation.utils.getInitials
import ndula.composeapp.generated.resources.Res
import ndula.composeapp.generated.resources.banner1
import ndula.composeapp.generated.resources.banner2
import ndula.composeapp.generated.resources.banner3
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import features.shop.presentation.components.ShoesVerticalGrid


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    onProductClick: (Shoe) -> Unit,
) {

    val uiState by viewModel.homeScreenState.collectAsState()
    val user by viewModel.user.collectAsState()

    val pagerState = rememberPagerState{ 3 }

    LaunchedEffect(Unit){
        viewModel.onEvent(HomeScreenEvents.OnFetchBrands)
        viewModel.onEvent(HomeScreenEvents.OnFetchCategories)
        viewModel.onEvent(HomeScreenEvents.OnSelectCategory("All"))
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ){
        HomeScreenAppBar(
            onSearchClick = {},
            user = user,
            onNotificationClick = {
                viewModel.logout()
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        BannersSection(
            banners = listOf(
                Res.drawable.banner3,
                Res.drawable.banner2,
                Res.drawable.banner1
            ),
            pagerState = pagerState,
            onBannerClick = {}
        )
        Spacer(modifier = Modifier.height(10.dp))
        BrandsSection(
            brandsState = uiState.brandsState,
            onSeeAllClick = {},
            onItemClick = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        CategoriesSection(
            categoriesState = uiState.categoriesState,
            selectedCategory = uiState.selectedCategory,
            onCategorySelected = {
                viewModel.onEvent(HomeScreenEvents.OnSelectCategory(it))
            },
            onSeeAllClick = {}
        )
        PopularShoesSection(
            modifier = Modifier.fillMaxSize(),
            popularShoesState = uiState.popularShoesState,
            onItemClick = onProductClick,
        )
    }
}

@Composable
fun BrandsSection(
    modifier: Modifier = Modifier,
    brandsState: BrandsState,
    onSeeAllClick: () -> Unit,
    onItemClick: (Brand) -> Unit
){
    when(brandsState){
        is BrandsState.Error -> {}
        is BrandsState.Loading -> {}
        is BrandsState.Success -> {
            val brands = brandsState.brands
            Column(
                modifier = modifier
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        "Brands",
                        style = MaterialTheme.typography.h6
                    )
                    TextButton(
                        onClick = onSeeAllClick
                    ){
                        Text(
                            "See all",
                            style = MaterialTheme.typography.body2,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                ) {
                    brands.forEach { brand ->
                        HomeScreenBrandItem(
                            brand = brand,
                            onItemClick = {
                                onItemClick(brand)
                            }
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun PopularShoesSection(
    modifier: Modifier = Modifier,
    popularShoesState: PopularShoesState,
    onItemClick: (Shoe)-> Unit,
){
    when(popularShoesState){
        is PopularShoesState.Error -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = popularShoesState.errorMessage,
                    style = MaterialTheme.typography.body2
                )
            }
        }
        is PopularShoesState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        is PopularShoesState.Success -> {
            val shoes = popularShoesState.shoes
            ShoesVerticalGrid(
                modifier = modifier,
                shoes = shoes,
                onClick = onItemClick
            )
        }
        is PopularShoesState.Idle -> {

        }
    }

}

@Composable
fun CategoriesSection(
    modifier: Modifier = Modifier,
    categoriesState: CategoriesState,
    selectedCategory: String,
    onCategorySelected: (String)-> Unit,
    onSeeAllClick: () -> Unit
){
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Most popular",
                style = MaterialTheme.typography.h6
            )
            TextButton(
                onClick = onSeeAllClick
            ){
                Text(
                    "See all",
                    style = MaterialTheme.typography.body2,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        when(categoriesState){
            is CategoriesState.Error -> {}
            is CategoriesState.Loading -> {
                // Show loading indicator
            }
            is CategoriesState.Success -> {
                val categories = categoriesState.categories
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
        }
    }
}

@Composable
fun HomeScreenAppBar(
    modifier: Modifier = Modifier,
    user: User?,
    onSearchClick: ()-> Unit,
    onNotificationClick: ()-> Unit
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        user?.let { user ->
            GreetingsSection(
                name = user.name,
                modifier = Modifier.weight(1f)
            )
        }
        Row {
            IconButton(
                onClick = onSearchClick
            ){
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search"
                )
            }
            IconButton(
                onClick = onNotificationClick
            ){
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications"
                )
            }
        }

    }
}

@Composable
fun GreetingsSection(
    modifier: Modifier = Modifier,
    name: String
){
    val initials by remember(name) { mutableStateOf(name.getInitials()) }
    val firstName by remember(name) { mutableStateOf(name.getFirstName())  }
    val greetings by remember { mutableStateOf(getGreetings()) }
    LaunchedEffect(Unit){
        println(getGreetings())
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colors.primary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ){
            Text(
                initials,
                style = MaterialTheme.typography.body2.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(14.dp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                greetings,
                style = MaterialTheme.typography.body2
            )
            Text(
                "$firstName \uD83D\uDC4B",
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.Bold
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

