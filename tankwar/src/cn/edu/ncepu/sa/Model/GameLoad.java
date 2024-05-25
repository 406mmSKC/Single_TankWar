package cn.edu.ncepu.sa.Model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class GameLoad {
    public static GameState loadGame(String filePath)  {
        // 从文件读取JSON字符串
        byte[] encoded = new byte[0];
        try {
            encoded = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath));
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("you have no save file");
            System.exit(0);
        }
        String jsonString = new String(encoded, StandardCharsets.UTF_8);
        // 使用fastjson从JSON字符串恢复对象
        //System.out.println(jsonString);
        GameState g=GameState.fromJsonString(jsonString);
        //System.out.println(g.warData.userTank.hp);
        return g;
    }

}
