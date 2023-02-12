package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

import java.util.Date;


@Entity
@Getter
@Setter
@ToString
@Table(name = "bidlist")
@RequiredArgsConstructor
public class BidList {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bidListId;
    @NotBlank(message = "Account is mandatory")
    @Length(min = 1, max = 30)
    private String account;
    @NotBlank(message = "Type is mandatory")
    @Length(min = 1, max = 30)
    private String type;
    @DecimalMin(value = "0.0", inclusive = false)
    private double bidQuantity;
    private double askQuantity;
    private double bid;
    private double ask;
    @Length(min = 0, max = 125)
    private String benchmark;
    private Date bidListDate;
    @Length(min = 0, max = 125)
    private String commentary;
    @Length(min = 0, max = 125)
    private String security;
    @Length(min = 0, max = 10)
    private String status;
    @Length(min = 0, max = 125)
    private String trader;
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
