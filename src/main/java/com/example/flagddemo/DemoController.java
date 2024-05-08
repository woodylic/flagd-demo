package com.example.flagddemo;

import dev.openfeature.sdk.Client;
import dev.openfeature.sdk.EvaluationContext;
import dev.openfeature.sdk.ImmutableContext;
import dev.openfeature.sdk.MutableContext;
import dev.openfeature.sdk.OpenFeatureAPI;
import dev.openfeature.sdk.Value;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Boolean.TRUE;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DemoController {

    private final OpenFeatureAPI openFeature;

    @GetMapping("/api/homepage")
    public Map<String, String> test(@RequestHeader("company") String company) {

        final Client client = openFeature.getClient();

        Map<String, String> resp = new HashMap<>();
        resp.put("content", "Welcome to the homepage!");

        Boolean showBannerEnabled = client.getBooleanValue("show-welcome-banner", false);
        if (TRUE.equals(showBannerEnabled)) {
            resp.put("banner", "Welcome!");
        }

        EvaluationContext context = new ImmutableContext(Map.of("company", new Value(company)));
        String backgroundColor = client.getStringValue("background-color", "red", context);
        resp.put("backgroundColor", backgroundColor);

        return resp;
    }
}
