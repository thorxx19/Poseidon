package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@Table(name = "rulename")
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Length(min = 0, max = 125)
    private String name;
    @Length(min = 0, max = 125)
    private String description;
    @Length(min = 0, max = 125)
    private String json;
    @Length(min = 0, max = 512)
    private String template;
    @Length(min = 0, max = 125)
    private String sqlStr;
    @Length(min = 0, max = 125)
    private String sqlPart;
}
