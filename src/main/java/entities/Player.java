package entities;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import model.TexturedModel;
import renderEngine.DisplayManager;
import terrain.Terrain;

public class Player extends Entity {

    private static final float RUN_SPEED = 40;
    private static final float TURN_SPEED = 180;
    private static float GRAVITY = -50;
    private static final float JUMP_POWER = 15;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    private boolean isInAir = false;

    private List<Entity> inventory = new ArrayList<>();

    public Player(TexturedModel texturedModel, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(texturedModel, position, rotX, rotY, rotZ, scale);
    };

    public void move(Terrain terrain) {
        checkKeyboardInputs();
        super.increaseRotation(0, currentTurnSpeed * DisplayManager.getDelta(), 0);
        float distance = currentSpeed * DisplayManager.getDelta();

        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
        super.increasePosition(dx, 0, dz);
        upwardsSpeed += GRAVITY * DisplayManager.getDelta();
        super.increasePosition(0, upwardsSpeed * DisplayManager.getDelta(), 0);
        float terrainHeight = terrain.getHeightOfTerrainAtCoordinate(super.getPosition().x, super.getPosition().z);
        if (super.getPosition().y < terrainHeight) {
            upwardsSpeed = 0;
            isInAir = false;
            super.getPosition().y = terrainHeight;
        }
    }

    private void jump() {
        if (!isInAir) {
            this.upwardsSpeed = JUMP_POWER;
            isInAir = true;
        }
    }

    private void checkKeyboardInputs() {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            this.currentSpeed = RUN_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            this.currentSpeed = -RUN_SPEED;
        } else {
            this.currentSpeed = 0;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            this.currentTurnSpeed = -TURN_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            this.currentTurnSpeed = TURN_SPEED;
        } else {
            currentTurnSpeed = 0;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            jump();
        }
    }

    public void pickUp(Entity entity) {
        if (!inventory.contains(entity)) {
            System.out.println("Pickedup entity");
            inventory.add(entity);
            entity.setShouldRender(false);
        }
        System.out.println("Currently there are " + inventory.size() + " items in inventory");
    }

}
