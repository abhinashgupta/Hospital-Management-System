package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class TokenBlacklistService {

    // For a real application, use a persistent cache like Redis instead of an in-memory set.
    private final Set<String> blacklist = Collections.synchronizedSet(new HashSet<>());

    public void blacklistToken(String token) {
        blacklist.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklist.contains(token);
    }
}
