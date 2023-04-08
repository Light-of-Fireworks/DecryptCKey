package com.ckey.decrypt.utils;

import com.ckey.decrypt.pojo.CKeyToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class LoadToken {

    private static final String SEPARATOR = " ";

    @Autowired
    private Environment environment;

    /**
     * Load the CKeyToken from the property defined by the given key.
     *
     * @param configKey the key to retrieve the property from
     * @return the CKeyToken loaded from the property
     * @throws IllegalArgumentException if the property format is incorrect
     */
    public CKeyToken load(String configKey) {
        // Load the value from the property
        String property = environment.getProperty(configKey);
        if (StringUtils.isEmpty(property)) {
            throw new IllegalArgumentException("Property is null or empty");
        }

        // Split the property into parts
        String[] parts = property.split(SEPARATOR);
        if (parts.length < 5) {
            throw new IllegalArgumentException("Property format is incorrect");
        }

        // Create the CKeyToken from the parts
        CKeyToken cKeyToken = new CKeyToken();
        cKeyToken.setCkey(parts[1]);
        cKeyToken.setToken(parts[4]);
        return cKeyToken;
    }
}
