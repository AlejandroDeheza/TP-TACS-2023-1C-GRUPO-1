package com.tacs.backend.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("event_options")
public class EventOption {
    @Id
    private String id;
    @DBRef
    private Event event;
    @Field("date_time")
    private Date dateTime;
    @DBRef
    @Field("vote_users")
    private List<User> voteUsers = new ArrayList<>();
    @Field("vote_quantity")
    private long voteQuantity;

}
