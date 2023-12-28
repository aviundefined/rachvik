package com.rachvik.clients;

import com.rachvik.clients.grpc.config.Service;

public interface GrpcClient<T> {

  T getStub();

  Service service();
}
