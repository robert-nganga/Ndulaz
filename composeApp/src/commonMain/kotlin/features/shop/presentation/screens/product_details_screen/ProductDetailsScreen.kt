package features.shop.presentation.screens.product_details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.automirrored.outlined.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import features.shop.domain.models.Shoe
import features.shop.domain.models.Size
import features.shop.presentation.components.ExpandableText
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import ndula.composeapp.generated.resources.Res
import ndula.composeapp.generated.resources.add_to_cart
import ndula.composeapp.generated.resources.description
import ndula.composeapp.generated.resources.quantity
import ndula.composeapp.generated.resources.size
import ndula.composeapp.generated.resources.total_price
import org.jetbrains.compose.resources.stringResource


@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel,
    onNavigateBack: () -> Unit
){

    val uiState by viewModel.productDetailsState.collectAsState()

    when{
        uiState.product == null ->{
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        else -> {
            Scaffold(
                bottomBar = {
                    ProductDetailsBottomBar(
                        totalPrice = "Ksh 1000.00",
                        onAddToCart = {}
                    )
//                    BottomAppBar(
//                        modifier = Modifier.fillMaxWidth(),
//                        backgroundColor = MaterialTheme.colors.background
//                    ) {
//                        ProductDetailsBottomBar(
//                            totalPrice = "Ksh 1000.00",
//                            onAddToCart = {}
//                        )
//                    }
                }
            ){
                ProductDetailsScreenContent(
                    uiState = uiState,
                    onEvent = viewModel::onEvent,
                    onNavigateBack = onNavigateBack
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
    onNavigateBack: () -> Unit
){
    Column(
        modifier = modifier.fillMaxSize()
    ){
        Spacer(modifier = Modifier.height(10.dp))
        ImagesSection(
            images = uiState.product!!.images,
            onNavigateBack = onNavigateBack
        )
        Spacer(modifier = Modifier.height(16.dp))
        ProductInfoSection(
            shoe = uiState.product
        )
        Spacer(modifier = Modifier.height(16.dp))
        SizeSection(
            sizes = uiState.product.sizes,
            selectedSize = uiState.selectedSize,
            onSizeSelected = {
                onEvent(ProductDetailsEvent.OnSizeSelected(it))
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        QuantitySection(
            quantity = uiState.quantity,
            onQuantityChanged = {
                onEvent(ProductDetailsEvent.OnQuantityChange(it))
            }
        )
    }
}

@Composable
fun ProductDetailsBottomBar(
    modifier: Modifier = Modifier,
    totalPrice: String,
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
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 16.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column {
                    Text(
                        stringResource(Res.string.total_price),
                        style = MaterialTheme.typography.body1
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        totalPrice,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(18.dp),
                    onClick = onAddToCart
                ){
                    Text(
                        stringResource(Res.string.add_to_cart),
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 10.dp
                        ),
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }


    }
}

@Composable
fun QuantitySection(
    modifier: Modifier = Modifier,
    quantity: Int,
    onQuantityChanged: (Int)-> Unit
){
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(Res.string.quantity),
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(32.dp))
                .background(
                    color = MaterialTheme.colors.primary.copy(alpha = 0.18f)
                ),
            contentAlignment = Alignment.Center
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = {
                        if (quantity > 1){
                            onQuantityChanged(quantity - 1)
                        }
                    }
                ){
                    Text(
                        "-",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    quantity.toString(),
                    style = MaterialTheme.typography.h6
                )
                Spacer(modifier = Modifier.width(10.dp))
                TextButton(
                    onClick = {
                        onQuantityChanged(quantity + 1)
                    }
                ){
                    Text(
                        "+",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier
                    )
                }
            }
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
            text = shoe.name,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(color = MaterialTheme.colors.primary.copy(alpha = 0.15f))
                    .padding(8.dp)
            ){
                Text(
                    "23 sold",
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                Icons.AutoMirrored.Filled.StarHalf,
                contentDescription = "",
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                "4.6 (20 reviews)",
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(Res.string.description),
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(8.dp))
        ExpandableText(
            text = shoe.description
        )
//        Text(
//            shoe.description,
//            style = MaterialTheme.typography.body2
//        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SizeSection(
    modifier: Modifier = Modifier,
    sizes: List<Size>,
    onSizeSelected: (Size)-> Unit,
    selectedSize: Size
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ){
        Text(
            stringResource(Res.string.size),
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            sizes.forEach { size ->
                SizeItem(
                    size = size,
                    isSelectable = size.quantity < 6,
                    isSelected = size == selectedSize,
                    onSizeSelected = {
                        onSizeSelected(size)
                    }
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }

}

@Composable
fun SizeItem(
    size: Size,
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
            text = "EU ${size.size}",
            color = textColor,
            modifier = Modifier.padding(
                horizontal = 14.dp,
                vertical = 8.dp
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
    images: List<String>,
    onNavigateBack: () -> Unit
){
    var selectedImage by remember {
        mutableStateOf(images[0])
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
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
                    .fillMaxHeight(0.3f)
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
                                selectedImage = image
                            }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
        IconButton(
            onClick = onNavigateBack,
            modifier = Modifier.align(Alignment.TopStart)
        ){
            Icon(
                Icons.AutoMirrored.Outlined.ArrowBackIos  ,
                contentDescription = "",
            )
        }
    }


}