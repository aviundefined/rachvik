package com.rachvik.user.service;

import com.rachvik.common.utils.StringUtils;
import com.rachvik.profile.user.model.GetUserRequest;
import com.rachvik.profile.user.model.GetUserResponse;
import com.rachvik.profile.user.model.SaveUserRequest;
import com.rachvik.profile.user.model.SaveUserResponse;
import com.rachvik.user.mappers.UserMapper;
import com.rachvik.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public SaveUserResponse saveUser(final SaveUserRequest request) {
    if (!request.hasUser()) {
      throw new RuntimeException("Invalid request: " + request);
    }
    userRepository.save(userMapper.protoToModel(request.getUser()));
    return SaveUserResponse.newBuilder().setIsSuccess(true).build();
  }

  public GetUserResponse getUser(final GetUserRequest request) {
    if (StringUtils.isBlank(request.getUsername())) {
      throw new RuntimeException("Invalid request: " + request);
    }
    val user =
        userRepository
            .findByUsername(request.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found: " + request.getUsername()));
    return GetUserResponse.newBuilder().setUser(userMapper.modelToProto(user)).build();
  }
}
