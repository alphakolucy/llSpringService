package com.booledata.llspringparent.model.springPoint.response;

import com.booledata.llspringparent.common.model.response.ResponseResult;
import com.booledata.llspringparent.common.model.response.ResultCode;
//import com.booledata.llspringparent.model.springPoint.SpringPoint;
import com.booledata.llspringparent.model.springPoint.SpringPoint;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SpringPointResult extends ResponseResult {

    @ApiModelProperty(value = "温泉点信息", example = "true", required = true)
    SpringPoint springPoint;
    public SpringPointResult(ResultCode resultCode, SpringPoint springPoint) {
        super(resultCode);
        this.springPoint = springPoint;
    }
}
