package com.kuymakov.mylibrary.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.kuymakov.mylibrary.base.recyclerview.Item
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class MyBook constructor(
    var title: String,
    val uri: String,

    @PrimaryKey(autoGenerate = true)
    val booksId: Long = 0
) : Item<Long>, Parcelable {

    override val id: Long get() = booksId

    @IgnoredOnParcel
    @Ignore
    var isSelected: Boolean = false

    fun fullCopy() = copy().also { book ->
        book.isSelected = isSelected
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MyBook

        if (title != other.title) return false
        if (uri != other.uri) return false
        if (isSelected != other.isSelected) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + uri.hashCode()
        result = 31 * result + isSelected.hashCode()
        return result
    }


}