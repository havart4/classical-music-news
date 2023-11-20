package com.vandele.classicalmusicnews.ui.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vandele.classicalmusicnews.R
import com.vandele.classicalmusicnews.model.CmnSettings
import com.vandele.classicalmusicnews.model.DarkModePreference
import com.vandele.classicalmusicnews.model.RssSource
import com.vandele.classicalmusicnews.ui.theme.CmnSpacing

@Composable
fun SettingsScreen(contentPadding: PaddingValues) {
    val viewModel = hiltViewModel<SettingsViewModel>()
    val settings = viewModel.settings.collectAsState(initial = CmnSettings()).value
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(horizontal = CmnSpacing.m),
        contentPadding = contentPadding,
    ) {
        item { Spacer(Modifier.height(CmnSpacing.m)) }
        item {
            DarkModePreferenceSection(settings, viewModel::setDarkModePreference)
        }
        item { Spacer(Modifier.height(CmnSpacing.m)) }
        item {
            SourcePreferenceSection(settings, viewModel::toggleRssSource)
        }
        item { Spacer(Modifier.height(CmnSpacing.m)) }
    }
}

@Composable
private fun DarkModePreferenceSection(
    settings: CmnSettings,
    setDarkModePreference: (DarkModePreference) -> Unit,
) {
    Column {
        SectionHeader(stringResource(R.string.dark_mode))
        Column(Modifier.selectableGroup()) {
            SettingsDialogThemeChooserRow(
                text = stringResource(R.string.system_default),
                selected = settings.darkModePreference == DarkModePreference.SYSTEM_DEFAULT,
                onClick = { setDarkModePreference(DarkModePreference.SYSTEM_DEFAULT) },
            )
            SettingsDialogThemeChooserRow(
                text = stringResource(R.string.light),
                selected = settings.darkModePreference == DarkModePreference.LIGHT,
                onClick = { setDarkModePreference(DarkModePreference.LIGHT) },
            )
            SettingsDialogThemeChooserRow(
                text = stringResource(R.string.dark),
                selected = settings.darkModePreference == DarkModePreference.DARK,
                onClick = { setDarkModePreference(DarkModePreference.DARK) },
            )
        }
    }
}

@Composable
private fun SourcePreferenceSection(
    settings: CmnSettings,
    toggleRssSource: (RssSource) -> Unit,
) {
    Column {
        SectionHeader(stringResource(R.string.news_sources))
        RssSource.values().forEach { rssSource ->
            val checked = rssSource !in settings.disabledRssSources
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = checked,
                        role = Role.Checkbox,
                        onClick = { toggleRssSource(rssSource) },
                    )
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = null,
                )
                Spacer(Modifier.width(8.dp))
                Text(stringResource(rssSource.nameRes))
            }
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
    )
}

@Composable
fun SettingsDialogThemeChooserRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
        )
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}
