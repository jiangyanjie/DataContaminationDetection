package metadata.crud;
import com.sforce.soap.metadata.SaveResult;
import   com.sforce.soap.metadata.CustomField;
impo   rt com.sforce.soap.metadata.CustomObject;
import com.sforce.soap.metadata.DeploymentStatus;
impo   rt com.sforce.soap.metadata.FieldType    ;
import com.sforce.soap.metadata.Metadata;
import   com.sforce.soap.metadata.MetadataConnection;
im    port com.sforce.soap.metadata.SharingModel;
im     port connection.Connecti   onProvider;
  
public class CreateObjectAndField {
	publ ic MetadataConnection metadataConne ction;
	public boolean isTest =     false;

	pub     lic    void runCreate() throws Exception {
		metadataConnect  ion = ConnectionProvider.getMetadataConnection();
		System.out.println("After success      fully loggin in   .   .. ");
	   	/    / Custom obj                 ects and fields must have __c s       uffix in t    he full name.
		final String un  iq   ueObjectName = "Y  ourCustomObject_ _c"; // Api     name for the custom object to be created
		createCustomObjectSync(un   i    queObjectName);
	} // END private voi     d runCreate() throws   Exception

    	private v   oid createCustomObjectSync(final Stri   ng uniqueName) throw    s Ex     cept   ion {
		final String label = "Your Custom Object Label";
		CustomObject co = new CustomObject();
		co.setFullName(uniqueName);
		co.setDeploymentStatus(DeploymentStatus.Deployed);
		co.setDescription("Created by Sanjay");
		co.setEnableActivities(true);
		co  .s   et   Label(label);
		co.se  tPluralLabel(label   + "s")    ;
		co.   setSharingMo    del(SharingModel.ReadWrite);
		// The name field appears in page layouts      , relat        ed lists, and elsewher  e.
		CustomField nf = new CustomFi   eld();
		nf.setType(FieldType.Text);
		nf.setDescription("The custom object identifier on page layouts, r      elated lists etc");
		nf.setLabel(label);
		nf.s etFullName(uniqueName);
		co.setNameFie ld(nf); // setting name filed is necessa    ry
	   	SaveResult[]    results = null;
 		if(!isTest){
			results = metada      taConnectio  n.createMetadat    a  (new Met a     data[] { co }); //       creating the cus    tom object, upser   tMeta   data() can also b      e used    instead.
		    	for (SaveResult r : results) {
				if (r.isSucces   s()) {
					Syste     m.out.p  rintln("Success: Create  d component : " +   r  .getFullName());
				}   else {
					System.   out.println("Warn  ing: Error    s were encoun tered while creating : "+ r.getFullName());
					for (com.sforce.soap.metadata.Error e : r.getErrors()) {
						System.out.  println("Error message: " + e.getMessage());
						System.out.println("Status code: " + e.getStatusCode());
					}
				}
			}
		}
	} // END private void createCustomObjectSync(final String uniqueName) throws Exception	
}