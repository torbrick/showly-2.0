package com.michaldrabik.ui_model

import com.michaldrabik.common.Config
import com.michaldrabik.common.Config.AWS_IMAGE_BASE_URL
import com.michaldrabik.ui_model.ImageSource.AWS
import com.michaldrabik.ui_model.ImageSource.TMDB
import com.michaldrabik.ui_model.ImageStatus.UNAVAILABLE
import com.michaldrabik.ui_model.ImageStatus.UNKNOWN
import com.michaldrabik.ui_model.ImageType.FANART
import com.michaldrabik.ui_model.ImageType.FANART_WIDE
import com.michaldrabik.ui_model.ImageType.POSTER

data class MovieImage(
  val id: Long,
  val idTmdb: IdTmdb,
  val type: ImageType,
  val fileUrl: String,
  val status: ImageStatus,
  val source: ImageSource
) {

  val fullFileUrl = when (source) {
    TMDB -> when (type) {
      POSTER -> "${Config.TMDB_IMAGE_BASE_POSTER_URL}$fileUrl"
      FANART, FANART_WIDE -> "${Config.TMDB_IMAGE_BASE_FANART_URL}$fileUrl"
    }
    AWS -> "$AWS_IMAGE_BASE_URL$fileUrl"
    else -> ""
  }

  companion object {
    fun createUnknown(type: ImageType) =
      MovieImage(0, IdTmdb(0), type, "", UNKNOWN, TMDB)

    fun createUnavailable(type: ImageType) =
      MovieImage(0, IdTmdb(0), type, "", UNAVAILABLE, TMDB)
  }
}
