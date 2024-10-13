package com.ru.hazakura.util

sealed class AccessCodeState {
    object Access : AccessCodeState()
    object Empty : AccessCodeState()
    object Checking : AccessCodeState()
}