package hotciv.broker;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;

public class LocalMethodClientRequestHandler implements ClientRequestHandler {
    private final Invoker invoker;

    public LocalMethodClientRequestHandler(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public String sendToServerAndAwaitReply(String request) {
        System.out.println(" --> " + request);
        String reply = invoker.handleRequest(request);
        System.out.println(" --< " + reply);
        return reply;
    }

    @Override
    public void setServer(String hostname, int port) {

    }

    @Override
    public void close() {

    }
}
