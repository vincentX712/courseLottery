package com.officerschool.courselottery.service;

import com.github.benmanes.caffeine.cache.Cache;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 6/24/24
 */
@Service
public class ShareService {

    private static final Logger logger = LoggerFactory.getLogger(ShareService.class);

    @Resource
    Cache<String, String> caffeineCache;

    /**
     * 生成分享链接（将url中的id字段的值进行加密，并写入缓存）
     * @param id
     * @return
     */
//    public String shareUrl(String encodedUrl) {
//        try {
//            String url = URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.name());
//            Map<String, String> parameters = parseQueryParameters(url);
//            if (parameters == null)
//                return null;
//            String idValue = parameters.get("id");
//            if (StringUtils.isBlank(idValue))
//                return null;
//            String idCode = UUID.randomUUID().toString().replace("-", "");
//            caffeineCache.put(idCode, idValue);
//            caffeineCache.put(idValue, idCode);
//            parameters.put("id", idCode);
//            return generateUrl(getBaseUrl(url), parameters);
//        } catch (Exception e) {
//            logger.error("shareUrl error", e);
//            return null;
//        }
//    }
    public String shareUrl(String id) {
        try {
            String idCode = UUID.randomUUID().toString().replace("-", "");
            caffeineCache.put(idCode, id);
            caffeineCache.put(id, idCode);
            return idCode;
        } catch (Exception e) {
            logger.error("shareUrl error", e);
            return null;
        }
    }

    /**
     * 解析分享链接（将url中的id字段的值进行解密，并返回，缓存失效则解析失败）
     * @param cipher
     * @return
     */
//    public String originUrl(String encodedUrl) {
//        try {
//            String url = URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.name());
//            Map<String, String> parameters = parseQueryParameters(url);
//            if (parameters == null)
//                return null;
//            String idCode = parameters.get("id");
//            if (StringUtils.isBlank(idCode))
//                return null;
//            String cacheIdValue = caffeineCache.getIfPresent(idCode);
//            if (StringUtils.isBlank(cacheIdValue))
//                return null;
//            parameters.put("id", cacheIdValue);
//            return generateUrl(getBaseUrl(url), parameters);
////            return cacheIdValue;
//        } catch (Exception e) {
//            logger.error("originUrl error", e);
//            return null;
//        }
//    }
    public String originUrl(String cipher) {
        try {

            if (cipher == null)
                return null;
            if (StringUtils.isBlank(cipher))
                return null;
            String cacheIdValue = caffeineCache.getIfPresent(cipher);
            if (StringUtils.isBlank(cacheIdValue))
                return null;
            return cacheIdValue;
        } catch (Exception e) {
            logger.error("originUrl error", e);
            return null;
        }
    }
    private static String getBaseUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            return url.getProtocol() + "://" + url.getHost() +
                    (url.getPort() == -1 ? "" : ":" + url.getPort()) +
                    url.getPath();
        } catch (Exception e) {
            throw new RuntimeException("Invalid URL", e);
        }
    }

    private static Map<String, String> parseQueryParameters(String urlString) {
        try {
            URL url = new URL(urlString);
            Map<String, String> parameters = new LinkedHashMap<>();
            String query = url.getQuery();
            if (query != null) {
                String[] pairs = query.split("&");
                for (String pair : pairs) {
                    int idx = pair.indexOf("=");
                    String key = idx > 0 ? pair.substring(0, idx) : pair;
                    String value = idx > 0 && pair.length() > idx + 1 ? pair.substring(idx + 1) : "";
                    parameters.put(URLDecoder.decode(key, "UTF-8"), URLDecoder.decode(value, "UTF-8"));
                }
            }
            return parameters;
        } catch (Exception e) {
            logger.error("ShareService#parseQueryParameters 解析url失败, error: ", e);
            return null;
        }
    }

    private static String generateUrl(String baseUrl, Map<String, String> parameters) {
        try {
            StringBuilder urlBuilder = new StringBuilder(baseUrl);
            if (baseUrl.contains("?")) {
                urlBuilder.append("&");
            } else {
                urlBuilder.append("?");
            }

            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                if (urlBuilder.length() > baseUrl.length() + 1) {
                    urlBuilder.append("&");
                }
                String encodedKey = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString());
                String encodedValue = URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString());
                urlBuilder.append(encodedKey).append("=").append(encodedValue);
            }

            return urlBuilder.toString();
        } catch (Exception e) {
            logger.error("ShareService#generateUrl, error: ", e);
            return null;
        }
    }
}
