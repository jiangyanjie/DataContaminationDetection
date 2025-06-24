package app.management.prototype;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
public abstract class AccountParent {

	private String name;
	private String address;
	private String profession;
	private int appsUsedFree;
	private int appsPurchased;
	protected double amountToBePaid;
        private boolean admin = false;
        Path currentRelativePath = Paths.get("");
        public String accDir = currentRelativePath.toAbsolutePath().toString()+"\\Accounts\\";
       
public AccountParent (int aa) throws IOException
        {
          buildAccount(aa);            
        }
	
public void buildAccount(int fn)
    throws IOException
  {
    FileReader fr = new FileReader(accDir+fn+".txt");
    BufferedReader textReader = new BufferedReader(fr);
    
    int numberOfLines = 6;
    String[] textData = new String[numberOfLines];
    for (int i = 0; i < numberOfLines; i++) {
        
      textData[i] = textReader.readLine();
            
    }
    this.name = textData[0];
    this.address = textData[1];
    this.profession = textData[2];
    this.appsPurchased = Integer.parseInt(textData[3]);
    this.amountToBePaid = Double.parseDouble(textData[4]);
    setAdmin();
    textReader.close();
      
  }
//Setters	
	public void setName(String n){
		this.name = n;
	}
	public void setAddress(String a){
		this.address = a;
	}
	public void setProfession(String p){
		this.profession = p;
	}
	public void setAppsUsedFree(int af){
		this.appsUsedFree = af;
	}
	public void setAppsPurchased(int ap){
		this.appsPurchased = ap;
	}
	public void setAmountToBePaid(double amountp){
		this.amountToBePaid = amountp;
	}
	public void setAdmin()
        {
            if (this.name == "admin")
            { this.admin = true; } 
            else { this.admin = false;}
         }
	//Getters
	public String getName(){ 
		return this.name; 
	}
	public String getAddress(){ 
		return this.address; 
	}
	public String getProfession(){ 
		return this.profession; 
	}
	public int getAppsUsedFree(){ 
		return this.appsUsedFree; 
	}
	public int getAppsPurchased(){ 
		return this.appsPurchased; 
	}
	public double getAmountToBePaid(){ 
		return this.amountToBePaid; 
	}
}