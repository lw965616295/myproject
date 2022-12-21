# 动态数据源
>相比于多套数据源项目，动态数据源不再是为每个数据库建立一套独立的数据处理逻辑，而是根据实际业务需求来统一逻辑，再需要切换数据源的实际进行处理即可，所以我们不需要在Service层或者Mapper层就对数据库进行划分。

>SpringBoot动态数据源的本质是将多个DataSource存储在一个Map集合中，当需要用到某个数据源时，从Map中获取此数据源进行处理。Spring提供了抽象类AbstractRoutingDataSource，实现了此功能，所以我们实现动态数据源时继承它，实现自己的获取数据源的逻辑即可。
