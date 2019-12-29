package com.onfresh.cloud.api.dao;


import com.onfresh.cloud.api.domain.JDAuthorizeToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JdAuthorizeTokenDao extends JpaRepository<JDAuthorizeToken, Long> {
}
