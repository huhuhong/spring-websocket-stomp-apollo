# spring-websocket-stomp-apollo
Sample Spring 4 WebSocket over STOMP with Apollo Full Feature Broker Integration

### Introduction 
1. Open a brower and go to , http://localhost:8080/spring-websocket-stomp-apollo/index.html
2. Click **Connect** button to start the WebSocket over Stomp connection
3. Click **Send** button to send message to Server and receive a **/queue/message** event
4. click **Broadcast** button to broadcast mesage client who subscribe to **/topic/broadcast** event

### Prerequisite 
1. Install Apollo, https://activemq.apache.org/apollo/documentation/getting-started.html
2. Default Authentication, Login: **admin**, Password: **password**, you may change accordingly in **etc/users.properties** file of **Broker Configuration**, https://activemq.apache.org/apollo/documentation/user-manual.html#Broker_Configuration

### Stomp Broker Relay Configuration
1. You may switch between Simple Broker and Stomp Broker by changing **register.enableSimpleBroker/.enableStompBrokerRelay** in **WebSocketConfig**
```java
    //WebSocketConfig
    @Override
    public void configureMessageBrokerLog(MessageBrokerRegistry registry) {

        //registry.enableSimpleBroker("/queue/", "/topic/");

        registry.enableStompBrokerRelay("/queue/", "/topic/")
                .setRelayHost("localhost") // Change this accordingly
                .setVirtualHost("mybroker") //This is the default broker name according to Apollo installation guide
                .setClientLogin("admin") //Refer to Step.2 in Prerequisite
                .setClientPasscode("password") //Refer to Step.2 in Prerequisite
                .setSystemLogin("admin") //Refer to Step.2 in Prerequisite
                .setSystemPasscode("password") //Refer to Step.2 in Prerequisite
                .setRelayPort(61613); //This is the default port according to Apollo installation guide

        registry.setApplicationDestinationPrefixes("/app");
    }
```

### Sample Websocket-shapr STOMP Client Code
```csharp
            using (var ws = new WebSocket("ws://localhost:8080/spring-websocket-stomp-apollo/chat/websocket"))
            {
                ws.OnMessage += Ws_OnMessage;
                ws.OnOpen += Ws_OnOpen;
                ws.OnError += Ws_OnError;
                ws.Connect();
                Thread.Sleep(1000);

                StompMessageSerializer serializer = new StompMessageSerializer();

                var connect = new StompMessage("CONNECT");
                connect["accept-version"] = "1.2";
                connect["host"] = "";
                ws.Send(serializer.Serialize(connect));

                Thread.Sleep(1000);

                var sub = new StompMessage("SUBSCRIBE");
                sub["id"] = "sub-999";
                sub["destination"] = "/topic/broadcast";
                ws.Send(serializer.Serialize(sub));

                Console.ReadKey(true);
            }
```
### Reference GIT Project
https://github.com/rstoyanchev/spring-websocket-portfolio
