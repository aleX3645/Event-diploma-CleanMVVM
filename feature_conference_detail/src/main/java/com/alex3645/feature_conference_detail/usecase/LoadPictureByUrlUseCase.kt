package com.alex3645.feature_conference_detail.usecase

import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.alex3645.feature_conference_detail.domain.model.User
import com.squareup.picasso.Picasso
import java.lang.Exception

class LoadPictureByUrlUseCase {
    interface Result {
        object Success : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(imageUri: String, view: ImageView): Result {
        return try{
            Log.d("!!!","loadPic")
            Picasso.get().load(imageUri).fit().centerCrop().into(view)
            Result.Success
        }catch (e: Exception){
            throw e
            Result.Error(e)
        }
    }
}