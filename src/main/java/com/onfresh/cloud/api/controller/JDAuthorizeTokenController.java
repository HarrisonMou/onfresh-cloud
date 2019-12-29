package com.onfresh.cloud.api.controller;

import com.google.gson.Gson;
import com.onfresh.cloud.api.dao.JdAuthorizeTokenDao;
import com.onfresh.cloud.api.domain.JDAuthorizeToken;
import com.onfresh.cloud.api.vo.ApiResponseVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/authorize/token")
public class JDAuthorizeTokenController {

    private Gson gson = new Gson();

    private static Logger logger = LoggerFactory.getLogger(JDAuthorizeTokenController.class);

    @Autowired
    private JdAuthorizeTokenDao jdAuthorizeTokenDao;

    @RequestMapping(value = "/jddj", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponseVo<String> getJDDJAuthorizeToken(String token){
        logger.info("method:{}, parms:{}", "getJDDJAuthorizeToken", token);
        JDAuthorizeToken jdAuthorizeToken = gson.fromJson(token, JDAuthorizeToken.class);
        if(StringUtils.isNotEmpty(token)){
            jdAuthorizeTokenDao.saveAndFlush(jdAuthorizeToken);
        }
        return ApiResponseVo.ok("");
    }
}
