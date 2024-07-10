package dev.platovco.mentorium.core.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp


/**
 * @property h1 55sp, Bold
 * @property h2 24sp, Bold
 * @property h3 18sp, Bold
 * @property h4 16sp, Bold
 * @property body1 16sp, SemiBold
 * @property body2 14sp, Bold
 * @property body3 12sp, SemiBold
 * @property footnote 12sp, Bold
 * @property caption 11sp, Bold
 * @property title 18sp, Bold
 * @property number 10sp, Bold
 */
@Immutable
data class AppTypography(
    val materialTypography: Typography,
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val h4: TextStyle,
    val body1: TextStyle,
    val body2: TextStyle,
    val body3: TextStyle,
    val body4: TextStyle,
    val footnote: TextStyle,
    val footnote1: TextStyle,
    val number: TextStyle,
    val caption: TextStyle,
    val caption1: TextStyle,
    val title: TextStyle,
)



internal fun appTypography(style: TextStyles): AppTypography =
    with(style) {
        AppTypography(
            materialTypography = Typography(),
            h1 = h1,
            h2 = h2,
            h3 = h3,
            h4 = h4,
            body1 = body1,
            body2 = body2,
            body3 = body3,
            body4 = body4,
            footnote = footnote,
            footnote1 = footnote1,
            number = number,
            caption = caption,
            caption1 = caption1,
            title = title,
        )
    }




class TextStyles {

    val h1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 55.sp,
        letterSpacing = 0.em,
    )

    val h2 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        letterSpacing = 0.em,
    )

    val h3 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        letterSpacing = 0.em,
    )

    val h4 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        letterSpacing = 0.em,
    )

    val body1 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        letterSpacing = 0.em,
    )

    val body2 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        letterSpacing = 0.em,
    )

    val body3 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        letterSpacing = 0.em,
    )

    val body4 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.em,
    )

    val footnote = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        letterSpacing = 0.em,
    )

    val footnote1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        letterSpacing = 0.em,
    )

    val number = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        letterSpacing = 0.em,
    )

    val caption = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        letterSpacing = 0.em,
    )

    val caption1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        letterSpacing = 0.em,
    )

    val title = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        letterSpacing = (-0.03).em,
    )
}


internal val LocalAppTypography = staticCompositionLocalOf {
    appTypography(TextStyles())
}