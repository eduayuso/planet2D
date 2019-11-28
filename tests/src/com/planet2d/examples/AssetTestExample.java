package com.planet2d.examples;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.Gdx;
import com.planet2d.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class AssetTestExample {
    @Test
    public void badlogicLogoFileExists() {
        assertTrue("This test will only pass when the badlogic.jpg file coming with a new project setup has not been deleted.", Gdx.files
                .internal("../core/assets/badlogic.jpg").exists());
    }
}