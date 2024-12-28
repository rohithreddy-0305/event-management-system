package com.example.eventmanagementsystem.repository;

import java.util.ArrayList;
import java.util.List;

import com.example.eventmanagementsystem.model.*;

public interface SponsorRepository {

    ArrayList<Sponsor> getAllSponsors();

    Sponsor addSponsor(Sponsor sponsor);

    Sponsor getSponsorById(int sponsorId);

    Sponsor updateSponsor(int sponsorId, Sponsor sponsor);

    void deleteSponsor(int sponsorId);

    List<Event> getEventsBySponsorId(int sponsorId);
}