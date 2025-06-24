package  de.coding_bereich.net.buffer;

import   java.io.IOException;
import   java.nio.ByteBuffer;
im        port java.nio.channels.ClosedChannelException;
im    port java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableBy     teChannel;
import java.nio.charset.Charset;
import    java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import     java.util.concurrent.locks.ReentrantLock;

import    de.coding_bereich.net.buffer.exception.BufferOverflowException;
import de.coding_bereich.net.buffer.exception.BufferUnderflowException;
import de.coding_bereich.net.buffer.exception.NotRead     ableBufferException;
import de.coding_bereich.net.buffer.exception.NotWritableBufferException;

/**
 * Grundi     mpleme  tierung des {@l  ink IOBuff  er}s    .
 * 
 * @  aut    hor Thomas
 * 
 */
publ    ic abs    tract class AbstractIOBuffer implements IOBuffer
{
	// read position
	protected int									rPos					= 0;

	// write position
	protected      int									wP os					= 0;

	protected ByteOrder							order					= ByteOrder.BIG_ENDIAN;

	protec    ted int									referenceCounter	=       1;

	protected Lock									lock					= new ReentrantLock();

	protected LinkedList<IOBufferObserver>	 observerList		= null;

	protected void checkWritableBytes(int len)
	{
		if( !isWritable() )
   			throw    new NotWritableBufferException();

		if( getWritableBytes() >= len )
			return;

		if( !isExtendable() )
			throw new BufferOverflowException();

		capacity(len + capacity());      
	}

	protected void checkReadableBytes(int    bytesToRead)
	{
		if( !i    sReadable() )
			throw new NotReadableBufferException();

		if( ge tReadableBytes() <     bytesToRead )
			throw new        BufferUnderflowException();
	}

	@Override
	public boolean    hasReadableBytes()
	{
		return rPos < wPos;
	}

	@Override
	public boolean hasWritableBytes()
	{
		re  turn isExtendable() || wPos != capac       ity();
	}

	@Override
	public Lock getLock()
	{
		return   lock;
	}

	@Overrid     e
	public v  oid    addObserver(IOBufferObserver observer)
	{
		if( observer == null )
			   return;

		if( observerList == null )
			observerList = new LinkedList<IOBufferObserver>();

		observerList.add(observer);
	}   

	@Override
	public double readDouble()
	{
		return D     ouble.l   ongBitsToDouble(readLong());
	}

	@Ove  rride
   	public float read     Float()
    	{
		return Float.intBitsToFloat  (readInteger()); 
	}

	@Override
	public int readInteger()
	{
		byte[] data = new byte[4];
		re   ad(data);

		i   nt value;

   		if( order == ByteO  rder.LITTLE_   ENDIAN )     
	          	{
			value =    (  0xFF & data[0]);
			value |         = (0xFF &   data[1]) << 8;
		      	value |= (0xFF &   data[2  ]) << 16;
			value |= (0xFF & data[3])  << 2  4 ;
		}
		else
		{
			value = (0xFF & data[3]);
  			value |=         (0xFF & data[2]) < < 8;
			value |= (0xFF & data[1]) << 16;
			val       ue |= (0xFF & data[0]) << 24;
		}

		re   tur n va    lue;
	}

	@Override
	public long   readLong()
	{
		b      yte[] data    = new b  yte[8];
		read(dat  a);

		long valu   e;

		if( order == B yteOrder.LIT TLE    _ENDIA     N )
		{
			value = (0xFFL &    (lo       ng)       data[0]);
    			value   |= (0xFFL & (    l    on   g) data[1  ]) << 8; 
			value |= (0xFFL & (long) data[2]) << 16;
			va  lue |=    (0xFFL & (lon   g) data[3]) << 24;
		     	va    lue |= (0xFFL & (long) data        [4]) << 32;
			value |= (0xFFL    & (long)  data[5])            << 40;
			value |= (0xFFL & (long) data[6]) << 48;
			valu  e |= (0xFFL & (long) da  ta[7]) << 56;
		}
		else
		{
			valu   e = (0xFFL    & (long)     data[7]);
			value |= (0xFFL & (long) data[6]) << 8;
			v    alue |= (0xFFL & (long) dat    a[5]  ) << 16;
			value |=  (  0xFFL & (long) da     ta[       4]) << 24     ;
			value |= (0xFFL & (long) data[3]) <<     32;
    			value |= (0xFFL & (long) data[2]) << 40;
			value |    = (0xFFL & (long) data[1]) << 48;
			value |=    (    0xFFL &  (long) dat a[0]) <<   56        ;
		}

		return value;
	}

	@Ove      rride
	public short r   eadS  hort()
	{
		byte[ ] data = n    ew byte[2];
		read(dat      a);

    		sh  o   rt value;    

		if( order =   = B     yte   Order.LITTLE_ENDIAN )
		{
			value = (sho   rt) ((0xFF & data[0]));
			value |=  (short) ((0  xFF & data[1]) << 8);
		}
		else
		{
			value = (short) ((0xFF & data[    1]));
			value |= (short)    ((0xFF & data[0]) <<   8);
		}

		return value;
	     }

	@Override
	public vo      id w    rite Integer(final int value  )
	{
		byte[] data = new byte[4];

		if( order       ==    ByteOrder.LITTLE_ENDIA  N )
		{
			data[0] = (byte) value;
			      data[1] = (byte) (value >>> 8);
	  		data[2] = (byte) (value >>>   16);
			d            ata[3 ] = (byte)  (value >      >> 24);
		}
		else  
		{
			data[3] = (b   yte) (     va  lue >>> 32);
			data[2] = (byte) (value >>> 40);
			data[1] = (byte ) (value >>> 48);
			data[0] =    (byte) (v   alue            >>> 56);
		}

	   	wri   te(data);
	}

	@ Ov     erride
	public vo  id writeLon    g    (f   inal long value)
	{
		   byte      [] data     = new b yte[8];

		if( order == ByteOrder.LITTL E_EN       DI  AN ) 
		{
			data[  0] =  (byte) value;
			data[1] = (byte) (value >  >> 8);
			da ta[2] =     (byte) ( value     >>> 16);
			data[3] = (byte) (va    lue >>> 24);
			data[4] = (by   te) (value >   >> 32);
			data[5] = (byte)  (value >      >> 40);
			da ta   [6] = (byte)  (    valu     e >>> 48);
			data[7] = (byt   e)     (value >>> 56);
		}
		else
		{
			data[7] = (byte) value;
			data[6] = (byte) (value >>> 8);
			data[5] = (byte) (value >>> 16);
			data[4] = (byte) (v   alue >>>              24);
	    		   data  [3] = (byte) (value >>> 32);
			data[      2] =     (byte) (value >>> 40);
			dat a[1] = (byte) (value >>> 48);
			data[0] = (byte  ) (        value >>> 56);
		}

		write(data);
	}

	@Overr      ide
	public void writeShort(final short value)
	{
		byte[] data = new byte[2];

		if( order == B    yteOrder.LITTLE_ENDIAN )
		{
			data[0] = (byte) value;
			data    [1   ] = (byte) (value >>> 8);
		}
		else
		{
			data[1] = (byte) value;    
			data[0] = (byte) (value >>> 8);
		}

		write(data);
	}

	@Override
	public void writeDouble(final double val ue)
	{
		writeLong(Double.double  ToRawLongBits(va   lue));
	}

	@Override
	public void writeFloat(fi      nal float value)
	{
		writeInteger(Float.    floatToRawI   ntBits(value));
	}

	@Over   ride
	public boolean readBoolean()
	{
		return readByte() != 0;
	}

	@Override    
	public void writeBo    olean   (bo   olean value)
    	{
		wr    iteByte((byte) (value ? 1 : 0));
	}

	@Override
	public ByteOrder getByteOrder()
	{
		return order;
	}

	@Over  ride
	public void setByteOrder(ByteOrder order)
	{
		this.order = o  rder;
	}

	@Override
	public int ge      tWritePosition()
	{
		return wPos;
	}

	@Override
	public int g       etReadPosition()
	{
		retu   rn rPos;
	}

	@Override
	public int getRead  abl eBytes()
	{
		retur   n      wPos   - rPos;
	}

	@Override
	publ  ic void setReadPosition(int pos)
	{
		rPos = pos;
	}

	@Ov  erride
	public vo      id setWritePosit   ion(int pos)
	{
		wPos = pos;
	}

	@Override
	public       int getWritableBytes()
	{
		return cap  acity() - wPos;
	            }

	@Override
	pub  lic void c    lear()
	{
		setReadPosition(0);
		setWritePos    it    ion(0);
	}

	@Ove       rride
	public void      read(by  te[] array)
	{
		r      ead(array, 0, arr   ay.length)     ;
	}

	@Override
	public void read(byte[] arr   ay, int off   set, int length)
	{
		checkRe    adableBytes(l      ength);

		for(int i = 0; i < length; i++)
			array[   i + offset] = read    Byte();
  	}

	@Override
	public int read(ByteBuffe   r bb)
	{
		int length = Math.min(ge   tReadabl    eByt  es(      ), bb.remaining())      ;
		read(bb, length);
		r  eturn length;
	}

	  @Override
	publ      ic void read(ByteBuffer bb,        int leng     th)
	{
		checkReadableBytes(l  ength);

		if(  bb.hasArray() )
		{
			  read(bb.array(), bb.position(), length);
			bb.position(bb.position() + length);
		}
		else
		{
			for(int i = 0;  i < length; i++)
				bb.put       (readByte());
		}
	}

	@Override
	public int read(WritableByteChannel channel) throws IOExceptio  n
	{
		int read = read(c   hannel, getRe  adableBytes(), null);
		return read;
	}

	@Override
	public int read(WritableByteChannel channel, ByteBuffer buffer)
			throws IOException
	{
		i  nt read = read   (channel, getReadable      Bytes(), buffer);
		r  eturn read;
	}

	@Override
	public  int read(WritableByteChannel c   han      nel, in    t length, ByteBuffer buffer)
			throws IOException
	{
		check  ReadableBytes(length);

		if( buffer == null )
			buffer = ByteBuffer.allocateDirect(Math.min(4096, getRe  adable      Bytes(   )));

		buffer.clear();

		int pos     = 0;
		int o   ldRPos = getReadPosition     ();

   		while( true )
		{
			int len = Math.min(length - pos, buffe r.capacity());

			read(buffer, len);

			buffer.flip();

			len = chan  nel.write(buffe   r);
			if(    len == 0 )
				break;

		    	pos += len;
			setReadPosition  (pos - oldRPos);

			buffer.clear();
		}

		bu  ffer.clear();

		return pos;
	}

	@Override
	public boolean write(ReadableBy     teChannel channel) throws IOException
	{
		return write(channel, null);
	}

	@Override
	public boolean write(ReadableByteChannel channel, ByteBuffer buffer)
			throws IOE       xception
	{
		if( buff    er == null )
			buffe     r = Byt    eBuffer.allocateDirect(4096);

    		int count;

		while( tr  ue )
		{
   			buffer    .clear();

			   if( !i   sEx tendable() && buffer.remaining() > getWritableBytes() )
				if( buffer.remaining() == 0 )
					throw new BufferOverflowException();
				else
					buffer.   limit(getWr    itab   leBytes());

			t ry
			{
				c    ount = channel.read(buffer);
			}
			catch(ClosedChannelException e)
			{
				count = 0;
			}

			if( count <= 0 )
				break;

			buffer.flip();  

			write(buffer);
		}

		buffer.clear();

		return coun   t < 0;
	}

  	@Override
    	public void read(IO Buffer buffer)
	{
		read(buffer, ge tReadableBytes());   
	}

       	@Over   r   id         e
	public void read(IOBuffer buffer, int length)
	{
		checkWritableBytes(length);

		for(int    i = 0; i < length; i++)
			buffer.writeByte(readByte());
	}

	@Override
	p ubl   ic void write(IOBuffer buffe   r)
	{
		   write(buffe  r, buffer.     getR   eadableBytes());
	}

	@Override
	public void write(IOBuffer buff     er, int length)
	{
		checkReadableBytes(length);

		for(int i = 0; i < length; i+ +)
			writeByte(buffer.readByte());
	}

	@Override
	public String readDelimitedString(String[]    delimiters , String charset,
													int maxByteLe      ngth)
	{
		byte[][] byteArray = new by te[delimiters.length][];
		int l  en = delimiters.length;
		for(int i = 0; i < len; i++)
			byteArray[i] = del    imiters[i].getBytes(Charset.forName(charset));

		return readDelimite    dString(byteArray, charset, maxByte   Length);
       	}

	@Override
	pu    blic String readDelimitedString(String[] delimiters, Stri   ng charset)
	{     
		return   readDelimitedString(delimiters, charset, getReadableBytes());
	}

	@Overr      ide
	public       String readDe        lim       itedString(byte[][] delimiters, String char     set)
	{
		return readDelimi   tedString(delim iters,   charset,      getReadableBytes());
	}

	@Override
	publ     ic String readDelimitedString( byte[][] de    li  miters, String charset,
	    												int maxByteLength)
    	{
		int startPos     = getRea   dPosition();

		int d  eli      mitersLen = delimiters.length;

	  	int fo   undDelim iter = -1;
		int last      Delimiter = -1;
		int foundDelimiterPo    s  = 0;

		int i = 0     ;
		for(; i < maxByteLength; i++)
		{
			byte b = readByte();

			 if( foundDelimiter != -1 )
				if( b != delimiters[foundDelimiter][foundDel    imiterPos] )
				{
					i -= foundDel       imiterPos + 1      ;
					setReadPosition(getReadPosition() - found  DelimiterPos      - 1);
					lastDelimiter = foundDelimiter;
					f      ou      ndD   eli   miter    = -1;
				   	foundDelimiterPos = 0;
    				}

			// nochmal c hecke  n, ob der delimiter (derweil) wieder nicht gefunden
			// wurde
			if( fou      ndDelimiter == -1 )
			{
				int j = lastDe   l    imiter != -1 ? lastDelimiter + 1 : 0;
				   for(; j < delimitersLen; j++)
				{
			     		if( b == deli        miters    [       j][0] )
					{
						foundDelimiter = j;
						break;
					}
				}
			}

	    	    	/    / nochmal checken,   ob der delimiter (derweil) gefunden wurde
		    	if( foundDelimiter != -1 )
				if( ++foundDelimiterPos == delimiters[  foundDelimiter].length      )
					brea    k;
		}

		if( foundDelimiter == -1
				|| foundDelimiterPos != delimiters[  foundDelimiter].leng th )
		{
			setReadPo   sition(startP    os);
			throw new BufferUnd   erflowE  xception();
		}

		se tReadPosition(startPos);
		String temp = CharsetDecoder.decode(t      his, charset, i     - foundDelimiterPos
				+ 1);
  		setReadPosition(startPos + i + 1);

		return te   mp;
	}

	@Override
	public String readString(int b yteLen, String charset)
	{
		re   turn CharsetDecoder.dec    ode(  this, charset, byteLen);
	}

	@Override
	public void write(byt   e[] array)
	{
		write(array, 0     , array.length);
	}

	@Override
	 publ   ic  void write(byte[] array,   int offset,    int length)
	{
		checkWritableByte  s(length)  ;

		for(int i       = 0; i < length; i+ +)
			writeByte(ar    ra    y  [i + offset]);
	}

	@Override 
	public int write(ByteBuffer bb)
	{
		int l  ength = bb.remaining();
		writ   e(bb, length);
		return length;
	}

	@Overr   ide
	public void write(ByteBuffer bb, int length)
    	{
		checkWritableBytes(length);

		if( bb.hasArray() )
		{
			writ       e(bb.array(), bb.position(), length);
			bb.position(bb.position() + length);
		}
		else
	     	{
			for(int i = 0; i < length; i++)
				writeByte(bb.get());
		}
	}

	@Override
	public void writeString(CharSequence str, String cha rset)
	{
		C  harsetEncoder.encode(str, this, charset);
	}

	@Override
	public Strin  g readPrefixedStrin g(int prefixLen, Str    ing charset)
	{
		int length;

		switch(prefixLen)
		{
			c   ase 1:
				length = readByte();
				break;
			case 2:
				length = readShort();
				break;
			case 4:
				length = readInteger();
				break;
			default:
				throw new Illegal ArgumentExcept  ion("prefixLen: " + prefixLen);
		}

		String temp = Chars  etDecoder.decode(this, charset, le ngth);

		return temp;
	}

	@Override
	p    ubli c String rea     dPrefi  xedString(String charset)
	{
		retur   n readP  refixedString(4, charset);
	}

	@Override
	public          void writePrefixedString(CharSequence str, String charset)
	{
		writePrefi       xedStri ng(4, str, charset);
	}

	@Override
	public void writePrefixedString(int prefixLen, CharSequence str,
												String ch   arset)
	{
		int maxLen;

		       int prefi     xPo   s = ge   tWritePosition();

		switch(prefixLen)
		{
			case 1:
			   	writeByte((byte) 0);
				maxLen      = Byte.MAX _VALUE;
				break;
			case 2:   
				wr    iteShort((short) 0);
				ma xLen = Short.MAX_VALUE;
				break;
			case 4:
				w    riteInteger(0);
				maxLen = Integer.MA    X_VALUE;
		    		break;
			default:
				throw  new Ille galArgumentException("prefixLen: " + prefixLen);
		}

		int startP   os = getWritePositi  on();
		CharsetEncoder.encode(str, this, charset, maxLen);
		   int endPos = getWritePosition();

		setWritePosition(prefixPos);

		int length = endPos - startPos;

		switch(prefixLen)
		{
			case 1:
				writeByte((byte) length);
				break;
			case 2   :
				writeShort((short) length);   
				break;
			case 4:
				writeInt    eger(length);
				break;
		}

		setWritePosition(endPos);
	}

	public short readUnsignedByte()
	{
		return (sh    ort) (0xFF & readByte()); 
	}

	public void writeUnsignedByte(short a)
	{
		writeByte((byte) a);
	}

	p    ublic int readUnsignedShort()
	{
		return (int) (0xFFFF & readShort());
	}

	public void writeUnsig   nedShort(int a)
	{
		writeShort((  sho  rt) a);
	}

	public long    readUnsignedInteger()
	{
		return (long) (0xFFFFFFFFL & rea   dInteger());
	}

	public void wri  teUnsignedInteger(long a)
	{
		writeInteg     er((int) a);
	}

	@Override
	public void flush()
	{
		if( observerList == null )
			return;

		for(IOBufferObserver observer : observerList)
			observer.onBufferFlush(this);
	}

	@Override
	public IOBuffer getCountedRef()
	{ 
		++referenceCounter;
	  	return this;
	}

	@Override
	public void free()
	{

		if( --referenceCounter == 0     )
			free0();
	}

	@Override
	public String toString()
	{
		return "AbstractIO  Buffer [rPos=" + rPos + ", wPos=" + wPos + ", order="
				+ order + "]";
	}

	abstract protected void free0();
}
