package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Moodys Rating is mandatory")
    @Length(min = 1, max = 125)
    private String moodysRating;
    @NotBlank(message = "Sand Rating is mandatory")
    @Length(min = 1, max = 125)
    private String sandPRating;
    @NotBlank(message = "Fitch Rating is mandatory")
    @Length(min = 1, max = 125)
    private String fitchRating;
    @DecimalMin(value = "0.0", inclusive = false)
    private Integer orderNumber;

}
