package com.tacs.bot.validator;

import com.tacs.bot.dto.Message;
import com.tacs.bot.enums.Type;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class EventsCreateValidate extends TypeValidator{

    public void validate(Message message) {
        if(Type.EVENTS_CREATE.name().equals(message.getType()) && StringUtils.isBlank(message.getBody())){
            throw new RuntimeException("Body is required");
        }
    }
}
