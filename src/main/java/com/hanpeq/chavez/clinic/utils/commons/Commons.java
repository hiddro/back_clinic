package com.hanpeq.chavez.clinic.utils.commons;

import com.hanpeq.chavez.clinic.dto.UserRequest;
import com.hanpeq.chavez.clinic.models.UserPrincipal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@AllArgsConstructor
public class Commons {

    public static String generateCode(){
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 8;
        SecureRandom random = new SecureRandom();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString()
                .toUpperCase();

        return generatedString;
    }
}
