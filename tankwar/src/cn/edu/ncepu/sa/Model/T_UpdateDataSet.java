package cn.edu.ncepu.sa.Model;

public class T_UpdateDataSet implements Runnable{
        private WarData warData;
        public T_UpdateDataSet(WarData warData){
            this.warData=warData;
        }
        public void run(){
            warData.updateDataSet();
        }
}
