package com.michaldrabik.ui_base.common.sheets.context_menu.movie.cases

import com.michaldrabik.repository.PinnedItemsRepository
import com.michaldrabik.repository.SettingsRepository
import com.michaldrabik.repository.TranslationsRepository
import com.michaldrabik.repository.movies.MoviesRepository
import com.michaldrabik.ui_base.common.sheets.context_menu.movie.helpers.MovieContextItem
import com.michaldrabik.ui_base.dates.DateFormatProvider
import com.michaldrabik.ui_base.images.MovieImagesProvider
import com.michaldrabik.ui_model.IdTrakt
import com.michaldrabik.ui_model.ImageType
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

@ViewModelScoped
class MovieContextMenuLoadItemCase @Inject constructor(
  private val moviesRepository: MoviesRepository,
  private val pinnedItemsRepository: PinnedItemsRepository,
  private val imagesProvider: MovieImagesProvider,
  private val translationsRepository: TranslationsRepository,
  private val settingsRepository: SettingsRepository,
  private val dateFormatProvider: DateFormatProvider,
) {

  private val language by lazy { settingsRepository.language }

  suspend fun loadItem(traktId: IdTrakt) = coroutineScope {
    val movie = moviesRepository.movieDetails.load(traktId)
    val dateFormat = dateFormatProvider.loadShortDayFormat()

    val imageAsync = async { imagesProvider.findCachedImage(movie, ImageType.POSTER) }
    val translationAsync = async { translationsRepository.loadTranslation(movie, language = language, onlyLocal = true) }

    val isMyMovieAsync = async { moviesRepository.myMovies.exists(traktId) }
    val isWatchlistAsync = async { moviesRepository.watchlistMovies.exists(traktId) }
    val isHiddenAsync = async { moviesRepository.hiddenMovies.exists(traktId) }

    val isPinnedAsync = async { pinnedItemsRepository.isItemPinned(movie) }

    MovieContextItem(
      movie = movie,
      image = imageAsync.await(),
      translation = translationAsync.await(),
      isMyMovie = isMyMovieAsync.await(),
      isWatchlist = isWatchlistAsync.await(),
      isHidden = isHiddenAsync.await(),
      isPinnedTop = isPinnedAsync.await(),
      dateFormat = dateFormat
    )
  }
}