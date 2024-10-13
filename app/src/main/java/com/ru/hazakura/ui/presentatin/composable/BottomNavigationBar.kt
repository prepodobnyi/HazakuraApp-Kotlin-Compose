package com.ru.hazakura.ui.presentatin.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.ru.hazakura.ui.presentatin.composableItem.BottomNavigationItem
import com.ru.hazakura.util.Screen


val bottomNavItems = listOf(
    BottomNavigationItem(
        icon = Icons.Rounded.Menu,
        route = Screen.HomeTab.route
    ),
    BottomNavigationItem(
        icon = Icons.Rounded.Search,
        route = Screen.SearchTab.route
    ),
    BottomNavigationItem(
        icon = Icons.Rounded.Bookmark,
        route = Screen.BookmarkTab.route
    ),
    BottomNavigationItem(
        icon = Icons.Rounded.Person,
        route = Screen.Person.route
    )
)



@Composable
fun BottomNavigationBar(navController: NavController,currentDestination: NavDestination?) {

    val currentIndex = bottomNavItems.indexOfFirst { it.route == currentDestination?.route }


    BottomNavigation {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .height(65.dp)
                .padding(bottom = 23.dp)
        ) {
            bottomNavItems.forEachIndexed { index, bottomNavItem ->
                NavigationBarItem(
                    selected = index == currentIndex,
                    onClick = {
                        navController.navigate(bottomNavItem.route){
                            popUpTo(navController.graph.startDestinationId){
                                inclusive = true
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(imageVector = bottomNavItem.icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground)
                    }
                )
            }
        }
    }
}




















