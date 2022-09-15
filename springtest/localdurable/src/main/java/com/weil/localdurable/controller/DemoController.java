package com.weil.localdurable.controller;

import com.weil.localdurable.model.ProInfo;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;

/**
 * @Name: DemoController
 * @Description:
 * @Author: weil
 * @Date: 2022-09-06 10:19
 * @Version: 1.0
 */
@RestController
public class DemoController {
    @PostMapping("pros")
    public List<ProInfo> saveData(@RequestBody List<ProInfo> proInfos) throws Exception{
        // 保存到classpath: 下面，及打包后的“项目\BOOT-INF\classes”目录下
//        String path = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath() + "/de.txt";
        // 上面的这些对于jar包形式，不能进行读取和保存操作，不过可以使用Thread.currentThread().getContextClassLoader()
        //                .getResourceAsStream(""); 返回流的形式，来读取jar包中的内容
        // 判断系统类型，指定不同保存路径
        String path = "";
        if(isWindow()){
            path = "d:/de.txt";
        }else {
            path = "/opt/de.txt";
        }
        System.out.println(path);
        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
//        ProInfo proInfo = new ProInfo();
//        proInfo.setProName("开始阶段");
//        proInfo.setStartDate(new Date());
//        proInfo.setEndDate(new Date());
//        proInfo.setRate(17);
//        oos.writeObject(proInfo);
            oos.writeObject(proInfos);

        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        List<ProInfo> u = (List<ProInfo>) ois.readObject();
        return u;
    }
    @GetMapping("pros")
    public String getData(){
        return null;
    }
    public boolean isWindow(){
        String name = System.getProperty("os.name");
        return name.toLowerCase().startsWith("window")?true:false;
    }
}
