# 限流实现

## 1.基于redis和lua脚本实现
## 2.优化方式1
>程序每次执行每次都需要通过buildLuaScript()方法构建lua执行脚本，
>效率低，我们可以生成一个lua文件放在resources目录下，利用@PostConstruct注解提前加载。