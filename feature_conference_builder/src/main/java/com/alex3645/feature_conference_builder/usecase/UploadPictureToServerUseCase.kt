package com.alex3645.feature_conference_builder.usecase

import com.alex3645.feature_conference_builder.data.model.Response
import com.alex3645.feature_conference_builder.domain.repository.ConferenceBuilderRepository
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.InputStream
import java.lang.Exception
import javax.inject.Inject

class UploadPictureToServerUseCase @Inject constructor(
    private val repository: ConferenceBuilderRepository
){
    interface Result {
        data class Success(val response: Response) : Result
        data class Error(val e: Exception) : Result
    }

    suspend operator fun invoke(token: String,stream: InputStream): Result {
        return try{
            var response = repository.uploadImage(token,
                MultipartBody.Part.createFormData("file", stream.hashCode().toString(), RequestBody.create(
                    MediaType.parse("image/*"), stream.readBytes())))
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