package com.example.a5046.presentation.MainPage

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import com.example.a5046.R
import com.example.a5046.components.LunaLogTitle
import com.example.a5046.presentation.Routes


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MainPage( navController: NavHostController = rememberNavController()) {
//    val backStackEntry by navController.currentBackStackEntryAsState()
//    val currentScreen = Routes.valueOf(
//        backStackEntry?.destination?.route ?: Routes.Main.value
//    )
//
////    val navController = rememberNavController()
//    Scaffold(
//        modifier = Modifier,
//        bottomBar = {
//            BottomNavigation (backgroundColor = MaterialTheme.colors.background ){
//                val navBackStackEntry by
//                navController.currentBackStackEntryAsState()
//                val currentDestination = navBackStackEntry?.destination
//                NavBarItem().NavBarItems().forEach { navItem ->
//                    BottomNavigationItem(
//                        icon = { Icon(
//                            if (currentDestination?.route == navItem.route)
//                                painterResource(navItem.iconSelected)
//                            else
//                                painterResource(navItem.icon)
//                            ,
//                            contentDescription = navItem.label,
//                    )},
//                        label = { Text(navItem.label) },
//                        selected = currentDestination?.hierarchy?.any {
//                            it.route == navItem.route
//                        } == true,
//                        onClick = {
//                            navController.navigate(navItem.route) {
//                                popUpTo(navController.graph.findStartDestination().id) {
//                                    saveState = true
//                                }
//                                launchSingleTop = true
//                                restoreState = true
//                            }
//                        }
//                    )
//                }
//            }
//        },
//
//        topBar = {
//            lunaLogTopBar(
//                currentScreen = currentScreen,
//                canNavigateBack = navController.previousBackStackEntry != null,
//                navigateUp = { navController.navigateUp() }
//            )
//        }
//    ) { paddingValues ->
//        NavHost(
//            navController,
//            startDestination = if (Firebase.auth.currentUser != null) {
//                Routes.Main.value
//            } else {
//                Routes.SignIn.value
//            },
//            Modifier.padding(paddingValues)
//        ) {
////            Add all your screen here
//            composable(Routes.Report.value) {
//                ReportScreen(navController)
//            }
//            composable(Routes.Main.value) {
//                MainPageScreen(navController)
//            }
//            composable(Routes.Report.value) {
//                ReportScreen(navController)
//            }
//            composable(Routes.Diary.value) {
//                DiaryScreen(navController, onNextButtonClicked = { navController.navigate(Routes.DiaryItem.value) }, viewModel())
//            }
//            composable(Routes.DiaryItem.value) {
//                DiaryItemScreen(navController, viewModel())
//            }
//            composable(Routes.Mood.value){
//                MoodScreen(answersViewModel = viewModel())
//            }
//            composable(Routes.SignUp.value){
//                SignUpScreen(navController)
//            }
//            composable(Routes.Setting.value){
//                SettingScreen()
//            }
//            composable(Routes.SignIn.value){
//                SignInScreen(navController)
//            }
//            composable(Routes.PeriodTracker.value){
//                PeriodTracker(viewModel())
//            }
////            composable(Routes.SignIn.value){
////                SignInScreen(navController)
////            }
//
//        }
//    }
//}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun lunaLogTopBar(
    currentScreen: Routes,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    modifier: Modifier = Modifier,
    navController: NavHostController,
){
    if(currentScreen.value != "Main" && currentScreen.value != "Report" && currentScreen.value != "Setting") {
        TopAppBar(
            title = { LunaLogTitle(currentScreen.value) },
            modifier = modifier,
            navigationIcon = {
                if(currentScreen.value == "Diary")
                {
                    IconButton(onClick = { navController.navigate(Routes.Main.value) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_left),
                            contentDescription = "go back"
                        )
                    }}
                else if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_left),
                            contentDescription = "go Back"
                        )
                    }
                }
            }
        )
    }
}

