package com.alex3645.feature_account.usecase

import android.net.Uri
import android.widget.ImageView
import com.alex3645.feature_account.data.model.AccResponse
import com.alex3645.feature_account.domain.repository.AccountRepository
import com.squareup.picasso.Picasso
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.lang.Exception
import java.net.URI
import javax.inject.Inject

class UploadPictureToServer @Inject constructor(
    private val repository: AccountRepository
){
    interface Result {
        data class Success(val response: AccResponse) : Result
        data class Error(val e: Exception) : Result
    }

    suspend operator fun invoke(token: String,imageUri: Uri): Result {
        return try{
            var file = File(imageUri.path?:"")
            var response = repository.uploadImage(token,
                MultipartBody.Part.createFormData("file", file.name, RequestBody.create(
                    MediaType.parse("image/*"), file)))
            if(response.success){
                Result.Success(response)
            }else{
                Result.Error(Exception(response.message))
            }
        }catch (e: Exception){
            throw e
            Result.Error(e)
        }
    }
}