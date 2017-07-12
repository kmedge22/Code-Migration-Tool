package com.metlife.cdi.tools;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * If file is a java file and is not a java test file, the maven structure is src\\main\\java and matches = true
 */
public class JavaFileRule implements Rule {
    private boolean isAMatch;
    private final Path path;


    JavaFileRule(Path path){
        this.path = path;
        this.isAMatch = false;
        checkFileType();
    }

    public Boolean matches() { return isAMatch; }

    public void checkFileType(){
        String myPath = path.toString().toLowerCase();
        if (myPath.endsWith(".java")){
            checkIfTestFile(myPath);
        }
    }

    /**
     * Check if java extension is a test file
     * @param myPath path of file
     */
    private void checkIfTestFile(String myPath) {
        if (myPath.contains(".java")) {
            isAMatch = true;
        }
        if (myPath.contains("test.java")){
            isAMatch = false;
        }
    }

    public Path getMavenPath() {
        return Paths.get("src\\main\\java");
    }
}
