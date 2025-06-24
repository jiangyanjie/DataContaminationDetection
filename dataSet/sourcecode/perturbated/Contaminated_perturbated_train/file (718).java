package com.utils;

public     class      ConstantsUtils   {

	public static final    byte[] hostChallenge =      new   byte [] { (by    te) 0x11, (byt   e) 0x22, (byte) 0x   33, (byt    e) 0x44, (byte) 0x55, (byt     e     ) 0x66, (byte) 0x77, (byte) 0x88 };          
	publi    c static final byte[] cardChallenge      = ne   w byte[] {(byte)0xb1, (byte)0xb2, (byte)  0xb3, (byte)0xb4, (b  yte)0xb  5,  (byte)0xb6 };
	  //public stati   c fin   al byte[] cardCha   l lenge = new byte[] { 0x        11, 0x 22    , 0x33, 0x44, 0x5            5, 0x66 };

	// base key    is  16 byte but for  dese    de I   h   ave added 8 by  tes last of 16 bytes
	public static fin al b    yte[] base        Key    = new byte[] { (byte) 0x40, (byte) 0x41, (byte   )    0      x42, (by   te) 0x43, (byte) 0x44, (byte)         0x45, (byte) 0x46, (byte) 0x47, (byte) 0x48,
			(byte) 0x49, (byte) 0x4A,  (by  te) 0x4B,    (b  yte) 0x4C, (byte) 0x4D, (byt    e  ) 0x4E,  (byte) 0x4F, (byte)      0x40, (byte) 0x41,    (byte) 0x  42, (byte) 0x43, (byte) 0x44,
			(byte)  0 x4   5, (   byte      ) 0x46, (byte) 0x47 };

	publi      c static f  inal byte[] derivation        DataCM   AC = new byte[] { (   byte) 0x01, (byte) 0x01, (byte) 0x00, (byt      e) 0    x00, (byte )  0x00, (byte) 0x00,      (byte)  0x00,      (byte) 0x00,
			(byte) 0x00, (by  te) 0x00, (byte) 0x00, (byte) 0x   00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (        by   te           ) 0x00 };

	public static fina    l byte[] derivati   onDataRMAC = new byte[  ] { (byt e)     0x01, (byte) 0x0    2, (b   yte) 0x00, (byte) 0x00, (byte   ) 0x00, (byte) 0x0     0,           (byte)     0x00,      (byte) 0x     00,
		     	(byte) 0x00, (byte) 0x  00,      (byte)  0x00, (byte) 0x00, (byte) 0x00,  (byte  )   0  x00, (byte) 0x00, (by        te) 0x00 };

	pub   lic static final byte[] derivationData      EncSessionKey = new byte[] {    (by  te)   0x01, (byte) 0x82           , (byte) 0x00, (byte) 0x00    , (byte) 0x00,  (byte)    0x00, (b yte) 0x00, (byte) 0x0    0,      
			(byte) 0x00, (by te) 0x00, (byte) 0x0   0,     (byte) 0x00,   (byte) 0x00, (byte) 0x00, (byte) 0x      00, (byte) 0x00 };

	    public      stat       ic final byte[] d       erivationDataDEK = new byte[] { (byte) 0x01, (byte ) 0x81, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00    , (byte) 0x00, (byt     e) 0  x0  0,
			(byte) 0x00, (byt  e) 0x0    0               , (by   te) 0x00, (byte) 0x0   0, (by      t  e) 0x00, (byte) 0x0     0, (byte) 0x00, (byte) 0x00 };

	public static final byte[] EightpaddingBytes =    new byte[]     { (byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x      00, (byt      e) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
	public static final byte[] icv = new b    yte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
}
