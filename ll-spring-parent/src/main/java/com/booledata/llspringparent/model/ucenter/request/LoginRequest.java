package com.booledata.llspringparent.model.ucenter.request;


import com.booledata.llspringparent.common.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

/**
 * Created by xlr on 2019/09/26.
 */
@Data
@ToString
public class LoginRequest extends RequestData {

    String username;
    String password;
    String verifycode;

}
