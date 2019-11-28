package com.planet2d.editor.layouts

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.planet2d.editor.Editor
import com.planet2d.editor.config.EditorTypes.PageType
import com.planet2d.editor.pages.PageTabs
import com.planet2d.engine.layouts.Layout
import com.planet2d.engine.screens.Screen
import com.planet2d.editor.ui.ToolBar

class WindowLayout(screen: Screen) : Layout(screen) {

    var mainToolBar: ToolBar? = null
    var pagesTabs: PageTabs? = null

    override fun init() {

        super.init()
        this.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                unselectActors()
                return true
            }
        })
    }

    fun unselectActors() {

        this.mainToolBar?.unselectButtons()
    }

    fun create() {

        this.mainToolBar = ToolBar("mainToolBar")
        this.mainToolBar!!.resize()

        this.pagesTabs = PageTabs()
        this.pagesTabs?.addTab(PageType.WELCOME)
        this.setPagesTabsPosition()

        this.addActor(this.pagesTabs)
        this.addActor(this.mainToolBar)
    }

    private fun setPagesTabsPosition() {

        this.pagesTabs?.setPosition(0f, this.mainToolBar!!.y - this.pagesTabs?.height!! - 2f)
    }

    override fun resize(width: Int, height: Int) {

        super.resize(width, height)
        this.mainToolBar?.resize()
        this.setPagesTabsPosition()
        this.pagesTabs?.width = width.toFloat()
    }
}
