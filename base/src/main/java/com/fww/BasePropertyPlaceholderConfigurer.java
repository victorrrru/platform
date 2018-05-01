package com.fww;

import com.taobao.diamond.manager.DiamondManager;
import com.taobao.diamond.manager.ManagerListener;
import com.taobao.diamond.manager.impl.DefaultDiamondManager;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.concurrent.Executor;

/**
 * @author Administrator
 * @date 2018/04/29 12:17
 */
public class BasePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    private long timeOut = 5000L;
    private static Properties properties = new Properties();
    private static String DEF_DEMAIN_LIST = "a.b.c";
    private static int DEF_PORT = 8080;

    public BasePropertyPlaceholderConfigurer() {
    }

    public void loadOneConfigFromDiamond(String group, String dataId) {
        DiamondManager manager = new DefaultDiamondManager(group, dataId, new ManagerListener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println(configInfo + "--------------------------");
                BasePropertyPlaceholderConfigurer.this.loadConfig(configInfo);
            }

            @Override
            public Executor getExecutor() {
                return null;
            }
        }, DEF_DEMAIN_LIST);
        manager.getDiamondConfigure().setPort(DEF_PORT);
        String configInfo = manager.getAvailableConfigureInfomation(this.timeOut);
        this.loadConfig(configInfo);
    }

    public Properties loadModuleConfigFromDiamond(String group, String dataId) {
        new Properties();
        DiamondManager manager = new DefaultDiamondManager(group, dataId, new ManagerListener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                BasePropertyPlaceholderConfigurer.this.loadModuleConfig(configInfo);
            }

            @Override
            public Executor getExecutor() {
                return null;
            }
        }, DEF_DEMAIN_LIST);
        manager.getDiamondConfigure().setPort(DEF_PORT);
        String configInfo = manager.getAvailableConfigureInfomation(this.timeOut);
        Properties modProperties = this.loadModuleConfig(configInfo);
        return modProperties;
    }

    public void loadMultConfigFromDiamond(List<Map<String, String>> list) {
        if(null != list && list.size() >= 1) {
            String group = "";
            String dataId = "";
            Iterator var4 = list.iterator();

            while(var4.hasNext()) {
                Map<String, String> map = (Map)var4.next();
                group = (String)map.get("group");
                dataId = (String)map.get("dataId");
                this.loadOneConfigFromDiamond(group, dataId);
            }

        }
    }

    private Properties loadModuleConfig(String configInfo) {
        Properties modProperties = new Properties();

        try {
            modProperties.load(new StringReader(configInfo));
            return modProperties;
        } catch (IOException var4) {
            throw new RuntimeException("装载properties失败：" + configInfo, var4);
        }
    }

    private void loadConfig(String configInfo) {
        try {
            properties.load(new StringReader(configInfo));
        } catch (IOException var3) {
            throw new RuntimeException("装载properties失败：" + configInfo, var3);
        }

        this.setProperties(properties);
    }

    public String getConfig(String key) {
        return null != key && !"".equals(key)?properties.getProperty(key):"";
    }

    public Set getPropertiesAllKey(Properties modProperties) {
        return modProperties.keySet();
    }

    public static Properties getProperties() {
        return properties;
    }
}
