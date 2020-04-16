package engineTester;

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

    public static void main(String[] args) {
        DisplayManager.createDisplay();

        Loader loader = new Loader();

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("flowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("brickRoad"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendmap"));

        Camera camera = new Camera();

        Light light = new Light(new Vector3f(2000, 2000, 2000), new Vector3f(1, 1, 1));

        ModelData modelData = OBJLoader.load("grassModel");
        Model model = loader.loadToVertexArrayObject(modelData.getVertices(), modelData.getTextureCoordinates(),
                modelData.getNormals(), modelData.getIndices());
        TexturedModel texturedModel = new TexturedModel(model,
                new ModelTexture(loader.loadTexture("grass_transparent")));
        texturedModel.getTexture().setHasTransparency(true);
        texturedModel.getTexture().setUseFakeLighting(true);
        ModelTexture texture = texturedModel.getTexture();
        texture.setReflectivity(1f);
        texture.setShineDamper(10f);

        Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -7), 0, 0, 0, 1);

        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap);
        Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap);

        ModelData girlData = OBJLoader.load("girl");
        Model girl = loader.loadToVertexArrayObject(girlData.getVertices(), girlData.getTextureCoordinates(),
                girlData.getNormals(), girlData.getIndices());
        TexturedModel texturedGirl = new TexturedModel(girl, new ModelTexture(loader.loadTexture("girl")));
        Player player = new Player(texturedGirl, new Vector3f(0, 0, 0), 0, 0, 0, 1);

        MasterRenderer renderer = new MasterRenderer();
        while (!Display.isCloseRequested()) {
            // entity.increaseRotation(0, 1, 0);
            player.move();
            renderer.processEntity(player);
            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
            renderer.processEntity(entity);
            renderer.render(light, camera);
            camera.move();
            DisplayManager.updateDisplay();
        }
        renderer.cleanUp();
        loader.cleanup();
        DisplayManager.closeDisplay();
    }
}
