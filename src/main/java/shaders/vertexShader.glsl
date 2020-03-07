#version 400 core

in vec3 position;
in vec2 textureCoordinates;

out vec3 colour;
out vec2 pass_textureCoordinates;

uniform mat4 transformationMatrix;

void main() {
    gl_Position = vec4(position, 1.0);
    pass_textureCoordinates = textureCoordinates;
    colour = vec3(position.x+0.5, position.y+0.5, position.z+0.5);
}
