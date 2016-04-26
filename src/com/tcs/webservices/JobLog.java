package com.tcs.webservices;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "data")
public class JobLog implements Serializable {
	
	@Id
    private long id;
	
	@Column(name = "runid")
	private long runId;
	
	@Column(name = "entity")
	private String ent;
	
	@Column(name = "run_mode")
	private String runMode;
	
	@Column(name = "ExtractStart")
	private String extStart;
	
	@Column(name = "ExtractEnd")
	private String extEnd;
	
	@Column(name = "S3LoadStart")
	private String s3Start;
	
	@Column(name = "S3LoadEnd")
	private String s3End;
	
	@Column(name = "RedShiftLoadStart")
	private String redShiftStart;
	
	@Column(name = "RedShiftLoadEnd")
	private String redShiftEnd;
	
	@Column(name = "job_status")
	private String jobStatus;
	
	@Column(name = "subsidiary_id")
	private int subsidiaryId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRunId() {
		return runId;
	}

	public void setRunId(long runId) {
		this.runId = runId;
	}

	public String getEnt() {
		return ent;
	}

	public void setEnt(String ent) {
		this.ent = ent;
	}

	public String getRunMode() {
		return runMode;
	}

	public void setRunMode(String runMode) {
		this.runMode = runMode;
	}

	public String getExtStart() {
		return extStart;
	}

	public void setExtStart(String extStart) {
		this.extStart = extStart;
	}

	public String getExtEnd() {
		return extEnd;
	}

	public void setExtEnd(String extEnd) {
		this.extEnd = extEnd;
	}

	public String getS3Start() {
		return s3Start;
	}

	public void setS3Start(String s3Start) {
		this.s3Start = s3Start;
	}

	public String getS3End() {
		return s3End;
	}

	public void setS3End(String s3End) {
		this.s3End = s3End;
	}

	public String getRedShiftStart() {
		return redShiftStart;
	}

	public void setRedShiftStart(String redShiftStart) {
		this.redShiftStart = redShiftStart;
	}

	public String getRedShiftEnd() {
		return redShiftEnd;
	}

	public void setRedShiftEnd(String redShiftEnd) {
		this.redShiftEnd = redShiftEnd;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public int getSubsidiaryId() {
		return subsidiaryId;
	}

	public void setSubsidiaryId(int subsidiaryId) {
		this.subsidiaryId = subsidiaryId;
	}
	

	  
}
