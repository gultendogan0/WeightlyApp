package com.gultendogan.weightlyapp.ui.list

import androidx.lifecycle.ViewModel
import com.gultendogan.weightlyapp.ui.splash.SplashViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class ListViewModel @Inject constructor() : ViewModel() {

    sealed class Event{
        object NavigateToHome : Event()
        object NavigateToOnBoardingScreen : ListViewModel.Event()
    }

    private val eventChannel = Channel<SplashViewModel.Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()
}