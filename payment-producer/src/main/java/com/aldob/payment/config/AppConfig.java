package com.aldob.payment.config;

import java.util.Locale;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "app")
@RequiredArgsConstructor
public class AppConfig {

    private final BuildProperties buildProperties;

    @Value("${server.servlet.context-path}")
    private String contextVersion;

    private boolean ignoreSession;
    private boolean useProxy;
    private String environment;
    private String allowedOrigins;
    private String allowedMethods;
    private String allowedHeaders;
    private String exposedHeaders;
    private String projectId;
    private String secretNameDb;
    public String compatibilityContextVersion() {
        int position = this.contextVersion.toLowerCase(Locale.ROOT).indexOf("/v") + 2;
        String semanticContextVersion = this.contextVersion.substring(position);
        String majorVersionContext = semanticContextVersion.split("[.]")[0];
        String majorVersion = this.buildProperties.getVersion() == null ?
                majorVersionContext : this.buildProperties.getVersion().split("[.]")[0];

        if (majorVersionContext.equals(majorVersion)) {
            return this.buildProperties.getVersion();
        } else {
            return String.format("Major project version in pom.xml (%s), differs from major context version (%s)", majorVersion, semanticContextVersion);
        }
    }
}
