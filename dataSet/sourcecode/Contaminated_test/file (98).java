/**
 * $HeadURL$
 */
package ch.goatbrain.smooks.csvreader;

/**
 * TODO
 *
 * @author Michael Buchli <michael.buchli@wuerth-itensis.com>
 * @copyright (c) 2007-2014, Wuerth ITensis AG
 * @created 31.01.2014 10:24:42
 *
 * @$Revision$
 *
 * @$LastChangedBy$
 * @$LastChangedDate$
 * @$Id$
 */
public class CustomerSknvk extends AbstractCsvDataClass {

    public CustomerSknvk() {
        setCsvFields("kunnr,abtnr,parla,wue_zzanre,namev,knvk_name1,pakn5,pafkt,knvk_telf1,wue_zzteltxe,wue_zztelfx,knvk_zzemail,parau,wue_zzgbdat,titel_ap,knvk_sortl,parh1,parh2,parh3,parh4,parh5");
    }
    private String kunnr;
    private String abtnr;
    private String parla;
    private String wue_zzanre;
    private String namev;
    private String knvk_name1;
    private String pakn5;
    private String pafkt;
    private String knvk_telf1;
    private String wue_zzteltxe;
    private String wue_zztelfx;
    private String knvk_zzemail;
    private String parau;
    private String wue_zzgbdat;
    private String titel_ap;
    private String knvk_sortl;
    private String parh1;
    private String parh2;
    private String parh3;
    private String parh4;
    private String parh5;

    public String getKunnr() {
        return kunnr;
    }

    public void setKunnr(String kunnr) {
        this.kunnr = kunnr;
    }

    public String getAbtnr() {
        return abtnr;
    }

    public void setAbtnr(String abtnr) {
        this.abtnr = abtnr;
    }

    public String getParla() {
        return parla;
    }

    public void setParla(String parla) {
        this.parla = parla;
    }

    public String getWue_zzanre() {
        return wue_zzanre;
    }

    public void setWue_zzanre(String wue_zzanre) {
        this.wue_zzanre = wue_zzanre;
    }

    public String getNamev() {
        return namev;
    }

    public void setNamev(String namev) {
        this.namev = namev;
    }

    public String getKnvk_name1() {
        return knvk_name1;
    }

    public void setKnvk_name1(String knvk_name1) {
        this.knvk_name1 = knvk_name1;
    }

    public String getPakn5() {
        return pakn5;
    }

    public void setPakn5(String pakn5) {
        this.pakn5 = pakn5;
    }

    public String getPafkt() {
        return pafkt;
    }

    public void setPafkt(String pafkt) {
        this.pafkt = pafkt;
    }

    public String getKnvk_telf1() {
        return knvk_telf1;
    }

    public void setKnvk_telf1(String knvk_telf1) {
        this.knvk_telf1 = knvk_telf1;
    }

    public String getWue_zzteltxe() {
        return wue_zzteltxe;
    }

    public void setWue_zzteltxe(String wue_zzteltxe) {
        this.wue_zzteltxe = wue_zzteltxe;
    }

    public String getWue_zztelfx() {
        return wue_zztelfx;
    }

    public void setWue_zztelfx(String wue_zztelfx) {
        this.wue_zztelfx = wue_zztelfx;
    }

    public String getKnvk_zzemail() {
        return knvk_zzemail;
    }

    public void setKnvk_zzemail(String knvk_zzemail) {
        this.knvk_zzemail = knvk_zzemail;
    }

    public String getParau() {
        return parau;
    }

    public void setParau(String parau) {
        this.parau = parau;
    }

    public String getWue_zzgbdat() {
        return wue_zzgbdat;
    }

    public void setWue_zzgbdat(String wue_zzgbdat) {
        this.wue_zzgbdat = wue_zzgbdat;
    }

    public String getTitel_ap() {
        return titel_ap;
    }

    public void setTitel_ap(String titel_ap) {
        this.titel_ap = titel_ap;
    }

    public String getKnvk_sortl() {
        return knvk_sortl;
    }

    public void setKnvk_sortl(String knvk_sortl) {
        this.knvk_sortl = knvk_sortl;
    }

    public String getParh1() {
        return parh1;
    }

    public void setParh1(String parh1) {
        this.parh1 = parh1;
    }

    public String getParh2() {
        return parh2;
    }

    public void setParh2(String parh2) {
        this.parh2 = parh2;
    }

    public String getParh3() {
        return parh3;
    }

    public void setParh3(String parh3) {
        this.parh3 = parh3;
    }

    public String getParh4() {
        return parh4;
    }

    public void setParh4(String parh4) {
        this.parh4 = parh4;
    }

    public String getParh5() {
        return parh5;
    }

    public void setParh5(String parh5) {
        this.parh5 = parh5;
    }

    public String toCsvString() {
        return kunnr + separatorChar + abtnr + separatorChar + parla + separatorChar + wue_zzanre + separatorChar + namev + separatorChar + knvk_name1 + separatorChar + pakn5 + separatorChar + pafkt + separatorChar + knvk_telf1 + separatorChar + wue_zzteltxe + separatorChar + wue_zztelfx + separatorChar + knvk_zzemail + separatorChar + parau + separatorChar + wue_zzgbdat + separatorChar + titel_ap + separatorChar + knvk_sortl + separatorChar + parh1 + separatorChar + parh2 + separatorChar + parh3 + separatorChar + parh4 + separatorChar + parh5;
    }

    @Override
    public String toString() {
        return "CustomerSknvk{" + "kunnr=" + kunnr + ", abtnr=" + abtnr + ", parla=" + parla + ", wue_zzanre=" + wue_zzanre + ", namev=" + namev + ", knvk_name1=" + knvk_name1 + ", pakn5=" + pakn5 + ", pafkt=" + pafkt + ", knvk_telf1=" + knvk_telf1 + ", wue_zzteltxe=" + wue_zzteltxe + ", wue_zztelfx=" + wue_zztelfx + ", knvk_zzemail=" + knvk_zzemail + ", parau=" + parau + ", wue_zzgbdat=" + wue_zzgbdat + ", titel_ap=" + titel_ap + ", knvk_sortl=" + knvk_sortl + ", parh1=" + parh1 + ", parh2=" + parh2 + ", parh3=" + parh3 + ", parh4=" + parh4 + ", parh5=" + parh5 + '}';
    }

    protected boolean mandatoryFieldsAvailable(boolean recordIsValid) {
        if (fieldHasContent(kunnr, "kunnr")
                && fieldHasContent(abtnr, "abtnr")
                && fieldHasContent(parla, "parla")
                && fieldHasContent(wue_zzanre, "wue_zzanre")
                && fieldHasContent(knvk_name1, "knvk_name1")
                && fieldHasContent(pakn5, "pakn5")
                && fieldHasContent(pafkt, "pafkt")) {
            recordIsValid = true;
        }
        return recordIsValid;
    }

    @Override
    protected boolean fieldContentIsValid(boolean recordIsValid) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return true;
    }
}
