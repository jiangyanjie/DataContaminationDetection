package testGroups;

import    javax.media.j3d.Appearance;
imp    ort javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.RenderingAttributes;    
import javax.media.j3d.Shape3D;
import      javax.media.j3d.Transform3D    ;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;   

import com.sun.j3d.utils.geometr y.ColorCube;
import com.sun.j3d.utils.geometry.Sphere;

i  mport view.graph   icscomponents.*;

    

/**
         * Mock object for testing that    CThreePObject.moveTo()   work    s.
 * @author Simon     
 *    
 */
public cl   ass CThreePointsMockObject extends CThreePObject{

	public CThreePointsMockObject(BranchGroup mainGroup) {
		super(mai    nGroup);
		Transform3D locTran    s =  new Transform3D();

		ColorCube ccube = new ColorCube();
		TransformGroup tgroup =    new TransformGroup();
		tgroup.addChild(   ccube);
		super.setObject(tgroup);
		Vector3d    point1 = new Vector3d(2.4, -13, 8);
		super.setPoint1    (point1);
		Vector3d p  oint2 = new Vector3d(-3, 6, 2   4);
		super.     setPoint 2(poin  t2);    
		Vector3d point3 = new Vector3d(12, 7, 3);
		super.setPoint3(point3);
		
	      	S    phere sphere1 = new S  p         here(0.2f);
	   	
		Appearance   ap        = sphere1.getAppe       arance();
		 Material mat = a   p.getMaterial();
		 mat.setDiffuseColor(new Color3f(0,0      ,1));

	     R       enderingAttrib  utes ra  = new RenderingAttri  b     utes();
//	      ra.se  tDepthBufferEnable(false);
	     ra.setDepthTestFunction(RenderingAttributes   .NEVE              R);
		 ap.setRenderingAttributes(ra);
		 // set appearance back
		 sp    here1.setAppear     ance(ap);

		Shape3D sh3D=sphere 1.getSha   pe();
		 ap =   sh3D.getApp earance();
		 mat = ap.  getMaterial();
		 mat.setDiffuseColor(new Color3f(0,0,1));
		 // set appearance back
		 ap.setRenderingAttributes(ra);
		 sh3D.setAppearance(ap);  
		 
		 
   		TransformGroup sphereGroup1x = new TransformGroup();
		locTrans.setTranslation(point1);
		spher       eGroup1x.setTransform(locTrans);
		sphereGroup1x.addChi     ld  (   sphere1);
		this.addChild(sphereGroup1x);
		
		Sphere sphere2 = new  Sphere(0.2    f);
		ap = sphere2.getAppearance();
		 mat = ap.getMaterial();
		 mat.setDiffuseColor(new   Color3f(0,1,0));
		 ap.setRenderingAttributes(ra);
		 // set appearance back
		 sphere2.setAppearance(ap);

		sh3D=sphere2.getSha  pe();
		 a   p = s h3D.getAppearance();
		 mat = ap.getMaterial();
		 mat.setDiffuseColor(new Color3f(0,1,0));
		 ap.setRenderingAttribute s(ra);
		 // set appe arance back
		 sh3D   .setAppearance(ap);
 		       
		TransformGroup sphereGroup2x = new TransformGroup  ();
		locTra   ns.setTranslati    on(point2);
		sphereGr  oup2x.setTransform(locTrans);
		sphereGroup2x.addChild(sphere2);
		this.addChild(sphereGroup2x);
		
		Sphere sphere3 = new Sphere(0.2f) ;
		TransformGroup sphereGroup3x = new TransformGroup();
		locTrans.setTranslation(point3);
		sphereGroup3x.setTransform(locTrans);
		sphereGroup3x.addChild(sphere3);
		this.addChild(sphereGroup3x);
		

		
		
		
	}
	
	

}
