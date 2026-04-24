package com.example.noteapp.database

import app.cash.sqldelight.Transacter
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import com.example.noteapp.database.composeApp.newInstance
import com.example.noteapp.database.composeApp.schema
import comexamplenoteapp.database.NoteQueries
import kotlin.Unit

public interface NotesDatabase : Transacter {
  public val noteQueries: NoteQueries

  public companion object {
    public val Schema: SqlSchema<QueryResult.Value<Unit>>
      get() = NotesDatabase::class.schema

    public operator fun invoke(driver: SqlDriver): NotesDatabase = NotesDatabase::class.newInstance(driver)
  }
}
