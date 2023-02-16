package com.zaktsy.products.presentation.navigation

sealed class NavigationRoute(val path: String) {

    object Products: NavigationRoute("products")

    object ShoppingList: NavigationRoute("shoppingList")

    object AddProduct: NavigationRoute("addProduct")

    // build navigation path (for screen navigation)
    fun withArgs(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/$arg")
            }
        }
    }

    // build and setup route format (in navigation graph)
    fun withArgsFormat(vararg args: String) : String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/{$arg}")
            }
        }
    }
}
