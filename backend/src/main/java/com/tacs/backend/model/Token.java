package com.tacs.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("tokens")
public class Token {
    @Id
    private String id;
    private String token;
    @Field("token_type")
    private TokenType tokenType;
    private boolean revoked;
    private boolean expired;
    @DBRef
    private User user;
}
