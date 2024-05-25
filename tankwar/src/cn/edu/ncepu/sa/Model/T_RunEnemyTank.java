package cn.edu.ncepu.sa.Model;

public class T_RunEnemyTank implements Runnable{
    private WarData warData;
    private int width;
    private int height;
    private int speed;
    public T_RunEnemyTank(WarData warData,int width,int  height,int speed){
        this.warData=warData;
        this.width=width;
        this.height=height;
        this.speed=speed;
    }
    public void run(){
        warData.runEnemyTank( width, height, speed);
    }
}
