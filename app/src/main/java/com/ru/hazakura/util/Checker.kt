package com.ru.hazakura.util

sealed class Checker {
    object Checking: Checker()
    object Ok: Checker()
    object Not: Checker()
}