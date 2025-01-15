package sg.nus.edu.iss.vttp_5a_paf_day25_lecture_consumer.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    
    private int id; 
    private String name;

    @JsonDeserialize(using=LocalDateDeserializer.class)
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate dateOfBirth;
}
