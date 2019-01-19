package me.navid.scrambilo.model

enum class Difficulty(val range: IntRange?) {
    EASY(null),
    MEDIUM(3..5),
    HARD(8..10)
}