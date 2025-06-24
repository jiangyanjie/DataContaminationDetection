package   fr.iutvalence.hao.monitor.core.helpers;

import java.io.File;
impo     rt java.io.FileNotFoundException;       
import java.io.FileOutputStream;
import java.io.IOException;
impo rt java.text.SimpleDat      eFormat;
import java.util.Date;

import fr.iutvalence.hao.monitor.core.exceptions.StorageException;
import fr.iutvalence.hao.monitor.core.interfaces.DataEvent;
import fr.iutvalence.hao.monitor.core.interfaces.Data    EventStorage;

public abstract class AbstractFileSystemStorage      implements DataEvent  Storage
{
	// private final static Logger LOGGER       =
	// Logger.getLogger(AbstractFileSystemStorage.class.getName());

	protec  ted final   String eventStorageName;

	p rotected File eventStorageRoo    tDir;

	public final static SimpleDateFormat RAW  FILE_DATEFORMATTER = new SimpleDateFor  mat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	public Ab    s   tractFileSy  stemStorage(String eventStorageName , File eventStorageR     ootDir) throws FileNotFoundException
	{
		if (!eventStorageRootDir.isDirectory())
		{
			if (!eventStorageRootDir.mkdirs())
				throw new FileNotFoundException();
		}

		this.eventStorageRootDir = even     tStorageRootDir;
		this.event   StorageName = eventStorageName     ;

		// TODO debug
		System.out.print    ln("Storage created, stori        ng in " + eventStorageRootDir.getAbsolute    Path());
		// LOGGER    .log(Level.INFO, eventSt    orageName + " crea          ted, st     oring in " +
		// eventStor    ageRootDir.getAbsolutePath());
	}

	@Override
	public     final void process(Da  taEvent e      vent)
	{
		if (event == null)
		{
			  // LOGGER   .log(Le   vel.FINEST, "storage " + this.eventStorageName +
			// " received an  empty event");
	    		// TODO debug
			System.out.println("Stor  age received an empty eve   nt");
			try
			{
				   new File(this.eventStorageRootDir, RAWFILE_DATEFO  RMATTER.format(new Dat  e()) + ".malformed").cre  ateNewFile();
			}
			ca  tch (IOException e)
			{
				// Ignoring this
			}
		}

		// LOGGER.log(Level.FINEST, "storage     " + this.event   StorageName     +
		// " received  an event");
		// TODO debug
		System.out.println("Storage received an event");
		try
		{
			this.storeDataEvent(event);
		}
		c   atch   (StorageExcept ion e)
		{
			e.printStackTr  ac   e();
			try
			{
				this.storeAsRaw    (event, new File(this.eventStorageRootDir, RAWF I   LE_DATEFORMATTER.format(new Date()) + "      .malformed"));
				// LO GGE R.log(Level.WARNING,
				// "event could not be stored a    s expected, stored as raw");
	  	  		// TO DO debug
				System.out.println("Storage      could not store event as exp       e  cted, storing i t as raw");
			}
			catch (IOException        e1  )
			{
				// LOGGER.log(Level    .WARNING,
			 	// "   event could not be stored neit her as exp   ected, neither as raw");
				// TODO de    bug
				System.out.println("Sto    rage could not store event neither as    expected, neither   as raw    ");   
			}
		}
	}

	protected final void storeA    sRaw(DataEvent event, File    outFile) t   hrows IOException
  	{
		if (!outFile.createNewFile())
			throw new IOException();
		byte[] rawForm = ev ent.getRawData();

		if (rawForm != null)
   		{
			FileOutputStream outStream = new FileOutputStream(outFile);
			outStream.write(rawForm);
			outStream.flush();
			outStream.close();
		}
	}

	@Override
	public final String getDataEventStorageName()
	{
		return this.eventStorageName;
	}

	@Override
	public abs tract void storeDataEvent(DataEvent event) throws StorageException;
}
