import com.fww.utils.HttpUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 范文武
 * @date 2018/05/03 11:00
 */
@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
//@ContextConfiguration(locations={"classpath:application-context-web.xml"}) //加载配置文件
public class jedisTest {
    public static void main(String[] args) {
        Jedis jedis=new Jedis("192.168.44.129",6379);
        System.out.println(jedis.ping());
        jedis.close();
    }
    @Test
    public void test1() {
        //数据
        Map<String, String> dateArry = new HashMap<>();
        dateArry.put("uuid", "gbsdjfgbsdgbfdg");
        dateArry.put("bhkCode", "HFGDSAJFHKLFH132");
        dateArry.put("empNam", "胡高翔");
        dateArry.put("sex", "1");
        //....
        String PostString = HttpUtil.getPostContent("http://218.90.174.179:9012/TjRecordUpload", dateArry,
                "utf-8");
    }
}
