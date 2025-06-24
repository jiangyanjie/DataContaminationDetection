package Application;

import java.io.File;
import java.io.FilenameFilter;
import java.util.logging.Logger;

public class ABWUpdateCSVPrepare 
{
	private static Logger logger = Logger.getLogger(ABWUpdateCSVPrepare.class.getName());
	
	public ABWUpdateCSVPrepare()
	{}
	
	public void extractCsvFromExcel(String folder)
	{
		if( !folder.endsWith("/"))
			folder+="/";
		File currentExportFile = getLatestFile(folder);
		String cmd = "";
		try
		{
			cmd = "cmd /c start /wait ExecuteCSVExtract.bat \"" + currentExportFile.getAbsolutePath() + "\" \"" + folder + "CurrentBatch.csv\"";
			logger.info("Executing command - [" + cmd + "]");
			Runtime.getRuntime().exec(cmd);
		}
		catch(Exception e)
		{
			logger.warning(e.getMessage());
			throw new RuntimeException("Error executing command [" + cmd + "]", e);
		}
	}
	
	// read through directory and get file with largest numeric increment X - files named like this bacsd_uku4X.xls
	private File getLatestFile(String folder)
	{
		File folderToCheck = new File(folder);
		
		FilenameFilter filter = new FilenameFilter() {			
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().startsWith("bacsd_uku4");
			}
		};
		
		File[] files = folderToCheck.listFiles(filter); 
		
		if(files.length>0)
		{
			File latestFile = files[0];
			for(File f : files)
			{
				if(getFileNumber(f.getName()) > getFileNumber(latestFile.getName()))
					latestFile = f;
			}
			return latestFile;
		}
		return null;
	}
	
	private int getFileNumber(String fileName)
	{
		try
		{
			String justNumber = fileName.toLowerCase().replace("bacsd_uku4", "").replace(".xls", "");
			return XmlHelper.getIntegerFromXmlString(justNumber);
		}
		catch(Exception e)
		{
			logger.warning(e.getMessage());
		}
		return 0;
	}
}
