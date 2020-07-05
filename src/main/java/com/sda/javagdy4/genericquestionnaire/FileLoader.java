package com.sda.javagdy4.genericquestionnaire;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.javagdy4.genericquestionnaire.exceptions.LoaderException;
import com.sda.javagdy4.genericquestionnaire.model.Loadable;
import lombok.extern.log4j.Log4j;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Log4j
public class FileLoader<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Class<T> tclass;

    public FileLoader(Class<T> tclass) {
        this.tclass = tclass;
    }

    public Map<Long, T> load() {
        Optional<String> stringOptional = Loadable.getLoadableFilePath(tclass);
        if (stringOptional.isPresent()) {
            String path = stringOptional.get();
            try {
                Map<Long, T> map = objectMapper.readValue(new File(path), new TypeReference<Map<Long, T>>() {});
                return map;
            } catch (IOException ioe) {
                log.error("Error while loading json file: " + path + " : ", ioe);
            }
        }
        throw new LoaderException("Unable to load json file.");
    }
}
