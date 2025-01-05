package com.example.eventmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import com.example.eventmanagementsystem.model.*;
import com.example.eventmanagementsystem.repository.*;

@Service
public class EventJpaService implements EventRepository {

	@Autowired
	private EventJpaRepository eventRepo;

	@Autowired
	private SponsorJpaRepository sponsorRepo;

	@Override
	public ArrayList<Event> getAllEvents() {
		List<Event> eventsList = eventRepo.findAll();
		ArrayList<Event> events = new ArrayList<>(eventsList);
		return events;
	}

	@Override
	public Event addEvent(Event event) {
		// getting the sponsorInfo
		List<Integer> sponsorIds = new ArrayList<>();
		for (Sponsor sponsor : event.getSponsors()) {
			sponsorIds.add(sponsor.getSponsorId());
		}
		try {
			List<Sponsor> sponsorsDataList = sponsorRepo.findAllById(sponsorIds);
			if (sponsorsDataList.size() != sponsorIds.size()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incomplete Sponsors Data");
			}
			event.setSponsors(sponsorsDataList);
			return eventRepo.save(event);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "sponsor doesn't exist");
		}
	}

	@Override
	public Event getEventById(int eventId) {
		try {
			Event event = eventRepo.findById(eventId).get();
			return event;
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Event updateEvent(int eventId, Event event) {
		try {
			Event existingEvent = eventRepo.findById(eventId).get();
			if (event.getEventName() != null) {
				existingEvent.setEventName(event.getEventName());
			}
			if (event.getDate() != null) {
				existingEvent.setDate(event.getDate());
			}
			if (event.getSponsors().size() != 0) {
				List<Integer> sponsorIds = new ArrayList<>();
				for (Sponsor sponsor : event.getSponsors()) {
					sponsorIds.add(sponsor.getSponsorId());
				}
				List<Sponsor> sponsorsDataList = sponsorRepo.findAllById(sponsorIds);
				if (sponsorsDataList.size() != sponsorIds.size()) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incomplete Sponsors Data");
				}
				event.setSponsors(sponsorsDataList);
			}

			return eventRepo.save(existingEvent);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "event not found");
		}
	}

	@Override
	public void deleteEvent(int eventId) {
		try {
			Event event = eventRepo.findById(eventId).get();
			List<Sponsor> sponsorsList = event.getSponsors();
			for (Sponsor sponsor : sponsorsList) {
				sponsor.getEvents().remove(event);
			}
			sponsorRepo.saveAll(sponsorsList);
			eventRepo.deleteById(eventId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "event is not present in DB");
		}
		throw new ResponseStatusException(HttpStatus.NO_CONTENT, "event deleted successfully");
	}

	@Override
	public List<Sponsor> getSponsorsOfEventByEventId(int eventId) {
		try {
			Event event = eventRepo.findById(eventId).get();
			return event.getSponsors();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "event not found");
		}
	}

}