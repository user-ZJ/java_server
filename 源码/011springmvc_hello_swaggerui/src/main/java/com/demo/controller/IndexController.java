package com.demo.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;





@RestController
@RequestMapping(value = {"/test/"})
@Api(tags = {"IndexController"},description = "测试")
public class IndexController {
    @ApiOperation(value = "测试接口",notes = "测试接口")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String test(@PathVariable Long id) {
        return "hello swaggerui";
    }
}
