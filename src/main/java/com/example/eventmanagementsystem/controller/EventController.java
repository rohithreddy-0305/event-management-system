package com.example.eventmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import com.example.eventmanagementsystem.model.Event;
import com.example.eventmanagementsystem.model.Sponsor;
import com.example.eventmanagementsystem.service.EventJpaService;

@RestController
public class EventController {

    @Autowired
    private EventJpaService eventService;

    // API - 1
    @GetMapping("/events")
    public ArrayList<Event> getEvents() {
        return eventService.getAllEvents();
    }

    // API - 2
    @PostMapping("/events")
    public Event addEvent(@RequestBody Event event) {
        return eventService.addEvent(event);
    }

    // API - 3
    @GetMapping("/events/{eventId}")
    public Event getEventByEventId(@PathVariable("eventId") int eventId) {
        return eventService.getEventById(eventId);
    }

    // API - 4
    @PutMapping("/events/{eventId}")
    public Event updateEvent(@PathVariable("eventId") int eventId, @RequestBody Event event) {
        return eventService.updateEvent(eventId, event);
    }

    // API - 5
    @DeleteMapping("/events/{eventId}")
    public void deleteEvent(@PathVariable("eventId") int eventId) {
        eventService.deleteEvent(eventId);
    }

    // API - 6
    @GetMapping("/events/{eventId}/sponsors")
    public List<Sponsor> getAllSponsorsOfEvent(@PathVariable("eventId") int eventId) {
        return eventService.getSponsorsOfEventByEventId(eventId);
    }
}