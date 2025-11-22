package com.example.taskFlowProgram.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "TaskFlow")
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private boolean completed;
}

