package features.shop.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import features.shop.domain.models.Shoe
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun SuggestionsVerticalGrid(
    modifier: Modifier = Modifier,
    suggestions: List<Shoe>,
    onSuggestionSelected: (Shoe) -> Unit
){
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Fixed(2)
    ){
        items(
            suggestions.size
        ){index ->
            KamelImage(
                modifier = Modifier
                    .padding(2.5.dp)
                    .clickable {
                       onSuggestionSelected(suggestions[index])
                    },
                resource = asyncPainterResource(suggestions[index].images.first()),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
}