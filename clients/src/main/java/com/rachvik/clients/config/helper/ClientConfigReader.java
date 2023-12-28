package com.rachvik.clients.config.helper;

import com.google.common.io.Resources;
import com.google.protobuf.TextFormat;
import com.rachvik.clients.grpc.config.ClientConfig;
import com.rachvik.clients.grpc.config.ClientConfigs;
import com.rachvik.clients.grpc.config.Service;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@NoArgsConstructor
public class ClientConfigReader {

  private static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";
  private static final String DEFAULT_PROFILE = "dev";
  private static final String GRPC_CONFIG_FILE_PATH = "client_configurations-%s.txt";

  private final Map<Service, ClientConfig> grpcConfigs = new HashMap<>();

  @PostConstruct
  public void loadConfig() throws IOException {
    val profile = System.getProperty(SPRING_PROFILES_ACTIVE, DEFAULT_PROFILE);
    val grpcFilePath = String.format(GRPC_CONFIG_FILE_PATH, profile);
    val text = Resources.toString(Resources.getResource(grpcFilePath), StandardCharsets.UTF_8);
    val builder = ClientConfigs.newBuilder();
    TextFormat.merge(text, builder);
    for (val config : builder.getGrpcConfigList()) {
      grpcConfigs.put(config.getService(), config);
    }
  }

  public ClientConfig getGrpcClientConfig(final Service service) {
    val clientConfig = this.grpcConfigs.get(service);
    if (clientConfig == null) {
      throw new RuntimeException("GrpcConfig not found for service: " + service);
    }
    return clientConfig;
  }
}
