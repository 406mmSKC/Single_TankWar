package cn.edu.ncepu.sa.Control;

import cn.edu.ncepu.sa.GameView.GameEndPanel;
import cn.edu.ncepu.sa.GameView.GameView;
import cn.edu.ncepu.sa.Model.ThreadPool;
import cn.edu.ncepu.sa.Model.WarData;

public class GameStart_and_End {

        //本来是想用事件监听器的，但是太麻烦了
        public static void  newStart(){
            WarData warData = new WarData();


            //构造显示组件，并告知显示组件要显示的数据，非单例类
            GameView win = new GameView(warData);

            // 构造控制器组件
            WarControl warControl = new WarControl();

            // 依据用户输入刷新显示，关联View层和数据层
            warControl.StartWar(win, warData);
        }


        public  static void settleGame(WarData warData, GameView win, ThreadPool threadPool){
            int winner=warData.isEnd();
            //首先检验是否某一方全部被消灭
            if(winner==-2){
                String title=new String("Score Draw!");

                long currTime=System.currentTimeMillis();
                int intervalEnd=(int)(currTime-win.panel.gameStartTime);
                String gameTime=String.format("%2d:%2d:%2d",intervalEnd/3600000,(intervalEnd/60000)%60,(intervalEnd/1000)%60);

                //GameEndPanel gameEndPanel=new GameEndPanel(title,gameTime,warData.getUserKillNum());
                win.setVisible(false);
                threadPool.executorService.shutdown();
                win.dispose();
                GameEndPanel gameEndPanel=new GameEndPanel(title,gameTime,warData.getUserKillNum());
            }
            else if(winner!=-1){
                String winTeam=new String();
                if(winner==1){//如果添加多个阵营，这里应该添加一些else if
                    winTeam="Blue Team";
                }
                else {
                    winTeam="Red Team";
                }
                String title=new String(winTeam+" wins the game!");

                long currTime=System.currentTimeMillis();
                int intervalEnd=(int)(currTime-win.panel.gameStartTime);
                String gameTime=String.format("%2d:%2d:%2d",intervalEnd/3600000,(intervalEnd/60000)%60,(intervalEnd/1000)%60);

                //GameEndPanel gameEndPanel=new GameEndPanel(title,gameTime,warData.getUserKillNum());
                win.setVisible(false);
                threadPool.executorService.shutdown();
                win.dispose();
                GameEndPanel gameEndPanel=new GameEndPanel(title,gameTime,warData.getUserKillNum());
            }
        }
    }

