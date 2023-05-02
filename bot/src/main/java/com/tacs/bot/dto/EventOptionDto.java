package com.tacs.bot.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class EventOptionDto {
    @JsonProperty("id")
    private String id = null;
    @JsonProperty("date_time")
    private String dateTime;

    @JsonProperty("vote_quantity")
    private long voteQuantity;

    @JsonProperty("vote_users")
    private Set<UserDto> voteUsers;

    @JsonProperty("update_time")
    private String updateDate;

    @JsonProperty("event_name")
    private String eventName;

}
