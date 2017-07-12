package com.metlife.cdi.tools;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class CopyEngineTest {

        private CopyEngine ce;

        //clean object every test
        @Before
        public void setup() throws IOException {
                ce = new CopyEngine(Paths.get(
                        "CDICommonApp/source/com/metlife/ib/cdi/app/cldm/generated/vo/aoc/REQUEST.java"),
                        Paths.get("CDICommonApp"), Paths.get("src/main/java"), Paths.get("C:/testTarget"));
        }

        private Path loadFile(String filePath) {
            Path p = null;

            URL fileUrl = this.getClass().getClassLoader().getResource(filePath);

            if (fileUrl != null) {
                File fileToLoad = new File(fileUrl.getFile());
                p = Paths.get(fileToLoad.getAbsolutePath());
            }

            return p;
        }

        //Comparing expected Path with actual Path
        @Test
        public void QualifiedPathHasBeenReceived() {
                String filePath = "CDICommonApp/source/com/metlife/ib/cdi/app/cldm/generated/vo/aoc/REQUEST.java";
                ce.setOriginalPath(loadFile(filePath));
                Path expected = loadFile(filePath);
                Path actual = ce.getFile();
                assertEquals(expected, actual);
        }

        @Test
        public void QualifiedPathHasBeenReceivedRefactored() {
                String filePath = "CDICommonApp/source/com/metlife/ib/cdi/app/cldm/generated/vo/aoc/REQUEST.java";
                ce.setOriginalPath(loadFile(filePath));
                Path expected = loadFile(filePath);
                Path actual = ce.getFile();
                assertEquals(expected, actual);
        }

        @Test
        public void rootExtHasBeenCreated() {
                Path expected = Paths.get("C:/testTarget/CDICommonApp/src/main/java");
                Path actual = ce.createRootExt(Paths.get("CDICommonApp"), Paths.get("src/main/java"),
                        Paths.get("C:/testTarget"));
                assertEquals(expected, actual);
        }

        //Tests if CommonApp and CommonWeb split correctly
        @Test
        public void CommonAppAndCommonWebHaveBeenSplitRefactoredMETHOD() {
                String filePath = "CDICommonApp/source/com/metlife/ib/cdi/app/cldm/generated/vo/aoc/REQUEST.java";
                ce.setOriginalPath(loadFile(filePath));
                Path expected = Paths.get("com/metlife/ib/cdi/app/cldm/generated/vo/aoc/REQUEST.java");
                Path actual = ce.cdiCommon(Paths.get("source/com/metlife/ib/cdi/app/cldm/generated/vo/aoc/REQUEST.java"));
                assertEquals(expected, actual);
        }

        //Splits CDIWeb
        @Test
        public void CDIWebHasBeenSplitRefactoredMETHOD() throws IOException {
                String filePath = "CDIWeb/WebContent/WEB-INF/SearchClaimService_mapping.xml";
                ce.setOriginalPath(loadFile(filePath));
                Path expected = Paths.get("WEB-INF/SearchClaimService_mapping.xml");
                Path actual = ce.cdiWeb(Paths.get("WebContent/WEB-INF/SearchClaimService_mapping.xml"));
                assertEquals(expected, actual);
        }

        //Splitting other CDIApplications
        @Test
        public void OtherCDIApplicationHaveBeenSplitRefactored() throws IOException {
                String filePath = "CDIClient/appClientModule/com/metlife/ib/cdi/client/common/event/CDIEventProcessor.java";
                ce.setOriginalPath(loadFile(filePath));
                Path expected = Paths.get("appClientModule/com/metlife/ib/cdi/client/common/event/CDIEventProcessor.java");
                Path actual = ce.splitPaths();
                assertEquals(expected, actual);
        }

        //Check to make sure Paths have been merged correctly (CommonApp and CommonWeb)
        @Test
        public void PathsHaveBeenMergedRefactored() {
                String filePath = "CDICommonApp/source/com/metlife/ib/cdi/app/cldm/generated/vo/aoc/REQUEST.java";
                ce.setOriginalPath(loadFile(filePath));
                ce.setRoot(Paths.get("C:/testTarget/CDICommonApp/src/main/java"));
                Path expected = Paths.get("C:/testTarget/CDICommonApp/src/main/java/com/metlife/ib/cdi/app/cldm/generated/vo/aoc/REQUEST.java");
                Path actual = ce.joinPaths(Paths.get("com/metlife/ib/cdi/app/cldm/generated/vo/aoc/REQUEST.java"));
                assertEquals(expected, actual);
        }

        //Checks to make sure a directory is created
        @Test
        public void DirectoryPathHasBeenSet() throws IOException {
                String filePath = "CDICommonApp/source/com/metlife/ib/cdi/app/cldm/generated/vo/aoc/REQUEST.java";
                ce.setOriginalPath(loadFile(filePath));
                ce.setRoot(Paths.get("C:/testTarget/CDICommonApp/src/main/java"));

                Path expected = Paths.get("C:/testTarget/CDICommonApp/src/main/java/com/metlife/ib/cdi/app/cldm/generated/vo/aoc");
                Path actual = ce.dirSet(Paths.get("C:/testTarget/CDICommonApp/src/main/java/com/metlife/ib/cdi/app/cldm/generated/vo/aoc/REQUEST.java"));
                assertEquals(expected, actual);
        }

        //No duplicate directories are made
        @Test
        public void NoDuplicateDirectories() throws IOException {
                String filePath = "CDICommonApp/source/com/metlife/ib/cdi/app/cldm/generated/vo/aoc/REQUEST.java";
                ce.setOriginalPath(loadFile(filePath));
                ce.setRoot(Paths.get("C:/testTarget/CDICommonApp/src/main/java"));

                Path expected = Paths.get("C:/testTarget/CDICommonApp/src/main/java/com/metlife/ib/cdi/app/cldm/generated/vo/aoc");
                Path actual = ce.dirSet(Paths.get("C:/testTarget/CDICommonApp/src/main/java/com/metlife/ib/cdi/app/cldm/generated/vo/aoc/REQUEST.java"));
                assertEquals(expected, actual);
        }

        //No duplicate files are made
        @Test
        public void NoDuplicateFiles() throws IOException {
                String filePath = "CDICommonApp/source/com/metlife/ib/cdi/app/cldm/generated/vo/aoc/REQUEST.java";
                ce.setOriginalPath(loadFile(filePath));
                ce.setRoot(Paths.get("C:/testTarget/CDICommonApp/src/main/java"));

                boolean expected = false;
                boolean actual = ce.copyFilesOver(Paths.get("C:/testTarget/CDICommonApp/src/main/java/com/metlife/ib/cdi/app/cldm/generated/vo/aoc/REQUEST.java"));
                assertEquals(expected, actual);
        }
}
