package com.tacs.backend.model;


import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Event event;
    @Field("date_time")
    private Date dateTime;

    @Field("vote_users")
    @DBRef
    private List<User> voteUsers;
    @Field("vote_quantity")
    private long voteQuantity;

    @Field("update_time")
    private Date updateDate;

    @Field("event_name")
    private String eventName;

}
