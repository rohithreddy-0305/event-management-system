package com.example.eventmanagementsystem.repository;

import java.util.ArrayList;
import java.util.List;

import com.example.eventmanagementsystem.model.*;

public interface EventRepository {

    ArrayList<Event> getAllEvents();

    Event addEvent(Event event);

    Event getEventById(int eventId);

    Event updateEvent(int eventId, Event event);

    void deleteEvent(int eventId);

    List<Sponsor> getSponsorsOfEventByEventId(int eventId);
}