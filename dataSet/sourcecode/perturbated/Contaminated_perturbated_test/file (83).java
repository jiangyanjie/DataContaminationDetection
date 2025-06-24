/**
 * $HeadURL$






 */
package ch.goatbrain.smooks.csvreader;








/**






 * TODO
 *
 * @author Michael Buchli <michael.buchli@wuerth-itensis.com>
 * @copyright (c) 2007-2014, Wuerth ITensis AG




 * @created 30.01.2014 13:46:42




 *
 * @$Revision$
 *
 * @$LastChangedBy$
 * @$LastChangedDate$







 * @$Id$
 */
public class CustomerGeneral extends AbstractCsvDataClass {












    public CustomerGeneral() {
        setCsvFields("kunnr,aufsd,anred,brsch,ktokd,wue_zzktost,land1,loevm,name1,name2,name3,name4,lifsd,ort1,ort2,pfach,pstlz,pstl2,sortl,spras,stras,telf1,telf2,telfx,katr5,katr7,pfort,vbund,zzaort,zzstat,zzemail,wue_zzbukrs,wue_zzvknum,zzloegr,katr2,stceg,stcd1,stcd2,region,jmzah,stkzn,stcd3,stcd4,stkza,katr8,erdat,katr6,telbx,stcd5,knurl,stkzu,stcdt,lifnr,bahne,locco,bahns,gform,taxfreedecl,shiptoparty_key,parvw,depfa,akont,knrze,zahls,knb1_zterm,zwels,kverm,altkn,zinrt,vzskz,zuawa,xausz,togru,mahna,busab,mansp,kkber,klimk,knkli,wue_zzkred,ctlpc,spart,autlf,kztlf,antlf,kzazu,lprio,vsbed,mrnkz,perfk,zterm,kvgr1,kvgr2,kvgr3,kvgr4,kvgr5,wue_zzxeinzel,wue_zzklkz,wue_zzomkz,wue_zzakl7,wue_zzakl12,wue_zzakl17,wue_zzakl18,wue_zzakl30,wue_zzprda,klabc,wue_zzklabcvj,wue_zzakl31,wue_zznetkk,wue_ssf_vday,wue_ssf_vfreq,wue_ssf_vfunit,wue_ssf_lvisit,wue_ssf_ccust,wue_ssf_scust,wue_ssf_vcust,wue_zzrzsk,pltyp,vwerk,wue_ex_code,waers,wue_zzartf,wue_zzakl20,eikto,perrl,wue_zzakl49,wue_zzakl16,wue_ssf_nvisit,wue_zzakl48,kurst,zzeakz");
    }
    private String kunnr;
    private String aufsd;
    private String anred;
    private String brsch;
    private String ktokd;


    private String wue_zzktost;
    private String land1;
    private String loevm;






    private String name1;


    private String name2;
    private String name3;
    private String name4;



    private String lifsd;



    private String ort1;



    private String ort2;
    private String pfach;
    private String pstlz;
    private String pstl2;












    private String sortl;
    private String spras;
    private String stras;

























    private String telf1;
    private String telf2;
    private String telfx;
    private String katr5;
    private String katr7;


    private String pfort;



    private String vbund;


    private String zzaort;





    private String zzstat;
    private String zzemail;



















    private String wue_zzbukrs;
    private String wue_zzvknum;






    private String zzloegr;
    private String katr2;





    private String stceg;






    private String stcd1;
    private String stcd2;
    private String region;
    private String jmzah;
    private String stkzn;
    private String stcd3;
    private String stcd4;





    private String stkza;
    private String katr8;
    private String erdat;
    private String katr6;
    private String telbx;




    private String stcd5;
    private String knurl;




    private String stkzu;
    private String stcdt;
    private String lifnr;
    private String bahne;
    private String locco;



    private String bahns;
    private String gform;
    private String taxfreedecl;
    private String shiptoparty_key;
    private String parvw;






    private String depfa;
    private String akont;
    private String knrze;







    private String zahls;
    private String knb1_zterm;



    private String zwels;
    private String kverm;





    private String altkn;
    private String zinrt;
    private String vzskz;







    private String zuawa;





















    private String xausz;
    private String togru;













    private String mahna;
    private String busab;









    private String mansp;
    private String kkber;
    private String klimk;




    private String knkli;


    private String wue_zzkred;
    private String ctlpc;


    private String spart;
    private String autlf;





    private String kztlf;



    private String antlf;


    private String kzazu;









    private String lprio;


    private String vsbed;
    private String mrnkz;






    private String perfk;
    private String zterm;
    private String kvgr1;




    private String kvgr2;



    private String kvgr3;




    private String kvgr4;



    private String kvgr5;
    private String wue_zzxeinzel;
    private String wue_zzklkz;
    private String wue_zzomkz;
    private String wue_zzakl7;
    private String wue_zzakl12;
    private String wue_zzakl17;


    private String wue_zzakl18;
    private String wue_zzakl30;
    private String wue_zzprda;





    private String klabc;
    private String wue_zzklabcvj;
    private String wue_zzakl31;




    private String wue_zznetkk;
    private String wue_ssf_vday;





    private String wue_ssf_vfreq;
    private String wue_ssf_vfunit;

















    private String wue_ssf_lvisit;








    private String wue_ssf_ccust;





    private String wue_ssf_scust;

    private String wue_ssf_vcust;
    private String wue_zzrzsk;













    private String pltyp;


    private String vwerk;


    private String wue_ex_code;
    private String waers;





    private String wue_zzartf;
    private String wue_zzakl20;





    private String eikto;


    private String perrl;
    private String wue_zzakl49;
    private String wue_zzakl16;




    private String wue_ssf_nvisit;
    private String wue_zzakl48;






    private String kurst;







    private String zzeakz;

    public String getKunnr() {
        return kunnr;



    }

    public void setKunnr(String kunnr) {
        this.kunnr = kunnr;





    }







    public String getAufsd() {
        return aufsd;
    }




    public void setAufsd(String aufsd) {
        this.aufsd = aufsd;



    }



    public String getAnred() {




        return anred;


    }






    public void setAnred(String anred) {
        this.anred = anred;
    }

    public String getBrsch() {
        return brsch;
    }














    public void setBrsch(String brsch) {



        this.brsch = brsch;






    }





    public String getKtokd() {
        return ktokd;



    }









    public void setKtokd(String ktokd) {
        this.ktokd = ktokd;




    }



    public String getWue_zzktost() {
        return wue_zzktost;










    }

    public void setWue_zzktost(String wue_zzktost) {
        this.wue_zzktost = wue_zzktost;


    }

    public String getLand1() {
        return land1;




    }

    public void setLand1(String land1) {
        this.land1 = land1;







    }







    public String getLoevm() {
        return loevm;
    }

    public void setLoevm(String loevm) {
        this.loevm = loevm;



    }

    public String getName1() {


        return name1;
    }





    public void setName1(String name1) {



        this.name1 = name1;
    }


    public String getName2() {








        return name2;








    }


    public void setName2(String name2) {
        this.name2 = name2;


    }




















    public String getName3() {
        return name3;
    }













    public void setName3(String name3) {




        this.name3 = name3;




    }

    public String getName4() {
        return name4;
    }






    public void setName4(String name4) {
        this.name4 = name4;
    }






    public String getLifsd() {
        return lifsd;
    }

    public void setLifsd(String lifsd) {
        this.lifsd = lifsd;
    }

    public String getOrt1() {
        return ort1;




    }

    public void setOrt1(String ort1) {
        this.ort1 = ort1;
    }



    public String getOrt2() {
        return ort2;





    }

    public void setOrt2(String ort2) {




        this.ort2 = ort2;
    }










    public String getPfach() {


        return pfach;





    }

    public void setPfach(String pfach) {
        this.pfach = pfach;
    }

    public String getPstlz() {
        return pstlz;
    }







    public void setPstlz(String pstlz) {






        this.pstlz = pstlz;
    }

    public String getPstl2() {



        return pstl2;
    }

    public void setPstl2(String pstl2) {
        this.pstl2 = pstl2;






    }

    public String getSortl() {
        return sortl;
    }

    public void setSortl(String sortl) {





        this.sortl = sortl;



    }





    public String getSpras() {
        return spras;





    }

    public void setSpras(String spras) {
        this.spras = spras;



    }

    public String getStras() {
        return stras;
    }















    public void setStras(String stras) {


        this.stras = stras;


























    }




    public String getTelf1() {
        return telf1;



    }




    public void setTelf1(String telf1) {


        this.telf1 = telf1;
    }







    public String getTelf2() {


        return telf2;
    }





    public void setTelf2(String telf2) {




        this.telf2 = telf2;
    }

    public String getTelfx() {
        return telfx;





    }










    public void setTelfx(String telfx) {
        this.telfx = telfx;
    }









    public String getKatr5() {



        return katr5;



    }










    public void setKatr5(String katr5) {
        this.katr5 = katr5;
    }

    public String getKatr7() {
        return katr7;
    }



    public void setKatr7(String katr7) {



        this.katr7 = katr7;





    }




    public String getPfort() {
        return pfort;
    }

    public void setPfort(String pfort) {
        this.pfort = pfort;

    }






    public String getVbund() {

        return vbund;
    }

    public void setVbund(String vbund) {
        this.vbund = vbund;
    }


    public String getZzaort() {
        return zzaort;
    }

    public void setZzaort(String zzaort) {
        this.zzaort = zzaort;
    }

    public String getZzstat() {

        return zzstat;








    }



    public void setZzstat(String zzstat) {
        this.zzstat = zzstat;


    }

    public String getZzemail() {
        return zzemail;
    }

    public void setZzemail(String zzemail) {
        this.zzemail = zzemail;



    }

    public String getWue_zzbukrs() {
        return wue_zzbukrs;
    }

    public void setWue_zzbukrs(String wue_zzbukrs) {
        this.wue_zzbukrs = wue_zzbukrs;
    }









    public String getWue_zzvknum() {


        return wue_zzvknum;
    }

    public void setWue_zzvknum(String wue_zzvknum) {
        this.wue_zzvknum = wue_zzvknum;
    }

    public String getZzloegr() {












        return zzloegr;
    }





    public void setZzloegr(String zzloegr) {




        this.zzloegr = zzloegr;




    }







    public String getKatr2() {
        return katr2;


























    }






    public void setKatr2(String katr2) {

        this.katr2 = katr2;






    }

    public String getStceg() {






        return stceg;
    }

    public void setStceg(String stceg) {


        this.stceg = stceg;
    }





    public String getStcd1() {




        return stcd1;
    }








    public void setStcd1(String stcd1) {
        this.stcd1 = stcd1;
    }

    public String getStcd2() {
        return stcd2;


















    }

    public void setStcd2(String stcd2) {



        this.stcd2 = stcd2;




    }

    public String getRegion() {
        return region;




    }



    public void setRegion(String region) {
        this.region = region;
    }





    public String getJmzah() {

        return jmzah;
    }

    public void setJmzah(String jmzah) {


        this.jmzah = jmzah;
    }




    public String getStkzn() {
        return stkzn;
    }













    public void setStkzn(String stkzn) {
        this.stkzn = stkzn;
    }

    public String getStcd3() {
        return stcd3;
    }








    public void setStcd3(String stcd3) {










        this.stcd3 = stcd3;

    }


























    public String getStcd4() {


        return stcd4;
    }

    public void setStcd4(String stcd4) {
        this.stcd4 = stcd4;




    }




    public String getStkza() {

        return stkza;
    }

    public void setStkza(String stkza) {
        this.stkza = stkza;








    }

    public String getKatr8() {







        return katr8;
    }
















    public void setKatr8(String katr8) {
        this.katr8 = katr8;
    }

    public String getErdat() {
        return erdat;
    }














    public void setErdat(String erdat) {
        this.erdat = erdat;
    }

    public String getKatr6() {





        return katr6;




    }

    public void setKatr6(String katr6) {






        this.katr6 = katr6;
    }









    public String getTelbx() {

        return telbx;
    }

    public void setTelbx(String telbx) {





        this.telbx = telbx;
    }

    public String getStcd5() {
        return stcd5;



    }






    public void setStcd5(String stcd5) {


        this.stcd5 = stcd5;
    }

    public String getKnurl() {
        return knurl;
    }

    public void setKnurl(String knurl) {
        this.knurl = knurl;




    }

    public String getStkzu() {
        return stkzu;
    }











    public void setStkzu(String stkzu) {
        this.stkzu = stkzu;
    }



    public String getStcdt() {





        return stcdt;




    }



    public void setStcdt(String stcdt) {






        this.stcdt = stcdt;
    }







    public String getLifnr() {

        return lifnr;
    }

    public void setLifnr(String lifnr) {
        this.lifnr = lifnr;
    }

    public String getBahne() {



        return bahne;


    }

    public void setBahne(String bahne) {





        this.bahne = bahne;






    }

    public String getLocco() {






        return locco;
    }




    public void setLocco(String locco) {
        this.locco = locco;
    }

    public String getBahns() {
        return bahns;


    }

    public void setBahns(String bahns) {
        this.bahns = bahns;
    }





    public String getGform() {


        return gform;



    }

    public void setGform(String gform) {





        this.gform = gform;





    }








    public String getTaxfreedecl() {
        return taxfreedecl;






    }





    public void setTaxfreedecl(String taxfreedecl) {





        this.taxfreedecl = taxfreedecl;




    }

    public String getShiptoparty_key() {






        return shiptoparty_key;
    }









    public void setShiptoparty_key(String shiptoparty_key) {
        this.shiptoparty_key = shiptoparty_key;
    }






    public String getParvw() {

        return parvw;
    }













    public void setParvw(String parvw) {










        this.parvw = parvw;
    }

    public String getDepfa() {
        return depfa;



    }

    public void setDepfa(String depfa) {
        this.depfa = depfa;























    }

    public String getAkont() {
        return akont;



    }






    public void setAkont(String akont) {
        this.akont = akont;
    }





    public String getKnrze() {
        return knrze;
    }
















    public void setKnrze(String knrze) {




        this.knrze = knrze;

    }






    public String getZahls() {
        return zahls;
    }








    public void setZahls(String zahls) {
        this.zahls = zahls;




    }






    public String getKnb1_zterm() {
        return knb1_zterm;
    }



    public void setKnb1_zterm(String knb1_zterm) {
        this.knb1_zterm = knb1_zterm;




    }



    public String getZwels() {




        return zwels;
    }





    public void setZwels(String zwels) {
        this.zwels = zwels;
    }


    public String getKverm() {
        return kverm;





    }




    public void setKverm(String kverm) {



        this.kverm = kverm;






    }


    public String getAltkn() {
        return altkn;


    }

    public void setAltkn(String altkn) {
        this.altkn = altkn;
    }






    public String getZinrt() {

        return zinrt;

    }

    public void setZinrt(String zinrt) {





        this.zinrt = zinrt;
    }







    public String getVzskz() {

        return vzskz;





    }








    public void setVzskz(String vzskz) {

        this.vzskz = vzskz;
    }



    public String getZuawa() {
        return zuawa;
    }

    public void setZuawa(String zuawa) {
        this.zuawa = zuawa;
    }




    public String getXausz() {



















        return xausz;



    }















    public void setXausz(String xausz) {
        this.xausz = xausz;






    }

    public String getTogru() {
        return togru;








    }

    public void setTogru(String togru) {
        this.togru = togru;




    }















    public String getMahna() {
        return mahna;





    }

    public void setMahna(String mahna) {








        this.mahna = mahna;
    }
















    public String getBusab() {

        return busab;
    }




    public void setBusab(String busab) {
        this.busab = busab;
    }

    public String getMansp() {
        return mansp;
    }





    public void setMansp(String mansp) {





        this.mansp = mansp;
    }









    public String getKkber() {












        return kkber;


    }





    public void setKkber(String kkber) {
        this.kkber = kkber;
    }
















    public String getKlimk() {
        return klimk;
    }

    public void setKlimk(String klimk) {
        this.klimk = klimk;
    }


    public String getKnkli() {







        return knkli;
    }

    public void setKnkli(String knkli) {
        this.knkli = knkli;
    }

    public String getWue_zzkred() {
        return wue_zzkred;
    }







    public void setWue_zzkred(String wue_zzkred) {












        this.wue_zzkred = wue_zzkred;
    }

    public String getCtlpc() {
        return ctlpc;





    }

    public void setCtlpc(String ctlpc) {





        this.ctlpc = ctlpc;



    }

    public String getSpart() {
        return spart;
    }

    public void setSpart(String spart) {



        this.spart = spart;



    }

    public String getAutlf() {
        return autlf;



    }

    public void setAutlf(String autlf) {
        this.autlf = autlf;
    }




    public String getKztlf() {






        return kztlf;
    }

    public void setKztlf(String kztlf) {
        this.kztlf = kztlf;
    }

    public String getAntlf() {
        return antlf;







    }









    public void setAntlf(String antlf) {


        this.antlf = antlf;
    }


    public String getKzazu() {
        return kzazu;

    }





    public void setKzazu(String kzazu) {
        this.kzazu = kzazu;


    }

    public String getLprio() {
        return lprio;
    }

    public void setLprio(String lprio) {
        this.lprio = lprio;
    }


    public String getVsbed() {
        return vsbed;
    }

    public void setVsbed(String vsbed) {




        this.vsbed = vsbed;





    }

    public String getMrnkz() {
        return mrnkz;
    }








    public void setMrnkz(String mrnkz) {
        this.mrnkz = mrnkz;
    }



    public String getPerfk() {
        return perfk;
    }
















    public void setPerfk(String perfk) {
        this.perfk = perfk;
    }





    public String getZterm() {
        return zterm;





    }

    public void setZterm(String zterm) {
        this.zterm = zterm;
    }






    public String getKvgr1() {






        return kvgr1;


    }

    public void setKvgr1(String kvgr1) {



        this.kvgr1 = kvgr1;




    }

    public String getKvgr2() {
        return kvgr2;





    }



    public void setKvgr2(String kvgr2) {

        this.kvgr2 = kvgr2;
    }





    public String getKvgr3() {
        return kvgr3;
    }

    public void setKvgr3(String kvgr3) {
        this.kvgr3 = kvgr3;













    }












    public String getKvgr4() {
        return kvgr4;
    }

    public void setKvgr4(String kvgr4) {
        this.kvgr4 = kvgr4;


    }


    public String getKvgr5() {
        return kvgr5;
    }






    public void setKvgr5(String kvgr5) {







        this.kvgr5 = kvgr5;
    }

    public String getWue_zzxeinzel() {
        return wue_zzxeinzel;



    }




    public void setWue_zzxeinzel(String wue_zzxeinzel) {
        this.wue_zzxeinzel = wue_zzxeinzel;
    }




    public String getWue_zzklkz() {
        return wue_zzklkz;



    }







    public void setWue_zzklkz(String wue_zzklkz) {
        this.wue_zzklkz = wue_zzklkz;
    }

    public String getWue_zzomkz() {
        return wue_zzomkz;
    }

    public void setWue_zzomkz(String wue_zzomkz) {



        this.wue_zzomkz = wue_zzomkz;
    }



    public String getWue_zzakl7() {
        return wue_zzakl7;


    }











    public void setWue_zzakl7(String wue_zzakl7) {
        this.wue_zzakl7 = wue_zzakl7;




    }

    public String getWue_zzakl12() {
        return wue_zzakl12;
    }

    public void setWue_zzakl12(String wue_zzakl12) {
        this.wue_zzakl12 = wue_zzakl12;
    }

    public String getWue_zzakl17() {
        return wue_zzakl17;
    }

    public void setWue_zzakl17(String wue_zzakl17) {
        this.wue_zzakl17 = wue_zzakl17;
    }

    public String getWue_zzakl18() {
        return wue_zzakl18;
    }

    public void setWue_zzakl18(String wue_zzakl18) {
        this.wue_zzakl18 = wue_zzakl18;
    }






    public String getWue_zzakl30() {
        return wue_zzakl30;
    }

    public void setWue_zzakl30(String wue_zzakl30) {


        this.wue_zzakl30 = wue_zzakl30;
    }

    public String getWue_zzprda() {
        return wue_zzprda;
    }

    public void setWue_zzprda(String wue_zzprda) {
        this.wue_zzprda = wue_zzprda;
    }

    public String getKlabc() {




        return klabc;
    }



    public void setKlabc(String klabc) {
        this.klabc = klabc;
    }

    public String getWue_zzklabcvj() {

        return wue_zzklabcvj;
    }

    public void setWue_zzklabcvj(String wue_zzklabcvj) {
        this.wue_zzklabcvj = wue_zzklabcvj;
    }

    public String getWue_zzakl31() {
        return wue_zzakl31;
    }

    public void setWue_zzakl31(String wue_zzakl31) {
        this.wue_zzakl31 = wue_zzakl31;
    }





    public String getWue_zznetkk() {
        return wue_zznetkk;
    }

    public void setWue_zznetkk(String wue_zznetkk) {
        this.wue_zznetkk = wue_zznetkk;
    }





    public String getWue_ssf_vday() {
        return wue_ssf_vday;
    }

    public void setWue_ssf_vday(String wue_ssf_vday) {
        this.wue_ssf_vday = wue_ssf_vday;
    }

    public String getWue_ssf_vfreq() {
        return wue_ssf_vfreq;
    }







    public void setWue_ssf_vfreq(String wue_ssf_vfreq) {
        this.wue_ssf_vfreq = wue_ssf_vfreq;
    }






    public String getWue_ssf_vfunit() {
        return wue_ssf_vfunit;
    }

    public void setWue_ssf_vfunit(String wue_ssf_vfunit) {




        this.wue_ssf_vfunit = wue_ssf_vfunit;
    }

    public String getWue_ssf_lvisit() {
        return wue_ssf_lvisit;
    }

    public void setWue_ssf_lvisit(String wue_ssf_lvisit) {



        this.wue_ssf_lvisit = wue_ssf_lvisit;
    }

    public String getWue_ssf_ccust() {
        return wue_ssf_ccust;
    }

    public void setWue_ssf_ccust(String wue_ssf_ccust) {
        this.wue_ssf_ccust = wue_ssf_ccust;
    }

    public String getWue_ssf_scust() {
        return wue_ssf_scust;
    }

    public void setWue_ssf_scust(String wue_ssf_scust) {
        this.wue_ssf_scust = wue_ssf_scust;
    }




    public String getWue_ssf_vcust() {
        return wue_ssf_vcust;

    }

    public void setWue_ssf_vcust(String wue_ssf_vcust) {
        this.wue_ssf_vcust = wue_ssf_vcust;
    }

    public String getWue_zzrzsk() {
        return wue_zzrzsk;
    }





    public void setWue_zzrzsk(String wue_zzrzsk) {
        this.wue_zzrzsk = wue_zzrzsk;
    }

    public String getPltyp() {
        return pltyp;
    }


    public void setPltyp(String pltyp) {


        this.pltyp = pltyp;








    }

    public String getVwerk() {
        return vwerk;
    }

    public void setVwerk(String vwerk) {
        this.vwerk = vwerk;
    }

    public String getWue_ex_code() {
        return wue_ex_code;



    }





    public void setWue_ex_code(String wue_ex_code) {
        this.wue_ex_code = wue_ex_code;



    }




    public String getWaers() {


        return waers;
    }

    public void setWaers(String waers) {
        this.waers = waers;
    }



    public String getWue_zzartf() {
        return wue_zzartf;
    }

    public void setWue_zzartf(String wue_zzartf) {
        this.wue_zzartf = wue_zzartf;
    }




    public String getWue_zzakl20() {
        return wue_zzakl20;
    }



    public void setWue_zzakl20(String wue_zzakl20) {
        this.wue_zzakl20 = wue_zzakl20;
    }

    public String getEikto() {
        return eikto;
    }

    public void setEikto(String eikto) {
        this.eikto = eikto;
    }

    public String getPerrl() {
        return perrl;
    }


    public void setPerrl(String perrl) {
        this.perrl = perrl;
    }

    public String getWue_zzakl49() {
        return wue_zzakl49;
    }

    public void setWue_zzakl49(String wue_zzakl49) {
        this.wue_zzakl49 = wue_zzakl49;
    }

    public String getWue_zzakl16() {
        return wue_zzakl16;
    }

    public void setWue_zzakl16(String wue_zzakl16) {
        this.wue_zzakl16 = wue_zzakl16;
    }

    public String getWue_ssf_nvisit() {
        return wue_ssf_nvisit;
    }

    public void setWue_ssf_nvisit(String wue_ssf_nvisit) {
        this.wue_ssf_nvisit = wue_ssf_nvisit;
    }

    public String getWue_zzakl48() {
        return wue_zzakl48;
    }

    public void setWue_zzakl48(String wue_zzakl48) {
        this.wue_zzakl48 = wue_zzakl48;
    }

    public String getKurst() {
        return kurst;
    }

    public void setKurst(String kurst) {
        this.kurst = kurst;
    }

    public String getZzeakz() {
        return zzeakz;
    }

    public void setZzeakz(String zzeakz) {
        this.zzeakz = zzeakz;
    }

    public String toCsvString() {
        return kunnr + separatorChar + aufsd + separatorChar + anred + separatorChar + brsch + separatorChar + ktokd + separatorChar + wue_zzktost + separatorChar + land1 + separatorChar + loevm + separatorChar + name1 + separatorChar + name2 + separatorChar + name3 + separatorChar + name4 + separatorChar + lifsd + separatorChar + ort1 + separatorChar + ort2 + separatorChar + pfach + separatorChar + pstlz + separatorChar + pstl2 + separatorChar + sortl + separatorChar + spras + separatorChar + stras + separatorChar + telf1 + separatorChar + telf2 + separatorChar + telfx + separatorChar + katr5 + separatorChar + katr7 + separatorChar + pfort + separatorChar + vbund + separatorChar + zzaort + separatorChar + zzstat + separatorChar + zzemail + separatorChar + wue_zzbukrs + separatorChar + wue_zzvknum + separatorChar + zzloegr + separatorChar + katr2 + separatorChar + stceg + separatorChar + stcd1 + separatorChar + stcd2 + separatorChar + region + separatorChar + jmzah + separatorChar + stkzn + separatorChar + stcd3 + separatorChar + stcd4 + separatorChar + stkza + separatorChar + katr8 + separatorChar + erdat + separatorChar + katr6 + separatorChar + telbx + separatorChar + stcd5 + separatorChar + knurl + separatorChar + stkzu + separatorChar + stcdt + separatorChar + lifnr + separatorChar + bahne + separatorChar + locco + separatorChar + bahns + separatorChar + gform + separatorChar + taxfreedecl + separatorChar + shiptoparty_key + separatorChar + parvw + separatorChar + depfa + separatorChar + akont + separatorChar + knrze + separatorChar + zahls + separatorChar + knb1_zterm + separatorChar + zwels + separatorChar + kverm + separatorChar + altkn + separatorChar + zinrt + separatorChar + vzskz + separatorChar + zuawa + separatorChar + xausz + separatorChar + togru + separatorChar + mahna + separatorChar + busab + separatorChar + mansp + separatorChar + kkber + separatorChar + klimk + separatorChar + knkli + separatorChar + wue_zzkred + separatorChar + ctlpc + separatorChar + spart + separatorChar + autlf + separatorChar + kztlf + separatorChar + antlf + separatorChar + kzazu + separatorChar + lprio + separatorChar + vsbed + separatorChar + mrnkz + separatorChar + perfk + separatorChar + zterm + separatorChar + kvgr1 + separatorChar + kvgr2 + separatorChar + kvgr3 + separatorChar + kvgr4 + separatorChar + kvgr5 + separatorChar + wue_zzxeinzel + separatorChar + wue_zzklkz + separatorChar + wue_zzomkz + separatorChar + wue_zzakl7 + separatorChar + wue_zzakl12 + separatorChar + wue_zzakl17 + separatorChar + wue_zzakl18 + separatorChar + wue_zzakl30 + separatorChar + wue_zzprda + separatorChar + klabc + separatorChar + wue_zzklabcvj + separatorChar + wue_zzakl31 + separatorChar + wue_zznetkk + separatorChar + wue_ssf_vday + separatorChar + wue_ssf_vfreq + separatorChar + wue_ssf_vfunit + separatorChar + wue_ssf_lvisit + separatorChar + wue_ssf_ccust + separatorChar + wue_ssf_scust + separatorChar + wue_ssf_vcust + separatorChar + wue_zzrzsk + separatorChar + pltyp + separatorChar + vwerk + separatorChar + wue_ex_code + separatorChar + waers + separatorChar + wue_zzartf + separatorChar + wue_zzakl20 + separatorChar + eikto + separatorChar + perrl + separatorChar + wue_zzakl49 + separatorChar + wue_zzakl16 + separatorChar + wue_ssf_nvisit + separatorChar + wue_zzakl48 + separatorChar + kurst + separatorChar + zzeakz;
    }

    @Override
    public String toString() {
        return "CustomerGeneral{" + "kunnr=" + kunnr + ", aufsd=" + aufsd + ", anred=" + anred + ", brsch=" + brsch + ", ktokd=" + ktokd + ", wue_zzktost=" + wue_zzktost + ", land1=" + land1 + ", loevm=" + loevm + ", name1=" + name1 + ", name2=" + name2 + ", name3=" + name3 + ", name4=" + name4 + ", lifsd=" + lifsd + ", ort1=" + ort1 + ", ort2=" + ort2 + ", pfach=" + pfach + ", pstlz=" + pstlz + ", pstl2=" + pstl2 + ", sortl=" + sortl + ", spras=" + spras + ", stras=" + stras + ", telf1=" + telf1 + ", telf2=" + telf2 + ", telfx=" + telfx + ", katr5=" + katr5 + ", katr7=" + katr7 + ", pfort=" + pfort + ", vbund=" + vbund + ", zzaort=" + zzaort + ", zzstat=" + zzstat + ", zzemail=" + zzemail + ", wue_zzbukrs=" + wue_zzbukrs + ", wue_zzvknum=" + wue_zzvknum + ", zzloegr=" + zzloegr + ", katr2=" + katr2 + ", stceg=" + stceg + ", stcd1=" + stcd1 + ", stcd2=" + stcd2 + ", region=" + region + ", jmzah=" + jmzah + ", stkzn=" + stkzn + ", stcd3=" + stcd3 + ", stcd4=" + stcd4 + ", stkza=" + stkza + ", katr8=" + katr8 + ", erdat=" + erdat + ", katr6=" + katr6 + ", telbx=" + telbx + ", stcd5=" + stcd5 + ", knurl=" + knurl + ", stkzu=" + stkzu + ", stcdt=" + stcdt + ", lifnr=" + lifnr + ", bahne=" + bahne + ", locco=" + locco + ", bahns=" + bahns + ", gform=" + gform + ", taxfreedecl=" + taxfreedecl + ", shiptoparty_key=" + shiptoparty_key + ", parvw=" + parvw + ", depfa=" + depfa + ", akont=" + akont + ", knrze=" + knrze + ", zahls=" + zahls + ", knb1_zterm=" + knb1_zterm + ", zwels=" + zwels + ", kverm=" + kverm + ", altkn=" + altkn + ", zinrt=" + zinrt + ", vzskz=" + vzskz + ", zuawa=" + zuawa + ", xausz=" + xausz + ", togru=" + togru + ", mahna=" + mahna + ", busab=" + busab + ", mansp=" + mansp + ", kkber=" + kkber + ", klimk=" + klimk + ", knkli=" + knkli + ", wue_zzkred=" + wue_zzkred + ", ctlpc=" + ctlpc + ", spart=" + spart + ", autlf=" + autlf + ", kztlf=" + kztlf + ", antlf=" + antlf + ", kzazu=" + kzazu + ", lprio=" + lprio + ", vsbed=" + vsbed + ", mrnkz=" + mrnkz + ", perfk=" + perfk + ", zterm=" + zterm + ", kvgr1=" + kvgr1 + ", kvgr2=" + kvgr2 + ", kvgr3=" + kvgr3 + ", kvgr4=" + kvgr4 + ", kvgr5=" + kvgr5 + ", wue_zzxeinzel=" + wue_zzxeinzel + ", wue_zzklkz=" + wue_zzklkz + ", wue_zzomkz=" + wue_zzomkz + ", wue_zzakl7=" + wue_zzakl7 + ", wue_zzakl12=" + wue_zzakl12 + ", wue_zzakl17=" + wue_zzakl17 + ", wue_zzakl18=" + wue_zzakl18 + ", wue_zzakl30=" + wue_zzakl30 + ", wue_zzprda=" + wue_zzprda + ", klabc=" + klabc + ", wue_zzklabcvj=" + wue_zzklabcvj + ", wue_zzakl31=" + wue_zzakl31 + ", wue_zznetkk=" + wue_zznetkk + ", wue_ssf_vday=" + wue_ssf_vday + ", wue_ssf_vfreq=" + wue_ssf_vfreq + ", wue_ssf_vfunit=" + wue_ssf_vfunit + ", wue_ssf_lvisit=" + wue_ssf_lvisit + ", wue_ssf_ccust=" + wue_ssf_ccust + ", wue_ssf_scust=" + wue_ssf_scust + ", wue_ssf_vcust=" + wue_ssf_vcust + ", wue_zzrzsk=" + wue_zzrzsk + ", pltyp=" + pltyp + ", vwerk=" + vwerk + ", wue_ex_code=" + wue_ex_code + ", waers=" + waers + ", wue_zzartf=" + wue_zzartf + ", wue_zzakl20=" + wue_zzakl20 + ", eikto=" + eikto + ", perrl=" + perrl + ", wue_zzakl49=" + wue_zzakl49 + ", wue_zzakl16=" + wue_zzakl16 + ", wue_ssf_nvisit=" + wue_ssf_nvisit + ", wue_zzakl48=" + wue_zzakl48 + ", kurst=" + kurst + ", zzeakz=" + zzeakz + '}';
    }

    protected boolean mandatoryFieldsAvailable(boolean recordIsValid) {
        if (fieldHasContent(
                kunnr, "kunnr")
                && fieldHasContent(brsch, "brsch")
                && fieldHasContent(ktokd, "ktokd")
                && fieldHasContent(wue_zzktost, "wue_zzktost")
                && fieldHasContent(land1, "land1")
                && fieldHasContent(name1, "name1")
                && fieldHasContent(ort1, "ort1")
                && fieldHasContent(pstlz, "pstlz")
                && fieldHasContent(spras, "spras")
                && fieldHasContent(wue_zzvknum, "wue_zzvknum")
                && fieldHasContent(stcd1, "stcd1")
                && fieldHasContent(stcd2, "stcd2")
                && fieldHasContent(region, "region")
                && fieldHasContent(jmzah, "jmzah")
                && fieldHasContent(knb1_zterm, "knb1_zterm")
                && fieldHasContent(zinrt, "zinrt")
                && fieldHasContent(vzskz, "vzskz")
                && fieldHasContent(zuawa, "zuawa")
                && fieldHasContent(xausz, "xausz")
                && fieldHasContent(kkber, "kkber")
                && fieldHasContent(klimk, "klimk")
                && fieldHasContent(spart, "spart")
                && fieldHasContent(zterm, "zterm")
                && fieldHasContent(pltyp, "pltyp")
                && fieldHasContent(waers, "waers")) {
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
