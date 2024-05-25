package cn.edu.ncepu.sa.GameView;

import cn.edu.ncepu.sa.Model.Element;

import java.awt.*;
//废案，本来想用这个接口，下面的各种draw类实现本接口，但是想了想发现直接开一个EleDraw类更方便
public interface Idraw {
    public void draw(Graphics2D g2);
}
