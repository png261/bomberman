package com.png261.bomberman.object;

public abstract class GameObject extends PhysicObject {
    protected Boolean isExist = true;

    public GameObject() {
        super();
    }

    public abstract void load(LoaderParams position);

    public abstract void update(float delta);

    public abstract void render();

    public boolean exist() {
        return isExist;
    }

    protected void disappear() {
        isExist = false;
    }
}
