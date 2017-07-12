package com.metlife.cdi.tools;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;


class FolderTest {

    @Test
    public void initTest(){
        FolderFinder ft = new FolderFinder(Paths.get("c:\\Users\\CDIWeb\\yay.java"));
        Path expected = Paths.get("CDIWeb");
        Path actual = ft.getCDIFolder();
        Assert.assertEquals(expected,actual);
    }
}
