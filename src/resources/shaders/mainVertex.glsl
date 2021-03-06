#version 410 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 color;
layout(location = 2) in vec2 textureCoord;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;
uniform int type;

layout(location = 0) out vec3 passColor;
layout(location = 1) out vec2 passTextureCoord;
layout(location = 2) out int passType;



void main() {
	gl_Position =  projection * view  * model *  vec4(position, 1.0);
	passColor = color;
	passType = type;

	passTextureCoord = textureCoord;
}