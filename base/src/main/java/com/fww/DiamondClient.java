package com.fww;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 范文武
 * @date 2018/04/29 12:13
 */
public class DiamondClient extends BasePropertyPlaceholderConfigurer {
    private static DiamondClient diamondClient = new DiamondClient();

    public static DiamondClient getInstance() {
        return diamondClient;
    }

    public void init(){
        List<Map<String,String>> list = new ArrayList<>();

        Map<String,String> map1 = new HashMap<>(2);
        map1.put("group", "loan");
        map1.put("dataId", "jdbc");
        list.add(map1);

        this.loadMultConfigFromDiamond(list);
    }
}
