package com.png261.bomberman.object;

public abstract class GameObject extends PhysicObject {
    protected Boolean isExist = true;
    protected LoaderParams params;

    public GameObject() {
        super();
    }

    public void load(LoaderParams params) {
        this.params = params;
    };

    public abstract void update(float delta);

    public abstract void render();

    public boolean exist() {
        return isExist;
    }

    protected void disappear() {
        isExist = false;
    }
}
