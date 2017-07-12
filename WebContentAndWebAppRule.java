package com.metlife.cdi.tools;

import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * if file is a pom file, .iml, .jsp, or .html, assign the maven path
 * src\\main\\webapp and matches = true
 */
public class WebContentAndWebAppRule implements Rule {
    private boolean isAMatch;
    private final Path path;

    WebContentAndWebAppRule(Path path){
        this.path = path;
        this.isAMatch = false;
        checkFileType();
    }

    public Boolean matches() {
        return isAMatch;
    }

    public void checkFileType(){
        String myPath = path.toString().toLowerCase();
        if ( myPath.endsWith("pom.xml") || myPath.endsWith(".iml") || myPath.endsWith(".jsp") || myPath.endsWith(".gph")
               || myPath.endsWith(".html")){
            isAMatch = true;
        }
    }

    public Path getMavenPath() {
        return Paths.get("src\\main\\webapp");
    }

}
