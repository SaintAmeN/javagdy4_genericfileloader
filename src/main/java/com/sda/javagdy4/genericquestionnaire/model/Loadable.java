package com.sda.javagdy4.genericquestionnaire.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Loadable {
    private final static Map<Class<?>, String> MAPPED_FILES = new HashMap<>();

    static {
        MAPPED_FILES.put(Question.class, "pytania.json");
        MAPPED_FILES.put(Questionnaire.class, "odpowiedzi.json");
    }

    public static Optional<String> getLoadableFilePath(Class<?> tclass){
        return Optional.ofNullable(MAPPED_FILES.get(tclass));
    }

}
