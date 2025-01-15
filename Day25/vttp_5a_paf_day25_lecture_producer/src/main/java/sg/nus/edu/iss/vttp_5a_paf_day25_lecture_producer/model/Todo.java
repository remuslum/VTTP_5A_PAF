package sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    
    private int id;
    private String task;
}
