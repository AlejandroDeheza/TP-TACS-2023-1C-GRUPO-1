package com.tacs.bot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String type;
    private String token;
    private String eventId;
    private String optionId;
    private String status;
    private String body;
}
