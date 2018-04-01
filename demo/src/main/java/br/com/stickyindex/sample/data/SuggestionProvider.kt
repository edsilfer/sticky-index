package br.com.stickyindex.sample.data

import android.content.SearchRecentSuggestionsProvider

/**
 * Receive suggestions for queries in the database
 */
class SuggestionProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(SuggestionProvider::class.java.simpleName!!, DATABASE_MODE_QUERIES)
    }
}