package com.michaldrabik.ui_my_shows.hidden.cases

import com.michaldrabik.common.dispatchers.CoroutineDispatchers
import com.michaldrabik.repository.RatingsRepository
import com.michaldrabik.repository.UserTraktManager
import com.michaldrabik.ui_model.IdTrakt
import com.michaldrabik.ui_model.TraktRating
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class HiddenRatingsCase @Inject constructor(
  private val dispatchers: CoroutineDispatchers,
  private val ratingsRepository: RatingsRepository,
  private val userTraktManager: UserTraktManager
) {

  suspend fun loadRatings(): Map<IdTrakt, TraktRating?> =
    withContext(dispatchers.IO) {
      if (!userTraktManager.isAuthorized()) {
        return@withContext emptyMap()
      }
      ratingsRepository.shows.loadShowsRatings().associateBy { it.idTrakt }
    }
}
