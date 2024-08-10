package features.shop.presentation.screens.search_screen

import androidx.compose.foundation.background
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import features.shop.presentation.components.ShoesVerticalGrid
import features.shop.presentation.components.SuggestionsVerticalGrid


@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onNavigateBack: () -> Unit
){
    val uiState by viewModel.searchState.collectAsState()

    val searchQuery by viewModel.query.collectAsState()

    val isSearchSuccessful by remember(uiState){
        mutableStateOf(uiState is SearchScreenState.Success)
    }
    val resultsTotal by remember(uiState){
        mutableStateOf(
            when(uiState){
                is SearchScreenState.Success -> (uiState as SearchScreenState.Success).results.size
                else -> 0
            }
        )
    }

    Scaffold(
        topBar = {
            SearchScreenAppBar(
                onNavigateBack = onNavigateBack,
                title = "Explore",
                query = searchQuery,
                onQueryChange = viewModel::onQueryChange,
                isSearchSuccessful = isSearchSuccessful,
                resultsTotal = resultsTotal,
            )
        },
        backgroundColor = MaterialTheme.colors.onBackground.copy(
            alpha = 0.05f
        )
    ){
        SearchScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            state = uiState,
        )
    }
}

@Composable
fun SearchScreenContent(
    modifier: Modifier = Modifier,
    state: SearchScreenState
){
    Column(
        modifier = modifier
    ){
        when(state){
            is SearchScreenState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize()
                ){
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            is SearchScreenState.Success -> {
                val shoes = state.results
                ShoesVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize(),
                    shoes = shoes,
                    onClick = {

                    },
                    onWishListClicked = {}
                )
            }
            is SearchScreenState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize()
                ){
                    Text(
                        text = state.error,
                    )
                }
            }

            is SearchScreenState.Idle -> {
                val suggestions = state.suggestions
                SuggestionsVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize(),
                    suggestions = suggestions,
                    onSuggestionSelected = {}
                )
            }
        }

    }
}

@Composable
fun SearchScreenAppBar(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    title: String,
    query: String,
    onQueryChange: (String) -> Unit,
    isSearchSuccessful: Boolean,
    resultsTotal: Int,
){
    Column(
        modifier = modifier
            .background(MaterialTheme.colors.background)
            .padding(
                vertical = 16.dp
            )
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = onNavigateBack
            ){
                Icon(
                    Icons.AutoMirrored.Default.ArrowBackIos,
                    contentDescription = "Back icon"
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight.Bold
                )
            )

        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = {
                Text(text = "Search Shoe")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary.copy(alpha = 0.5f),
                unfocusedBorderColor = Color.Transparent,
                backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.08f)
            ),
            shape = RoundedCornerShape(16.dp)
        )
        if (isSearchSuccessful){
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    "Results for \"$query\"",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "$resultsTotal found",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }

}