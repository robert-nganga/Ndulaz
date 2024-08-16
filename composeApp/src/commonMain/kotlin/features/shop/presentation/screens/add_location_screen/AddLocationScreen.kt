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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.outlined.Apartment
import androidx.compose.material.icons.outlined.EditLocation
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import features.shop.domain.models.PlaceDetail

@Composable
fun AddLocationScreen(
    viewModel: AddLocationViewModel,
    onNavigateBack: () -> Unit
){

    val uiState by viewModel.addLocationScreenState.collectAsState()

    Scaffold(
        topBar = {
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
                }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 10.dp
                    )
            ){
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(
                        "Save Address",
                        style = MaterialTheme.typography.button,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
    ){ paddingValues ->
        AddLocationScreenContent(
            modifier = Modifier
                .padding(paddingValues),
            state = uiState,
            onEvent = viewModel::onEvent,
            onSearchLocation = {}
        )
    }
}

@Composable
fun AddLocationScreenContent(
    state: AddLocationScreenState,
    onEvent: (AddLocationScreenEvents) -> Unit,
    onSearchLocation: () -> Unit,
    modifier: Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp
            )
    ){
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
                IconButton(
                    onClick = {
                        onEvent(AddLocationScreenEvents.OnPhoneNumberChange(""))
                    }
                ){
                    if (state.phoneNumber.isNotEmpty()){
                        Icon(
                            Icons.Rounded.Clear,
                            contentDescription = ""
                        )
                    }
                }
            }
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
                    IconButton(
                        onClick = {
                            onEvent(AddLocationScreenEvents.OnFloorNumberChange(""))
                        }
                    ){
                        if (state.floorNumber.isNotEmpty()) {
                            Icon(
                                Icons.Rounded.Clear,
                                contentDescription = ""
                            )
                        }
                    }
                }
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
                    IconButton(
                        onClick = {
                            onEvent(AddLocationScreenEvents.OnDoorNumberChange(""))
                        }
                    ) {
                        if (state.doorNumber.isNotEmpty()) {
                            Icon(
                                Icons.Rounded.Clear,
                                contentDescription = ""
                            )
                        }
                    }
                }
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
                IconButton(
                    onClick = {
                        onEvent(AddLocationScreenEvents.OnBuildingNameChange(""))
                    }
                ){
                    if (state.buildingName.isNotEmpty()){
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
    trailingIcon: @Composable (() -> Unit)? = null
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
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
        )
    )
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
                    color = MaterialTheme.colors.onSurface,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
                .clickable {
                    onEdit()

                },
            contentAlignment = Alignment.Center
        ){
            Text(
                "Search location",
                style = MaterialTheme.typography.body1.copy(
                    color = MaterialTheme.colors.onSurface.copy(
                        alpha = 0.5f
                    )
                )
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
