package com.sda.javagdy4.genericquestionnaire.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Questionnaire {
    private List<Answer> answers;
}
