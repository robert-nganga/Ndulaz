package features.shop.presentation.screens.product_details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.automirrored.outlined.ArrowBackIos
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.rounded.AddShoppingCart
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState
import features.shop.domain.models.Brand
import features.shop.domain.models.Review
import features.shop.domain.models.Shoe
import features.shop.domain.models.ShoeVariant
import features.shop.presentation.sheets.AddToCartBottomSheet
import features.shop.presentation.sheets.AllReviewsBottomSheet
import features.shop.presentation.components.ExpandableText
import features.shop.presentation.components.RatingBar
import features.shop.presentation.components.Ratings
import features.shop.presentation.components.ReviewItem
import features.shop.presentation.utils.parseColorFromString
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch
import ndula.composeapp.generated.resources.Res
import ndula.composeapp.generated.resources.add_to_cart
import ndula.composeapp.generated.resources.description
import ndula.composeapp.generated.resources.out_of_stock
import ndula.composeapp.generated.resources.price
import ndula.composeapp.generated.resources.size
import ndula.composeapp.generated.resources.units_left
import ndula.composeapp.generated.resources.variation
import org.jetbrains.compose.resources.stringResource
import kotlin.math.round
import kotlin.math.roundToInt


@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToCart: () -> Unit
){
    val uiState by viewModel.productDetailsState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    val addToCartSheetState = rememberFlexibleBottomSheetState(
        isModal = true,
        skipIntermediatelyExpanded = false,
        skipSlightlyExpanded = true
    )

    val allReviewsSheetState = rememberFlexibleBottomSheetState(
        isModal = true,
        skipIntermediatelyExpanded = true,
        skipSlightlyExpanded = true
    )

    var showAllReviewsSheet by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMessage){
        uiState.snackBarMessage?.let { message ->
            snackBarHostState.showSnackbar(message)
            viewModel.onEvent(ProductDetailsEvent.OnResetError)
        }
    }

    when{
        uiState.product == null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        else -> {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize(),
                snackbarHost = {
                    SnackbarHost(snackBarHostState) { snackBarData ->
                        Snackbar(
                            snackbarData =  snackBarData,
                            backgroundColor = if (uiState.isError) MaterialTheme.colors.error else Color(0xFF188503),
                            actionColor = MaterialTheme.colors.surface,
                            contentColor = MaterialTheme.colors.onError
                        )
                    }
               },
                bottomBar = {
                    ProductDetailsBottomBar(
                        selectedVariation = uiState.selectedVariation,
                        onAddToCart = {
                            viewModel.onEvent(ProductDetailsEvent.OnAddToCart)
                        },
                        quantity = uiState.quantity,
                        onQuantityChanged = {
                            viewModel.onEvent(ProductDetailsEvent.OnQuantityChange(it))
                        }
                    )
                }
            ){ paddingValues ->
                if (uiState.showAddToCartSheet){
                    AddToCartBottomSheet(
                        sheetState = addToCartSheetState,
                        addToCartState = uiState.addToCartState,
                        onAddToCart = {
                           viewModel.onEvent(ProductDetailsEvent.OnSaveCartItem(it))
                        },
                        onNavigateBack = {
                            scope.launch {
                                addToCartSheetState.hide()
                            }.invokeOnCompletion {
                                viewModel.updateAddToCartSheetVisibility(false)
                                onNavigateBack()
                                viewModel.resetState()
                            }
                        },
                        onDismiss = {
                            scope.launch {
                                addToCartSheetState.hide()
                            }.invokeOnCompletion {
                                viewModel.updateAddToCartSheetVisibility(false)
                            }
                        },
                        onNavigateToCart = {
                            scope.launch {
                                addToCartSheetState.hide()
                            }.invokeOnCompletion {
                                viewModel.updateAddToCartSheetVisibility(false)
                                onNavigateToCart()
                                viewModel.resetState()
                            }
                        },
                    )
                }

                if (showAllReviewsSheet){
                    AllReviewsBottomSheet(
                        sheetState = allReviewsSheetState,
                        state = uiState.allReviewsState,
                        onDismiss = {
                            scope.launch {
                                allReviewsSheetState.hide()
                            }.invokeOnCompletion {
                                showAllReviewsSheet = false
                            }
                        }
                    )
                }

                ProductDetailsScreenContent(
                    modifier = Modifier.padding(paddingValues),
                    uiState = uiState,
                    onEvent = viewModel::onEvent,
                    onNavigateBack = {
                        viewModel.resetState()
                        onNavigateBack()
                    },
                    onWishListIconClick = {
                        viewModel.onEvent(ProductDetailsEvent.OnWishListIconClick)
                    },
                    onSeeAllReviews = {
                        viewModel.onEvent(ProductDetailsEvent.OnSeeMoreReviews)
                        showAllReviewsSheet = true
                    }
                )
            }
        }
    }
}

@Composable
fun ProductDetailsScreenContent(
    uiState: ProductDetailsState,
    modifier: Modifier = Modifier,
    onEvent: (ProductDetailsEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onWishListIconClick: () -> Unit,
    onSeeAllReviews: () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ){
            ImagesSection(
                images = uiState.product!!.images,
                selectedImage = uiState.selectedImage,
                onImageSelected = {
                    onEvent(ProductDetailsEvent.OnImageSelected(it))
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            ProductInfoSection(
                shoe = uiState.product
            )
            uiState.selectedVariation?.let {
                Spacer(modifier = Modifier.height(16.dp))
                VariationInfo(
                    variation = it
                )
            }
            // Find an efficient way to achieve this
            if (uiState.colors.size >1){
                Spacer(modifier = Modifier.height(16.dp))
                ColorSection(
                    colors = uiState.colors,
                    selectedColor = uiState.selectedColor,
                    onColorSelected = {
                        onEvent(ProductDetailsEvent.OnColorSelected(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            SizeSection(
                shoeSizes = uiState.sizes,
                selectedSize = uiState.selectedSize,
                onSizeSelected = {
                    onEvent(ProductDetailsEvent.OnSizeSelected(it))
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            DescriptionSection(
                description = uiState.product.description
            )
            Spacer(modifier = Modifier.height(16.dp))
            ReviewSection(
                featuredReviewsState = uiState.featuredReviewsState,
                onSeeAllReviews = onSeeAllReviews
            )
        }

        ProductDetailsTopAppBar(
            modifier = Modifier
                .padding(top = 10.dp)
                .align(Alignment.TopCenter),
            isShoeInWishList = uiState.product!!.isInWishList,
            onNavigateBack = onNavigateBack,
            onWishListIconClick = onWishListIconClick
        )
    }
}

@Composable
fun ReviewSection(
    modifier: Modifier = Modifier,
    featuredReviewsState: FeaturedReviewsState,
    onSeeAllReviews: () -> Unit
){
    Column(
        modifier = modifier
            .padding(
                horizontal = 16.dp
            )
    ){
        Text(
            "Reviews",
            style = MaterialTheme.typography.h6.copy(
                letterSpacing = 0.08.sp
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        when(featuredReviewsState){
            is FeaturedReviewsState.Error -> {
            }
            is FeaturedReviewsState.Loading -> {
            }
            is FeaturedReviewsState.Success -> {
                val totalReviews = featuredReviewsState.paginatedReview.totalCount
                val reviews = featuredReviewsState.paginatedReview.reviews
                Column(
                    modifier = Modifier
                        .fillMaxWidth()

                ){
                    ReviewHeaderSection(
                        reviews = reviews
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    reviews.forEachIndexed { index, review ->
                        ReviewItem(
                            review = review
                        )
                        if (index != reviews.size - 1){
                            Divider(
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colors.primary.copy(
                                    alpha = 0.15f
                                ),
                                thickness = 0.8.dp
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    if (totalReviews > 1){
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colors.onBackground,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    onSeeAllReviews()
                                }
                        ){
                            Text(
                                "See more",
                                modifier = Modifier
                                    .padding(10.dp),
                                style = MaterialTheme.typography.body2
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            is FeaturedReviewsState.Empty -> {
            }
        }
    }
}

@Composable
fun ReviewHeaderSection(
    modifier: Modifier = Modifier,
    reviews: List<Review>
) {
    val averageRating by remember(reviews) {
        mutableStateOf(reviews.getAverageRating())
    }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "$averageRating",
                style = MaterialTheme.typography.h3.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Ratings(
                rating = averageRating.toInt(),
                starColor = MaterialTheme.colors.primary,
                starSize = 15.dp
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                "(${reviews.size} reviews)",
                style = MaterialTheme.typography.body2
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            (5 downTo 1).forEach { rating ->
                RatingBar(
                    number = rating,
                    value = reviews.numberOfReviews(rating).toFloat() / reviews.size,
                )
            }
        }
    }
}


fun List<Review>.numberOfReviews(rating: Int): Int{
    return this.count { it.rating.toInt() == rating }
}

fun List<Review>.getAverageRating(): Double {
    return (round(this.map { it.rating }.average() * 10) / 10)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductDetailsTopAppBar(
    modifier: Modifier = Modifier,
    isShoeInWishList: Boolean,
    onNavigateBack: () -> Unit,
    onWishListIconClick: () -> Unit
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Card(
            shape = RoundedCornerShape(10.dp),
            onClick = onNavigateBack,
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 5.dp
        ){
            Icon(
                Icons.AutoMirrored.Outlined.ArrowBackIos,
                contentDescription = "",
                modifier = Modifier.padding(10.dp),
                tint = MaterialTheme.colors.onSurface
            )
        }

        Card(
            shape = RoundedCornerShape(10.dp),
            onClick = onWishListIconClick,
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 5.dp
        ){
            Icon(
                if (isShoeInWishList) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                contentDescription = "",
                modifier = Modifier.padding(10.dp),
                tint = MaterialTheme.colors.onSurface
            )
        }
    }
}

@Composable
fun ProductDetailsBottomBar(
    modifier: Modifier = Modifier,
    selectedVariation: ShoeVariant? = null,
    quantity: Int,
    onQuantityChanged: (Int) -> Unit,
    onAddToCart: ()-> Unit
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.background
            )
    ){
        Column {
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.primary.copy(
                    alpha = 0.15f
                ),
                thickness = 0.8.dp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 10.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                QuantityButtons(
                    quantity = quantity,
                    onQuantityChanged = onQuantityChanged
                )
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(18.dp),
                    onClick = onAddToCart,
                    enabled = selectedVariation != null
                ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Rounded.AddShoppingCart,
                            contentDescription = "",
                        )
                        Text(
                            stringResource(Res.string.add_to_cart),
                            modifier = Modifier.padding(
                                horizontal = 10.dp,
                                vertical = 10.dp
                            ),
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold
                            ),
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ColorSection(
    colors: List<String>,
    selectedColor: String,
    onColorSelected: (String)-> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .padding(
                horizontal = 16.dp
            )
    ) {
        Text(
            "Color",
            style = MaterialTheme.typography.h6.copy(
                letterSpacing = 0.08.sp
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        FlowRow {
            colors.forEach { color ->
                ColorItem(
                    color = color,
                    isSelected = color == selectedColor,
                    onColorSelected = {
                        onColorSelected(color)
                    }
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }

}

@Composable
fun ColorItem(
    modifier: Modifier = Modifier,
    color: String,
    isSelected: Boolean,
    onColorSelected: ()-> Unit
){
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(
                color = color.parseColorFromString()
            )
            .border(
                width = 1.5.dp,
                color = if (isSelected) MaterialTheme.colors.primary.copy( alpha = 0.6f) else MaterialTheme.colors.primary.copy( alpha = 0.2f),
                shape = CircleShape
            )
            .clickable { onColorSelected() },
        contentAlignment = Alignment.Center
    ){
        if (isSelected){
            Icon(
                Icons.Filled.Check,
                contentDescription = "",
                modifier = Modifier.size(30.dp),
                tint = if (color == "White") Color.Black else Color.White
            )
        }
    }
}

@Composable
fun QuantityButtons(
    modifier: Modifier = Modifier,
    onQuantityChanged: (Int)-> Unit,
    quantity: Int,
    paddingValues: PaddingValues? = null,
    boxSize: Dp = 35.dp
){
    Row(
        modifier = modifier
            .padding(
                paddingValues ?: PaddingValues(horizontal = 16.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(boxSize)
                .clip(CircleShape)
                .background(
                    color = MaterialTheme.colors.primary.copy(alpha = 0.5f)
                )
                .clickable { if (quantity > 1) onQuantityChanged(quantity - 1) },
            contentAlignment = Alignment.Center
        ){
            Text(
                "-",
                style = MaterialTheme.typography.h6.copy(
                    color = MaterialTheme.colors.onPrimary
                ),
                modifier = Modifier
                    .padding(2.dp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            quantity.toString(),
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .size(boxSize)
                .clip(CircleShape)
                .background(
                    color = MaterialTheme.colors.primary,
                )
                .clickable { onQuantityChanged(quantity + 1)},
            contentAlignment = Alignment.Center
        ){
            Text(
                "+",
                style = MaterialTheme.typography.h6.copy(
                    color = MaterialTheme.colors.onPrimary
                ),
                modifier = Modifier
                    .padding(2.dp)
            )
        }
    }
}

@Composable
fun ProductInfoSection(
    modifier: Modifier = Modifier,
    shoe: Shoe
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Ksh ${shoe.price}",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            shoe.name,
            style = MaterialTheme.typography.h6.copy(
                color = MaterialTheme.colors.onBackground.copy(
                    alpha = 0.8f
                ),
                letterSpacing = 0.08.sp
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(color = MaterialTheme.colors.primary.copy(alpha = 0.15f))
                    .padding(
                        horizontal = 8.dp,
                        vertical = 2.dp
                    )
            ){
                Text(
                    "23 sold",
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                Icons.AutoMirrored.Filled.StarHalf,
                contentDescription = "",
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                "4.6 (20 reviews)",
                style = MaterialTheme.typography.body2
            )
        }
        shoe.brand?.let {
            Spacer(modifier = Modifier.height(16.dp))
            BrandSection(
                brand = it
            )
        }
    }
}

@Composable
fun BrandSection(
    modifier: Modifier = Modifier,
    brand: Brand
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        KamelImage(
            asyncPainterResource(brand.logoUrl ?: ""),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape),
            colorFilter = ColorFilter.tint(
                color = MaterialTheme.colors.onBackground
            )
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            brand.name,
            style = MaterialTheme.typography.body2.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.width(5.dp))
        Icon(
            Icons.Filled.Verified,
            contentDescription = "",
            tint = Color.Blue,
            modifier = Modifier.size(12.dp)
        )
    }
}

@Composable
fun DescriptionSection(
    modifier: Modifier = Modifier,
    description: String,
){
    Column(
        modifier = modifier
            .padding(
                horizontal = 16.dp
            )
    ) {
        Text(
            stringResource(Res.string.description),
            style = MaterialTheme.typography.h6.copy(
                letterSpacing = 0.08.sp
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        ExpandableText(
            text = description
        )
    }
}


@Composable
fun VariationInfo(
    variation: ShoeVariant,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
        ) {
        Text(
            "${stringResource(Res.string.variation)}:",
            style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.08.sp
            )
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${stringResource(Res.string.price)}: ",
                    style = MaterialTheme.typography.body2.copy(
                        color = MaterialTheme.colors.onBackground.copy(
                            alpha = 0.6f
                        )
                    )
                )
                Text(
                    "Ksh ${variation.price}",
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.08.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Stock: ",
                    style = MaterialTheme.typography.body2.copy(
                        color = MaterialTheme.colors.onBackground.copy(
                            alpha = 0.6f
                        )
                    )
                )
                Text(
                    if (variation.quantity > 0) "${variation.quantity} ${stringResource(Res.string.units_left)}"
                        else stringResource(Res.string.out_of_stock),
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.08.sp
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SizeSection(
    modifier: Modifier = Modifier,
    shoeSizes: List<Int>,
    onSizeSelected: (Int)-> Unit,
    selectedSize: Int
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ){
        Text(
            stringResource(Res.string.size),
            style = MaterialTheme.typography.h6.copy(
                letterSpacing = 0.08.sp
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            shoeSizes.forEach { shoeSize ->
                SizeItem(
                    size = shoeSize,
                    isSelectable = true,
                    isSelected = shoeSize == selectedSize,
                    onSizeSelected = {
                        onSizeSelected(shoeSize)
                    }
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}

@Composable
fun SizeItem(
    size: Int,
    modifier: Modifier = Modifier,
    isSelectable: Boolean,
    isSelected: Boolean,
    onSizeSelected: ()-> Unit
){
    val textColor = if (isSelected) {
        MaterialTheme.colors.onPrimary
    } else {
        if (isSelectable){
            MaterialTheme.colors.primary
        } else{
            MaterialTheme.colors.primary.copy(alpha = 0.3f)
        }
    }
    Box(
        modifier = modifier
            .border(
                width = 1.5.dp,
                color = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.primary.copy(alpha = 0.25f),
                shape = RoundedCornerShape(4.dp)
            )
            .clip(RoundedCornerShape(4.dp))
            .background(
                color = if (isSelected) MaterialTheme.colors.primary else Color.Transparent
            )
            .isSelectable(
                isSelectable = isSelectable,
                onClick = onSizeSelected
            ),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "EU $size",
            color = textColor,
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 6.dp
            )
        )
    }
}

fun Modifier.isSelectable(isSelectable: Boolean, onClick: ()-> Unit): Modifier {
    return if (isSelectable) {
        this.clickable { onClick() }
    } else {
        this
    }
}


@Composable
fun ImagesSection(
    modifier: Modifier = Modifier,
    selectedImage: String,
    images: List<String>,
    onImageSelected: (String) -> Unit
){
    Column (
        modifier = modifier
            .fillMaxWidth(),
    ){
        KamelImage(
            asyncPainterResource(selectedImage),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            onLoading = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray.copy(alpha = 0.2f)),
                )
            },
            modifier = Modifier
                .height(240.dp)
                .fillMaxWidth()
                .background(color = Color.Gray.copy(alpha = 0.4f))
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            images.forEach { image ->
                KamelImage(
                    asyncPainterResource(image),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    onLoading = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Gray.copy(alpha = 0.2f)),
                        )
                    },
                    modifier = Modifier
                        .height(60.dp)
                        .width(60.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = Color.Gray.copy(alpha = 0.55f))
                        .border(
                            width = 1.5.dp,
                            color = if(selectedImage == image) Color.Blue else Color.Gray.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            onImageSelected(image)
                        }
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }


}