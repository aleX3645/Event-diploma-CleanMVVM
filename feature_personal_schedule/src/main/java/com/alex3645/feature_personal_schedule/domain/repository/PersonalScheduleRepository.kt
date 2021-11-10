package com.alex3645.feature_personal_schedule.domain.repository

import com.alex3645.feature_personal_schedule.domain.model.Event
import com.alex3645.feature_personal_schedule.domain.model.User

interface PersonalScheduleRepository {
    suspend fun loadAccountByLogin(login: String) : User
    suspend fun loadPersonalSchedule(id: Long) : List<Event>
}