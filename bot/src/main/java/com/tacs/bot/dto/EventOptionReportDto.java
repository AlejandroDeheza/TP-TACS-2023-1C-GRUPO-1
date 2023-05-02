package com.tacs.bot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventOptionReportDto {
    @JsonProperty("id")
    private String id = null;
    @JsonProperty("date_time")
    private String dateTime;

    @JsonProperty("last_update_time")
    private String lastUpdateDate;

    @JsonProperty("votes_quantity")
    private long votesQuantity;

    @JsonProperty("event_name")
    private String eventName;
}
