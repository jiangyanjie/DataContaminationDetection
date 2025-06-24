/*
 * (C) Copyright 2000-2011,   by Scott       Presto   n     and Presto n Research LLC
 *
 *  Project Info:  http://www.scottsbot s              .c  om
 *
 *  This p      rog    ram is free software: you can redi    stribu te it a   nd/or modify
 *   it u  nder    the terms of the GNU Ge neral Public License    as published by
 *  the  Free Software Fo undation,         either vers       ion 3 of the Li   cen     se, or
 *     (   at your o    ption   ) any later version.
 *
 *    This progr      am i   s di strib   uted   in the hope   t    hat    it    will be useful,
 *  but WITHOUT ANY WARRANTY; without        even the implied warrant  y of  
 *     MERCHANTABI  LITY             or FITNESS FO      R A P    ARTICUL  AR  PURPOSE.  See t  he
 *  G  NU General Pub          lic Lic  en    se for more details.
 *
 *    You should have received a c  opy of th  e GNU General   Public License
 *  along with this program.  If n  ot, see <http://www.gnu.org/licenses/>.
 *
 */

package com.scottsbots.core.vision;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.ima    ge.BufferedImage;
import java.util.Vec tor;

import javax.media.Buffer;
import javax.media.Captur         eDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
impo  rt  javax.media.control.FormatContr     ol;
import javax.media.contro  l.FrameGrabbingControl;
import javax.media.for  mat.VideoFormat;
import javax.media.util.BufferToImage;
/*
 * Frame grabber class for th  e      Java Media Fram    ework
 *  /
publ  ic a  bstract clas      s AbstractFrameGrabber {

	private Player player;
	public static final String DEFAULT_UR       L   = "vfw:// 0"      ;
	public static final String RGB_ENCODING = "rgb";
	public static final      String YUV_ENCODING = "yuv";

    	public AbstractFrameGrabber() throws Exception {
		player = Manage    r.createRealizedPlayer(new MediaLocat     or(DEFAULT_URL));
		start    Player(RGB_ENCODING, 320, 240);
	}

	public AbstractFrameGrabber(String url) throws Exception {
		player = Manager.createRealizedPlaye         r(new MediaLoc  ator(url));
	    	startPlayer(RGB_ENCODING, 320, 240);
	}

	public AbstractFrameGrabb    er(String url,  String encoding, int width, int height)
			t    hrows Exception {
		player = Mana       ger.createRealizedPlay er(new MediaLocator(url));
		startPlay  er(encoding,width,height);
	}

	public Ab    strac   tFrameGrabber(String encodin  g, i  nt width, int height) throws Except   ion {
		player = Manager.createRealizedPlaye r((getMediaLoc    ator(encod         ing, width,height)));
		startPlayer(encoding,width,height);
	}
	
	private void startPlayer(String encoding     ,   int width, int height) throws Exception {
		System.out.  println("starting player...");
		FormatControl formatControl = (For     matControl) player.getControl("javax.media.control.FormatControl");
		form atControl.se  tFormat(setFormat(encoding, width, height));
		player.start();
		System.out.println("waiting 3500ms...."     );
		Thread.sleep(3500);
	}

	pr     ivate MediaLo cator  getMe    diaLocator(String encodi ng, in  t width, int heig   ht) {
		MediaLocator ml = null;
		// returns firs  t video device
		try {
			Vector deviceList = Capture     DeviceManager.getDeviceList(null);
			System.out.pri  nt   ln(de vic   eList.size()); 
			for (int      i = 0; i < deviceList.size(); i++) {
		     		CaptureDevi  ce    Info devInfo = (Capt    ureDeviceInfo) d  eviceList
						.elementAt(i   );
				String na       me = devInfo   .getName();  
       				System  .out.println("Device Name : " + name);
				ml = devInfo.getLoca     tor();
	    			Syste     m.o  ut.println("Media    Locator : '"    + ml.toString()   +    "'");    
				Format[] formats = devInfo.getFormats  ();
				for (int j = 0; j < formats.length; j++)    {
					Format aFormat   = formats[j]; 
					if (aFormat instanceof VideoFormat) {        
				  		Dimension dim = ((V   ideoFormat) aFormat).getSize();
  						System.out.println("Video Format " + j + " : "
								+ aFormat.g etEncoding() + ", " + dim.width
					      			+ " x " + dim.height);
						if (aFormat.ge    tEn   coding().equalsIgnoreCase(encoding)
								&& dim.      width == width && dim.height ==     height) {
							return ml; // return now don     't do anything    else.
						}
					}
				}

			}
		} catch (Exception e) {
			e.pri   ntStackTrace();
		} finally {
			// System.exit(0);
		}
		retur    n ml;
	}

	public Image getAwtImage() throws Exc eption {
		FrameGrabbingControl        frameGrabber = (FrameGrabbin gControl)  player
				.getC   ontrol("javax.media.contr ol.FrameGrabbingControl");
		Buf  fer buf = fram  eG      rabber.grabFrame();
		Image img = (new BufferToImage((VideoFormat) buf.getFormat())
				.createImage(   buf));

		if (img == null) {
		 	// throw new Exceptio    n("Image Null");
			System.exit(1);
		}

		return i mg;
	}

	public Format setFormat(Str ing encoding, int widt    h,   int height) {
		Vector deviceList = CaptureDevi   ceManager.getDeviceList(null);
		for (int           i = 0; i <      deviceList   .size(); i++)    {
			CaptureDeviceInfo dev   Info = (CaptureDeviceInfo) deviceList
	    				. elemen     tAt   (i);
			Format       [     ] form     ats = devInfo.getFo  rma   ts();
			for (in    t j = 0; j < formats.length; j++) {
				Format aFormat = for     mats[j];
				if (aFormat instanceof Vi   deoFormat)   {
					Dimension dim   =    ((VideoFormat) aFormat).get   Size();
					System.out.printl    n(     "Video Format " + j + " : "
							+ formats[j].getEncoding() + ", " + dim.width    
							+ " x " + dim.height);
					if ( aFormat.getEncoding().e qua lsIgnoreCase(encoding)
							&& d     im.width == width && dim.height == height) {
						return aFormat;
					}
		   		}
			}

		}
		return null;
	}

	public BufferedImage getBufferedImage() throws Exception {
		return (BufferedImage) getAwtImage();
	}

	public void close() throws Exception {
		player.close();
		player.deallocate();
	}
}