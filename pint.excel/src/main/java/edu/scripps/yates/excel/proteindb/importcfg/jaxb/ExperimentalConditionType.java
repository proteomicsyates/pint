//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.03.11 at 03:44:17 PM PST 
//


package edu.scripps.yates.excel.proteindb.importcfg.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for experimental_conditionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="experimental_conditionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="identification_info" type="{}identification_InfoType" minOccurs="0"/>
 *         &lt;element name="quantification_info" type="{}quantification_InfoType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="sampleRef" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "experimental_conditionType", propOrder = {
    "description",
    "identificationInfo",
    "quantificationInfo"
})
public class ExperimentalConditionType {

    @XmlElement(required = true)
    protected String description;
    @XmlElement(name = "identification_info")
    protected IdentificationInfoType identificationInfo;
    @XmlElement(name = "quantification_info")
    protected QuantificationInfoType quantificationInfo;
    @XmlAttribute(name = "id", required = true)
    protected String id;
    @XmlAttribute(name = "sampleRef", required = true)
    protected String sampleRef;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the identificationInfo property.
     * 
     * @return
     *     possible object is
     *     {@link IdentificationInfoType }
     *     
     */
    public IdentificationInfoType getIdentificationInfo() {
        return identificationInfo;
    }

    /**
     * Sets the value of the identificationInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificationInfoType }
     *     
     */
    public void setIdentificationInfo(IdentificationInfoType value) {
        this.identificationInfo = value;
    }

    /**
     * Gets the value of the quantificationInfo property.
     * 
     * @return
     *     possible object is
     *     {@link QuantificationInfoType }
     *     
     */
    public QuantificationInfoType getQuantificationInfo() {
        return quantificationInfo;
    }

    /**
     * Sets the value of the quantificationInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuantificationInfoType }
     *     
     */
    public void setQuantificationInfo(QuantificationInfoType value) {
        this.quantificationInfo = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the sampleRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSampleRef() {
        return sampleRef;
    }

    /**
     * Sets the value of the sampleRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSampleRef(String value) {
        this.sampleRef = value;
    }

}
