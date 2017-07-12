package com.metlife.cdi.tools;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static java.lang.System.out;

class RulesEngine {
    private final ArrayList<Rule> ruleArray = new ArrayList<>();
    private Path path;
    private Path mavenPath;
    private Path targetDir;
    private Path folder;
    private static ArrayList<String> unknowns;
    static int skippedCounter;

    //called from FileVisitor
    RulesEngine() {}

    //called for testing
    RulesEngine(Path sourceDir, Path targetDir) throws IOException {
        this.targetDir = targetDir;
        this.path = sourceDir;
        setUp();
    }

    void setPath(Path path) throws IOException {
        this.path = path;
        targetDir = FileVisitor.targetDir;
        setUp();
    }

    /**
     * Creates list to store unknown file types, connect to checkRules to find a rule match.
     * Used to call for original and overloaded constructors
     */
    private void setUp() throws IOException {
        setUnknownsList();
        checkRules();
    }

    private static void setUnknownsList(){
        unknowns = new ArrayList<>();
    }

    void setTarget(Path target){this.targetDir = target;}

    /**
     * Identify and set folder type
     * @return folder as Path
     */
    Path getFolder() {
        FolderFinder finder = new FolderFinder(path);
        this.folder = finder.getCDIFolder();
        return this.folder;
    }

    /**
     * List of Rule(s) to determine where to put each file (a file filter)
     */
    private void loadRuleArray() {
        ruleArray.add(new JavaFileRule(path));
        ruleArray.add(new JavaTestRule(path));
        ruleArray.add(new XmlAndPropertiesRule(path));
        ruleArray.add(new MFFileRule(path));
        ruleArray.add(new WebContentAndWebAppRule(path));
    }

    /**
     * If the file type is not on the list of files to ignore, iterate through the array of Rule(s) to find
     * one that matches. If the file path has a CDIConfig folder, or a WebContent folder, set appropriate mvc path.
     * Set maven path to the path that coordinates with the matching Rule. If no rule matches,
     * put the file in the list of unknowns. Connect to CopyEngine
     * @return maven path
     */
    Path checkRules() throws IOException {
        if (!shouldSkipTheseFiles()){
            getFolder();

            if (folder.equals(Paths.get("CDIConfig"))){
                this.mavenPath = Paths.get("src\\main\\resources");
                sendToCopyEngine();
                return Paths.get("src\\main\\resources");
            }
            else if (path.toString().toLowerCase().contains("webcontent")){
                this.mavenPath = Paths.get("src\\main\\webapp");
                sendToCopyEngine();
                return this.mavenPath;
            }
            else{
                loadRuleArray();
                for (Rule aRuleArray : ruleArray) {
                    if (aRuleArray.matches()) {
                        mavenPath = aRuleArray.getMavenPath();
                        sendToCopyEngine();
                        return mavenPath;
                    }
                }
            }
        }
        forSkippedFiles(path);
        return null;
    }

    private static void forSkippedFiles(Path path) {
        unknowns.add(path.getFileName().toString());
        skippedCounter++;
        out.println("unidentified or skipped file type: " + path.getFileName() + '\t' + '\n' + path);
    }

    /**
     * List of file extensions that are to be ignored upon encountering.
     * @return boolean
     */
    private boolean shouldSkipTheseFiles() {
        String myPath = path.toString().toLowerCase();
        return (myPath.contains(".class") || myPath.contains(".project")
                || myPath.contains(".settings") || myPath.contains(".websettings"))
                || myPath.contains(".jar") //|| myPath.contains(".gph")
                || (myPath.contains(".mf") && myPath.contains("cdicommonapp"))
                || (myPath.contains(".mf") && myPath.contains("cdicommonweb"));
    }

    /**
     * Connect to Copy Engine
     */
    private void sendToCopyEngine() throws IOException {
        new CopyEngine(this.path, this.folder, mavenPath, targetDir);
        }
    
    public static List getUnknowns() {
        for (Object unknown : unknowns) out.println(unknown);
        return unknowns;
    }

}
