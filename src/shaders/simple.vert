#version 450 core

layout(location = 0) in vec2 vPosition;
layout(location = 1) in vec3 vColor;
layout(location = 2) in mat4 model;

out vec3 fColor;

uniform mat4 projView;

void main()
{
  gl_PointSize = 10.f;
  gl_Position = projView * model * vec4(vPosition, 0.f, 1.f);
  fColor = vColor;
}