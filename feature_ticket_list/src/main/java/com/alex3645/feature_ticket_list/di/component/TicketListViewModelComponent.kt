package com.alex3645.feature_ticket_list.di.component

import com.alex3645.feature_ticket_list.di.module.TicketListViewModelModule
import com.alex3645.feature_ticket_list.presentation.ticketListView.TicketListViewModel
import dagger.Component

@Component(modules = [TicketListViewModelModule::class])
interface TicketListViewModelComponent {
    @Component.Factory
    interface Factory {
        fun create(): TicketListViewModelComponent
    }

    fun inject(ticketListViewModel: TicketListViewModel)
}