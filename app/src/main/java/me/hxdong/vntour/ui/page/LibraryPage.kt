package me.hxdong.vntour.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.hxdong.vntour.viewmodel.LibraryViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryPage(viewModel: LibraryViewModel, navigateToDetails: (Int) -> Unit) {

    val visibleTours = viewModel.visibleTours

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Danh sách Tour đăng ký") })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            TourList(tours = visibleTours) { id ->
                navigateToDetails(id)
            }
        }
    }
}
