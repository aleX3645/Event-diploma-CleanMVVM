package com.alex3645.feature_conference_builder.presentation.validators

import java.util.*

class DateTimeValidator {

    fun firstDateOlder(firstDate: Calendar, secondDate: Calendar) : Boolean{
        return (firstDate.get(Calendar.YEAR) >= secondDate.get(Calendar.YEAR)
                && firstDate.get(Calendar.MONTH) >= secondDate.get(Calendar.MONTH)
                && firstDate.get(Calendar.DATE) > secondDate.get(Calendar.DATE))
    }
}