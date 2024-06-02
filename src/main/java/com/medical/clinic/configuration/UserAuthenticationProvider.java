package com.medical.clinic.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.medical.clinic.dto.CredentialsDTO;
import com.medical.clinic.dto.UserDTOSecurity;
import com.medical.clinic.service.AuthenticationService;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class UserAuthenticationProvider {
    private static final String ID = "id";
    private static final String ROLES = "roles";
    private static final String ROLES_DELIMITOR = ",";
    private final AuthenticationService authenticationService;

    @Value(
            "${jwt.key}"
    )
    private String secretKey;

    public UserAuthenticationProvider(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostConstruct
    protected void init() {

        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDTOSecurity userDTO) throws ParseException {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000);
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withIssuer(userDTO.getUsername())
                //.withClaim(ID, userDTO.getUserId())
                .withClaim(ROLES, userDTO.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(ROLES_DELIMITOR)))
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(algorithm);
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        UserDTOSecurity userDTO = new UserDTOSecurity();
        //authenticationService.findByLogin(decodedJWT.getIssuer());
        // avoided - to avoid possible DB hit if it were a prod application
        //userDTO.setUserId(Integer.valueOf(decodedJWT.getClaim(ID).toString()));
        userDTO.setUsername(decodedJWT.getIssuer());
        userDTO.setAuthorities(Arrays.asList(decodedJWT.getClaim(ROLES).toString().replaceAll("\"", "").split(ROLES_DELIMITOR)));
        userDTO.setToken(token);
        return new UsernamePasswordAuthenticationToken(userDTO, null, userDTO.getAuthorities());
    }

    public Authentication validateCredentials(CredentialsDTO credentialsDTO) {
        UserDTOSecurity userDTO = authenticationService.authenticate(credentialsDTO);
        return new UsernamePasswordAuthenticationToken(userDTO, null, userDTO.getAuthorities());
    }

}
