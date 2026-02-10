package com.example.a5046.presentation
import NavBarItem
import android.app.Activity
import android.content.pm.PackageManager
import android.location.LocationManager
import SignUpViewModel
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import com.example.a5046.ui.theme._5046Theme
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
//import com.example.a5046.presentation.MainPage.MainPage
import com.example.a5046.presentation.MainPage.lunaLogTopBar
import com.example.a5046.presentation.diary.DiaryItemScreen
import com.example.a5046.presentation.diary.DiaryScreen
import com.example.a5046.presentation.diary.DiaryViewModel
import com.example.a5046.presentation.main.mainpage.MainPageScreen
import com.example.a5046.presentation.moodtracker.AnswersViewModel
import com.example.a5046.presentation.moodtracker.MoodScreen
import com.example.a5046.presentation.period.PeriodTracker
import com.example.a5046.presentation.period.PeriodViewModel
import com.example.a5046.presentation.report.ReportScreen
import com.example.a5046.presentation.setting.NavigationViewModel
import com.example.a5046.presentation.setting.NotificationPage
import com.example.a5046.presentation.setting.PasswordPage
import com.example.a5046.presentation.setting.ProfilePage
import com.example.a5046.presentation.setting.SettingScreen
import com.example.a5046.presentation.signin.SignInScreen
import com.example.a5046.presentation.signup.SignUpScreen
import com.example.a5046.presentation.userdata.BirthDate
import com.example.a5046.presentation.userdata.CycleLength
import com.example.a5046.presentation.userdata.LastDate
import com.example.a5046.presentation.userdata.PeriodLength
import com.example.a5046.presentation.userdata.UserName
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.auth


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val DiaryViewModel : DiaryViewModel by viewModels()
        val PeriodViewModel: PeriodViewModel by viewModels()
        val AnswersViewModel: AnswersViewModel by viewModels()
        val NavigationViewModel: NavigationViewModel by viewModels()

        setContent {
            _5046Theme {
                val navController = rememberNavController()

                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    MainPage(navController)
//                }
                val backStackEntry by navController.currentBackStackEntryAsState()

                var currentScreen = Routes.valueOf( Routes.Main.value)
                if(backStackEntry?.destination?.route != "diary_item/{ID}"){
                    currentScreen = Routes.valueOf(backStackEntry?.destination?.route ?: Routes.Main.value)
                }

                Scaffold(
                    modifier = Modifier,
                    bottomBar = {
                        if(currentScreen.value == "Main" || currentScreen.value == "Mood"
                            || currentScreen.value == "PeriodTracker" || currentScreen.value == "Report"
                            || currentScreen.value == "Setting") {
                            BottomNavigation(backgroundColor = androidx.compose.material.MaterialTheme.colors.background) {
                                val navBackStackEntry by
                                navController.currentBackStackEntryAsState()
                                val currentDestination = navBackStackEntry?.destination
                                NavBarItem().NavBarItems().forEach { navItem ->
                                    BottomNavigationItem(
                                        icon = {
                                            Icon(
                                                if(navItem.route == "Main" &&
                                                    (currentDestination?.route == "Diary" ||
                                                    currentDestination?.route == "Mood" ||
                                                    currentDestination?.route == "PeriodTracker")){
                                                    painterResource(navItem.iconSelected)
                                                }
                                                else if (currentDestination?.route == navItem.route)
                                                    painterResource(navItem.iconSelected)
                                                else
                                                    painterResource(navItem.icon),
                                                contentDescription = navItem.label,
                                            )
                                        },
                                        label = { Text(navItem.label) },
                                        selected = currentDestination?.hierarchy?.any {
                                            it.route == navItem.route
                                        } == true,
                                        onClick = {
                                            navController.navigate(navItem.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    },

                        topBar = {
                            if(currentScreen.value!= "diary_item/{ID}" && currentScreen.value!= "SignIn")
                                lunaLogTopBar(
                                    currentScreen = currentScreen,
                                    canNavigateBack = navController.previousBackStackEntry != null,
                                    navigateUp = { navController.navigateUp() },
                                    navController = navController
                                )
                        }

                ) { paddingValues ->
                    NavHost(
                        navController,
//                        startDestination = Routes.Setting.value,
                        startDestination = if (Firebase.auth.currentUser != null &&
                            currentScreen.value != "Username" && currentScreen.value != "BirthDate"
                            && currentScreen.value != "LastDate" && currentScreen.value != "PeriodLength"
                            && currentScreen.value != "CycleLength")
                        {
                            Routes.Main.value
                        } else {
                            Routes.SignIn.value
                        },
                        Modifier.padding(paddingValues)
                        ) {
                        //         1. add your route in Routes
                        //         2.Add all your screen here
                        composable(Routes.Main.value) {
                            MainPageScreen(navController)
                        }
                        composable(Routes.Report.value) {
                            ReportScreen(navController, answersViewModel = AnswersViewModel)
                        }
                        composable(Routes.Diary.value) {
                            DiaryScreen(navController, DiaryViewModel)
                        }
                        composable(
                            Routes.DiaryItem.value,
                            arguments = listOf(navArgument("ID") {
                                type = NavType.IntType
                            }
                            )
                        ) {
                            DiaryItemScreen(
                                navController = navController,
                                DiaryViewModel,
                                it.arguments?.getInt("ID")!!
                            )
                        }
                        composable(Routes.Mood.value){
                            MoodScreen(answersViewModel = AnswersViewModel)
                        }
                        composable(Routes.Mood.value)
                        {
                            MoodScreen(answersViewModel = viewModel())
                        }
                        composable(Routes.SignUp.value){
                            SignUpScreen(navController)
                        }
                        composable(Routes.Setting.value){
                            SettingScreen(navController)
                        }
                        composable(Routes.SignIn.value){
                            SignInScreen(navController)
                        }
                        composable(Routes.PeriodTracker.value){
                            PeriodTracker(navController, PeriodViewModel)
                        }
                        composable(Routes.Username.value) {
                            UserName(navController, NavigationViewModel)
                        }
                        composable(Routes.LastDate.value) {
                            LastDate(navController, NavigationViewModel)
                        }
                        composable(Routes.BirthDate.value) {
                            BirthDate(navController, NavigationViewModel)
                        }
                        composable(Routes.PeriodLength.value) {
                            PeriodLength(navController, NavigationViewModel)
                        }
                        composable(Routes.CycleLength.value) {
                            CycleLength(navController, NavigationViewModel, PeriodViewModel)
                        }
                        composable(Routes.Notification.value) {
                            NotificationPage(navController)
                        }
                        composable(Routes.Profile.value) {
                            ProfilePage(navController, NavigationViewModel)
                        }
                        composable(Routes.Password.value) {
                            PasswordPage(navController)
                        }

                    }
                }
            }
        }
    }
}
