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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for conditionRefType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="conditionRefType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="conditionRef" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "conditionRefType")
public class ConditionRefType {

    @XmlAttribute(name = "conditionRef", required = true)
    protected String conditionRef;

    /**
     * Gets the value of the conditionRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConditionRef() {
        return conditionRef;
    }

    /**
     * Sets the value of the conditionRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConditionRef(String value) {
        this.conditionRef = value;
    }

}
