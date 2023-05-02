package com.tacs.bot.validator;

import com.tacs.bot.dto.Message;
import com.tacs.bot.enums.Type;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class TokenValidator extends TypeValidator{

    public void validate(Message message) {
        boolean isAuthService = Type.AUTH_REGISTER.name().equals(message.getType()) || Type.AUTH_AUTHENTICATION.name().equals(message.getType());
        if(!isAuthService && StringUtils.isBlank(message.getToken())){
            throw new RuntimeException("Token is required");
        }
    }
}
