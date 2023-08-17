# CloseAI

对接CloseAI API:

* 如果您经常大量使用 OpenAI 的 API 服务，您应该知道，其实当前 OpenAI 的接口非常不稳定，经常会返回 408, 429, 500 之类的错误，其根本原因就是因为GPU算力不足导致的。本站特意为开发者提供了 OpenAI 侧负载过高报错时，自动重试的能力，此项功能基于我们申请的企业级的全球多数据中心 OpenAI API接口实现，当某一个请求异常时，会自动更换不同的账户，甚至不同的数据中心来重试（每个数据中心的GPU负载是不一样的），最大程度保证接口请求成功，如果您调用频率很高，您会发现我们平台的平均接口错误率要比其他单纯做转发的小平台要低的多(比OpenAI官方还低)，因为所有的错误我们都内部消化了。错误或超时重试有一定额外成本，这部分成本由平台垫付了，无需用户付费，属于平台提供的优化服务之一。

closeai官方网站： https://doc.closeai-asia.com/

openai api 接口文档：
http://chatgpt.cndoc.wiki/doc/

- closeai-spring-starter
- closeai-spring-test

进入closeai-spring-test模块，需要将application.yml文件中的token补充完整，此时就集成完成了，您就可以进行测试了！

application.yml
```
closeai:
    token: --
```
