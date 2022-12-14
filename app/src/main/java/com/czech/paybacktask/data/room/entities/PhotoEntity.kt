package com.czech.paybacktask.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey
    val id: Int?,
    val type: String?,
    val tags: String?,
    val previewURL: String?,
    val largeImageURL: String?,
    val views: Int?,
    val downloads: Int?,
    val likes: Int?,
    val comments: Int?,
    val userId: Int?,
    val user: String?,
    val userImageURL: String?
)