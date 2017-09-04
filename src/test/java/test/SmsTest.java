package test;

/**
 * Created with IntelliJ IDEA.
 * Author: CuiCan
 * Date: 2017-9-4
 * Time: 10:51
 * Description: 发短信测试
 */
public class SmsTest extends BasicTest {
/**
 * UserId	东方般若提供的用户名
 * Password	东方般若提供的密码
 * Mobiles	手机号码（支持多手机号，以英文逗号分隔，
 * Get最多不超过1000个手机号;
 * Post无限制，建议不超过1万个，手机号越多响应时间越长）
 * Content	短信内容
 * SenderAddr	扩展尾号（无扩展尾号此参数可为空）V1.3
 * ScheduleTime	计划发送开始时间日期格式YYYYMMDDHHMISS  V1.6
 * 缺省值系统时间
 * ExpireTime	发送超期时间日期格式YYYYMMDDHHMISS  V1.6
 * 缺省值系统时间+1天
 * <p>
 * 乱码处理说明
 * <p>
 * 1.发送内容乱码：
 * try
 * {
 *          content = URLEncoder.encode("您好","GBK");
 * }
 * catch(Exception e)
 * {
 *         
 * }
 * <p>
 * 2.接收响应乱码：
 * // 定义BufferedReader输入流来读取URL的响应
 * in = new BufferedReader(
 *      new InputStreamReader(conn.getInputStream(),"GBK"));
 * 报告匹配说明
 * 1.调用方单条消息发送（MsgId）调用SendSMS接口提交成功后，接口返回GatewayId，
 * 调用方将MsgId与GatewanyId进行对应，异步调用ReceiveReport接口获取报告，
 * 通过报告中返回的GateWayId匹配MsgId，确定消息的发送状态。
 * 2.调用方多条消息发送（MsgId1，MsgId2,…MsgIdN）调用SendSMS接口提交成功后，
 * 接口返回GatewayId，调用方将MsgId与GatewanyId及手机号码进行对应，异步调用ReceiveReport接口获取报告，
 * 通过报告中返回的GateWayId和手机号码匹配MsgId，确定消息的发送状态。
 */

/**
 * 乱码处理说明
 * <p>
 * 1.发送内容乱码：
 * try
 * {
 *          content = URLEncoder.encode("您好","GBK");
 * }
 * catch(Exception e)
 * {
 *         
 * }
 * <p>
 * 2.接收响应乱码：
 * // 定义BufferedReader输入流来读取URL的响应
 * in = new BufferedReader(
 *      new InputStreamReader(conn.getInputStream(),"GBK"));
 * 报告匹配说明
 * 1.调用方单条消息发送（MsgId）调用SendSMS接口提交成功后，接口返回GatewayId，
 * 调用方将MsgId与GatewanyId进行对应，异步调用ReceiveReport接口获取报告，
 * 通过报告中返回的GateWayId匹配MsgId，确定消息的发送状态。
 * 2.调用方多条消息发送（MsgId1，MsgId2,…MsgIdN）调用SendSMS接口提交成功后，
 * 接口返回GatewayId，调用方将MsgId与GatewanyId及手机号码进行对应，异步调用ReceiveReport接口获取报告，
 * 通过报告中返回的GateWayId和手机号码匹配MsgId，确定消息的发送状态。
 */

    /*@Before
    public void getHttpTools(){
        HttpAPIService httpAPIService = applicationContext.getBean(HttpAPIService.class);
    }*/
    /*public static void main(String[] args) {



//        HttpAPIService httpAPIService = applicationContext.getBean(HttpAPIService.class);



        String ip = "118.145.22.172";
        String port = "9888";

        String userId = "3047";
        String Password = "HDjf_170901";
        String mobiles = "18637010312,17612180312";
        String content = "尊敬的用户，这是一条测试短信";

        String base_url = "http://118.145.22.178:9888/smsservice/SendSMS?UserId=3047&Password=HDjf_170901&";

        String result = "";

        try {
            content = URLEncoder.encode(content, "GBK");
            System.out.println("content = " + content);

            String url = base_url + "&Mobiles=" + mobiles + "&Content=" + content;
            result = httpAPIService.doGet(url);
            System.out.println("url = " + url);
            System.out.println("result = " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
}
