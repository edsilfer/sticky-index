package br.com.stickyindex.model

/**
 * A POJO that stores significant information for letter styling in sticky-index list
 */
data class RowStyle(
        val height: Float,
        val width: Float,
        val color: Int,
        val size: Float,
        val style: Int
)