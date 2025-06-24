/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bandarra.dbf.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author andre.bandarra
 */
public class DBFFile {
    private static final byte MAGIC_NUMBER = 0x03;
    private static final byte MAGIC_NUMBER_MEMO = (byte) 0x83;
    private static final byte DELETED_FLAG = 0x2A;
    private static final byte ACTIVE_FLAG = 0x20;
    private DBFMetaData metaData;
    private RandomAccessFile file;
    
    private Date calcDate(byte year, byte month, byte day){
        int iyear = year < 60 ? 2000 + year : 1900 + year;        
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(iyear, month+1, day);        
        return gc.getTime();
    }
    
    private void readMetaData() throws IOException{
        setMetaData(new DBFMetaData());
        synchronized(file){
            file.seek(0);
            byte magic = file.readByte();
            if (magic != MAGIC_NUMBER && magic != MAGIC_NUMBER_MEMO){
                throw new IOException("Invalid DBF File");
            }

            byte year = file.readByte();
            byte month = file.readByte();
            byte day = file.readByte();
            getMetaData().setLastUpdate(calcDate(year, month, day));
            
            getMetaData().setNumberOfRecords(
                    Integer.reverseBytes(file.readInt()));
            
            getMetaData().setBytesInHeader(
                    Short.reverseBytes(file.readShort()));
            
            getMetaData().setBytesInRecord(
                    Short.reverseBytes(file.readShort()));
            
            int qtRecords = (getMetaData().getBytesInHeader() - 33) / 32;
            file.skipBytes(20);
            byte[] bName = new byte[11];
            int offsetInRecord = 1;
            for (int i=0; i<qtRecords; i++){
                file.readFully(bName);
                String fieldName = new String(bName, "US-ASCII").trim();
                char type = (char)file.readByte();
                file.skipBytes(4);
                byte fieldLength = file.readByte();
                byte decimalCount = file.readByte();                
                file.skipBytes(14);
                DBFField field = new DBFField(fieldName, type, fieldLength,
                        decimalCount);
                field.setOffsetInRecord(offsetInRecord);
                offsetInRecord += fieldLength;
                getMetaData().getFields().put(fieldName, field);
            }
            
        }
    }
    
    public DBFFile(String file) throws FileNotFoundException, IOException{
        this.file = new RandomAccessFile(file, "r");        
        readMetaData();        
    }
    
    public DBFFile(File f) throws FileNotFoundException, IOException{
        this.file = new RandomAccessFile(f, "r");        
        readMetaData();
    }

    public DBFMetaData getMetaData() {
        return metaData;
    }

    void setMetaData(DBFMetaData metaData) {
        this.metaData = metaData;
    }
    
    public boolean isDeleted(int record) throws IOException{
        synchronized (file){
            file.seek(getMetaData().getBytesInHeader() + record * getMetaData().getBytesInRecord());
            byte b = file.readByte();
            if (b == DELETED_FLAG){
                return false;
            } else if (b == ACTIVE_FLAG){
                return true;
            } else {
                throw new IOException("Invalid Record Flag");
            }
        }
    }

    public byte[] getData(String field, int record) throws IOException{
        DBFField dbField = getMetaData().getFields().get(field);
        byte[] data = new byte[dbField.getLength()];           
        synchronized (file){
            file.seek(getMetaData().getBytesInHeader() + 
                    record * getMetaData().getBytesInRecord() + dbField.getOffsetInRecord());
            file.readFully(data);
            return data;
        }
    }
    
    public void close() throws IOException{
        file.close();
    }    
}
