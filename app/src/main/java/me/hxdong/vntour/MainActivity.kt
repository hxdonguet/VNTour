package me.hxdong.vntour

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.hxdong.vntour.ui.theme.VNTourTheme
import me.hxdong.vntour.ui.view.HomeView
import me.hxdong.vntour.ui.view.LoginView
import me.hxdong.vntour.ui.view.RegisterView
import me.hxdong.vntour.ui.view.TourDetailsView
import me.hxdong.vntour.viewmodel.HomeViewModel
import me.hxdong.vntour.viewmodel.LibraryViewModel
import me.hxdong.vntour.viewmodel.LoginViewModel
import me.hxdong.vntour.viewmodel.ProfileViewModel
import me.hxdong.vntour.viewmodel.RegisterViewModel
import me.hxdong.vntour.viewmodel.TourViewModel
import me.hxdong.vntour.viewmodel.viewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            VNTourTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VNTourApp()
                }
            }
        }
    }
}

@Composable
fun VNTourApp() {

    val navigator = rememberNavController()
    val registerViewModel = viewModel<RegisterViewModel>(factory = viewModelFactory {
        RegisterViewModel(VNTourApplication.appModule.authService)
    })
    val loginViewModel = viewModel<LoginViewModel>(factory = viewModelFactory {
        LoginViewModel(VNTourApplication.appModule.authService)
    })
    val homeViewModel = viewModel<HomeViewModel>(factory = viewModelFactory {
        HomeViewModel(VNTourApplication.appModule.tourRepo)
    })
    val tourViewModel = viewModel<TourViewModel>(factory = viewModelFactory {
        TourViewModel(VNTourApplication.appModule.tourRepo)
    })
    val libraryViewModel = viewModel<LibraryViewModel>(factory = viewModelFactory {
        LibraryViewModel(VNTourApplication.appModule.tourRepo)
    })
    val profileViewModel = viewModel<ProfileViewModel>(factory = viewModelFactory {
        ProfileViewModel(VNTourApplication.appModule.authService)
    })

    NavHost(navController = navigator, startDestination = Routes.Register.name) {
        composable(route = Routes.Home.name) {
            HomeView(
                homeViewModel, tourViewModel, libraryViewModel, profileViewModel,
                navigateToDetails = { id -> navigator.navigate("${Routes.Tour.name}/${id}") })
        }
        composable(
            route = "${Routes.Tour.name}/{id}"
        ) { backStackEntry ->
            val id = backStackEntry.arguments.let {
                if (it == null) {
                    "1"
                } else {
                    it.getString("id", "1")
                }
            }
            TourDetailsView(id = id.toInt()) {
                navigator.popBackStack()
            }
        }
        composable(route = Routes.Register.name) {
            RegisterView(
                state = registerViewModel.state.value,
                onRegisterClick = { user ->
                    registerViewModel.register(user);
                    navigator.navigate(Routes.Login.name)
                },
                onLoginClick = { navigator.navigate(Routes.Login.name) })
        }

        composable(route = Routes.Login.name) {
            LoginView(state = loginViewModel.state.value, onLoginClick = { username, password ->
                loginViewModel.login(username, password) {
                    navigator.navigate(Routes.Home.name) {
                        popUpTo(Routes.Register.name) {
                            inclusive = true
                        }
                    }
                }
            }) {
                navigator.navigate(Routes.Register.name)
            }
        }

    }

}

enum class Routes {
    Home, Tour, Register, Login
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VNTourTheme {
        VNTourApp()
    }
}