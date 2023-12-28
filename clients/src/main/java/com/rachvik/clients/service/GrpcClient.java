package com.rachvik.clients.service;

import com.rachvik.clients.grpc.config.Service;

public interface GrpcClient<T> {

  T getStub();

  Service service();
}
