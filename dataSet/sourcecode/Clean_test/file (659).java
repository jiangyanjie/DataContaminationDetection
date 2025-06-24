
package DBFullReportsClient;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CorporateLinkagesOverview" type="{http://gateway.dnb.com/getProduct}LinkageSummaryType" minOccurs="0"/>
 *         &lt;element name="CurrentLinkagesInformation" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ImmediateParentsInformation" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ImmediateParentsInformationEntry" type="{http://gateway.dnb.com/getProduct}ParentInformationEntryType" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="DomesticUltimateParentsInformation" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="DomesticUltimateParentsInformationEntry" type="{http://gateway.dnb.com/getProduct}ParentInformationEntryType" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="GlobalUltimateParentsInformation" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="GlobalUltimateParentsInformationEntry" type="{http://gateway.dnb.com/getProduct}ParentInformationEntryType" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="ShareholdersInformation" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ShareholdersInformationEntry" type="{http://gateway.dnb.com/getProduct}ShareholdersInformationEntryType" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="SubsidiariesInformation" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="SubsidiariesInformationEntry" type="{http://gateway.dnb.com/getProduct}ChildLinkageInformationEntryType" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="MinorityInterestsInformation" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="MinorityInterestsInformationEntry" type="{http://gateway.dnb.com/getProduct}ChildLinkageInformationEntryType" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="BranchesInformation" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="BranchesInformationEntry" type="{http://gateway.dnb.com/getProduct}BranchesInformationEntryType" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="AssociateCorporateDirectorships" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="AssociateCorporateDirectorshipsEntry" type="{http://gateway.dnb.com/getProduct}AssociateCorporateDirectorshipsEntryType" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="LetterofLiabilityInformation" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="LetterofLiabilitySubscriberInformation" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="CurrentLetterofLiabilitySubscriberInformation" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;extension base="{http://gateway.dnb.com/getProduct}LetterofLiabilityRolePlayerInformationEntryType">
 *                                               &lt;sequence minOccurs="0">
 *                                                 &lt;element name="OtherLetterofLiabilitySubscriptionsInformation">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="OtherLetterofLiabilitySubscriptionsInformationEntry" type="{http://gateway.dnb.com/getProduct}LetterofLiabilityRolePlayerInformationEntryType" maxOccurs="unbounded"/>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/extension>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="PreviousLetterofLiabilitySubscriberInformation" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;extension base="{http://gateway.dnb.com/getProduct}LetterofLiabilityRolePlayerInformationEntryType">
 *                                               &lt;sequence minOccurs="0">
 *                                                 &lt;element name="LetterWIthdrawnDate" type="{http://gateway.dnb.com/getProduct}DNBDate"/>
 *                                               &lt;/sequence>
 *                                             &lt;/extension>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="LetterofLiabilityIssuerInformation" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="LetterofLiabilityIssuerInformationEntry" maxOccurs="unbounded">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;extension base="{http://gateway.dnb.com/getProduct}LetterofLiabilityRolePlayerInformationEntryType">
 *                                               &lt;sequence minOccurs="0">
 *                                                 &lt;element name="LastLetterofAgreementDate" type="{http://gateway.dnb.com/getProduct}DNBDate"/>
 *                                               &lt;/sequence>
 *                                             &lt;/extension>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="LetterofAgreementInformation" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="LetterofAgreementInformationEntry" type="{http://gateway.dnb.com/getProduct}LetterofAgreementInformationEntryType" maxOccurs="unbounded"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="PreviousLinkagesInformation" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ImmediateParentsInformation" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ImmediateParentsInformationEntry" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{http://gateway.dnb.com/getProduct}ParentInformationEntryType">
 *                                     &lt;sequence minOccurs="0">
 *                                       &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
 *                                       &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="DomesticUltimateParentsInformation" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="DomesticUltimateParentsInformationEntry" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{http://gateway.dnb.com/getProduct}ParentInformationEntryType">
 *                                     &lt;sequence minOccurs="0">
 *                                       &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
 *                                       &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="GlobalUltimateParentsInformation" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="GlobalUltimateParentsInformationEntry" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{http://gateway.dnb.com/getProduct}ParentInformationEntryType">
 *                                     &lt;sequence minOccurs="0">
 *                                       &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
 *                                       &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="ShareholdersInformation" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ShareholdersInformationEntry" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{http://gateway.dnb.com/getProduct}ShareholdersInformationEntryType">
 *                                     &lt;sequence minOccurs="0">
 *                                       &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
 *                                       &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="SubsidiariesInformation" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="SubsidiariesInformationEntry" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{http://gateway.dnb.com/getProduct}ChildLinkageInformationEntryType">
 *                                     &lt;sequence minOccurs="0">
 *                                       &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
 *                                       &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="MinorityInterestsInformation" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="MinorityInterestsInformationEntry" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{http://gateway.dnb.com/getProduct}ChildLinkageInformationEntryType">
 *                                     &lt;sequence minOccurs="0">
 *                                       &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
 *                                       &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="BranchesInformation" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="BranchesInformationEntry" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{http://gateway.dnb.com/getProduct}BranchesInformationEntryType">
 *                                     &lt;sequence minOccurs="0">
 *                                       &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
 *                                       &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="AssociateCorporateDirectorships" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="AssociateCorporateDirectorshipsEntry" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{http://gateway.dnb.com/getProduct}AssociateCorporateDirectorshipsEntryType">
 *                                     &lt;sequence minOccurs="0">
 *                                       &lt;element name="PrincipalPositionResignedDate" type="{http://gateway.dnb.com/getProduct}DNBDate"/>
 *                                     &lt;/sequence>
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "corporateLinkagesOverview",
    "currentLinkagesInformation",
    "previousLinkagesInformation"
})
@XmlRootElement(name = "CorporateLinkages")
public class CorporateLinkages {

    @XmlElement(name = "CorporateLinkagesOverview")
    protected LinkageSummaryType corporateLinkagesOverview;
    @XmlElement(name = "CurrentLinkagesInformation")
    protected CorporateLinkages.CurrentLinkagesInformation currentLinkagesInformation;
    @XmlElement(name = "PreviousLinkagesInformation")
    protected CorporateLinkages.PreviousLinkagesInformation previousLinkagesInformation;

    /**
     * Gets the value of the corporateLinkagesOverview property.
     * 
     * @return
     *     possible object is
     *     {@link LinkageSummaryType }
     *     
     */
    public LinkageSummaryType getCorporateLinkagesOverview() {
        return corporateLinkagesOverview;
    }

    /**
     * Sets the value of the corporateLinkagesOverview property.
     * 
     * @param value
     *     allowed object is
     *     {@link LinkageSummaryType }
     *     
     */
    public void setCorporateLinkagesOverview(LinkageSummaryType value) {
        this.corporateLinkagesOverview = value;
    }

    /**
     * Gets the value of the currentLinkagesInformation property.
     * 
     * @return
     *     possible object is
     *     {@link CorporateLinkages.CurrentLinkagesInformation }
     *     
     */
    public CorporateLinkages.CurrentLinkagesInformation getCurrentLinkagesInformation() {
        return currentLinkagesInformation;
    }

    /**
     * Sets the value of the currentLinkagesInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link CorporateLinkages.CurrentLinkagesInformation }
     *     
     */
    public void setCurrentLinkagesInformation(CorporateLinkages.CurrentLinkagesInformation value) {
        this.currentLinkagesInformation = value;
    }

    /**
     * Gets the value of the previousLinkagesInformation property.
     * 
     * @return
     *     possible object is
     *     {@link CorporateLinkages.PreviousLinkagesInformation }
     *     
     */
    public CorporateLinkages.PreviousLinkagesInformation getPreviousLinkagesInformation() {
        return previousLinkagesInformation;
    }

    /**
     * Sets the value of the previousLinkagesInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link CorporateLinkages.PreviousLinkagesInformation }
     *     
     */
    public void setPreviousLinkagesInformation(CorporateLinkages.PreviousLinkagesInformation value) {
        this.previousLinkagesInformation = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ImmediateParentsInformation" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ImmediateParentsInformationEntry" type="{http://gateway.dnb.com/getProduct}ParentInformationEntryType" maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="DomesticUltimateParentsInformation" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="DomesticUltimateParentsInformationEntry" type="{http://gateway.dnb.com/getProduct}ParentInformationEntryType" maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="GlobalUltimateParentsInformation" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="GlobalUltimateParentsInformationEntry" type="{http://gateway.dnb.com/getProduct}ParentInformationEntryType" maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="ShareholdersInformation" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ShareholdersInformationEntry" type="{http://gateway.dnb.com/getProduct}ShareholdersInformationEntryType" maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="SubsidiariesInformation" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="SubsidiariesInformationEntry" type="{http://gateway.dnb.com/getProduct}ChildLinkageInformationEntryType" maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="MinorityInterestsInformation" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="MinorityInterestsInformationEntry" type="{http://gateway.dnb.com/getProduct}ChildLinkageInformationEntryType" maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="BranchesInformation" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="BranchesInformationEntry" type="{http://gateway.dnb.com/getProduct}BranchesInformationEntryType" maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="AssociateCorporateDirectorships" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="AssociateCorporateDirectorshipsEntry" type="{http://gateway.dnb.com/getProduct}AssociateCorporateDirectorshipsEntryType" maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="LetterofLiabilityInformation" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="LetterofLiabilitySubscriberInformation" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="CurrentLetterofLiabilitySubscriberInformation" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;extension base="{http://gateway.dnb.com/getProduct}LetterofLiabilityRolePlayerInformationEntryType">
     *                                     &lt;sequence minOccurs="0">
     *                                       &lt;element name="OtherLetterofLiabilitySubscriptionsInformation">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="OtherLetterofLiabilitySubscriptionsInformationEntry" type="{http://gateway.dnb.com/getProduct}LetterofLiabilityRolePlayerInformationEntryType" maxOccurs="unbounded"/>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/extension>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="PreviousLetterofLiabilitySubscriberInformation" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;extension base="{http://gateway.dnb.com/getProduct}LetterofLiabilityRolePlayerInformationEntryType">
     *                                     &lt;sequence minOccurs="0">
     *                                       &lt;element name="LetterWIthdrawnDate" type="{http://gateway.dnb.com/getProduct}DNBDate"/>
     *                                     &lt;/sequence>
     *                                   &lt;/extension>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="LetterofLiabilityIssuerInformation" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="LetterofLiabilityIssuerInformationEntry" maxOccurs="unbounded">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;extension base="{http://gateway.dnb.com/getProduct}LetterofLiabilityRolePlayerInformationEntryType">
     *                                     &lt;sequence minOccurs="0">
     *                                       &lt;element name="LastLetterofAgreementDate" type="{http://gateway.dnb.com/getProduct}DNBDate"/>
     *                                     &lt;/sequence>
     *                                   &lt;/extension>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="LetterofAgreementInformation" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="LetterofAgreementInformationEntry" type="{http://gateway.dnb.com/getProduct}LetterofAgreementInformationEntryType" maxOccurs="unbounded"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "immediateParentsInformation",
        "domesticUltimateParentsInformation",
        "globalUltimateParentsInformation",
        "shareholdersInformation",
        "subsidiariesInformation",
        "minorityInterestsInformation",
        "branchesInformation",
        "associateCorporateDirectorships",
        "letterofLiabilityInformation",
        "letterofAgreementInformation"
    })
    public static class CurrentLinkagesInformation {

        @XmlElement(name = "ImmediateParentsInformation")
        protected CorporateLinkages.CurrentLinkagesInformation.ImmediateParentsInformation immediateParentsInformation;
        @XmlElement(name = "DomesticUltimateParentsInformation")
        protected CorporateLinkages.CurrentLinkagesInformation.DomesticUltimateParentsInformation domesticUltimateParentsInformation;
        @XmlElement(name = "GlobalUltimateParentsInformation")
        protected CorporateLinkages.CurrentLinkagesInformation.GlobalUltimateParentsInformation globalUltimateParentsInformation;
        @XmlElement(name = "ShareholdersInformation")
        protected CorporateLinkages.CurrentLinkagesInformation.ShareholdersInformation shareholdersInformation;
        @XmlElement(name = "SubsidiariesInformation")
        protected CorporateLinkages.CurrentLinkagesInformation.SubsidiariesInformation subsidiariesInformation;
        @XmlElement(name = "MinorityInterestsInformation")
        protected CorporateLinkages.CurrentLinkagesInformation.MinorityInterestsInformation minorityInterestsInformation;
        @XmlElement(name = "BranchesInformation")
        protected CorporateLinkages.CurrentLinkagesInformation.BranchesInformation branchesInformation;
        @XmlElement(name = "AssociateCorporateDirectorships")
        protected CorporateLinkages.CurrentLinkagesInformation.AssociateCorporateDirectorships associateCorporateDirectorships;
        @XmlElement(name = "LetterofLiabilityInformation")
        protected CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation letterofLiabilityInformation;
        @XmlElement(name = "LetterofAgreementInformation")
        protected CorporateLinkages.CurrentLinkagesInformation.LetterofAgreementInformation letterofAgreementInformation;

        /**
         * Gets the value of the immediateParentsInformation property.
         * 
         * @return
         *     possible object is
         *     {@link CorporateLinkages.CurrentLinkagesInformation.ImmediateParentsInformation }
         *     
         */
        public CorporateLinkages.CurrentLinkagesInformation.ImmediateParentsInformation getImmediateParentsInformation() {
            return immediateParentsInformation;
        }

        /**
         * Sets the value of the immediateParentsInformation property.
         * 
         * @param value
         *     allowed object is
         *     {@link CorporateLinkages.CurrentLinkagesInformation.ImmediateParentsInformation }
         *     
         */
        public void setImmediateParentsInformation(CorporateLinkages.CurrentLinkagesInformation.ImmediateParentsInformation value) {
            this.immediateParentsInformation = value;
        }

        /**
         * Gets the value of the domesticUltimateParentsInformation property.
         * 
         * @return
         *     possible object is
         *     {@link CorporateLinkages.CurrentLinkagesInformation.DomesticUltimateParentsInformation }
         *     
         */
        public CorporateLinkages.CurrentLinkagesInformation.DomesticUltimateParentsInformation getDomesticUltimateParentsInformation() {
            return domesticUltimateParentsInformation;
        }

        /**
         * Sets the value of the domesticUltimateParentsInformation property.
         * 
         * @param value
         *     allowed object is
         *     {@link CorporateLinkages.CurrentLinkagesInformation.DomesticUltimateParentsInformation }
         *     
         */
        public void setDomesticUltimateParentsInformation(CorporateLinkages.CurrentLinkagesInformation.DomesticUltimateParentsInformation value) {
            this.domesticUltimateParentsInformation = value;
        }

        /**
         * Gets the value of the globalUltimateParentsInformation property.
         * 
         * @return
         *     possible object is
         *     {@link CorporateLinkages.CurrentLinkagesInformation.GlobalUltimateParentsInformation }
         *     
         */
        public CorporateLinkages.CurrentLinkagesInformation.GlobalUltimateParentsInformation getGlobalUltimateParentsInformation() {
            return globalUltimateParentsInformation;
        }

        /**
         * Sets the value of the globalUltimateParentsInformation property.
         * 
         * @param value
         *     allowed object is
         *     {@link CorporateLinkages.CurrentLinkagesInformation.GlobalUltimateParentsInformation }
         *     
         */
        public void setGlobalUltimateParentsInformation(CorporateLinkages.CurrentLinkagesInformation.GlobalUltimateParentsInformation value) {
            this.globalUltimateParentsInformation = value;
        }

        /**
         * Gets the value of the shareholdersInformation property.
         * 
         * @return
         *     possible object is
         *     {@link CorporateLinkages.CurrentLinkagesInformation.ShareholdersInformation }
         *     
         */
        public CorporateLinkages.CurrentLinkagesInformation.ShareholdersInformation getShareholdersInformation() {
            return shareholdersInformation;
        }

        /**
         * Sets the value of the shareholdersInformation property.
         * 
         * @param value
         *     allowed object is
         *     {@link CorporateLinkages.CurrentLinkagesInformation.ShareholdersInformation }
         *     
         */
        public void setShareholdersInformation(CorporateLinkages.CurrentLinkagesInformation.ShareholdersInformation value) {
            this.shareholdersInformation = value;
        }

        /**
         * Gets the value of the subsidiariesInformation property.
         * 
         * @return
         *     possible object is
         *     {@link CorporateLinkages.CurrentLinkagesInformation.SubsidiariesInformation }
         *     
         */
        public CorporateLinkages.CurrentLinkagesInformation.SubsidiariesInformation getSubsidiariesInformation() {
            return subsidiariesInformation;
        }

        /**
         * Sets the value of the subsidiariesInformation property.
         * 
         * @param value
         *     allowed object is
         *     {@link CorporateLinkages.CurrentLinkagesInformation.SubsidiariesInformation }
         *     
         */
        public void setSubsidiariesInformation(CorporateLinkages.CurrentLinkagesInformation.SubsidiariesInformation value) {
            this.subsidiariesInformation = value;
        }

        /**
         * Gets the value of the minorityInterestsInformation property.
         * 
         * @return
         *     possible object is
         *     {@link CorporateLinkages.CurrentLinkagesInformation.MinorityInterestsInformation }
         *     
         */
        public CorporateLinkages.CurrentLinkagesInformation.MinorityInterestsInformation getMinorityInterestsInformation() {
            return minorityInterestsInformation;
        }

        /**
         * Sets the value of the minorityInterestsInformation property.
         * 
         * @param value
         *     allowed object is
         *     {@link CorporateLinkages.CurrentLinkagesInformation.MinorityInterestsInformation }
         *     
         */
        public void setMinorityInterestsInformation(CorporateLinkages.CurrentLinkagesInformation.MinorityInterestsInformation value) {
            this.minorityInterestsInformation = value;
        }

        /**
         * Gets the value of the branchesInformation property.
         * 
         * @return
         *     possible object is
         *     {@link CorporateLinkages.CurrentLinkagesInformation.BranchesInformation }
         *     
         */
        public CorporateLinkages.CurrentLinkagesInformation.BranchesInformation getBranchesInformation() {
            return branchesInformation;
        }

        /**
         * Sets the value of the branchesInformation property.
         * 
         * @param value
         *     allowed object is
         *     {@link CorporateLinkages.CurrentLinkagesInformation.BranchesInformation }
         *     
         */
        public void setBranchesInformation(CorporateLinkages.CurrentLinkagesInformation.BranchesInformation value) {
            this.branchesInformation = value;
        }

        /**
         * Gets the value of the associateCorporateDirectorships property.
         * 
         * @return
         *     possible object is
         *     {@link CorporateLinkages.CurrentLinkagesInformation.AssociateCorporateDirectorships }
         *     
         */
        public CorporateLinkages.CurrentLinkagesInformation.AssociateCorporateDirectorships getAssociateCorporateDirectorships() {
            return associateCorporateDirectorships;
        }

        /**
         * Sets the value of the associateCorporateDirectorships property.
         * 
         * @param value
         *     allowed object is
         *     {@link CorporateLinkages.CurrentLinkagesInformation.AssociateCorporateDirectorships }
         *     
         */
        public void setAssociateCorporateDirectorships(CorporateLinkages.CurrentLinkagesInformation.AssociateCorporateDirectorships value) {
            this.associateCorporateDirectorships = value;
        }

        /**
         * Gets the value of the letterofLiabilityInformation property.
         * 
         * @return
         *     possible object is
         *     {@link CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation }
         *     
         */
        public CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation getLetterofLiabilityInformation() {
            return letterofLiabilityInformation;
        }

        /**
         * Sets the value of the letterofLiabilityInformation property.
         * 
         * @param value
         *     allowed object is
         *     {@link CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation }
         *     
         */
        public void setLetterofLiabilityInformation(CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation value) {
            this.letterofLiabilityInformation = value;
        }

        /**
         * Gets the value of the letterofAgreementInformation property.
         * 
         * @return
         *     possible object is
         *     {@link CorporateLinkages.CurrentLinkagesInformation.LetterofAgreementInformation }
         *     
         */
        public CorporateLinkages.CurrentLinkagesInformation.LetterofAgreementInformation getLetterofAgreementInformation() {
            return letterofAgreementInformation;
        }

        /**
         * Sets the value of the letterofAgreementInformation property.
         * 
         * @param value
         *     allowed object is
         *     {@link CorporateLinkages.CurrentLinkagesInformation.LetterofAgreementInformation }
         *     
         */
        public void setLetterofAgreementInformation(CorporateLinkages.CurrentLinkagesInformation.LetterofAgreementInformation value) {
            this.letterofAgreementInformation = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="AssociateCorporateDirectorshipsEntry" type="{http://gateway.dnb.com/getProduct}AssociateCorporateDirectorshipsEntryType" maxOccurs="unbounded" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "associateCorporateDirectorshipsEntry"
        })
        public static class AssociateCorporateDirectorships {

            @XmlElement(name = "AssociateCorporateDirectorshipsEntry")
            protected List<AssociateCorporateDirectorshipsEntryType> associateCorporateDirectorshipsEntry;

            /**
             * Gets the value of the associateCorporateDirectorshipsEntry property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the associateCorporateDirectorshipsEntry property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getAssociateCorporateDirectorshipsEntry().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link AssociateCorporateDirectorshipsEntryType }
             * 
             * 
             */
            public List<AssociateCorporateDirectorshipsEntryType> getAssociateCorporateDirectorshipsEntry() {
                if (associateCorporateDirectorshipsEntry == null) {
                    associateCorporateDirectorshipsEntry = new ArrayList<AssociateCorporateDirectorshipsEntryType>();
                }
                return this.associateCorporateDirectorshipsEntry;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="BranchesInformationEntry" type="{http://gateway.dnb.com/getProduct}BranchesInformationEntryType" maxOccurs="unbounded" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "branchesInformationEntry"
        })
        public static class BranchesInformation {

            @XmlElement(name = "BranchesInformationEntry")
            protected List<BranchesInformationEntryType> branchesInformationEntry;

            /**
             * Gets the value of the branchesInformationEntry property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the branchesInformationEntry property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getBranchesInformationEntry().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link BranchesInformationEntryType }
             * 
             * 
             */
            public List<BranchesInformationEntryType> getBranchesInformationEntry() {
                if (branchesInformationEntry == null) {
                    branchesInformationEntry = new ArrayList<BranchesInformationEntryType>();
                }
                return this.branchesInformationEntry;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="DomesticUltimateParentsInformationEntry" type="{http://gateway.dnb.com/getProduct}ParentInformationEntryType" maxOccurs="unbounded" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "domesticUltimateParentsInformationEntry"
        })
        public static class DomesticUltimateParentsInformation {

            @XmlElement(name = "DomesticUltimateParentsInformationEntry")
            protected List<ParentInformationEntryType> domesticUltimateParentsInformationEntry;

            /**
             * Gets the value of the domesticUltimateParentsInformationEntry property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the domesticUltimateParentsInformationEntry property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getDomesticUltimateParentsInformationEntry().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ParentInformationEntryType }
             * 
             * 
             */
            public List<ParentInformationEntryType> getDomesticUltimateParentsInformationEntry() {
                if (domesticUltimateParentsInformationEntry == null) {
                    domesticUltimateParentsInformationEntry = new ArrayList<ParentInformationEntryType>();
                }
                return this.domesticUltimateParentsInformationEntry;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="GlobalUltimateParentsInformationEntry" type="{http://gateway.dnb.com/getProduct}ParentInformationEntryType" maxOccurs="unbounded" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "globalUltimateParentsInformationEntry"
        })
        public static class GlobalUltimateParentsInformation {

            @XmlElement(name = "GlobalUltimateParentsInformationEntry")
            protected List<ParentInformationEntryType> globalUltimateParentsInformationEntry;

            /**
             * Gets the value of the globalUltimateParentsInformationEntry property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the globalUltimateParentsInformationEntry property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getGlobalUltimateParentsInformationEntry().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ParentInformationEntryType }
             * 
             * 
             */
            public List<ParentInformationEntryType> getGlobalUltimateParentsInformationEntry() {
                if (globalUltimateParentsInformationEntry == null) {
                    globalUltimateParentsInformationEntry = new ArrayList<ParentInformationEntryType>();
                }
                return this.globalUltimateParentsInformationEntry;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="ImmediateParentsInformationEntry" type="{http://gateway.dnb.com/getProduct}ParentInformationEntryType" maxOccurs="unbounded" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "immediateParentsInformationEntry"
        })
        public static class ImmediateParentsInformation {

            @XmlElement(name = "ImmediateParentsInformationEntry")
            protected List<ParentInformationEntryType> immediateParentsInformationEntry;

            /**
             * Gets the value of the immediateParentsInformationEntry property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the immediateParentsInformationEntry property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getImmediateParentsInformationEntry().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ParentInformationEntryType }
             * 
             * 
             */
            public List<ParentInformationEntryType> getImmediateParentsInformationEntry() {
                if (immediateParentsInformationEntry == null) {
                    immediateParentsInformationEntry = new ArrayList<ParentInformationEntryType>();
                }
                return this.immediateParentsInformationEntry;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="LetterofAgreementInformationEntry" type="{http://gateway.dnb.com/getProduct}LetterofAgreementInformationEntryType" maxOccurs="unbounded"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "letterofAgreementInformationEntry"
        })
        public static class LetterofAgreementInformation {

            @XmlElement(name = "LetterofAgreementInformationEntry", required = true)
            protected List<LetterofAgreementInformationEntryType> letterofAgreementInformationEntry;

            /**
             * Gets the value of the letterofAgreementInformationEntry property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the letterofAgreementInformationEntry property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getLetterofAgreementInformationEntry().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link LetterofAgreementInformationEntryType }
             * 
             * 
             */
            public List<LetterofAgreementInformationEntryType> getLetterofAgreementInformationEntry() {
                if (letterofAgreementInformationEntry == null) {
                    letterofAgreementInformationEntry = new ArrayList<LetterofAgreementInformationEntryType>();
                }
                return this.letterofAgreementInformationEntry;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="LetterofLiabilitySubscriberInformation" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="CurrentLetterofLiabilitySubscriberInformation" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;extension base="{http://gateway.dnb.com/getProduct}LetterofLiabilityRolePlayerInformationEntryType">
         *                           &lt;sequence minOccurs="0">
         *                             &lt;element name="OtherLetterofLiabilitySubscriptionsInformation">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="OtherLetterofLiabilitySubscriptionsInformationEntry" type="{http://gateway.dnb.com/getProduct}LetterofLiabilityRolePlayerInformationEntryType" maxOccurs="unbounded"/>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/extension>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="PreviousLetterofLiabilitySubscriberInformation" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;extension base="{http://gateway.dnb.com/getProduct}LetterofLiabilityRolePlayerInformationEntryType">
         *                           &lt;sequence minOccurs="0">
         *                             &lt;element name="LetterWIthdrawnDate" type="{http://gateway.dnb.com/getProduct}DNBDate"/>
         *                           &lt;/sequence>
         *                         &lt;/extension>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="LetterofLiabilityIssuerInformation" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="LetterofLiabilityIssuerInformationEntry" maxOccurs="unbounded">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;extension base="{http://gateway.dnb.com/getProduct}LetterofLiabilityRolePlayerInformationEntryType">
         *                           &lt;sequence minOccurs="0">
         *                             &lt;element name="LastLetterofAgreementDate" type="{http://gateway.dnb.com/getProduct}DNBDate"/>
         *                           &lt;/sequence>
         *                         &lt;/extension>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "letterofLiabilitySubscriberInformation",
            "letterofLiabilityIssuerInformation"
        })
        public static class LetterofLiabilityInformation {

            @XmlElement(name = "LetterofLiabilitySubscriberInformation")
            protected CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilitySubscriberInformation letterofLiabilitySubscriberInformation;
            @XmlElement(name = "LetterofLiabilityIssuerInformation")
            protected CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilityIssuerInformation letterofLiabilityIssuerInformation;

            /**
             * Gets the value of the letterofLiabilitySubscriberInformation property.
             * 
             * @return
             *     possible object is
             *     {@link CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilitySubscriberInformation }
             *     
             */
            public CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilitySubscriberInformation getLetterofLiabilitySubscriberInformation() {
                return letterofLiabilitySubscriberInformation;
            }

            /**
             * Sets the value of the letterofLiabilitySubscriberInformation property.
             * 
             * @param value
             *     allowed object is
             *     {@link CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilitySubscriberInformation }
             *     
             */
            public void setLetterofLiabilitySubscriberInformation(CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilitySubscriberInformation value) {
                this.letterofLiabilitySubscriberInformation = value;
            }

            /**
             * Gets the value of the letterofLiabilityIssuerInformation property.
             * 
             * @return
             *     possible object is
             *     {@link CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilityIssuerInformation }
             *     
             */
            public CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilityIssuerInformation getLetterofLiabilityIssuerInformation() {
                return letterofLiabilityIssuerInformation;
            }

            /**
             * Sets the value of the letterofLiabilityIssuerInformation property.
             * 
             * @param value
             *     allowed object is
             *     {@link CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilityIssuerInformation }
             *     
             */
            public void setLetterofLiabilityIssuerInformation(CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilityIssuerInformation value) {
                this.letterofLiabilityIssuerInformation = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="LetterofLiabilityIssuerInformationEntry" maxOccurs="unbounded">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;extension base="{http://gateway.dnb.com/getProduct}LetterofLiabilityRolePlayerInformationEntryType">
             *                 &lt;sequence minOccurs="0">
             *                   &lt;element name="LastLetterofAgreementDate" type="{http://gateway.dnb.com/getProduct}DNBDate"/>
             *                 &lt;/sequence>
             *               &lt;/extension>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "letterofLiabilityIssuerInformationEntry"
            })
            public static class LetterofLiabilityIssuerInformation {

                @XmlElement(name = "LetterofLiabilityIssuerInformationEntry", required = true)
                protected List<CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilityIssuerInformation.LetterofLiabilityIssuerInformationEntry> letterofLiabilityIssuerInformationEntry;

                /**
                 * Gets the value of the letterofLiabilityIssuerInformationEntry property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the letterofLiabilityIssuerInformationEntry property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getLetterofLiabilityIssuerInformationEntry().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilityIssuerInformation.LetterofLiabilityIssuerInformationEntry }
                 * 
                 * 
                 */
                public List<CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilityIssuerInformation.LetterofLiabilityIssuerInformationEntry> getLetterofLiabilityIssuerInformationEntry() {
                    if (letterofLiabilityIssuerInformationEntry == null) {
                        letterofLiabilityIssuerInformationEntry = new ArrayList<CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilityIssuerInformation.LetterofLiabilityIssuerInformationEntry>();
                    }
                    return this.letterofLiabilityIssuerInformationEntry;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;extension base="{http://gateway.dnb.com/getProduct}LetterofLiabilityRolePlayerInformationEntryType">
                 *       &lt;sequence minOccurs="0">
                 *         &lt;element name="LastLetterofAgreementDate" type="{http://gateway.dnb.com/getProduct}DNBDate"/>
                 *       &lt;/sequence>
                 *     &lt;/extension>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "lastLetterofAgreementDate"
                })
                public static class LetterofLiabilityIssuerInformationEntry
                    extends LetterofLiabilityRolePlayerInformationEntryType
                {

                    @XmlElement(name = "LastLetterofAgreementDate")
                    protected DNBDate lastLetterofAgreementDate;

                    /**
                     * Gets the value of the lastLetterofAgreementDate property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link DNBDate }
                     *     
                     */
                    public DNBDate getLastLetterofAgreementDate() {
                        return lastLetterofAgreementDate;
                    }

                    /**
                     * Sets the value of the lastLetterofAgreementDate property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link DNBDate }
                     *     
                     */
                    public void setLastLetterofAgreementDate(DNBDate value) {
                        this.lastLetterofAgreementDate = value;
                    }

                }

            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="CurrentLetterofLiabilitySubscriberInformation" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;extension base="{http://gateway.dnb.com/getProduct}LetterofLiabilityRolePlayerInformationEntryType">
             *                 &lt;sequence minOccurs="0">
             *                   &lt;element name="OtherLetterofLiabilitySubscriptionsInformation">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="OtherLetterofLiabilitySubscriptionsInformationEntry" type="{http://gateway.dnb.com/getProduct}LetterofLiabilityRolePlayerInformationEntryType" maxOccurs="unbounded"/>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/extension>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="PreviousLetterofLiabilitySubscriberInformation" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;extension base="{http://gateway.dnb.com/getProduct}LetterofLiabilityRolePlayerInformationEntryType">
             *                 &lt;sequence minOccurs="0">
             *                   &lt;element name="LetterWIthdrawnDate" type="{http://gateway.dnb.com/getProduct}DNBDate"/>
             *                 &lt;/sequence>
             *               &lt;/extension>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "currentLetterofLiabilitySubscriberInformation",
                "previousLetterofLiabilitySubscriberInformation"
            })
            public static class LetterofLiabilitySubscriberInformation {

                @XmlElement(name = "CurrentLetterofLiabilitySubscriberInformation")
                protected CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilitySubscriberInformation.CurrentLetterofLiabilitySubscriberInformation currentLetterofLiabilitySubscriberInformation;
                @XmlElement(name = "PreviousLetterofLiabilitySubscriberInformation")
                protected CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilitySubscriberInformation.PreviousLetterofLiabilitySubscriberInformation previousLetterofLiabilitySubscriberInformation;

                /**
                 * Gets the value of the currentLetterofLiabilitySubscriberInformation property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilitySubscriberInformation.CurrentLetterofLiabilitySubscriberInformation }
                 *     
                 */
                public CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilitySubscriberInformation.CurrentLetterofLiabilitySubscriberInformation getCurrentLetterofLiabilitySubscriberInformation() {
                    return currentLetterofLiabilitySubscriberInformation;
                }

                /**
                 * Sets the value of the currentLetterofLiabilitySubscriberInformation property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilitySubscriberInformation.CurrentLetterofLiabilitySubscriberInformation }
                 *     
                 */
                public void setCurrentLetterofLiabilitySubscriberInformation(CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilitySubscriberInformation.CurrentLetterofLiabilitySubscriberInformation value) {
                    this.currentLetterofLiabilitySubscriberInformation = value;
                }

                /**
                 * Gets the value of the previousLetterofLiabilitySubscriberInformation property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilitySubscriberInformation.PreviousLetterofLiabilitySubscriberInformation }
                 *     
                 */
                public CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilitySubscriberInformation.PreviousLetterofLiabilitySubscriberInformation getPreviousLetterofLiabilitySubscriberInformation() {
                    return previousLetterofLiabilitySubscriberInformation;
                }

                /**
                 * Sets the value of the previousLetterofLiabilitySubscriberInformation property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilitySubscriberInformation.PreviousLetterofLiabilitySubscriberInformation }
                 *     
                 */
                public void setPreviousLetterofLiabilitySubscriberInformation(CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilitySubscriberInformation.PreviousLetterofLiabilitySubscriberInformation value) {
                    this.previousLetterofLiabilitySubscriberInformation = value;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;extension base="{http://gateway.dnb.com/getProduct}LetterofLiabilityRolePlayerInformationEntryType">
                 *       &lt;sequence minOccurs="0">
                 *         &lt;element name="OtherLetterofLiabilitySubscriptionsInformation">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="OtherLetterofLiabilitySubscriptionsInformationEntry" type="{http://gateway.dnb.com/getProduct}LetterofLiabilityRolePlayerInformationEntryType" maxOccurs="unbounded"/>
                 *                 &lt;/sequence>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *       &lt;/sequence>
                 *     &lt;/extension>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "otherLetterofLiabilitySubscriptionsInformation"
                })
                public static class CurrentLetterofLiabilitySubscriberInformation
                    extends LetterofLiabilityRolePlayerInformationEntryType
                {

                    @XmlElement(name = "OtherLetterofLiabilitySubscriptionsInformation")
                    protected CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilitySubscriberInformation.CurrentLetterofLiabilitySubscriberInformation.OtherLetterofLiabilitySubscriptionsInformation otherLetterofLiabilitySubscriptionsInformation;

                    /**
                     * Gets the value of the otherLetterofLiabilitySubscriptionsInformation property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilitySubscriberInformation.CurrentLetterofLiabilitySubscriberInformation.OtherLetterofLiabilitySubscriptionsInformation }
                     *     
                     */
                    public CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilitySubscriberInformation.CurrentLetterofLiabilitySubscriberInformation.OtherLetterofLiabilitySubscriptionsInformation getOtherLetterofLiabilitySubscriptionsInformation() {
                        return otherLetterofLiabilitySubscriptionsInformation;
                    }

                    /**
                     * Sets the value of the otherLetterofLiabilitySubscriptionsInformation property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilitySubscriberInformation.CurrentLetterofLiabilitySubscriberInformation.OtherLetterofLiabilitySubscriptionsInformation }
                     *     
                     */
                    public void setOtherLetterofLiabilitySubscriptionsInformation(CorporateLinkages.CurrentLinkagesInformation.LetterofLiabilityInformation.LetterofLiabilitySubscriberInformation.CurrentLetterofLiabilitySubscriberInformation.OtherLetterofLiabilitySubscriptionsInformation value) {
                        this.otherLetterofLiabilitySubscriptionsInformation = value;
                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;sequence>
                     *         &lt;element name="OtherLetterofLiabilitySubscriptionsInformationEntry" type="{http://gateway.dnb.com/getProduct}LetterofLiabilityRolePlayerInformationEntryType" maxOccurs="unbounded"/>
                     *       &lt;/sequence>
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "otherLetterofLiabilitySubscriptionsInformationEntry"
                    })
                    public static class OtherLetterofLiabilitySubscriptionsInformation {

                        @XmlElement(name = "OtherLetterofLiabilitySubscriptionsInformationEntry", required = true)
                        protected List<LetterofLiabilityRolePlayerInformationEntryType> otherLetterofLiabilitySubscriptionsInformationEntry;

                        /**
                         * Gets the value of the otherLetterofLiabilitySubscriptionsInformationEntry property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the otherLetterofLiabilitySubscriptionsInformationEntry property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getOtherLetterofLiabilitySubscriptionsInformationEntry().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link LetterofLiabilityRolePlayerInformationEntryType }
                         * 
                         * 
                         */
                        public List<LetterofLiabilityRolePlayerInformationEntryType> getOtherLetterofLiabilitySubscriptionsInformationEntry() {
                            if (otherLetterofLiabilitySubscriptionsInformationEntry == null) {
                                otherLetterofLiabilitySubscriptionsInformationEntry = new ArrayList<LetterofLiabilityRolePlayerInformationEntryType>();
                            }
                            return this.otherLetterofLiabilitySubscriptionsInformationEntry;
                        }

                    }

                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;extension base="{http://gateway.dnb.com/getProduct}LetterofLiabilityRolePlayerInformationEntryType">
                 *       &lt;sequence minOccurs="0">
                 *         &lt;element name="LetterWIthdrawnDate" type="{http://gateway.dnb.com/getProduct}DNBDate"/>
                 *       &lt;/sequence>
                 *     &lt;/extension>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "letterWIthdrawnDate"
                })
                public static class PreviousLetterofLiabilitySubscriberInformation
                    extends LetterofLiabilityRolePlayerInformationEntryType
                {

                    @XmlElement(name = "LetterWIthdrawnDate")
                    protected DNBDate letterWIthdrawnDate;

                    /**
                     * Gets the value of the letterWIthdrawnDate property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link DNBDate }
                     *     
                     */
                    public DNBDate getLetterWIthdrawnDate() {
                        return letterWIthdrawnDate;
                    }

                    /**
                     * Sets the value of the letterWIthdrawnDate property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link DNBDate }
                     *     
                     */
                    public void setLetterWIthdrawnDate(DNBDate value) {
                        this.letterWIthdrawnDate = value;
                    }

                }

            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="MinorityInterestsInformationEntry" type="{http://gateway.dnb.com/getProduct}ChildLinkageInformationEntryType" maxOccurs="unbounded" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "minorityInterestsInformationEntry"
        })
        public static class MinorityInterestsInformation {

            @XmlElement(name = "MinorityInterestsInformationEntry")
            protected List<ChildLinkageInformationEntryType> minorityInterestsInformationEntry;

            /**
             * Gets the value of the minorityInterestsInformationEntry property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the minorityInterestsInformationEntry property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getMinorityInterestsInformationEntry().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ChildLinkageInformationEntryType }
             * 
             * 
             */
            public List<ChildLinkageInformationEntryType> getMinorityInterestsInformationEntry() {
                if (minorityInterestsInformationEntry == null) {
                    minorityInterestsInformationEntry = new ArrayList<ChildLinkageInformationEntryType>();
                }
                return this.minorityInterestsInformationEntry;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="ShareholdersInformationEntry" type="{http://gateway.dnb.com/getProduct}ShareholdersInformationEntryType" maxOccurs="unbounded" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "shareholdersInformationEntry"
        })
        public static class ShareholdersInformation {

            @XmlElement(name = "ShareholdersInformationEntry")
            protected List<ShareholdersInformationEntryType> shareholdersInformationEntry;

            /**
             * Gets the value of the shareholdersInformationEntry property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the shareholdersInformationEntry property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getShareholdersInformationEntry().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ShareholdersInformationEntryType }
             * 
             * 
             */
            public List<ShareholdersInformationEntryType> getShareholdersInformationEntry() {
                if (shareholdersInformationEntry == null) {
                    shareholdersInformationEntry = new ArrayList<ShareholdersInformationEntryType>();
                }
                return this.shareholdersInformationEntry;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="SubsidiariesInformationEntry" type="{http://gateway.dnb.com/getProduct}ChildLinkageInformationEntryType" maxOccurs="unbounded" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "subsidiariesInformationEntry"
        })
        public static class SubsidiariesInformation {

            @XmlElement(name = "SubsidiariesInformationEntry")
            protected List<ChildLinkageInformationEntryType> subsidiariesInformationEntry;

            /**
             * Gets the value of the subsidiariesInformationEntry property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the subsidiariesInformationEntry property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getSubsidiariesInformationEntry().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ChildLinkageInformationEntryType }
             * 
             * 
             */
            public List<ChildLinkageInformationEntryType> getSubsidiariesInformationEntry() {
                if (subsidiariesInformationEntry == null) {
                    subsidiariesInformationEntry = new ArrayList<ChildLinkageInformationEntryType>();
                }
                return this.subsidiariesInformationEntry;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ImmediateParentsInformation" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ImmediateParentsInformationEntry" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;extension base="{http://gateway.dnb.com/getProduct}ParentInformationEntryType">
     *                           &lt;sequence minOccurs="0">
     *                             &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
     *                             &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/extension>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="DomesticUltimateParentsInformation" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="DomesticUltimateParentsInformationEntry" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;extension base="{http://gateway.dnb.com/getProduct}ParentInformationEntryType">
     *                           &lt;sequence minOccurs="0">
     *                             &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
     *                             &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/extension>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="GlobalUltimateParentsInformation" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="GlobalUltimateParentsInformationEntry" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;extension base="{http://gateway.dnb.com/getProduct}ParentInformationEntryType">
     *                           &lt;sequence minOccurs="0">
     *                             &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
     *                             &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/extension>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="ShareholdersInformation" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ShareholdersInformationEntry" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;extension base="{http://gateway.dnb.com/getProduct}ShareholdersInformationEntryType">
     *                           &lt;sequence minOccurs="0">
     *                             &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
     *                             &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/extension>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="SubsidiariesInformation" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="SubsidiariesInformationEntry" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;extension base="{http://gateway.dnb.com/getProduct}ChildLinkageInformationEntryType">
     *                           &lt;sequence minOccurs="0">
     *                             &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
     *                             &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/extension>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="MinorityInterestsInformation" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="MinorityInterestsInformationEntry" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;extension base="{http://gateway.dnb.com/getProduct}ChildLinkageInformationEntryType">
     *                           &lt;sequence minOccurs="0">
     *                             &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
     *                             &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/extension>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="BranchesInformation" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="BranchesInformationEntry" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;extension base="{http://gateway.dnb.com/getProduct}BranchesInformationEntryType">
     *                           &lt;sequence minOccurs="0">
     *                             &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
     *                             &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/extension>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="AssociateCorporateDirectorships" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="AssociateCorporateDirectorshipsEntry" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;extension base="{http://gateway.dnb.com/getProduct}AssociateCorporateDirectorshipsEntryType">
     *                           &lt;sequence minOccurs="0">
     *                             &lt;element name="PrincipalPositionResignedDate" type="{http://gateway.dnb.com/getProduct}DNBDate"/>
     *                           &lt;/sequence>
     *                         &lt;/extension>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "immediateParentsInformation",
        "domesticUltimateParentsInformation",
        "globalUltimateParentsInformation",
        "shareholdersInformation",
        "subsidiariesInformation",
        "minorityInterestsInformation",
        "branchesInformation",
        "associateCorporateDirectorships"
    })
    public static class PreviousLinkagesInformation {

        @XmlElement(name = "ImmediateParentsInformation")
        protected CorporateLinkages.PreviousLinkagesInformation.ImmediateParentsInformation immediateParentsInformation;
        @XmlElement(name = "DomesticUltimateParentsInformation")
        protected CorporateLinkages.PreviousLinkagesInformation.DomesticUltimateParentsInformation domesticUltimateParentsInformation;
        @XmlElement(name = "GlobalUltimateParentsInformation")
        protected CorporateLinkages.PreviousLinkagesInformation.GlobalUltimateParentsInformation globalUltimateParentsInformation;
        @XmlElement(name = "ShareholdersInformation")
        protected CorporateLinkages.PreviousLinkagesInformation.ShareholdersInformation shareholdersInformation;
        @XmlElement(name = "SubsidiariesInformation")
        protected CorporateLinkages.PreviousLinkagesInformation.SubsidiariesInformation subsidiariesInformation;
        @XmlElement(name = "MinorityInterestsInformation")
        protected CorporateLinkages.PreviousLinkagesInformation.MinorityInterestsInformation minorityInterestsInformation;
        @XmlElement(name = "BranchesInformation")
        protected CorporateLinkages.PreviousLinkagesInformation.BranchesInformation branchesInformation;
        @XmlElement(name = "AssociateCorporateDirectorships")
        protected CorporateLinkages.PreviousLinkagesInformation.AssociateCorporateDirectorships associateCorporateDirectorships;

        /**
         * Gets the value of the immediateParentsInformation property.
         * 
         * @return
         *     possible object is
         *     {@link CorporateLinkages.PreviousLinkagesInformation.ImmediateParentsInformation }
         *     
         */
        public CorporateLinkages.PreviousLinkagesInformation.ImmediateParentsInformation getImmediateParentsInformation() {
            return immediateParentsInformation;
        }

        /**
         * Sets the value of the immediateParentsInformation property.
         * 
         * @param value
         *     allowed object is
         *     {@link CorporateLinkages.PreviousLinkagesInformation.ImmediateParentsInformation }
         *     
         */
        public void setImmediateParentsInformation(CorporateLinkages.PreviousLinkagesInformation.ImmediateParentsInformation value) {
            this.immediateParentsInformation = value;
        }

        /**
         * Gets the value of the domesticUltimateParentsInformation property.
         * 
         * @return
         *     possible object is
         *     {@link CorporateLinkages.PreviousLinkagesInformation.DomesticUltimateParentsInformation }
         *     
         */
        public CorporateLinkages.PreviousLinkagesInformation.DomesticUltimateParentsInformation getDomesticUltimateParentsInformation() {
            return domesticUltimateParentsInformation;
        }

        /**
         * Sets the value of the domesticUltimateParentsInformation property.
         * 
         * @param value
         *     allowed object is
         *     {@link CorporateLinkages.PreviousLinkagesInformation.DomesticUltimateParentsInformation }
         *     
         */
        public void setDomesticUltimateParentsInformation(CorporateLinkages.PreviousLinkagesInformation.DomesticUltimateParentsInformation value) {
            this.domesticUltimateParentsInformation = value;
        }

        /**
         * Gets the value of the globalUltimateParentsInformation property.
         * 
         * @return
         *     possible object is
         *     {@link CorporateLinkages.PreviousLinkagesInformation.GlobalUltimateParentsInformation }
         *     
         */
        public CorporateLinkages.PreviousLinkagesInformation.GlobalUltimateParentsInformation getGlobalUltimateParentsInformation() {
            return globalUltimateParentsInformation;
        }

        /**
         * Sets the value of the globalUltimateParentsInformation property.
         * 
         * @param value
         *     allowed object is
         *     {@link CorporateLinkages.PreviousLinkagesInformation.GlobalUltimateParentsInformation }
         *     
         */
        public void setGlobalUltimateParentsInformation(CorporateLinkages.PreviousLinkagesInformation.GlobalUltimateParentsInformation value) {
            this.globalUltimateParentsInformation = value;
        }

        /**
         * Gets the value of the shareholdersInformation property.
         * 
         * @return
         *     possible object is
         *     {@link CorporateLinkages.PreviousLinkagesInformation.ShareholdersInformation }
         *     
         */
        public CorporateLinkages.PreviousLinkagesInformation.ShareholdersInformation getShareholdersInformation() {
            return shareholdersInformation;
        }

        /**
         * Sets the value of the shareholdersInformation property.
         * 
         * @param value
         *     allowed object is
         *     {@link CorporateLinkages.PreviousLinkagesInformation.ShareholdersInformation }
         *     
         */
        public void setShareholdersInformation(CorporateLinkages.PreviousLinkagesInformation.ShareholdersInformation value) {
            this.shareholdersInformation = value;
        }

        /**
         * Gets the value of the subsidiariesInformation property.
         * 
         * @return
         *     possible object is
         *     {@link CorporateLinkages.PreviousLinkagesInformation.SubsidiariesInformation }
         *     
         */
        public CorporateLinkages.PreviousLinkagesInformation.SubsidiariesInformation getSubsidiariesInformation() {
            return subsidiariesInformation;
        }

        /**
         * Sets the value of the subsidiariesInformation property.
         * 
         * @param value
         *     allowed object is
         *     {@link CorporateLinkages.PreviousLinkagesInformation.SubsidiariesInformation }
         *     
         */
        public void setSubsidiariesInformation(CorporateLinkages.PreviousLinkagesInformation.SubsidiariesInformation value) {
            this.subsidiariesInformation = value;
        }

        /**
         * Gets the value of the minorityInterestsInformation property.
         * 
         * @return
         *     possible object is
         *     {@link CorporateLinkages.PreviousLinkagesInformation.MinorityInterestsInformation }
         *     
         */
        public CorporateLinkages.PreviousLinkagesInformation.MinorityInterestsInformation getMinorityInterestsInformation() {
            return minorityInterestsInformation;
        }

        /**
         * Sets the value of the minorityInterestsInformation property.
         * 
         * @param value
         *     allowed object is
         *     {@link CorporateLinkages.PreviousLinkagesInformation.MinorityInterestsInformation }
         *     
         */
        public void setMinorityInterestsInformation(CorporateLinkages.PreviousLinkagesInformation.MinorityInterestsInformation value) {
            this.minorityInterestsInformation = value;
        }

        /**
         * Gets the value of the branchesInformation property.
         * 
         * @return
         *     possible object is
         *     {@link CorporateLinkages.PreviousLinkagesInformation.BranchesInformation }
         *     
         */
        public CorporateLinkages.PreviousLinkagesInformation.BranchesInformation getBranchesInformation() {
            return branchesInformation;
        }

        /**
         * Sets the value of the branchesInformation property.
         * 
         * @param value
         *     allowed object is
         *     {@link CorporateLinkages.PreviousLinkagesInformation.BranchesInformation }
         *     
         */
        public void setBranchesInformation(CorporateLinkages.PreviousLinkagesInformation.BranchesInformation value) {
            this.branchesInformation = value;
        }

        /**
         * Gets the value of the associateCorporateDirectorships property.
         * 
         * @return
         *     possible object is
         *     {@link CorporateLinkages.PreviousLinkagesInformation.AssociateCorporateDirectorships }
         *     
         */
        public CorporateLinkages.PreviousLinkagesInformation.AssociateCorporateDirectorships getAssociateCorporateDirectorships() {
            return associateCorporateDirectorships;
        }

        /**
         * Sets the value of the associateCorporateDirectorships property.
         * 
         * @param value
         *     allowed object is
         *     {@link CorporateLinkages.PreviousLinkagesInformation.AssociateCorporateDirectorships }
         *     
         */
        public void setAssociateCorporateDirectorships(CorporateLinkages.PreviousLinkagesInformation.AssociateCorporateDirectorships value) {
            this.associateCorporateDirectorships = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="AssociateCorporateDirectorshipsEntry" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{http://gateway.dnb.com/getProduct}AssociateCorporateDirectorshipsEntryType">
         *                 &lt;sequence minOccurs="0">
         *                   &lt;element name="PrincipalPositionResignedDate" type="{http://gateway.dnb.com/getProduct}DNBDate"/>
         *                 &lt;/sequence>
         *               &lt;/extension>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "associateCorporateDirectorshipsEntry"
        })
        public static class AssociateCorporateDirectorships {

            @XmlElement(name = "AssociateCorporateDirectorshipsEntry")
            protected List<CorporateLinkages.PreviousLinkagesInformation.AssociateCorporateDirectorships.AssociateCorporateDirectorshipsEntry> associateCorporateDirectorshipsEntry;

            /**
             * Gets the value of the associateCorporateDirectorshipsEntry property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the associateCorporateDirectorshipsEntry property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getAssociateCorporateDirectorshipsEntry().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link CorporateLinkages.PreviousLinkagesInformation.AssociateCorporateDirectorships.AssociateCorporateDirectorshipsEntry }
             * 
             * 
             */
            public List<CorporateLinkages.PreviousLinkagesInformation.AssociateCorporateDirectorships.AssociateCorporateDirectorshipsEntry> getAssociateCorporateDirectorshipsEntry() {
                if (associateCorporateDirectorshipsEntry == null) {
                    associateCorporateDirectorshipsEntry = new ArrayList<CorporateLinkages.PreviousLinkagesInformation.AssociateCorporateDirectorships.AssociateCorporateDirectorshipsEntry>();
                }
                return this.associateCorporateDirectorshipsEntry;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;extension base="{http://gateway.dnb.com/getProduct}AssociateCorporateDirectorshipsEntryType">
             *       &lt;sequence minOccurs="0">
             *         &lt;element name="PrincipalPositionResignedDate" type="{http://gateway.dnb.com/getProduct}DNBDate"/>
             *       &lt;/sequence>
             *     &lt;/extension>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "principalPositionResignedDate"
            })
            public static class AssociateCorporateDirectorshipsEntry
                extends AssociateCorporateDirectorshipsEntryType
            {

                @XmlElement(name = "PrincipalPositionResignedDate")
                protected DNBDate principalPositionResignedDate;

                /**
                 * Gets the value of the principalPositionResignedDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link DNBDate }
                 *     
                 */
                public DNBDate getPrincipalPositionResignedDate() {
                    return principalPositionResignedDate;
                }

                /**
                 * Sets the value of the principalPositionResignedDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link DNBDate }
                 *     
                 */
                public void setPrincipalPositionResignedDate(DNBDate value) {
                    this.principalPositionResignedDate = value;
                }

            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="BranchesInformationEntry" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{http://gateway.dnb.com/getProduct}BranchesInformationEntryType">
         *                 &lt;sequence minOccurs="0">
         *                   &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
         *                   &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/extension>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "branchesInformationEntry"
        })
        public static class BranchesInformation {

            @XmlElement(name = "BranchesInformationEntry")
            protected List<CorporateLinkages.PreviousLinkagesInformation.BranchesInformation.BranchesInformationEntry> branchesInformationEntry;

            /**
             * Gets the value of the branchesInformationEntry property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the branchesInformationEntry property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getBranchesInformationEntry().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link CorporateLinkages.PreviousLinkagesInformation.BranchesInformation.BranchesInformationEntry }
             * 
             * 
             */
            public List<CorporateLinkages.PreviousLinkagesInformation.BranchesInformation.BranchesInformationEntry> getBranchesInformationEntry() {
                if (branchesInformationEntry == null) {
                    branchesInformationEntry = new ArrayList<CorporateLinkages.PreviousLinkagesInformation.BranchesInformation.BranchesInformationEntry>();
                }
                return this.branchesInformationEntry;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;extension base="{http://gateway.dnb.com/getProduct}BranchesInformationEntryType">
             *       &lt;sequence minOccurs="0">
             *         &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
             *         &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *       &lt;/sequence>
             *     &lt;/extension>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "associationEndDate",
                "officialReference"
            })
            public static class BranchesInformationEntry
                extends BranchesInformationEntryType
            {

                @XmlElement(name = "AssociationEndDate")
                protected DNBDate associationEndDate;
                @XmlElement(name = "OfficialReference")
                protected String officialReference;

                /**
                 * Gets the value of the associationEndDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link DNBDate }
                 *     
                 */
                public DNBDate getAssociationEndDate() {
                    return associationEndDate;
                }

                /**
                 * Sets the value of the associationEndDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link DNBDate }
                 *     
                 */
                public void setAssociationEndDate(DNBDate value) {
                    this.associationEndDate = value;
                }

                /**
                 * Gets the value of the officialReference property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getOfficialReference() {
                    return officialReference;
                }

                /**
                 * Sets the value of the officialReference property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setOfficialReference(String value) {
                    this.officialReference = value;
                }

            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="DomesticUltimateParentsInformationEntry" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{http://gateway.dnb.com/getProduct}ParentInformationEntryType">
         *                 &lt;sequence minOccurs="0">
         *                   &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
         *                   &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/extension>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "domesticUltimateParentsInformationEntry"
        })
        public static class DomesticUltimateParentsInformation {

            @XmlElement(name = "DomesticUltimateParentsInformationEntry")
            protected List<CorporateLinkages.PreviousLinkagesInformation.DomesticUltimateParentsInformation.DomesticUltimateParentsInformationEntry> domesticUltimateParentsInformationEntry;

            /**
             * Gets the value of the domesticUltimateParentsInformationEntry property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the domesticUltimateParentsInformationEntry property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getDomesticUltimateParentsInformationEntry().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link CorporateLinkages.PreviousLinkagesInformation.DomesticUltimateParentsInformation.DomesticUltimateParentsInformationEntry }
             * 
             * 
             */
            public List<CorporateLinkages.PreviousLinkagesInformation.DomesticUltimateParentsInformation.DomesticUltimateParentsInformationEntry> getDomesticUltimateParentsInformationEntry() {
                if (domesticUltimateParentsInformationEntry == null) {
                    domesticUltimateParentsInformationEntry = new ArrayList<CorporateLinkages.PreviousLinkagesInformation.DomesticUltimateParentsInformation.DomesticUltimateParentsInformationEntry>();
                }
                return this.domesticUltimateParentsInformationEntry;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;extension base="{http://gateway.dnb.com/getProduct}ParentInformationEntryType">
             *       &lt;sequence minOccurs="0">
             *         &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
             *         &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *       &lt;/sequence>
             *     &lt;/extension>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "associationEndDate",
                "officialReference"
            })
            public static class DomesticUltimateParentsInformationEntry
                extends ParentInformationEntryType
            {

                @XmlElement(name = "AssociationEndDate")
                protected DNBDate associationEndDate;
                @XmlElement(name = "OfficialReference")
                protected String officialReference;

                /**
                 * Gets the value of the associationEndDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link DNBDate }
                 *     
                 */
                public DNBDate getAssociationEndDate() {
                    return associationEndDate;
                }

                /**
                 * Sets the value of the associationEndDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link DNBDate }
                 *     
                 */
                public void setAssociationEndDate(DNBDate value) {
                    this.associationEndDate = value;
                }

                /**
                 * Gets the value of the officialReference property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getOfficialReference() {
                    return officialReference;
                }

                /**
                 * Sets the value of the officialReference property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setOfficialReference(String value) {
                    this.officialReference = value;
                }

            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="GlobalUltimateParentsInformationEntry" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{http://gateway.dnb.com/getProduct}ParentInformationEntryType">
         *                 &lt;sequence minOccurs="0">
         *                   &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
         *                   &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/extension>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "globalUltimateParentsInformationEntry"
        })
        public static class GlobalUltimateParentsInformation {

            @XmlElement(name = "GlobalUltimateParentsInformationEntry")
            protected List<CorporateLinkages.PreviousLinkagesInformation.GlobalUltimateParentsInformation.GlobalUltimateParentsInformationEntry> globalUltimateParentsInformationEntry;

            /**
             * Gets the value of the globalUltimateParentsInformationEntry property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the globalUltimateParentsInformationEntry property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getGlobalUltimateParentsInformationEntry().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link CorporateLinkages.PreviousLinkagesInformation.GlobalUltimateParentsInformation.GlobalUltimateParentsInformationEntry }
             * 
             * 
             */
            public List<CorporateLinkages.PreviousLinkagesInformation.GlobalUltimateParentsInformation.GlobalUltimateParentsInformationEntry> getGlobalUltimateParentsInformationEntry() {
                if (globalUltimateParentsInformationEntry == null) {
                    globalUltimateParentsInformationEntry = new ArrayList<CorporateLinkages.PreviousLinkagesInformation.GlobalUltimateParentsInformation.GlobalUltimateParentsInformationEntry>();
                }
                return this.globalUltimateParentsInformationEntry;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;extension base="{http://gateway.dnb.com/getProduct}ParentInformationEntryType">
             *       &lt;sequence minOccurs="0">
             *         &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
             *         &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *       &lt;/sequence>
             *     &lt;/extension>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "associationEndDate",
                "officialReference"
            })
            public static class GlobalUltimateParentsInformationEntry
                extends ParentInformationEntryType
            {

                @XmlElement(name = "AssociationEndDate")
                protected DNBDate associationEndDate;
                @XmlElement(name = "OfficialReference")
                protected String officialReference;

                /**
                 * Gets the value of the associationEndDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link DNBDate }
                 *     
                 */
                public DNBDate getAssociationEndDate() {
                    return associationEndDate;
                }

                /**
                 * Sets the value of the associationEndDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link DNBDate }
                 *     
                 */
                public void setAssociationEndDate(DNBDate value) {
                    this.associationEndDate = value;
                }

                /**
                 * Gets the value of the officialReference property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getOfficialReference() {
                    return officialReference;
                }

                /**
                 * Sets the value of the officialReference property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setOfficialReference(String value) {
                    this.officialReference = value;
                }

            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="ImmediateParentsInformationEntry" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{http://gateway.dnb.com/getProduct}ParentInformationEntryType">
         *                 &lt;sequence minOccurs="0">
         *                   &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
         *                   &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/extension>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "immediateParentsInformationEntry"
        })
        public static class ImmediateParentsInformation {

            @XmlElement(name = "ImmediateParentsInformationEntry")
            protected List<CorporateLinkages.PreviousLinkagesInformation.ImmediateParentsInformation.ImmediateParentsInformationEntry> immediateParentsInformationEntry;

            /**
             * Gets the value of the immediateParentsInformationEntry property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the immediateParentsInformationEntry property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getImmediateParentsInformationEntry().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link CorporateLinkages.PreviousLinkagesInformation.ImmediateParentsInformation.ImmediateParentsInformationEntry }
             * 
             * 
             */
            public List<CorporateLinkages.PreviousLinkagesInformation.ImmediateParentsInformation.ImmediateParentsInformationEntry> getImmediateParentsInformationEntry() {
                if (immediateParentsInformationEntry == null) {
                    immediateParentsInformationEntry = new ArrayList<CorporateLinkages.PreviousLinkagesInformation.ImmediateParentsInformation.ImmediateParentsInformationEntry>();
                }
                return this.immediateParentsInformationEntry;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;extension base="{http://gateway.dnb.com/getProduct}ParentInformationEntryType">
             *       &lt;sequence minOccurs="0">
             *         &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
             *         &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *       &lt;/sequence>
             *     &lt;/extension>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "associationEndDate",
                "officialReference"
            })
            public static class ImmediateParentsInformationEntry
                extends ParentInformationEntryType
            {

                @XmlElement(name = "AssociationEndDate")
                protected DNBDate associationEndDate;
                @XmlElement(name = "OfficialReference")
                protected String officialReference;

                /**
                 * Gets the value of the associationEndDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link DNBDate }
                 *     
                 */
                public DNBDate getAssociationEndDate() {
                    return associationEndDate;
                }

                /**
                 * Sets the value of the associationEndDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link DNBDate }
                 *     
                 */
                public void setAssociationEndDate(DNBDate value) {
                    this.associationEndDate = value;
                }

                /**
                 * Gets the value of the officialReference property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getOfficialReference() {
                    return officialReference;
                }

                /**
                 * Sets the value of the officialReference property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setOfficialReference(String value) {
                    this.officialReference = value;
                }

            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="MinorityInterestsInformationEntry" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{http://gateway.dnb.com/getProduct}ChildLinkageInformationEntryType">
         *                 &lt;sequence minOccurs="0">
         *                   &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
         *                   &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/extension>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "minorityInterestsInformationEntry"
        })
        public static class MinorityInterestsInformation {

            @XmlElement(name = "MinorityInterestsInformationEntry")
            protected List<CorporateLinkages.PreviousLinkagesInformation.MinorityInterestsInformation.MinorityInterestsInformationEntry> minorityInterestsInformationEntry;

            /**
             * Gets the value of the minorityInterestsInformationEntry property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the minorityInterestsInformationEntry property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getMinorityInterestsInformationEntry().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link CorporateLinkages.PreviousLinkagesInformation.MinorityInterestsInformation.MinorityInterestsInformationEntry }
             * 
             * 
             */
            public List<CorporateLinkages.PreviousLinkagesInformation.MinorityInterestsInformation.MinorityInterestsInformationEntry> getMinorityInterestsInformationEntry() {
                if (minorityInterestsInformationEntry == null) {
                    minorityInterestsInformationEntry = new ArrayList<CorporateLinkages.PreviousLinkagesInformation.MinorityInterestsInformation.MinorityInterestsInformationEntry>();
                }
                return this.minorityInterestsInformationEntry;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;extension base="{http://gateway.dnb.com/getProduct}ChildLinkageInformationEntryType">
             *       &lt;sequence minOccurs="0">
             *         &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
             *         &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *       &lt;/sequence>
             *     &lt;/extension>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "associationEndDate",
                "officialReference"
            })
            public static class MinorityInterestsInformationEntry
                extends ChildLinkageInformationEntryType
            {

                @XmlElement(name = "AssociationEndDate")
                protected DNBDate associationEndDate;
                @XmlElement(name = "OfficialReference")
                protected String officialReference;

                /**
                 * Gets the value of the associationEndDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link DNBDate }
                 *     
                 */
                public DNBDate getAssociationEndDate() {
                    return associationEndDate;
                }

                /**
                 * Sets the value of the associationEndDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link DNBDate }
                 *     
                 */
                public void setAssociationEndDate(DNBDate value) {
                    this.associationEndDate = value;
                }

                /**
                 * Gets the value of the officialReference property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getOfficialReference() {
                    return officialReference;
                }

                /**
                 * Sets the value of the officialReference property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setOfficialReference(String value) {
                    this.officialReference = value;
                }

            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="ShareholdersInformationEntry" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{http://gateway.dnb.com/getProduct}ShareholdersInformationEntryType">
         *                 &lt;sequence minOccurs="0">
         *                   &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
         *                   &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/extension>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "shareholdersInformationEntry"
        })
        public static class ShareholdersInformation {

            @XmlElement(name = "ShareholdersInformationEntry")
            protected List<CorporateLinkages.PreviousLinkagesInformation.ShareholdersInformation.ShareholdersInformationEntry> shareholdersInformationEntry;

            /**
             * Gets the value of the shareholdersInformationEntry property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the shareholdersInformationEntry property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getShareholdersInformationEntry().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link CorporateLinkages.PreviousLinkagesInformation.ShareholdersInformation.ShareholdersInformationEntry }
             * 
             * 
             */
            public List<CorporateLinkages.PreviousLinkagesInformation.ShareholdersInformation.ShareholdersInformationEntry> getShareholdersInformationEntry() {
                if (shareholdersInformationEntry == null) {
                    shareholdersInformationEntry = new ArrayList<CorporateLinkages.PreviousLinkagesInformation.ShareholdersInformation.ShareholdersInformationEntry>();
                }
                return this.shareholdersInformationEntry;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;extension base="{http://gateway.dnb.com/getProduct}ShareholdersInformationEntryType">
             *       &lt;sequence minOccurs="0">
             *         &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
             *         &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *       &lt;/sequence>
             *     &lt;/extension>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "associationEndDate",
                "officialReference"
            })
            public static class ShareholdersInformationEntry
                extends ShareholdersInformationEntryType
            {

                @XmlElement(name = "AssociationEndDate")
                protected DNBDate associationEndDate;
                @XmlElement(name = "OfficialReference")
                protected String officialReference;

                /**
                 * Gets the value of the associationEndDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link DNBDate }
                 *     
                 */
                public DNBDate getAssociationEndDate() {
                    return associationEndDate;
                }

                /**
                 * Sets the value of the associationEndDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link DNBDate }
                 *     
                 */
                public void setAssociationEndDate(DNBDate value) {
                    this.associationEndDate = value;
                }

                /**
                 * Gets the value of the officialReference property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getOfficialReference() {
                    return officialReference;
                }

                /**
                 * Sets the value of the officialReference property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setOfficialReference(String value) {
                    this.officialReference = value;
                }

            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="SubsidiariesInformationEntry" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{http://gateway.dnb.com/getProduct}ChildLinkageInformationEntryType">
         *                 &lt;sequence minOccurs="0">
         *                   &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
         *                   &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/extension>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "subsidiariesInformationEntry"
        })
        public static class SubsidiariesInformation {

            @XmlElement(name = "SubsidiariesInformationEntry")
            protected List<CorporateLinkages.PreviousLinkagesInformation.SubsidiariesInformation.SubsidiariesInformationEntry> subsidiariesInformationEntry;

            /**
             * Gets the value of the subsidiariesInformationEntry property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the subsidiariesInformationEntry property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getSubsidiariesInformationEntry().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link CorporateLinkages.PreviousLinkagesInformation.SubsidiariesInformation.SubsidiariesInformationEntry }
             * 
             * 
             */
            public List<CorporateLinkages.PreviousLinkagesInformation.SubsidiariesInformation.SubsidiariesInformationEntry> getSubsidiariesInformationEntry() {
                if (subsidiariesInformationEntry == null) {
                    subsidiariesInformationEntry = new ArrayList<CorporateLinkages.PreviousLinkagesInformation.SubsidiariesInformation.SubsidiariesInformationEntry>();
                }
                return this.subsidiariesInformationEntry;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;extension base="{http://gateway.dnb.com/getProduct}ChildLinkageInformationEntryType">
             *       &lt;sequence minOccurs="0">
             *         &lt;element name="AssociationEndDate" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
             *         &lt;element name="OfficialReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *       &lt;/sequence>
             *     &lt;/extension>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "associationEndDate",
                "officialReference"
            })
            public static class SubsidiariesInformationEntry
                extends ChildLinkageInformationEntryType
            {

                @XmlElement(name = "AssociationEndDate")
                protected DNBDate associationEndDate;
                @XmlElement(name = "OfficialReference")
                protected String officialReference;

                /**
                 * Gets the value of the associationEndDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link DNBDate }
                 *     
                 */
                public DNBDate getAssociationEndDate() {
                    return associationEndDate;
                }

                /**
                 * Sets the value of the associationEndDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link DNBDate }
                 *     
                 */
                public void setAssociationEndDate(DNBDate value) {
                    this.associationEndDate = value;
                }

                /**
                 * Gets the value of the officialReference property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getOfficialReference() {
                    return officialReference;
                }

                /**
                 * Sets the value of the officialReference property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setOfficialReference(String value) {
                    this.officialReference = value;
                }

            }

        }

    }

}
