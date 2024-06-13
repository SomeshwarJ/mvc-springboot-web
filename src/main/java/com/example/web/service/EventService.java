package com.example.web.service;

import com.example.web.dto.EventDto;

import java.util.List;

public interface EventService {
    void createEvent(Long clubId, EventDto eventDto);
    List<EventDto> findByClubId(Long clubId);
    List<EventDto> findAllEvents();

    EventDto findByEventId(Long eventId);
}
