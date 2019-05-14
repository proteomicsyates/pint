package edu.scripps.yates.proteindb.persistence.mysql;

// Generated Feb 24, 2015 2:42:07 PM by Hibernate Tools 3.4.0.CR1

/**
 * PsmScore generated by hbm2java
 */
public class PsmScore implements java.io.Serializable {

	private Integer id;
	private String confidenceScoreType;
	private Psm psm;
	private String name;
	private double value;

	public PsmScore() {
	}

	public PsmScore(String confidenceScoreType, Psm psm, String name, double value) {
		this.confidenceScoreType = confidenceScoreType;
		this.psm = psm;
		this.name = name;
		this.value = value;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConfidenceScoreType() {
		return this.confidenceScoreType;
	}

	public void setConfidenceScoreType(String confidenceScoreType) {
		this.confidenceScoreType = confidenceScoreType;
	}

	public Psm getPsm() {
		return this.psm;
	}

	public void setPsm(Psm psm) {
		this.psm = psm;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}
