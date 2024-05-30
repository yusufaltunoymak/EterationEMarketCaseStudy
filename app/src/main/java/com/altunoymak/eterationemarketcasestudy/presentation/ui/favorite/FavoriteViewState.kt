package com.altunoymak.eterationemarketcasestudy.presentation.ui.favorite

import com.altunoymak.eterationemarketcasestudy.data.local.model.FavoriteProduct

data class FavoriteViewState(
    val isLoading: Boolean? = null,
    val favList: List<FavoriteProduct>? = null,
    val errorMessage: String? = null
)
