package com.huangniuniu.auth.client;

import com.huangniuniu.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient
public interface UserClient extends UserApi {
}
