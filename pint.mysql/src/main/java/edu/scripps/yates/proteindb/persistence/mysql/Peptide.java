package edu.scripps.yates.proteindb.persistence.mysql;

// Generated Feb 24, 2015 2:42:07 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Peptide generated by hbm2java
 */
public class Peptide implements java.io.Serializable {

	private Integer id;
	private Set msRuns = new HashSet(0);
	private String sequence;
	private String fullSequence;
	private Integer numPsms;
	private Set peptideAmounts = new HashSet(0);
	private Set peptideRatioValues = new HashSet(0);
	private Set psms = new HashSet(0);
	private Set conditions = new HashSet(0);
	private Set peptideScores = new HashSet(0);
	private Set proteins = new HashSet(0);
	private Set ptms = new HashSet(0);

	public Peptide() {
	}

	public Peptide(String sequence) {

		this.sequence = sequence;
	}

	public Peptide(Set msRuns, String sequence, String fullSequence, Integer numPsms, Set peptideAmounts,
			Set peptideRatioValues, Set psms, Set conditions, Set peptideScores, Set proteins, Set ptms) {
		this.msRuns = msRuns;
		this.sequence = sequence;
		this.fullSequence = fullSequence;
		setNumPsms(numPsms);
		this.peptideAmounts = peptideAmounts;
		this.peptideRatioValues = peptideRatioValues;
		this.psms = psms;
		this.conditions = conditions;
		this.peptideScores = peptideScores;
		this.proteins = proteins;
		this.ptms = ptms;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set getMsRuns() {
		return msRuns;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public Set getPeptideAmounts() {
		return peptideAmounts;
	}

	public void setPeptideAmounts(Set peptideAmounts) {
		this.peptideAmounts = peptideAmounts;
	}

	public Set getPeptideRatioValues() {
		return peptideRatioValues;
	}

	public void setPeptideRatioValues(Set peptideRatioValues) {
		this.peptideRatioValues = peptideRatioValues;
	}

	public Set getPsms() {
		return psms;
	}

	public void setPsms(Set psms) {
		this.psms = psms;
	}

	public Set getConditions() {
		return conditions;
	}

	public void setConditions(Set conditions) {
		this.conditions = conditions;
	}

	public Set getPeptideScores() {
		return peptideScores;
	}

	public void setPeptideScores(Set peptideScores) {
		this.peptideScores = peptideScores;
	}

	public Set getProteins() {
		return proteins;
	}

	public void setProteins(Set proteins) {
		this.proteins = proteins;
	}

	public Set getPtms() {
		return ptms;
	}

	public void setPtms(Set ptms) {
		this.ptms = ptms;
	}

	public String getFullSequence() {
		return fullSequence;
	}

	public void setFullSequence(String fullSequence) {
		this.fullSequence = fullSequence;
	}

	public Integer getNumPsms() {
		return numPsms;
	}

	public void setNumPsms(Integer numPsms) {
		this.numPsms = numPsms;
	}

	public void setMsRuns(Set msRuns) {
		this.msRuns = msRuns;
	}

}
