package dev.alexsoft.msfigueroajimenez.infrastructure.client;

import dev.alexsoft.msfigueroajimenez.domain.aggregates.constants.Constant;
import dev.alexsoft.msfigueroajimenez.domain.aggregates.dto.ReniecDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "apis-net-reniec", url = Constant.URL_BASE + "reniec/")
public interface ApisNetReniecClient {

    @GetMapping("dni")
    ReniecDto getReniecInfo(
            @RequestParam("numero") String ruc,
            @RequestHeader("Authorization") String token
    );
}
