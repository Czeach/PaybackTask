package com.czech.paybacktask.utils

import com.czech.paybacktask.data.network.model.Result
import com.czech.paybacktask.data.room.entities.PhotoEntity

fun PhotoEntity.toHit(): Result.Hit {
    return Result.Hit(
        id,
        type,
        tags,
        previewURL,
        largeImageURL,
        views,
        downloads,
        likes,
        comments,
        userId,
        user,
        userImageURL
    )
}

fun List<PhotoEntity>.toHitList(): List<Result.Hit> {
    return map { it.toHit() }
}

fun Result.Hit.toEntity(): PhotoEntity {
    return PhotoEntity(
        id,
        type,
        tags,
        previewURL,
        largeImageURL,
        views,
        downloads,
        likes,
        comments,
        userId,
        user,
        userImageURL
    )
}

fun List<Result.Hit>.toEntityList(): List<PhotoEntity> {
    return map { it.toEntity() }
}