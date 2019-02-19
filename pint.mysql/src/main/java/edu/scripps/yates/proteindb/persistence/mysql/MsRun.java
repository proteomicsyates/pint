package edu.scripps.yates.proteindb.persistence.mysql;

// Generated Feb 24, 2015 2:42:07 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * MsRun generated by hbm2java
 */
public class MsRun implements java.io.Serializable {

	private Integer id;
	private String runId;
	private String path;
	private Date date;
	private Project project;
	private Set peptides = new HashSet(0);
	private Set proteins = new HashSet(0);
	private Set proteins2 = new HashSet(0);
	private Set psms = new HashSet(0);

	public MsRun() {
	}

	public MsRun(String runId, String path, Project project) {
		this.runId = runId;
		this.path = path;
		setProject(project);
	}

	public MsRun(String runId, String path, Date date, Project project, Set peptides, Set proteins, Set psms) {
		this.runId = runId;
		this.path = path;
		this.date = date;
		setProject(project);
		this.peptides = peptides;
		this.proteins = proteins;
		this.psms = psms;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRunId() {
		return runId;
	}

	public void setRunId(String runId) {
		this.runId = runId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Set getPeptides() {
		return peptides;
	}

	public void setPeptides(Set peptides) {
		this.peptides = peptides;
	}

	public Set getProteins() {
		return proteins;
	}

	public void setProteins(Set proteins) {
		this.proteins = proteins;
	}

	public Set getPsms() {
		return psms;
	}

	public void setPsms(Set psms) {
		this.psms = psms;
	}

	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	protected Set getProteins2() {
		return proteins2;
	}

	protected void setProteins2(Set proteins2) {
		this.proteins2 = proteins2;
	}

}
