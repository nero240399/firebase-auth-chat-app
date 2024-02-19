package com.neronguyen.psychicmemory.app.ui

import androidx.compose.runtime.Composable
import com.neronguyen.psychicmemory.feature.auth.AuthScreen
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import org.koin.compose.koinInject

@Composable
fun PsychicMemoryApp(circuit: Circuit = koinInject()) {

    val backStack = rememberSaveableBackStack(root = AuthScreen)
    val navigator = rememberCircuitNavigator(backStack)

    CircuitCompositionLocals(circuit) {
        NavigableCircuitContent(navigator = navigator, backStack = backStack)
    }
}
