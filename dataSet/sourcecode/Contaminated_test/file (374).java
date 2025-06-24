
package DBGDPV3;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DATARS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DATARS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SRVRTID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GDA_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CACHE_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SECTION" type="{http://www.dnb.com/DNB_WebServices/Providers/OrderAndInvestigations/GDP_V3/wsp_GDP_V3}SECTION2" minOccurs="0"/>
 *         &lt;element name="DUNS_NBR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PRIM_NME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ADR_LINE" type="{http://www.dnb.com/DNB_WebServices/Providers/OrderAndInvestigations/GDP_V3/wsp_GDP_V3}ArrayOfstring" minOccurs="0"/>
 *         &lt;element name="POST_TOWN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PRIM_GEO_AREA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="POST_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CTRY_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BR_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ACT_PAY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ACT_REC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ACTIVITY_TEXT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ADDL_SIC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ADDL_SIC_TYPE_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ADR_TENR_TYPE_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ANN_SALE_CONS_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ANN_SALE_CRCY_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ANN_SALE_ESTD_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ANN_SALE_VOL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ANN_SALE_VOL_TEXT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ASET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AUDT_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AUDT_QLFN_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AVG_HIGH_CR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BUS_REGN_NBR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BUS_REGN_NBR_TYPE_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BUS_STRU" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CAPL_AMT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CAPL_CRCY_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CAPL_TYPE_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CASH_LIQ_ASET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CEO_NME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CEO_TITL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CLM_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CONS_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COST_OF_SLS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CR_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CR_SCR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CR_SCR_CMTY" type="{http://www.dnb.com/DNB_WebServices/Providers/OrderAndInvestigations/GDP_V3/wsp_GDP_V3}ArrayOfstring" minOccurs="0"/>
 *         &lt;element name="CRCY_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CRIM_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CURR_CNTL_YR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CURR_RATO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DBTR_IN_POSN_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DIVD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CR_SCR_CLAS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DNB_RATG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DOM_ULT_CTRY_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DOM_ULT_PNT_DUNS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DOM_ULT_PNT_NME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DSR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DSTR_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EMPL_AT_PRIM_ADR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EMPL_AT_PRIM_ADR_ESTD_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EMPL_AT_PRIM_ADR_MIN_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EMPL_AT_PRIM_ADR_TEXT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ESTD_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EXPT_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FAIL_SCR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FAIL_SCR_12_MO_AGO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FAIL_SCR_6_MO_AGO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FAIL_SCR_CMTY" type="{http://www.dnb.com/DNB_WebServices/Providers/OrderAndInvestigations/GDP_V3/wsp_GDP_V3}ArrayOfstring" minOccurs="0"/>
 *         &lt;element name="FAIL_SCR_IND_PCTG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FAIL_SCR_NATL_PCTG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FAIL_SCR_OVRD_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FAX_NBR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FCST_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FINL_EMBT_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FINL_LGL_EVNT_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FISC_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FIXD_ASET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FLNG_CRCY_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FLNG_NBR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FLNG_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FLNG_VAL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FNAL_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GBL_ULT_CTRY_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GBL_ULT_PNT_DUNS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GBL_ULT_PNT_NME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SER_RAT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GROS_INCM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HIGH_CR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HIST_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HQ_CTRY_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HQ_DUNS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HQ_NME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IMPT_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="INCM_STMT_DT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="INCN_YR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="INTL_DLNG_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ITNG_ASET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LCL_ACTV_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LCL_ACTV_CD_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LGL_FORM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LOCN_STAT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LT_DBT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MAX_CR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MAX_CR_CRCY_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NBR_NEG_PMT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NET_INCM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NET_WRTH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NON_POST_TOWN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OPEN_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OPRG_SPEC_EVNT_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OTHR_SPEC_EVNT_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OUT_BUS_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PAYD_3_MO_AGO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PAYD_NORM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PAYD_SCR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PNT_CTRY_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PNT_DUNS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PNT_NME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PREV_NET_WRTH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PREV_SLS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PREV_STMT_DT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PREV_WRKG_CAPL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PRIM_SIC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PRIM_SIC_TYPE_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PRO_FRMA_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="QK_RATO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="REGN_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="REST_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RETN_ERNG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SCDY_GEO_AREA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SCRD_FLNG_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SD_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SGND_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SLS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STAT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STK" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STMT_CRCY_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STMT_DT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STMT_FROM_DT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STMT_TO_DT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STRT_YR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SUIT_JDGT_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TANG_NET_WRTH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TANG_NET_WRTH_CRCY_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TANG_NET_WRTH_ESTD_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TANG_NET_WRTH_TEXT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TLCM_NBR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TOT_ASET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TOT_CURR_ASET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TOT_CURR_LIAB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TOT_EMPL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TOT_EMPL_ESTD_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TOT_EMPL_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TOT_EMPL_MIN_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TOT_EMPL_TEXT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TOT_LIAB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TOT_LIAB_AND_EQY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TOT_LT_LIAB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TOT_PMT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TRDG_STYL" type="{http://www.dnb.com/DNB_WebServices/Providers/OrderAndInvestigations/GDP_V3/wsp_GDP_V3}ArrayOfstring" minOccurs="0"/>
 *         &lt;element name="TRL_BAL_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UBAL_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ADDL_SIC_DESC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ENQ_DUNS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PRIN_NME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PRIN_TTL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PFT_BEF_TAX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CNGL_DIST_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LBR_SPLS_AREA_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LBR_SPLS_AREA_YR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SML_BUS_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SML_BUS_YR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WOMN_OWND_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WOMN_OWND_YR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MINY_OWND_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MINY_OWND_YR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DAVD_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DBAR_CNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DBAR_DT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DAVD_YR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BKCY_OPEN_CNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BKCY_CLSD_CNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BKCY_OPEN_MSG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BKCY_CLSD_MSG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SLON_CMNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FAIL_SCR_ENTR" type="{http://www.dnb.com/DNB_WebServices/Providers/OrderAndInvestigations/GDP_V3/wsp_GDP_V3}FAIL_SCR_ENTR" minOccurs="0"/>
 *         &lt;element name="DELQ_SCR_ENTR" type="{http://www.dnb.com/DNB_WebServices/Providers/OrderAndInvestigations/GDP_V3/wsp_GDP_V3}DELQ_SCR_ENTR" minOccurs="0"/>
 *         &lt;element name="EMMA_SCR_ENTR" type="{http://www.dnb.com/DNB_WebServices/Providers/OrderAndInvestigations/GDP_V3/wsp_GDP_V3}EMMA_SCR_ENTR" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DATARS", propOrder = {
    "srvrtid",
    "gdaid",
    "cacheind",
    "section",
    "dunsnbr",
    "primnme",
    "adrline",
    "posttown",
    "primgeoarea",
    "postcode",
    "ctrycd",
    "brind",
    "actpay",
    "actrec",
    "activitytext",
    "addlsic",
    "addlsictypecd",
    "adrtenrtypecd",
    "annsaleconsind",
    "annsalecrcycd",
    "annsaleestdind",
    "annsalevol",
    "annsalevoltext",
    "aset",
    "audtind",
    "audtqlfnind",
    "avghighcr",
    "busregnnbr",
    "busregnnbrtypecd",
    "busstru",
    "caplamt",
    "caplcrcycd",
    "capltypecd",
    "cashliqaset",
    "ceonme",
    "ceotitl",
    "clmind",
    "consind",
    "costofsls",
    "crind",
    "crscr",
    "crscrcmty",
    "crcycd",
    "crimind",
    "currcntlyr",
    "currrato",
    "dbtrinposnind",
    "divd",
    "crscrclas",
    "dnbratg",
    "domultctrycd",
    "domultpntduns",
    "domultpntnme",
    "dsr",
    "dstrind",
    "emplatprimadr",
    "emplatprimadrestdind",
    "emplatprimadrminind",
    "emplatprimadrtext",
    "estdind",
    "exptind",
    "failscr",
    "failscr12MOAGO",
    "failscr6MOAGO",
    "failscrcmty",
    "failscrindpctg",
    "failscrnatlpctg",
    "failscrovrdcd",
    "faxnbr",
    "fcstind",
    "finlembtind",
    "finllglevntind",
    "fiscind",
    "fixdaset",
    "flngcrcycd",
    "flngnbr",
    "flngtype",
    "flngval",
    "fnalind",
    "gblultctrycd",
    "gblultpntduns",
    "gblultpntnme",
    "serrat",
    "grosincm",
    "highcr",
    "histind",
    "hqctrycd",
    "hqduns",
    "hqnme",
    "imptind",
    "incmstmtdt",
    "incnyr",
    "intldlngcd",
    "itngaset",
    "lclactvcd",
    "lclactvcdtype",
    "lglform",
    "locnstat",
    "ltdbt",
    "maxcr",
    "maxcrcrcycd",
    "nbrnegpmt",
    "netincm",
    "netwrth",
    "nonposttown",
    "openind",
    "oprgspecevntind",
    "othrspecevntind",
    "outbusind",
    "payd3MOAGO",
    "paydnorm",
    "paydscr",
    "pntctrycd",
    "pntduns",
    "pntnme",
    "prevnetwrth",
    "prevsls",
    "prevstmtdt",
    "prevwrkgcapl",
    "primsic",
    "primsictypecd",
    "profrmaind",
    "qkrato",
    "regntype",
    "restind",
    "retnerng",
    "scdygeoarea",
    "scrdflngind",
    "sdind",
    "sgndind",
    "sls",
    "stat",
    "stk",
    "stmtcrcycd",
    "stmtdt",
    "stmtfromdt",
    "stmttodt",
    "strtyr",
    "suitjdgtind",
    "tangnetwrth",
    "tangnetwrthcrcycd",
    "tangnetwrthestdind",
    "tangnetwrthtext",
    "tlcmnbr",
    "totaset",
    "totcurraset",
    "totcurrliab",
    "totempl",
    "totemplestdind",
    "totemplind",
    "totemplminind",
    "totempltext",
    "totliab",
    "totliabandeqy",
    "totltliab",
    "totpmt",
    "trdgstyl",
    "trlbalind",
    "ubalind",
    "addlsicdesc",
    "enqduns",
    "prinnme",
    "printtl",
    "pftbeftax",
    "cngldistcd",
    "lbrsplsareaind",
    "lbrsplsareayr",
    "smlbusind",
    "smlbusyr",
    "womnowndind",
    "womnowndyr",
    "minyowndind",
    "minyowndyr",
    "davdind",
    "dbarcnt",
    "dbardt",
    "davdyr",
    "bkcyopencnt",
    "bkcyclsdcnt",
    "bkcyopenmsg",
    "bkcyclsdmsg",
    "sloncmnt",
    "failscrentr",
    "delqscrentr",
    "emmascrentr"
})
public class DATARS {

    @XmlElementRef(name = "SRVRTID", type = JAXBElement.class)
    protected JAXBElement<String> srvrtid;
    @XmlElementRef(name = "GDA_ID", type = JAXBElement.class)
    protected JAXBElement<String> gdaid;
    @XmlElementRef(name = "CACHE_IND", type = JAXBElement.class)
    protected JAXBElement<String> cacheind;
    @XmlElementRef(name = "SECTION", type = JAXBElement.class)
    protected JAXBElement<SECTION2> section;
    @XmlElementRef(name = "DUNS_NBR", type = JAXBElement.class)
    protected JAXBElement<String> dunsnbr;
    @XmlElementRef(name = "PRIM_NME", type = JAXBElement.class)
    protected JAXBElement<String> primnme;
    @XmlElementRef(name = "ADR_LINE", type = JAXBElement.class)
    protected JAXBElement<ArrayOfstring> adrline;
    @XmlElementRef(name = "POST_TOWN", type = JAXBElement.class)
    protected JAXBElement<String> posttown;
    @XmlElementRef(name = "PRIM_GEO_AREA", type = JAXBElement.class)
    protected JAXBElement<String> primgeoarea;
    @XmlElementRef(name = "POST_CODE", type = JAXBElement.class)
    protected JAXBElement<String> postcode;
    @XmlElementRef(name = "CTRY_CD", type = JAXBElement.class)
    protected JAXBElement<String> ctrycd;
    @XmlElementRef(name = "BR_IND", type = JAXBElement.class)
    protected JAXBElement<String> brind;
    @XmlElementRef(name = "ACT_PAY", type = JAXBElement.class)
    protected JAXBElement<String> actpay;
    @XmlElementRef(name = "ACT_REC", type = JAXBElement.class)
    protected JAXBElement<String> actrec;
    @XmlElementRef(name = "ACTIVITY_TEXT", type = JAXBElement.class)
    protected JAXBElement<String> activitytext;
    @XmlElementRef(name = "ADDL_SIC", type = JAXBElement.class)
    protected JAXBElement<String> addlsic;
    @XmlElementRef(name = "ADDL_SIC_TYPE_CD", type = JAXBElement.class)
    protected JAXBElement<String> addlsictypecd;
    @XmlElementRef(name = "ADR_TENR_TYPE_CD", type = JAXBElement.class)
    protected JAXBElement<String> adrtenrtypecd;
    @XmlElementRef(name = "ANN_SALE_CONS_IND", type = JAXBElement.class)
    protected JAXBElement<String> annsaleconsind;
    @XmlElementRef(name = "ANN_SALE_CRCY_CD", type = JAXBElement.class)
    protected JAXBElement<String> annsalecrcycd;
    @XmlElementRef(name = "ANN_SALE_ESTD_IND", type = JAXBElement.class)
    protected JAXBElement<String> annsaleestdind;
    @XmlElementRef(name = "ANN_SALE_VOL", type = JAXBElement.class)
    protected JAXBElement<String> annsalevol;
    @XmlElementRef(name = "ANN_SALE_VOL_TEXT", type = JAXBElement.class)
    protected JAXBElement<String> annsalevoltext;
    @XmlElementRef(name = "ASET", type = JAXBElement.class)
    protected JAXBElement<String> aset;
    @XmlElementRef(name = "AUDT_IND", type = JAXBElement.class)
    protected JAXBElement<String> audtind;
    @XmlElementRef(name = "AUDT_QLFN_IND", type = JAXBElement.class)
    protected JAXBElement<String> audtqlfnind;
    @XmlElementRef(name = "AVG_HIGH_CR", type = JAXBElement.class)
    protected JAXBElement<String> avghighcr;
    @XmlElementRef(name = "BUS_REGN_NBR", type = JAXBElement.class)
    protected JAXBElement<String> busregnnbr;
    @XmlElementRef(name = "BUS_REGN_NBR_TYPE_CD", type = JAXBElement.class)
    protected JAXBElement<String> busregnnbrtypecd;
    @XmlElementRef(name = "BUS_STRU", type = JAXBElement.class)
    protected JAXBElement<String> busstru;
    @XmlElementRef(name = "CAPL_AMT", type = JAXBElement.class)
    protected JAXBElement<String> caplamt;
    @XmlElementRef(name = "CAPL_CRCY_CD", type = JAXBElement.class)
    protected JAXBElement<String> caplcrcycd;
    @XmlElementRef(name = "CAPL_TYPE_CD", type = JAXBElement.class)
    protected JAXBElement<String> capltypecd;
    @XmlElementRef(name = "CASH_LIQ_ASET", type = JAXBElement.class)
    protected JAXBElement<String> cashliqaset;
    @XmlElementRef(name = "CEO_NME", type = JAXBElement.class)
    protected JAXBElement<String> ceonme;
    @XmlElementRef(name = "CEO_TITL", type = JAXBElement.class)
    protected JAXBElement<String> ceotitl;
    @XmlElementRef(name = "CLM_IND", type = JAXBElement.class)
    protected JAXBElement<String> clmind;
    @XmlElementRef(name = "CONS_IND", type = JAXBElement.class)
    protected JAXBElement<String> consind;
    @XmlElementRef(name = "COST_OF_SLS", type = JAXBElement.class)
    protected JAXBElement<String> costofsls;
    @XmlElementRef(name = "CR_IND", type = JAXBElement.class)
    protected JAXBElement<String> crind;
    @XmlElementRef(name = "CR_SCR", type = JAXBElement.class)
    protected JAXBElement<String> crscr;
    @XmlElementRef(name = "CR_SCR_CMTY", type = JAXBElement.class)
    protected JAXBElement<ArrayOfstring> crscrcmty;
    @XmlElementRef(name = "CRCY_CD", type = JAXBElement.class)
    protected JAXBElement<String> crcycd;
    @XmlElementRef(name = "CRIM_IND", type = JAXBElement.class)
    protected JAXBElement<String> crimind;
    @XmlElementRef(name = "CURR_CNTL_YR", type = JAXBElement.class)
    protected JAXBElement<String> currcntlyr;
    @XmlElementRef(name = "CURR_RATO", type = JAXBElement.class)
    protected JAXBElement<String> currrato;
    @XmlElementRef(name = "DBTR_IN_POSN_IND", type = JAXBElement.class)
    protected JAXBElement<String> dbtrinposnind;
    @XmlElementRef(name = "DIVD", type = JAXBElement.class)
    protected JAXBElement<String> divd;
    @XmlElementRef(name = "CR_SCR_CLAS", type = JAXBElement.class)
    protected JAXBElement<String> crscrclas;
    @XmlElementRef(name = "DNB_RATG", type = JAXBElement.class)
    protected JAXBElement<String> dnbratg;
    @XmlElementRef(name = "DOM_ULT_CTRY_CD", type = JAXBElement.class)
    protected JAXBElement<String> domultctrycd;
    @XmlElementRef(name = "DOM_ULT_PNT_DUNS", type = JAXBElement.class)
    protected JAXBElement<String> domultpntduns;
    @XmlElementRef(name = "DOM_ULT_PNT_NME", type = JAXBElement.class)
    protected JAXBElement<String> domultpntnme;
    @XmlElementRef(name = "DSR", type = JAXBElement.class)
    protected JAXBElement<String> dsr;
    @XmlElementRef(name = "DSTR_IND", type = JAXBElement.class)
    protected JAXBElement<String> dstrind;
    @XmlElementRef(name = "EMPL_AT_PRIM_ADR", type = JAXBElement.class)
    protected JAXBElement<String> emplatprimadr;
    @XmlElementRef(name = "EMPL_AT_PRIM_ADR_ESTD_IND", type = JAXBElement.class)
    protected JAXBElement<String> emplatprimadrestdind;
    @XmlElementRef(name = "EMPL_AT_PRIM_ADR_MIN_IND", type = JAXBElement.class)
    protected JAXBElement<String> emplatprimadrminind;
    @XmlElementRef(name = "EMPL_AT_PRIM_ADR_TEXT", type = JAXBElement.class)
    protected JAXBElement<String> emplatprimadrtext;
    @XmlElementRef(name = "ESTD_IND", type = JAXBElement.class)
    protected JAXBElement<String> estdind;
    @XmlElementRef(name = "EXPT_IND", type = JAXBElement.class)
    protected JAXBElement<String> exptind;
    @XmlElementRef(name = "FAIL_SCR", type = JAXBElement.class)
    protected JAXBElement<String> failscr;
    @XmlElementRef(name = "FAIL_SCR_12_MO_AGO", type = JAXBElement.class)
    protected JAXBElement<String> failscr12MOAGO;
    @XmlElementRef(name = "FAIL_SCR_6_MO_AGO", type = JAXBElement.class)
    protected JAXBElement<String> failscr6MOAGO;
    @XmlElementRef(name = "FAIL_SCR_CMTY", type = JAXBElement.class)
    protected JAXBElement<ArrayOfstring> failscrcmty;
    @XmlElementRef(name = "FAIL_SCR_IND_PCTG", type = JAXBElement.class)
    protected JAXBElement<String> failscrindpctg;
    @XmlElementRef(name = "FAIL_SCR_NATL_PCTG", type = JAXBElement.class)
    protected JAXBElement<String> failscrnatlpctg;
    @XmlElementRef(name = "FAIL_SCR_OVRD_CD", type = JAXBElement.class)
    protected JAXBElement<String> failscrovrdcd;
    @XmlElementRef(name = "FAX_NBR", type = JAXBElement.class)
    protected JAXBElement<String> faxnbr;
    @XmlElementRef(name = "FCST_IND", type = JAXBElement.class)
    protected JAXBElement<String> fcstind;
    @XmlElementRef(name = "FINL_EMBT_IND", type = JAXBElement.class)
    protected JAXBElement<String> finlembtind;
    @XmlElementRef(name = "FINL_LGL_EVNT_IND", type = JAXBElement.class)
    protected JAXBElement<String> finllglevntind;
    @XmlElementRef(name = "FISC_IND", type = JAXBElement.class)
    protected JAXBElement<String> fiscind;
    @XmlElementRef(name = "FIXD_ASET", type = JAXBElement.class)
    protected JAXBElement<String> fixdaset;
    @XmlElementRef(name = "FLNG_CRCY_CD", type = JAXBElement.class)
    protected JAXBElement<String> flngcrcycd;
    @XmlElementRef(name = "FLNG_NBR", type = JAXBElement.class)
    protected JAXBElement<String> flngnbr;
    @XmlElementRef(name = "FLNG_TYPE", type = JAXBElement.class)
    protected JAXBElement<String> flngtype;
    @XmlElementRef(name = "FLNG_VAL", type = JAXBElement.class)
    protected JAXBElement<String> flngval;
    @XmlElementRef(name = "FNAL_IND", type = JAXBElement.class)
    protected JAXBElement<String> fnalind;
    @XmlElementRef(name = "GBL_ULT_CTRY_CD", type = JAXBElement.class)
    protected JAXBElement<String> gblultctrycd;
    @XmlElementRef(name = "GBL_ULT_PNT_DUNS", type = JAXBElement.class)
    protected JAXBElement<String> gblultpntduns;
    @XmlElementRef(name = "GBL_ULT_PNT_NME", type = JAXBElement.class)
    protected JAXBElement<String> gblultpntnme;
    @XmlElementRef(name = "SER_RAT", type = JAXBElement.class)
    protected JAXBElement<String> serrat;
    @XmlElementRef(name = "GROS_INCM", type = JAXBElement.class)
    protected JAXBElement<String> grosincm;
    @XmlElementRef(name = "HIGH_CR", type = JAXBElement.class)
    protected JAXBElement<String> highcr;
    @XmlElementRef(name = "HIST_IND", type = JAXBElement.class)
    protected JAXBElement<String> histind;
    @XmlElementRef(name = "HQ_CTRY_CD", type = JAXBElement.class)
    protected JAXBElement<String> hqctrycd;
    @XmlElementRef(name = "HQ_DUNS", type = JAXBElement.class)
    protected JAXBElement<String> hqduns;
    @XmlElementRef(name = "HQ_NME", type = JAXBElement.class)
    protected JAXBElement<String> hqnme;
    @XmlElementRef(name = "IMPT_IND", type = JAXBElement.class)
    protected JAXBElement<String> imptind;
    @XmlElementRef(name = "INCM_STMT_DT", type = JAXBElement.class)
    protected JAXBElement<String> incmstmtdt;
    @XmlElementRef(name = "INCN_YR", type = JAXBElement.class)
    protected JAXBElement<String> incnyr;
    @XmlElementRef(name = "INTL_DLNG_CD", type = JAXBElement.class)
    protected JAXBElement<String> intldlngcd;
    @XmlElementRef(name = "ITNG_ASET", type = JAXBElement.class)
    protected JAXBElement<String> itngaset;
    @XmlElementRef(name = "LCL_ACTV_CD", type = JAXBElement.class)
    protected JAXBElement<String> lclactvcd;
    @XmlElementRef(name = "LCL_ACTV_CD_TYPE", type = JAXBElement.class)
    protected JAXBElement<String> lclactvcdtype;
    @XmlElementRef(name = "LGL_FORM", type = JAXBElement.class)
    protected JAXBElement<String> lglform;
    @XmlElementRef(name = "LOCN_STAT", type = JAXBElement.class)
    protected JAXBElement<String> locnstat;
    @XmlElementRef(name = "LT_DBT", type = JAXBElement.class)
    protected JAXBElement<String> ltdbt;
    @XmlElementRef(name = "MAX_CR", type = JAXBElement.class)
    protected JAXBElement<String> maxcr;
    @XmlElementRef(name = "MAX_CR_CRCY_CD", type = JAXBElement.class)
    protected JAXBElement<String> maxcrcrcycd;
    @XmlElementRef(name = "NBR_NEG_PMT", type = JAXBElement.class)
    protected JAXBElement<String> nbrnegpmt;
    @XmlElementRef(name = "NET_INCM", type = JAXBElement.class)
    protected JAXBElement<String> netincm;
    @XmlElementRef(name = "NET_WRTH", type = JAXBElement.class)
    protected JAXBElement<String> netwrth;
    @XmlElementRef(name = "NON_POST_TOWN", type = JAXBElement.class)
    protected JAXBElement<String> nonposttown;
    @XmlElementRef(name = "OPEN_IND", type = JAXBElement.class)
    protected JAXBElement<String> openind;
    @XmlElementRef(name = "OPRG_SPEC_EVNT_IND", type = JAXBElement.class)
    protected JAXBElement<String> oprgspecevntind;
    @XmlElementRef(name = "OTHR_SPEC_EVNT_IND", type = JAXBElement.class)
    protected JAXBElement<String> othrspecevntind;
    @XmlElementRef(name = "OUT_BUS_IND", type = JAXBElement.class)
    protected JAXBElement<String> outbusind;
    @XmlElementRef(name = "PAYD_3_MO_AGO", type = JAXBElement.class)
    protected JAXBElement<String> payd3MOAGO;
    @XmlElementRef(name = "PAYD_NORM", type = JAXBElement.class)
    protected JAXBElement<String> paydnorm;
    @XmlElementRef(name = "PAYD_SCR", type = JAXBElement.class)
    protected JAXBElement<String> paydscr;
    @XmlElementRef(name = "PNT_CTRY_CD", type = JAXBElement.class)
    protected JAXBElement<String> pntctrycd;
    @XmlElementRef(name = "PNT_DUNS", type = JAXBElement.class)
    protected JAXBElement<String> pntduns;
    @XmlElementRef(name = "PNT_NME", type = JAXBElement.class)
    protected JAXBElement<String> pntnme;
    @XmlElementRef(name = "PREV_NET_WRTH", type = JAXBElement.class)
    protected JAXBElement<String> prevnetwrth;
    @XmlElementRef(name = "PREV_SLS", type = JAXBElement.class)
    protected JAXBElement<String> prevsls;
    @XmlElementRef(name = "PREV_STMT_DT", type = JAXBElement.class)
    protected JAXBElement<String> prevstmtdt;
    @XmlElementRef(name = "PREV_WRKG_CAPL", type = JAXBElement.class)
    protected JAXBElement<String> prevwrkgcapl;
    @XmlElementRef(name = "PRIM_SIC", type = JAXBElement.class)
    protected JAXBElement<String> primsic;
    @XmlElementRef(name = "PRIM_SIC_TYPE_CD", type = JAXBElement.class)
    protected JAXBElement<String> primsictypecd;
    @XmlElementRef(name = "PRO_FRMA_IND", type = JAXBElement.class)
    protected JAXBElement<String> profrmaind;
    @XmlElementRef(name = "QK_RATO", type = JAXBElement.class)
    protected JAXBElement<String> qkrato;
    @XmlElementRef(name = "REGN_TYPE", type = JAXBElement.class)
    protected JAXBElement<String> regntype;
    @XmlElementRef(name = "REST_IND", type = JAXBElement.class)
    protected JAXBElement<String> restind;
    @XmlElementRef(name = "RETN_ERNG", type = JAXBElement.class)
    protected JAXBElement<String> retnerng;
    @XmlElementRef(name = "SCDY_GEO_AREA", type = JAXBElement.class)
    protected JAXBElement<String> scdygeoarea;
    @XmlElementRef(name = "SCRD_FLNG_IND", type = JAXBElement.class)
    protected JAXBElement<String> scrdflngind;
    @XmlElementRef(name = "SD_IND", type = JAXBElement.class)
    protected JAXBElement<String> sdind;
    @XmlElementRef(name = "SGND_IND", type = JAXBElement.class)
    protected JAXBElement<String> sgndind;
    @XmlElementRef(name = "SLS", type = JAXBElement.class)
    protected JAXBElement<String> sls;
    @XmlElementRef(name = "STAT", type = JAXBElement.class)
    protected JAXBElement<String> stat;
    @XmlElementRef(name = "STK", type = JAXBElement.class)
    protected JAXBElement<String> stk;
    @XmlElementRef(name = "STMT_CRCY_CD", type = JAXBElement.class)
    protected JAXBElement<String> stmtcrcycd;
    @XmlElementRef(name = "STMT_DT", type = JAXBElement.class)
    protected JAXBElement<String> stmtdt;
    @XmlElementRef(name = "STMT_FROM_DT", type = JAXBElement.class)
    protected JAXBElement<String> stmtfromdt;
    @XmlElementRef(name = "STMT_TO_DT", type = JAXBElement.class)
    protected JAXBElement<String> stmttodt;
    @XmlElementRef(name = "STRT_YR", type = JAXBElement.class)
    protected JAXBElement<String> strtyr;
    @XmlElementRef(name = "SUIT_JDGT_IND", type = JAXBElement.class)
    protected JAXBElement<String> suitjdgtind;
    @XmlElementRef(name = "TANG_NET_WRTH", type = JAXBElement.class)
    protected JAXBElement<String> tangnetwrth;
    @XmlElementRef(name = "TANG_NET_WRTH_CRCY_CD", type = JAXBElement.class)
    protected JAXBElement<String> tangnetwrthcrcycd;
    @XmlElementRef(name = "TANG_NET_WRTH_ESTD_IND", type = JAXBElement.class)
    protected JAXBElement<String> tangnetwrthestdind;
    @XmlElementRef(name = "TANG_NET_WRTH_TEXT", type = JAXBElement.class)
    protected JAXBElement<String> tangnetwrthtext;
    @XmlElementRef(name = "TLCM_NBR", type = JAXBElement.class)
    protected JAXBElement<String> tlcmnbr;
    @XmlElementRef(name = "TOT_ASET", type = JAXBElement.class)
    protected JAXBElement<String> totaset;
    @XmlElementRef(name = "TOT_CURR_ASET", type = JAXBElement.class)
    protected JAXBElement<String> totcurraset;
    @XmlElementRef(name = "TOT_CURR_LIAB", type = JAXBElement.class)
    protected JAXBElement<String> totcurrliab;
    @XmlElementRef(name = "TOT_EMPL", type = JAXBElement.class)
    protected JAXBElement<String> totempl;
    @XmlElementRef(name = "TOT_EMPL_ESTD_IND", type = JAXBElement.class)
    protected JAXBElement<String> totemplestdind;
    @XmlElementRef(name = "TOT_EMPL_IND", type = JAXBElement.class)
    protected JAXBElement<String> totemplind;
    @XmlElementRef(name = "TOT_EMPL_MIN_IND", type = JAXBElement.class)
    protected JAXBElement<String> totemplminind;
    @XmlElementRef(name = "TOT_EMPL_TEXT", type = JAXBElement.class)
    protected JAXBElement<String> totempltext;
    @XmlElementRef(name = "TOT_LIAB", type = JAXBElement.class)
    protected JAXBElement<String> totliab;
    @XmlElementRef(name = "TOT_LIAB_AND_EQY", type = JAXBElement.class)
    protected JAXBElement<String> totliabandeqy;
    @XmlElementRef(name = "TOT_LT_LIAB", type = JAXBElement.class)
    protected JAXBElement<String> totltliab;
    @XmlElementRef(name = "TOT_PMT", type = JAXBElement.class)
    protected JAXBElement<String> totpmt;
    @XmlElementRef(name = "TRDG_STYL", type = JAXBElement.class)
    protected JAXBElement<ArrayOfstring> trdgstyl;
    @XmlElementRef(name = "TRL_BAL_IND", type = JAXBElement.class)
    protected JAXBElement<String> trlbalind;
    @XmlElementRef(name = "UBAL_IND", type = JAXBElement.class)
    protected JAXBElement<String> ubalind;
    @XmlElementRef(name = "ADDL_SIC_DESC", type = JAXBElement.class)
    protected JAXBElement<String> addlsicdesc;
    @XmlElementRef(name = "ENQ_DUNS", type = JAXBElement.class)
    protected JAXBElement<String> enqduns;
    @XmlElementRef(name = "PRIN_NME", type = JAXBElement.class)
    protected JAXBElement<String> prinnme;
    @XmlElementRef(name = "PRIN_TTL", type = JAXBElement.class)
    protected JAXBElement<String> printtl;
    @XmlElementRef(name = "PFT_BEF_TAX", type = JAXBElement.class)
    protected JAXBElement<String> pftbeftax;
    @XmlElementRef(name = "CNGL_DIST_CD", type = JAXBElement.class)
    protected JAXBElement<String> cngldistcd;
    @XmlElementRef(name = "LBR_SPLS_AREA_IND", type = JAXBElement.class)
    protected JAXBElement<String> lbrsplsareaind;
    @XmlElementRef(name = "LBR_SPLS_AREA_YR", type = JAXBElement.class)
    protected JAXBElement<String> lbrsplsareayr;
    @XmlElementRef(name = "SML_BUS_IND", type = JAXBElement.class)
    protected JAXBElement<String> smlbusind;
    @XmlElementRef(name = "SML_BUS_YR", type = JAXBElement.class)
    protected JAXBElement<String> smlbusyr;
    @XmlElementRef(name = "WOMN_OWND_IND", type = JAXBElement.class)
    protected JAXBElement<String> womnowndind;
    @XmlElementRef(name = "WOMN_OWND_YR", type = JAXBElement.class)
    protected JAXBElement<String> womnowndyr;
    @XmlElementRef(name = "MINY_OWND_IND", type = JAXBElement.class)
    protected JAXBElement<String> minyowndind;
    @XmlElementRef(name = "MINY_OWND_YR", type = JAXBElement.class)
    protected JAXBElement<String> minyowndyr;
    @XmlElementRef(name = "DAVD_IND", type = JAXBElement.class)
    protected JAXBElement<String> davdind;
    @XmlElementRef(name = "DBAR_CNT", type = JAXBElement.class)
    protected JAXBElement<String> dbarcnt;
    @XmlElementRef(name = "DBAR_DT", type = JAXBElement.class)
    protected JAXBElement<String> dbardt;
    @XmlElementRef(name = "DAVD_YR", type = JAXBElement.class)
    protected JAXBElement<String> davdyr;
    @XmlElementRef(name = "BKCY_OPEN_CNT", type = JAXBElement.class)
    protected JAXBElement<String> bkcyopencnt;
    @XmlElementRef(name = "BKCY_CLSD_CNT", type = JAXBElement.class)
    protected JAXBElement<String> bkcyclsdcnt;
    @XmlElementRef(name = "BKCY_OPEN_MSG", type = JAXBElement.class)
    protected JAXBElement<String> bkcyopenmsg;
    @XmlElementRef(name = "BKCY_CLSD_MSG", type = JAXBElement.class)
    protected JAXBElement<String> bkcyclsdmsg;
    @XmlElementRef(name = "SLON_CMNT", type = JAXBElement.class)
    protected JAXBElement<String> sloncmnt;
    @XmlElementRef(name = "FAIL_SCR_ENTR", type = JAXBElement.class)
    protected JAXBElement<FAILSCRENTR> failscrentr;
    @XmlElementRef(name = "DELQ_SCR_ENTR", type = JAXBElement.class)
    protected JAXBElement<DELQSCRENTR> delqscrentr;
    @XmlElementRef(name = "EMMA_SCR_ENTR", type = JAXBElement.class)
    protected JAXBElement<EMMASCRENTR> emmascrentr;

    /**
     * Gets the value of the srvrtid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSRVRTID() {
        return srvrtid;
    }

    /**
     * Sets the value of the srvrtid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSRVRTID(JAXBElement<String> value) {
        this.srvrtid = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the gdaid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getGDAID() {
        return gdaid;
    }

    /**
     * Sets the value of the gdaid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setGDAID(JAXBElement<String> value) {
        this.gdaid = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the cacheind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCACHEIND() {
        return cacheind;
    }

    /**
     * Sets the value of the cacheind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCACHEIND(JAXBElement<String> value) {
        this.cacheind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the section property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link SECTION2 }{@code >}
     *     
     */
    public JAXBElement<SECTION2> getSECTION() {
        return section;
    }

    /**
     * Sets the value of the section property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link SECTION2 }{@code >}
     *     
     */
    public void setSECTION(JAXBElement<SECTION2> value) {
        this.section = ((JAXBElement<SECTION2> ) value);
    }

    /**
     * Gets the value of the dunsnbr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDUNSNBR() {
        return dunsnbr;
    }

    /**
     * Sets the value of the dunsnbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDUNSNBR(JAXBElement<String> value) {
        this.dunsnbr = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the primnme property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPRIMNME() {
        return primnme;
    }

    /**
     * Sets the value of the primnme property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPRIMNME(JAXBElement<String> value) {
        this.primnme = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the adrline property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     *     
     */
    public JAXBElement<ArrayOfstring> getADRLINE() {
        return adrline;
    }

    /**
     * Sets the value of the adrline property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     *     
     */
    public void setADRLINE(JAXBElement<ArrayOfstring> value) {
        this.adrline = ((JAXBElement<ArrayOfstring> ) value);
    }

    /**
     * Gets the value of the posttown property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPOSTTOWN() {
        return posttown;
    }

    /**
     * Sets the value of the posttown property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPOSTTOWN(JAXBElement<String> value) {
        this.posttown = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the primgeoarea property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPRIMGEOAREA() {
        return primgeoarea;
    }

    /**
     * Sets the value of the primgeoarea property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPRIMGEOAREA(JAXBElement<String> value) {
        this.primgeoarea = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the postcode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPOSTCODE() {
        return postcode;
    }

    /**
     * Sets the value of the postcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPOSTCODE(JAXBElement<String> value) {
        this.postcode = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the ctrycd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCTRYCD() {
        return ctrycd;
    }

    /**
     * Sets the value of the ctrycd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCTRYCD(JAXBElement<String> value) {
        this.ctrycd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the brind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getBRIND() {
        return brind;
    }

    /**
     * Sets the value of the brind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setBRIND(JAXBElement<String> value) {
        this.brind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the actpay property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getACTPAY() {
        return actpay;
    }

    /**
     * Sets the value of the actpay property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setACTPAY(JAXBElement<String> value) {
        this.actpay = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the actrec property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getACTREC() {
        return actrec;
    }

    /**
     * Sets the value of the actrec property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setACTREC(JAXBElement<String> value) {
        this.actrec = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the activitytext property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getACTIVITYTEXT() {
        return activitytext;
    }

    /**
     * Sets the value of the activitytext property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setACTIVITYTEXT(JAXBElement<String> value) {
        this.activitytext = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the addlsic property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getADDLSIC() {
        return addlsic;
    }

    /**
     * Sets the value of the addlsic property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setADDLSIC(JAXBElement<String> value) {
        this.addlsic = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the addlsictypecd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getADDLSICTYPECD() {
        return addlsictypecd;
    }

    /**
     * Sets the value of the addlsictypecd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setADDLSICTYPECD(JAXBElement<String> value) {
        this.addlsictypecd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the adrtenrtypecd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getADRTENRTYPECD() {
        return adrtenrtypecd;
    }

    /**
     * Sets the value of the adrtenrtypecd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setADRTENRTYPECD(JAXBElement<String> value) {
        this.adrtenrtypecd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the annsaleconsind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getANNSALECONSIND() {
        return annsaleconsind;
    }

    /**
     * Sets the value of the annsaleconsind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setANNSALECONSIND(JAXBElement<String> value) {
        this.annsaleconsind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the annsalecrcycd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getANNSALECRCYCD() {
        return annsalecrcycd;
    }

    /**
     * Sets the value of the annsalecrcycd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setANNSALECRCYCD(JAXBElement<String> value) {
        this.annsalecrcycd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the annsaleestdind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getANNSALEESTDIND() {
        return annsaleestdind;
    }

    /**
     * Sets the value of the annsaleestdind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setANNSALEESTDIND(JAXBElement<String> value) {
        this.annsaleestdind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the annsalevol property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getANNSALEVOL() {
        return annsalevol;
    }

    /**
     * Sets the value of the annsalevol property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setANNSALEVOL(JAXBElement<String> value) {
        this.annsalevol = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the annsalevoltext property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getANNSALEVOLTEXT() {
        return annsalevoltext;
    }

    /**
     * Sets the value of the annsalevoltext property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setANNSALEVOLTEXT(JAXBElement<String> value) {
        this.annsalevoltext = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the aset property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getASET() {
        return aset;
    }

    /**
     * Sets the value of the aset property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setASET(JAXBElement<String> value) {
        this.aset = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the audtind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAUDTIND() {
        return audtind;
    }

    /**
     * Sets the value of the audtind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAUDTIND(JAXBElement<String> value) {
        this.audtind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the audtqlfnind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAUDTQLFNIND() {
        return audtqlfnind;
    }

    /**
     * Sets the value of the audtqlfnind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAUDTQLFNIND(JAXBElement<String> value) {
        this.audtqlfnind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the avghighcr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAVGHIGHCR() {
        return avghighcr;
    }

    /**
     * Sets the value of the avghighcr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAVGHIGHCR(JAXBElement<String> value) {
        this.avghighcr = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the busregnnbr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getBUSREGNNBR() {
        return busregnnbr;
    }

    /**
     * Sets the value of the busregnnbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setBUSREGNNBR(JAXBElement<String> value) {
        this.busregnnbr = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the busregnnbrtypecd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getBUSREGNNBRTYPECD() {
        return busregnnbrtypecd;
    }

    /**
     * Sets the value of the busregnnbrtypecd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setBUSREGNNBRTYPECD(JAXBElement<String> value) {
        this.busregnnbrtypecd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the busstru property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getBUSSTRU() {
        return busstru;
    }

    /**
     * Sets the value of the busstru property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setBUSSTRU(JAXBElement<String> value) {
        this.busstru = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the caplamt property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCAPLAMT() {
        return caplamt;
    }

    /**
     * Sets the value of the caplamt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCAPLAMT(JAXBElement<String> value) {
        this.caplamt = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the caplcrcycd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCAPLCRCYCD() {
        return caplcrcycd;
    }

    /**
     * Sets the value of the caplcrcycd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCAPLCRCYCD(JAXBElement<String> value) {
        this.caplcrcycd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the capltypecd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCAPLTYPECD() {
        return capltypecd;
    }

    /**
     * Sets the value of the capltypecd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCAPLTYPECD(JAXBElement<String> value) {
        this.capltypecd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the cashliqaset property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCASHLIQASET() {
        return cashliqaset;
    }

    /**
     * Sets the value of the cashliqaset property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCASHLIQASET(JAXBElement<String> value) {
        this.cashliqaset = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the ceonme property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCEONME() {
        return ceonme;
    }

    /**
     * Sets the value of the ceonme property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCEONME(JAXBElement<String> value) {
        this.ceonme = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the ceotitl property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCEOTITL() {
        return ceotitl;
    }

    /**
     * Sets the value of the ceotitl property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCEOTITL(JAXBElement<String> value) {
        this.ceotitl = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the clmind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCLMIND() {
        return clmind;
    }

    /**
     * Sets the value of the clmind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCLMIND(JAXBElement<String> value) {
        this.clmind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the consind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCONSIND() {
        return consind;
    }

    /**
     * Sets the value of the consind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCONSIND(JAXBElement<String> value) {
        this.consind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the costofsls property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCOSTOFSLS() {
        return costofsls;
    }

    /**
     * Sets the value of the costofsls property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCOSTOFSLS(JAXBElement<String> value) {
        this.costofsls = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the crind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCRIND() {
        return crind;
    }

    /**
     * Sets the value of the crind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCRIND(JAXBElement<String> value) {
        this.crind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the crscr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCRSCR() {
        return crscr;
    }

    /**
     * Sets the value of the crscr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCRSCR(JAXBElement<String> value) {
        this.crscr = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the crscrcmty property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     *     
     */
    public JAXBElement<ArrayOfstring> getCRSCRCMTY() {
        return crscrcmty;
    }

    /**
     * Sets the value of the crscrcmty property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     *     
     */
    public void setCRSCRCMTY(JAXBElement<ArrayOfstring> value) {
        this.crscrcmty = ((JAXBElement<ArrayOfstring> ) value);
    }

    /**
     * Gets the value of the crcycd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCRCYCD() {
        return crcycd;
    }

    /**
     * Sets the value of the crcycd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCRCYCD(JAXBElement<String> value) {
        this.crcycd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the crimind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCRIMIND() {
        return crimind;
    }

    /**
     * Sets the value of the crimind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCRIMIND(JAXBElement<String> value) {
        this.crimind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the currcntlyr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCURRCNTLYR() {
        return currcntlyr;
    }

    /**
     * Sets the value of the currcntlyr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCURRCNTLYR(JAXBElement<String> value) {
        this.currcntlyr = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the currrato property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCURRRATO() {
        return currrato;
    }

    /**
     * Sets the value of the currrato property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCURRRATO(JAXBElement<String> value) {
        this.currrato = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the dbtrinposnind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDBTRINPOSNIND() {
        return dbtrinposnind;
    }

    /**
     * Sets the value of the dbtrinposnind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDBTRINPOSNIND(JAXBElement<String> value) {
        this.dbtrinposnind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the divd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDIVD() {
        return divd;
    }

    /**
     * Sets the value of the divd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDIVD(JAXBElement<String> value) {
        this.divd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the crscrclas property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCRSCRCLAS() {
        return crscrclas;
    }

    /**
     * Sets the value of the crscrclas property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCRSCRCLAS(JAXBElement<String> value) {
        this.crscrclas = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the dnbratg property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDNBRATG() {
        return dnbratg;
    }

    /**
     * Sets the value of the dnbratg property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDNBRATG(JAXBElement<String> value) {
        this.dnbratg = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the domultctrycd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDOMULTCTRYCD() {
        return domultctrycd;
    }

    /**
     * Sets the value of the domultctrycd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDOMULTCTRYCD(JAXBElement<String> value) {
        this.domultctrycd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the domultpntduns property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDOMULTPNTDUNS() {
        return domultpntduns;
    }

    /**
     * Sets the value of the domultpntduns property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDOMULTPNTDUNS(JAXBElement<String> value) {
        this.domultpntduns = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the domultpntnme property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDOMULTPNTNME() {
        return domultpntnme;
    }

    /**
     * Sets the value of the domultpntnme property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDOMULTPNTNME(JAXBElement<String> value) {
        this.domultpntnme = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the dsr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDSR() {
        return dsr;
    }

    /**
     * Sets the value of the dsr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDSR(JAXBElement<String> value) {
        this.dsr = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the dstrind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDSTRIND() {
        return dstrind;
    }

    /**
     * Sets the value of the dstrind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDSTRIND(JAXBElement<String> value) {
        this.dstrind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the emplatprimadr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getEMPLATPRIMADR() {
        return emplatprimadr;
    }

    /**
     * Sets the value of the emplatprimadr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEMPLATPRIMADR(JAXBElement<String> value) {
        this.emplatprimadr = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the emplatprimadrestdind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getEMPLATPRIMADRESTDIND() {
        return emplatprimadrestdind;
    }

    /**
     * Sets the value of the emplatprimadrestdind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEMPLATPRIMADRESTDIND(JAXBElement<String> value) {
        this.emplatprimadrestdind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the emplatprimadrminind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getEMPLATPRIMADRMININD() {
        return emplatprimadrminind;
    }

    /**
     * Sets the value of the emplatprimadrminind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEMPLATPRIMADRMININD(JAXBElement<String> value) {
        this.emplatprimadrminind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the emplatprimadrtext property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getEMPLATPRIMADRTEXT() {
        return emplatprimadrtext;
    }

    /**
     * Sets the value of the emplatprimadrtext property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEMPLATPRIMADRTEXT(JAXBElement<String> value) {
        this.emplatprimadrtext = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the estdind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getESTDIND() {
        return estdind;
    }

    /**
     * Sets the value of the estdind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setESTDIND(JAXBElement<String> value) {
        this.estdind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the exptind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getEXPTIND() {
        return exptind;
    }

    /**
     * Sets the value of the exptind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEXPTIND(JAXBElement<String> value) {
        this.exptind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the failscr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFAILSCR() {
        return failscr;
    }

    /**
     * Sets the value of the failscr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFAILSCR(JAXBElement<String> value) {
        this.failscr = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the failscr12MOAGO property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFAILSCR12MOAGO() {
        return failscr12MOAGO;
    }

    /**
     * Sets the value of the failscr12MOAGO property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFAILSCR12MOAGO(JAXBElement<String> value) {
        this.failscr12MOAGO = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the failscr6MOAGO property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFAILSCR6MOAGO() {
        return failscr6MOAGO;
    }

    /**
     * Sets the value of the failscr6MOAGO property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFAILSCR6MOAGO(JAXBElement<String> value) {
        this.failscr6MOAGO = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the failscrcmty property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     *     
     */
    public JAXBElement<ArrayOfstring> getFAILSCRCMTY() {
        return failscrcmty;
    }

    /**
     * Sets the value of the failscrcmty property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     *     
     */
    public void setFAILSCRCMTY(JAXBElement<ArrayOfstring> value) {
        this.failscrcmty = ((JAXBElement<ArrayOfstring> ) value);
    }

    /**
     * Gets the value of the failscrindpctg property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFAILSCRINDPCTG() {
        return failscrindpctg;
    }

    /**
     * Sets the value of the failscrindpctg property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFAILSCRINDPCTG(JAXBElement<String> value) {
        this.failscrindpctg = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the failscrnatlpctg property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFAILSCRNATLPCTG() {
        return failscrnatlpctg;
    }

    /**
     * Sets the value of the failscrnatlpctg property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFAILSCRNATLPCTG(JAXBElement<String> value) {
        this.failscrnatlpctg = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the failscrovrdcd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFAILSCROVRDCD() {
        return failscrovrdcd;
    }

    /**
     * Sets the value of the failscrovrdcd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFAILSCROVRDCD(JAXBElement<String> value) {
        this.failscrovrdcd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the faxnbr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFAXNBR() {
        return faxnbr;
    }

    /**
     * Sets the value of the faxnbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFAXNBR(JAXBElement<String> value) {
        this.faxnbr = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the fcstind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFCSTIND() {
        return fcstind;
    }

    /**
     * Sets the value of the fcstind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFCSTIND(JAXBElement<String> value) {
        this.fcstind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the finlembtind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFINLEMBTIND() {
        return finlembtind;
    }

    /**
     * Sets the value of the finlembtind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFINLEMBTIND(JAXBElement<String> value) {
        this.finlembtind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the finllglevntind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFINLLGLEVNTIND() {
        return finllglevntind;
    }

    /**
     * Sets the value of the finllglevntind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFINLLGLEVNTIND(JAXBElement<String> value) {
        this.finllglevntind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the fiscind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFISCIND() {
        return fiscind;
    }

    /**
     * Sets the value of the fiscind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFISCIND(JAXBElement<String> value) {
        this.fiscind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the fixdaset property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFIXDASET() {
        return fixdaset;
    }

    /**
     * Sets the value of the fixdaset property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFIXDASET(JAXBElement<String> value) {
        this.fixdaset = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the flngcrcycd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFLNGCRCYCD() {
        return flngcrcycd;
    }

    /**
     * Sets the value of the flngcrcycd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFLNGCRCYCD(JAXBElement<String> value) {
        this.flngcrcycd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the flngnbr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFLNGNBR() {
        return flngnbr;
    }

    /**
     * Sets the value of the flngnbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFLNGNBR(JAXBElement<String> value) {
        this.flngnbr = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the flngtype property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFLNGTYPE() {
        return flngtype;
    }

    /**
     * Sets the value of the flngtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFLNGTYPE(JAXBElement<String> value) {
        this.flngtype = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the flngval property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFLNGVAL() {
        return flngval;
    }

    /**
     * Sets the value of the flngval property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFLNGVAL(JAXBElement<String> value) {
        this.flngval = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the fnalind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFNALIND() {
        return fnalind;
    }

    /**
     * Sets the value of the fnalind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFNALIND(JAXBElement<String> value) {
        this.fnalind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the gblultctrycd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getGBLULTCTRYCD() {
        return gblultctrycd;
    }

    /**
     * Sets the value of the gblultctrycd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setGBLULTCTRYCD(JAXBElement<String> value) {
        this.gblultctrycd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the gblultpntduns property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getGBLULTPNTDUNS() {
        return gblultpntduns;
    }

    /**
     * Sets the value of the gblultpntduns property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setGBLULTPNTDUNS(JAXBElement<String> value) {
        this.gblultpntduns = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the gblultpntnme property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getGBLULTPNTNME() {
        return gblultpntnme;
    }

    /**
     * Sets the value of the gblultpntnme property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setGBLULTPNTNME(JAXBElement<String> value) {
        this.gblultpntnme = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the serrat property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSERRAT() {
        return serrat;
    }

    /**
     * Sets the value of the serrat property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSERRAT(JAXBElement<String> value) {
        this.serrat = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the grosincm property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getGROSINCM() {
        return grosincm;
    }

    /**
     * Sets the value of the grosincm property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setGROSINCM(JAXBElement<String> value) {
        this.grosincm = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the highcr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getHIGHCR() {
        return highcr;
    }

    /**
     * Sets the value of the highcr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setHIGHCR(JAXBElement<String> value) {
        this.highcr = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the histind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getHISTIND() {
        return histind;
    }

    /**
     * Sets the value of the histind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setHISTIND(JAXBElement<String> value) {
        this.histind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the hqctrycd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getHQCTRYCD() {
        return hqctrycd;
    }

    /**
     * Sets the value of the hqctrycd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setHQCTRYCD(JAXBElement<String> value) {
        this.hqctrycd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the hqduns property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getHQDUNS() {
        return hqduns;
    }

    /**
     * Sets the value of the hqduns property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setHQDUNS(JAXBElement<String> value) {
        this.hqduns = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the hqnme property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getHQNME() {
        return hqnme;
    }

    /**
     * Sets the value of the hqnme property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setHQNME(JAXBElement<String> value) {
        this.hqnme = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the imptind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIMPTIND() {
        return imptind;
    }

    /**
     * Sets the value of the imptind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIMPTIND(JAXBElement<String> value) {
        this.imptind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the incmstmtdt property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINCMSTMTDT() {
        return incmstmtdt;
    }

    /**
     * Sets the value of the incmstmtdt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINCMSTMTDT(JAXBElement<String> value) {
        this.incmstmtdt = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the incnyr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINCNYR() {
        return incnyr;
    }

    /**
     * Sets the value of the incnyr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINCNYR(JAXBElement<String> value) {
        this.incnyr = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the intldlngcd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINTLDLNGCD() {
        return intldlngcd;
    }

    /**
     * Sets the value of the intldlngcd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINTLDLNGCD(JAXBElement<String> value) {
        this.intldlngcd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the itngaset property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getITNGASET() {
        return itngaset;
    }

    /**
     * Sets the value of the itngaset property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setITNGASET(JAXBElement<String> value) {
        this.itngaset = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the lclactvcd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLCLACTVCD() {
        return lclactvcd;
    }

    /**
     * Sets the value of the lclactvcd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLCLACTVCD(JAXBElement<String> value) {
        this.lclactvcd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the lclactvcdtype property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLCLACTVCDTYPE() {
        return lclactvcdtype;
    }

    /**
     * Sets the value of the lclactvcdtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLCLACTVCDTYPE(JAXBElement<String> value) {
        this.lclactvcdtype = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the lglform property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLGLFORM() {
        return lglform;
    }

    /**
     * Sets the value of the lglform property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLGLFORM(JAXBElement<String> value) {
        this.lglform = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the locnstat property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLOCNSTAT() {
        return locnstat;
    }

    /**
     * Sets the value of the locnstat property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLOCNSTAT(JAXBElement<String> value) {
        this.locnstat = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the ltdbt property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLTDBT() {
        return ltdbt;
    }

    /**
     * Sets the value of the ltdbt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLTDBT(JAXBElement<String> value) {
        this.ltdbt = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the maxcr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMAXCR() {
        return maxcr;
    }

    /**
     * Sets the value of the maxcr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMAXCR(JAXBElement<String> value) {
        this.maxcr = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the maxcrcrcycd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMAXCRCRCYCD() {
        return maxcrcrcycd;
    }

    /**
     * Sets the value of the maxcrcrcycd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMAXCRCRCYCD(JAXBElement<String> value) {
        this.maxcrcrcycd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the nbrnegpmt property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNBRNEGPMT() {
        return nbrnegpmt;
    }

    /**
     * Sets the value of the nbrnegpmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNBRNEGPMT(JAXBElement<String> value) {
        this.nbrnegpmt = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the netincm property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNETINCM() {
        return netincm;
    }

    /**
     * Sets the value of the netincm property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNETINCM(JAXBElement<String> value) {
        this.netincm = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the netwrth property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNETWRTH() {
        return netwrth;
    }

    /**
     * Sets the value of the netwrth property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNETWRTH(JAXBElement<String> value) {
        this.netwrth = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the nonposttown property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNONPOSTTOWN() {
        return nonposttown;
    }

    /**
     * Sets the value of the nonposttown property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNONPOSTTOWN(JAXBElement<String> value) {
        this.nonposttown = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the openind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOPENIND() {
        return openind;
    }

    /**
     * Sets the value of the openind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOPENIND(JAXBElement<String> value) {
        this.openind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the oprgspecevntind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOPRGSPECEVNTIND() {
        return oprgspecevntind;
    }

    /**
     * Sets the value of the oprgspecevntind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOPRGSPECEVNTIND(JAXBElement<String> value) {
        this.oprgspecevntind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the othrspecevntind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOTHRSPECEVNTIND() {
        return othrspecevntind;
    }

    /**
     * Sets the value of the othrspecevntind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOTHRSPECEVNTIND(JAXBElement<String> value) {
        this.othrspecevntind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the outbusind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOUTBUSIND() {
        return outbusind;
    }

    /**
     * Sets the value of the outbusind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOUTBUSIND(JAXBElement<String> value) {
        this.outbusind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the payd3MOAGO property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPAYD3MOAGO() {
        return payd3MOAGO;
    }

    /**
     * Sets the value of the payd3MOAGO property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPAYD3MOAGO(JAXBElement<String> value) {
        this.payd3MOAGO = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the paydnorm property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPAYDNORM() {
        return paydnorm;
    }

    /**
     * Sets the value of the paydnorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPAYDNORM(JAXBElement<String> value) {
        this.paydnorm = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the paydscr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPAYDSCR() {
        return paydscr;
    }

    /**
     * Sets the value of the paydscr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPAYDSCR(JAXBElement<String> value) {
        this.paydscr = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the pntctrycd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPNTCTRYCD() {
        return pntctrycd;
    }

    /**
     * Sets the value of the pntctrycd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPNTCTRYCD(JAXBElement<String> value) {
        this.pntctrycd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the pntduns property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPNTDUNS() {
        return pntduns;
    }

    /**
     * Sets the value of the pntduns property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPNTDUNS(JAXBElement<String> value) {
        this.pntduns = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the pntnme property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPNTNME() {
        return pntnme;
    }

    /**
     * Sets the value of the pntnme property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPNTNME(JAXBElement<String> value) {
        this.pntnme = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the prevnetwrth property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPREVNETWRTH() {
        return prevnetwrth;
    }

    /**
     * Sets the value of the prevnetwrth property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPREVNETWRTH(JAXBElement<String> value) {
        this.prevnetwrth = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the prevsls property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPREVSLS() {
        return prevsls;
    }

    /**
     * Sets the value of the prevsls property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPREVSLS(JAXBElement<String> value) {
        this.prevsls = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the prevstmtdt property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPREVSTMTDT() {
        return prevstmtdt;
    }

    /**
     * Sets the value of the prevstmtdt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPREVSTMTDT(JAXBElement<String> value) {
        this.prevstmtdt = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the prevwrkgcapl property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPREVWRKGCAPL() {
        return prevwrkgcapl;
    }

    /**
     * Sets the value of the prevwrkgcapl property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPREVWRKGCAPL(JAXBElement<String> value) {
        this.prevwrkgcapl = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the primsic property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPRIMSIC() {
        return primsic;
    }

    /**
     * Sets the value of the primsic property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPRIMSIC(JAXBElement<String> value) {
        this.primsic = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the primsictypecd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPRIMSICTYPECD() {
        return primsictypecd;
    }

    /**
     * Sets the value of the primsictypecd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPRIMSICTYPECD(JAXBElement<String> value) {
        this.primsictypecd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the profrmaind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPROFRMAIND() {
        return profrmaind;
    }

    /**
     * Sets the value of the profrmaind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPROFRMAIND(JAXBElement<String> value) {
        this.profrmaind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the qkrato property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getQKRATO() {
        return qkrato;
    }

    /**
     * Sets the value of the qkrato property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setQKRATO(JAXBElement<String> value) {
        this.qkrato = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the regntype property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getREGNTYPE() {
        return regntype;
    }

    /**
     * Sets the value of the regntype property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setREGNTYPE(JAXBElement<String> value) {
        this.regntype = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the restind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRESTIND() {
        return restind;
    }

    /**
     * Sets the value of the restind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRESTIND(JAXBElement<String> value) {
        this.restind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the retnerng property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRETNERNG() {
        return retnerng;
    }

    /**
     * Sets the value of the retnerng property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRETNERNG(JAXBElement<String> value) {
        this.retnerng = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the scdygeoarea property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSCDYGEOAREA() {
        return scdygeoarea;
    }

    /**
     * Sets the value of the scdygeoarea property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSCDYGEOAREA(JAXBElement<String> value) {
        this.scdygeoarea = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the scrdflngind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSCRDFLNGIND() {
        return scrdflngind;
    }

    /**
     * Sets the value of the scrdflngind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSCRDFLNGIND(JAXBElement<String> value) {
        this.scrdflngind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the sdind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSDIND() {
        return sdind;
    }

    /**
     * Sets the value of the sdind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSDIND(JAXBElement<String> value) {
        this.sdind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the sgndind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSGNDIND() {
        return sgndind;
    }

    /**
     * Sets the value of the sgndind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSGNDIND(JAXBElement<String> value) {
        this.sgndind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the sls property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSLS() {
        return sls;
    }

    /**
     * Sets the value of the sls property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSLS(JAXBElement<String> value) {
        this.sls = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the stat property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSTAT() {
        return stat;
    }

    /**
     * Sets the value of the stat property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSTAT(JAXBElement<String> value) {
        this.stat = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the stk property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSTK() {
        return stk;
    }

    /**
     * Sets the value of the stk property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSTK(JAXBElement<String> value) {
        this.stk = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the stmtcrcycd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSTMTCRCYCD() {
        return stmtcrcycd;
    }

    /**
     * Sets the value of the stmtcrcycd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSTMTCRCYCD(JAXBElement<String> value) {
        this.stmtcrcycd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the stmtdt property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSTMTDT() {
        return stmtdt;
    }

    /**
     * Sets the value of the stmtdt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSTMTDT(JAXBElement<String> value) {
        this.stmtdt = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the stmtfromdt property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSTMTFROMDT() {
        return stmtfromdt;
    }

    /**
     * Sets the value of the stmtfromdt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSTMTFROMDT(JAXBElement<String> value) {
        this.stmtfromdt = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the stmttodt property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSTMTTODT() {
        return stmttodt;
    }

    /**
     * Sets the value of the stmttodt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSTMTTODT(JAXBElement<String> value) {
        this.stmttodt = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the strtyr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSTRTYR() {
        return strtyr;
    }

    /**
     * Sets the value of the strtyr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSTRTYR(JAXBElement<String> value) {
        this.strtyr = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the suitjdgtind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSUITJDGTIND() {
        return suitjdgtind;
    }

    /**
     * Sets the value of the suitjdgtind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSUITJDGTIND(JAXBElement<String> value) {
        this.suitjdgtind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the tangnetwrth property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTANGNETWRTH() {
        return tangnetwrth;
    }

    /**
     * Sets the value of the tangnetwrth property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTANGNETWRTH(JAXBElement<String> value) {
        this.tangnetwrth = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the tangnetwrthcrcycd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTANGNETWRTHCRCYCD() {
        return tangnetwrthcrcycd;
    }

    /**
     * Sets the value of the tangnetwrthcrcycd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTANGNETWRTHCRCYCD(JAXBElement<String> value) {
        this.tangnetwrthcrcycd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the tangnetwrthestdind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTANGNETWRTHESTDIND() {
        return tangnetwrthestdind;
    }

    /**
     * Sets the value of the tangnetwrthestdind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTANGNETWRTHESTDIND(JAXBElement<String> value) {
        this.tangnetwrthestdind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the tangnetwrthtext property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTANGNETWRTHTEXT() {
        return tangnetwrthtext;
    }

    /**
     * Sets the value of the tangnetwrthtext property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTANGNETWRTHTEXT(JAXBElement<String> value) {
        this.tangnetwrthtext = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the tlcmnbr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTLCMNBR() {
        return tlcmnbr;
    }

    /**
     * Sets the value of the tlcmnbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTLCMNBR(JAXBElement<String> value) {
        this.tlcmnbr = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the totaset property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTOTASET() {
        return totaset;
    }

    /**
     * Sets the value of the totaset property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTOTASET(JAXBElement<String> value) {
        this.totaset = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the totcurraset property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTOTCURRASET() {
        return totcurraset;
    }

    /**
     * Sets the value of the totcurraset property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTOTCURRASET(JAXBElement<String> value) {
        this.totcurraset = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the totcurrliab property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTOTCURRLIAB() {
        return totcurrliab;
    }

    /**
     * Sets the value of the totcurrliab property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTOTCURRLIAB(JAXBElement<String> value) {
        this.totcurrliab = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the totempl property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTOTEMPL() {
        return totempl;
    }

    /**
     * Sets the value of the totempl property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTOTEMPL(JAXBElement<String> value) {
        this.totempl = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the totemplestdind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTOTEMPLESTDIND() {
        return totemplestdind;
    }

    /**
     * Sets the value of the totemplestdind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTOTEMPLESTDIND(JAXBElement<String> value) {
        this.totemplestdind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the totemplind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTOTEMPLIND() {
        return totemplind;
    }

    /**
     * Sets the value of the totemplind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTOTEMPLIND(JAXBElement<String> value) {
        this.totemplind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the totemplminind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTOTEMPLMININD() {
        return totemplminind;
    }

    /**
     * Sets the value of the totemplminind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTOTEMPLMININD(JAXBElement<String> value) {
        this.totemplminind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the totempltext property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTOTEMPLTEXT() {
        return totempltext;
    }

    /**
     * Sets the value of the totempltext property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTOTEMPLTEXT(JAXBElement<String> value) {
        this.totempltext = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the totliab property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTOTLIAB() {
        return totliab;
    }

    /**
     * Sets the value of the totliab property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTOTLIAB(JAXBElement<String> value) {
        this.totliab = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the totliabandeqy property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTOTLIABANDEQY() {
        return totliabandeqy;
    }

    /**
     * Sets the value of the totliabandeqy property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTOTLIABANDEQY(JAXBElement<String> value) {
        this.totliabandeqy = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the totltliab property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTOTLTLIAB() {
        return totltliab;
    }

    /**
     * Sets the value of the totltliab property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTOTLTLIAB(JAXBElement<String> value) {
        this.totltliab = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the totpmt property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTOTPMT() {
        return totpmt;
    }

    /**
     * Sets the value of the totpmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTOTPMT(JAXBElement<String> value) {
        this.totpmt = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the trdgstyl property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     *     
     */
    public JAXBElement<ArrayOfstring> getTRDGSTYL() {
        return trdgstyl;
    }

    /**
     * Sets the value of the trdgstyl property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     *     
     */
    public void setTRDGSTYL(JAXBElement<ArrayOfstring> value) {
        this.trdgstyl = ((JAXBElement<ArrayOfstring> ) value);
    }

    /**
     * Gets the value of the trlbalind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTRLBALIND() {
        return trlbalind;
    }

    /**
     * Sets the value of the trlbalind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTRLBALIND(JAXBElement<String> value) {
        this.trlbalind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the ubalind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUBALIND() {
        return ubalind;
    }

    /**
     * Sets the value of the ubalind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUBALIND(JAXBElement<String> value) {
        this.ubalind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the addlsicdesc property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getADDLSICDESC() {
        return addlsicdesc;
    }

    /**
     * Sets the value of the addlsicdesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setADDLSICDESC(JAXBElement<String> value) {
        this.addlsicdesc = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the enqduns property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getENQDUNS() {
        return enqduns;
    }

    /**
     * Sets the value of the enqduns property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setENQDUNS(JAXBElement<String> value) {
        this.enqduns = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the prinnme property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPRINNME() {
        return prinnme;
    }

    /**
     * Sets the value of the prinnme property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPRINNME(JAXBElement<String> value) {
        this.prinnme = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the printtl property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPRINTTL() {
        return printtl;
    }

    /**
     * Sets the value of the printtl property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPRINTTL(JAXBElement<String> value) {
        this.printtl = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the pftbeftax property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPFTBEFTAX() {
        return pftbeftax;
    }

    /**
     * Sets the value of the pftbeftax property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPFTBEFTAX(JAXBElement<String> value) {
        this.pftbeftax = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the cngldistcd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCNGLDISTCD() {
        return cngldistcd;
    }

    /**
     * Sets the value of the cngldistcd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCNGLDISTCD(JAXBElement<String> value) {
        this.cngldistcd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the lbrsplsareaind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLBRSPLSAREAIND() {
        return lbrsplsareaind;
    }

    /**
     * Sets the value of the lbrsplsareaind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLBRSPLSAREAIND(JAXBElement<String> value) {
        this.lbrsplsareaind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the lbrsplsareayr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLBRSPLSAREAYR() {
        return lbrsplsareayr;
    }

    /**
     * Sets the value of the lbrsplsareayr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLBRSPLSAREAYR(JAXBElement<String> value) {
        this.lbrsplsareayr = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the smlbusind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSMLBUSIND() {
        return smlbusind;
    }

    /**
     * Sets the value of the smlbusind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSMLBUSIND(JAXBElement<String> value) {
        this.smlbusind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the smlbusyr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSMLBUSYR() {
        return smlbusyr;
    }

    /**
     * Sets the value of the smlbusyr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSMLBUSYR(JAXBElement<String> value) {
        this.smlbusyr = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the womnowndind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getWOMNOWNDIND() {
        return womnowndind;
    }

    /**
     * Sets the value of the womnowndind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setWOMNOWNDIND(JAXBElement<String> value) {
        this.womnowndind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the womnowndyr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getWOMNOWNDYR() {
        return womnowndyr;
    }

    /**
     * Sets the value of the womnowndyr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setWOMNOWNDYR(JAXBElement<String> value) {
        this.womnowndyr = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the minyowndind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMINYOWNDIND() {
        return minyowndind;
    }

    /**
     * Sets the value of the minyowndind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMINYOWNDIND(JAXBElement<String> value) {
        this.minyowndind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the minyowndyr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMINYOWNDYR() {
        return minyowndyr;
    }

    /**
     * Sets the value of the minyowndyr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMINYOWNDYR(JAXBElement<String> value) {
        this.minyowndyr = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the davdind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDAVDIND() {
        return davdind;
    }

    /**
     * Sets the value of the davdind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDAVDIND(JAXBElement<String> value) {
        this.davdind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the dbarcnt property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDBARCNT() {
        return dbarcnt;
    }

    /**
     * Sets the value of the dbarcnt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDBARCNT(JAXBElement<String> value) {
        this.dbarcnt = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the dbardt property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDBARDT() {
        return dbardt;
    }

    /**
     * Sets the value of the dbardt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDBARDT(JAXBElement<String> value) {
        this.dbardt = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the davdyr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDAVDYR() {
        return davdyr;
    }

    /**
     * Sets the value of the davdyr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDAVDYR(JAXBElement<String> value) {
        this.davdyr = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the bkcyopencnt property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getBKCYOPENCNT() {
        return bkcyopencnt;
    }

    /**
     * Sets the value of the bkcyopencnt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setBKCYOPENCNT(JAXBElement<String> value) {
        this.bkcyopencnt = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the bkcyclsdcnt property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getBKCYCLSDCNT() {
        return bkcyclsdcnt;
    }

    /**
     * Sets the value of the bkcyclsdcnt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setBKCYCLSDCNT(JAXBElement<String> value) {
        this.bkcyclsdcnt = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the bkcyopenmsg property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getBKCYOPENMSG() {
        return bkcyopenmsg;
    }

    /**
     * Sets the value of the bkcyopenmsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setBKCYOPENMSG(JAXBElement<String> value) {
        this.bkcyopenmsg = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the bkcyclsdmsg property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getBKCYCLSDMSG() {
        return bkcyclsdmsg;
    }

    /**
     * Sets the value of the bkcyclsdmsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setBKCYCLSDMSG(JAXBElement<String> value) {
        this.bkcyclsdmsg = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the sloncmnt property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSLONCMNT() {
        return sloncmnt;
    }

    /**
     * Sets the value of the sloncmnt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSLONCMNT(JAXBElement<String> value) {
        this.sloncmnt = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the failscrentr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link FAILSCRENTR }{@code >}
     *     
     */
    public JAXBElement<FAILSCRENTR> getFAILSCRENTR() {
        return failscrentr;
    }

    /**
     * Sets the value of the failscrentr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link FAILSCRENTR }{@code >}
     *     
     */
    public void setFAILSCRENTR(JAXBElement<FAILSCRENTR> value) {
        this.failscrentr = ((JAXBElement<FAILSCRENTR> ) value);
    }

    /**
     * Gets the value of the delqscrentr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link DELQSCRENTR }{@code >}
     *     
     */
    public JAXBElement<DELQSCRENTR> getDELQSCRENTR() {
        return delqscrentr;
    }

    /**
     * Sets the value of the delqscrentr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link DELQSCRENTR }{@code >}
     *     
     */
    public void setDELQSCRENTR(JAXBElement<DELQSCRENTR> value) {
        this.delqscrentr = ((JAXBElement<DELQSCRENTR> ) value);
    }

    /**
     * Gets the value of the emmascrentr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link EMMASCRENTR }{@code >}
     *     
     */
    public JAXBElement<EMMASCRENTR> getEMMASCRENTR() {
        return emmascrentr;
    }

    /**
     * Sets the value of the emmascrentr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link EMMASCRENTR }{@code >}
     *     
     */
    public void setEMMASCRENTR(JAXBElement<EMMASCRENTR> value) {
        this.emmascrentr = ((JAXBElement<EMMASCRENTR> ) value);
    }

}
