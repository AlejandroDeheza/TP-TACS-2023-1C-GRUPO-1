package com.tacs.bot.validator;

import com.tacs.bot.dto.Message;
import jakarta.annotation.PostConstruct;

public abstract class TypeValidator {
    public abstract void validate(Message message);
    @PostConstruct
    protected void init(){
        TypeValidatorChain.register(this);
    }
}
