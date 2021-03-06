package com.test.helloeeg;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

public class OpenGLRenderer implements Renderer {
	private Square square;
	float height = -3;
	double velocity = 0.0;
	boolean up = true;
	boolean reset = false;
	Meditation caller;
	
	public OpenGLRenderer(Meditation caller) {
		// Initialize our square. 
		this.caller = caller;
		square = new Square();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.opengl.GLSurfaceView.Renderer#onSurfaceCreated(javax.microedition
	 * .khronos.opengles.GL10, javax.microedition.khronos.egl.EGLConfig)
	 */
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Set the background color to black ( rgba ).
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		// Enable Smooth Shading, default not really needed.
		gl.glShadeModel(GL10.GL_SMOOTH);
		// Depth buffer setup.
		gl.glClearDepthf(1.0f);
		// Enables depth testing.
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// The type of depth testing to do.
		gl.glDepthFunc(GL10.GL_LEQUAL);
		// Really nice perspective calculations.
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.opengl.GLSurfaceView.Renderer#onDrawFrame(javax.microedition.
	 * khronos.opengles.GL10)
	 */
	public void onDrawFrame(GL10 gl) {
		// Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// Replace the current matrix with the identity matrix
		gl.glLoadIdentity();
		// Translates 10 units into the screen.
		gl.glTranslatef(0, 0, -10); 
		

		// SQUARE C
		// Save the current matrix
		//gl.glPushMatrix();
		// Make the rotation around B
		//gl.glRotatef(-angle, 0, 0, 1);
		//gl.glTranslatef(2, 0, 0);	
		// Scale it to 50% of square B
		//gl.glScalef(.5f, .5f, .5f);
		// Rotate around it's own center.
		//gl.glRotatef(angle*10, 0, 0, 1);
		// Draw square C.
		gl.glTranslatef(0, height, 0);
		square.draw(gl);
		
		// Restore to the matrix as it was before C.
		gl.glPopMatrix();
		// Restore to the matrix as it was before B.
		gl.glPopMatrix();
		
		if (WaveData.med < 50) {
			velocity = -.05;
		} else {
			velocity = .05;
		}
			
		//float prevHeight = height;
		height += velocity;
		
		if (height < -3){
			reset = true;
			height = -3;
		} else if (height > 3) {
			height = 3;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.opengl.GLSurfaceView.Renderer#onSurfaceChanged(javax.microedition
	 * .khronos.opengles.GL10, int, int)
	 */
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// Sets the current view port to the new size.
		gl.glViewport(0, 0, width, height);
		// Select the projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// Reset the projection matrix
		gl.glLoadIdentity();
		// Calculate the aspect ratio of the window
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,
				100.0f);
		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// Reset the modelview matrix
		gl.glLoadIdentity();
	}
}
