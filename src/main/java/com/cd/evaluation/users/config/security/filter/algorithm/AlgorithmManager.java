package com.cd.evaluation.users.config.security.filter.algorithm;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

@Component
public class AlgorithmManager {
    public Algorithm retrieveAlgorithm() {
        //TODO mudar esse secret, salvar em algum lugar cryptografado e depois descrypt e colocar aqui
        return Algorithm.HMAC256("secret".getBytes());
    }
}
