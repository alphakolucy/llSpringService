package com.booledata.llspringparent.api.auth;


import com.booledata.llspringparent.common.model.response.ResponseResult;
import com.booledata.llspringparent.model.ucenter.request.LoginRequest;
import com.booledata.llspringparent.model.ucenter.response.JwtResult;
import com.booledata.llspringparent.model.ucenter.response.LoginResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* @author Administrator
* @date 2019/10/11
**/
@Api(value = "用户认证",tags = {"用户认证接口"})
public interface AuthControllerApi {
    @ApiOperation("登录")
    public LoginResult login(LoginRequest loginRequest);

    @ApiOperation("退出")
    public ResponseResult logout();

    @ApiOperation("查询用户jwt令牌--暂未启用")
    public JwtResult userjwt();
}
