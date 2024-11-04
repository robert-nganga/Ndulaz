package features.shop.presentation.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.flexible.bottomsheet.material.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetState
import features.shop.domain.models.CartItem
import features.shop.presentation.screens.product_details_screen.AddToCartState
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun AddToCartBottomSheet(
    modifier: Modifier = Modifier,
    addToCartState: AddToCartState,
    sheetState: FlexibleSheetState,
    onAddToCart: (CartItem) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToCart: () -> Unit,
    onDismiss: () -> Unit,
) {
    FlexibleBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier,
        sheetState = sheetState,
        containerColor = MaterialTheme.colors.surface,
        scrimColor = MaterialTheme.colors.onSurface.copy(
            alpha = 0.2f
        ),
        windowInsets = WindowInsets(left = 0.dp, right = 0.dp, top = 0.dp, bottom = 0.dp)
    ){
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Text(
                    "Add to cart",
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.Bold
                    ),
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = onDismiss
                ){
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Dismiss"
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            when(addToCartState){
                is AddToCartState.Error -> {
                    //val message = addToCartState.message
                    AddToCartErrorItem(
                        message = "Add to cart failed",
                        onNavigateBack = onNavigateBack,
                        onNavigateToCart = onNavigateToCart
                    )
                }
                is AddToCartState.Idle -> {
                    val item = addToCartState.item
                    AddToCartItem(
                        cartItem = item,
                        onAddToCart = { quantity ->
                            onAddToCart(item.copy(quantity = quantity))
                        }
                    )
                }
                is AddToCartState.Loading -> {
                    Spacer(modifier = Modifier.height(50.dp))
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                is AddToCartState.Success -> {
                    //val message = addToCartState.message
                    AddToCartSuccessItem(
                        message = "Added to cart",
                        onNavigateBack = onNavigateBack,
                        onNavigateToCart = onNavigateToCart
                    )
                }
            }
        }
    }
}

@Composable
fun AddToCartErrorItem(
    modifier: Modifier = Modifier,
    message: String,
    onNavigateBack: () -> Unit,
    onNavigateToCart: () -> Unit
){
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(
                    color = MaterialTheme.colors.primary
                ),
            contentAlignment = Alignment.Center
        ){
            Icon(
                Icons.Default.Close,
                contentDescription = "",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colors.onPrimary
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            message,
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold
            ),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            "1 item total",
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onSurface.copy(
                    alpha = 0.5f
                )
            ),
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ){
            OutlinedButton(
                onClick = onNavigateBack,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary.copy(
                        alpha = 0.1f
                    )
                )
            ){
                Text(
                    "Back Discovery",
                    style = MaterialTheme.typography.body1.copy(
                        letterSpacing = 0.sp
                    ),
                    modifier = Modifier.padding(6.dp)
                )
            }
            Button(
                onClick = onNavigateToCart,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                shape = RoundedCornerShape(16.dp)
            ){
                Text(
                    "To Cart",
                    style = MaterialTheme.typography.body1.copy(
                        letterSpacing = 0.sp
                    ),
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }
}

@Composable
fun AddToCartSuccessItem(
    modifier: Modifier = Modifier,
    message: String,
    onNavigateBack: () -> Unit,
    onNavigateToCart: () -> Unit
){
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(
                    color = MaterialTheme.colors.primary
                ),
            contentAlignment = Alignment.Center
        ){
           Icon(
               Icons.Default.Check,
               contentDescription = "",
               modifier = Modifier.size(40.dp),
               tint = MaterialTheme.colors.onPrimary
           )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            message,
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold
            ),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            "1 item total",
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onSurface.copy(
                    alpha = 0.5f
                )
            ),
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ){
            OutlinedButton(
                onClick = onNavigateBack,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 6.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary.copy(
                        alpha = 0.1f
                    )
                )
            ){
                Text(
                    "Back Discovery",
                    style = MaterialTheme.typography.body1.copy(
                        letterSpacing = 0.sp
                    ),
                    modifier = Modifier.padding(6.dp)
                )
            }
            Button(
                onClick = onNavigateToCart,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                shape = RoundedCornerShape(16.dp)
            ){
                Text(
                    "To Cart",
                    style = MaterialTheme.typography.body1.copy(
                        letterSpacing = 0.sp
                    ),
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }
}

@Composable
fun AddToCartItem(
    modifier: Modifier = Modifier,
    cartItem: CartItem,
    onAddToCart: (Int) -> Unit
){
    var quantity by remember {
        mutableStateOf( cartItem.quantity)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ){
            KamelImage(
                resource = asyncPainterResource(cartItem.imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(140.dp)
                    .height(120.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    cartItem.name,
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                AddToCartAttribute(
                    attribute = "Color",
                    value = cartItem.color,
                )
                Spacer(modifier = Modifier.height(2.dp))
                AddToCartAttribute(
                    attribute = "Size",
                    value = cartItem.size.toString(),
                )
                Spacer(modifier = Modifier.height(2.dp))
                AddToCartAttribute(
                    attribute = "Price",
                    value = cartItem.price.toString(),
                )

            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            AddToCartQuantitySection(
                quantity = quantity,
                onDecrement = { quantity-- },
                onIncrement = { quantity++ }
            )
            Spacer(modifier = Modifier.weight(1f))
            AddToCartAttribute(
                attribute = "SubTotal",
                value = (cartItem.price * quantity).toString()
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { onAddToCart(quantity) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ){
            Text(
                "Add to cart",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(8.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun AddToCartQuantitySection(
    modifier: Modifier = Modifier,
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
){
    Row(
        modifier = modifier
            .padding(
                horizontal = 10.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            if (quantity > 1) "$quantity Items" else "$quantity Item",
            style = MaterialTheme.typography.body2.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.width(12.dp))
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .border(
                    width = 1.5.dp,
                    color = MaterialTheme.colors.onSurface.copy(
                        alpha = 0.5f
                    ),
                    shape = CircleShape
                )
                .clickable { onDecrement() },
            contentAlignment = Alignment.Center
        ){
            Text(
                "-",
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onSurface.copy(
                        alpha = 0.5f
                    )
                ),
                modifier = Modifier.padding(
                    bottom = 4.dp
                )
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .border(
                    width = 1.5.dp,
                    color = MaterialTheme.colors.onSurface,
                    shape = CircleShape
                )
                .clickable { onIncrement() },
            contentAlignment = Alignment.Center
        ){
            Text(
                "+",
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onSurface
                ),
                modifier = Modifier.padding(
                    bottom = 4.dp
                )
            )
        }

    }
}

@Composable
fun AddToCartAttribute(
    modifier: Modifier = Modifier,
    attribute: String,
    value: String,
){
    Row(
        modifier = modifier
    ){
        Text(
            "$attribute:",
            style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onSurface.copy(
                    alpha = 0.5f
                )
            )
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            value,
            style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}