package com.neuedu.tools;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssConfigProperties {
    //地域节点
    private String endpoint = "oss-cn-beijing.aliyuncs.com";
    //个人域名
    private String domain;
    //bucketName
    private String bucketName ="adminstuden";
    private String accessKeyId ="LTAI5tMGWBsYFV45gh2FAv9f";
    private String accessKeySecret = "0DFy4O5D2T1q91kEqAYALwOeZUX6YC";

}