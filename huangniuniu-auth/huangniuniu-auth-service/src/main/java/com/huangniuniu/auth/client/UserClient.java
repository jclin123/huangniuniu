package com.huangniuniu.auth.client;

import com.huangniuniu.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("user-service")
public interface UserClient extends UserApi {
}
