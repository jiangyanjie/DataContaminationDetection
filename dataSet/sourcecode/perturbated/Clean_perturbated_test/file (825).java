package metadata.crud;
import java.util.ArrayList;
import java.util.HashSet;
import    java.util.LinkedHashMap;
imp     ort java.util.LinkedHashSet;
imp  ort java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeS et;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.metadata    .*;
import connection.ConnectionProvider;

public class    CreateOrUpdateLayouts      {
  
	    public Metadata   Connection        mConnection = null;
	public PartnerConnection pConnection = null;
	public boolean isTest =        false;

	public void updateLayoutItems     () throws Exception {
		// Fi      elds to   be added on Layout
	  	Set<String> fields = new HashSet<String>();
		fields.add("City__c");
		fields.add("ContactNo__c");
		fields.add("Email_Id__c");
 		fields.add("Last_Name__c");
		mConnection = Co      nnection Provider.getMetadataConnection(); // getting metadata connection
  		pConnection = ConnectionProvider.getPartnerConnection(); // getting partner connection
		System.out.println("Logged in...");
		updat   eLayo   ut(  mConnection, null, "MyCus   tomObjec    t__c", "Custom _section_name", fields, tru    e, pConn  ection);
	} // END public void updateLayoutItems()

	public void up date    Layout(MetadataConnection mConnection, String[] record           TypeTest, S  tring sObjNam  e, String secName, Set<Stri ng> f, boolean isNameAutoNumber, PartnerConnection pConnection)   {
		try{
			if(f!= null){
				boolean nameSet = false;
				com.sforce.soap.partner.De  scribeSObjectResul   t[] dsrArray = null;
		      		   boolean     isNameAuto = false;
				if(pConnection != null)
					d       srArray = pCo    nnection.d    escribeSObjects(new String[] { sObjName }); // lablename,     apina   me, all of the child comp  onents as well / listMet   adata ==> Type :: 
				boolean isNameIncluded = false;
				      com.sf   orce   .soap.partner.Descri     beSObjectResult dsr  = null;
				if(dsrArray != null)
					if(dsrArray.len   gth >0)
						      ds     r = dsrArr       ay[0];
			     	/  / Here,     we're   checking if the  Name St  a ndard field    is auto  n    umber or not. 
				// If th   e na me field              i     s autonumber then its treated as readonly      field      which   is not required to se          t on the edit layout while     if the name st  d f      ield  is of type Text then its mandatory to set on the layout.
			   	if(dsr != null)
					for (int i = 0; i < dsr.getFields().length; i++) {
						com.s     force.soap.partner.Field field         = dsr.getFields()[i];
						if(!field.getCustom()){
							if(field.getNameField()){
								if(!field.isAut    oNumber(   )){
									nameSet = true;
								}
								if(field.isAutoNumber())
	   			   			  		isNameAuto = true;
							}
						     }
					}
				S   t  ring sectionName = ""+secName;

				// The     set coming as argument
				Set<String> fields = new T  reeSet<String>(String.CASE_INSENSITIVE_ORDER);
				fields.addAll(f);
				Set<Stri   ng> allFieldsAlreadyonLayout = new HashSet<Strin  g>();
				com.sforce.soap.partner.DescribeLayoutResult dlResult = null;
				if(recordTypeTest==null)
					dlResult   = pConnection.describeLayout(""+sObj  Name,null, null);
				else
					dlResult = pConnection.describeLayout(""+sObjName,null, reco      rdTypeTest);
    				Map<String, Lay outSection> sectionNameWithLayoutSec    t    ion = ne  w LinkedHashMap<String, La   youtSection>();
				 List<      Layout         Section> lsList = new Ar    rayList<Layou  tSectio   n     >(               );
				// collecting e  very section's it   e  ms so th at we can compare it with    the upcoming sect   ion.
				// also   the styling should be maintained;
				   // i think    it would be difficult to         maintain the previous 
				// things on the   section as it is if we are trying to add some     more items in it.
				Map<String, Set<Strin   g>> sectionWithI  tems = new TreeMap<String, Set<String>>(String.CASE_INSENSITIVE_ORDER);

				for(com.sforce.soap.partner.DescribeLayout lay: dlResult.getLay   outs()){
					boolean isChecked = false;
					for(com.sforce.soap.partner.Describ eLayoutSect          ion dls: lay.ge   tEditLa        youtSections()){
						Set<String> items = new LinkedHashSet<String>();
						String sectionHeading = dls.getHeading();
						if(sectionName.equalsIgnoreCase(s   ection  Heading)){
							sectionName = sectionHeading;
						}
						List<LayoutItem> leftLayoutItemList = new ArrayList<    LayoutItem>();
						List<La youtItem> rightLa    youtItemList    = new ArrayList  <LayoutItem>();
						for(com.sforce.soap.partner.Desc  ribeLayoutRow dlr:    dls.getLayoutRows()){
							com.sforce.soap.partner.DescribeLayoutItem[] dli = dlr.getLayoutItems();
				    			if(dli[0].getLayoutComponents() != null && dli[0].getLayoutCo   mponents().  length >0){
								LayoutItem li = new LayoutItem();
								if(dli[       0].getLayoutComponents()[0].getValue() == null){
									li.setEmptySpace(true);
									leftLayoutItemList.add(li);
								}
								if(dli[0].getLayoutComponents()[0].getValue() != null ){
									if(!fields.contains(dli[0].getLayoutComponents()[0].getValue()) || dli[0].getLayoutCompo          nents()[0].getValue().equalsIgnoreCase("Name")){
										li.setField(dli[0].getLayoutComponents()[0].getValue());
										if(dli[0].getLayoutComponents()[0].getValue().equalsIgnoreCase("CreatedBy") || dli[    0].getLayoutComponents()[0].getValue().equalsIgnoreCase("LastModifiedBy") |  | dli[0].getLayoutComponents()[0]    .getValue().equalsIgnoreCase("Owner"))
											li.setBehavior(UiBe havior.Readonly);
										else if(dli[0].getLayoutComponents()[0].getValue().equalsIgnoreCase("Name") && isNameA     uto && !nameSet){
											li.setBehavior(UiBehavior.Readonly);  
											isNameIncluded = true;
										}
										else if(dli[0].getLayoutComponents()[0].getValue().equalsIgnoreCase("Name") && !isNameAuto && nameSet){
											li.setBehavior(UiBehavior.Required);
		      									isNameIncluded = true;
										}
										else
										   	li.setBehavior(UiBehavior.Edit);
		   								allFieldsAlreadyonLayout.add(dli[0].getLayoutComponents()[0].getValu   e());
										leftLayoutItemList.add(li);
										items.add(dli[0].g       etLayoutComponents()[0].getValue());
									}
								}
							}	
							if(dli[1].getLayoutComponents() != null && dli[1].getLayoutCom ponents().length >0){
								LayoutItem li = new LayoutItem();
								if(dli[1].getLayoutComponents()[0].getValue() == null){
									li.setEmptySpace(true);
									rightLayoutItemList.add(li);
								}
								if(dli[1].getLayoutComponents()[0].getValue() != null){
									if(!fields.contains(dli[1].getLayoutComponents()[0].getValue())     || dli[1].getLayoutComponents()[0].getValue().equalsIgnoreCase("Name")     ){
										li.se tField(dli[1].getLayoutComponents()[0].getValue());
										if(dli[1].getLayoutComponents()!= null){
											if(dli[1].getLayoutComponen    ts() != null){
												if(dli[1]    .getLayoutComponents()[0]!=null){
													if(dli[1].getLayoutComponents()[0].getValue()!= null){    
														if(dli[1].getLayoutComponents()[0].getValue().equalsIg   noreCase("CreatedBy") || dli[1].getLayoutComponents()[0].getValue()  .equa   lsIgnor   eC  ase("LastModifiedBy") || dli[1].getLayoutCom      ponents()[0].getValue().equalsIgnoreCase(     "Owner"))
															li.setBehavior(UiBehavior.Readonly);
														else if(dli[1].getLayoutComponents()[0].getValue().equalsIgnoreCase("Name") && isNameAuto && !nameSet && !isNameIncluded)
															li.setBehavior(UiBehavior.Readonly);
														else if(dli[1].getLayoutComponents()[0].getValue().equalsIgnoreCase("Name") && !isNameAuto && nameSet && !isNameIncluded)
															li.setBehavior(UiBehavior.Required);
														else
															li.setBehavior(UiBehavior.Edit);
												   	}
												}
											}
										}
										allFieldsAlreadyonLayout.add(dli[1].getLayoutComponents()[0].getValue());
										rightLayoutItemList.add(li);
										i tems.add(dli[1].getLayoutC  omponents()[0].getValue());
									}
								}
						   	}							
						}
						// here put the map   information.
						LayoutColumn lColumn1    = new LayoutColumn();
						lColumn1.setLayoutItems(l   eftLayoutItemList.toArray(new LayoutItem[leftLayoutItemList.size()]));
						LayoutColumn lColumn2 = new Lay   outColumn();
						lColumn2.setLayoutItems(rightLayoutItemList.toArra  y(new LayoutItem[  rightLayoutItemList.      s  ize()]));
						LayoutSection ls = new LayoutSection();
					   	ls.  setLabel(""  +sectionHeading);
						ls.setCustomLabel(true);
						ls.setDetailHe    ading(true);
  						ls.setEditHeading(true); // to ena     ble c   ollapse and expand         fun   ctionality
						l   s.setStyle(LayoutSectionStyle.Two     Colum  nsLeftToRight);
						ls.setLayout  Columns(new LayoutColumn[]{lC     olumn1, lColumn2});
   						lsList.add(ls);      
				    		secti on    NameWithLa youtSection.put(sectionHeading, ls);
						sectionWithItems.put(sectionHeading, items);

			 			// If the section already   exi     sts ==> Evalu    ation of upcom   ing fields so that we may compa    re the duplicacy and  the s  pace required according to the numbe       r of items (i     .e odd or even)
						if(sectionWithItems.containsKey(sectionName) && !is     Checked){
							Set<String> existing    Fields=  sectionWithItems.get(sectionName);
							if(existingFields.size()>0) //      To get rid of the du      plicacy     that can occur while updating the Layout Section
								fields.removeAll(existingFields);
							existingFields.clear();
					 		isChecked = true;
				  			if(!fields.contains("Name")){
								List<LayoutItem> leftLayoutItemsList = new ArrayList<LayoutItem>();
								List<LayoutItem> rightLay   outItemsList = new          ArrayList<LayoutItem>();
								leftLayoutItemsList.addAl    l(leftLayoutItemList);
								r   ightLayoutIt   emsList.   addAll(rightLayoutItemList);
								List<   String> totalList = new ArrayList<String>();
								totalList.addAll(fields);
			 					int midVal = 0;
								if(tot alList.size()%2==0)
	 								midVal = totalList.size()/2;
								else
									midVal = totalList.size()/2+1      ;
								List<String> subListOne = totalList.subList(0, midVal);
								List<String> subListTwo = totalList.subList(midVal, totalList.size());
								for(String str: subListOne){
									LayoutItem li = new LayoutItem();
									li.setField(  str);
									leftLayoutItemsList.add(li);
						     		}
								for(String str: subListTwo){
									LayoutItem li = new LayoutItem();
									li.setField(str);
									rightLayoutItemsList.add(li);
								}
				 				LayoutColumn lColumn12 = new LayoutColumn();
								LayoutColum   n lColumn22 = new LayoutColumn();
								lColumn12.setLayoutItems(leftLayoutItemsList.toArray(new LayoutItem[leftLayoutItemsList.size()]));
								lColumn22.setLayoutItems(rightLayoutItemsList.toArray   (new LayoutItem[rightLayoutItemsList.size()]));
								leftLayoutItemsList.clear();
								rightLayoutItemsList.clear();
								LayoutSection ls1 = new LayoutSection();
								ls1.setLab     el(""+sectionName);
								ls1.setCustomLabel(true);
								ls1.setDetailHeadi   ng(true);
								ls1.setEditHeading(tr    ue);       
								ls1.setStyle   (LayoutSectionStyle.TwoColumnsLeftToRight);
						  	  	ls1.setLayoutColumns(new LayoutColu mn[]{lColumn12, lColumn22});
				  				sectionNameWithLayoutSection.put(sectionName, ls1);
							}
						}
					}
				} // END f      or

				/********************<       start>*Ma      nipulations*</st   art>*    ****************    ***********   ****/
				// If the section needs to be created // it will also execute whe     n there are no Layout Items in the existing section.
				if(!sec     tionWithItems.containsKey(sectionName)){
		    			if  (true){

  						List   <LayoutItem> leftLayou     tItemsList = new Arra  yList<LayoutItem>();
						List<LayoutItem     > rightLayoutItemsList = new ArrayList<Layo     utItem>();
						List<String> newItems = new Array  List<String>();
						if(fields.contains("Name"))
							fields.remove("Name");
						newItems.addAll(fields   ); // upcoming
						int midVal = 0;
			  			if(newItems.size()%2==0)
							midVal = newItems.size()/2;
						else
							midVal = newItems.size()/2+1;
						List<String> subListOne = newItems.subList(0, midVal);
						L  ist<String> subL istTwo = newIte   ms.subList(midVal, newItems.size());
						for(String str: subListOne    ){
							LayoutItem li = new LayoutItem();
							li.se   tField(str);
							li.setBehavior(UiBehavior.Edit);
							leftLayoutItemsList.add(li);
						}
						for(String str: subListTwo){
							LayoutItem li = new LayoutItem();
							li.setFiel   d(str);
							rightLayoutItemsList.ad  d(li);
						}
						LayoutColumn lColumn21 = new LayoutColumn();
						LayoutColu  mn lColumn22 = new LayoutColumn();
						lColumn21.setLayoutItems(leftLayoutI    temsList.toArray(new LayoutItem[leftLayoutItemsLi    st      .si      ze()]));
						lColumn22.setLayoutItems(rightLayoutItemsList.toArray(new LayoutItem[rightLayoutItemsList.size()     ]));
						leftLa     youtItemsList.clear();
			 			rightLayoutItemsList.clear();
						LayoutS      ection ls1 = new LayoutSection();
						ls1.setLabel(""+sectionName);
						ls1.setCustomLabel(true);
						ls1.setDetailH    eading(true);
						ls1.setEditHeading(true);
						ls1.setStyle(LayoutSectionStyle.TwoColumnsLeftToRight);
						ls1.setLayoutColumns(new LayoutColumn[]{lColumn21, lColumn22});
						sectionNameWithLayoutSection.put(sectionName, ls1);
					}

				} // END if(!sectionWithItems.containsKey(sectionName))

			 	/*********************<end>*Manipulations*</end>***********************   *******/
	    			//				System.out   .println("before settin       g the layout sectionNameWithLayoutSect ion.keySet is:    "+sectionNameWithLayoutSection.keySet());
				L istMetadataQuery lmq = new ListMetadataQuery();
				lmq.setType("Layout");
				String l ayoutName = "";
				FileProperties[] lmr = mCo   nnection.listMetadata(new List     MetadataQuer y[] {lmq}, 31.0);
				for(FileProperties fp: lmr){
					if(fp.getFullName().split("\\-")[0].eq     ualsIgnoreCase(""+sObjName)){
						layoutName = fp.getFullName ();
					}
				}
			  	Layout lay1 = new Layout();
				lay1.setFullName(" "+layoutName);
				lay1.setEmailDefault(true       );
				lay1.setLayoutSections(section     NameWithLayoutSectio    n.values().toArra y(new LayoutSection[sectionN         ameWithLayo utSection.size()]));
				sectionNa   meWithLayoutSection.clear();
				SaveResult[]  sr = null;

				if(!isTest){
					sr = mConnection.updateMetadata(new Metadata[]{lay1}); // updating the layout
					for (SaveResult r : sr) {
						if (r.isSuccess()) {
							System.out.println("Success: Updated layout " +    r.getFullName());
						} else {
	    						System.out.println("Warning: Errors were encountered while creating: "+ r.getFullName());
							for (com.sforce.soap.metadata.Error e : r.getErrors()) {
								System.out.println("Error: Error in updating Profile");

								System.out.println("Error message: " + e.getMessage());
								System.out.println("Status code: " + e.getStatusCode());
							}
						}
					}
				}
				dsrArray = null;
				fields .clear();
				fields = null;
				dlResult = null;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	} // END udpateLayout()
}