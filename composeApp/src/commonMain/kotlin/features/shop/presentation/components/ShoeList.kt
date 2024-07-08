package presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import features.shop.domain.models.Shoe


@Composable
fun ShoeList(
    shoes: List<Shoe>,
    onClick: (Shoe) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 13.5.dp, vertical = 16.dp)
    ){
        items(shoes.size){
            ShoeItem(
                shoe = shoes[it],
                onShoeSelected = { onClick(it) }
            )
        }
    }
}