package com.example.eventmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sponsor")
public class Sponsor {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sponsorId;
	@Column(name = "name")
	private String sponsorName;
	@Column(name = "industry")
	private String industry;
	@ManyToMany
	@JoinTable(name = "event_sponsor", joinColumns = @JoinColumn(name = "sponsorid"), inverseJoinColumns = @JoinColumn(name = "eventid"))
	@JsonIgnoreProperties("sponsors")
	private List<Event> events;

	public Sponsor() {
	}

	public Sponsor(int sponsorId, String sponsorName, String industry, List<Event> events) {
		this.sponsorId = sponsorId;
		this.sponsorName = sponsorName;
		this.industry = industry;
		this.events = events;
	}

	public int getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(int sponsorId) {
		this.sponsorId = sponsorId;
	}

	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

}