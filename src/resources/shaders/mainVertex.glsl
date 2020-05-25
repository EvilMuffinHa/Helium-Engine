#version 410 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 color;
layout(location = 2) in vec2 textureCoord;

layout(location = 0) out vec3 passColor;
layout(location = 1) out vec2 passTextureCoord;

void main() {
    gl_Position = vec4(position, 1.0);
    passColor = color;
    passTextureCoord = textureCoord;
}