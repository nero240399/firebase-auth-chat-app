package com.neronguyen.psychicmemory.feature.chatroom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.neronguyen.psychicmemory.core.model.User
import com.neronguyen.psychicmemory.feature.chatroom.ChatRoomScreen.Event.ConnectSocket
import com.neronguyen.psychicmemory.feature.chatroom.ChatRoomScreen.Event.InputMessage
import com.neronguyen.psychicmemory.feature.chatroom.ChatRoomScreen.Event.SignOut
import com.neronguyen.psychicmemory.feature.chatroom.component.ChattieTopAppBar
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data object ChatRoomScreen : Screen {

    data class State(
        val currentUser: User,
        val messages: List<String>,
        val inputMessage: String,
        val eventSink: (Event) -> Unit
    ) : CircuitUiState

    sealed class Event {
        data object ConnectSocket : Event()
        data class InputMessage(val value: String) : Event()
        data class SendMessage(val message: String) : Event()
        data object SignOut : Event()
    }
}

@Composable
fun ChatRoomUi(state: ChatRoomScreen.State, modifier: Modifier = Modifier) {
    LaunchedEffect(true) {
        state.eventSink(ConnectSocket)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            ChattieTopAppBar(state.currentUser) {
                state.eventSink(SignOut)
            }
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            LazyColumn(Modifier.weight(1f), reverseLayout = true) {
                items(state.messages) { text ->
                    Text(text = text)
                }
            }
            OutlinedTextField(
                value = state.inputMessage,
                onValueChange = { value ->
                    state.eventSink(InputMessage(value))
                },
                trailingIcon = {
                    IconButton(onClick = { state.eventSink(ChatRoomScreen.Event.SendMessage(state.inputMessage)) }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "")
                    }
                }
            )
        }
    }
}
