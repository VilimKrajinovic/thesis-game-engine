package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import loaders.ModelData;
import loaders.OBJLoader;
import model.Model;
import model.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrain.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class Main {

    private static boolean pickedUpOne = false;
    private static boolean pickedUpTwo = false;
    private static boolean shouldWriteVictory = true;

    public static void main(String[] args) {
        DisplayManager.createDisplay();

        Loader loader = new Loader();

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("flowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("brickRoad"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendmap"));

        Light light = new Light(new Vector3f(2000, 2000, 2000), new Vector3f(1, 1, 1));

        ModelData grassModelData = OBJLoader.load("grassModel");
        Model grassModel = loader.loadToVertexArrayObject(grassModelData.getVertices(),
                grassModelData.getTextureCoordinates(), grassModelData.getNormals(), grassModelData.getIndices());
        TexturedModel texturedGrass = new TexturedModel(grassModel,
                new ModelTexture(loader.loadTexture("grass_transparent")));
        texturedGrass.getTexture().setHasTransparency(true);
        texturedGrass.getTexture().setUseFakeLighting(true);

        ModelData fernModelData = OBJLoader.load("fern");
        Model fernModel = loader.loadToVertexArrayObject(fernModelData.getVertices(),
                fernModelData.getTextureCoordinates(), fernModelData.getNormals(), fernModelData.getIndices());
        TexturedModel texturedFern = new TexturedModel(fernModel, new ModelTexture(loader.loadTexture("fern")));
        texturedFern.getTexture().setHasTransparency(true);
        texturedFern.getTexture().setUseFakeLighting(true);

        Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap, "heightMap2");

        List<Entity> entities = new ArrayList<>();
        Random random = new Random();
        populateWorldWithGrassAndFerns(texturedGrass, texturedFern, entities, random, terrain);

        ModelData girlData = OBJLoader.load("girl");
        Model girl = loader.loadToVertexArrayObject(girlData.getVertices(), girlData.getTextureCoordinates(),
                girlData.getNormals(), girlData.getIndices());
        TexturedModel texturedGirl = new TexturedModel(girl, new ModelTexture(loader.loadTexture("girl")));
        Player player = new Player(texturedGirl, new Vector3f(0, 0, 0), 0, 0, 0, 1);

        // PICKABLE OBJECTS
        float x = random.nextFloat() * 100;
        float z = random.nextFloat() * 100;

        ModelData oneModelData = OBJLoader.load("one");
        Model oneModel = loader.loadToVertexArrayObject(oneModelData.getVertices(),
                oneModelData.getTextureCoordinates(), oneModelData.getNormals(), oneModelData.getIndices());
        TexturedModel texturedOneModel = new TexturedModel(oneModel, new ModelTexture(loader.loadTexture("white")));
        Entity numberOne = new Entity(texturedOneModel,
                new Vector3f(x, terrain.getHeightOfTerrainAtCoordinate(x, z), z), 0, 0, 0, 6);

        x = random.nextFloat() * 100;
        z = random.nextFloat() * 100;

        ModelData twoModelData = OBJLoader.load("two");
        Model twoModel = loader.loadToVertexArrayObject(twoModelData.getVertices(),
                twoModelData.getTextureCoordinates(), twoModelData.getNormals(), twoModelData.getIndices());
        TexturedModel texturedTwoModel = new TexturedModel(twoModel, new ModelTexture(loader.loadTexture("white")));
        Entity numberTwo = new Entity(texturedTwoModel,
                new Vector3f(x, terrain.getHeightOfTerrainAtCoordinate(x, z), z), 0, 0, 0, 6);

        Camera camera = new Camera(player);
        MasterRenderer renderer = new MasterRenderer();
        while (!Display.isCloseRequested()) {
            // entity.increaseRotation(0, 1, 0);
            player.move(terrain);

            checkNumbers(player, numberOne, numberTwo);

            renderer.processEntity(player);
            renderer.processTerrain(terrain);
            // renderer.processTerrain(terrain2);
            for (Entity entity : entities) {
                renderer.processEntity(entity);
            }

            renderer.processEntity(numberOne);
            renderer.processEntity(numberTwo);
            numberOne.increaseRotation(0, 0.3f, 0);
            numberTwo.increaseRotation(0, 0.3f, 0);
            renderer.render(light, camera);
            camera.move();
            DisplayManager.updateDisplay();
        }
        renderer.cleanUp();
        loader.cleanup();
        DisplayManager.closeDisplay();
    }

    private static void populateWorldWithGrassAndFerns(TexturedModel grass, TexturedModel fern, List<Entity> entities,
            Random random, Terrain terrain) {
        for (int i = 0; i < 10000; i++) {
            if (i % 10 == 0) {
                float x = random.nextFloat() * 800;
                float z = random.nextFloat() * 800;
                float y = terrain.getHeightOfTerrainAtCoordinate(x, z);
                entities.add(new Entity(grass, new Vector3f(x, y, z), 0, 0, 0, 1));
            }
            if (i % 5 == 0) {
                float x = random.nextFloat() * 800;
                float z = random.nextFloat() * 800;
                float y = terrain.getHeightOfTerrainAtCoordinate(x, z);
                entities.add(new Entity(fern, new Vector3f(x, y, z), 0, 0, 0, 0.5f));
            }
        }
    }

    private static void checkNumbers(Player player, Entity one, Entity two) {
        if (!pickedUpOne) {
            if (one.isShouldRender() && checkCollision(player, one)) {
                player.pickUp(one);
                pickedUpOne = true;
            }
        } else if (!pickedUpTwo) {
            if (two.isShouldRender() && checkCollision(player, two)) {
                player.pickUp(two);
                pickedUpTwo = true;
            }
        }
        if (shouldWriteVictory && pickedUpTwo) {
            shouldWriteVictory = false;
            System.out.println("VICTORY!");
        }
    }

    private static boolean checkCollision(Player player, Entity entity) {
        if (checkXAxis(player, entity))
            return checkZAxis(player, entity);
        return false;
    }

    private static boolean checkZAxis(Player player, Entity entity) {
        return player.getPosition().z >= entity.getPosition().z - 5
                && player.getPosition().z <= entity.getPosition().z + 5;
    }

    private static boolean checkXAxis(Player player, Entity entity) {
        return player.getPosition().x >= entity.getPosition().x - 5
                && player.getPosition().x <= entity.getPosition().x + 5;
    }
}
