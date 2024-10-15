package features.profile.presentation.screens.onboarding_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ndula.composeapp.generated.resources.Add_to_Cart_rafiki
import ndula.composeapp.generated.resources.Delivery_bro
import ndula.composeapp.generated.resources.Marketplace_rafiki
import ndula.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


data class OnboardingContent(
    val image: DrawableResource,
    val title: String,
    val description: String
)

@Composable
fun OnboardingScreen(
    onNavigateToLogin: () -> Unit
){

    val pagerState = rememberPagerState{ 3 }

    val scope = rememberCoroutineScope()

    val onboardingScreens = listOf(
        OnboardingContent(
            image = Res.drawable.Marketplace_rafiki,
            title = "One stop shop",
            description = "Discover everything you need in one place. Shop with ease and enjoy a world of endless possibilities!"
        ),
        OnboardingContent(
            image = Res.drawable.Add_to_Cart_rafiki,
            title = "Convenient shopping",
            description = "Browse, add to cart, and checkout in just a few taps. Your seamless shopping experience starts here!"
        ),
        OnboardingContent(
            image = Res.drawable.Delivery_bro,
            title = "Instant delivery",
            description = "Get what you want, when you want it. Speedy deliveries right to your doorstep!"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                actions = {
                    TextButton(
                        onClick = onNavigateToLogin
                    ){
                        Text(
                            "Skip",
                            style = MaterialTheme.typography.body2.copy(
                                color = MaterialTheme.colors.primary
                            )
                        )
                    }
                }
            )
        },
        bottomBar = {
            OnboardingScreenBottomBar(
                pagerState = pagerState,
                onNext = {
                    scope.launch {
                        if (pagerState.currentPage == 2){
                            onNavigateToLogin()
                            return@launch
                        }
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                onBack = {
                     scope.launch {
                         pagerState.animateScrollToPage(pagerState.currentPage - 1)
                     }
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(
                        horizontal = 24.dp,
                        vertical = 30.dp
                    )
            ){index ->
                val content = onboardingScreens[index]
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Image(
                        painter = painterResource(resource = content.image),
                        contentDescription = "Onboarding Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.6f),
                        contentScale = ContentScale.Crop,
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        content.title,
                        style = MaterialTheme.typography.h5.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        content.description,
                        style = MaterialTheme.typography.body2.copy(
                            color = MaterialTheme.colors.primary.copy(
                                alpha = 0.6f
                            )
                        ),
                        textAlign = TextAlign.Center
                    )
                }

            }
        }
    }
}

@Composable
fun OnboardingScreenPagerIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int,
    currentPage: Int
){
   Row(
       modifier = modifier
           .padding(10.dp),
       verticalAlignment = Alignment.CenterVertically,
   ){
       (0..<pageCount).forEach {
           Box(
               modifier = Modifier
                   .width(if (currentPage == it) 24.dp else 12.dp)
                   .height(12.dp)
                   .clip(RoundedCornerShape(8.dp))
                   .background(
                       color = if (it == currentPage) MaterialTheme.colors.primary else MaterialTheme.colors.primary.copy(alpha = 0.2f)
                   )
           )
           Spacer(modifier = Modifier.width(8.dp))
       }
   }
}


@Composable
fun OnboardingScreenBottomBar(
    modifier: Modifier = Modifier,
    onNext: () -> Unit,
    onBack: () -> Unit,
    pagerState: PagerState
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 10.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        if (pagerState.currentPage != 0){
            TextButton(
                onClick = onBack
            ){
                Text(
                    "Back",
                    style = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.primary.copy(
                            alpha = 0.6f
                        ),
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        Spacer(modifier = Modifier.width(20.dp))
        OnboardingScreenPagerIndicator(
            modifier = Modifier.weight(1f),
            pageCount = pagerState.pageCount,
            currentPage = pagerState.currentPage
        )
        Button(
            onClick = onNext,
            shape = RoundedCornerShape(16.dp),
        ){
            Text(
                if (pagerState.currentPage == 2) "Finish" else "Next",
                style = MaterialTheme.typography.button.copy(
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}