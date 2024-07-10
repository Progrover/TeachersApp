package dev.platovco.mentorium.feature.auth.impl.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import dev.platovco.mentorium.core.theme.AppTheme
import dev.platovco.mentorium.feature.auth.impl.R

@Composable
internal fun AppLogo(
    modifier: Modifier,
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.paddingX3),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Image(
            modifier = Modifier
                .size(AppTheme.sizes.size30),
            imageVector = ImageVector.vectorResource(R.drawable.ic_books_logo),
            contentDescription = null,
        )

        Text(
            text = stringResource(id = R.string.app_name),
            style = AppTheme.typography.h2,
            color = AppTheme.colors.onPrimary,
        )
    }
}

@Composable
@Preview(showBackground = true, locale = "ru")
private fun AppLogoPreview() =
    AppLogo(
        modifier = Modifier
            .background(AppTheme.colors.primary)
            .padding(AppTheme.paddings.paddingX2)
    )