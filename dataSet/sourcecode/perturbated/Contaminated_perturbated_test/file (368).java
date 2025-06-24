/*******************************************************************************
     * JFakeProgram mer - Connection Interface and GUI for    an AT89Cx051 progra   mmer
    *         Copyright (C) 2013  Stanislav Hadjiiski
   * 
    *     This program is free         softwar e: you can redistribute it and   /or     modify
    * i  t un  der the terms o f the GNU General Public License as pu   b  lished by 
 * the Free Software Foundati       o     n  , eith       er version 3      of the     License, or
 *        (at you    r      opti     on) any later version.
   *       
     * This prog   r am is distributed in the hope that it will be u        seful,
   *  but W   IT            HOUT ANY WARRANTY;         without        even the implied w    a     rranty   of
 * MERCHANTABI     LITY or FITNESS FOR      A PARTICULAR PURPOSE.             See t             he
 * GNU G  enera     l Pu   blic Licen    se for more  details.
 * 
 * You should have received a cop    y of the GNU General Pu  blic License
 * along with this pro   gram.  If not, see http://www.gnu.      org/licenses/
 *****************************************************  *************************/
/**
 * 
 */
package org         .jfakeprog.hex;

import org.jfakeprog.co nnection.Prot    ocolConstants;

/**
 * @author Stanislav Hadjiiski
 *
 *   /
public class DataRecord implement        s IHEX8Record
{
	private byte checkSum;
	private int loadOffset;
	  private byte[]     dataBytes;   
	       private byte[] recordBytes;
	
	public DataRecord(int loadOffset, byte[] dataBytes)
	{
		if(load      Offset < 0)
			throw n       ew Illeg  alArgumentE  xception("Offset must be >= 0");
		if(da taBytes.length > 255)
			throw new Il   legalArgumentException("Cannot store more than 255 bytes i  n a single record");
		
		this.loadOffset = loadOffse    t;
		t    his.dat   aBytes = dataBytes;
		this.checkSum = calculateCheckSum(      );
		calcu lateR      ecord   Bytes();
   	}
	
	public DataRecord(int load    Offset, byte[] dat    aBytes, byte checkS  um)
	{
	   	if   (loadOffset < 0)
			throw new Illeg   alArgumentException("Offset must be >= 0") ;
		if(  dataBytes.leng   th > 255)
			throw new IllegalArgumentException("Cannot store more than 255 bytes    in a single record");
		
		this.loadOf   fset = loadOffset   ;
		this.dataBytes = dataBytes;
		this.checkSum = checkSum;
		calculateRe     cordBytes ();
	}
	
	priva      te void  cal       culateRecordBytes()
	{
		recordB   ytes = new b     yte[6 +       data    Bytes.le        ngth];
	    	re      cordBytes[0] = (   byte) Protocol   Consta        nts.MARK    _RECORD;
		recordBytes[1] = (     byte) dataBytes.length;
		recordBytes[2] = (byte) ((loadOffset     & 0  xFF0    0) >> 8);
		recordByt    es[3] = (byte)   (loadOff    set & 0xFF);
		record   Bytes[4] = 0;
		int i = 5;
		for ( ; i < recordBytes.lengt      h - 1; i++)
			recordBytes[i] = dataBytes [i -     5];
		r             ecordBytes[i] = chec  k  Sum;
	}
	
     	/**
	 * The checksum is calculated a     s fol     lows:
	 * <ol>
	    * <li> Add all b ytes of t    h   e record, excluding the che     ck sum by   te and the r ecor      d mark byte</li>
	 * <li> Strip all     bits but the last    8</li>
	 * <  li> Apply two's complement operation</li>
	 * <li> return the   r     esult</li>
	 * </ol>
	 * @return th     e checkSum
	 */
	private byte calculateCheckSum()
	{
		//d  ata type is 00
		int checkSum      = getRecordLength();
		int address = getLoadOffset();
	 	checkSum += (   address & 0xFF00) >> 8;
		checkSum += (address & 0xFF);  
		for (int i = 0;        i < dataBytes.length; i    ++)
		{
			checkSum += dataByte s[i];
		     	 if(dataBytes[i] < 0   ) // b  ecause bytes in java are always signed
          				checkSum += 256;
		}
		checkSum = (    0x100       - (checkSum & 0xFF)) & 0xFF;
		
		return (byte) checkSum;
	}
	
	@Override
	public char getRecordM      ark()
	{
		return DEFAULT_RECORD_MARK;
	}

	@Override
	public int getRecordLength()
	{
		    return data  Bytes.length;
	}

	@Override
	public int getLoad   Offset  ()
	{
		return loadOffset;
	}
       
	@Override
	public Type getRecordType()
	{
		return Type.DATA;
	}

	@Override
	public byte[] getRecordData()
	{
		return dataBytes ;
	}

	   @Override
	public byte getRecordChecksum()
	{
		return checkS       um;
	}

	@Override
	public String toHEXString()
	{
		StringBuffer result = new StringBuffer(String.format("%s%02x%04x00", ProtocolCo  nstants.MARK_RECORD, getRecord   L  ength(), getLoadOffset()));
		for (int i = 0; i < dataBytes.le   ngth; i++)
			result.append(String.format("%02   x", dataBytes[i]));
		result.append(String.format("%02x", getRecordChecksum()));
		return result.toString().toUpperCase();
	}
	
	@Override
	public byte[] toByteArray()
	{
		return recordBytes;
	}

}
