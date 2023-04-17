package com.tacs.backend.service;

import com.tacs.backend.dto.EventReportDto;
import com.tacs.backend.model.Event;
import com.tacs.backend.repository.EventOptionRepository;
import com.tacs.backend.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitorService {
    private final EventRepository eventRepository;
    private final EventOptionRepository eventOptionRepository;

    public EventReportDto getLastEventEntries(int milliseconds) {
        List<Event> events = eventRepository.findLastCreatedEvents(milliseconds);

        EventReportDto report = new EventReportDto();
        report.setCreatedEventCounter(events.size());
        report.setOptionsVotedCounter(0);

        return report;
    }
}
