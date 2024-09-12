package features.shop.presentation.screens.review_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import core.presentation.components.ProgressDialog
import features.shop.domain.models.OrderItem
import features.shop.presentation.components.ConfirmDialog
import features.shop.presentation.components.OrderItemDetailsCard
import features.shop.presentation.components.Ratings
import features.shop.presentation.utils.NavigationUtils


@Composable
fun ReviewScreen(
    viewModel: ReviewViewModel,
    orderItem: OrderItem = NavigationUtils.orderItem,
    onNavigateBack: () -> Unit
){
    val state by viewModel.reviewScreenState.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }
    var showDiscardDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit){
        viewModel.updateOrderItem(orderItem)
    }

    LaunchedEffect(state.errorMessage){
        state.errorMessage?.let { message ->
            snackBarHostState.showSnackbar(message)
            viewModel.resetErrorMessage()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState) { snackBarData ->
                Snackbar(
                    modifier = Modifier.padding(
                        bottom = 80.dp
                    ),
                    snackbarData =  snackBarData,
                    backgroundColor = MaterialTheme.colors.error,
                    actionColor = MaterialTheme.colors.onError,
                    contentColor = MaterialTheme.colors.onError
                )
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Leave a review",
                        style = MaterialTheme.typography.h5.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                elevation = 0.dp,
                backgroundColor = MaterialTheme.colors.background,
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.padding(start = 10.dp),
                        onClick = {
                            showDiscardDialog = true
                        }
                    ){
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBackIos,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        backgroundColor = MaterialTheme.colors.onBackground.copy(
            alpha = 0.038f
        )
    ){
        if (state.isLoading){
            ProgressDialog(
                text = "Please wait"
            )
        }

        if (showDiscardDialog){
            ConfirmDialog(
                title = "Discard Review",
                message = "Are you sure you want to discard your review?",
                confirmButtonText = "Discard",
                dismissButtonText = "Cancel",
                onConfirm = {
                    showDiscardDialog = false
                    viewModel.resetReviewScreenState()
                    onNavigateBack()
                },
                onDismiss = {
                    showDiscardDialog = false
                }
            )
        }

        if (state.isSubmitted){
            ReviewSuccessDialog(
                onOkayPressed = {
                    viewModel.resetReviewScreenState()
                    onNavigateBack()
                }
            )
        }

        ReviewScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            state = state,
            orderItem = orderItem,
            onReviewSubmitted = viewModel::onSubmitReview,
            onRatingChanged = viewModel::onRatingChanged,
            onReviewChanged = viewModel::onReviewChanged,
            onNavigateBack = {
                showDiscardDialog = true
            }
        )
    }
}

@Composable
fun ReviewSuccessDialog(
    modifier: Modifier = Modifier,
    onOkayPressed: () -> Unit
){
    Dialog(
        onDismissRequest = {},
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.background)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Thank you!",
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "We appreciate your feedback",
                style = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.onBackground.copy(
                        alpha = 0.5f
                    )
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = onOkayPressed,
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                    )
            ){
                Text(
                    "Okay",
                    style = MaterialTheme.typography.button.copy(
                        color = MaterialTheme.colors.onPrimary
                    ),
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun ReviewScreenContent(
    modifier: Modifier = Modifier,
    state: ReviewScreenState,
    onNavigateBack: () -> Unit,
    onReviewSubmitted: () -> Unit,
    onRatingChanged: (rating: Double) -> Unit,
    onReviewChanged: (review: String) -> Unit,
    orderItem: OrderItem,
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(30.dp))
        OrderItemDetailsCard(
            orderItem = orderItem,
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            "How was your order?",
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.Bold
            ),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            "Please give your rating & also your review",
            style = MaterialTheme.typography.body2.copy(
                color = MaterialTheme.colors.onSurface.copy(
                    alpha = 0.5f
                )
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Ratings(
            modifier = Modifier.fillMaxWidth(),
            rating = state.rating.toInt(),
            starSize = 45.dp,
            starColor = MaterialTheme.colors.primary,
            onRatingChanged = onRatingChanged
        )
        Spacer(modifier = Modifier.height(40.dp))
        OutlinedTextField(
            value = state.review,
            onValueChange = {
                onReviewChanged(it)
            },
            label = { Text("Describe your experience") },
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.onSurface,
                unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(
                    alpha = 0.5f
                ),
            ),
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ){
            Button(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(32.dp),
                onClick = onNavigateBack,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary.copy(
                        alpha = 0.04f
                    )
                )
            ){
                Text(
                    "Cancel",
                    style = MaterialTheme.typography.button.copy(
                        color = MaterialTheme.colors.primary
                    ),
                    modifier = Modifier.padding(10.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                shape = RoundedCornerShape(32.dp),
                onClick = onReviewSubmitted,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor =  MaterialTheme.colors.primary
                ),
                modifier = Modifier.weight(1f),
            ){
                Text(
                    "Submit",
                    style = MaterialTheme.typography.button.copy(
                        color = MaterialTheme.colors.onPrimary
                    ),
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}