package com.altunoymak.eterationemarketcasestudy

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.altunoymak.eterationemarketcasestudy.databinding.ActivityMainBinding
import com.altunoymak.eterationemarketcasestudy.presentation.ui.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val productViewModel: ProductViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNavigationView,navHostFragment.navController)
        productViewModel.getCartItemCount()
        productViewModel.cartItemCount.observe(this) { count ->
            updateBadge(count)
        }
    }
    fun updateBadge(count: Int) {
        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.basketFragment)
        badge.number = count
        badge.isVisible = count > 0
    }
}