package com.alex3645.feature_conference_list.usecase

import com.alex3645.feature_conference_list.data.network.service.ApiRetrofitConferenceService
import com.alex3645.feature_conference_list.domain.model.Conference
import javax.inject.Inject

class LoadConferencesUseCase @Inject constructor(private val apiRetrofitConferenceService: ApiRetrofitConferenceService) {
    var confList: MutableList<Conference> = mutableListOf<Conference>()

    private var pageNumber = 0;
    private val pageSize = 5;

    private var lastSize = 0;
    suspend operator fun invoke(): List<Conference>{

        confList.addAll(apiRetrofitConferenceService.getAllConferences(pageNumber,pageSize)[0].conferenceJsonList.map{it.toDomainModel()})

        if(confList.size != lastSize){
            pageNumber++
            lastSize = confList.size
        }
        
        return confList;
    }
}