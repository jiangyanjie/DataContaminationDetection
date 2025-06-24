package utils;

import inputLayer.InputLayerParameters;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import preprocess.PreprocessParameters;
import trainer.TrainerParameters;
import classifier.ClassifierParameters;

public class Configuration {

	private String configFile="eeg.cfg";
	private InputLayerParameters inputParams;
	private PreprocessParameters preproccParams;
	private TrainerParameters trainerParams;
	private ClassifierParameters classifierParams;
	
	
	public Configuration(String configFile)
	{
		this.configFile = configFile;
	}
	
	
	public Configuration() {
		readConfigFile();
	}


	private void readConfigFile(){
		
		try(BufferedReader br = new BufferedReader(new FileReader(configFile))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	
		    	String[] parts = line.split(" ");
		    	//TODO to be continued
		    }
		    // line is not visible here.
		} catch (FileNotFoundException e) {
		
			this.classifierParams = new ClassifierParameters();
			this.inputParams = new InputLayerParameters();
			this.preproccParams = new PreprocessParameters();
			this.trainerParams = new TrainerParameters();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void writeConfigFile()
	{
		
	}
	

	public InputLayerParameters getInputLayerParameters()
	{
		return this.inputParams;
	}
	
	public PreprocessParameters getPreprocessParameters()
	{
		return this.preproccParams;
	}
	
	public TrainerParameters getTrainerParameters()
	{
		return this.trainerParams;
	}
	
	public ClassifierParameters getClassifierParameters()
	{
		return this.classifierParams;
	}


	public boolean isValid() {
		// TODO Auto-generated method stub
		return true;
	}


	public String getErrorMsg() {
		// TODO Auto-generated method stub
		return "Is valid returned false";
	}


	public String getLogFilename() {
		// TODO Auto-generated method stub
		return "log.out";
	}
	
	
	
	
}
