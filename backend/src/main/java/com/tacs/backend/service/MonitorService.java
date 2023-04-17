package com.tacs.backend.service;

import com.tacs.backend.dto.EventOptionDto;
import com.tacs.backend.dto.EventReportDto;
import com.tacs.backend.mapper.EventOptionMapper;
import com.tacs.backend.model.Event;
import com.tacs.backend.model.EventOption;
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
    private final EventOptionMapper eventOptionMapper;

    public EventReportDto getLastEventEntries() {
        EventReportDto report = new EventReportDto();
        report.setCreatedEventCounter(eventRepository.getLastCreatedEventsCount());
        report.setOptionsVotedCounter(0);

        return report;
    }

    public List<EventOptionDto> getLastEventOptions() {
        List<EventOption> eventOptions = eventOptionRepository.getLastVotedEventOptions();

        return eventOptionMapper.entityListToDtoList(eventOptions);
    }
}
