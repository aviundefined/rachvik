package com.rachvik.tinyurl.service;

import com.rachvik.tinyurl.OriginalURLRequest;
import com.rachvik.tinyurl.ShortURLRequest;
import com.rachvik.tinyurl.TinyURLResponse;
import com.rachvik.tinyurl.respository.URLMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TinyURLService {
    private static final String SYSTEM_USER_ID = "SYSTEM_USER_ID";

    private final URLMappingRepository urlMappingRepository;
    public TinyURLResponse getShortURL(final ShortURLRequest request) {
        return null;
    }

    public TinyURLResponse getOriginalURL(final OriginalURLRequest request) {
        return null;
    }
}
