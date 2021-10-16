import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Random;

public class WhatAmIDo {
	Point center = new Point();
	LinkedList<Point> chunki = new LinkedList<Point>();
	public String toString() {
		return "Chunks found: " + chunki.size() + ", Center chunk: " + center.x + ", " + center.y + ".";
	}
}

class Warn extends Main{
	public void NOT_VANILLA(String text){
		JFrame jFrame = new JFrame("Error");
		jFrame.setBounds(500,700,300,200);
		jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		jFrame.setResizable(false);
		JPanel jPanel = new JPanel();
		jPanel.setLayout(null);
		JLabel label13 = new JLabel("Not Vanilla!"+text);
		JLabel label14 = new JLabel("code:0x0001BF52 for find unexpected data");
		label13.setBounds(0,0,300,40);
		label14.setBounds(0,40,300,40);
		JButton jButton = new JButton("Close&Retype");
		jButton.setBorder(BorderFactory.createEtchedBorder());
		jButton.setBounds(50,90,200,30);
		jButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jFrame.dispose();}});
		jPanel.add(label13);jPanel.add(label14);jPanel.add(jButton);
		jPanel.setVisible(true);jFrame.add(jPanel);jFrame.setVisible(true);
	}
	public void WrongType(String which,String type,Object obj){
		JFrame jFrame = new JFrame("Error");
		jFrame.setBounds(300,500,360,160);
		jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		jFrame.setResizable(false);
		JPanel jPanel = new JPanel();
		jPanel.setLayout(null);
		JLabel label13 = new JLabel(which+".TypeError:Except a ["+obj.getClass()+"]");
		JLabel label14 = new JLabel("code:0x0001BF52 Find a ["+type+"]");
		label13.setBounds(20,0,480,40);
		label14.setBounds(20,40,300,40);
		JButton jButton = new JButton("Close&RandomSetNumber");
		jButton.setBorder(BorderFactory.createEtchedBorder());
		jButton.setBounds(50,90,200,30);
		jButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Random generation = new Random(System.nanoTime());
				randomNum1 = generation.nextInt(5140);
				jFrame.dispose();}});
		jPanel.add(label13);jPanel.add(label14);jPanel.add(jButton);
		jPanel.setVisible(true);jFrame.add(jPanel);jFrame.setVisible(true);
	}
}