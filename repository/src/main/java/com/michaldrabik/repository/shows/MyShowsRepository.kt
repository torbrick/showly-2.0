package com.michaldrabik.repository.shows

import androidx.room.withTransaction
import com.michaldrabik.common.extensions.nowUtcMillis
import com.michaldrabik.data_local.database.AppDatabase
import com.michaldrabik.data_local.database.model.MyShow
import com.michaldrabik.repository.mappers.Mappers
import com.michaldrabik.ui_model.IdTrakt
import javax.inject.Inject

class MyShowsRepository @Inject constructor(
  private val database: AppDatabase,
  private val mappers: Mappers,
) {

  suspend fun load(id: IdTrakt) =
    database.myShowsDao().getById(id.id)?.let {
      mappers.show.fromDatabase(it)
    }

  suspend fun loadAll() =
    database.myShowsDao().getAll()
      .map { mappers.show.fromDatabase(it) }

  suspend fun loadAll(ids: List<IdTrakt>) =
    database.myShowsDao().getAll(ids.map { it.id })
      .map { mappers.show.fromDatabase(it) }

  suspend fun loadAllRecent(amount: Int) =
    database.myShowsDao().getAllRecent(amount)
      .map { mappers.show.fromDatabase(it) }

  suspend fun loadAllIds() = database.myShowsDao().getAllTraktIds()

  suspend fun insert(id: IdTrakt) {
    val dbShow = MyShow.fromTraktId(
      traktId = id.id,
      createdAt = nowUtcMillis(),
      updatedAt = 0
    )
    with(database) {
      withTransaction {
        myShowsDao().insert(listOf(dbShow))
        watchlistShowsDao().deleteById(id.id)
        archiveShowsDao().deleteById(id.id)
      }
    }
  }

  suspend fun delete(id: IdTrakt) =
    database.myShowsDao().deleteById(id.id)

  suspend fun exists(id: IdTrakt) =
    database.myShowsDao().checkExists(id.id)
}
