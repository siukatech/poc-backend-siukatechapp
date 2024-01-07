package com.siukatech.poc.react.backend.parent.util;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.core5.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@NoArgsConstructor
public class URLEncoderUtils {
    private static final Logger logger = LoggerFactory.getLogger(URLEncoderUtils.class);

    public static String encodeToQueryString(List<NameValuePair> nameValuePairList) {
        List<String> queryList = nameValuePairList.stream().map(nameValuePair -> {
                    String str = "";
                    try {
                        str = URLEncoder.encode(nameValuePair.getName(), StandardCharsets.UTF_8.name())
                                + "="
                                + URLEncoder.encode(nameValuePair.getValue(), StandardCharsets.UTF_8.name()
                        );
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                    return str;
                })
                .filter(s -> StringUtils.isNoneEmpty(s)).toList();
        String queryString = StringUtils.join(queryList, "&");
        return queryString;
    }
}