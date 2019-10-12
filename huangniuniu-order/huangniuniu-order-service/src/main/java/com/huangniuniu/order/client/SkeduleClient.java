package com.huangniuniu.order.client;

import com.huangniuniu.cinema.api.SkeduleApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("cinema-service")
public interface SkeduleClient extends SkeduleApi {
}
