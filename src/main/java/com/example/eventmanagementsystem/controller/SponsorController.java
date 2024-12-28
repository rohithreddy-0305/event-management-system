package com.example.eventmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import com.example.eventmanagementsystem.model.Event;
import com.example.eventmanagementsystem.model.Sponsor;
import com.example.eventmanagementsystem.service.SponsorJpaService;

@RestController
public class SponsorController {

    @Autowired
    private SponsorJpaService sponsorService;

    // API - 7
    @GetMapping("/events/sponsors")
    public ArrayList<Sponsor> getSponsors() {
        return sponsorService.getAllSponsors();
    }

    // API - 8
    @PostMapping("/events/sponsors")
    public Sponsor addSponsor(@RequestBody Sponsor sponsor) {
        return sponsorService.addSponsor(sponsor);
    }

    // API - 9
    @GetMapping("/events/sponsors/{sponsorId}")
    public Sponsor getSponsor(@PathVariable("sponsorId") int sponsorId) {
        return sponsorService.getSponsorById(sponsorId);
    }

    // API - 10
    @PutMapping("/events/sponsors/{sponsorId}")
    public Sponsor updateSponsor(@PathVariable("sponsorId") int sponsorId, @RequestBody Sponsor sponsor) {
        return sponsorService.updateSponsor(sponsorId, sponsor);
    }

    // API - 11
    @DeleteMapping("/events/sponsors/{sponsorId}")
    public void deleteSponsor(@PathVariable("sponsorId") int sponsorId) {
        sponsorService.deleteSponsor(sponsorId);
    }

    // API - 12
    @GetMapping("/sponsors/{sponsorId}/events")
    public List<Event> getEventsBySponsorId(@PathVariable("sponsorId") int sponsorId) {
        return sponsorService.getEventsBySponsorId(sponsorId);
    }
}