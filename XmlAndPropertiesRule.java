package com.metlife.cdi.tools;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * If file is .xml or .properties, put in maven path of src\\main\\resources and matches = true
 */
public class XmlAndPropertiesRule implements Rule {
    private boolean isAMatch;
    private final Path path;

    XmlAndPropertiesRule(Path path){
        this.path = path;
        this.isAMatch = false;
        checkFileType();
    }

    public Boolean matches() {
        return isAMatch;
    }

    public void checkFileType(){
        String myPath = path.toString().toLowerCase();

        if (myPath.contains(".xml") || myPath.contains(".properties")){
            isAMatch = true;
        }
    }

    public Path getMavenPath() {
        return Paths.get("src\\main\\resources");
    }

}

