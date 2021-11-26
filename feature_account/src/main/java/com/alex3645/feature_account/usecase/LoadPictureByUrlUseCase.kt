package com.alex3645.feature_account.usecase

import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.lang.Exception

class LoadPictureByUrlUseCase {
    interface Result {
        object Success : Result
        data class Error(val e: Exception) : Result
    }

    suspend operator fun invoke(imageUri: String, view: ImageView): Result {
        return try{
            Picasso.get().load(imageUri).fit().centerCrop().into(view)
            Result.Success
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}