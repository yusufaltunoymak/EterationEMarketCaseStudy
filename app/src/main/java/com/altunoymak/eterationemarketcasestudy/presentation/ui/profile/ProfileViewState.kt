package com.altunoymak.eterationemarketcasestudy.presentation.ui.profile

import com.altunoymak.eterationemarketcasestudy.data.local.model.Order

data class ProfileViewState(
    val isLoading: Boolean? = null,
    val orderList: List<Order>? = null,
    val errorMessage: String? = null
)