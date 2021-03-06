#version 400 core

in vec2 pass_textureCoordinates;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;
in float visibility;

out vec4 out_colour;

uniform sampler2D textureSampler;
uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;

void main() {
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);

    float nDot = dot(unitNormal, unitLightVector);
    float brightness = max(nDot, 0.2);
    vec3 diffuse = brightness * lightColor;

    vec3 unitVectorToCamera = normalize(toCameraVector);
    vec3 lightDirection = -unitLightVector;
    vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

    float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
    specularFactor = max(specularFactor, 0.0);
    float dampedFactor = pow(specularFactor,shineDamper);
    vec3 finalSpecular = dampedFactor * reflectivity * lightColor;

    vec4 textureColor = texture(textureSampler, pass_textureCoordinates);
    if(textureColor.a < 0.5){
        discard;
    }
    out_colour = (vec4(diffuse, 1.0) * textureColor) + vec4(finalSpecular,1.0);
    out_colour = mix(vec4(skyColor, 1.0), out_colour, visibility);
}
