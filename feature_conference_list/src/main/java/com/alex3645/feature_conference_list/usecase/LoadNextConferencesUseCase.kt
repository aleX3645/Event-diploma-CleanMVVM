package com.alex3645.feature_conference_list.usecase

import android.util.Log
import com.alex3645.base.data.Resource
import com.alex3645.feature_conference_list.data.model.ConferenceJson
import com.alex3645.feature_conference_list.domain.model.Conference
import com.alex3645.feature_conference_list.domain.repository.ConferenceRepository
import java.lang.Exception
import javax.inject.Inject

class LoadNextConferencesUseCase @Inject constructor(private val conferenceRepository: ConferenceRepository) {
    interface Result {
        data class Success(val data: List<Conference>) : Result
        data class Error(val e: Throwable) : Result
    }

    var confList: MutableList<Conference> = mutableListOf()

    private var pageNumber = 0;
    private val pageSize = 5;

    private var lastSize = 0;
    suspend operator fun invoke() : Result{

        return try{
            Result.Success(conferenceRepository.getConferences(pageNumber,pageSize))
        }catch (e: Exception){
            Log.d("UseCaseError", e.message!!)
            throw Exception(e.message)
            Result.Error(e)
        }
    }


}