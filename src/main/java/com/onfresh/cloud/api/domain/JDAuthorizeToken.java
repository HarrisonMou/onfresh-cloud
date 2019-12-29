package com.onfresh.cloud.api.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 京东到家授权token实体类
 */
@Table(name = "jd_authorize_token")
@Entity
@Data
public class JDAuthorizeToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private String expires_in;
    private String time;
    private String uid;
    private String user_nick;
    private String venderId;
}
