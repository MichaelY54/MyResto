package com.example.myresto.restosapp.presentation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myresto.restosapp.domain.Resto

@Composable
fun RestoScreen(
        state: RestosScreenState,
        onItemClick: (id: Int) -> Unit,
        onFavoriteClick: (id: Int, oldvalue: Boolean) -> Unit
    ) {
//    val viewModel: RestoViewModel = viewModel()
//    val state = viewModel.state.value
//    LaunchedEffect(key1 = "request restos"){
//        viewModel.getRestoCoroutine()
//    }
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        LazyColumn(contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp)){
            items(state.restos){
                resto -> RestoItem(
                    resto,
//                    onFavoriteClick = { id, oldValue -> viewModel.toggleFavorite(id, oldValue) },
                    onFavoriteClick = { id, oldValue -> onFavoriteClick(id, oldValue) },
                    onItemClick = { id -> onItemClick(id)}
                )
            }
        }
        if(state.isLoading)
            CircularProgressIndicator()
        if(state.error != null)
            Text(text = state.error)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestoItem(item: Resto, onFavoriteClick: (id: Int, oldvalue: Boolean) -> Unit, onItemClick: (id: Int) -> Unit){
//    val favoriteState = remember{mutableStateOf(false)}

    val icon = if(item.isFavorite)
        Icons.Filled.Favorite
    else
        Icons.Filled.FavoriteBorder

    Card(elevation = CardDefaults.cardElevation(defaultElevation = 10.dp), modifier = Modifier
        .padding(8.dp)
        .clickable { onItemClick(item.id) }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            RestoIcon(Icons.Filled.Place, Modifier.weight(0.15f))
            RestoDetail(item.title, item.description, Modifier.weight(0.70f))
            FavoriteIcon(icon, Modifier.weight(0.15f)){
        //            favoriteState.value = !favoriteState.value
                onFavoriteClick(item.id, item.isFavorite)
            }
        }
    }
}

@Composable
public fun RestoDetail(title: String, description: String, modifier: Modifier, ){
    Column(modifier = modifier) {
        Text(text = title, style = MaterialTheme.typography.headlineMedium)
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant) {
            Text(text = description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun FavoriteIcon(icon: ImageVector, modifier: Modifier, onClick:() -> Unit){
    Image(
        imageVector = icon,
        contentDescription = "favorite resto",
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() }
//            .clickable { favoriteState.value = !favoriteState.value }
    )
}

@Composable
public fun RestoIcon(icon: ImageVector, modifier: Modifier, onClick: () -> Unit = {}){
    Image(imageVector = icon, contentDescription = "ikon resto", modifier = Modifier.padding(8.dp))
    onClick()
}

