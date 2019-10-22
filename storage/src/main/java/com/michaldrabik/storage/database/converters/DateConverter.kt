package com.michaldrabik.storage.database.converters

import androidx.room.TypeConverter
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

class DateConverter {

  @TypeConverter
  fun stringToDate(value: Long?) =
    value?.let { ZonedDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.of("UTC")) }

  @TypeConverter
  fun dateToString(date: ZonedDateTime?) =
    date?.toInstant()?.toEpochMilli()
}