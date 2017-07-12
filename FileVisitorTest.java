package com.metlife.cdi.tools;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by kedge on 6/22/2017.
 */
@SuppressWarnings("DefaultFileTemplate")
public class FileVisitorTest {
//    private FileVisitor visit = new FileVisitor();
    private Path fileDir;
    


    @Before
    public void setup(){
        fileDir = Paths.get("c:\\Users\\kedge\\Desktop\\tests");
    }


    @Test
    public void testFileCounter() throws IOException {
        fileDir = Paths.get("c:\\Users\\kedge\\Desktop\\tests\\CDIWeb\\createMe");
        FileVisitor visit = new FileVisitor();

        Files.walkFileTree(fileDir, visit);
        int expected = 2;
        int actual = visit.getFinalFileCount();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void doesItSkipBinFolder() throws IOException {
        fileDir = Paths.get("c:\\Users\\kedge\\Desktop\\tests\\CDIWeb\\bin");
        FileVisitor visit = new FileVisitor();

        Files.walkFileTree(fileDir, visit);
        int actual = visit.getFinalFileCount();
        int expected = 0;
        Assert.assertEquals(expected, actual);
    }
}
