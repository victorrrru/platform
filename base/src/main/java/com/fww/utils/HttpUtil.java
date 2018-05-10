package com.fww.utils;

import com.taobao.diamond.test.AESUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * @author 范文武
 * @date 2018/05/10 10:42
 */
public class HttpUtil {
    private static Logger logger = Logger.getLogger(HttpUtil.class);
    private static int MAX_PER_ROUTE = 10;
    private static int MAX_TOTAL = 100;
    private static int SO_TIMEOUT = '썐';
    private static int CONNECTION_TIMETOU = '썐';
    private static HttpClient httpClient = null;
    private static String CHARSET = "UTF-8";
    private static String UA = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";

    public HttpUtil() {
    }

    public static HttpClient getThreadSafeHttpClient(int maxPerRoute, int maxTotal, int soTimeout, int connectionTimeout) {
        DefaultHttpClient httpClient = null;

        try {
            PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
            cm.setDefaultMaxPerRoute(maxPerRoute);
            cm.setMaxTotal(maxTotal);
            httpClient = new DefaultHttpClient(cm);
            httpClient.getParams().setIntParameter("http.socket.timeout", soTimeout);
            httpClient.getParams().setIntParameter("http.connection.timeout", connectionTimeout);
            httpClient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(3, true));
            HttpClientParams.setCookiePolicy(httpClient.getParams(), "compatibility");
            httpClient.setRedirectStrategy(new LaxRedirectStrategy());
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init((KeyManager[])null, new TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }}, (SecureRandom)null);
            SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            Scheme sch = new Scheme("https", 443, sf);
            Scheme sch2 = new Scheme("http", 80, PlainSocketFactory.getSocketFactory());
            httpClient.getConnectionManager().getSchemeRegistry().register(sch);
            httpClient.getConnectionManager().getSchemeRegistry().register(sch2);
        } catch (Exception var10) {
            logger.error(var10.getMessage(), var10);
        }

        return httpClient;
    }

    public static String getPostContent(String url, Map<String, String> formEntity, String charset) {
        try {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nvps = new ArrayList();
            Iterator var5 = formEntity.entrySet().iterator();

            while(var5.hasNext()) {
                Map.Entry<String, String> entity = (Map.Entry)var5.next();
                nvps.add(new BasicNameValuePair((String)entity.getKey(), (String)entity.getValue()));
            }

            httpPost.setHeader("User-Agent", "Http_Client_4.2");
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
            HttpResponse response = httpClient.execute(httpPost);
            String content = null;
            if(response.getEntity() != null) {
                charset = EntityUtils.getContentCharSet(response.getEntity()) == null?charset:EntityUtils.getContentCharSet(response.getEntity());
                content = new String(EntityUtils.toByteArray(response.getEntity()), charset);
            }

            httpPost.abort();
            return content;
        } catch (Exception var7) {
            logger.error(var7.getMessage(), var7);
            return null;
        }
    }

    public static String getPostContent(String url, Map<String, String> headerEntity, String dataStr, String charset) {
        try {
            HttpPost httpPost = new HttpPost(url);
            Iterator var5 = headerEntity.entrySet().iterator();

            while(var5.hasNext()) {
                Map.Entry<String, String> entity = (Map.Entry)var5.next();
                httpPost.addHeader((String)entity.getKey(), (String)entity.getValue());
            }

            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
            httpPost.setEntity(new StringEntity(dataStr, Charset.forName(charset)));
            HttpResponse response = httpClient.execute(httpPost);
            String content = null;
            if(response.getEntity() != null) {
                charset = EntityUtils.getContentCharSet(response.getEntity()) == null?charset:EntityUtils.getContentCharSet(response.getEntity());
                content = new String(EntityUtils.toByteArray(response.getEntity()), charset);
            }

            httpPost.abort();
            return content;
        } catch (Exception var7) {
            logger.error(var7.getMessage(), var7);
            return null;
        }
    }

    public static byte[] getPostStream(String url, Map<String, String> formEntity, String charset) {
        try {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nvps = new ArrayList();
            Iterator var5 = formEntity.entrySet().iterator();

            while(var5.hasNext()) {
                Map.Entry<String, String> entity = (Map.Entry)var5.next();
                nvps.add(new BasicNameValuePair((String)entity.getKey(), (String)entity.getValue()));
            }

            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
            HttpResponse response = httpClient.execute(httpPost);
            byte[] content = null;
            if(response.getEntity() != null) {
                content = EntityUtils.toByteArray(response.getEntity());
            }

            httpPost.abort();
            return content;
        } catch (Exception var7) {
            logger.error(var7.getMessage(), var7);
            return null;
        }
    }

    public static byte[] getMultiPartPostStream(String url, Map<String, Object> formEntity, String charset) {
        try {
            HttpPost httpPost = new HttpPost(url);
            MultipartEntity reqEntity = new MultipartEntity();
            Iterator var5 = formEntity.entrySet().iterator();

            while(var5.hasNext()) {
                Map.Entry<String, Object> entity = (Map.Entry)var5.next();
                if(entity.getValue() instanceof File) {
                    reqEntity.addPart((String)entity.getKey(), new FileBody((File)entity.getValue()));
                } else {
                    reqEntity.addPart((String)entity.getKey(), new StringBody(entity.getValue().toString(), Charset.forName(CHARSET)));
                }
            }

            httpPost.setHeader("User-Agent", "Http_Client_4.2");
            httpPost.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(httpPost);
            byte[] content = null;
            if(response.getEntity() != null) {
                content = EntityUtils.toByteArray(response.getEntity());
            }

            httpPost.abort();
            return content;
        } catch (Exception var7) {
            logger.error(var7.getMessage(), var7);
            return null;
        }
    }

    public static String getPostContent(String url, byte[] data, String charset) {
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("User-Agent", "Http_Client_4.2");
            httpPost.setEntity(new ByteArrayEntity(data));
            HttpResponse response = httpClient.execute(httpPost);
            String content = null;
            if(response.getEntity() != null) {
                charset = EntityUtils.getContentCharSet(response.getEntity()) == null?charset:EntityUtils.getContentCharSet(response.getEntity());
                content = new String(EntityUtils.toByteArray(response.getEntity()), charset);
            }

            httpPost.abort();
            return content;
        } catch (Exception var6) {
            logger.error(var6.getMessage(), var6);
            return null;
        }
    }

    public static String getContent(String url) {
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", UA);
            HttpResponse response = httpClient.execute(httpGet);
            String content = null;
            if(response.getEntity() != null) {
                CHARSET = EntityUtils.getContentCharSet(response.getEntity()) == null?CHARSET:EntityUtils.getContentCharSet(response.getEntity());
                content = new String(EntityUtils.toByteArray(response.getEntity()), CHARSET);
            }

            httpGet.abort();
            return content;
        } catch (Exception var4) {
            logger.error(var4.getMessage(), var4);
            return null;
        }
    }

    public static String postContent(String url, Map<String, Object> formEntity) {
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("User-Agent", UA);
            MultipartEntity reqEntity = new MultipartEntity();
            Iterator var4 = formEntity.entrySet().iterator();

            while(var4.hasNext()) {
                Map.Entry<String, Object> entity = (Map.Entry)var4.next();
                if(entity.getValue() instanceof File) {
                    reqEntity.addPart((String)entity.getKey(), new FileBody((File)entity.getValue()));
                } else {
                    reqEntity.addPart((String)entity.getKey(), new StringBody(entity.getValue().toString(), Charset.forName(CHARSET)));
                }
            }

            httpPost.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(httpPost);
            String content = null;
            if(response.getEntity() != null) {
                CHARSET = EntityUtils.getContentCharSet(response.getEntity()) == null?CHARSET:EntityUtils.getContentCharSet(response.getEntity());
                content = new String(EntityUtils.toByteArray(response.getEntity()), CHARSET);
            }

            httpPost.abort();
            return content;
        } catch (Exception var6) {
            logger.error(var6.getMessage(), var6);
            return null;
        }
    }

    public static String postContent(String url, Map<String, Object> formEntity, int soTimeout, int connectionTimeout) {
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("User-Agent", UA);
            MultipartEntity reqEntity = new MultipartEntity();
            Iterator var6 = formEntity.entrySet().iterator();

            while(var6.hasNext()) {
                Map.Entry<String, Object> entity = (Map.Entry)var6.next();
                if(entity.getValue() instanceof File) {
                    reqEntity.addPart((String)entity.getKey(), new FileBody((File)entity.getValue()));
                } else {
                    reqEntity.addPart((String)entity.getKey(), new StringBody(entity.getValue().toString(), Charset.forName(CHARSET)));
                }
            }

            httpPost.setEntity(reqEntity);
            httpClient.getParams().setIntParameter("http.socket.timeout", soTimeout);
            httpClient.getParams().setIntParameter("http.connection.timeout", connectionTimeout);
            HttpResponse response = httpClient.execute(httpPost);
            String content = null;
            if(response.getEntity() != null) {
                CHARSET = EntityUtils.getContentCharSet(response.getEntity()) == null?CHARSET:EntityUtils.getContentCharSet(response.getEntity());
                content = new String(EntityUtils.toByteArray(response.getEntity()), CHARSET);
            }

            httpPost.abort();
            return content;
        } catch (Exception var8) {
            logger.error(var8.getMessage(), var8);
            return null;
        }
    }

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        String aesData = AESUtil.encrypt("demo," + time, "7D92AC6DE5D04C9D5E25667D8AF6A99B");
        String url = "http://localhost:8080/diamond-server/ws.do?method=findSysMenuAll&code=" + aesData + "&data=" + AESUtil.encrypt("demo", "7D92AC6DE5D04C9D5E25667D8AF6A99B");
        Map<String, Object> formEntity = new HashMap();
        formEntity.put("data", AESUtil.encrypt("demo", "7D92AC6DE5D04C9D5E25667D8AF6A99B"));
        formEntity.put("code", aesData);
        String result = postContent(url, formEntity);
        System.out.println("===post====result:" + result);
    }

    static {
        httpClient = getThreadSafeHttpClient(MAX_PER_ROUTE, MAX_TOTAL, SO_TIMEOUT, CONNECTION_TIMETOU);
    }
}
