package com.desitum.library.game_objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.desitum.library.animation.Animator;
import com.desitum.library.animation.MovementAnimator;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by kody on 12/27/15.
 * can be used by kody and people in [kody}]
 */
public class GameObject extends Sprite implements Comparable<GameObject> {

    public static final int DEFAULT_Z = 0;
    private ArrayList<Animator> animators;
    private OnFinishedMovingListener onFinishedMovingListener;
    private int z;

    private float speed, speedX, speedY, gravityX, gravityY, rotationSpeed, rotationResistance;
    private float[] moveTo;

    @SuppressWarnings("unused")
    public GameObject() {
        animators = new ArrayList<Animator>();
        z = DEFAULT_Z;
    }

    @SuppressWarnings("unused")
    public GameObject(int z) {
        animators = new ArrayList<Animator>();
        this.z = z;
    }

    public void update(float delta) {
        // update animators (Rotate, Scale, Movement)
        if (animators.size() > 0) {
            Iterator<Animator> iterator = animators.iterator();
            while (iterator.hasNext()) {
                Animator animator = iterator.next();
                animator.update(delta);
                if (animator.didFinish()) {
                    iterator.remove();
                }
            }
        }
        // update position
        setSpeedX(getSpeedX() + getGravityX() * delta);
        setSpeedY(getSpeedY() + getGravityY() * delta);
        setX(getX() + getSpeedX());
        setY(getY() + getSpeedY());
        setRotationSpeed(getRotationSpeed() * rotationResistance);
        if (moveTo != null) {
            updateMovement();
        }
    }

    private void updateMovement() {
        if (moveTo != null) {
            if (speedX < 0) {
                if (getX() < moveTo[0]) {
                    moveFinished();
                }
            } else if (speedX > 0) {
                if (getX() > moveTo[0]) {
                    moveFinished();
                }
            } else if (speedY < 0) {
                if (getY() < moveTo[1]) {
                    moveFinished();
                }
            } else if (speedY > 0) {
                if (getY() > moveTo[1]) {
                    moveFinished();
                }
            }
        }
    }

    private void moveFinished() {
        setX(moveTo[0]);
        setY(moveTo[1]);
        moveTo = null;
        if (onFinishedMovingListener != null) {
            onFinishedMovingListener.onFinished(this);
        }
    }

    @SuppressWarnings("unused")
    public void moveToPosition(float x, float y) {
        // x = cos
        // y = sin
        float deltaX = getX() - x;
        float deltaY = getY() - y;
        float angle = (float) Math.atan2(deltaY, deltaX); // in radians
        speedX = (float) Math.cos(angle) * speed;
        speedY = (float) Math.sin(angle) * speed;
    }

    @SuppressWarnings("unused")
    public void updateTouchInput(Vector3 touchPos, boolean touchDown) {

    }

    @SuppressWarnings("unused")
    public void moveTo(float x, float y, float duration, int interpolation) {
        MovementAnimator xAnimator = new MovementAnimator(this, getX(), x, duration, 0, interpolation, true, false);
        MovementAnimator yAnimator = new MovementAnimator(this, getY(), y, duration, 0, interpolation, false, true);
        xAnimator.start(true);
        yAnimator.start(true);
        this.animators.add(xAnimator);
        this.animators.add(yAnimator);
    }

    @SuppressWarnings("unused")
    public void addAndStartAnimator(Animator anim) {
        anim.setSprite(this);
        animators.add(anim);
    }

    public int getZ() {
        return z;
    }

    @Override
    public int compareTo(GameObject gameObject2D) {
        return this.getZ() - gameObject2D.getZ();
    }

    @SuppressWarnings("unused")
    public void setZ(int z) {
        this.z = z;
    }

    @SuppressWarnings("unused")
    public float getSpeed() {
        return speed;
    }

    @SuppressWarnings("unused")
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @SuppressWarnings("unused")
    public float getSpeedX() {
        return speedX;
    }

    @SuppressWarnings("unused")
    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    @SuppressWarnings("unused")
    public float getSpeedY() {
        return speedY;
    }

    @SuppressWarnings("unused")
    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    @SuppressWarnings("unused")
    public float getGravityX() {
        return gravityX;
    }

    @SuppressWarnings("unused")
    public void setGravityX(float gravityX) {
        this.gravityX = gravityX;
    }

    @SuppressWarnings("unused")
    public float getGravityY() {
        return gravityY;
    }

    @SuppressWarnings("unused")
    public void setGravityY(float gravityY) {
        this.gravityY = gravityY;
    }

    @SuppressWarnings("unused")
    public float getRotationSpeed() {
        return rotationSpeed;
    }

    @SuppressWarnings("unused")
    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    @SuppressWarnings("unused")
    public float getRotationResistance() {
        return rotationResistance;
    }

    /**
     * Set rotation resistance where less than 1 slows down
     * more than 1 speed up rotation
     * and where 0 stops immediately
     * @param rotationResistance rate to slow down by
     */
    @SuppressWarnings("unused")
    public void setRotationResistance(float rotationResistance) {
        this.rotationResistance = rotationResistance;
    }

    @SuppressWarnings("unused")
    public void setOnFinishedMovingListener(OnFinishedMovingListener onFinishedMovingListener) {
        this.onFinishedMovingListener = onFinishedMovingListener;
    }

    @SuppressWarnings("unused")
    public OnFinishedMovingListener getOnFinishedMovingListener() {
        return onFinishedMovingListener;
    }
}
