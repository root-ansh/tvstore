package io.github.curioustools.tvstore.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.unit.sp
import androidx.tv.material3.LocalContentColor
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Typography
import androidx.tv.material3.darkColorScheme
import io.github.curioustools.tvstore.R


data class UpdatedColorXml(

    //TEXT
    val textDarkV0: Color = Color(0xFF101F43),
    val textDarkV1: Color = Color(0xFF404C69),
    val textDarkV2: Color = Color(0xFF5F6981),
    val textDarkV4: Color = Color(0xFF9198A9),
    val textLight: Color = Color(0xFFFFFFFF),
    val textTeal:Color = Color(0xFF21484C),
    val textGradient: List<Color> = listOf(Color(0xFF0089F3), Color(0xFF5C63D6)),

    //SURFACE
    val surfaceMain: Color = Color(0xFFF5F5F5),
    val surfaceHigh: Color = Color(0xFFFFFFFF),
    val surfaceLow: List<Color> = listOf(Color(0xFF2A3ACA), Color(0xFF0B2F5E)),

    //CTA's

    // CTA Primary - Enabled
    val ctaPrimaryEnabledSurface: List<Color> = listOf(Color(0xFF35D3E1), Color(0xFF0D3594)),
    val ctaPrimaryEnabledText: Color = Color(0xFFFFFFFF),

    // CTA Primary - Disabled
    val ctaPrimaryDisabledSurface: List<Color> = listOf(Color(0xFFB1D9DE), Color(0xFFC0CEF0)),
    val ctaPrimaryDisabledText: Color = Color(0xFFFFFFFF),

    // CTA Secondary - Enabled
    val ctaSecondaryEnabledSurface: Color = Color(0xFFEFF0F3),
    val ctaSecondaryEnabledText: Color = Color(0xFF264A9F),

    // CTA Secondary - Disabled
    val ctaSecondaryDisabledSurface: Color = Color(0xFFEFF0F3),
    val ctaSecondaryDisabledText: Color = Color(0xFF9BACD3),

    // CTA Tertiary
    val ctaTertiarySurface: Color = Color(0x00FFFFFF), // Transparent white
    val ctaTertiaryText: Color = Color(0xFF264A9F),


    // Outline & Divider
    val outlineV0: Color = Color(0xFFE9EDF5),
    val outlineV1: Color = Color(0xFFBCC7E1),

    // Shadows
    val shadowBuyButton: Color = Color(0x4D1F7DB8),   // 30% opacity
    val shadowBottomNav: Color = Color(0x0D1F7DB8),   // 5% opacity

    // Icon Colors
    val iconBlue: Color = Color(0xFF264A9F),
    val iconTeal: Color = Color(0xFF4EACB5),
    val iconWhite: Color = Color(0xFFFFFFFF),

    // Scrim
    val scrimV0: Color = Color(0xCC020E28), // 80% opacity

    // Solid Container
    val containerWhite: Color = Color(0xFFFFFFFF),

    // Gradient/section
    val sectionGradientV1: List<Color> = listOf(Color(0xFFD1AEFE), Color(0xFF47CEFF)),
    val sectionGradientV2: List<Color> = listOf(Color(0xFFE8F1FF), Color(0xFFFFFFFF)),



    //Gradient/Card
    val cardGradientCoupon: List<Color> = listOf(
        Color(0xFF35D3E1), Color(0xFF4CB1EA)
    ),

    val nstpGradientCounterOffer:List<Color> = listOf(Color(0xFF4CAF50), Color(0xFF81C784)),
    val nstpGradientInfo:List<Color> = listOf(Color(0xFFF3791F), Color(0xFFFBD5BA)),
    val nstpGradientDefault:List<Color> = listOf(Color(0xFFD0DDFF), Color(0xFF6890F1)),


    val darkBgGradient: List<Color> = listOf(
        Color(0x00000000), Color(0xCC000000)
    ),


    // Gradient/p0
    val gradientPriority0: List<Color> = listOf(
        Color(0xFFF8C9C7), Color(0xFFD64741)
    ),
    val gradientPriority1: List<Color> = listOf(
        Color(0xFFFBD5BA), Color(0xFFF3791F)
    ),
    val gradientPriority2: List<Color> = listOf(
        Color(0xFF4975EF), Color(0xFF6890F1)
    ),
    val gradientPriority3: List<Color> = listOf(
        Color(0xFF84C193), Color(0xFF439455)
    ),

    val gradientPriority1V2:Color = Color(0xFFF3791F),

    // State
    val stateLink: Color = Color(0xFF9BACD3),
    val stateActivePlus: Color = Color(0xFF34DE00),
    val stateActive: Color = Color(0xFF4EA965),
    val stateExpiring: Color = Color(0xFFDD6E1C),
    val stateDecline: Color = Color(0xFFA32824),
    val stateExpiredNotification: Color = Color(0xFFE63933),


    // Pastel
    val pastelV1Surface: Color = Color(0xFFE3F0FF),
    val pastelV1Outline: Color = Color(0xFFC2DEFF),
    val pastelV2Surface: Color = Color(0xFFECE8FF),
    val pastelV2Outline: Color = Color(0xFFD9D1FF),
    val pastelV3Surface: Color = Color(0xFFF7DDFF),
    val pastelV3Outline: Color = Color(0xFFF2C9FF),
    val pastelV4Surface: Color = Color(0xFFFFE3F6),
    val pastelV4Outline: Color = Color(0xFFFFD6F2),
    val pastelV5Surface: Color = Color(0xFFFDEBEB),
    val pastelV5Outline: Color = Color(0xFFFFD5D5),
    val pastelV6Surface: Color = Color(0xFFFEF2E9),
    val pastelV6Outline: Color = Color(0xFFFFE8D8),
    val pastelV7Surface: Color = Color(0xFFE3FFFD),
    val pastelV7Outline: Color = Color(0xFF90F6EE),
    val pastelV8Surface: Color = Color(0xFFDCF5F8),
    val pastelV8Outline: Color = Color(0xFFABECF3),
    val pastelV9Surface: Color = Color(0xFFFFFDD2),
    val pastelV9Outline: Color = Color(0xFFF2EEAC),

    val coolColors: List<Color> = listOf(pastelV1Surface,pastelV2Surface,pastelV3Surface,pastelV4Surface,pastelV5Surface,pastelV6Surface,pastelV7Surface),

    val pairs:List<Pair<Color, Color>> = listOf(
        pastelV1Surface to pastelV1Outline,
        pastelV2Surface to pastelV2Outline,
        pastelV3Surface to pastelV3Outline,
        pastelV4Surface to pastelV4Outline,
        pastelV5Surface to pastelV5Outline,
        pastelV6Surface to pastelV6Outline,
        pastelV7Surface to pastelV7Outline,
        pastelV8Surface to pastelV8Outline,
        pastelV9Surface to pastelV9Outline,
    ),


    // Form Colors
    val alertSuccess: Color = Color(0xFF4EA965),
    val alertWarning: Color = Color(0xFFDD6E1C),
    val alertError: Color = Color(0xFFE63933),
    val textLabel: Color = Color(0xFF516EB2),
    val textInput: Color = Color(0xFF101F43),
    val textPlaceholder: Color = Color(0xFF5F6981),

    // Stepper Colors
    val stepperIconText: Color = Color(0xFFFFFFFF),
    val stepperLabel: Color = Color(0xFF264A9F),
    val stepperOutline: Color = Color(0xFFE9EDF5),
    val stepperIconActive: List<Color> = listOf(Color(0xFF0089F3), Color(0xFF5C63D6)),
    val stepperIconInactive: Color = Color(0xFFBCC7E1),

    // Background Colors
    val bgInput: Color = Color(0xFFFFFFFF),
    val bgDropdownSelected: Color = Color(0xFFE2EDFF),

    // Text Icon Color
    val textIcon: Color = Color(0xFF264A9F),

    val shimmerColor: Color = Color(0xFFD4D4D4),



    // Container Color
    val containerSolidTeal30:Color = Color(0xFFC8E5E8),

    val ratingStarColor: Color = Color(0xFFF1D500), // Gold color for rating stars

    //Use Full Colors
    val black: Color = Color(0xFF000000),
    val red: Color = Color(0xFFFF0000),
    val green: Color = Color(0xFF00FF00),
    val blue: Color = Color(0xFF0000FF),
    val yellow: Color = Color(0xFFFFFF00),
    val purple: Color = Color(0xFF800080),
    val orange: Color = Color(0xFFFFA500),
    val pink: Color = Color(0xFFFFC0CB),
    val brown: Color = Color(0xFFA52A2A),
    val cyan: Color = Color(0xFF00FFFF),
    val magenta: Color = Color(0xFFFF00FF),
    val transparent: Color = Color.Transparent,
    val white: Color = Color(0xFFFFFFFF),
    val starYellow:Color = Color(0xFFF1D500),
    val success50: Color = Color(0xFFe9f4ec),
    val newShimmerColor: Color = Color(0xFFE6E6E6),
    val splashScreenColor: Color = Color(0xFF005baa),
    val coachMarkDimBackground:Color = Color(0xE6101F43)

)


private val Inter = FontFamily(
    fonts = listOf(
        Font(R.font.inter_black, weight = FontWeight.Black),
        Font(R.font.inter_bold, weight = FontWeight.Bold),
        Font(R.font.inter_extra_bold, weight = FontWeight.ExtraBold),
        Font(R.font.inter_extra_light, weight = FontWeight.ExtraLight),
        Font(R.font.inter_light, weight = FontWeight.Light),
        Font(R.font.inter_medium, weight = FontWeight.Medium),
        Font(R.font.inter_regular, weight = FontWeight.Normal),
        Font(R.font.inter_semi_bold, weight = FontWeight.SemiBold),
        Font(R.font.inter_thin, weight = FontWeight.Thin)
    )
)

val LexendExa = FontFamily(
    fonts = listOf(
        Font(R.font.lexend_exa_medium, weight = FontWeight.Medium),
    )
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontSize = 57.sp,
        lineHeight = 64.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = (-0.25).sp,
        fontFamily = Inter,
        textMotion = TextMotion.Animated
    ),
    displayMedium = TextStyle(
        fontSize = 45.sp,
        lineHeight = 52.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp,
        fontFamily = Inter,
        textMotion = TextMotion.Animated
    ),
    displaySmall = TextStyle(
        fontSize = 36.sp,
        lineHeight = 44.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp,
        fontFamily = Inter,
        textMotion = TextMotion.Animated
    ),
    headlineLarge = TextStyle(
        fontSize = 32.sp,
        lineHeight = 40.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp,
        fontFamily = Inter,
        textMotion = TextMotion.Animated
    ),
    headlineMedium = TextStyle(
        fontSize = 28.sp,
        lineHeight = 36.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp,
        fontFamily = Inter,
        textMotion = TextMotion.Animated
    ),
    headlineSmall = TextStyle(
        fontSize = 24.sp,
        lineHeight = 32.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp,
        fontFamily = Inter,
        textMotion = TextMotion.Animated
    ),
    titleLarge = TextStyle(
        fontSize = 22.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp,
        fontFamily = Inter,
        textMotion = TextMotion.Animated
    ),
    titleMedium = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.15.sp,
        fontFamily = Inter,
        textMotion = TextMotion.Animated
    ),
    titleSmall = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.1.sp,
        fontFamily = Inter,
        textMotion = TextMotion.Animated
    ),
    labelLarge = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.1.sp,
        fontFamily = Inter,
        textMotion = TextMotion.Animated
    ),
    labelMedium = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.25.sp,
        fontFamily = Inter,
        textMotion = TextMotion.Animated
    ),
    labelSmall = TextStyle(
        fontSize = 11.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.1.sp,
        fontFamily = Inter,
        textMotion = TextMotion.Animated
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.5.sp,
        fontFamily = Inter,
        textMotion = TextMotion.Animated
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.25.sp,
        fontFamily = Inter,
        textMotion = TextMotion.Animated
    ),
    bodySmall = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.2.sp,
        fontFamily = Inter,
        textMotion = TextMotion.Animated
    )
)

private val darkColorScheme @Composable get() = darkColorScheme(
    primary = colorResource(R.color.primary),
    onPrimary = colorResource(R.color.onPrimary),
    primaryContainer = colorResource(R.color.primaryContainer),
    onPrimaryContainer = colorResource(R.color.onPrimaryContainer),
    secondary = colorResource(R.color.secondary),
    onSecondary = colorResource(R.color.onSecondary),
    secondaryContainer = colorResource(R.color.secondaryContainer),
    onSecondaryContainer = colorResource(R.color.onSecondaryContainer),
    tertiary = colorResource(R.color.tertiary),
    onTertiary = colorResource(R.color.onTertiary),
    tertiaryContainer = colorResource(R.color.tertiaryContainer),
    onTertiaryContainer = colorResource(R.color.onTertiaryContainer),
    background = colorResource(R.color.background),
    onBackground = colorResource(R.color.onBackground),
    surface = colorResource(R.color.surface),
    onSurface = colorResource(R.color.onSurface),
    surfaceVariant = colorResource(R.color.surfaceVariant),
    onSurfaceVariant = colorResource(R.color.onSurfaceVariant),
    error = colorResource(R.color.error),
    onError = colorResource(R.color.onError),
    errorContainer = colorResource(R.color.errorContainer),
    onErrorContainer = colorResource(R.color.onErrorContainer),
    border = colorResource(R.color.border),
)

private val LocalUpdatedColors = staticCompositionLocalOf { UpdatedColorXml() }
val MaterialTheme.localUpdatedColors: UpdatedColorXml
    @Composable
    @ReadOnlyComposable
    get() = LocalUpdatedColors.current


@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalUpdatedColors provides UpdatedColorXml(),
        LocalContentColor provides MaterialTheme.colorScheme.onSurface
    ) {
        MaterialTheme(
            colorScheme = darkColorScheme,
            shapes = MaterialTheme.shapes,
            typography = Typography,
            content = content
        )
    }
}
