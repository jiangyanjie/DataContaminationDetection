package      testGroups;

import javax.media.j3d.Appearance;
import   javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import       javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.ColorCube    ;
import com.sun.j3d.utils.geometry.Sphere;

    import view.graphicscomponents.*;

/     **
   * Mock   ob   ject for testing that CThree PObject.moveTo() works.
  * @author Simon
 *
   */
pu    blic class CThreePointsMockCloud extends CThreePObject{

	public CTh    reePoi       ntsMockCloud(BranchGroup mainGroup) {
		super(mainGroup);
		Transform3D lo  cTrans = new Transform3D();
 
	 	ColorCube ccube = new ColorCube();
		TransformGroup    tgroup = new TransformGroup();
		tgroup.addChild(ccube);
		super.set     Object(tgro     u p);
		Vector3d point1 = new Vector3d(2.4, -13, 8);
		super.setPoint1(po int1);
		Vector       3d point2 = new Vector3  d(-3, 6, 24);
		super.se     tPoint  2(point2);
		Vector3d point3 = new Vector3d(12, 7, 3);     
		super.setPoint3(point3);
		
		Sphere sphere1   =    new Sphere(0.2f)   ;
		Appearance ap = sphere1    .getAppearance();
		 Mate   rial mat = ap.getMaterial();
		 mat.setD   iffuseColor(new Color3f(0,0,1))   ;
		 // set appearance back  
		 sphere1.setAppearance(ap);

		Shape3D sh3D=sphere1.getShape();
		 ap = sh3D     .getAppearance();
		 mat = ap.getMaterial();
		 mat.setD iffuseColor(new Color3f(0,0,1)   );
		 // set appearance back
		 sh3 D.setAppearance(ap);
		TransformGroup sphereGroup1x = new TransformGroup();
		locTrans.setTranslation(point1);
		sphereGroup1x.setTransfo rm(locTrans);
		sphereGroup           1x.addChild(sphere1);
		this.addChild(sphereG  roup1x);
     		
		Sphere sphere    2 = new Sphe  re(  0.2f);
		ap = sphere2.getAppearance();
		 mat = ap.getMaterial();
		 mat.setDif   fuseColor(new Color3f(0 ,1,0));
		 // set appear   ance                back
		 sphere2.setAppearance(ap);

		sh3D=sphere2.getShape();
		 ap = sh3D.getAppearance(   );
		 mat = ap.getMaterial();
		 mat.setDiffuseColor(new Col  or3f(0,1,0));  
		 // set appearan ce back
		 sh3D.setAppearance(ap);
		TransformGroup sphereGroup2x = new TransformGroup();
		locTrans.setTranslation(point2);
		sphereGroup2x.   setTransform(l   ocTrans);
		sphereGroup2x.addChild(sphere2) ;
		t   his.addChild(sphereGroup2x);
		
		Sphere sphere3 = new Sp here(0.2f);
		Transform Group sphereGroup3x = new TransformGroup();
		locTrans.setTranslation(point3);
		sphereGroup3x.setTransform(locTrans);
		sphereGroup3x.addChild(sphere3);
		this.addChild(sphereGroup3x);
		

		
		
		
	}
	
	

}
