import com.haitao.thirdpart.sdk.APIUtil;
import com.netease.haitao.thirdpart.constant.LogConstant;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ThirdpartQueryGoodsInfoByIdsTest {
    public static void main(String[] args) throws JSONException {
        String url = "http://test1.thirdpart.kaolatest.netease.com/api/queryGoodsInfoByIds";
        HttpPost httpRequst = new HttpPost(url);// 创建HttpPost对象

        long time = System.currentTimeMillis();
        String source = "1200";
        String sign_method = "md5";
        String appKey = "xxxxxxxxxxxxx";
        String appSecret = "xxxxxxxxxxxxx";
        JSONArray skuIds= new JSONArray();
        skuIds.put("xxxxxxx-xxxxxxxxxxx");
	JSONArray channelSalePrices = new JSONArray();        
        channelSalePrices.put("496");
        Integer queryType = 0;
        TreeMap<String, String> parameterMap = new TreeMap<String, String>();
        parameterMap.put("timestamp", new Timestamp(time).toString());
        parameterMap.put("v", "1.0");
        parameterMap.put("sign_method", sign_method);
        parameterMap.put("app_key", appKey);
        parameterMap.put("channelId", source);
        parameterMap.put("queryType", queryType.toString());
        parameterMap.put("skuIds", skuIds.toString());
        parameterMap.put("channelSalePrices", channelSalePrices.toString());
        String sign = APIUtil.createSign(appSecret, parameterMap);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("timestamp", new Timestamp(time).toString()));
        params.add(new BasicNameValuePair("v", "1.0"));
        params.add(new BasicNameValuePair("sign_method", sign_method));
        params.add(new BasicNameValuePair("app_key", appKey));
        params.add(new BasicNameValuePair("sign", sign));
        params.add(new BasicNameValuePair("channelId", source));
        params.add(new BasicNameValuePair("queryType", queryType.toString()));
        params.add(new BasicNameValuePair("skuIds", skuIds.toString()));
        params.add(new BasicNameValuePair("channelSalePrices", channelSalePrices.toString()));


        try {
            httpRequst.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            CloseableHttpClient httpclient = HttpClients.createDefault();
            CloseableHttpResponse httpResponse = httpclient.execute(httpRequst);
            System.out.println(httpResponse.getStatusLine().getStatusCode());
            HttpEntity httpEntity = httpResponse.getEntity();
            String result = EntityUtils.toString(httpEntity);// 取出应答字符串
            LogConstant.pushLog.info("[Thirdpart queryGoodsInfoById][result] " + result);
            System.out.println("[Thirdpart queryGoodsInfoById][result] " + result);

        } catch (UnsupportedEncodingException e) {
            LogConstant.pushLog.error("[Thirdpart Order][result] ", e);
        } catch (ClientProtocolException e) {
            LogConstant.pushLog.error("[[Thirdpart Order][result] ", e);
        } catch (IOException e) {
            LogConstant.pushLog.error("[[Thirdpart Order][result] ", e);
        } catch (Exception e) {
            LogConstant.pushLog.error("[[Thirdpart Order][result] ", e);
        }
        System.out.println("end...");

    }
}
