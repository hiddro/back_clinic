package com.hanpeq.chavez.clinic.service;

import com.hanpeq.chavez.clinic.dto.TokenRequest;
import com.hanpeq.chavez.clinic.dto.TokenResponse;
import com.hanpeq.chavez.clinic.security.models.User;

public interface TokenService {

    TokenResponse getTokenResponse(TokenRequest request, User userDetails);
}
