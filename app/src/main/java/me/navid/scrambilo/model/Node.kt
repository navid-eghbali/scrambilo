package me.navid.scrambilo.model

class Node {
    val children = HashMap<Char, Node>()
    var isWord: Boolean = false

    var content: String? = null //Just for debugging
}