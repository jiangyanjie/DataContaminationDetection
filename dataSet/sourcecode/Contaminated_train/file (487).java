package sh.gpiosimulator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * User: Simon
 * Date: 08.11.13
 * Time: 03:21
 */
public class ConfigManager {
    private static ConfigManager configManager = null; //Singleton Pattern

    //region fields
    private String fileName;
    private String filePath;
    private SortedProperties configFile;
    private int gpioPinNum;
    private String gpioDirection;
    private int gpioValue;
    //endregion

    /**
     * Constructor
     */
    private ConfigManager() {
        setFileName("config.properties");
        setFilePath("");
        setGpioPinNum(17);
        setGpioDirection("in");
        setGpioValue(0);
        configFile = new SortedProperties();
    }

    public static ConfigManager getInstance() {
        if (configManager == null)
            configManager = new ConfigManager();
        return configManager;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getGpioPinNum() {
        return gpioPinNum;
    }

    public void setGpioPinNum(int gpioPinNum) {
        this.gpioPinNum = gpioPinNum;
    }

    public String getGpioDirection() { return gpioDirection; }

    public void setGpioDirection(String gpioDirection) { this.gpioDirection = gpioDirection; }

    public int getGpioValue() { return gpioValue; }

    public void setGpioValue(int gpioValue) { this.gpioValue = gpioValue; }

    public boolean configFileExists() {
        File f = new File(filePath + fileName);
        if (f.exists())
            return true;
        else
            return false;
    }

    public void createConfigFile() {

        if (!configFileExists()) {
            configFile.setProperty("NumberOfPins", "" + getGpioPinNum() + "");
            for (int i = 0; i <= getGpioPinNum(); i++) {
                configFile.setProperty("Gpio" + i + "Direction", getGpioDirection());
                configFile.setProperty("Gpio" + i + "Value", "" + getGpioValue() + "");
            }

            saveConfigFile();

        }
        else {
            System.out.println("Configuration file already exists!");
        }

    }

    //TODO returnvalue keys to edit! -> String or int value!!!!
    public void readConfigFile() {

        if (configFileExists()) {
            try {
                FileInputStream fis = new FileInputStream(getFilePath() + getFileName());
                configFile.load(fis);

                //list all properties and print them on the screen
                configFile.list(System.out);

                //get all keys from the properties file.
                /*
                Enumeration propertyKeys = configFile.propertyNames();
                while (propertyKeys.hasMoreElements()) {
                    String key = (String) propertyKeys.nextElement();
                    String prop = configFile.getProperty(key);
                }*/

                //close the input stream.
                fis.close();
            } catch (Exception ise) {
                ise.printStackTrace();
            }

        }
        else {
            System.out.println("Configuration file does not exist!");
        }

    }

    /**
     * editConfigFile for String Value
     *
     * @param key
     * @param newValue
     */
    public void editConfigFile(String key, String newValue) {

        configFile.getProperty(key);
    }

    /**
     * editConfigFile for int Value
     *
     * @param key
     * @param newValue
     */
    public void editConfigFile(String key, int newValue) {

        configFile.setProperty(key, Integer.toString(newValue));
    }

    public void saveConfigFile() {
        try {
            //Save the config file
            FileOutputStream fos = new FileOutputStream(getFilePath() + getFileName());
            configFile.store(fos, "GPIO-Simulator configuration file");

            //Console output if the file is saved
            System.out.println("Configuration file saved under the path: " + getFilePath());

            //close the output stream.
            fos.close();
        } catch (Exception ose) {
            ose.printStackTrace();
        }
    }


    public void deleteConfigFile() {
        if (configFileExists()) {
            boolean deleted;
            File f = new File(getFilePath() + getFileName());
            //delete the file.
            deleted = f.delete();
            if (deleted)
                System.out.println("Configuration file deleted!");
            else
                System.out.println("Configuration file could not be deleted!");
        }
        else {
            System.out.println("There is no config file to delete!");
        }
    }
}
