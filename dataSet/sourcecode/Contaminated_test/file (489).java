/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bandarra.dbf.file;

/**
 *
 * @author andre.bandarra
 */
public class DBFField {
    private String name;
    private DBFFieldType type;
    private byte length;
    private byte decimalCount;
    private int offsetInRecord;
    
    DBFField(String name, char type, byte length, byte decimalCount){
        setName(name);
        setType(type);
        setLength(length);
        setDecimalcount(decimalCount);        
    }
    
    DBFField(String name, DBFFieldType type, byte length, byte decimalCount){
        setName(name);
        setType(type);
        setLength(length);
        setDecimalcount(decimalCount);
    }
    
    void setName(String name){
        this.name = name;
    }
    
    void setType(char type){
        switch (type){
            case 'C': this.type = DBFFieldType.CHARACTER; break;
            case 'D': this.type = DBFFieldType.DATE; break;            
            case 'N': this.type = DBFFieldType.NUMERIC; break;                        
            case 'L': this.type = DBFFieldType.LOGICAL; break;
            case 'M': this.type = DBFFieldType.MEMO; break;            
        }
        
    }
    
    void setType(DBFFieldType type){
        this.type = type;        
    }
    
    void setLength(byte length){
        this.length = length;
    }
    
    void setDecimalcount(byte decimalCount){
        this.decimalCount = decimalCount;
    }
  
    public String getName(){
        return this.name;
    }
    
    public DBFFieldType getType(){
        return this.type;
    }
    
    public byte getLength(){
        return this.length;
    }
    
    public byte getDecimalCount(){
        return this.decimalCount;
    }
    
    @Override
    public String toString(){
        return name.toString();
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DBFField other = (DBFField) obj;
        if (this.name != other.name && 
                (this.name == null || !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    public int getOffsetInRecord() {
        return offsetInRecord;
    }

    void setOffsetInRecord(int offsetInRecord) {
        this.offsetInRecord = offsetInRecord;
    }
    
    

}
