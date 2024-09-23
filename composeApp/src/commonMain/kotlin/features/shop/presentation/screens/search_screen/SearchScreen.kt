package features.shop.presentation.screens.search_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import features.shop.domain.models.Brand
import features.shop.domain.models.Category
import features.shop.domain.models.Shoe
import features.shop.presentation.components.ShoesVerticalGrid
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource


@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onNavigateBack: () -> Unit,
    onShoeClick: (Shoe) -> Unit,
    onBrandClicked: (Brand) -> Unit,
    onCategoryClicked: (Category) -> Unit,
){
    val uiState by viewModel.searchState.collectAsState()

    val searchQuery by viewModel.query.collectAsState()

    val isSearchSuccessful by remember(uiState){
        mutableStateOf(uiState is SearchScreenState.Success)
    }
    val resultsTotal by remember(uiState){
        mutableStateOf(
            when(uiState){
                is SearchScreenState.Success -> (uiState as SearchScreenState.Success).results.size
                else -> 0
            }
        )
    }

    LaunchedEffect(Unit){
        viewModel.fetchAllBrands()
        viewModel.fetchAllCategories()
    }

    Scaffold(
        topBar = {
            SearchScreenAppBar(
                onNavigateBack = onNavigateBack,
                query = searchQuery,
                onQueryChange = viewModel::onQueryChange,
                isSearchSuccessful = isSearchSuccessful,
                resultsTotal = resultsTotal,
            )
        },
        backgroundColor = MaterialTheme.colors.onBackground.copy(
            alpha = 0.04f
        )
    ){
        SearchScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            state = uiState,
            onShoeClick = onShoeClick,
            onWishListClicked = {
            },
            onBrandClicked = onBrandClicked,
            onCategoryClicked = onCategoryClicked
        )
    }
}

@Composable
fun SearchScreenContent(
    modifier: Modifier = Modifier,
    state: SearchScreenState,
    onShoeClick: (Shoe) -> Unit,
    onWishListClicked: (Shoe) -> Unit,
    onBrandClicked: (Brand) -> Unit,
    onCategoryClicked: (Category) -> Unit,
){
    Column(
        modifier = modifier
    ){
        when(state){
            is SearchScreenState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize()
                ){
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            is SearchScreenState.Success -> {
                val shoes = state.results
                ShoesVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize(),
                    shoes = shoes,
                    onClick = onShoeClick,
                    onWishListClicked = onWishListClicked
                )
            }
            is SearchScreenState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize()
                ){
                    Text(
                        text = state.error,
                    )
                }
            }

            is SearchScreenState.Idle -> {
                val brands = state.brands
                val categories = state.categories
                BrandsAndCategoriesSection(
                    brands = brands,
                    categories = categories,
                    onBrandClicked = onBrandClicked,
                    onCategoryClicked = onCategoryClicked
                )
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BrandsAndCategoriesSection(
    modifier: Modifier = Modifier,
    brands: List<Brand>,
    categories: List<Category>,
    onBrandClicked: (Brand) -> Unit,
    onCategoryClicked: (Category) -> Unit,
){
    Column(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
                vertical = 10.dp
            )
            .verticalScroll(rememberScrollState())
    ){
        Text(
            text = "Brands",
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            brands.forEach { brand ->
                BrandInfoItem(
                    brand = brand,
                    onItemClick = {
                        onBrandClicked(brand)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Categories",
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            maxItemsInEachRow = 2
        ) {
            categories.forEach { category ->
                CategoryInfoItem(
                    category = category,
                    onItemClick = {
                        onCategoryClicked(category)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.48f)
                )
            }
        }
    }
}

@Composable
fun BrandInfoItem(
    brand: Brand,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .padding(horizontal = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(
                    color = MaterialTheme.colors.onBackground.copy(
                        alpha = 0.12f
                    )
                )
                .clickable { onItemClick() }
        ){
            KamelImage(
                resource = asyncPainterResource(brand.logoUrl ?: ""),
                contentDescription = "${brand.name} logo",
                modifier = Modifier
                    .size(60.dp)
                    .padding(10.dp),
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colors.onBackground
                )
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = brand.name,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryInfoItem(
    category: Category,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier,
        onClick = onItemClick,
        shape = RoundedCornerShape(16.dp),
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            KamelImage(
                resource = asyncPainterResource(category.image),
                contentDescription = "${category.name} image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = category.name,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun SearchScreenAppBar(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    query: String,
    onQueryChange: (String) -> Unit,
    isSearchSuccessful: Boolean,
    resultsTotal: Int,
){
    Column(
        modifier = modifier
            .background(MaterialTheme.colors.background)
            .padding(
                vertical = 16.dp
            )
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onNavigateBack
            ){
                Icon(
                    Icons.AutoMirrored.Default.ArrowBackIos,
                    contentDescription = "Back icon"
                )
            }
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                placeholder = {
                    Text(text = "Search Shoe")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        end = 6.dp
                    ),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.primary.copy(alpha = 0.5f),
                    unfocusedBorderColor = Color.Transparent,
                    backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.08f)
                ),
                shape = RoundedCornerShape(16.dp),
            )

        }
        if (isSearchSuccessful){
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp
                    )
            ){
                Text(
                    "Results for \"$query\"",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "$resultsTotal found",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }

}