package com.alex3645.feature_ticket_list.di.component

import com.alex3645.feature_ticket_list.di.module.TicketListFragmentModule
import com.alex3645.feature_ticket_list.presentation.ticketListView.TicketListFragment
import dagger.Component

@Component(modules = [TicketListFragmentModule::class])
interface TicketListFragmentComponent {
    @Component.Factory
    interface Factory {
        fun create(): TicketListFragmentComponent
    }

    fun inject(ticketListFragment: TicketListFragment)
}