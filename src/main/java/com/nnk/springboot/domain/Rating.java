package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@Table(name = "rating")
public class Rating {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Length(min = 0, max = 125)
    private String moodysRating;
    @Length(min = 0, max = 125)
    private String sandPRating;
    @Length(min = 0, max = 125)
    private String fitchRating;
    private Integer orderNumber;

}
