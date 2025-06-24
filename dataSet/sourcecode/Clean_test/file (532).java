package cs355.solution.controller;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import cs355.HouseModel;
import cs355.WireFrame;
import cs355.solution.util.math.Matrix;
import cs355.solution.util.math.Vector3D;

public class Controller3D
{
	private final int		view_height		= 2048;
	private final int		view_width		= 2048;
	private final float		aspect			= view_width * 1f / view_height;
	private final float		fov_x			= (float) (Math.PI * 0.35);
	private final float		fov_y			= fov_x * aspect;
	private final float		zoom_x			= (float) (1.0 / Math.tan(fov_x * 0.5));
	private final float		zoom_y			= (float) (1.0 / Math.tan(fov_y * 0.5));
	private final float		scalar			= 0.25f;
	private final float		z_near			= 0.1f;
	private final int		z_far			= 100;
	private final float		rotate_speed	= (float) (Math.PI / 128f);

	private boolean			enabled;
	private WireFrame		model;

	private final Vector3D	forward;
	private final Vector3D	up				= Vector3D.Y_AXIS.getScaledCopy(scalar);
	private final Vector3D	camera;
	private boolean			dirty;

	private final Matrix	wtocTranslation;
	private final Matrix	wtocRotation;
	private final Matrix	clip;

	private Matrix			wtoClip;
	private final Matrix	clipToScreen;

	public Controller3D()
	{
		enable(false);

		camera = new Vector3D();
		forward = new Vector3D();

		wtocTranslation = new Matrix();
		wtocRotation = new Matrix();

		loadHome();

		clip = Matrix.createClipMatrix(zoom_x, zoom_y, z_far, z_near);
		clipToScreen = Matrix.createClipToScreenMatrix(view_width, view_height);

		dirty = true;
	}

	private void loadHome()
	{
		camera.copyValues(0, -4, -20);
		forward.copyValues(Vector3D.Z_AXIS.getInvertedCopy());

		wtocTranslation.copyValues(Matrix.createTranslationMatrix(camera));
		wtocRotation.copyValues(Matrix.createWorldToCameraRotationMatrix(camera, camera.getAddedCopy(forward), up));
	}

	private void update()
	{
		wtoClip = clip.getMultipliedCopy(wtocRotation.getMultipliedCopy(wtocTranslation));
		dirty = false;
	}

	public Matrix getObjectToClipTransform()
	{
		if (dirty)
			update();

		return wtoClip;
	}

	public Matrix getClipToScreenTransform()
	{
		return clipToScreen;
	}

	public WireFrame getHouseModel()
	{
		return model;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void enable()
	{
		enable(true);
	}

	public void enable(boolean enable)
	{
		enabled = enable;
		if (enabled)
		{
			switch ((int) (Math.random() * 1))
			{
				case 0:
					model = new HouseModel();
			}
		}
	}

	public boolean keyPressed(Iterator<Integer> itr)
	{
		ArrayList<Integer> keys = new ArrayList<Integer>();
		while (itr.hasNext())
			keys.add(itr.next());

		if (keys.contains(KeyEvent.VK_W))
		{
			Vector3D trans = forward.getInvertedCopy();
			wtocTranslation.translate(trans);
			dirty = true;
		}
		if (keys.contains(KeyEvent.VK_S))
		{
			wtocTranslation.translate(forward);
			dirty = true;
		}
		if (keys.contains(KeyEvent.VK_A))
		{
			Vector3D left = up.cross(forward);
			wtocTranslation.translate(left);
			dirty = true;
		}
		if (keys.contains(KeyEvent.VK_D))
		{
			Vector3D right = forward.cross(up);
			wtocTranslation.translate(right);
			dirty = true;
		}
		if (keys.contains(KeyEvent.VK_Q))
		{
			forward.rotateY(-rotate_speed);
			wtocRotation.rotateY(rotate_speed);
			dirty = true;
		}
		if (keys.contains(KeyEvent.VK_E))
		{
			forward.rotateY(rotate_speed);
			wtocRotation.rotateY(-rotate_speed);
			dirty = true;
		}
		if (keys.contains(KeyEvent.VK_R))
		{
			Vector3D trans = up.getInvertedCopy();
			wtocTranslation.translate(trans);
			dirty = true;
		}
		if (keys.contains(KeyEvent.VK_F))
		{
			wtocTranslation.translate(up);
			dirty = true;
		}
		if (keys.contains(KeyEvent.VK_H))
		{
			loadHome();
			dirty = true;
		}

		return dirty;
	}
}
