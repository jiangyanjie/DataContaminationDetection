
package CreateXML;
import java.util.ArrayList;
import java.util.Scanner;
import sqlite.*;
import java.io.*;
import java.util.Arrays;
import CreateXML.*;

public class CreateXml {
    //�������� xml �����
   private String nameXml;
   //��� �������� �� ����������� � ��� ��������(�����)
   private ArrayList<String> rowNameAndValue;
   //��� �������� �� ��������� � ��� ��������(�����)
   private ArrayList<String> columnNameAndValue;
   private ArrayList<String> section;
   //������ ���������� �������� ���� ���������
   private ArrayList<String> dimension ; 
   //�������� ����������� ���������
   private ArrayList<String> control;
    
    public CreateXml(){
        String[] array = {"Apple", "Store", "Time"};
        this.dimension = new ArrayList<String>( Arrays.asList(array));
        this.control = new ArrayList<String>(Arrays.asList(array));
        this.section = new ArrayList<String>();
        this.rowNameAndValue = new ArrayList<String>();
        this.columnNameAndValue = new ArrayList<String>();
    }
    
    void getDimension(ArrayList<String> al){
        Scanner sc = new Scanner(System.in);
        Sqlite.getResultSet(new File("").getAbsolutePath()+"\\Apple","Select * from "+al.get(0));
            System.out.println("������� ����� : ");
                al.add(sc.nextLine());
    }
    public void buildXml(){ 
        Scanner sc = new Scanner(System.in);
        String[] array = {"Apple", "Store", "Time"};
        for(int i = 0; i < 3; i++){
            this.section.add(this.dimension.get(i));
            System.out.println("����������� ��������� : "+this.section.get(0));
            this.getDimension(this.section);
            this.control.remove(i);
            System.out.println("��������� ��  ��������� : ");
            System.out.println("1."+this.control.get(0));
            System.out.println("2."+this.control.get(1));
            int column = Integer.valueOf(sc.nextLine())-1;
            this.columnNameAndValue.add(this.control.get(column));
            this.getDimension(this.columnNameAndValue);
            this.control.remove(column);
            
            System.out.println("��������� �� ����������� : "+this.control.get(0));
            this.rowNameAndValue.add(this.control.get(0));
            this.control.remove(0);
            this.getDimension(this.rowNameAndValue);
            this.control = new ArrayList<String>(Arrays.asList(array));
            BuildXML.buildXML(this.rowNameAndValue, this.columnNameAndValue, this.section);
            this.section.clear();this.columnNameAndValue.clear();this.rowNameAndValue.clear();
        }
            
    }
}
