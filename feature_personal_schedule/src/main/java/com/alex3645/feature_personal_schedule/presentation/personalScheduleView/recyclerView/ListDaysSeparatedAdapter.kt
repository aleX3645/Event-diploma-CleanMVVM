package com.alex3645.feature_personal_schedule.presentation.personalScheduleView.recyclerView

import android.util.Log
import com.alex3645.feature_personal_schedule.domain.model.Event
import java.text.SimpleDateFormat
import java.util.*

class ListDaysSeparatedAdapter {
    constructor(events: List<Event>){
        separatedList = transformEventList(events.map{it.toEventCalendar()})
    }

    private var separatedList: MutableList<EventSeparatedListItem>
    private val dateFormatter: SimpleDateFormat = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault())

    private fun transformEventList(events: List<EventCalendar>) : MutableList<EventSeparatedListItem>{
        var result: MutableList<EventSeparatedListItem> = mutableListOf()
        var sortedList = events.sortedWith(comparator)

        var lastDate: Calendar = Calendar.getInstance(Locale.getDefault())
        if(events.isNotEmpty()){
            lastDate = events[0].dateStart
            result.add(EventSeparatedListItem(titleCode,null, dateFormatter.format(lastDate.time)))
        }

        var i = 0
        while(i<events.size){
            if(events[i].dateStart.get(Calendar.DAY_OF_YEAR)==lastDate.get(Calendar.DAY_OF_YEAR) &&
                events[i].dateStart.get(Calendar.YEAR)==lastDate.get(Calendar.YEAR)){
                result.add(EventSeparatedListItem(eventCode,events[i].toEvent()))
                i++
            }else{
                lastDate = events[i].dateStart
                result.add(EventSeparatedListItem(titleCode,null, dateFormatter.format(lastDate.time)))
            }
        }

        return result
    }

    fun getByPosition(position: Int) : EventSeparatedListItem{
        return separatedList[position]
    }

    fun getCount() : Int{
        return separatedList.size
    }

    fun getList() : List<EventSeparatedListItem>{
        return separatedList
    }

    private val comparator =  Comparator<EventCalendar> { a, b ->
        when {
            (a.dateStart.time>b.dateStart.time)->1
            (a.dateStart.time<b.dateStart.time)->-1
            else -> 0
        }
    }

    companion object{
        val titleCode = 0
        val eventCode = 1
    }
}