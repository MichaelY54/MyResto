package com.example.myresto.restosapp.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myresto.restosapp.presentation.list.RestoDetail
import com.example.myresto.restosapp.presentation.list.RestoIcon

@Composable
fun RestoDetailScreen() {
    val viewModel: RestoDetailViewModel = viewModel()
    val item = viewModel.state.value
    if(item != null){
        Column() {
            RestoIcon(Icons.Filled.Place, Modifier.padding(top = 32.dp, bottom = 32.dp))
            RestoDetail(item.title, item.description, Modifier.padding(bottom = 32.dp))
            Text("nantikan info selanjutnya")
        }
    }
}