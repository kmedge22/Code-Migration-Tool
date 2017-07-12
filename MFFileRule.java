package com.metlife.cdi.tools;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Rules to determine where .MF file extensions go
 * Assign maven file structure based upon rules
 * .MF in CDICommonApp and CDICommonWeb should be ignored
 */
public class MFFileRule implements Rule {
    private boolean isAMatch;
    private final Path path;
    private Path mavenPath;

    MFFileRule(Path path){
        this.path = path;
        this.isAMatch = false;
        checkFileType();
    }

    public Boolean matches() {
        return isAMatch;
    }

    public void checkFileType(){
        String myPath = path.toString().toLowerCase();
        if (myPath.contains(".mf") || myPath.contains(".modulemaps")||myPath.contains(".xmi") || myPath.contains(".logfactory")){
            isAMatch = true;
            checkMFFiles(myPath);
        }
    }

    public Path getMavenPath() {
        return mavenPath;
    }

    /**
     * Rules to determine where .MF file extensions go
     * Assign maven file structure based upon rules
     * @param pathAsSting original file path as string
     */
    private void checkMFFiles(String pathAsSting){
        if (pathAsSting.contains("cdienterprise")){
            mavenPath = Paths.get("src\\main\\application");
            isAMatch = true;
        }

        else if (pathAsSting.contains("cdiclient") || (pathAsSting.toLowerCase().contains("cdiweb")) ||
                pathAsSting.contains("cdiejb")){
            mavenPath = Paths.get("src\\main\\resources");
            isAMatch = true;
        }

        else if (pathAsSting.toLowerCase().contains("cdicommonapp") || pathAsSting.toLowerCase().contains("cdicommonweb")){
            isAMatch = false;
        }
    }

}
