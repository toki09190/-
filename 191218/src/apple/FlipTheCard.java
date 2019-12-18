package apple;

import java.awt.event.*;
import java.awt.*;
import apple.Ss;
import javax.swing.*;


class JLabel2 extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel l1=new JLabel();
	JLabel l2=new JLabel();
	public JLabel2(JLabel l2,String a,int x,int y) {
		this.l1.setText(a);
		this.l2=l2;
		
		setLayout(null);
		Font f=new Font("Gothic",Font.PLAIN,20);
		l1.setFont(f);
		l2.setFont(f);
		l1.setSize(70,50);
		l2.setSize(50,50);
		l1.setLocation(x,y);
		l2.setLocation(x+70,y);
		
		add(l1);
		add(l2);
		
		setSize(700,100);
		setVisible(true);
		
		
	}
}

class TimeChecker extends JLabel implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Ss sel;
	private JLabel l;
	public TimeChecker(JLabel l,Ss sel) {
		this.l=l;
		this.sel=sel;
		
	}
	@Override
	public void run() {
		sel.time=60;
		while(sel.time>=0&&sel.reversed != 0) {
			l.setText(Integer.toString(sel.time));
			sel.time--;
			try {
				Thread.sleep(1000);
			}
			catch(InterruptedException e) {
				return;
			}
		}
	}
}

class ShowScore extends JFrame implements MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon[] AnimalList= {
			new ImageIcon("chick.png"),new ImageIcon("lemur.png"),
			new ImageIcon("fox.png"),new ImageIcon("hedgehog.png"),
	};
	public ShowScore(Ss sel) {
		setTitle("ShowScore");
		setLayout(null);
		
		JLabel scr1=new JLabel("Total Score : ");
		scr1.setFont(new Font("Gothic",Font.PLAIN,25));
		scr1.setSize(250,30);
		scr1.setLocation(85,160);
		add(scr1);
		//int total=sel.score+sel.time;
		JLabel scr2=new JLabel(Integer.toString(sel.score+sel.time+1));
		scr2.setFont(new Font("Gothic",Font.PLAIN,25));
		scr2.setSize(80,30);
		scr2.setLocation(260,160);
		add(scr2);
		
		addWindowListener(new MyWinExit());
		
		JButton end=new JButton("END");
		end.setFont(new Font("Gothic",Font.PLAIN,20));
		end.setSize(80,30);
		end.setLocation(140,200);
		end.addMouseListener(this);
		add(end);
		
		JButton img=new JButton(AnimalList[sel.select]);
		img.setSize(AnimalList[sel.select].getIconWidth(),AnimalList[sel.select].getIconHeight());
		img.setLocation(110,30);
		img.setBorderPainted(false); //테두리 없애기
		img.setContentAreaFilled(false); //배경색 안채우도록
		add(img);
		
		setSize(400,300);
		setVisible(true);
	}
	public class MyWinExit extends WindowAdapter{
		public void windowClosing(WindowEvent w) {
			System.exit(0);
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		this.setVisible(false);
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}

class G3 extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Ss sel;
	int[] save= {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}; //위치저장할 배열. 0: computer, 1: gamer
	int score=0;
	private ImageIcon[] AnimalList= {
			new ImageIcon("chick.png"),new ImageIcon("lemur.png"),
			new ImageIcon("fox.png"),new ImageIcon("hedgehog.png"),
	};
	JButton but[]=new JButton[16]; //사진이 들어갈 배열. 스레드와 쉽게 연결하기 위해 뺐당
	public G3() {}
	public G3(Ss sel) {
		
		setLayout(null);
		setSize(700,570);
		
		this.sel=sel;
		int gamer=sel.select; //사용자가 뽑은 사진 인덱스
		int x=50; //x좌표 시작 위치
		int y=30;//y좌표 시작 위치
		int randomIndex=-1;
		int a;
		
		do { //컴퓨터가 사용할 사진 뽑기
			a=(int)(Math.random()*4);
			}while(a == gamer);
		
		int com=a; //컴퓨터의 사진 인덱스
		
		for(int j=0;j<8;j++) { //초기 사진 위치 정하기()
			do{
				randomIndex=(int)(Math.random()*16);
			}while(save[randomIndex]==0);
			save[randomIndex]=0;
		}
		
		for(int i=0;i<16;i++) { //버튼달기
			if(i!=0 && i%4==0) {
				y += 130;
				x=50;
				}
			if(save[i]==0) {
			but[i] = new JButton(AnimalList[com]);
			but[i].setSize(AnimalList[com].getIconWidth(),AnimalList[com].getIconHeight());
			sel.reversed++;
			}
			else {
				but[i] = new JButton(AnimalList[gamer]);
				but[i].setSize(AnimalList[gamer].getIconWidth(),AnimalList[gamer].getIconHeight());
			}
			but[i].setLocation(x,y);
			but[i].setBorderPainted(false); //테두리 없애기
			but[i].setContentAreaFilled(false); //배경색 안채우도록
			add(but[i]);
			sel.but[i]=but[i];
			x+=150;
			}
		
		for(int i=0;i<16;i++) {
			but[i].addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JButton b=(JButton)e.getSource();
				if(b.getIcon().equals(AnimalList[com])) { //com영역을 선택했을 때 gamer꺼로 바꾸기
					b.setIcon(AnimalList[gamer]);
					sel.score++;
					sel.reversed--;
					sel.count.setText(Integer.toString(sel.score));
					}
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			
			});
		}
		setVisible(true);
		setLocation(0,50);
		
	//	SelectCom runnable2=new SelectCom(but,com,sel);
	//	Thread th2=new Thread(runnable2);
		
	//	th2.start();
	}
}
class SelectCom extends G3 implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Ss sel;
	JButton but[]=new JButton[16];
	int com;
	private ImageIcon[] AnimalList= {
			new ImageIcon("chick.png"),new ImageIcon("lemur.png"),
			new ImageIcon("fox.png"),new ImageIcon("hedgehog.png"),
	};
	public SelectCom(JButton[] but,int com,Ss sel) {
		super();
		this.sel=sel;
		for(int i=0;i<16;i++) {
			this.but[i]=but[i];
		}
		this.com=com;
	}
	@Override
	public void run() {
		int randomIndex=-1;
		while(sel.time>=0) {
			do {
			randomIndex=(int)(Math.random()*16);
			}while(but[randomIndex].getIcon().equals(AnimalList[com]));
			but[randomIndex].setIcon(AnimalList[com]);
		//	sel.reversed++;
			try {
				Thread.sleep(700);
			}
			catch(InterruptedException e) {return;}
		}
	}
	public class MyWinExit extends WindowAdapter{
		public void windowClosing(WindowEvent w) {
			System.exit(0);
		}
	}
}


class G2 extends JFrame implements MouseListener{
	Ss sel; //사용자가 선택한 동물의 인덱스가 들어있음
	JLabel count;
	ImageIcon[] AnimalList= {
			new ImageIcon("chick.png"),new ImageIcon("lemur.png"),
			new ImageIcon("fox.png"),new ImageIcon("hedgehog.png"),
	};
	JButton but[]=new JButton[16];
	
	public G2(Ss sel) {
		this.sel=sel;
		setLayout(null);
		this.count=sel.count;
		G3 g=new G3(sel);
		g.setLocation(0,50);
		add(g);
		
		JLabel timerL=new JLabel("");
		timerL.setFont(new Font("Gothic",Font.PLAIN,50));
		count.setText(Integer.toString(sel.score));
		count.setFont(new Font("Gothic",Font.PLAIN,50));
		add(new JLabel2(timerL,"Timer :",80,10));
		add(new JLabel2(count,"Score :",500,10));
		
		addWindowListener(new MyWinExit());
		
		TimeChecker runnable=new TimeChecker(timerL,sel);//타이머
		Thread th=new Thread(runnable);
		
		JButton exitButton=new JButton("EXIT");
		exitButton.setFont(new Font("Gothic",Font.PLAIN,25));
		exitButton.setSize(100,40);
		exitButton.setLocation(290,620);
		exitButton.addMouseListener(this);
		add(exitButton);
		
		setSize(700,800);
		setVisible(true);
		getContentPane().setFocusable(true);
		getContentPane().requestFocus();
		
		th.start();
		
		}
	public class MyWinExit extends WindowAdapter{
		public void windowClosing(WindowEvent w) {
			System.exit(0);
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		new ShowScore(sel);
		this.setVisible(false);
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}

class G1 extends JFrame implements MouseListener{
	Ss sel=new Ss();
	ImageIcon[] ChoiceAnimal= {
			new ImageIcon("chick.png"),new ImageIcon("lemur.png"),
			new ImageIcon("fox.png"),new ImageIcon("hedgehog.png"),
			new ImageIcon("play-button2.png"),
	};
	JButton but[]=new JButton[4];
	public G1() {
		setTitle("G1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(700,800);
		
		JLabel ll1=new JLabel("First, Choice Your Card");
		setLayout(null);
		Font f=new Font("Gothic",Font.PLAIN,30);
		ll1.setFont(f);
		ll1.setSize(400,50);
		ll1.setLocation(180,100);
		add(ll1);
		
		JLabel ll2=new JLabel("And Then, Click This Button");
		setLayout(null);
		ll2.setFont(f);
		ll2.setSize(500,50);
		ll2.setLocation(150,520);
		add(ll2);
		
	int x=50,y=220,select;
	for(int i=0;i<4;i++) { //버튼달기
			but[i] = new JButton(ChoiceAnimal[i]);
			but[i].setSize(ChoiceAnimal[i].getIconWidth(),ChoiceAnimal[i].getIconHeight());
		but[i].setLocation(x,y);
		but[i].setBorderPainted(false); //테두리 없애기
		but[i].setContentAreaFilled(false); //배경색 안채우도록
		add(but[i]);
		
		x+=150;
		}
	
	for(int i=0;i<4;i++) {
		but[i].addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b=(JButton)e.getSource();
			if(b.getIcon().equals(ChoiceAnimal[0]))  sel.select=0;
			else if(b.getIcon().equals(ChoiceAnimal[1]))  sel.select=1;
			else if(b.getIcon().equals(ChoiceAnimal[2]))  sel.select=2;
			else sel.select=3; // if(b.getIcon().equals(ChoiceAnimal[3]))
			
			b.setContentAreaFilled(true);
			b.setBackground(new Color(190,186,234));//선택되면 색이 바뀝니당
			}
		});
	}
	JButton j=new JButton(ChoiceAnimal[4]);
	j.setSize(ChoiceAnimal[4].getIconWidth(),ChoiceAnimal[4].getIconHeight());
	j.setLocation(305,430);
	j.addMouseListener(this);
	j.setContentAreaFilled(false);
	j.setBorderPainted(false);
	add(j);
	
		addWindowListener(new MyWinExit());
		setVisible(true);
	}
	public class MyWinExit extends WindowAdapter{
		public void windowClosing(WindowEvent w) {
			System.exit(0);
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		new G2(sel);
		this.setVisible(false);
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
}

class Pictures extends JPanel implements Runnable{
	ImageIcon[] firstImage= {
			new ImageIcon("bgp1.png"),new ImageIcon("bgp2.png"),new ImageIcon("bgp3.png"),new ImageIcon("bgp4.png"),
			new ImageIcon("play1.png"),new ImageIcon("play2.png"),
	};
	JButton b1=new JButton();
	JButton b2=new JButton();
	public Pictures(ImageIcon[] firstImage,JButton b1,JButton b2) {
		this.b2=b2;
		this.b1=b1;
	}
	@Override
	public void run() {
		int n=0;
		while(n<1000) {
			n++;
			if(n%4==0) b1.setIcon(firstImage[0]);
			else if(n%4==1) b1.setIcon(firstImage[1]);
			else if(n%4==2) b1.setIcon(firstImage[2]);
			else b1.setIcon(firstImage[3]);
			if(n%2==0)
				b2.setIcon(firstImage[4]);
			else
				b2.setIcon(firstImage[5]);
		try {Thread.sleep(500);}
		catch(InterruptedException e) {return;}
		
		}
	}
}
class Start1 extends JFrame implements MouseListener{ //스타트 버튼도 넣기
	ImageIcon[] firstImage= {
			new ImageIcon("bgp1.png"),new ImageIcon("bgp2.png"),new ImageIcon("bgp3.png"),new ImageIcon("bgp4.png"),
			new ImageIcon("play1.png"),new ImageIcon("play2.png"),
	};
	public Start1() {
		
		setLayout(null);
		setSize(700,800);
		
		JLabel ll=new JLabel("Flip The Crad");
			setLayout(null);
			Font f=new Font("Gothic",Font.PLAIN,40);
			ll.setFont(f);
			ll.setSize(300,50);
			ll.setLocation(210,50);
			add(ll);
			
		
		JButton b1[]=new JButton[4];
		JButton b2=new JButton(new ImageIcon("c1.png"));
		JButton b3=new JButton(new ImageIcon("play1.png"));
		b2.setSize(firstImage[0].getIconWidth(),firstImage[0].getIconHeight());
		b3.setSize(firstImage[4].getIconWidth(),firstImage[4].getIconHeight());

		b2.setLocation(200,140);
		b2.setBorderPainted(false); //테두리 없애기
		b2.setContentAreaFilled(false); //배경색 안채우도록
		add(b2);
		
		b3.setLocation(270,440);
		b3.setBorderPainted(false); //테두리 없애기
		b3.setContentAreaFilled(false); //배경색 안채우도록
		add(b3);
		addWindowListener(new MyWinExit());
		setVisible(true);
		
		
		Thread th=new Thread(new Pictures(firstImage,b2,b3));
		
		getContentPane().setFocusable(true);
		getContentPane().requestFocus();
		
		th.start();
		b3.addMouseListener(this);
		}
	public class MyWinExit extends WindowAdapter{
		public void windowClosing(WindowEvent w) {
			System.exit(0);
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		new G1();
		this.setVisible(false);
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}

public class FlipTheCard {
	public static void main(String[] args) {
		new Start1();
	}

}


