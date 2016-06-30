import com.thoughtworks.pos.domains.UserInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.thoughtworks.pos.services.services.VIPinputParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
/**
 * Created by pyggy on 2016/6/29.
 */
public class VipInputParserTest {
    private File file;


    @Before
    public void setUp() throws Exception {
        file = new File("./UserInfos.json");
String userInfos=new StringBuilder()
        .append("{\n")
        .append("'USER001':{\n")
        .append("\"name\":'USER 001',\n")
        .append("\"isVIP\":true,\n")
        .append("\"points\":200\n")
        .append("},\n")
        .append("'USER002':{\n")
        .append("\"name\":'USER 002',\n")
        .append("\"isVIP\":false,\n")
        .append("\"points\":0\n")
        .append("},\n")
        .append("'USER003':{\n")
        .append("\"name\":'USER 003',\n")
        .append("\"isVIP\":true,\n")
        .append("\"points\":0\n")
        .append("}\n")
        .append("}\n")
        .toString();
        WriteToFile(file,userInfos);
    }

    @After
    public void tearDown() throws Exception {
        if(file.exists()){
            file.delete();
        }

    }



    private void WriteToFile(File file, String content) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.write(content);
        printWriter.close();
    }
    @Test
    public void testParseJsonFileToUseInfo() throws Exception {
        UserInfo userInfo=new VIPinputParser(file).parser("USER001");

        assertThat(userInfo.getUserName(), is("USER 001"));
        assertThat(userInfo.getUserBar(), is("USER001"));
        assertThat(userInfo.getUserIsVIP(), is(true));
        assertThat(userInfo.getUserPoints(),is(200));

    } @Test
    public void testParseJsonFileToUseInfoWhenNotVIP() throws Exception {
        UserInfo userInfo=new VIPinputParser(file).parser("USER002");

        assertThat(userInfo.getUserName(), is("USER 002"));
        assertThat(userInfo.getUserBar(), is("USER002"));
        assertThat(userInfo.getUserIsVIP(), is(false));
        assertThat(userInfo.getUserPoints(),is(000));

    }

}
