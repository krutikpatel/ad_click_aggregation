package com.jsn.adevent.mapper.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Offset service definition. Reducer will send it to mapper
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.60.0)",
    comments = "Source: adeventservice.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class OffsetServiceGrpc {

  private OffsetServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "OffsetService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.jsn.adevent.mapper.grpc.Offset,
      com.jsn.adevent.mapper.grpc.Empty> getSendOffsetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendOffset",
      requestType = com.jsn.adevent.mapper.grpc.Offset.class,
      responseType = com.jsn.adevent.mapper.grpc.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.jsn.adevent.mapper.grpc.Offset,
      com.jsn.adevent.mapper.grpc.Empty> getSendOffsetMethod() {
    io.grpc.MethodDescriptor<com.jsn.adevent.mapper.grpc.Offset, com.jsn.adevent.mapper.grpc.Empty> getSendOffsetMethod;
    if ((getSendOffsetMethod = OffsetServiceGrpc.getSendOffsetMethod) == null) {
      synchronized (OffsetServiceGrpc.class) {
        if ((getSendOffsetMethod = OffsetServiceGrpc.getSendOffsetMethod) == null) {
          OffsetServiceGrpc.getSendOffsetMethod = getSendOffsetMethod =
              io.grpc.MethodDescriptor.<com.jsn.adevent.mapper.grpc.Offset, com.jsn.adevent.mapper.grpc.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendOffset"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.jsn.adevent.mapper.grpc.Offset.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.jsn.adevent.mapper.grpc.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new OffsetServiceMethodDescriptorSupplier("SendOffset"))
              .build();
        }
      }
    }
    return getSendOffsetMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static OffsetServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OffsetServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OffsetServiceStub>() {
        @java.lang.Override
        public OffsetServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OffsetServiceStub(channel, callOptions);
        }
      };
    return OffsetServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static OffsetServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OffsetServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OffsetServiceBlockingStub>() {
        @java.lang.Override
        public OffsetServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OffsetServiceBlockingStub(channel, callOptions);
        }
      };
    return OffsetServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static OffsetServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OffsetServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OffsetServiceFutureStub>() {
        @java.lang.Override
        public OffsetServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OffsetServiceFutureStub(channel, callOptions);
        }
      };
    return OffsetServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Offset service definition. Reducer will send it to mapper
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Sends an Offset
     * </pre>
     */
    default void sendOffset(com.jsn.adevent.mapper.grpc.Offset request,
        io.grpc.stub.StreamObserver<com.jsn.adevent.mapper.grpc.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendOffsetMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service OffsetService.
   * <pre>
   * Offset service definition. Reducer will send it to mapper
   * </pre>
   */
  public static abstract class OffsetServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return OffsetServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service OffsetService.
   * <pre>
   * Offset service definition. Reducer will send it to mapper
   * </pre>
   */
  public static final class OffsetServiceStub
      extends io.grpc.stub.AbstractAsyncStub<OffsetServiceStub> {
    private OffsetServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OffsetServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OffsetServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends an Offset
     * </pre>
     */
    public void sendOffset(com.jsn.adevent.mapper.grpc.Offset request,
        io.grpc.stub.StreamObserver<com.jsn.adevent.mapper.grpc.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendOffsetMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service OffsetService.
   * <pre>
   * Offset service definition. Reducer will send it to mapper
   * </pre>
   */
  public static final class OffsetServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<OffsetServiceBlockingStub> {
    private OffsetServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OffsetServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OffsetServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends an Offset
     * </pre>
     */
    public com.jsn.adevent.mapper.grpc.Empty sendOffset(com.jsn.adevent.mapper.grpc.Offset request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendOffsetMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service OffsetService.
   * <pre>
   * Offset service definition. Reducer will send it to mapper
   * </pre>
   */
  public static final class OffsetServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<OffsetServiceFutureStub> {
    private OffsetServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OffsetServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OffsetServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends an Offset
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.jsn.adevent.mapper.grpc.Empty> sendOffset(
        com.jsn.adevent.mapper.grpc.Offset request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendOffsetMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEND_OFFSET = 0;

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
        case METHODID_SEND_OFFSET:
          serviceImpl.sendOffset((com.jsn.adevent.mapper.grpc.Offset) request,
              (io.grpc.stub.StreamObserver<com.jsn.adevent.mapper.grpc.Empty>) responseObserver);
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
          getSendOffsetMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.jsn.adevent.mapper.grpc.Offset,
              com.jsn.adevent.mapper.grpc.Empty>(
                service, METHODID_SEND_OFFSET)))
        .build();
  }

  private static abstract class OffsetServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    OffsetServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.jsn.adevent.mapper.grpc.AdEventProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("OffsetService");
    }
  }

  private static final class OffsetServiceFileDescriptorSupplier
      extends OffsetServiceBaseDescriptorSupplier {
    OffsetServiceFileDescriptorSupplier() {}
  }

  private static final class OffsetServiceMethodDescriptorSupplier
      extends OffsetServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    OffsetServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (OffsetServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new OffsetServiceFileDescriptorSupplier())
              .addMethod(getSendOffsetMethod())
              .build();
        }
      }
    }
    return result;
  }
}
