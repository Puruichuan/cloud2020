import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @Author : Mr、Puruichuan
 * @Date : 2021/10/14-9:24 上午
 * @Version : V1.0
 * @TUDO : **
 */
public class T2 {
    public static void main(String[] args) {
        ZonedDateTime zbj = ZonedDateTime.now();//默认时区（中国默认时区为上海不是北京）
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("America/New_York"));//指定时区
        System.out.println("默认时区："+zbj);
        System.out.println("指定时区为美国："+zonedDateTime);
    }
}
