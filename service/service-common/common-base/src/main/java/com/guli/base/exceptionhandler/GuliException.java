package com.guli.base.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Alan_
 * @create 2020/12/21 21:08
 */
@Data
@AllArgsConstructor // lobook生成有参数构造方法
@NoArgsConstructor // 生成无参数构造
public class GuliException extends RuntimeException {

    private Integer code; // 异常状态码

    private String msg; // 异常信息


}
