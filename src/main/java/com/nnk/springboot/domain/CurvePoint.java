package com.nnk.springboot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Date;
import java.sql.Timestamp;


@Entity
@Getter
@Setter
@ToString
@Table(name = "curvepoint")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private Integer curveId;
    private Date asOfDate;
    @DecimalMin(value = "0.0", inclusive = false)
    private double term;
    @DecimalMin(value = "0.0", inclusive = false)
    private double value;
    private Date creationDate;
}
