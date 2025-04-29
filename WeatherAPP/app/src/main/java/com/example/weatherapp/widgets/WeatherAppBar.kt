package com.example.weatherapp.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.navigation.WeatherScreens
import com.example.weatherapp.screens.FavoriteViewModel
import kotlin.math.exp

@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    val showDialog = remember {
        mutableStateOf(false)
    }

    if (showDialog.value){
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }
    TopAppBar(
        title = {
            Text(text = title,
                color = MaterialTheme.colors.onSecondary,
                style = TextStyle(fontWeight = FontWeight.Bold,
                    fontSize = 15.sp))
    },
        actions = {
            if (isMainScreen){
                IconButton(onClick = {
                    onAddActionClicked.invoke()
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "search icon")
                }
                IconButton(onClick = {
                    showDialog.value = true
                }) {
                    Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "More Icon")
                }
            }else Box {}
        },
        navigationIcon = {
            if (icon != null){
                Icon(imageVector = icon, contentDescription = null,
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.clickable {
                        onButtonClicked.invoke()
                    })
            }
            if(isMainScreen){
                Icon(imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite Icon",
                    modifier = Modifier
                        .scale(0.9f)
                        .clickable{
                            val dataList = title.split(",")
                            favoriteViewModel.insertFavorite(
                                Favorite(
                                    city= dataList[0],
                                    country = dataList[1]
                                )
                            )
                        },
                    tint = Color.Red.copy(alpha = 0.6f)
                )
            }


        },
        backgroundColor = Color.Transparent,
        elevation = elevation
    )

}

@Composable
fun ShowSettingDropDownMenu(
    showDialog: MutableState<Boolean>,
    navController: NavController
) {
    var expanded by remember {
        mutableStateOf(true)
    }

    val items = listOf("About","Favorites", "Settings")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false},
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        ) {
            items.forEachIndexed()
            {
                index, text->
                DropdownMenuItem(onClick = {
                    expanded = false
                    showDialog.value = false
                }
                ) {
                    Icon(imageVector = when(text){
                        "About" -> Icons.Default.Info
                        "Favorites" -> Icons.Default.FavoriteBorder
                        else -> Icons.Default.Settings
                    },
                        contentDescription = null,
                        tint = Color.LightGray)
                    Text(text = text,
                        modifier = Modifier
                            .clickable{
                                navController.navigate(
                                    when (text){
                                        "About" -> WeatherScreens.AboutScreen.name
                                        "Favorites" -> WeatherScreens.FavoritesScreen.name
                                        else -> WeatherScreens.SettingsScreen.name
                                    }
                                )

                            },
                        fontWeight = FontWeight.W300)
                }

            }
            }
        }
    }