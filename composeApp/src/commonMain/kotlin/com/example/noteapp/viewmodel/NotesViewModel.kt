package com.example.noteapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.model.Note
import com.example.noteapp.data.repository.NoteRepository
import com.example.noteapp.data.SettingsManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.ExperimentalCoroutinesApi

enum class SortOrder {
    NEWEST, OLDEST
}

class NotesViewModel(
    private val repository: NoteRepository,
    private val settingsManager: SettingsManager
) : ViewModel() {

    private val _searchQuery = MutableStateFlow<String>("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _sortOrder = MutableStateFlow(SortOrder.NEWEST)
    val sortOrder: StateFlow<SortOrder> = _sortOrder.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val notes: StateFlow<List<Note>> = combine(_searchQuery, _sortOrder) { query, order ->
        query to order
    }.flatMapLatest { (query, order) ->
        val baseFlow = if (query.isEmpty()) repository.getAllNotes()
        else repository.searchNotes(query)
        
        baseFlow.map { list ->
            when (order) {
                SortOrder.NEWEST -> list.sortedByDescending { it.id } // Assuming higher ID is newer
                SortOrder.OLDEST -> list.sortedBy { it.id }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _isDarkMode = MutableStateFlow<Boolean>(settingsManager.isDarkMode)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun setSortOrder(order: SortOrder) {
        _sortOrder.value = order
    }

    fun toggleTheme(enabled: Boolean) {
        viewModelScope.launch {
            settingsManager.isDarkMode = enabled
            _isDarkMode.value = enabled
        }
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch { repository.insertNote(title, content) }
    }

    fun updateNote(id: Long, title: String, content: String) {
        viewModelScope.launch { repository.updateNote(id, title, content) }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch { repository.deleteNote(id) }
    }

    fun toggleFavorite(note: Note) {
        viewModelScope.launch { repository.toggleFavorite(note.id, note.isFavorite) }
    }
}