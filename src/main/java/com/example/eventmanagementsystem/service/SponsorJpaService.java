package com.example.eventmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import com.example.eventmanagementsystem.model.*;
import com.example.eventmanagementsystem.repository.*;

@Service
public class SponsorJpaService implements SponsorRepository {

	@Autowired
	private SponsorJpaRepository sponsorRepo;

	@Autowired
	private EventJpaRepository eventRepo;

	@Override
	public ArrayList<Sponsor> getAllSponsors() {
		List<Sponsor> sponsorsList = sponsorRepo.findAll();
		ArrayList<Sponsor> list = new ArrayList<>(sponsorsList);
		return list;
	}

	@Override
	public Sponsor addSponsor(Sponsor sponsor) {
		List<Integer> eventIds = new ArrayList<>();
		for (Event event : sponsor.getEvents()) {
			eventIds.add(event.getEventId());
		}
		try {
			List<Event> eventsDataList = eventRepo.findAllById(eventIds);
			if (eventIds.size() != eventsDataList.size()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " some events are missing");
			}
			sponsor.setEvents(eventsDataList);

			return sponsorRepo.save(sponsor);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "event doesn't exist");
		}
	}

	@Override
	public Sponsor getSponsorById(int sponsorId) {
		try {
			Sponsor sponsor = sponsorRepo.findById(sponsorId).get();
			return sponsor;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Sponsor updateSponsor(int sponsorId, Sponsor sponsor) {
		try {
			Sponsor existingSponsor = sponsorRepo.findById(sponsorId).get();
			if (sponsor.getSponsorName() != null) {
				existingSponsor.setSponsorName(sponsor.getSponsorName());
			}
			if (sponsor.getIndustry() != null) {
				existingSponsor.setIndustry(sponsor.getIndustry());
			}
			if (sponsor.getEvents().size() != 0) {
				List<Integer> eventIdsList = new ArrayList<>();
				for (Event event : sponsor.getEvents()) {
					eventIdsList.add(event.getEventId());
				}
				List<Event> eventDataList = eventRepo.findAllById(eventIdsList);
				if (eventDataList.size() != eventIdsList.size()) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "some events are not found");
				}
				sponsor.setEvents(eventDataList);
			}
			return sponsorRepo.save(existingSponsor);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "sponsor not found");
		}

	}

	@Override
	public void deleteSponsor(int sponsorId) {
		try {
			Sponsor sponsor = sponsorRepo.findById(sponsorId).get();
			List<Event> events = sponsor.getEvents();
			for (Event event : events) {
				event.getSponsors().remove(sponsor);
			}
			eventRepo.saveAll(events);
			sponsorRepo.deleteById(sponsorId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, " sponsor not found");
		}
		throw new ResponseStatusException(HttpStatus.NO_CONTENT, " sponsor deleted successfully");

	}

	@Override
	public List<Event> getEventsBySponsorId(int sponsorId) {
		try {
			Sponsor sponsor = sponsorRepo.findById(sponsorId).get();
			return sponsor.getEvents();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sponsor not found");
		}
	}

}