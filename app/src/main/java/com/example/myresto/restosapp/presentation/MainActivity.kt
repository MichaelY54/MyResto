package com.example.myresto.restosapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.myresto.restosapp.presentation.detail.RestoDetailScreen
import com.example.myresto.restosapp.presentation.list.RestoScreen
import com.example.myresto.restosapp.presentation.list.RestoViewModel
import com.example.myresto.restosapp.presentation.list.RestosScreenState
import com.example.myresto.ui.theme.MyRestoTheme
import dagger.hilt.android.AndroidEntryPoint


//command untuk simulasi prompt
// adb shell am start -W -a android.intent.action.VIEW -d "https://www.restaurantsapp.details.com/2"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyRestoTheme {
//                RestoScreen()
//                RestoDetailScreen()
                RestoApp()
            }
        }
    }
}

@Composable
private fun RestoApp(){
    val navController = rememberNavController()
    NavHost(navController, startDestination = "restaurants"){
        composable(route = "restaurants"){
//            val viewModel: RestoViewModel = viewModel()
            val viewModel: RestoViewModel = hiltViewModel()
            viewModel.getRestoCoroutine()
            RestoScreen(
                state = viewModel.state.value,
                onItemClick = {id -> navController.navigate("restaurants/$id")},
                onFavoriteClick = {id, oldValue -> viewModel.toggleFavorite(id, oldValue)}
            )
//            {
//                id -> navController.navigate("restaurants/$id")
//            }
        }

        composable(route = "restaurants/{restaurant_id}",
            arguments = listOf(navArgument("restaurant_id"){
                type = NavType.IntType
            }),
            deepLinks = listOf(navDeepLink {
                uriPattern = "www.restaurantsapp.details.com/{restaurant_id}"
            })
        ){
            RestoDetailScreen()
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyRestoTheme {
        RestoScreen(
            RestosScreenState(listOf(), true),
            {},
            {_,_->}
        )
    }
}