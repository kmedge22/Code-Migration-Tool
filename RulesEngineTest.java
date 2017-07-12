package com.metlife.cdi.tools;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Path;
import static org.junit.Assert.assertEquals;
public class RulesEngineTest{

    private Path loadFile(String filePath) {
        Path p = null;

        URL fileUrl = this.getClass().getClassLoader().getResource(filePath);

        if (fileUrl != null) {
            File fileToLoad = new File(fileUrl.getFile());
            p = Paths.get(fileToLoad.getAbsolutePath());
        }

        return p;
    }

    @Before
    public void setUp(){
        RulesEngine rules = new RulesEngine();
        rules.setTarget(Paths.get("c:\\testTarget"));
    }

    @Test
    public void checkJavaFile(){
        String path = "CDICommonApp\\source\\com\\metlife\\ib\\cdi\\app\\cldm\\generated\\vo\\aoc\\REQUEST.java";
        JavaFileRule rule = new JavaFileRule(loadFile(path));
        boolean expected = true;
        boolean actual = rule.matches();
        Assert.assertEquals(true, actual);
    }

    @Test
    public void trickJavaFileWithJavaTest(){
        String path = "CDICommonApp/source/com/metlife/ib/cdi/app/cldm/generated/vo/aoc/thisIsATest.java";
        JavaFileRule rule = new JavaFileRule(loadFile(path));
        boolean actual = rule.matches();
        Assert.assertEquals(false, actual);
    }

    @Test
    public void checkJavaTest(){
        String path = "CDICommonApp/source/com/metlife/ib/cdi/app/cldm/generated/vo/aoc/thisIsATest.java";
        JavaTestRule rule = new JavaTestRule(loadFile(path));
        boolean expected = true;
        boolean actual = rule.matches();
        Assert.assertEquals(true, actual);
    }

    @Test
    public void checkXmlAndPropertiesRule(){
        Path path = Paths.get("c:\\Users\\kedge\\Desktop\\tests\\nameTest.xml");
        XmlAndPropertiesRule rule = new XmlAndPropertiesRule(path);
        boolean actual = rule.matches();
        Assert.assertEquals(true, actual);
    }

    @Test
    public void checkXmlAndPropertiesRule2(){
        Path path = Paths.get("c:\\Users\\kedge\\Desktop\\tests\\nameTest.properties");
        XmlAndPropertiesRule rule = new XmlAndPropertiesRule(path);
        boolean actual = rule.matches();
        Assert.assertEquals(true, actual);
    }

    @Test
    public void checkRuleArrayFunctionalityJavaFile() throws IOException {
        RulesEngine re = new RulesEngine(loadFile("CDIEJB/ejbModule/com/ibm/ejs/container/_EJSWrapper_Stub.java"), Paths.get("c:\\testTarget"));
        String expected = "src\\main\\java";
        String actual = re.checkRules().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkRuleArrayFunctionalityXML() throws IOException {
        RulesEngine re = new RulesEngine(loadFile("CDIConfig/INT/gmiregistry/services/cdi_event_error.properties"), Paths.get("c:\\testTarget"));
        String expected = "src\\main\\resources";
        String actual = re.checkRules().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkRuleArrayFunctionalityProperties() throws IOException {
        RulesEngine re = new RulesEngine(Paths.get("C:\\IND_Consolidated_Disbursements_Init_WAS61\\source\\CDIEnterprise\\META-INF\\application.xml"), Paths.get("c:\\testTarget"));
        String expected = "src\\main\\resources";
        String actual = re.checkRules().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkRuleArrayMFCDICLIENT() throws IOException {
        RulesEngine re = new RulesEngine(loadFile("CDIClient/appClientModule/META-INF/MANIFEST.MF"), Paths.get("c:\\testTarget"));
        String expected = "src\\main\\resources";
        String actual = re.checkRules().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkCDICommonApp() throws IOException {
        RulesEngine re = new RulesEngine(Paths.get("CDICommonApp\\WebContent\\cdi\\jsp\\body\\Manifest.MF"), Paths.get("c:\\testTarget"));
        Path expected = null;
        Path actual = re.checkRules();
        assertEquals(expected, actual);
    }

    @Test
    public void checkCDIEJBMF() throws IOException {

        RulesEngine re = new RulesEngine(loadFile("CDIEJB/ejbModule/META-INF/MANIFEST.MF"), Paths.get("c://testTarget"));
        String expected = "src\\main\\resources";
        String actual = re.checkRules().toString();
        assertEquals(expected, actual);
    }

    @Test
    public void checkCDIEnterprize() throws IOException {

        RulesEngine re = new RulesEngine(Paths.get("C:\\IND_Consolidated_Disbursements_Init_WAS61\\source\\CDIEJB\\ejbModule\\META-INF\\MANIFEST.MF"), Paths.get("c:\\testTarget"));
        String expected = "src\\main\\resources";
        String actual = re.checkRules().toString();
        assertEquals(expected, actual);
    }

    @Test
    public void xmlToMavenStructurePath() throws IOException {
        RulesEngine re = new RulesEngine(Paths.get("C:\\IND_Consolidated_Disbursements_Init_WAS61\\source\\CDICommonApp\\source\\CDIPropertiesLoc.xml"), Paths.get("c:\\testTarget"));
        Path expected = Paths.get("src\\main\\resources\\");
        Path actual = re.checkRules();
        assertEquals(expected, actual);
    }

    @Test
    public void CDIConfigTestMavenPath() throws IOException {
        RulesEngine re = new RulesEngine(Paths.get("C:\\IND_Consolidated_Disbursements_Init_WAS61\\source\\CDIConfig\\DEV\\gmiregistry\\dsimsgclient.qa2oreopsqa"), Paths.get("c:\\testTarget"));
        Path expected = Paths.get("src\\main\\resources\\");
        Path actual = re.checkRules();
        assertEquals(expected, actual);
    }

    @Test
    public void unidentifiedFileTest() throws IOException {
        String filePath = "CDICommonWeb/source/CDIProperties.lol";
        RulesEngine re = new RulesEngine(loadFile(filePath), Paths.get("c:\\testTarget"));
        Path actual = re.checkRules();
        assertEquals(null, actual);
    }

    @Test
    public void folderCheck() throws IOException {
        RulesEngine re = new RulesEngine(loadFile("CDICommonWeb/source/com/metlife/ib/cdi/web/Constants.java"), Paths.get("c:\\testTarget"));
        Path expected = Paths.get("CDICommonWeb");
        Path actual = re.getFolder();
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void folderCheckCDICommonApp() throws IOException {
        String filePath = "CDICommonApp/source/org/oasis_open/docs/wss/_2004/_01/oasis_200401_wss_wssecurity_secext_1_0/ObjectFactory.java";
        RulesEngine re = new RulesEngine(loadFile(filePath), Paths.get("c:\\testTarget"));
        Path expected = Paths.get("CDICommonApp");
        Path actual = re.getFolder();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void MFCDIWebTest() throws IOException {
        String filePath = "CDIWeb/WebContent/META-INF/MANIFEST.MF";
        RulesEngine re = new RulesEngine(loadFile(filePath), Paths.get("c:\\testTarget"));
        Path expected = Paths.get("src\\main\\webapp");
        Path actual = re.checkRules();
        assertEquals(expected, actual);
    }

    @Test
    public void propertiesTest() throws IOException {
        String filePath = "CDIWeb/javasource/ApplicationResources.properties";
        RulesEngine re = new RulesEngine(loadFile(filePath), Paths.get("c:\\testTarget"));
        Path expected = Paths.get("src\\main\\resources");
        Path actual = re.checkRules();
        assertEquals(expected, actual);
    }

    @Test
    public void webContentTest() throws IOException {
        String filePath = "CDIWeb/WebContent/WEB-INF/validation_1_1.dtd";
        RulesEngine re = new RulesEngine(loadFile(filePath), Paths.get("c:\\testTarget"));
        Path expected = Paths.get("src\\main\\webapp");
        Path actual = re.checkRules();
        assertEquals(expected, actual);
    }

    @Test
    public void checkProjectTest() throws IOException {
        String filePath = "CDICommonApp\\WebContent\\cdi\\jsp\\body\\test.project";
        RulesEngine re = new RulesEngine(loadFile(filePath), Paths.get("c:\\testTarget"));
        Path expected = null;
        Path actual = re.checkRules();
        assertEquals(expected, actual);
    }

    @Test
    public void checkWebSettingsTest() throws IOException {
        String filePath = "CDICommonApp/WebContent/cdi/jsp/body/test.websettings";
        RulesEngine re = new RulesEngine(loadFile(filePath), Paths.get("c:\\testTarget"));
        Path expected = null;
        Path actual = re.checkRules();
        assertEquals(expected, actual);
    }

    @Test
    public void checkSettingsFolder() throws IOException {
        String filePath = "CDICommonApp/WebContent/cdi/.settings/body/test.project";
        RulesEngine re = new RulesEngine(loadFile(filePath), Paths.get("c:\\testTarget"));
        Path expected = null;
        Path actual = re.checkRules();
        assertEquals(expected, actual);
    }

    @Test
    public void checkClassTest() throws IOException {
        String filePath = "CDICommonWeb/WebContent/cdi/jsp/body/MANIFEST.MF";
        RulesEngine re = new RulesEngine(loadFile(filePath), Paths.get("c:\\testTarget"));
        Path expected = null;
        Path actual = re.checkRules();
        assertEquals(expected, actual);
    }

    @Test
    public void checkJARTest() throws IOException {
        String filePath = "CDICommonWeb\\WebContent\\cdi\\jsp\\body\\testOne.jar";
        RulesEngine re = new RulesEngine(loadFile(filePath), Paths.get("c:\\testTarget"));
        Path expected = null;
        Path actual = re.checkRules();
        assertEquals(expected, actual);
    }










}
