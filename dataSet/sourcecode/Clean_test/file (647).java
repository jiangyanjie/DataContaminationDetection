/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Coral;
import java.io.File;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author sujay
 */
public class CoralClassDivider {
 CoralClassDivider(){
     
 }   
 //getters
 @Entity
@Table(name="Students")
public class Student implements Serializable {

    private String StudentID;
    private String LastName;
    private String FirstName;
    private String email;
    private String PhoneNumber;
    private String DaysAttending;
    private String Comments;
    private String ItemsCheckedCurrently;
    private String ItemsChecked;
    private String Messages;
    private String Timeslot;
    private String Vacation;
    private String Sick;
    private String Flagged;
    private String Attended;
    private String Absent;
    private File SetCSV;

    public Student() { }

    public Student(String StudentID,File setCSV, String LastName, String FirstName,String email,String PhoneNumber,String DaysAttending,String Comments,String ItemsCheckedCurrently,String ItemsChecked,String Timeslot,String Vacation,String Messages,String Sick,String Flagged,String Attended,String Absent) {
        this.StudentID = StudentID;
        this.LastName = LastName;
        this.FirstName=FirstName;
        this.PhoneNumber=PhoneNumber;
        this.email=email;
        this.Messages=Messages;
        this.DaysAttending=DaysAttending;
        this.Comments=Comments;
        this.ItemsCheckedCurrently=ItemsCheckedCurrently;
        this.ItemsChecked=ItemsChecked;
        this.Timeslot=Timeslot;
        this.Vacation=Vacation;
        this.Sick=Sick;
        this.Flagged=Flagged;
        this.Attended=Attended;
        this.Absent=Absent;
        this.SetCSV=SetCSV;
  
    }
     @Id
    public String getStudentID() {
        return this.StudentID;
    }

    public String getLastName() {
        return this.LastName;
    }
      public String getFirstName() {
        return this.FirstName;
    }

    public String getPhoneNumber() {
        return this.PhoneNumber;
    }
      public String getemail() {
        return this.email;
    }

    public String getMessages() {
        return this.Messages;
    }
      public String getDaysAttending() {
        return this.DaysAttending;
    }

    public String getComments() {
        return this.Comments;
    }
      public String getItemsCheckedCurrently() {
        return this.ItemsCheckedCurrently;
    }

    public String getItemsChecked() {
        return this.ItemsChecked;
    }
      public String getVacation() {
        return this.Vacation;
    }

    public String getSick() {
        return this.Sick;
    }
      public String getFlagged() {
        return this.Flagged;
    }

    public String getAttended() {
        return this.Attended;
    }
    public String getAbsent() {
        return this.Absent;
    }
     public void  setStudentID(String ID) {
   this.StudentID = ID;
     }

    public void setLastName(String last) {
        this.LastName=last;
    }
      public void setFirstName(String first) {
        this.FirstName=first;
    }
      public void setCSV(File CSV){
          this.SetCSV=CSV;
      }

    public void setPhoneNumber(String phno) {
        this.PhoneNumber=phno;
    }
      public void setemail(String mail) {
        this.email=mail;
      }

    public void setMessages(String mssg) {
        this.Messages=mssg;
    }
      public void setDaysAttending(String days) {
     this.DaysAttending=days;
    }

    public void setComments(String cmmt) {
         this.Comments=cmmt;
    }
      public void setItemsCheckedCurrently(String CurrentItems) {
         this.ItemsCheckedCurrently=CurrentItems;
    }

    public void setItemsChecked(String checked) {
         this.ItemsChecked=checked;
    }
      public void setVacation(String vac) {
         this.Vacation=vac;
    }

    public void setSick(String sickstatus) {
         this.Sick=sickstatus;
    }
      public void setFlagged(String Flaggedstatus) {
         this.Flagged=Flaggedstatus;
    }

    public void setAttended(String present) {
         this.Attended=present;
    }
    public void setAbsent(String notpresent) {
        this.Absent=notpresent;
    }
    
 }
  @Entity
@Table(name="Items")
 public class Item implements Serializable {

    private int ISBN;
    private String itemtype;
    private String name;
    private String author;
    private int copiesavailable;
    private String itemhistory;
    private File SetCSV;

    public Item() { }

    public Item(int ISBN, String itemtype,File SetCSV, String name,String author,int copiesavailable,String itemhistory) {
        this.ISBN = ISBN;
        this.itemtype = itemtype;
        this.name=name;
        this.author=author;
        this.copiesavailable=copiesavailable;
        this.itemhistory=itemhistory;
  
    }
     @Id
    public int getISBN() {
        return this.ISBN;
    }

    public String getitemtype() {
        return this.itemtype;
    }
      public String getname() {
        return this.name;
    }

    public String getauthor() {
        return this.author;
    }
      public int getcopiesavailable() {
        return this.copiesavailable;
    }

    public String getitemhistory() {
        return this.itemhistory;
    }
     public void  setISBN(int code) {
   this.ISBN = code;
     }

    public void setitemtype(String type) {
        this.itemtype=type;
    }
     public void setCSV(File CSV){
          this.SetCSV=CSV;
      }

      public void setname(String itemname) {
        this.name=itemname;
    }

    public void setauthor(String creator) {
        this.author=creator;
    }
      public void setcopiesavailable(int copies) {
        this.copiesavailable=copies;
      }

    public void setitemhistory(String hist) {
        this.itemhistory=hist;
    }
     
    
 }
  @Entity
@Table(name="GeneralInfo")
 public class General implements Serializable {

    private String centernum;
    private String businessname;
    private String owner;
    private String[][][][][] employeelist;
    private String daysoperating;
    private String times;

    public General() { }

    public General(String centernum, String businessname, String owner,String[][][][][] employeelist,String daysoperating,String times) {
        this.centernum = centernum;
        this.businessname = businessname;
        this.owner=owner;
        this.employeelist=employeelist;
        this.daysoperating=daysoperating;
        this.times=times;
    }
     @Id
    public String getcenternum() {
        return this.centernum;
    }

    public String getbusinessname() {
        return this.businessname;
    }
      public String getowner() {
        return this.owner;
    }

    public String[][][][][] getemployeelist() {
        return this.employeelist;
    }
      public String getdaysoperating() {
        return this.daysoperating;
    }

    public String gettimes() {
        return this.times;
    }
     
     public void  setcenternum(String centerno) {
   this.centernum = centerno;
     }

    public void setbusinessname(String entityname) {
        this.businessname=entityname;
    }
      public void setowner(String boss) {
        this.owner=boss;
    }

    public void setemployeelist(String[][][][][] employees) {
        this.employeelist=employees;
    }
      public void setdaysoperating(String days) {
        this.daysoperating=days;
      }

    public void settimes(String open) {
        this.times=open;
    }
    
    
 }
 
}
