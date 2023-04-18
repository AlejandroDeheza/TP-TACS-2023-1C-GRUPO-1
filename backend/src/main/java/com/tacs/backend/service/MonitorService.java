package com.tacs.backend.service;

import com.tacs.backend.dto.EventOptionReportDto;
import com.tacs.backend.dto.MarketingReportDto;
import com.tacs.backend.model.EventOption;
import com.tacs.backend.repository.EventOptionRepository;
import com.tacs.backend.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonitorService {
    @Value("${monitor.time-range}")
    private int timeRange;
    private final EventRepository eventRepository;
    private final EventOptionRepository eventOptionRepository;

    //private final EventOptionReportMapper EventOptionReportMapper;
    // por ahora no codifico el mapper para EventOptionReportDto hasta que nos den el OK en el formato del reporte

    public MarketingReportDto getMarketingReport() {
        MarketingReportDto report = new MarketingReportDto();
        report.setEventsCount(eventRepository.getLastCreatedEventsCount(timeRange));
        report.setOptionsCount(eventOptionRepository.getLastVotedEventOptionsCount(timeRange));

        return report;
    }

    private Date removeSeconds(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, -calendar.get(Calendar.SECOND));
        calendar.add(Calendar.MILLISECOND, -calendar.get(Calendar.MILLISECOND));
        return calendar.getTime();
    }

    public List<EventOptionReportDto> getLastVotedEventOptions() {
        List<EventOption> eventOptions = eventOptionRepository.getLastVotedEventOptions(timeRange);

        // TODO: es necesario modificar la fecha de la opcion para quitar los segundos y milisegundos y asi poder agrupar y sumar

        Map<Date, Long> votes =
        eventOptions.stream().collect(Collectors.groupingBy(
                EventOption::getDateTime,
                //Collectors.maxBy(Comparator.comparing(EventOption::getUpdateDate))
                Collectors.summingLong(EventOption::getVoteQuantity))
        );

        /*
        Comparator<EventOptionReportDto> optionUpdateDateComparator = Comparator
                .comparing(EventOptionReportDto::getLastUpdateDate);

        List<EventOptionReportDto> l =
                eventOptions.stream().collect(Collectors.groupingBy(
                        EventOption::getDateTime,
                        Collectors.maxBy(Comparator.comparing(EventOption::getUpdateDate)),
                        Collectors.summingLong(EventOption::getVoteQuantity))
                );
        */

        List<EventOptionReportDto> report = new ArrayList<>();

        votes.forEach( (date, quantity) -> {
            report.add(new EventOptionReportDto() {{
                setDateTime(date);
                setVotesQuantity(quantity);
            }});
        });

        return report;
    }
}
