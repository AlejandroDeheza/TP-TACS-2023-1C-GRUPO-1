package com.tacs.bot.validator;

import com.tacs.bot.dto.Message;
import com.tacs.bot.enums.Type;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class EventsChangeStatusValidator extends TypeValidator{

    public void validate(Message message) {
        boolean isLackRequiredFields = StringUtils.isBlank(message.getEventId()) || StringUtils.isBlank(message.getStatus());
        if(Type.EVENTS_CHANGE_STATUS.name().equals(message.getType()) && isLackRequiredFields){
            throw new RuntimeException("Event Id is required");
        }
    }
}
