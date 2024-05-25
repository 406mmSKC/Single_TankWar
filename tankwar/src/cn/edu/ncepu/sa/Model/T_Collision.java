package cn.edu.ncepu.sa.Model;

public class T_Collision implements Runnable{
    private  WarData warData;
    public T_Collision(WarData warData){
        this.warData=warData;
    }
    public void run(){
        warData.CollisionDetection();
    }
}
