package features.shop.presentation.screens.add_location_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
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
import androidx.compose.material.icons.outlined.Apartment
import androidx.compose.material.icons.outlined.EditLocation
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Delete
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import core.presentation.components.ProgressDialog
import features.shop.domain.models.PlaceDetail
import features.shop.domain.models.ShippingAddress
import features.shop.presentation.components.ConfirmDialog
import features.shop.presentation.utils.NavigationUtils

@Composable
fun AddLocationScreen(
    viewModel: AddLocationViewModel,
    onNavigateBack: () -> Unit,
    shippingAddress: ShippingAddress? = NavigationUtils.shippingAddress
){

    val uiState by viewModel.addLocationScreenState.collectAsState()

    val suggestionsState by viewModel.suggestionsState.collectAsState()
    val query by viewModel.query.collectAsState()

    var isSearchLocationVisible by remember {
        mutableStateOf(false)
    }
    val snackBarHostState = remember { SnackbarHostState() }

    var showConfirmDeleteDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit){
        shippingAddress?.let {
            viewModel.updateShippingAddress(it)
        }
    }

    LaunchedEffect(uiState.isAddressSaved, uiState.errorMessage, uiState.isAddressDeleted){
        if (uiState.isAddressSaved){
            onNavigateBack()
            viewModel.resetAddLocationState()
        }

        if (uiState.isAddressDeleted){
            onNavigateBack()
            viewModel.resetAddLocationState()
        }

        if (uiState.errorMessage != null){
            snackBarHostState.showSnackbar(
                message = uiState.errorMessage!!
            )
            viewModel.resetErrorMessage()
        }

    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackBarHostState) { snackBarData ->
                Snackbar(
                    modifier = Modifier.padding(
                        bottom = 80.dp
                    ),
                    snackbarData =  snackBarData,
                    backgroundColor = MaterialTheme.colors.error,
                    actionColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.surface
                )
            }
        }
    ){ paddingValues ->

        if (uiState.isLoading){
            ProgressDialog(
                text = "Please wait..."
            )
        }

        if (showConfirmDeleteDialog){
            ConfirmDialog(
                title = "Confirm delete",
                message = "Are you sure you want to delete this address?",
                onConfirm = {
                    shippingAddress?.let {
                        viewModel.onEvent(AddLocationScreenEvents.OnDeleteShippingAddress(it.id))
                    }
                    showConfirmDeleteDialog = false
                },
                onDismiss = {
                    showConfirmDeleteDialog = false
                },
                confirmButtonText = "Delete",
                dismissButtonText = "Cancel"
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            AddLocationScreenContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                state = uiState,
                onEvent = viewModel::onEvent,
                onSearchLocation = {
                    isSearchLocationVisible = true
                },
                onNavigateBack = {
                    onNavigateBack()
                    viewModel.resetAddLocationState()
                },
                onDeleteAddress = {
                    showConfirmDeleteDialog = true
                },
                isEditMode = shippingAddress != null
            )
            AddLocationScreenBottomBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                onSaveAddress = {
                    if(shippingAddress != null){
                        viewModel.onEvent(AddLocationScreenEvents.OnUpdateAddress(shippingAddress.id))
                    } else {
                        viewModel.onEvent(AddLocationScreenEvents.OnSaveAddress)
                    }
                }
            )
            if(isSearchLocationVisible){
                SearchLocationContent(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colors.surface
                        )
                        .fillMaxSize(),
                    state = suggestionsState,
                    query = query,
                    onQueryChange = {
                        // println("Search location with query:: $it")
                        viewModel.onEvent(AddLocationScreenEvents.OnQueryChange(it))
                    },
                    onPlaceSelected = {
                        viewModel.onEvent(AddLocationScreenEvents.OnPlaceSelected(it))
                        isSearchLocationVisible = false
                        viewModel.resetSuggestionState()
                    },
                    onNavigateBack = {
                        isSearchLocationVisible = false
                        viewModel.resetSuggestionState()
                    }
                )
            }
        }

    }
}

@Composable
fun SearchLocationContent(
    modifier: Modifier,
    state: PlaceSuggestionsState,
    query: String,
    onQueryChange: (String) -> Unit,
    onPlaceSelected: (PlaceDetail) -> Unit,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = modifier
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                onClick = onNavigateBack
            ){
                Icon(
                    Icons.AutoMirrored.Default.ArrowBackIos,
                    contentDescription = ""
                )
            }
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        top = 16.dp,
                        bottom = 16.dp,
                        end = 16.dp
                    ),
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = MaterialTheme.colors.onSurface.copy(
                        alpha = 0.2f
                    ),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                placeholder = {
                    Text("Search town, road, building etc")
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            when(state){
                is PlaceSuggestionsState.Error -> {
                    Text(
                        state.message,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
                is PlaceSuggestionsState.Idle -> {

                }
                is PlaceSuggestionsState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is PlaceSuggestionsState.Success -> {
                    val places = state.suggestions
                    LazyColumn(
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp
                            )
                    ){
                        items(places){ place ->
                            PlaceSuggestionItem(
                                modifier = Modifier,
                                place = place,
                                onCLick = {
                                    onPlaceSelected(place)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlaceSuggestionItem(
    modifier: Modifier = Modifier,
    place: PlaceDetail,
    onCLick: () -> Unit
){
    Column{
        Row (
            modifier = modifier
                .padding(
                    vertical = 10.dp
                )
                .fillMaxWidth()
                .clickable {
                    onCLick()
                },
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                Icons.Outlined.LocationOn,
                contentDescription = ""

            )
            Spacer(modifier = Modifier.width(16.dp))
            Column{
                Text(
                    place.name,
                    style = MaterialTheme.typography.body1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    place.formattedAddress,
                    style = MaterialTheme.typography.body2.copy(
                        color = MaterialTheme.colors.onSurface.copy(
                            alpha = 0.5f
                        )
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Divider(
            color = MaterialTheme.colors.onSurface.copy(
                alpha = 0.1f
            ),
            thickness = 1.5.dp,
            modifier = Modifier.padding(
                horizontal = 16.dp
            )
        )
    }
}

@Composable
fun AddLocationScreenBottomBar(
    modifier: Modifier = Modifier,
    onSaveAddress: () -> Unit
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 24.dp,
                vertical = 10.dp
            )
    ){
        Button(
            onClick = onSaveAddress,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ){
            Text(
                "Save Address",
                style = MaterialTheme.typography.button,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

@Composable
fun AddLocationScreenContent(
    state: AddLocationScreenState,
    onEvent: (AddLocationScreenEvents) -> Unit,
    onSearchLocation: () -> Unit,
    onNavigateBack: () -> Unit,
    onDeleteAddress: () -> Unit,
    isEditMode: Boolean = false,
    modifier: Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp
            )
    ){
        TopAppBar(
            title = {
                Text(
                    "Address details"
                )
            },
            elevation = 0.dp,
            backgroundColor = Color.Transparent,
            navigationIcon = {
                IconButton(
                    onClick = onNavigateBack
                ){
                    Icon(
                        Icons.AutoMirrored.Default.ArrowBackIos,
                        contentDescription = ""
                    )
                }
            },
            actions = {
                if (isEditMode){
                    IconButton(
                        onClick = onDeleteAddress
                    ){
                        Icon(
                            Icons.Rounded.Delete,
                            contentDescription = ""
                        )
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        AddressSection(
            place = state.selectedPlace,
            onEdit = onSearchLocation
        )
        Spacer(modifier = Modifier.height(24.dp))
        CustomOutlinedTextField(
            value = state.phoneNumber,
            onValueChange = {
                onEvent(AddLocationScreenEvents.OnPhoneNumberChange(it))
            },
            modifier = Modifier.fillMaxWidth(),
            label = "Phone number",
            trailingIcon = {
                if (state.phoneNumber.isNotEmpty()){
                    IconButton(
                        onClick = {
                            onEvent(AddLocationScreenEvents.OnPhoneNumberChange(""))
                        }
                    ){
                        Icon(
                            Icons.Rounded.Clear,
                            contentDescription = ""
                        )
                    }
                }
            },
            error = state.phoneNumberError
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ){
            CustomOutlinedTextField(
                value = state.floorNumber,
                onValueChange = {
                    onEvent(AddLocationScreenEvents.OnFloorNumberChange(it))
                },
                modifier = Modifier.weight(1f),
                label = "Floor number",
                trailingIcon = {
                    if (state.floorNumber.isNotEmpty()){
                        IconButton(
                            onClick = {
                                onEvent(AddLocationScreenEvents.OnFloorNumberChange(""))
                            }
                        ){
                            Icon(
                                Icons.Rounded.Clear,
                                contentDescription = ""
                            )
                        }
                    }
                },
                error = state.floorNumberError
            )
            Spacer(modifier = Modifier.width(16.dp))
            CustomOutlinedTextField(
                value = state.doorNumber,
                onValueChange = {
                    onEvent(AddLocationScreenEvents.OnDoorNumberChange(it))
                },
                modifier = Modifier.weight(1f),
                label = "Door number",
                trailingIcon = {
                    if (state.doorNumber.isNotEmpty()){
                        IconButton(
                            onClick = {
                                onEvent(AddLocationScreenEvents.OnDoorNumberChange(""))
                            }
                        ) {
                            Icon(
                                Icons.Rounded.Clear,
                                contentDescription = ""
                            )
                        }
                    }
                },
                error = state.doorNumberError
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        CustomOutlinedTextField(
            value = state.buildingName,
            onValueChange = {
                onEvent(AddLocationScreenEvents.OnBuildingNameChange(it))
            },
            label = "Building name",
            modifier = Modifier
                .fillMaxWidth(),
            trailingIcon = {
                if (state.buildingName.isNotEmpty()){
                    IconButton(
                        onClick = {
                            onEvent(AddLocationScreenEvents.OnBuildingNameChange(""))
                        }
                    ){
                        Icon(
                            Icons.Rounded.Clear,
                            contentDescription = ""
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String,
    error: String? = null,
    trailingIcon: @Composable (() -> Unit)? = null
){
    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            label = {
                Text(label)
            },
            trailingIcon = trailingIcon,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.onSurface,
                unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(
                    alpha = 0.5f
                ),
            ),
            isError = error != null
        )
        Spacer(modifier = Modifier.height(4.dp))
        if (error != null){
            Text(
                error,
                style = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.error
                )
            )
        }
    }
}

@Composable
fun AddressSection(
    modifier: Modifier = Modifier,
    place: PlaceDetail?,
    onEdit: () -> Unit
){
    if (place == null){
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onSurface.copy(
                        alpha = 0.5f
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable {
                    onEdit()

                },
        ){
            Text(
                "Search location",
                style = MaterialTheme.typography.body1.copy(
                    color = MaterialTheme.colors.onSurface.copy(
                        alpha = 0.5f
                    )
                ),
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    } else {
        PlaceItem(
            place = place,
            onEdit = onEdit
        )
    }
}

@Composable
fun PlaceItem(
    modifier: Modifier = Modifier,
    place: PlaceDetail,
    onEdit: () -> Unit
){
   Row(
       modifier = modifier
           .fillMaxWidth(),
       verticalAlignment = Alignment.CenterVertically
   ){
       Box(
           modifier = Modifier
               .background(
                   color = MaterialTheme.colors.onSurface.copy(
                       alpha = 0.1f
                   ),
                   shape = RoundedCornerShape(16.dp)
               )
               .padding(16.dp)
       ){
           Icon(
               Icons.Outlined.Apartment,
               contentDescription = "",
           )
       }
       Spacer(modifier = Modifier.width(16.dp))
       Column{
           Text(
               place.name,
               style = MaterialTheme.typography.body1,
               maxLines = 1,
               overflow = TextOverflow.Ellipsis
           )
           Spacer(modifier = Modifier.width(4.dp))
           Text(
               place.formattedAddress,
               style = MaterialTheme.typography.body2.copy(
                   color = MaterialTheme.colors.onSurface.copy(
                       alpha = 0.5f
                   )
               ),
               maxLines = 1,
               overflow = TextOverflow.Ellipsis
           )
       }
       Spacer(modifier = Modifier.weight(1f))
       IconButton(
           onClick = onEdit
       ) {
           Icon(
               Icons.Outlined.EditLocation,
               contentDescription = ""
           )
       }
   }
}
