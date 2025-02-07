package com.rueloparente.order_service.domain;

import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public String getLoggedInUserName() {
        return "rueloparente";
    }
}
