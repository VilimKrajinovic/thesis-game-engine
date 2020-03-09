#version 400 core

in vec3 colour;
in vec2 pass_textureCoordinates;

out vec4 out_colour;

uniform sampler2D textureSampler;

void main() {

    vec4 textureColor = texture(textureSampler, pass_textureCoordinates);

    out_colour =  textureColor;
}
