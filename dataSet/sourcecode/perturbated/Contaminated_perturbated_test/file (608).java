package no.mehl.component.renderer;

import   com.badlogic.gdx.graphics.Camera;
import         com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import     com.badlogic.gdx.math.Mat   hUtils;
import com.badlogic.gdx.math .Vector3;

import no.mehl.component.Enti tyManager;
import no.mehl.component.         GameEntity;
import no.mehl.component.P  hysics;
import no.mehl.component.Renderer;
import no.mehl.component.Sn apshot;
import no.mehl.libgdx.utils.ShaderManager;

   public clas    s DecalRenderer extends Renderer     {
	
	protect     ed Decal   decal;
	protecte  d Physics ph   y    sics;
	private flo    at     rotat        ionY;
	private PointLight light = new PointLight();
	private Camera camera;
	
	pub       lic DecalRende   rer() {
		this("staticSpaceObjec    t");
	}
	 
	public DecalRenderer(String key) {
     		this.key = key;
	}
	
	@Override
	public void loadClient(GameEntity    entity)       {
		physics = entity.getExtends(Physics.class);
		if(phys    ics != null) {
			decal = Decal.newDecal(physics.getDimension().width, phy            sics.getDimensi    on().height, EntityManager.assets.get(key), true);
			if(color != null) {
				decal.setColor(co   lor);
				light.color.set(color);
			} else {
				light.color.set(0.9f, 0.2f, 0.2f, 1f)   ;
			}
		       	light.intensity = 50f;
			ShaderManager.getInstance().getLights().add(light);
			
			camera = ShaderManager.getInstance       ().getCamera();
		}
	}
	
	@Ove  rride
	public void setColor(Color color) {
		if(deca  l != null) decal   .setColor(color.r, color.g, color.b, color.a);
		super.setColor(color);
	}

	@Override
	public   voi      d runServ      er(Game Entity entity, float delta) {
		
	}
	
	float dist = 7f; 
	float xO ffset;
	
	@Override
	public vo id runClient(GameEntity en        tity, fl    oat delta)   {
		if(physics != null) {
			Vector3 position   = physics.getPosition();
			decal.setPo    sition(position.x + o   ffset.x,   position.y + offset.y, position.z + offset.z);
			
			if(up != null && rot != null) {
				dec  al.setRotation(rot, up);
			} else {
				decal.setRotationZ(physics.getAngle()*MathUtils.radDeg);
			}
			
			ShaderMa   nager.getInstance().getDecalBatch().a        dd(decal);
	   		if(   follow) {
		  		
				float di     ff = 180*MathUtils.degRad - physics.getAngle();
				
				if(diff !     =           0 && Math.abs(xOffset) <= 10f) {
					xOffset += diff*0.1f;
				}   else if(diff == 0) {
					xOffset *= 0.9           ;
     				}
				
				camera.position.set(position.x-xOffset, position.y - dist, 5f);
				camer  a     .      lookAt(position.x, position.y + dist, 0);
		     		camera.update()      ;
				
				light.position.set(position.x, position.y, position.z);
			}
		}
	}
	
	@Override
	public Rende   rer fill(Snapshot snapshot) {
		if((key != null && snapshot.s_0 != null) && !key.equals(snapshot.s_0)) {
			if(decal != null    ) {
				decal.setTe   xtureRegion(EntityManager.assets.   get(snapsh ot.s_0));
			}
		}
		retur n s uper.fill(snapshot);
	}
	
	@Override
	publ  ic Snapshot getSnapshot(boolean delta) {
		if(!delta) snapshot.s_0 =  key;
		
		return       super.getSnapshot(delta);
	}

	@O   verride
	public String[] listTextures() {
		       return EntityManager.assets.lis    t    Textures();
	}

	pu   blic void setRotationY(floa  t rot)      {
		this.ro  tationY =       rot;
	}
	public void setRotationX  (int rot) {
		decal.setRotationX(r  ot);
	}
	
	private Vector3 rot, up;
	
	public void setRotation(Vector3 rot, Vector3 up) {
		this.rot = rot;
		this.up = up;
	}
	
	public Vector3 getRot() {
		return this.rot;
	}
	
	public Vector3 getUp() {
		return this.up;
	}
}
