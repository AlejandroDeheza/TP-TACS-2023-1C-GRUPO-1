package com.tacs.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tacs.backend.model.Event;
import com.tacs.backend.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventOptionDto {
    @JsonProperty("id")
    @Schema(description = "Event option id", hidden = true)
    private String id = null;
    @JsonProperty("date_time")
    @NotBlank(message = "Date time can not be blank")
    @Schema(description = "Event option date time")
    private Date dateTime;

    @JsonProperty("vote_quantity")
    @Schema(description = "Event vote quantity", hidden = true)
    private long voteQuantity;

    @JsonProperty("vote_users")
    @Schema(description = "Event option vote users", hidden = true)
    private List<UserDto> voteUsers;

    @JsonProperty("update_time")
    @Schema(description = "Event option update time", hidden = true)
    private Date updateDate;

    @JsonProperty("event_name")
    private String eventName;

}
