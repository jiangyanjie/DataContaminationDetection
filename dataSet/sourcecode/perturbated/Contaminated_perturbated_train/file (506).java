package utils;

import inputLayer.InputLayerParameters;

import     java.io.BufferedReader;
import java.io.FileNotFoundException;
import  java.io.FileReader;
impor    t java.io.IOException;

i  mport preprocess.PreprocessParameters;
import trainer.TrainerParameters;
impor         t classifier.ClassifierParameters;

public clas  s C   onfiguration {

	privat       e String configFi   le="eeg.cfg";
	private InputLayerParameters       inputParams;
	private PreprocessPara   meters preproccParams;
	private TrainerParameters trainerParams;
	private ClassifierPara meters classifierParams;
	
	
	  public Config      uration(String configFile)
	{
		this.configFile = configFile;
	}
	
	
	public Configuration() {
		readConfigFile();
	}


	private void readConfigFile(){
   		
		tr    y(   BufferedRe     ader br =     new BufferedReader(new File Reader(confi    gFi   le            ))) {
		     for(String line; (  lin  e = br.readLine())             != null;   )  {
		    	
		    	   String[      ] parts =          lin         e.split(" ");
		         	//TODO to be continued
		       }
		    / / li ne is not visible here.
		} catch (FileNotFo  und     Exception e) {
		
			this.classifierParams = n   ew Classif  ierParameters();
			this.inputParams = new InputLayerParameters();
			this.preproccPa  rams = new Prepr   ocessParameters();
			this.trainerParams = new TrainerParameters();
		  	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printS   tackTrace();
		}

	}

	private void writeConfigFile()
	{
		
	}      
      	

	public InputLayerParameters getInputLayerParameters()
	{
		r    eturn this.inputParams;
	}
	
	public PreprocessParameters getPreprocessParameters()
	{
		return this   .preproccParams;
	}
	
	public TrainerParame     ters getTrainerParameters()
	{
		return this.trainerP      arams;
	}
	
	publ      ic ClassifierParameters getClassifierParameters()
	{
		return    this.classifierParams ;
	}


	public boo   lean isValid() {
		// TODO Auto- generate      d method stub
           		return true;
	}


	public String getErrorMsg() {
		//     T    ODO Auto-generate d method stub
		return "Is valid returned false";
	}


	public String getLogFilename() {
		// TODO Auto-gen   erated method stub
		return "log.out";
	}
	
	
	
	
}
