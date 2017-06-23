package com.metlife.cdi.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class AppRunner {
        public static void main(String[] args) throws IOException {
                AppRunner app = new AppRunner();

                app.run();

        }

        private void run() throws IOException {
            RulesEngine rules = new RulesEngine(Paths.get("c:\\IND_Consolidated_Disbursements_Init_WAS61\\source"),
                    Paths.get("c:\\Users\\kedge\\Desktop\\tests"));

            //init fileVisitor
            Path fileDir = rules.getOriginalPath();
            FileVisitor visitor = new FileVisitor();
            Files.walkFileTree(fileDir, visitor);
            rules.iterateThroughUnknownFileTypes();
            visitor.getFinalFileCount();
        }
}
