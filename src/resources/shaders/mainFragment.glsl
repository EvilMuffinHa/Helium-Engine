#version 410 core



layout(location = 0) in vec3 passColor;
layout(location = 1) in vec2 passTextureCoord;

layout(location = 0) out vec4 outColor;

void main() {
    outColor = vec4(passColor, 1.0);
}
