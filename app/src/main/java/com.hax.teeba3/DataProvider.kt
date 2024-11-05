// DataProvider.kt
package com.hax.teeba3

import com.hax.teeba3.model.Company

object DataProvider {
    val companies = listOf(
        Company(
            name = "Boston Scientific",
            logoResId = R.drawable.brand_logo_1,
            products = listOf(
                ProductConstants.BALLOON,
                ProductConstants.STENT,
                ProductConstants.GUIDE_WIRE,
                ProductConstants.GUIDE_CATHETER,
                ProductConstants.INFLATION_DEVICE,
                ProductConstants.MANIFOLD_SET,
                ProductConstants.INTRODUCER_SET,
                ProductConstants.WIRE_J,
                ProductConstants.DIAGNOSTIC_CATHETER,
                ProductConstants.Y_CONNECTOR
            )
        ),
        Company(
            name = "Abbott",
            logoResId = R.drawable.brand_logo_2,
            products = listOf(
                ProductConstants.STENT,
                ProductConstants.GUIDE_WIRE,
                ProductConstants.BALLOON,
                ProductConstants.GUIDE_CATHETER
            )
        ),
        Company(
            name = "Shunmei",
            logoResId = R.drawable.brand_logo_3,
            products = listOf(
                ProductConstants.INFLATION_DEVICE,
                ProductConstants.INTRODUCER_SET,
                ProductConstants.WIRE_J,
                ProductConstants.Y_CONNECTOR,
                ProductConstants.GUIDE_CATHETER,
                ProductConstants.MANIFOLD_SET
            )
        ),
        Company(
            name = "Lepu Medical",
            logoResId = R.drawable.brand_logo_4,
            products = listOf(
                ProductConstants.INFLATION_DEVICE,
                ProductConstants.INTRODUCER_SET,
                ProductConstants.WIRE_J,
                ProductConstants.Y_CONNECTOR,
                ProductConstants.GUIDE_CATHETER,
                ProductConstants.MANIFOLD_SET
            )
        ),
        Company(
            name = "Terumo",
            logoResId = R.drawable.brand_logo_5,
            products = listOf(
                ProductConstants.STENT,
                ProductConstants.INTRODUCER_SET,
                ProductConstants.WIRE_J,
                ProductConstants.Y_CONNECTOR,
                ProductConstants.GUIDE_CATHETER,
                ProductConstants.INFLATION_DEVICE,
                ProductConstants.BALLOON,
                ProductConstants.MANIFOLD_SET
            )
        ),
        Company(
            name = "Medtronic",
            logoResId = R.drawable.brand_logo_6,
            products = listOf(
                ProductConstants.STENT,
                ProductConstants.INTRODUCER_SET,
                ProductConstants.WIRE_J
            )
        ),
        Company(
            name = "SCW",
            logoResId = R.drawable.brand_logo_7,
            products = listOf(
                ProductConstants.Y_CONNECTOR,
                ProductConstants.INTRODUCER_SET,
                ProductConstants.WIRE_J,
                ProductConstants.GUIDE_CATHETER,
                ProductConstants.INFLATION_DEVICE,
                ProductConstants.MANIFOLD_SET
            )
        ),
        Company(
            name = "Biotronik",
            logoResId = R.drawable.brand_logo_8,
            products = listOf(
                ProductConstants.STENT
            )
        ),
        Company(
            name = "B.Braun",
            logoResId = R.drawable.brand_logo_9,
            products = listOf(
                ProductConstants.STENT
            )
        ),
        Company(
            name = "GE HEALTH CARE",
            logoResId = R.drawable.brand_logo_10,
            products = listOf(
                ProductConstants.OMNI_BACK
            )
        )
    )

    val coronaryProducts = listOf(
        ProductConstants.BALLOON,
        ProductConstants.STENT,
        ProductConstants.GUIDE_WIRE,
        ProductConstants.GUIDE_CATHETER,
        ProductConstants.INFLATION_DEVICE,
        ProductConstants.INTRODUCER_SET,
        ProductConstants.WIRE_J,
        ProductConstants.DIAGNOSTIC_CATHETER,
        ProductConstants.Y_CONNECTOR,
        ProductConstants.MANIFOLD_SET
    )
}
