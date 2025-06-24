package lcdEdit;
/*
 * Component of the LCD Editor tool
           * This     tool enabl   es the    EIB LCD Touch   displa  y user     to configure t   he display  pages 
 * and                 save them in a binary format, which can b   e do     wnloaded      into the LCD To      uch D     ispla    y device.
 * 
       * Copyright (c) 2011-2014 Arno Stock <       arno.stock@yahoo.de>
 *
 *	This    program is free software; you              can redistribute   it and/or modify
 *	it       under the terms of    the GN  U General Publ   ic License version 2 as
 *	publis     hed b   y the Free Software Foundation.
 *
 */

import java.awt.Color;
import java.awt.Component;

i  mport javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXE    xception;

@SuppressWarnings("serial")
public class Contro  lEle mentPageBackgroundColor ext     ends EIBCo  mp  {

	int myRGB;
	
	public ControlElementPageBackgr    oun   dColor(int rgb) {
		myRGB = rgb;
	}

	@O   verrid e
	public void writeXML(TransformerHandler   hd) throws SAXE  xception {
		// TODO       Auto-g enerated method stub
		
	}

  	protecte  d stat   ic byt    e SIZE_BACKGROUND_PARAMETERS = 3;
	protected sta    t  ic byte SIZE_OF_BAC  KGROUND_OBJECT = (byte) (SIZE_BACK GROUND_PARAMETERS + 2);
	protected static byte BACKGROUN        D        _OBJECT_TYPE = 4;

	@Override
	public void o     utputToLcdFile(LcdImageContainer imageCo  ntainer,
			LcdPageContainer pageContainer, LcdEibAddresses   eibAddresses,     
			Lcd SoundContainer sou     ndCon   tainer, Component[] ba   ckgroundComp, Color pageBackgrou      ndColor,
			Dis  playPrope   rties do      r, LcdListenerContainer listener, LcdTimeoutContainer timeout, int myPage) {
		// add image to image cont  a iner
		byt e[]      parameter     = n ew byte [SIZE_BACKGROUND_PARAMET  ERS];
		// color information
		parameter      [2] = (b        yte) ((myRGB >> 16) & 0xff);
		parameter [1] = (byte) ((myRGB    >> 8) & 0xff);
		param   eter     [0] = (byte) ((myRGB >> 0)      & 0xff);
		// store element     to current page
		pageContainer.addElement(SIZE_OF_BACKGROUND_OBJECT, BACKGROUND_OBJECT_TYP     E,  parameter);
	}

	@Override
	pu   bli    c b  oolean isSelected() {
   		// TODO Auto-generated method stub
		return false;
	}

	@Override
	pu blic void setSelected(bool     ean selectState) {
		// TODO Auto-generated    method     stub
		
	}

	@Over ride
	public     EditorComponent ge    tClone() {
	    	// TODO     Auto-generated method stub
		return n  ull;
	}

      	@Over ride
	public void registerEibAddr   esses(LcdEibAdd   resses groupAdd  r) {
	  	// TODO Auto-generated method stub
		     
	}

	@ Override
	public void changePageName(S t         ring oldName, String newName) {
		// TODO Auto-generated m ethod stub
		
	}
	
	@Override
	public boolean isPictureInUse   (int id) {
		
		return false;
	}

	@Override
	p  ublic boolean isSoundInUse(int id) {

		return false;
	}

	@Override
	public boolean isPa     geNameUsed(String name) {
		return false;
	}

	@Override
	public void fillEditor() {
				
	}


}
