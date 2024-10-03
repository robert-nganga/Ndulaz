package features.shop.presentation.screens.settings_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.presentation.ui.ThemeColors
import core.presentation.ui.darkColorScheme
import core.presentation.ui.lightColorScheme


enum class ThemeSelection{
    Light,
    Dark
}

@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel
){

    val state by viewModel.settingsState.collectAsState()

    val userPreferences by viewModel.userPreferences.collectAsState(null)

    LaunchedEffect(Unit){
        userPreferences?.let {
            viewModel.updateSelectedTheme(it.appTheme)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Settings",
                        style = MaterialTheme.typography.h6.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                elevation = 0.dp,
                backgroundColor = MaterialTheme.colors.background,
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
            )
        },
        backgroundColor = MaterialTheme.colors.onBackground.copy(
            alpha = 0.02f
        )
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(it)
                .verticalScroll(rememberScrollState())
        ){
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Theme",
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                ThemeSelection.entries.forEach { theme ->
                    ThemeTemplate(
                        modifier = Modifier.width(100.dp),
                        onThemeClick = {
                            viewModel.updateTheme(theme)
                        },
                        isSelected = state.selectedTheme == theme,
                        themeSelection = theme
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    "Use system theme",
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = true,
                    onCheckedChange = {
                    }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Divider(
                thickness = 1.dp,
                color = MaterialTheme.colors.onBackground.copy(
                    alpha = 0.2f
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Data usage",
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ){
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        "Data saver",
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onBackground
                        )
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        "When enabled, lower quality images will be loaded. This automatically reduces your data usage",
                        style = MaterialTheme.typography.body2.copy(
                            color = MaterialTheme.colors.onBackground.copy(
                                alpha = 0.6f
                            )
                        )
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Switch(
                    checked = false,
                    onCheckedChange = {}
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp
                    )
                    .clickable {  }
            ) {
                Text(
                    "Data sync interval",
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    "Hourly",
                    style = MaterialTheme.typography.body2.copy(
                        color = MaterialTheme.colors.onBackground.copy(
                            alpha = 0.6f
                        )
                    )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Divider(
                thickness = 1.dp,
                color = MaterialTheme.colors.onBackground.copy(
                    alpha = 0.2f
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Support us",
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp
                    )
                    .clickable {  }
            ) {
                Text(
                    "Share this app",
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    "Invite your friends to join us",
                    style = MaterialTheme.typography.body2.copy(
                        color = MaterialTheme.colors.onBackground.copy(
                            alpha = 0.6f
                        )
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp
                    )
                    .clickable{  }
            ){
                Text(
                    "Report an issue",
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    "Help us improve your experience on this app",
                    style = MaterialTheme.typography.body2.copy(
                        color = MaterialTheme.colors.onBackground.copy(
                            alpha = 0.6f
                        )
                    )
                )
            }
        }
    }
}

fun ThemeSelection.getThemeColors(): ThemeColors{
    return when(this){
        ThemeSelection.Light -> lightColorScheme
        ThemeSelection.Dark -> darkColorScheme
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ThemeTemplate(
    modifier: Modifier = Modifier,
    onThemeClick: () -> Unit,
    isSelected: Boolean,
    themeSelection: ThemeSelection
){
    val theme by remember(themeSelection) {
        mutableStateOf(themeSelection.getThemeColors())
    }

    Column{
        Card(
            onClick = {
                onThemeClick()
            } ,
            border = BorderStroke(
                width = 3.dp,
                color = if(isSelected) MaterialTheme.colors.primary else Color.Transparent,
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = 10.dp,
            backgroundColor =theme.background
        ){
            Column(
                modifier = modifier
                    .background(
                        color = theme.onBackGround.copy(alpha = 0.1f)
                    )
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .background(theme.background)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth()
                        .height(25.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(theme.onBackGround)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(theme.onBackGround)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth(0.5f)
                        .height(8.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(theme.onBackGround)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .background(theme.background)
                        .padding(
                            horizontal = 16.dp,
                            vertical = 10.dp
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    (1..4).forEach { index ->
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(if(index == 2) theme.primary else theme.onBackGround.copy(alpha = 0.5f))
                        )
                    }
                }

            }

        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            themeSelection.name,
            style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}