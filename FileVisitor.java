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
        private static int fileCounter = 0;
        private RulesEngine rules = new RulesEngine();

        /**
         * Something that will take place just before you are about to visit a directory
         * @param dir path
         * @param attrs basic
         * @return command to continue
         * @throws IOException default
         */
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        /**
         * What happens when you find a file
         * If visiting a regular file, send to Rule Engine, check the file type, and determine file path
         * Skip all bin directories
         * @param file file from path
         * @param attrs file/directory
         * @return command to continue
         * @throws IOException default
         */
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (file.toString().toLowerCase().contains("bin")){
                return FileVisitResult.SKIP_SUBTREE;
            }
             else if (attrs.isRegularFile()){
                fileCounter++;
                rules.setPath(file);
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            err.println(exc.getMessage());
            return FileVisitResult.CONTINUE;
        }

        /**
         * What happens after you have visited a file
         * @param dir path
         * @param exc default
         * @return command to continue
         * @throws IOException default
         */
        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        /**
         * Get a tally of total number of regular files found in the source tree
         */
        int getFinalFileCount(){
            out.println("Total files in tree: " + fileCounter);
            return fileCounter;
        }
    }

