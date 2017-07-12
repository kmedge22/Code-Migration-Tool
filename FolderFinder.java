package com.metlife.cdi.tools;

import java.nio.file.Path;
import java.nio.file.Paths;
import static java.lang.System.err;
/**
 * This class determines which folder will be child to the target directory and parent to the mavenPath.
 */

class FolderFinder {
    private Path folder;


    FolderFinder(Path path) {
        identifyFolder(path);
    }

    Path getCDIFolder() {
        return this.folder;
    }

    private void setFolder(Path folder) {
        this.folder = folder;
    }

    /**
     *Checks the directories only in your file path for a string match of specific folder names
     * @param path file path
     */
    private void identifyFolder(Path path){
        String myPath = path.subpath(0,path.getNameCount()-1).toString().toLowerCase();
        if (myPath.toLowerCase().contains("cdiclient")){
            setFolder(Paths.get("CDIClient"));
        }
        else if (myPath.contains("cdicommonapp")){
            setFolder(Paths.get("CDICommonApp"));
        }
        else if (myPath.contains("cdicommonweb")){
            setFolder(Paths.get("CDICommonWeb"));
        }
        else if (myPath.contains("cdiweb")){
            setFolder(Paths.get("CDIWeb"));
        }
        else if (myPath.contains("cdiejb")){
            setFolder(Paths.get("CDIEJB"));
        }
        else if (myPath.contains("cdienterprise")){
            setFolder(Paths.get("CDIEnterprise"));
        }
        else if (myPath.contains("cdiconfig")){
            setFolder(Paths.get("CDIConfig"));
        }
        else{
            err.print("Incorrect file path.");
        }
    }
}
