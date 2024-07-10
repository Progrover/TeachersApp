package dev.platovco.mentorium.core.uicommon.view.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import dev.platovco.mentorium.core.theme.AppTheme
import dev.platovco.mentorium.core.uicommon.R

@Composable
fun AppToolbar(
    modifier: Modifier,
    title: String,
    backIconResId: Int = R.drawable.ic_arrow_back,
    endIconResId: Int = R.drawable.ic_settings,
    backgroundColor: Color = AppTheme.colors.background,
    iconTint: Color = AppTheme.colors.primary,
    onBackClick: () -> Unit,
    onEndIconClick: (() -> Unit)? = null,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(AppTheme.sizes.size60)
            .background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        IconButton(
            onClick = onBackClick
        ) {
            Icon(
                modifier = Modifier
                    .size(AppTheme.sizes.size40),
                imageVector = ImageVector.vectorResource(backIconResId),
                contentDescription = null,
                tint = iconTint,
            )
        }

        Text(
            text = title,
            style = AppTheme.typography.h3,
            color = iconTint,
        )

        onEndIconClick?.let { onClick ->
            IconButton(
                onClick = onClick
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(endIconResId),
                    contentDescription = null,
                    tint = iconTint,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, locale = "ru")
private fun AppToolbarPreview() =
    AppToolbar(
        modifier = Modifier,
        title = "Page One",
        onBackClick = {},
    )