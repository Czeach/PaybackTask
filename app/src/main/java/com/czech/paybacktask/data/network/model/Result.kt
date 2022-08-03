package com.czech.paybacktask.data.network.model

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("hits")
    val hits: List<Hit>?
) {
    data class Hit(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("type")
        val type: String?,
        @SerializedName("tags")
        val tags: String?,
        @SerializedName("previewURL")
        val previewURL: String?,
        @SerializedName("largeImageURL")
        val largeImageURL: String?,
        @SerializedName("views")
        val views: Int?,
        @SerializedName("downloads")
        val downloads: Int?,
        @SerializedName("likes")
        val likes: Int?,
        @SerializedName("comments")
        val comments: Int?,
        @SerializedName("user_id")
        val userId: Int?,
        @SerializedName("user")
        val user: String?,
        @SerializedName("userImageURL")
        val userImageURL: String?
    )
}