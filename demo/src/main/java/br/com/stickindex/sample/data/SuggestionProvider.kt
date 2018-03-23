package br.com.stickindex.sample.data

import android.content.SearchRecentSuggestionsProvider

class SuggestionProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(SuggestionProvider::class.java.simpleName!!, DATABASE_MODE_QUERIES)
    }
}