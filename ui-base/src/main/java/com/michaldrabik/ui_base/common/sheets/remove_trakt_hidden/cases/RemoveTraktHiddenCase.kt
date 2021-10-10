package com.michaldrabik.ui_base.common.sheets.remove_trakt_hidden.cases

import com.michaldrabik.common.Mode
import com.michaldrabik.data_remote.Cloud
import com.michaldrabik.data_remote.trakt.model.SyncExportItem
import com.michaldrabik.data_remote.trakt.model.SyncExportRequest
import com.michaldrabik.repository.UserTraktManager
import com.michaldrabik.ui_model.IdTrakt
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class RemoveTraktHiddenCase @Inject constructor(
  private val cloud: Cloud,
  private val userManager: UserTraktManager,
) {

  suspend fun removeTraktHidden(traktId: IdTrakt, mode: Mode) {
    val token = userManager.checkAuthorization()
    val item = SyncExportItem.create(traktId.id)

    val request = when (mode) {
      Mode.SHOWS -> SyncExportRequest(shows = listOf(item))
      Mode.MOVIES -> SyncExportRequest(movies = listOf(item))
    }

    cloud.traktApi.postDeleteHidden(token.token, request)
  }
}
