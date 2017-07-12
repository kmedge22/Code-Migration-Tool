package com.metlife.cdi.tools;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static java.lang.System.*;

class AppRunner {

        public static void main(String[] args) throws IOException {
            AppRunner app = new AppRunner();
            Path source = getSource();
            Path target = getTarget();

            long startTime = currentTimeMillis();
            app.run(source, target);
            long endTime = currentTimeMillis();
            long totalTime = endTime - startTime;

            MetricVisitor m = new MetricVisitor();
            Files.walkFileTree(target, m);
            m.getFileCount();
            out.println("\n" + "milli: " + totalTime);
            out.println("Program completed in " + totalTime / 1000 + " seconds" + "\n");
        }

        private static Path getSource(){
            String response;
            Scanner keyboard = new Scanner(System.in);
            Path source;

            //Validation of Source Root Directory
            do{
                System.out.print("Enter the Source Directory: ");
                response = keyboard.nextLine();
            }while(!response.contains(":\\"));

            source = Paths.get(response);

            return source;
        }

        private static Path getTarget(){
            String response;
            Scanner keyboard = new Scanner(System.in);
            Path target;

            //Validation of Target Root Directory
            do{
                System.out.print("Enter the Target Directory: ");
                response = keyboard.nextLine();
            }while(!response.contains(":\\"));

            target = Paths.get(response);

            return target;
        }

        private void run(Path source, Path target) throws IOException {
            //init fileVisitor
            FileVisitor visitor = new FileVisitor();
            FileVisitor.setTargetDir(target);
            Files.walkFileTree(source, visitor);

        }
}
