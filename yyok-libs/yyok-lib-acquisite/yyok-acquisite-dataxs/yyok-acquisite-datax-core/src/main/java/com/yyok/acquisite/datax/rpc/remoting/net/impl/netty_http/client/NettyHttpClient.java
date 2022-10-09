package com.yyok.acquisite.datax.rpc.remoting.net.impl.netty_http.client;

import com.yyok.acquisite.datax.rpc.remoting.net.Client;
import com.yyok.acquisite.datax.rpc.remoting.net.common.ConnectClient;
import com.yyok.acquisite.datax.rpc.remoting.net.params.XxlRpcRequest;

public class NettyHttpClient extends Client {

    private Class<? extends ConnectClient> connectClientImpl = NettyHttpConnectClient.class;

    @Override
    public void asyncSend(String address, XxlRpcRequest xxlRpcRequest) throws Exception {
        ConnectClient.asyncSend(xxlRpcRequest, address, connectClientImpl, xxlRpcReferenceBean);
    }

}
