package com.example.flagddemo;

import dev.openfeature.contrib.providers.flagd.Config;
import dev.openfeature.contrib.providers.flagd.FlagdOptions;
import dev.openfeature.contrib.providers.flagd.FlagdProvider;
import dev.openfeature.sdk.OpenFeatureAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenFeatureConfig {

    @Bean
    public OpenFeatureAPI openFeature() {

        FlagdProvider flagd = new FlagdProvider(
            FlagdOptions.builder()
                .resolverType(Config.Evaluator.IN_PROCESS)
                .host("localhost")
                .port(8015) // 默认连接 8013，而 8013 是 HTTP 服务端口，需要指定 gRPC 端口 8015。
                .build()
        );

        final OpenFeatureAPI openFeatureAPI = OpenFeatureAPI.getInstance();
        openFeatureAPI.setProvider(flagd);

        return openFeatureAPI;
    }
}
