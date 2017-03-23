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
 * <p>Java class for fastaDisgestionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="fastaDisgestionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="cleavageAAs" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="misscleavages" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="enzymeOffset" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="enzymeNoCutResidues" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="isH2OPlusProtonAdded" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fastaDisgestionType")
public class FastaDisgestionType {

    @XmlAttribute(name = "cleavageAAs", required = true)
    protected String cleavageAAs;
    @XmlAttribute(name = "misscleavages", required = true)
    protected int misscleavages;
    @XmlAttribute(name = "enzymeOffset", required = true)
    protected int enzymeOffset;
    @XmlAttribute(name = "enzymeNoCutResidues", required = true)
    protected String enzymeNoCutResidues;
    @XmlAttribute(name = "isH2OPlusProtonAdded", required = true)
    protected boolean isH2OPlusProtonAdded;

    /**
     * Gets the value of the cleavageAAs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCleavageAAs() {
        return cleavageAAs;
    }

    /**
     * Sets the value of the cleavageAAs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCleavageAAs(String value) {
        this.cleavageAAs = value;
    }

    /**
     * Gets the value of the misscleavages property.
     * 
     */
    public int getMisscleavages() {
        return misscleavages;
    }

    /**
     * Sets the value of the misscleavages property.
     * 
     */
    public void setMisscleavages(int value) {
        this.misscleavages = value;
    }

    /**
     * Gets the value of the enzymeOffset property.
     * 
     */
    public int getEnzymeOffset() {
        return enzymeOffset;
    }

    /**
     * Sets the value of the enzymeOffset property.
     * 
     */
    public void setEnzymeOffset(int value) {
        this.enzymeOffset = value;
    }

    /**
     * Gets the value of the enzymeNoCutResidues property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnzymeNoCutResidues() {
        return enzymeNoCutResidues;
    }

    /**
     * Sets the value of the enzymeNoCutResidues property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnzymeNoCutResidues(String value) {
        this.enzymeNoCutResidues = value;
    }

    /**
     * Gets the value of the isH2OPlusProtonAdded property.
     * 
     */
    public boolean isIsH2OPlusProtonAdded() {
        return isH2OPlusProtonAdded;
    }

    /**
     * Sets the value of the isH2OPlusProtonAdded property.
     * 
     */
    public void setIsH2OPlusProtonAdded(boolean value) {
        this.isH2OPlusProtonAdded = value;
    }

}
