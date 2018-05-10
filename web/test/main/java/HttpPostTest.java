import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author 范文武
 * @date 2018/05/03 11:00
 */
//@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
//@ContextConfiguration(locations={"classpath:application-context-web.xml"}) //加载配置文件
public class HttpPostTest {
    public static String URL = "http://218.90.174.179:9012/JkzUploadServle";

    public static void main(String[] args) {

        JSONObject jsobj1 = new JSONObject();
        JSONObject jsobj2 = new JSONObject();
        jsobj2.put("deviceID", "112");
        jsobj2.put("channel", "channel");
        jsobj2.put("state", "0");
        jsobj1.put("item", jsobj2);
        jsobj1.put("requestCommand", "control");

        post(jsobj1);

    }

    public static String post(JSONObject json) {

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(URL);

        post.setHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Basic YWRtaW46");
        String result = "";

        try {

            StringEntity s = new StringEntity(json.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json"));
            post.setEntity(s);

            // 发送请求
            HttpResponse httpResponse = client.execute(post);

            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();

            result = strber.toString();
            System.out.println(result);

            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                System.out.println("请求服务器成功，做相应处理");

            } else {

                System.out.println("请求服务端失败");

            }

        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        }

        return result;
    }
}
