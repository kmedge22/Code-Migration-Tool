package com.metlife.cdi.tools;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import static java.lang.System.*;
    /**MODIFIED FROM JAVA API
     * Created by kedge on 6/21/2017.
     */

    class FileVisitor extends SimpleFileVisitor<Path> {
        static int fileCounter = 0;
        static Path targetDir;

        /**
         * Before visiting a directory, will determine if the directory is a bin folder, if so then skip everything
         * under the bin folder
         * @param dir path
         * @param attrs basic
         * @return command to continue
         * @throws IOException default
         */
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            if (dir.toString().toLowerCase().contains("bin")){
                return FileVisitResult.SKIP_SUBTREE;
            }
            return FileVisitResult.CONTINUE;
        }

        /**
         * If visiting a regular file, send to Rule Engine, check the file type, and determine file path
         * Skip all bin directories
         * @param file file from path
         * @param attrs file/directory
         * @return command to continue
         * @throws IOException default
         */
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
             if (attrs.isRegularFile()){
                 RulesEngine rules = new RulesEngine();
                 fileCounter();
                 rules.setPath(file);
            }
            return FileVisitResult.CONTINUE;
        }

        private static void fileCounter(){
            fileCounter++;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            err.println(exc.getMessage());
            return FileVisitResult.CONTINUE;
        }

        /**
         * Get a tally of total number of regular files found in the source tree
         */
        int getFinalFileCount(){
            out.println("Total files in tree: " + fileCounter);
            return fileCounter;
        }

        static void setTargetDir(Path target){
            targetDir = target;}
    }

