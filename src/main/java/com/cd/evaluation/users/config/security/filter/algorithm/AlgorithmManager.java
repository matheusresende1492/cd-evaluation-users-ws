package com.cd.evaluation.users.config.security.filter.algorithm;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

/**
 * Algorithm manager class
 */
@Component
public class AlgorithmManager {
    public Algorithm retrieveAlgorithm() {
        //Need to encrypt and save the secret in somewhere secure
        return Algorithm.HMAC256("secret".getBytes());
    }
}
