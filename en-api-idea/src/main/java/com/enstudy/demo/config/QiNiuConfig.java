package com.enstudy.demo.config;

import java.util.Properties;

import com.qiniu.common.Zone;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * 七牛云配置
 *
 **/

@Component
@Data
public class QiNiuConfig {
    @Value("${qiniu.access-key}")
    private String accessKey;
    @Value("${qiniu.bucket}")
    private String bucket;
    @Value("${qiniu.domain-of-bucket}")
    private String domainOfBucket;
    @Value("${qiniu.expire-in-seconds}")
    private long expireInSeconds;
    @Value("${qiniu.secret-key}")
    private String secretKey;
    @Value("${qiniu.zone}")
    private String zone;
    @Value("${qiniu.endpoint}")
    private String endpoint;
}