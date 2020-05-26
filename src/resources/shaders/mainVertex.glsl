#version 410 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 color;
layout(location = 2) in vec2 textureCoord;

uniform mat4 model;
uniform mat4 projection;

layout(location = 0) out vec3 passColor;
layout(location = 1) out vec2 passTextureCoord;



void main() {
	gl_Position = projection * model * vec4(position, 1.0);
	passColor = color;

	passTextureCoord = textureCoord;
}