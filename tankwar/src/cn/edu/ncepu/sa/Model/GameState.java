package cn.edu.ncepu.sa.Model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.HashSet;

public class GameState {
    public WarData warData;
    public Tank utank;
    public HashSet<Element> elements;
    public GameState(WarData warData){
        this.warData=warData;
        this.utank=warData.userTank;
        this.elements=warData.elements;
    }
    public GameState(){}
    public String toJsonString (){

        return JSON.toJSONString(this, SerializerFeature.WriteClassName);
    }
    public static GameState fromJsonString(String jsonString){
        ParserConfig.getGlobalInstance().addAccept("java.awt.Rectangle");
        GameState g=JSON.parseObject(jsonString,GameState.class);

        g.warData.elements=g.elements;
       // System.out.println(g.warData.elements+"over");
       // System.out.println(g.warData.elements.size());
        for(Element el: g.elements){
            if(el instanceof Tank){
                Tank ta=(Tank)el;
                    System.out.println(ta.name);
                if(ta.name.equals("Player")){
                    //System.out.println(ta.killNum+"ccc");
                    g.utank=ta;
                }
            }
        }
        if(g.utank==null)
        {
            System.out.println("aaa");
        }
        g.warData.userTank=g.utank;
        if(g.warData.userTank==null)
        {
            System.out.println("bbb");
        }
        System.out.println(g.warData.userTank.x);
        return g;
    }
}
