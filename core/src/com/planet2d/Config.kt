package com.planet2d

class Config {

    enum class AppType {
        EDITOR,
        GAME
    }

    var appType:AppType

    init {
        this.appType = AppType.EDITOR
    }
}