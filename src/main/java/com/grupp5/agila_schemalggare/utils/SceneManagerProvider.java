package com.grupp5.agila_schemalggare.utils;

public class SceneManagerProvider {

    private static SceneManager sceneManager;

    private SceneManagerProvider() {}

    public static void setSceneManager(SceneManager manager) {
        sceneManager = manager;
    }

    public static SceneManager getSceneManager() {
        if (sceneManager == null) {
            throw new IllegalStateException("SceneManager has not been initialized. Call setSceneManager() first.");
        }

        return sceneManager;
    }
}
