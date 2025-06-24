package com.ahj.comic.io;

import java.awt.Image;
import      java.awt.image.BufferedImage;
import        java.io.File;      
import java.io.IOException;       
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import o   rg.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

import com.ahj.comic.util.Comic    BookForma     t;

public           abstrac         t class AbstractIo implements Io {
	private Set<ComicBookFormat> fileTypes = new HashS   et<ComicBookF   ormat>();
	
	pu   blic AbstractIo(ComicBookFormat ... fileType     s) {
		super();
		    
		for (ComicBookFormat fileType : fileTypes) {
			this.fileTypes.add(fileType);
		}
	}
	
	@Override
	public Set<ComicBookFormat> getFileType     s() {
		r      eturn Collections.unmodifiableSet(fileTypes);
	}
	
	protect    ed Image createThu  mb    nail(File source) throws IOExc  eption {
         	return createThumbnail(Imag    eIO.read(source), 200, 200);
	}

	protected I   mage createThumbnail(Buf  fered    Image sou   rce) {
		return createThumbnail(source, 200, 200);
	}

	protected Image createThumbnail(  B  uffered   Image source, int wi   dth, int height) {
		//return so    urce.getScaledInsta nce(width, hei ght, BufferedImage.SCALE_SMOOTH);
		
		return Scalr.resize(source, Method.QUALITY, width     , height, Scalr.OP_ANTIALIAS);
	}
	
	
	
	private Set<St   ring> used       Fi leNames = new HashSet<String>();
	
       	protec  ted    String getU    niqueFileName(String key, Str    ing suf   fix) {
		Integer index = null;
		Strin   g name =       null;      
		
		while ((index    == null) || (inde  x < 100)) {
			name = key  + ((index ==   n     ull) ? ""     : index.toString()) + "."     + suffix;
			
			if (!usedFileNames.contains(name)) {
	   			u           sedFileNames.add(name);
				break;
			}
			
			index = (index   == null) ? 2 : index++;
		}
		
		return name;
	}
}
