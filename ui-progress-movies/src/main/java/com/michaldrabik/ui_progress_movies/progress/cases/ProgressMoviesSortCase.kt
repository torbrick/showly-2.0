package com.michaldrabik.ui_progress_movies.progress.cases

import com.michaldrabik.repository.SettingsRepository
import com.michaldrabik.ui_model.SortOrder
import com.michaldrabik.ui_model.SortType
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ProgressMoviesSortCase @Inject constructor(
  private val settingsRepository: SettingsRepository,
) {

  fun setSortOrder(sortOrder: SortOrder, sortType: SortType) {
    settingsRepository.sortSettings.progressMoviesSortOrder = sortOrder
    settingsRepository.sortSettings.progressMoviesSortType = sortType
  }

  fun loadSortOrder() = Pair(
    settingsRepository.sortSettings.progressMoviesSortOrder,
    settingsRepository.sortSettings.progressMoviesSortType
  )
}
