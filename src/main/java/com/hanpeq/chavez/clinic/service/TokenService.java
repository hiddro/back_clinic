package com.hanpeq.chavez.clinic.service;

import com.hanpeq.chavez.clinic.dto.TokenResponse;
import com.hanpeq.chavez.clinic.security.models.User;

public interface TokenService {

    TokenResponse buildTokenResponse(String passRequest, User userDetails);
}
