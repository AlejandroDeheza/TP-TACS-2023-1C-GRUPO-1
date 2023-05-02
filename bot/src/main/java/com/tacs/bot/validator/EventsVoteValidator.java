package com.tacs.bot.validator;

import com.tacs.bot.dto.Message;
import com.tacs.bot.enums.Type;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class EventsVoteValidator extends TypeValidator{

    public void validate(Message message) {
        boolean isLackRequiredFields = StringUtils.isBlank(message.getEventId()) || StringUtils.isBlank(message.getOptionId());
        if(Type.EVENTS_VOTE.name().equals(message.getType()) && isLackRequiredFields){
            throw new RuntimeException("Event Id and Option Id are required");
        }
    }

}
