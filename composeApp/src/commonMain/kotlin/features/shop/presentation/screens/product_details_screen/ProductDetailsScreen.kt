package features.shop.presentation.screens.product_details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import ndula.composeapp.generated.resources.Res
import ndula.composeapp.generated.resources.description
import ndula.composeapp.generated.resources.size
import org.jetbrains.compose.resources.stringResource


@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel
){

    val uiState by viewModel.productDetailsState.collectAsState()

    var selectedSize by remember {
        mutableStateOf(Size(0,0))
    }

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

            Column(
                modifier = Modifier.fillMaxSize()
            ){
                Spacer(modifier = Modifier.height(10.dp))
                ImagesSection(
                    images = uiState.product!!.images
                )
                Spacer(modifier = Modifier.height(16.dp))
                ProductInfoSection(
                    shoe = uiState.product!!
                )
                Spacer(modifier = Modifier.height(16.dp))
                SizeSection(
                    sizes = uiState.product!!.sizes,
                    selectedSize = selectedSize,
                    onSizeSelected = {
                        selectedSize = it
                    }
                )

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
        Text(
            shoe.description,
            style = MaterialTheme.typography.body2
        )
    }
}

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
        Row {
            sizes.forEach { size ->
                Box(
                    modifier = modifier
                        .border(
                            width = 1.5.dp,
                            color = MaterialTheme.colors.primary,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            color = if (selectedSize == size) MaterialTheme.colors.primary else MaterialTheme.colors.primary.copy(alpha = 0.08f)
                        )
                        .clickable { onSizeSelected(size) },
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = size.size.toString(),
                        color = if (selectedSize == size) MaterialTheme.colors.onPrimary else MaterialTheme.colors.primary,
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 8.dp
                        )
                    )
                }
            }
        }
    }

}


@Composable
fun ImagesSection(
    modifier: Modifier = Modifier,
    images: List<String>
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
                    .fillMaxHeight(0.4f)
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
            onClick = {},
            modifier = Modifier.align(Alignment.TopStart)
        ){
            Icon(
                Icons.AutoMirrored.Outlined.ArrowBackIos  ,
                contentDescription = "",
            )
        }
    }


}