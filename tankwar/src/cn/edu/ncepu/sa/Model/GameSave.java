package cn.edu.ncepu.sa.Model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class GameSave {
    public static void saveGame(GameState gameState, String filePath) {
        //byte[] bytes = ObjectAndByteUtils.toByteArray();
        String jsonString = gameState.toJsonString();
        // 将JSON字符串写入文件
        // 这里使用Java的Files类作为示例，你也可以使用其他方式
        try {
            java.nio.file.Files.write(java.nio.file.Paths.get(filePath), jsonString.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("don't find path");
            System.exit(0);
        }
    }
}
