package dev.alexsoft.msfigueroajimenez.infrastructure.client;

import dev.alexsoft.msfigueroajimenez.domain.aggregates.constants.Constant;
import dev.alexsoft.msfigueroajimenez.domain.aggregates.dto.SunatDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "apis-net-sunat", url = Constant.URL_BASE + "sunat/")
public interface ApisNetSunatClient {

    @GetMapping("ruc")
    SunatDto getSunatInfo(
            @RequestParam("numero") String ruc,
            @RequestHeader("Authorization") String token
    );
}
