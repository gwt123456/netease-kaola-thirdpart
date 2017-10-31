import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import org.json.JSONObject;

public class ThirdpartOrderConfirmTest {

    public static void main(String[] args) throws JSONException {
        String url = "http://test1.thirdpart.kaolatest.netease.com/api/orderConfirm";

        HttpPost httpRequst = new HttpPost(url);// 创建HttpPost对象

        long time = System.currentTimeMillis();
        String source = "1200";
        String sign_method = "md5";
        String appKey = "xxxx";
        String appSecret = "xxxxxxx";
        String goodsID = "xxxxx";
        String skuId = "xxxxxxxxxxxxx";
        String accountId = "xxxxxxxxxx";

        TreeMap<String, String> parameterMap = new TreeMap<String, String>();
        parameterMap.put("timestamp", new Timestamp(time).toString());
        parameterMap.put("v", "1.0");
        parameterMap.put("sign_method", sign_method);
        parameterMap.put("app_key", appKey);
        parameterMap.put("source", source);

        List<Map<String, Object>> orderItemList = new ArrayList<Map<String, Object>>();
        // 构造商品信息
        Map<String, Object> orderItemMap = new HashMap<String, Object>();
        orderItemMap.put("goodsId", goodsID);
        orderItemMap.put("skuId", skuId);
        orderItemMap.put("buyAmount", 1);
        orderItemMap.put("channelSalePrice", 200);
        orderItemList.add(orderItemMap);
        JSONObject orderItemJsonObject = new JSONObject();
        orderItemJsonObject.put("orderItemList", orderItemList);
        parameterMap.put("orderItemList", orderItemJsonObject.toString());
        // 构造用户信息
        Map<String, Object> userInfoMap = new HashMap<String, Object>();
        userInfoMap.put("accountId", accountId);
        userInfoMap.put("name", "xxxxxxxx");
        userInfoMap.put("mobile", "xxxxxxxxxx");
        userInfoMap.put("email", "xxxxxxxxx");
        userInfoMap.put("provinceName", "浙江省");
        userInfoMap.put("cityName", "宁波市");
        userInfoMap.put("districtName", "北仑区");
        userInfoMap.put("address", "新碶街道北仑世茂世界湾二期3幢201室");
        userInfoMap.put("identityId", "xxxxxxxxxx"); //一般贸易商品可不传
        JSONObject userInfoJsonObject = new JSONObject();
        userInfoJsonObject.put("userInfo", userInfoMap);
        parameterMap.put("userInfo", userInfoJsonObject.toString());

        String sign = APIUtil.createSign(appSecret, parameterMap);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("timestamp", new Timestamp(time).toString()));
        params.add(new BasicNameValuePair("v", "1.0"));
        params.add(new BasicNameValuePair("sign_method", sign_method));
        params.add(new BasicNameValuePair("app_key", appKey));
        params.add(new BasicNameValuePair("sign", sign));
        params.add(new BasicNameValuePair("source", source));
        params.add(new BasicNameValuePair("orderItemList", orderItemJsonObject.toString()));
        params.add(new BasicNameValuePair("userInfo", userInfoJsonObject.toString()));

        try {
            httpRequst.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            CloseableHttpClient httpclient = HttpClients.createDefault();
            CloseableHttpResponse httpResponse = httpclient.execute(httpRequst);
            System.out.println(httpResponse.getStatusLine().getStatusCode());
            HttpEntity httpEntity = httpResponse.getEntity();
            String result = EntityUtils.toString(httpEntity);// 取出应答字符串
            LogConstant.pushLog.info("[Thirdpart Order][result] " + result);
            System.out.println("[Thirdpart Order][result] " + result);

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
