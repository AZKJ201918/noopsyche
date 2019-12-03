package com.azkj.noopsyche.service;

import com.azkj.noopsyche.common.exception.NoopsycheException;

public interface UserService {
    String login(String code, String uuid, String encryptedData, String iv) throws NoopsycheException;
}
