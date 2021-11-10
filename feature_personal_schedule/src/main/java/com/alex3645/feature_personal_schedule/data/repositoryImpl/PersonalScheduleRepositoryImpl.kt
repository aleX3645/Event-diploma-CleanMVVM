package com.alex3645.feature_personal_schedule.data.repositoryImpl

import com.alex3645.feature_personal_schedule.domain.repository.PersonalScheduleRepository
import com.alex3645.feature_personal_schedule.data.network.service.ApiRetrofitPersonalScheduleService
import com.alex3645.feature_personal_schedule.domain.model.Event
import com.alex3645.feature_personal_schedule.domain.model.User
import javax.inject.Inject

class PersonalScheduleRepositoryImpl @Inject constructor(private val service: ApiRetrofitPersonalScheduleService): PersonalScheduleRepository {
    override suspend fun loadAccountByLogin(login: String): User {
        return service.loadUserByLogin(login).toDomainModel()
    }

    override suspend fun loadPersonalSchedule(id: Long): List<Event> {
        return service.getPersonalSchedule(id).map{ it.toDomainModel() }
    }
}