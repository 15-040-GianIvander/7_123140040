package comexamplenoteapp.database

import kotlin.Long
import kotlin.String

public data class NoteEntity(
  public val id: Long,
  public val title: String,
  public val content: String,
  public val is_favorite: Long,
  public val created_at: Long,
)
