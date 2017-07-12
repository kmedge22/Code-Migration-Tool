package com.metlife.cdi.tools;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * If file is a java test file, the maven structure is src\\test\\java and matches = true
 */
public class JavaTestRule implements Rule {
    private boolean isAMatch;
    private final Path path;

    JavaTestRule(Path path){
        this.path = path;
        this.isAMatch = false;
        checkFileType();
    }

    public Boolean matches() {
        return isAMatch;
    }

    public void checkFileType(){
        String myPath = path.toString().toLowerCase();
        if (myPath.contains("test.java")){
            this.isAMatch = true;
        }
    }

    @Override
    public Path getMavenPath() {
        return Paths.get("src\\test\\java");
    }
}



