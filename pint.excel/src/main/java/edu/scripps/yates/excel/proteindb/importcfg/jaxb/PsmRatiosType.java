//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.03.11 at 03:44:17 PM PST 
//


package edu.scripps.yates.excel.proteindb.importcfg.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for psm_ratiosType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="psm_ratiosType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="excel_ratio" type="{}excel_amount_ratioType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="remoteFiles_ratio" type="{}remote_files_ratioType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "psm_ratiosType", propOrder = {
    "excelRatio",
    "remoteFilesRatio"
})
public class PsmRatiosType {

    @XmlElement(name = "excel_ratio")
    protected List<ExcelAmountRatioType> excelRatio;
    @XmlElement(name = "remoteFiles_ratio")
    protected List<RemoteFilesRatioType> remoteFilesRatio;

    /**
     * Gets the value of the excelRatio property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the excelRatio property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExcelRatio().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExcelAmountRatioType }
     * 
     * 
     */
    public List<ExcelAmountRatioType> getExcelRatio() {
        if (excelRatio == null) {
            excelRatio = new ArrayList<ExcelAmountRatioType>();
        }
        return this.excelRatio;
    }

    /**
     * Gets the value of the remoteFilesRatio property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the remoteFilesRatio property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRemoteFilesRatio().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RemoteFilesRatioType }
     * 
     * 
     */
    public List<RemoteFilesRatioType> getRemoteFilesRatio() {
        if (remoteFilesRatio == null) {
            remoteFilesRatio = new ArrayList<RemoteFilesRatioType>();
        }
        return this.remoteFilesRatio;
    }

}
