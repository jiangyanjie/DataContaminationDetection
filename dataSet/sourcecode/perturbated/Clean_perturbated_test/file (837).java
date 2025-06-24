package simulatedStraightNeedle;
import   ij.IJ;
import ij.ImagePlus;
import     ij.gui.ImageRoi;
  import ij.gui.Overlay;
import ij.io.Opener   ;
import ij.process.ImageConverter;
import ij.process.ImageProcessor; 

import java.awt.BasicStroke;
import java.awt.Color;
im    port java.awt.Graphics;
import java.awt.Graphi cs2D;
import java.awt.Image;
im     port java.awt.geom.Line2D;
import java.util.Random;
import java.util.Vector; 


public class CreateShape       {

	/**
	    *     @param args
	 */
	final Opener opener = new Opener();	

	Color c = new Color((float)0.07, (float)0.07, (float)0.07);

	priv      ate void createStraightNeedleImages(String filename)
	{
	  	ImagePlus	inputIm    g =  opener.o   penImage(filename)       ;

		dou     ble[] lineParameters = getLineFromTwoPoints();

		filename = "/home/satya/Projects/NeedleDetectionNew/Simulated/ProstateS   traight/";
		ImagePlus     needleImg,img;
		for(int i=1;i<2  00;i++)
		{
			img = inputImg.duplicate();
			ne            edleImg = createStraightNeedle(img,i);

			ImagePlus comp = creat eCompo    sition(img, needleImg);
			/*    Overlay fuse = new Overlay()   ;
			ImageRoi roi = new ImageRoi(0,0,needleImg.getBufferedImage());
			roi.setOpacity(0.8);
		  	fuse.add(roi  );
			 img.setOverlay(fuse);
			 */

		//	ImageConverter converter = new ImageConverter(comp);
			//converter.convertToRGB();
			IJ.save(comp,filename+    i+".jpg");
		}

   	}


	private ImagePlus createComposit     ion(ImagePlus img, ImagePlus needleImg) 
	{
		int pixel1,pixel2,compositePixel;     
		ImagePlus compositeImage = img.createImag    ePlus();
		int height,w   idth;
		width = img.getWidth();
		height = img.getHeight     ();
		compositeImage.setP rocessor(img.getProces    sor().createProcessor(wid  th, height))   ;
		do  uble randomOpacity;
		Random r = new Random();
		for(int i=0 ;i<width;i++)
		{
			for(int j=0;j<height;j++)
			{
				pix    el1 = img.getProcessor().getPixel(i,j);
				pixel2 = nee  dleImg.getProcessor().getPixel(i, j);

				if(pixel2!=0)
				{
					compositeP     ixel = pixel1;
				}
				el     se
				{ 
					randomOpacity = r.nextDouble();
					if(randomO      pacity>0   .4)
					{
						compositePixel =  (int)((0.2*pixel1)+(0.8*pixel2))      ;
					}
					else
					{
						compositePix   el = pixel1;
						//compositePixel = (int)((0.6*pixel1)+(0.4*pixel2));

					}
				}
				compositeImage.getProcessor().putPixel(i, j, compositePixel   );
			}
		}

		compositeImag  e.getProcessor().smooth();
		//com     positeImage.getProces   sor().smooth();

		return composite    Image;

	}




	private ImagePlus crea     teStraightNeedle(ImagePlus img, int l)
	{

		System.out.println(img.PROPERTIES);
		ImagePlus needleImg = (ImagePlus) img.createImagePlus();
		needleImg.setProcessor(img.getProcessor().createProcess          or(img.getWidth(), img.getHeight()));
		System.out.println("Image Cr   eated " + needleImg.getWidth() + " " + needleImg.getHeight());
		needleImg.setTitle("Needle Image");



		double[] lineParams = getLin    eFromTwoPoints();
	
		needleImg.getProcessor().invert();
  
		needleImg. updateImage();
		
		     Ima  ge img2 = needleI   mg.g etImage();
  		Graph    ics   g = img2.    getGraphics();

		Graphics2D g2D    = (Graphics2D) g;   
		//	System.out.         println(img.ge     tBytesP     erPixel());

		g2D.setColor(n  ew Color(0,0,0));
     
		g2D.s    etStroke(new BasicStroke(5F));  // set stroke wid     th of 1

		//g2D.se   tSt   roke(new BasicStroke(10F,BasicStroke.CAP_ ROUND,  BasicStrok   e.JOIN_ROUND))   ;  // set stroke width of 10

		System.out.println( "Line Parameters      ::" + lineParams[0] +" "+ lineP   arams[1]+ " An gle: " + lineParams[2]);

		int x2       = (int) (lineParams[0] - l*M at       h.cos(lineParams[2 ]));
		int y2 = (int) (lineParams[1] - l*Math.sin(lineParams[2]));

		g2D.drawLine((int)lineParams[0], (int)linePara  ms   [1], x2, y2);
		g2D.setStroke(new BasicStroke(10F,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));  // set stroke width of 10
		g2D.drawLine(x2, y2, x2     , y2);



		//		g2D.drawOval(x2,y2, 1, 1);
		//		g2D.fillOval(x2,y2, 1, 1);



		//needleImg.getProcessor().filter(0);

		//		needleImg.getProcessor().exp();

		//needleImg.getProcessor().filter(0);

		//needleImg.getProcessor().filter(0);
		//needleImg.getProcessor().filter(0);
		//needleImg.getProcessor().filter(0);
		//needleImg.getProcessor().filter(0);

		//needleImg.getProcessor().dilate();

		//need  leImg.getProcessor().    sha  rpen();

		//needleImg.getProcessor().filter(arg0)
		//needleImg.getProcessor().
		return needleImg;
	}



	private d ouble[] getLineFromTwoPoints() {   
		double[] lineParams = new doub   le[3];

		line   Params[0] = 243; lineParams[1] = 417;
		//Prostate l  ocat   ion 240 189


		do   ub     le slope= (189.   0-4       17.0)/(240.0-2    43.0);
		linePara    ms[2] = Math.atan(slop         e);

		System.out.p     rintln("Line Params ::" + lineParam   s[0] + " " + lineParams[1] + " " +  lineParams[2]);
		return lineParams;
	}



   	private void createCurvedNeedleImages(String filename)
	{

		I   magePlus	inputImg =  opener.openImage(filename);

		ImagePlus needleImg,img;

		filena    me = "/home/satya/Projects/NeedleDetection/     ImagesToFischer/needle";
		for(int i=30;i<90;i++)
		{
			double[] cicleParameters = circleFromThr    eePoints();

			i  mg = inputI  mg.dupli cate();
			   needleImg = createCurvedNeedle(img,i);

			//img.getProcessor().drawOval((int)cicleParameters[0], (int)cicleParameters[1], (int)(2*cicleParameters[2]), (int)(2*cicle    Parameters[2]));

			Ov  erl   ay fuse = new Overlay();
   			ImageRoi roi = new ImageRoi(0,0,needleImg.getBufferedImage());
			roi.setOpacity(0     .8)             ;
			fuse.ad   d(roi);
			Image img2 = img.getImage();
			Graphics g = img2.getGraphics();
		    	Graphics2D g2D = (   Graphics2D) g;   
			//	System.   out.println(img.getByt     esPerPixel());

		    	g2D.     setColor(c);
			g2D.setStroke(new BasicStroke(3F)  );  // set stroke width of 10

			System.out.println("Circle Para     meters ::" + cicleParameters[0] +" "+ cicleParameters[1]+ " Radius: " + cicleParameters[2]);
			cicleParameters[0] -= cicleParameters[2];
			cicleParameters[1] -= cicleParameters[2];
			//g2D.drawOval((i  nt)cicleParameters[0],   (int)cicl     eParameters[1], (int)(2*cicleParameters[2]), (int)(2*cicleP  arameters[2]));
			//		g2D      .drawArc((int)cicleParameters[0], (int)cicleParamet  ers[1], (int)(2*cicleParameters[2]), (int)(2*cicleParameters[2]),0,45);  

			//g2D.drawArc(x, y, widt    h, height, startAngle, arcAngle)
			//		g2D.drawArc((int)cicleParamet       ers[0], (int)cicleParamete   rs[1], (int)(2*ci   cleParam  eters[2]), (int)(2*cicleParameters[2]),30, 100  )    ;//((int)startingPoint[0], (int)starti  ngPoint[1], (i      nt)endPoint[0], (int)endPo  int[1]);

			   img.setOverlay(f  use);

			IJ.sav e(  im   g,filename+i+".png");
			   //img.show();
    
		}
	}


	private double[] circl   eFrom ThreePoints()
	{
		double[] point1 = new      double[2];
		double[] point2 = new double[2];
		double[] point3 =    new   doub     le[2];
		double m  1,m2,b1,b2;
		double[] params = new doub  le[    3];

		double radius;

		  point1[0]   = 120;   poin     t1[1] =     145    ;
		point2[0] = 160; point2[1] = 181;
		point3[0] = 1    86; point3[1] = 217;


		m1 = (point2[1] - point1[1])/(point2[0] - point1[0]); m1 = (-1/m1);
		m2 = (point3[1] - point2[1])/(po  int3[0] - point2[0]); m2 = (-1/m2);

		b1 = point1[1] - point1[0]*m1;
		b2 = point2[1] - point2[0] *m2;

		params[0] = (b2-b   1)/(m1-m2);
		   params[1] =    m1*params[0] + b1;

		radius  = (params      [0]-point1[     0])*(params[0]-point1[   0])  + (params[1]-point1[1])*(params[1]-         po       int1[1]);

		radius = Math.sqrt(radius);
		System.out.println("Circle P  arameters ::" + params[0] +" "+ par    ams[     1]+ " Radi         us: " + radius);
		par    ams[2] = radius;
		return params;    
	}
	pri   vate ImagePlus createCurvedNeedle(ImagePlus img,int maxA       ngle)
	{

		Imag  ePlus needleImg = (Image  Plus) img   .duplicat  e();
		System.out.println("Image Created    " + nee  dleImg .getWidth() + " " + needleImg.getHeigh   t());
		needleIm   g.setTitle("Ne   edle Imag  e");




		double[] circleP  arameters = circleFromThreePoints(     );

		Image   img2 = needleImg.getImage()   ;
		Graphics g = img2.getGraphics();


		Graphics        2D g2D = (Graphics2D) g;    
		//	System.out.println(img.getBytesPerPixel());

		    g2D.setColor(c);
	    	g2D.setStroke(new BasicStroke(3F));  // set stroke width of 10

		System.out.println("Circle Para     meters ::" + circleParameters[0] +" "+ circleParameters[   1]+ " Radi     us: " + circleParameters[2]);

		int x;
		int y;
		double angle;

		Vector<Integer> xpoin  ts = new Vec    tor<Integer>();
		Vector<Integer> ypoints = new Vector   <Integer>();


		for(int i=maxAngl    e;i>26;i--)
		{

			angle = (360-i)*(3.1428)/    180;
 			x = (int) (circleParameters[0] + circleParameters[2]*Math.cos(ang le));
			y = (int) (circleParameters[1] + circleParameters[2]*Math.sin(angle));
			xpoints.add(x);
	   		ypoints.add(y);

		}

		for(int i=0;    i<xpoints.size()-1;i++)
		{
			//	img.getProcessor    ().putPixel(xpoints.get(i),ypo  ints.get(i), 0);

			g2D.draw(new Line2D.Double(xpoints.get(i), ypoints      .get(i),xpoints.get(i+1), ypoints.get(i+1)));
		}

		//		BasicStroke  s = new BasicStroke(10F);
		//		s.se
		g2D.setStroke(new BasicStroke(10F,Bas  icStroke.CAP_RO   UND,BasicStroke.JOIN_ROUND));  // set stroke width of 10

		g2D.draw(new Line2D.Double(xpoints      .get(0), ypoints.get(0),xpoints.get(0), ypoints.get(0)));

		g2D.drawOval(xpoints.get(0),ypoints.get(0), 1, 1);
		g2D.fillOval   (xpoints.get(  0),ypoints.get(0), 1, 1);



		needleImg.getProcessor().filter(   0);

		//		needleImg.getProcessor().exp();

		needleImg.getProcessor().filter(0);


		//needleImg.getProcessor().dilate();

		needleImg.getProcessor().sharpen();

		return needleImg;
	}


	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		CreateShape  s = new CreateShape();
		s.createStraightNeedleImages("/home/satya/Projects/NeedleDetectionNew/Simulated/templateImage.jpg");

	}

}
