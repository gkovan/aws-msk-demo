package com.gk.aws.msk.demo.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonLoader implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) throws IOException {
        // TODO Auto-generated method stub

        String sourceName = name !=null ? name:resource.getResource().getFilename();
        Map<String,Object> readValue = new ObjectMapper().readValue(resource.getInputStream(), Map.class);
        Map<String,Object> namespacedMap = new HashMap();

        for (Entry<String,Object> entry: readValue.entrySet()) {
            namespacedMap.put(sourceName+ "." + entry.getKey(), entry.getValue());
        }
        return new MapPropertySource(sourceName, namespacedMap);
    }
}
