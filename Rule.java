package com.metlife.cdi.tools;
import java.nio.file.Path;

interface Rule {

    Boolean matches();

    void checkFileType();

    Path getMavenPath();

}
