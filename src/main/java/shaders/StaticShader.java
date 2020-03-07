package shaders;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE_PATH = "src/main/java/shaders/vertexShader.glsl";
    private static final String FRAGMENT_FILE_PATH = "src/main/java/shaders/fragmentShader.glsl";

    private int locationTransformationMatrix;

    public StaticShader() {
        super(VERTEX_FILE_PATH, FRAGMENT_FILE_PATH);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoordinates");
    }

    @Override
    protected void getAllUniformLocations() {
        locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
    }

    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(locationTransformationMatrix, matrix);
    }
}
