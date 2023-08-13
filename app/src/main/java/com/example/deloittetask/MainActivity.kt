package com.example.deloittetask
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.deloittetask.presentation.home.dashboard.DashboardFragment
import com.example.deloittetask.presentation.home.more.MoreFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val dashboardFragment = DashboardFragment()
    private val profileFragment = MoreFragment()

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_nav_dashboard -> {
                    switchFragment(dashboardFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.bottom_nav_more -> {
                    switchFragment(profileFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        switchFragment(dashboardFragment)
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}
