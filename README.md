# mint

中台服务JAR包&service

## 注意：
  1. 所有需要校验用户context的API，请求URI中必须有"/service/"。如无此pattern，则无法校验用户合法性。
  2. 由于多系统之间存在RPC调用，因此子服务应分两部分：api包 & implementation包。方便其他service调用，将api定义在api包中。
  3. RPC调用使用"RpcHandler,get(APIClass<?>)"来完成调用，支持POST & GET 请求类型，足以满足绝大多数需求。
  4. POST RPC：封装单体DTO进行网络传输
  5. GET RPC： 以RESTFUL path variable 方式进行GET请求。
