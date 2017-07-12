package com.metlife.cdi.tools;

import java.io.IOException;
import java.nio.file.*;

class CopyEngine{
    private Path original;
    private Path rootExt;

    CopyEngine(Path original, Path folder, Path mavenStructure, Path target) throws IOException {
        this.original = original;
        createRootExt(folder, mavenStructure, target);
        Path split = splitPaths();
        Path join = joinPaths(split);
        dirSet(join);
        copyFilesOver(join);
    }

    void setOriginalPath(Path original) {this.original = original;}
    void setRoot(Path rootExt){this.rootExt = rootExt;}

    //Testing
    Path getFile() {
        return original;
    }

    //Joins target, folder, mavenStructure
    Path createRootExt(Path folder, Path mavenStructure, Path target){
        Path temp = target.resolve(folder);
        rootExt = temp.resolve(mavenStructure);
        return rootExt;
    }

    //This grabs the proper subPath for each file
    Path splitPaths(){
        int count1 = original.getNameCount();
        Path app = Paths.get("CDICommonApp");
        Path comWeb = Paths.get("CDICommonWeb");
        Path web = Paths.get("CDIWeb");
        Path path;
        String client ="CDIClient";
        String config = "CDIConfig";
        String ejb = "CDIEJB";
        String ent = "CDIEnterprise";

        for(int i = 0; i < count1; i++){
            if(original.subpath(i, i+1).equals(app) || original.subpath(i, i+1).equals(comWeb)){
                Path holder = original.subpath(i+1, count1);
                path = cdiCommon(holder);
                return path;
            } else if (original.subpath(i, i+1).equals(web)){
                Path holder = original.subpath(i+1, count1);
                path = cdiWeb(holder);
                return path;
            } else if ((original.toString().contains(client) || original.toString().contains(config) ||
                    original.toString().contains(ejb) || original.toString().contains(ent))
                    &&(original.subpath(i, i+1).equals(Paths.get(client))
                    || original.subpath(i, i+1).equals(Paths.get(config))
                    || original.subpath(i, i+1).equals(Paths.get(ejb))
                    || original.subpath(i, i+1).equals(Paths.get(ent)))){
                return original.subpath(i+1, count1);
                }
            }
        return null;
    }

    Path cdiCommon(Path holder){
        Path source = Paths.get("source");
        int count2 = holder.getNameCount();
        for(int j = 0; j < count2; j++){
            if(holder.subpath(j, j+1).equals(source)){
                return holder.subpath(j+1, count2);
            }
        }
        return null;
    }

    Path cdiWeb(Path holder){
        Path wc = Paths.get("WebContent");
        int count2 = holder.getNameCount();
        for(int j = 0; j < count2; j++){
            if(holder.subpath(j, j+1).equals(wc)){
                return holder.subpath(j+1, count2);
            }else{
                return holder;
            }
        }
        return null;
    }


    //Joins twig (from splitPaths)
    Path joinPaths(Path split){
        return rootExt.resolve(split);
    }

    //Creates a directory for files to be copied too
    Path dirSet(Path join) throws IOException {
        int count = join.getNameCount();
        Path dirPath = join.subpath(0, count-1);
        Path root = Paths.get("C:\\");

        dirPath = root.resolve(dirPath);

        if(dirPath.toFile().exists()) {
            dirPath.toFile().delete();
        }
        Files.createDirectories(dirPath);
        return dirPath;
    }

    //Copies files to the set path
     Boolean copyFilesOver(Path join) throws IOException {
        if(join.toFile().exists()){
            return false;
        }else{
            Files.copy(original, join);
            return true;
        }
    }
}
