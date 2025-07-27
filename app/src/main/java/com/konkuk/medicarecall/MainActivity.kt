package com.konkuk.medicarecall

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.konkuk.medicarecall.navigation.BottomNavItem
import com.konkuk.medicarecall.navigation.NavGraph
import com.konkuk.medicarecall.ui.LoginViewModelFactory
import com.konkuk.medicarecall.ui.login_info.viewmodel.LoginViewModel
import com.konkuk.medicarecall.ui.login_senior.LoginSeniorViewModel
import com.konkuk.medicarecall.ui.theme.MediCareCallTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        setContent {
            MediCareCallTheme {
                val navController = rememberNavController()

                val navBarItems = listOf(
                    BottomNavItem(
                        label = "홈",
                        route = "home",
                        selectedIcon = R.drawable.ic_home_selected,
                        unselectedIcon = R.drawable.ic_home_unselected
                    ),
                    BottomNavItem(
                        label = "주간 통계",
                        route = "statistics",
                        selectedIcon = R.drawable.ic_statistics_selected,
                        unselectedIcon = R.drawable.ic_statistics_unselected
                    ),
                    BottomNavItem(
                        label = "설정",
                        route = "settings",
                        selectedIcon = R.drawable.ic_settings_selected,
                        unselectedIcon = R.drawable.ic_settings_unselected,
                    )
                )

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route


                var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

                val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory())
                val loginSeniorViewModel: LoginSeniorViewModel = viewModel()
                val bottomBarRoutes = listOf(
                    "home", "statistics", "settings",
                )



                Scaffold(
                    modifier = Modifier.background(MediCareCallTheme.colors.bg),
                    contentWindowInsets = WindowInsets.safeDrawing,
                    bottomBar = {
                        if (currentRoute in bottomBarRoutes)
                            NavigationBar(
                                modifier = Modifier
                                    .drawBehind {
                                        val strokeWidth = 1.dp.toPx()
                                        drawLine(
                                            color = Color(0xFFECECEC), // NavigationBar의 상단 테두리
                                            start = Offset(0f, 0f),
                                            end = Offset(size.width, 0f),
                                            strokeWidth = strokeWidth,
                                        )
                                    },
                                containerColor = MediCareCallTheme.colors.white
                            )
                            {
                                navBarItems.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        selected = currentRoute == item.route,
                                        alwaysShowLabel = true,
                                        label = {
                                            Text(
                                                text = item.label,
                                                style = MediCareCallTheme.typography.R_14
                                            )
                                        },
                                        onClick = {
                                            selectedIndex = index
                                            navController.navigate(item.route) {
                                                launchSingleTop = true

                                            }
                                        },
                                        icon = {
                                            Icon(
                                                painter = painterResource(
                                                    if (currentRoute == item.route) {
                                                        item.selectedIcon
                                                    } else item.unselectedIcon
                                                ),
                                                contentDescription = item.label
                                            )
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            indicatorColor = Color.Transparent,
                                            selectedIconColor = MediCareCallTheme.colors.main, // 선택된 아이콘 색상
                                            unselectedIconColor = Color.Black, // 선택되지 않은 아이콘 색상
                                            selectedTextColor = MediCareCallTheme.colors.main, // 선택된 텍스트 색상
                                            unselectedTextColor = Color.Black // 선택되지 않은 텍스트 색상
                                        )
                                    )
                                }
                            }
                    }
                ) { innerPadding ->
                    NavGraph(
                        navController = navController,
                        loginViewModel = loginViewModel,
                        loginSeniorViewModel = loginSeniorViewModel,
                        modifier = Modifier
                            .padding(bottom = innerPadding.calculateBottomPadding())
                    )

                }
            }
        }
    }
}