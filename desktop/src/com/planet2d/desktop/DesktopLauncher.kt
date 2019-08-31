package com.planet2d.desktop

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.planet2d.Config
import com.planet2d.editor.Editor
import com.planet2d.game.Game

object DesktopLauncher {

    @JvmStatic
    fun main(arg: Array<String>) {

        val config = Config()
        if (config.appType == Config.AppType.EDITOR) {
            this.initEditor()
        } else {
            this.initGame()
        }
    }

    private fun initEditor() {

        val lwjglConfig = LwjglApplicationConfiguration()
        lwjglConfig.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
        lwjglConfig.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
        LwjglApplication(Editor(), lwjglConfig)
    }

    private fun initGame() {

        val lwjglConfig = LwjglApplicationConfiguration()
        lwjglConfig.fullscreen = true

        LwjglApplication(Game(), lwjglConfig)
    }
}
