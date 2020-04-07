#version 400 core

in vec2 pass_textureCoordinates;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;
in float visibility;

out vec4 out_colour;

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;

uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;

void main() {

    vec4 blendMapColor = texture(blendMap, pass_textureCoordinates);
    float backTextureAmmount = 1 - (blendMapColor.r + blendMapColor.g + blendMapColor.b);
    vec2 tiledCoordinates = pass_textureCoordinates * 70.0;
    vec4 backgroundTextureColor = texture(backgroundTexture, tiledCoordinates) * backTextureAmmount;

    vec4 rTextureColor = texture(rTexture, tiledCoordinates) * blendMapColor.r;
    vec4 gTextureColor = texture(gTexture, tiledCoordinates) * blendMapColor.g;
    vec4 bTextureColor = texture(bTexture, tiledCoordinates) * blendMapColor.b;

    vec4 totalColor = backgroundTextureColor + rTextureColor + gTextureColor + bTextureColor;


    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);

    float nDot = dot(unitNormal, unitLightVector);
    float brightness = max(nDot, 0.05);
    vec3 diffuse = brightness * lightColor;

    vec3 unitVectorToCamera = normalize(toCameraVector);
    vec3 lightDirection = -unitLightVector;
    vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

    float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
    specularFactor = max(specularFactor, 0.0);
    float dampedFactor = pow(specularFactor,shineDamper);
    vec3 finalSpecular = dampedFactor * reflectivity * lightColor;

    out_colour = (vec4(diffuse, 1.0) * totalColor) + vec4(finalSpecular,1.0);
    out_colour = mix(vec4(skyColor, 1.0), out_colour, visibility);
}
