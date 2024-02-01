package com.jsn.adevent.reducer.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * The AdEvent service definition. Mapper will send it to reducer
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.60.0)",
    comments = "Source: adeventservice.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AdClickEventServiceGrpc {

  private AdClickEventServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "AdClickEventService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.jsn.adevent.reducer.grpc.AdEventMap,
      com.jsn.adevent.reducer.grpc.Empty> getSendEventMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendEvent",
      requestType = com.jsn.adevent.reducer.grpc.AdEventMap.class,
      responseType = com.jsn.adevent.reducer.grpc.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.jsn.adevent.reducer.grpc.AdEventMap,
      com.jsn.adevent.reducer.grpc.Empty> getSendEventMethod() {
    io.grpc.MethodDescriptor<com.jsn.adevent.reducer.grpc.AdEventMap, com.jsn.adevent.reducer.grpc.Empty> getSendEventMethod;
    if ((getSendEventMethod = AdClickEventServiceGrpc.getSendEventMethod) == null) {
      synchronized (AdClickEventServiceGrpc.class) {
        if ((getSendEventMethod = AdClickEventServiceGrpc.getSendEventMethod) == null) {
          AdClickEventServiceGrpc.getSendEventMethod = getSendEventMethod =
              io.grpc.MethodDescriptor.<com.jsn.adevent.reducer.grpc.AdEventMap, com.jsn.adevent.reducer.grpc.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendEvent"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.jsn.adevent.reducer.grpc.AdEventMap.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.jsn.adevent.reducer.grpc.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new AdClickEventServiceMethodDescriptorSupplier("SendEvent"))
              .build();
        }
      }
    }
    return getSendEventMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AdClickEventServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AdClickEventServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AdClickEventServiceStub>() {
        @java.lang.Override
        public AdClickEventServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AdClickEventServiceStub(channel, callOptions);
        }
      };
    return AdClickEventServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AdClickEventServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AdClickEventServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AdClickEventServiceBlockingStub>() {
        @java.lang.Override
        public AdClickEventServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AdClickEventServiceBlockingStub(channel, callOptions);
        }
      };
    return AdClickEventServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AdClickEventServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AdClickEventServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AdClickEventServiceFutureStub>() {
        @java.lang.Override
        public AdClickEventServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AdClickEventServiceFutureStub(channel, callOptions);
        }
      };
    return AdClickEventServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * The AdEvent service definition. Mapper will send it to reducer
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Sends an AdEventMap
     * </pre>
     */
    default void sendEvent(com.jsn.adevent.reducer.grpc.AdEventMap request,
        io.grpc.stub.StreamObserver<com.jsn.adevent.reducer.grpc.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendEventMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service AdClickEventService.
   * <pre>
   * The AdEvent service definition. Mapper will send it to reducer
   * </pre>
   */
  public static abstract class AdClickEventServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return AdClickEventServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service AdClickEventService.
   * <pre>
   * The AdEvent service definition. Mapper will send it to reducer
   * </pre>
   */
  public static final class AdClickEventServiceStub
      extends io.grpc.stub.AbstractAsyncStub<AdClickEventServiceStub> {
    private AdClickEventServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdClickEventServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AdClickEventServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends an AdEventMap
     * </pre>
     */
    public void sendEvent(com.jsn.adevent.reducer.grpc.AdEventMap request,
        io.grpc.stub.StreamObserver<com.jsn.adevent.reducer.grpc.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendEventMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service AdClickEventService.
   * <pre>
   * The AdEvent service definition. Mapper will send it to reducer
   * </pre>
   */
  public static final class AdClickEventServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<AdClickEventServiceBlockingStub> {
    private AdClickEventServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdClickEventServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AdClickEventServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends an AdEventMap
     * </pre>
     */
    public com.jsn.adevent.reducer.grpc.Empty sendEvent(com.jsn.adevent.reducer.grpc.AdEventMap request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendEventMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service AdClickEventService.
   * <pre>
   * The AdEvent service definition. Mapper will send it to reducer
   * </pre>
   */
  public static final class AdClickEventServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<AdClickEventServiceFutureStub> {
    private AdClickEventServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdClickEventServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AdClickEventServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends an AdEventMap
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.jsn.adevent.reducer.grpc.Empty> sendEvent(
        com.jsn.adevent.reducer.grpc.AdEventMap request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendEventMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEND_EVENT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND_EVENT:
          serviceImpl.sendEvent((com.jsn.adevent.reducer.grpc.AdEventMap) request,
              (io.grpc.stub.StreamObserver<com.jsn.adevent.reducer.grpc.Empty>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getSendEventMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.jsn.adevent.reducer.grpc.AdEventMap,
              com.jsn.adevent.reducer.grpc.Empty>(
                service, METHODID_SEND_EVENT)))
        .build();
  }

  private static abstract class AdClickEventServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AdClickEventServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.jsn.adevent.reducer.grpc.AdEventProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AdClickEventService");
    }
  }

  private static final class AdClickEventServiceFileDescriptorSupplier
      extends AdClickEventServiceBaseDescriptorSupplier {
    AdClickEventServiceFileDescriptorSupplier() {}
  }

  private static final class AdClickEventServiceMethodDescriptorSupplier
      extends AdClickEventServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    AdClickEventServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (AdClickEventServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AdClickEventServiceFileDescriptorSupplier())
              .addMethod(getSendEventMethod())
              .build();
        }
      }
    }
    return result;
  }
}
