import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
/*
	Program Name:Minecraft Slime-Chunk Finder Application

	info:The night of 2021/10/14,one of my friend send a slime-chunk-finder in the Gunpowder Server.
		 Yet it does not use a Scanner or textBox to input data,so I decide to enhance it by
		 	write a swing UI for using it conveniently and visually.
	————————————————————————————————————————————————————————————————————————————————————————————————
	You may have found some codes are inelegant and non-standard,yet why do like that?
	To making debug the code easier,some parts of changeless codes are compressed.
	  ===============================
	||(Like "[JFrameName].add(comp)" ||
	||"[JPanelName].add(comp)"       ||
	||and others					 ||
	  ===============================
	Anyway,I have to do that,for easing debug-tasks.
	Rue~

	                                                         by KKoishi_/514
	                                                         2021/10/15
 */

public class Main {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		final long[] seed = {114514L};
		Point center = new Point(0, 0);
		final int[] distance = {1000};
		List<Objects> list = new ArrayList<>();//What am I doing?
		JFrame GUI = new JFrame("SlimeChunkFinder");
		GUI.setBounds(500,400,600,400);
		GUI.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();panel.setLayout(null);
		JLabel label = new JLabel("seed");
		JTextField textField = new JTextField("");
		JButton button_seed = new JButton("confirm");
		label.setBounds(10,10,70,30);
		textField.setBounds(90,10,300,45);
		button_seed.setBounds(450,15,100,30);
		button_seed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Objects.equals(textField.getText(), "")) tips_null("seed","114514");
				else {
				seed[0] = Integer.parseInt(textField.getText());
				Tips_SeedInputSuccess(seed[0]);}
			}
		});
		panel.add(label);panel.add(textField);panel.add(button_seed);
		JLabel label2 = new JLabel("centerLoc——————————————————————————");
		JLabel label4 = new JLabel("X:");
		JTextField text_x = new JTextField();
		JLabel label3 = new JLabel("Y:");
		JTextField text_y = new JTextField();
		JButton button_loc = new JButton("Confirm");
		label2.setBounds(10,70,500,30);
		label4.setBounds(10,110,30,30);
		label3.setBounds(210,110,40,30);
		text_x.setBounds(50,110,130,40);
		text_y.setBounds(260,110,130,40);
		button_loc.setBounds(450,100,100,30);
		button_loc.addActionListener(e -> {
			if (Objects.equals(text_x.getText(),"")||Objects.equals(text_y.getText(),""))
				tips_null("X/Y","0");
			else if (Objects.equals(text_x.getText(),"")&&Objects.equals(text_y.getText(),""))
				tips_null("(x,y)","(0,0)");
			else {
				center.x=Integer.parseInt(text_x.getText());
				center.y=Integer.parseInt(text_y.getText());
				Tips_PointInputSuccess(center.x, center.y);
			}
		});
		button_loc.setBorder(BorderFactory.createEtchedBorder());
		panel.add(label2);panel.add(label3);panel.add(label4);
		panel.add(text_x);panel.add(text_y);panel.add(button_loc);
		JLabel label1 = new JLabel("distance");
		JTextField text_distance = new JTextField();
		JButton button_distance = new JButton("Confirm");
		label1.setBounds(10,200,80,30);
		text_distance.setBounds(100,190,290,50);
		button_distance.setBounds(450,200,100,30);
		button_distance.setBorder(BorderFactory.createEtchedBorder());
		button_distance.addActionListener(e -> {
			if (Objects.equals(text_distance.getText(),"")) tips_null("distance","1000");
			else {
				distance[0] = Integer.parseInt(text_distance.getText());
				Tips_DistanceInputSuccess(distance[0]);
			}
		});
		panel.add(label1);panel.add(text_distance);panel.add(button_distance);
		JButton cal = new JButton("Start Calculation");
		cal.setBounds(220,280,140,70);
		cal.setBackground(Color.PINK);
		cal.setBorder(BorderFactory.createLineBorder(Color.GRAY,1,true));
		cal.addActionListener(e -> {
			long start = System.currentTimeMillis();
			cal(seed[0], center, distance[0]);
			long cost = new timer().end(start);
			time(cost);
		});
		panel.add(cal);
		JButton info = new JButton("Info");
		info.setBounds(80,295,70,40);
		info.setBorder(BorderFactory.createEtchedBorder());
		info.addActionListener(e -> info());
		panel.add(info);
		JButton exit = new JButton("EXIT");
		exit.setBounds(450,295,70,40);
		exit.setBorder(BorderFactory.createEtchedBorder());
		exit.addActionListener(e -> GUI.dispose());
		panel.add(exit);GUI.setResizable(false);
		panel.setVisible(true);GUI.add(panel);GUI.setVisible(true);
	}
	public static void cal(long seed,Point center,int distance){
		Najboljsi naj = new Najboljsi();
		center.x = blockToChunk(center.x);
		center.y = blockToChunk(center.y);
		distance = blockToChunk(distance);

		LinkedList<Point> chunki = new LinkedList<Point>();

		for (int cX = center.x - distance; cX <= center.x + distance; cX++) {
			for (int cY = center.y - distance; cY <= center.y + distance; cY++) {

				chunki.clear();

				for (int x = cX - 7; x <= cX + 7; x++) {
					for (int y = cY - 7; y <= cY + 7; y++) {

						Point razlika = new Point(x - cX, y - cY);
						long razdaljaKvadrat = razlika.x * razlika.x + razlika.y * razlika.y;

						if (razdaljaKvadrat > 1 && razdaljaKvadrat < 53 && isSlimeChunk(seed, x, y)) {
							chunki.add(new Point(x, y));
						}
					}
				}

				if (chunki.size() > naj.chunki.size()) {
					naj.center.x = cX;
					naj.center.y = cY;
					naj.chunki = (LinkedList<Point>) chunki.clone();
				}

			}
		}

		Point p = chunkCenter(naj.center);
		String str = naj.toString() + " Center block: " + p.x + ", " + p.y + ". All chunks:";
		for (Point c : naj.chunki) {
			str += "\n" + c.x + ", " + c.y;
		}
		System.out.println(str);
	}
	public static int blockToChunk(int block) {
		return (int) Math.floor((double) block / 16);
	}
	public static Point chunkCenter(Point chunk) {
		Point p = new Point(chunk.x * 16 + 8, chunk.y * 16 + 8);
		return p;
	}
	public static boolean isSlimeChunk(long seed, int xPosition, int zPosition) {
		
		Random rnd = new Random(seed +
                (long) (xPosition * xPosition * 0x4c1906) +
                (long) (xPosition * 0x5ac0db) + 
                (long) (zPosition * zPosition) * 0x4307a7L +
                (long) (zPosition * 0x5f24f) ^ 0x3ad8025f);
		return rnd.nextInt(10) == 0;
			
	}
	public static void Tips_SeedInputSuccess(long seed){
		JFrame tip1 = new JFrame("TIPS");
		JPanel panel1 = new JPanel();
		tip1.setBounds(600,450,300,100);
		tip1.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		panel1.setLayout(null);
		tip1.setResizable(false);
		JLabel label = new JLabel();
		JButton button = new JButton("Confirm");
		label.setText("The seed is set to:"+seed+".");
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setBounds(50,10,200,50);
		button.setBounds(110,70,80,30);
		button.addActionListener(e -> tip1.dispose());
		panel1.add(label);
		panel1.add(button);

		panel1.setVisible(true);
		tip1.add(panel1);
		tip1.setVisible(true);
	}
	public static void Tips_PointInputSuccess(int x,int y){
		JFrame tip1 = new JFrame("TIPS");
		JPanel panel1 = new JPanel();
		tip1.setBounds(600,450,300,100);
		tip1.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		panel1.setLayout(null);
		tip1.setResizable(false);
		JLabel label = new JLabel();
		JButton button = new JButton("Confirm");
		label.setText("The point is set to:("+x+","+y+").");
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setBounds(50,10,200,50);
		button.setBounds(110,70,80,30);
		button.addActionListener(e -> tip1.dispose());
		panel1.add(label);
		panel1.add(button);

		panel1.setVisible(true);
		tip1.add(panel1);
		tip1.setVisible(true);
	}
	public static void Tips_DistanceInputSuccess(int distance) {
		JFrame tip1 = new JFrame("TIPS");
		JPanel panel1 = new JPanel();
		tip1.setBounds(600,450,300,100);
		tip1.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		panel1.setLayout(null);
		tip1.setResizable(false);
		JLabel label = new JLabel();
		JButton button = new JButton("Confirm");
		label.setText("The distance is set to:"+distance+".");
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setBounds(50,10,200,50);
		button.setBounds(110,70,80,30);
		button.addActionListener(e -> tip1.dispose());
		panel1.add(label);
		panel1.add(button);

		panel1.setVisible(true);
		tip1.add(panel1);
		tip1.setVisible(true);
	}
	public static void tips_null(String text,String num) {
		JFrame tip2 = new JFrame("Warning");
		JPanel panel2 = new JPanel();
		tip2.setBounds(600,450,300,150);
		tip2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		panel2.setLayout(null);
		tip2.setResizable(false);
		JLabel label = new JLabel();
		JButton button = new JButton("Confirm");
		label.setText("The "+text+" is empty!DefaultValue is "+num+".");
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setBounds(0,10,300,50);
		button.setBounds(110,70,80,30);
		button.addActionListener(e -> tip2.dispose());
		panel2.add(label);
		panel2.add(button);

		panel2.setVisible(true);
		tip2.add(panel2);
		tip2.setVisible(true);
	}
	public static void resultOutput(String str) {
		//NEXT TOD0:FINISH THIS F**KING SWING
	}
	public static void time(long number){
		JFrame gui = new JFrame("Timer");JPanel panel = new JPanel();panel.setLayout(null);
		gui.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);gui.setResizable(false);
		gui.setBounds(500,400,250,150);
		JLabel label11 = new JLabel("Run cost:"+number+"ms");
		JButton close = new JButton("Close");
		close.addActionListener(e -> gui.dispose());
		label11.setBounds(0,0,200,70);
		close.setBounds(75,75,150,25);
		panel.add(label11);panel.add(close);panel.setVisible(true);gui.add(panel);gui.setVisible(true);
	}
	public static void info() {
		JFrame tip1 = new JFrame("Info");JPanel panel1 = new JPanel();
		tip1.setBounds(600,450,400,230);
		tip1.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		panel1.setLayout(null);tip1.setResizable(false);
		JLabel label = new JLabel();JLabel label5 = new JLabel();JLabel label6 = new JLabel();
		JLabel label7 = new JLabel();JLabel label8 = new JLabel();
		JLabel label9 = new JLabel("SlimeChunkFinder INFO  RUA CCS_Covenant&Youmiel");
		JLabel label10 = new JLabel("————————————————————————————");
		JButton button = new JButton("Close");
		label.setText("First input the seed.(use /seed or !!seed and so on");
		label5.setText("Next input the other data.(distance is used define the range)");
		label6.setText("Final click the button \"Confirm\".");
		label7.setText("This java programme is enhanced by KKoishi_(add UI)");
		label8.setText("github:https:\\\\github.com\\Koishi-Satori");
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setBounds(25,50,350,20);
		label5.setBounds(25,70,350,20);
		label6.setBounds(25,90,350,20);
		label7.setBounds(25,110,350,20);
		label8.setBounds(25,130,350,20);
		label9.setBounds(25,10,350,20);
		label10.setBounds(25,30,350,20);
		button.setBounds(150,150,80,30);
		button.addActionListener(e -> tip1.dispose());
		panel1.add(label);panel1.add(label5);panel1.add(label6);panel1.add(label7);
		panel1.add(label8);panel1.add(label9);panel1.add(label10);panel1.add(button);
		panel1.setVisible(true);tip1.add(panel1);tip1.setVisible(true);
	}
}