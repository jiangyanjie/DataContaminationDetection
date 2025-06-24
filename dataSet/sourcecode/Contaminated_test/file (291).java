
package perpustakaankampus.Entitas;

import java.util.Date;

public class DataBuku {
    private String Kode;
    private String Judul;
    private String Pengarang;
    private String Penerbit;
    private String Tempat;
    private String Tahun;
    private String nim;
    private String kode;
    private Date tanggalpinjam;
    private Date tangggalKembali;
    
    public DataBuku(){
        
    }
    public DataBuku(String nim,String kodepinjam,Date tanggalpinjam,Date tanggalkembali,String kode,String judul,String pengarang,String penerbit,String tempat,String tahun){
        this.nim=nim;
        this.Kode=kodepinjam;
        this.tanggalpinjam=tanggalpinjam;
        this.tangggalKembali=tanggalkembali;
        this.Kode=kode;
        this.Judul=judul;
        this.Pengarang = pengarang;
        this.Penerbit=penerbit;
        this.Tempat=tempat;
        this.Tahun=tahun;
    }
     public String getNim(){
        return nim;
    }
    public void setNim(String nim){
        this.nim=nim;
    }
    public String getKodepinjam(){
        return kode;
    }
    public void setKodepinjam(String kode){
        this.kode=kode;
    }
    public Date getTanggalPinjam(){
        return tanggalpinjam;
    }
    public void setTanggalPinjam(Date tanggalpinjam){
        this.tanggalpinjam=tanggalpinjam;
    }
    public Date getTanggalKembali(){
        return tangggalKembali;
    }
    public void setTanggalkembali(Date tanggalKembali){
        this.tangggalKembali=tanggalKembali;
    }
    public String getKode(){
        return Kode;
    }
    public void setKode(String kode){
        this.Kode = kode;
    }
    public String getJudul(){
        return Judul;
    }
    public void setJudul(String judul){
        this.Judul=judul;
    }
    public String getPengarang(){
        return Pengarang;
    }
    public void setPengarang(String pengarang){
        this.Pengarang=pengarang;
    }
    public String getPenerbit(){
        return Penerbit;
    }
    public void setPenerbit(String Penerbit){
        this.Penerbit=Penerbit;
    }
    public String getTempat(){
        return Tempat;
    }
    public void setTempat(String Tempat){
        this.Tempat=Tempat;
    }
    public String getTahun(){
        return Tahun;
    }
    public void setTahun(String tahun){
        this.Tahun=tahun;
    }
}
