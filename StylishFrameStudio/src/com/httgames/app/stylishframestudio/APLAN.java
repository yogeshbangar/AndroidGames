package com.httgames.app.stylishframestudio;

public class APLAN extends Mesh {
	
	public APLAN(float sx,float sy) {
		float textureCoordinates[] = { 0.0f, 1.0f, 
				1.0f, 1.0f, 
				0.0f, 0.0f, 
				1.0f, 0.0f, 
		};
		short[] indices = new short[] { 0, 1, 2, 1, 3, 2 };
		float[] vertices = new float[] 
				{ -sx, -sy, 0.0f, 
				   sx, -sy, 0.0f,
				  -sx,  sy, 0.0f,
				   sx,  sy, 0.0f };
		setIndices(indices);
		setVertices(vertices);
		setTextureCoordinates(textureCoordinates);
	}
	public APLAN() {
		this(1,1);
	}
}
