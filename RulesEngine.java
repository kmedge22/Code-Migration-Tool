package com.metlife.cdi.tools;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import static java.lang.System.out;

class RulesEngine {
    private Path path;
    private Path sendNewPath;
    private Path targetDir;
    private String fileType;
    private String folder;
    private static ArrayList<String> unknowns;

    RulesEngine() {
        unknowns = new ArrayList();
        folder = "";
    }

    RulesEngine(Path sourceDir, Path targetDir){
        this.targetDir = targetDir;
        unknowns = new ArrayList();
        folder = "";
        setPath(sourceDir);
    }

    void evaluate(File file) {
                if (file == null) {
                        throw new IllegalArgumentException("file cannot be null.");
                }
        }

    String getFileType(){return fileType;}

    Path getOriginalPath() {
        return path;
    }

    Path getSendNewPath() {
        return sendNewPath;
    }

    /**
     * Once path is set, automatically send to have file type analyzed, then  automatically identify root CDI Folder
     * @param path path to a select file
     */
    void setPath(Path path) {
        this.path = path;
        checkFileType();
        folder = determineCDIFolder(path.toString()).toString();
    }

    String getFolder() {
        return folder;
    }

    /**
     * Identify file types by matching file extensions
     * Assign maven file structure to sendNewPath based upon extension
     * rules applied from reqs doc
     */
    void checkFileType(){
        String myPath = path.toString().toLowerCase();

        if (myPath.contains(".java")){
            checkIfTestFile(myPath);
        }

        else if ( myPath.contains("pom.xml") || myPath.contains(".iml") || myPath.endsWith(".jsp") ||
                myPath.contains("webcontent") || myPath.endsWith(".html")){
            fileType = "pomIml";
            sendNewPath = Paths.get("src\\main\\webapp\\");
        }

        else if (myPath.contains(".xml") || path.startsWith(Paths.get("CDICommonApp\\source\\CDICommonApp\\"))
                || myPath.contains(".properties")){
            fileType = "xmlProperties";
            sendNewPath = Paths.get("src\\main\\resources\\");
        }

        else if (myPath.contains(".class") || myPath.contains(".project" )
                || myPath.contains(".settings") || myPath.contains(".websettings")){
            fileType = "ignore";
        }

        else {
            fileType = "";
            unknowns.add(path.getFileName().toString());
            //TODO : identify
        }

        if (myPath.contains(".mf")){
            fileType = "MF";
            checkMFFiles(myPath);
        }
        sendToCopyEngine();

    }

    /**
     * Check if java extension is a test file
     * Assign maven file structure to sendNewPath based upon extension
     * @param myPath
     */
    private void checkIfTestFile(String myPath) {
        if (myPath.contains("test.java")) {
            fileType = "javaTest";
            sendNewPath = Paths.get("src\\test\\java\\");
        }
        else{
            fileType = "javaFile";
            sendNewPath = Paths.get("src\\main\\java\\");
        }
    }

    /**
     * Determine which CDI folder the path should be moved to in target directory
     * @param pathAsString file's path passed in as a String
     * @return CDI folder as a Path
     */
    private Path determineCDIFolder(String pathAsString) {
        if (pathAsString.toLowerCase().contains("cdiclient")){
            folder = "CDIClient";
        }
        else if (pathAsString.toLowerCase().contains("cdicommonapp")){
            folder = "CDICommonApp";
        }
        else if (pathAsString.toLowerCase().contains("cdicommonweb")){
            folder = "CDICommonWeb";
        }
        else if (pathAsString.toLowerCase().contains("cdiconfig")){
            folder = "CDIConfig";
            //adding new line
            sendNewPath = Paths.get("src\\main\\resources");
        }
        else if (pathAsString.toLowerCase().contains("cdiejb")){
            folder = "CDIEJB";
        }
        else if (pathAsString.toLowerCase().contains("cdienterprise")){
            folder = "CDIEnterprise";
        }
        else if (pathAsString.toLowerCase().contains("cdiweb")){
            folder = "CDIWeb";
        }
        else{
            System.err.format("Incorrect file path.");
        }
        return Paths.get(folder);

    }

    /**
     * Rules to determine where .MF file extensions go
     * Assign maven file structure based upon rules
     * @param pathAsSting
     */
    private void checkMFFiles(String pathAsSting){
        if (pathAsSting.contains("cdienterprise")){
            sendNewPath = Paths.get("src\\main\\application\\");
        }
        else if (pathAsSting.contains("cdiclient") || (pathAsSting.toLowerCase().contains("cdiweb")) ||
                pathAsSting.contains("cdiejb")){
            sendNewPath = Paths.get("src\\main\\resources");
        }
        else if (pathAsSting.toLowerCase().contains("cdicommonapp") || pathAsSting.toLowerCase().contains("cdicommonweb")){
            fileType = "ignore";
        }

    }

    /**
     * Connect to Copy Engine
     */
    private void sendToCopyEngine() {
        if (fileType != "ignore"){
            CopyEngine ce = new CopyEngine(getOriginalPath(), Paths.get(folder), sendNewPath , targetDir);
            }
    }

    /**
     * TEMPORARY METHOD: tracks all unidentified file types
     */
    void iterateThroughUnknownFileTypes(){
        out.println('\n' + "Unknowns: ");
        for (Object unknown : unknowns) {
            out.println(unknown + ",");

        }
    }


}
