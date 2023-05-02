package com.tacs.bot.service;

import com.tacs.bot.dto.*;

import java.io.IOException;


public interface ApiService {
     Object authenticate(Message message);

     Object register(Message message);

     Object getAllEvent(Message message);

     Object createEvent(Message message);

     Object getEventById(Message message) ;

     Object registerEvent(Message message);

     Object changeEventStatus(Message message);

     Object getCounterReport(Message message);

     Object getOptionsReport(Message message);
}
