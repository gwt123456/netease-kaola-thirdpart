import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

public class ThirdpartQueryOrderStatus {

    public static void main(String[] args) throws JSONException {
        String url = "http://test1.thirdpart.kaolatest.netease.com/api/queryOrderStatus";
        HttpPost httpRequst = new HttpPost(url);// 创建HttpPost对象

        String thirdPartOrderId = "xxxxxxxx";
        String source = "1200";
        String appKey = "xxxxxxxxxxxxxxxxxxxx";
        String appSecret = "xxxxxxxxxxxxxx";


        long time = System.currentTimeMillis();
        TreeMap<String, String> parameterMap = new TreeMap<String, String>();
        parameterMap.put("thirdPartOrderId", thirdPartOrderId);
        parameterMap.put("channelId", source);
        parameterMap.put("timestamp", new Timestamp(time).toString());
        parameterMap.put("v", "1.0");
        parameterMap.put("sign_method", "md5");
        parameterMap.put("app_key", appKey);

        String sign = APIUtil.createSign(appSecret, parameterMap);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("thirdPartOrderId", thirdPartOrderId));
        params.add(new BasicNameValuePair("timestamp", new Timestamp(time).toString()));
        params.add(new BasicNameValuePair("v", "1.0"));
        params.add(new BasicNameValuePair("sign_method", "md5"));
        params.add(new BasicNameValuePair("app_key", appKey));
        params.add(new BasicNameValuePair("sign", sign));
        params.add(new BasicNameValuePair("channelId", source));

        try {
            httpRequst.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);
            System.out.println(httpResponse.getStatusLine().getStatusCode());
            HttpEntity httpEntity = httpResponse.getEntity();
            String result = EntityUtils.toString(httpEntity);// 取出应答字符串

            LogConstant.pushLog.info("[Thirdpart Order][reslut] " + result);
            System.out.println("[Thirdpart Order][reslut] " + result);

        } catch (UnsupportedEncodingException e) {
            LogConstant.pushLog.error("[Thirdpart Order][reslut] ", e);
        } catch (ClientProtocolException e) {
            LogConstant.pushLog.error("[[Thirdpart Order][reslut] ", e);
        } catch (IOException e) {
            LogConstant.pushLog.error("[[Thirdpart Order][reslut] ", e);
        } catch (Exception e) {
            LogConstant.pushLog.error("[[Thirdpart Order][reslut] ", e);
        }
        System.out.println("end...");

    }
}
