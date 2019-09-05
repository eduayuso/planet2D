package com.planet2d.desktop

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.planet2d.editor.Editor
import com.planet2d.engine.config.Config
import com.planet2d.game.Game


object DesktopLauncher {

    @JvmStatic
    fun main(arg: Array<String>) {

        if (Config.appType == Config.AppType.EDITOR) {
            this.initEditor()
        } else {
            this.initGame()
        }
    }

    private fun initEditor() {

        //System.setProperty("org.lwjgl.opengl.Window.undecorated", "true")
        val config = Lwjgl3ApplicationConfiguration()
     /*   config.width = Lwjgl3ApplicationConfiguration.de().width
        config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height*/
        /*config.addIcon("editor/textures/ui/app-icon-128.png", Files.FileType.Internal)
        config.addIcon("editor/textures/ui/app-icon-32.png", Files.FileType.Internal)
        config.addIcon("editor/textures/ui/app-icon-16.png", Files.FileType.Internal)*/
        config.setTitle("planet2D")
        config.setResizable(true)
        //config.setDecorated(false)
        config.setWindowedMode(Config.VIRTUAL_SCREEN_WIDTH, Config.VIRTUAL_SCREEN_HEIGHT)
        Lwjgl3Application(Editor(), config)
    }

    private fun initGame() {

        val config = Lwjgl3ApplicationConfiguration()
        Lwjgl3Application(Game(), config)
    }
}
