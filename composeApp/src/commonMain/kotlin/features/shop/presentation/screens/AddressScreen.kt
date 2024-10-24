package features.shop.presentation.screens

import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun AddressScreen(
    onNavigateBack: () -> Unit,
){
    val x = rememberModalBottomSheetState(
        skipHalfExpanded = true,
        initialValue = ModalBottomSheetValue.Hidden
    )
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                elevation = 0.dp,
                backgroundColor = MaterialTheme.colors.surface,
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack
                    ){
                        Icon(
                            Icons.AutoMirrored.Outlined.ArrowBackIos,
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    ){
        ModalBottomSheetLayout(
            // Bottom sheet state
            sheetState = x,
            sheetContent = {
                // Content of the bottom sheet
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    (1..10).forEach { _ ->
                        Text(text = "This is the content of the bottom sheet")
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { coroutineScope.launch { x.hide() } }) {
                        Text("Close Sheet")
                    }
                }
            }
        ) {
            // Main content
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { coroutineScope.launch { x.show() } }) {
                    Text("Show Bottom Sheet")
                }
            }
        }

    }
}