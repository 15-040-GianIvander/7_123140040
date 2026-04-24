package comexamplenoteapp.database

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.Long
import kotlin.String

public class NoteQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun <T : Any> selectAll(mapper: (
    id: Long,
    title: String,
    content: String,
    is_favorite: Long,
    created_at: Long,
  ) -> T): Query<T> = Query(636_680_174, arrayOf("NoteEntity"), driver, "Note.sq", "selectAll", "SELECT NoteEntity.id, NoteEntity.title, NoteEntity.content, NoteEntity.is_favorite, NoteEntity.created_at FROM NoteEntity ORDER BY created_at DESC") { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getLong(3)!!,
      cursor.getLong(4)!!
    )
  }

  public fun selectAll(): Query<NoteEntity> = selectAll(::NoteEntity)

  public fun <T : Any> search(query: String, mapper: (
    id: Long,
    title: String,
    content: String,
    is_favorite: Long,
    created_at: Long,
  ) -> T): Query<T> = SearchQuery(query) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getLong(3)!!,
      cursor.getLong(4)!!
    )
  }

  public fun search(query: String): Query<NoteEntity> = search(query, ::NoteEntity)

  /**
   * @return The number of rows updated.
   */
  public fun insert(
    title: String,
    content: String,
    is_favorite: Long,
    created_at: Long,
  ): QueryResult<Long> {
    val result = driver.execute(660_076_144, """
        |INSERT INTO NoteEntity(title, content, is_favorite, created_at)
        |VALUES (?, ?, ?, ?)
        """.trimMargin(), 4) {
          var parameterIndex = 0
          bindString(parameterIndex++, title)
          bindString(parameterIndex++, content)
          bindLong(parameterIndex++, is_favorite)
          bindLong(parameterIndex++, created_at)
        }
    notifyQueries(660_076_144) { emit ->
      emit("NoteEntity")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun updateNote(
    title: String,
    content: String,
    id: Long,
  ): QueryResult<Long> {
    val result = driver.execute(-377_335_662, """UPDATE NoteEntity SET title = ?, content = ? WHERE id = ?""", 3) {
          var parameterIndex = 0
          bindString(parameterIndex++, title)
          bindString(parameterIndex++, content)
          bindLong(parameterIndex++, id)
        }
    notifyQueries(-377_335_662) { emit ->
      emit("NoteEntity")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun deleteById(id: Long): QueryResult<Long> {
    val result = driver.execute(1_682_835_540, """DELETE FROM NoteEntity WHERE id = ?""", 1) {
          var parameterIndex = 0
          bindLong(parameterIndex++, id)
        }
    notifyQueries(1_682_835_540) { emit ->
      emit("NoteEntity")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun updateFavorite(is_favorite: Long, id: Long): QueryResult<Long> {
    val result = driver.execute(-1_469_113_796, """UPDATE NoteEntity SET is_favorite = ? WHERE id = ?""", 2) {
          var parameterIndex = 0
          bindLong(parameterIndex++, is_favorite)
          bindLong(parameterIndex++, id)
        }
    notifyQueries(-1_469_113_796) { emit ->
      emit("NoteEntity")
    }
    return result
  }

  private inner class SearchQuery<out T : Any>(
    public val query: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("NoteEntity", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("NoteEntity", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> = driver.executeQuery(937_531_743, """SELECT NoteEntity.id, NoteEntity.title, NoteEntity.content, NoteEntity.is_favorite, NoteEntity.created_at FROM NoteEntity WHERE title LIKE '%' || ? || '%' OR content LIKE '%' || ? || '%'""", mapper, 2) {
      var parameterIndex = 0
      bindString(parameterIndex++, query)
      bindString(parameterIndex++, query)
    }

    override fun toString(): String = "Note.sq:search"
  }
}
