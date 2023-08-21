package com.png261.bomberman.object;

public interface DamageableObject {
    public void damage(int damage);

    public void dead();

    public boolean isDead();
}
