package af.jogllab.draw;






import javax.media.opengl.GL2;


import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.GLEventListener;



import static javax.media.opengl.GL2.*;

public abstract class AbstractDrawable implements GLEventListener {


   protected GLU glu;













   @Override
   public void init(GLAutoDrawable drawable) {
      System.err.println("Init: " + drawable);
      GL2 gl = drawable.getGL().getGL2();






      System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());

      System.err.println("INIT GL IS: " + gl.getClass().getName());


      System.err.println("GL_VENDOR: " + gl.glGetString(GL2.GL_VENDOR));


      System.err.println("GL_RENDERER: " + gl.glGetString(GL2.GL_RENDERER));
      System.err.println("GL_VERSION: " + gl.glGetString(GL2.GL_VERSION));








      glu = new GLU();                         // get GL Utilities
      gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set background (clear) color
      gl.glClearDepth(1.0f);      // set clear depth value to farthest
      gl.glEnable(GL_DEPTH_TEST); // enables depth testing
      gl.glDepthFunc(GL_LEQUAL);  // the type of depth test to do
      gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // best perspective correction
      gl.glShadeModel(GL_SMOOTH); // blends colors nicely, and smoothes out lighting
      
   }












   @Override
   public void dispose(GLAutoDrawable drawable) {

      System.out.println("dispose");
   }

   @Override
   public abstract void display(GLAutoDrawable drawable);

   @Override
   public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {















      GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context






      if (height == 0) {

         height = 1;   // prevent divide by zero
      }
      float aspect = (float) width / height;




      // Set the view port (display area) to cover the entire window
      gl.glViewport(0, 0, width, height);

      // Setup perspective projection, with aspect ratio matches viewport
      gl.glMatrixMode(GL_PROJECTION);  // choose projection matrix


      gl.glLoadIdentity();             // reset projection matrix
      glu.gluPerspective(45.0, aspect, 0.1, 100.0); // fovy, aspect, zNear, zFar

      // Enable the model-view transform
      gl.glMatrixMode(GL_MODELVIEW);
      gl.glLoadIdentity(); // reset
   }
}
