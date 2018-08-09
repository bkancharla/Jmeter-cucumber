package com.lbrands.performance.misc;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.cucumberexpressions.ParameterType;
import io.cucumber.cucumberexpressions.Transformer;

import java.util.Locale;

public class TypeRegistryConfiguration implements TypeRegistryConfigurer {
    @Override
    public Locale locale() {
        return Locale.ENGLISH;
    }

   public void configureTypeRegistry(TypeRegistry typeRegistry) {
       /*   typeRegistry.defineParameterType(new ParameterType<>(
               "Color",           // name
                "red|blue|yellow", // regexp
                Color.class,       // type
                new Transformer<Color>() {
                    @Override
                    public Color transform(String s) throws Throwable {
                        return new Color();
                    }
                }       // transformer function
        ));
        */
    }

}

