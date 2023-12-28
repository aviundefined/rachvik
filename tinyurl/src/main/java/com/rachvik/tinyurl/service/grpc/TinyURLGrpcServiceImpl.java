package com.rachvik.tinyurl.service.grpc;

import com.rachvik.tinyurl.OriginalURLRequest;
import com.rachvik.tinyurl.ShortURLRequest;
import com.rachvik.tinyurl.TinyURLResponse;
import com.rachvik.tinyurl.TinyUrlServiceGrpc;
import com.rachvik.tinyurl.service.TinyURLService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TinyURLGrpcServiceImpl extends TinyUrlServiceGrpc.TinyUrlServiceImplBase {

    private final TinyURLService tinyURLService;

    @Override
    public void getShortURL(final ShortURLRequest request, final StreamObserver<TinyURLResponse> responseObserver) {
        responseObserver.onNext(tinyURLService.getShortURL(request));
        responseObserver.onCompleted();
    }

    @Override
    public void getOriginalURL(final OriginalURLRequest request, final StreamObserver<TinyURLResponse> responseObserver) {
        responseObserver.onNext(tinyURLService.getOriginalURL(request));
        responseObserver.onCompleted();
    }
}
