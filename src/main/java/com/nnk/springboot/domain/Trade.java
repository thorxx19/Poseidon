package com.nnk.springboot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Timestamp;


@Entity
@Table(name = "trade")
@Getter
@Setter
@ToString
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer tradeId;
    @Length(min = 0, max = 30)
    @NotBlank(message = "Account is mandatory")
    private String account;
    @NotBlank(message = "Username is mandatory")
    @Length(min = 0, max = 30)
    private String type;
    private double buyQuantity;
    private double sellQuantity;
    private double buyPrice;
    private double sellPrice;
    private Date tradeDate;
    @Length(min = 0, max = 125)
    private String security;
    @Length(min = 0, max = 10)
    private String status;
    @Length(min = 0, max = 125)
    private String trader;
    @Length(min = 0, max = 125)
    private String benchmark;
    @Length(min = 0, max = 125)
    private String book;
    @Length(min = 0, max = 125)
    private String creationName;
    private Date creationDate;
    @Length(min = 0, max = 125)
    private String revisionName;
    private Date revisionDate;
    @Length(min = 0, max = 125)
    private String dealName;
    @Length(min = 0, max = 125)
    private String dealType;
    @Length(min = 0, max = 125)
    private String sourceListId;
    @Length(min = 0, max = 125)
    private String side;

}
