package br.com.stickyindex.model

import android.content.Context
import android.content.res.TypedArray
import android.support.v4.content.ContextCompat
import br.com.edsilfer.toolkit.core.util.InvalidData
import br.com.stickyindex.R

/**
 * Encapsulates the logic to map a {@link TypedArray} into a {@link RowStyle} object
 */
object RowStyleMapper {

    private const val DEFAULT_TEXT_SIZE = 26f

    /**
     * Maps the given {@link TypedArray} into a {@link RowStyle} POJO
     */
    fun map(context: Context, typedArray: TypedArray?): RowStyle? {
        if (typedArray == null) return null
        val style = RowStyle(
                typedArray.getDimension(R.styleable.StickyIndex_rowHeight, InvalidData.UNINITIALIZED.getFloat()),
                typedArray.getDimension(R.styleable.StickyIndex_stickyWidth, InvalidData.UNINITIALIZED.getFloat()),
                typedArray.getColor(R.styleable.StickyIndex_android_textColor, ContextCompat.getColor(context, R.color.index_text_color)),
                typedArray.getDimension(R.styleable.StickyIndex_android_textSize, DEFAULT_TEXT_SIZE),
                typedArray.getInt(R.styleable.StickyIndex_android_textStyle, InvalidData.UNINITIALIZED.getInt())
        )
        typedArray.recycle()
        return style
    }
}
