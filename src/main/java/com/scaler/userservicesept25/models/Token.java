package com.scaler.userservicesept25.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Token extends BaseModel {
    private String tokenValue;
    @ManyToOne
    private User user;
    private Date expiryAt;
}

/*

  1         1
Token ---- User => M:1 : user_id in the tokens table.
  M         1

 */
