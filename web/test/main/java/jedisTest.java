import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

/**
 * @author 范文武
 * @date 2018/05/03 11:00
 */
//@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@ContextConfiguration(locations={"classpath:application-context-web.xml"}) //加载配置文件
public class jedisTest {
    public static void main(String[] args) {
        Jedis jedis=new Jedis("192.168.44.129",6379);
        System.out.println(jedis.ping());
        jedis.close();
    }
}
