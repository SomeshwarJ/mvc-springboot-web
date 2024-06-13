package com.example.web.service.impl;

import com.example.web.dto.ClubDto;
import com.example.web.dto.EventDto;
import com.example.web.models.Club;
import com.example.web.models.Event;
import com.example.web.repository.ClubRepository;
import com.example.web.repository.EventRepository;
import com.example.web.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.web.mapper.EventMapper.mapToEvent;
import static com.example.web.mapper.EventMapper.mapToEventDto;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;
    private ClubRepository clubRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, ClubRepository clubRepository) {
        this.eventRepository = eventRepository;
        this.clubRepository = clubRepository;
    }

    @Override
    public void createEvent(Long clubId, EventDto eventDto) {
        Club club = clubRepository.findById(clubId).get();
        Event event = mapToEvent(eventDto);
        event.setClub(club);
        eventRepository.save(event);
    }

    @Override
    public List<EventDto> findByClubId(Long clubId) {
        List<Event> events = eventRepository.findByClubId(clubId);
        return events.stream().map((event -> {
            return mapToEventDto(event);
        })).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> findAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(event -> mapToEventDto(event)).collect(Collectors.toList());
    }

    @Override
    public EventDto findByEventId(Long eventId) {
        Event event = eventRepository.findById(eventId).get();
        return mapToEventDto(event);
    }


}
