package com.tacs.bot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketingReportDto {
    @JsonProperty("events_count")
    private long eventsCount;

    @JsonProperty("options_count")
    private long optionsCount;
}
