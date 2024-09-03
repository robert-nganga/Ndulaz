package features.shop.presentation.screens.payment_success_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ndula.composeapp.generated.resources.Res
import ndula.composeapp.generated.resources.undraw_on_the_way_re_swjt
import org.jetbrains.compose.resources.painterResource

@Composable
fun PaymentSuccessScreen(
    onNavigateBack: () ->Unit,
    onNavigateToOrderScreen: () ->Unit
){
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                elevation = 0.dp,
                backgroundColor = Color.Transparent,
                navigationIcon = {
                    Spacer(modifier = Modifier.width(10.dp))
                    IconButton(
                        onClick = {
                            onNavigateBack()
                        }
                    ){
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBackIos,
                            contentDescription = "Back icon"
                        )
                    }
                }
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            ){
            Spacer(modifier = Modifier.height(30.dp))
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(
                        color = Color(0xff37ab4a)
                    ),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    Icons.Default.Check,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier
                        .size(120.dp)
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Payment Successful",
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Your order is on the way",
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(50.dp))
            Box(
                modifier = Modifier
                    .padding(
                        horizontal = 40.dp,
                        vertical = 10.dp
                    )
            ){
                Image(
                    painter = painterResource(Res.drawable.undraw_on_the_way_re_swjt),
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onNavigateToOrderScreen,
                modifier = Modifier
                    .padding(
                        horizontal = 20.dp
                    )
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ){
                Text(
                    "Checkout",
                    style = MaterialTheme.typography.button,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

        }
    }
}