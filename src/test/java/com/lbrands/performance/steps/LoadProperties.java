package com.lbrands.performance.steps;


import com.lbrands.performance.misc.Feature;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import org.picocontainer.annotations.Cache;

import java.nio.file.Paths;
import java.util.List;
public class LoadProperties {

    private static List<Feature> features;

    static {
        ObjectMapper mapper = JsonFactory.create();
        features = (List<Feature>) mapper
                .readValue(Paths.get("src/test/resources/setup.json").toFile(), List.class, Feature.class);
    }


    public LoadProperties(){

    }
    public List<Feature> getFeatures() {

        return features;
    }

    public static   Feature getPage(String page) {
        for (Feature feature : features) {
            if (feature.getApiName().equalsIgnoreCase(page)) {
                return feature;
            }
        }
        return null;
    }

    // This Code Reuires refacter


}
