package com.metlife.cdi.tools;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static java.lang.System.out;

class MetricVisitor extends SimpleFileVisitor<Path>{
    private static int fileCount = 0;
    private static int clientCount = 0;
    private static int comAppCount = 0;
    private static int comWebCount = 0;
    private static int configCount = 0;
    private static int ejbCount = 0;
    private static int entCount = 0;
    private static int webCount = 0;
    private static int javaCount = 0;
    private static int testCount = 0;
    private static int mfCount = 0;
    private static int xmlCount = 0;
    private static int propCount = 0;
    private static int pomCount = 0;
    private static int jspCount = 0;
    private static int otherCount = 0;


    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (attrs.isRegularFile()) {
            fileCount++;
        }

        String f =file.toString().toLowerCase();

        //count folders
        if (f.contains("cdiclient")) {
            clientCount++;
        } else if (f.contains("cdicommonapp")) {
            comAppCount++;
        } else if (f.contains("cdicommonweb")) {
            comWebCount++;
        } else if (f.contains("cdiconfig")) {
            configCount++;
        } else if (f.contains("cdiejb")) {
            ejbCount++;
        } else if (f.contains("cdienterprise")) {
            entCount++;
        } else if (f.contains("cdiweb")) {
            webCount++;
        }

        //counts java files and java tests files
        if (f.contains("test.java")) {
            testCount++;
        }else if(f.endsWith(".java")){
            javaCount++;
        }

        //counts pom.xml and .xml
        if (f.contains("pom.xml")) {
            pomCount++;
        }else if (f.endsWith(".xml")) {
            xmlCount++;
        }

        //counts other file types
        if (f.endsWith(".mf")) {
            mfCount++;
        }else if (f.endsWith(".properties")) {
            propCount++;
        }else if (f.endsWith(".iml")) {
            ejbCount++;
        }else if (f.endsWith(".jsp")) {
            jspCount++;
        }

        if(!f.endsWith(".java") && !f.endsWith(".properties") && !f.endsWith(".xml") && !f.endsWith(".mf") &&
                !f.endsWith(".iml") && !f.endsWith(".jsp")){
            out.println(file);
            otherCount++;
        }



        return FileVisitResult.CONTINUE;
    }

    int getFileCount(){

        out.println("\n" + "Files within Folders: " + '\t' + fileCount);

        out.println("CDIClient: " + '\t' + clientCount);
        out.println("CDICommonApp: " + '\t' + comAppCount);
        out.println("CDICommonWeb: " + '\t' + comWebCount);
        out.println("CDIConfig: " + '\t' + configCount);
        out.println("CDIEJB: " + '\t' + ejbCount);
        out.println("CDIEnterprise: " + '\t' + entCount);
        out.println("CDIWeb:" + '\t' + webCount);

        out.println("\n" + '\t' + "Filetypes:");

        out.println(".java: " + '\t' + javaCount);
        out.println("test.java: " + '\t' + testCount);
        out.println(".mf: " + '\t' + mfCount);
        out.println(".xml: " + '\t' + xmlCount);
        out.println(".properties: " + '\t' + propCount);
        out.println("pom.xml: " + '\t' + pomCount);
        int imlCount = 0;
        out.println(".iml: " + '\t' + imlCount);
        out.println(".jsp: " + '\t' + jspCount);

        out.println("Other Files: " + '\t' + otherCount);
        out.println("Skipped Files: " + '\t' + RulesEngine.skippedCounter);
        int total = javaCount + testCount + mfCount + xmlCount + propCount + pomCount + imlCount + jspCount + otherCount + RulesEngine.skippedCounter;
        out.println("Files analyzed from source folder: " + '\t' + FileVisitor.fileCounter);
        out.println("Total files copied to target directory: " + '\t' + total);

        return fileCount;
    }
}
