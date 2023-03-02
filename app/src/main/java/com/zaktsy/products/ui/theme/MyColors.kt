package com.zaktsy.products.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val seed = Color(0xFF6750A4)
val Yellow = Color(0xFFFFDA5B)
val Green = Color(0xFF458F21)
val Red = Color(0xFFD24544)
val light_Yellow = Color(0xFF725C00)
val light_onYellow = Color(0xFFFFFFFF)
val light_YellowContainer = Color(0xFFFFE07E)
val light_onYellowContainer = Color(0xFF231B00)
val dark_Yellow = Color(0xFFE7C447)
val dark_onYellow = Color(0xFF3C2F00)
val dark_YellowContainer = Color(0xFF564500)
val dark_onYellowContainer = Color(0xFFFFE07E)
val light_Green = Color(0xFF276C00)
val light_onGreen = Color(0xFFFFFFFF)
val light_GreenContainer = Color(0xFFA6F77D)
val light_onGreenContainer = Color(0xFF072100)
val dark_Green = Color(0xFF8BDA64)
val dark_onGreen = Color(0xFF113800)
val dark_GreenContainer = Color(0xFF1C5200)
val dark_onGreenContainer = Color(0xFFA6F77D)
val light_Red = Color(0xFFB02C2E)
val light_onRed = Color(0xFFFFFFFF)
val light_RedContainer = Color(0xFFFFDAD7)
val light_onRedContainer = Color(0xFF410004)
val dark_Red = Color(0xFFFFB3AE)
val dark_onRed = Color(0xFF68000C)
val dark_RedContainer = Color(0xFF8E111A)
val dark_onRedContainer = Color(0xFFFFDAD7)

@get:Composable
val Colors.Yellow: Color
    get() = if (isLight) light_Yellow else dark_Yellow

@get:Composable
val Colors.Green: Color
    get() = if (isLight) light_Green else dark_Green

@get:Composable
val Colors.Red: Color
    get() = if (isLight) light_Red else dark_Red

@get:Composable
val Colors.onYellow: Color
    get() = if (isLight) light_onYellow else dark_onYellow

@get:Composable
val Colors.onGreen: Color
    get() = if (isLight) light_onGreen else dark_onGreen

@get:Composable
val Colors.onRed: Color
    get() = if (isLight) light_onRed else dark_onRed

@get:Composable
val Colors.YellowContainer: Color
    get() = if (isLight) light_YellowContainer else dark_YellowContainer

@get:Composable
val Colors.GreenContainer: Color
    get() = if (isLight) light_GreenContainer else dark_GreenContainer

@get:Composable
val Colors.RedContainer: Color
    get() = if (isLight) light_RedContainer else dark_RedContainer

@get:Composable
val Colors.onYellowContainer: Color
    get() = if (isLight) light_onYellowContainer else dark_onYellowContainer

@get:Composable
val Colors.onGreenContainer: Color
    get() = if (isLight) light_onGreenContainer else dark_onGreenContainer

@get:Composable
val Colors.onRedContainer: Color
    get() = if (isLight) light_onRedContainer else dark_onRedContainer