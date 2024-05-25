package cn.edu.ncepu.sa.Control;

import cn.edu.ncepu.sa.GameView.GameView;
import cn.edu.ncepu.sa.Model.GameLoad;
import cn.edu.ncepu.sa.Model.GameSave;
import cn.edu.ncepu.sa.Model.GameState;
import cn.edu.ncepu.sa.Model.WarData;

import java.nio.file.Path;
import java.nio.file.Paths;

public class GameSave_and_Load {
    public static void clickLoad(){

        GameState gameState = GameLoad.loadGame("C:/Users/Lenovo/Desktop/gsave/game_save.json");
        WarData warData=gameState.warData;
        //System.out.println(warData.userTank.hp);
        GameView win = new GameView(warData);
        System.out.println("Gameload over i will enter warcontrol");
        // 构造控制器组件
        WarControl warControl = new WarControl(win,warData);

        // 依据用户输入刷新显示，关联View层和数据层
        warControl.StartWar(win,warData);
    }
    public static void clickSave(WarData warData){
        GameState gameState=new GameState(warData);
        String filep=new String("C:\\Users\\Lenovo\\Desktop\\gsave\\game_save.json");
        GameSave.saveGame(gameState,filep); // 保存游戏状态
    }
}
