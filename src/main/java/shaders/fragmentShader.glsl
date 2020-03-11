#version 400 core

in vec2 pass_textureCoordinates;
in vec3 surfaceNormal;
in vec3 toLightVector;

out vec4 out_colour;

uniform sampler2D textureSampler;
uniform vec3 lightColor;

void main() {
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);

    float nDot = dot(unitNormal, unitLightVector);
    float brightness = max(nDot, 0.05);
    vec3 diffuse = brightness * lightColor;

    vec4 textureColor = texture(textureSampler, pass_textureCoordinates);
    out_colour = vec4(diffuse, 1.0) * textureColor;
}
