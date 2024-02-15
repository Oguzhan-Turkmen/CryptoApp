package com.example.cryptoapp.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

class CustomTypographyRegular(
    val caption1: TextStyle,
    val caption2: TextStyle,
    val footnote: TextStyle,
    val subheadline: TextStyle,
    val callout: TextStyle,
    val body: TextStyle,
    val headline: TextStyle,
    val title1: TextStyle,
    val title2: TextStyle,
    val title3: TextStyle,
    val largeTitle: TextStyle,
)

class CustomTypographyBold(
    val caption1: TextStyle,
    val caption2: TextStyle,
    val footnote: TextStyle,
    val subheadline: TextStyle,
    val callout: TextStyle,
    val body: TextStyle,
    val headline: TextStyle,
    val title1: TextStyle,
    val title2: TextStyle,
    val title3: TextStyle,
    val largeTitle: TextStyle,
)

val MaterialTheme.customTypogrphyRegular: CustomTypographyRegular by lazy {
    CustomTypographyRegular(
        caption1 = TextStyle(
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight(400),
            color = AppColors.Black,
            letterSpacing = 0.07.sp,
        ),
        caption2 = TextStyle(
            fontSize = 11.sp,
            lineHeight = 13.sp,
            fontWeight = FontWeight(400),
            color = AppColors.Black,
        ),
        footnote = TextStyle(
            fontSize = 11.sp,
            lineHeight = 18.sp,
            fontWeight = FontWeight(400),
            color = AppColors.Black,
        ),
        subheadline = TextStyle(
            fontSize = 15.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight(400),
            color = AppColors.Black,
        ),
        callout = TextStyle(
            fontSize = 17.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight(400),
            color = AppColors.Black,
        ),
        body = TextStyle(
            fontSize = 17.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight(400),
            color = AppColors.Black,
        ),
        headline = TextStyle(
            fontSize = 17.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight(600),
            color = AppColors.Black,
        ),
        title1 = TextStyle(
            fontSize = 28.sp,
            lineHeight = 34.sp,
            fontWeight = FontWeight(400),
            color = AppColors.Black,
            letterSpacing = 0.36.sp,
        ),
        title2 = TextStyle(
            fontSize = 22.sp,
            lineHeight = 28.sp,
            fontWeight = FontWeight(400),
            color = AppColors.Black,
            letterSpacing = 0.35.sp,
        ),
        title3 = TextStyle(
            fontSize = 20.sp,
            lineHeight = 25.sp,
            fontWeight = FontWeight(400),
            color = AppColors.Black,
            letterSpacing = 0.38.sp,
        ),
        largeTitle = TextStyle(
            fontSize = 34.sp,
            lineHeight = 41.sp,
            fontWeight = FontWeight(400),
            color = AppColors.Black,
            letterSpacing = 0.37.sp,
        )
    )
}
val MaterialTheme.customTypographyBold: CustomTypographyBold by lazy {
    CustomTypographyBold(
        caption1 = TextStyle(
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight(500),
            color = AppColors.Black,
            letterSpacing = 0.07.sp,
        ),
        caption2 = TextStyle(
            fontSize = 11.sp,
            lineHeight = 13.sp,
            fontWeight = FontWeight(600),
            color = AppColors.Black,
        ),
        footnote = TextStyle(
            fontSize = 13.sp,
            lineHeight = 18.sp,
            fontWeight = FontWeight(600),
            color = AppColors.Black,
        ),
        subheadline = TextStyle(
            fontSize = 15.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight(600),
            color = AppColors.Black,
        ),
        callout = TextStyle(
            fontSize = 16.sp,
            lineHeight = 21.sp,
            fontWeight = FontWeight(600),
            color = AppColors.Black,
        ),
        body = TextStyle(
            fontSize = 17.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight(600),
            color = AppColors.Black,
        ),
        headline = TextStyle(
            fontSize = 17.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight(600),
            color = AppColors.Black,
        ),
        title1 = TextStyle(
            fontSize = 28.sp,
            lineHeight = 34.sp,
            fontWeight = FontWeight(700),
            color = AppColors.Black,
            letterSpacing = 0.36.sp,
        ),
        title2 = TextStyle(
            fontSize = 22.sp,
            lineHeight = 28.sp,
            fontWeight = FontWeight(700),
            color = AppColors.Black,
            letterSpacing = 0.35.sp,
        ),
        title3 = TextStyle(
            fontSize = 20.sp,
            lineHeight = 25.sp,
            fontWeight = FontWeight(600),
            color = AppColors.Black,
            letterSpacing = 0.38.sp,
        ),
        largeTitle = TextStyle(
            fontSize = 34.sp,
            lineHeight = 41.sp,
            fontWeight = FontWeight(700),
            color = AppColors.Black,
            letterSpacing = 0.37.sp,
        )
    )
}