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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Length(min = 1, max = 125)
    @NotBlank(message = "Name Rating is mandatory")
    private String name;
    @Length(min = 1, max = 125)
    @NotBlank(message = "Description Rating is mandatory")
    private String description;
    @Length(min = 1, max = 125)
    @NotBlank(message = "Json Rating is mandatory")
    private String json;
    @Length(min = 1, max = 512)
    @NotBlank(message = "Template Rating is mandatory")
    private String template;
    @Length(min = 1, max = 125)
    @NotBlank(message = "Sql Rating is mandatory")
    private String sqlStr;
    @Length(min = 1, max = 125)
    @NotBlank(message = "SqlPart Rating is mandatory")
    private String sqlPart;
}
