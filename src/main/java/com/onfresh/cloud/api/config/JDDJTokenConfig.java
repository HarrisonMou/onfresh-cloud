package com.onfresh.cloud.api.config;

import com.onfresh.cloud.api.dao.JdAuthorizeTokenDao;
import com.onfresh.cloud.api.domain.JDAuthorizeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Optional;

/**
 * 京东到家授权Token配置文件
 */
@Component
public class JDDJTokenConfig implements InitializingBean, ServletContextAware {

    private static Logger logger = LoggerFactory.getLogger(JDDJTokenConfig.class);

    public static String token;
    public static String expires_in;
    public static String time;

    @Autowired
    private JdAuthorizeTokenDao jdAuthorizeTokenDao;

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("InitializingBean JDDJTokenConfig!");
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        List<JDAuthorizeToken> all = jdAuthorizeTokenDao.findAll();
        if(all != null && all.size() > 0){
            logger.info("jd authorize token:{}", all.get(0));
            token = all.get(0).getToken();
            expires_in = all.get(0).getExpires_in();
            time = all.get(0).getTime();
        }
    }
}
