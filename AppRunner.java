package com.metlife.cdi.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class AppRunner {
        public static void main(String[] args) throws IOException {
                long startTime = currentTimeMillis();
                AppRunner app = new AppRunner();
                Path source = Paths.get("c:\\IND_Consolidated_Disbursements_Init_WAS61\\source\\");
                Path target = Paths.get("C:\\TestTarget");
                app.run(source, target);
                long endTime = currentTimeMillis();
                long totalTime = endTime - startTime;
                out.println("Program completed in " + totalTime / 1000 + " seconds" + "\n");
                MetricVisitor m = new MetricVisitor();
                Files.walkFileTree(target, m);
                m.getFileCount();

 }

        private void run() throws IOException {
                FileVisitor visitor = new FileVisitor();
                visitor.setTargetDir(target);
                Files.walkFileTree(source, visitor);

        }
}
