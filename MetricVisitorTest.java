package com.metlife.cdi.tools;

import org.junit.*;
import java.io.IOException;
import java.nio.file.*;

public class MetricVisitorTest {
    private Path targetDir;

    @Before
    public void setup(){
        targetDir = Paths.get("C:\\testTarget");
    }

    @Test
    public void testFileCounter() throws IOException {
        targetDir = Paths.get("C:\\testTarget");
        MetricVisitor m = new MetricVisitor();

        Files.walkFileTree(targetDir, m);
        int expected = 6316;
        int actual = m.getFileCount();
        Assert.assertEquals(expected, actual);
    }
}