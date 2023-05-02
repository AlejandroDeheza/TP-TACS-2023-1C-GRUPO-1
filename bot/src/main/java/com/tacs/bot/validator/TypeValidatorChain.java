package com.tacs.bot.validator;

import com.tacs.bot.dto.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class TypeValidatorChain {
    private static final List<TypeValidator> VALIDATORS = new ArrayList<>();

    public static void register(TypeValidator validator) {
        VALIDATORS.add(validator);
    }

    public static void validate(Message message){
        VALIDATORS.forEach(v -> v.validate(message));
    }
}
