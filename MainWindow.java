import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.border.*;
//import javax.swing.Border.*;

class MainWindow implements ActionListener
{
	
	JFrame f;
	JPanel north,west,south,nsouth,ncenter,nswest,nscenter,nseast,wcenter,wsouth,center,wnorth;
	JButton admin,neu,view,fees,logout,exit;
	JLabel wintitle,date,time,wsl,wnl;
	JDialog d;
	
	MainWindow()
	{
		if(ConnectMysql.dp!=null)
		{
			ConnectMysql.dp.removeAll();
			//ConnectMysql.dp=new JDesktopPane(); 
		}
		else
		{
			ConnectMysql.dp=new JDesktopPane();
		}
		
		f=new JFrame();
		/*Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = ((int) tk.getScreenSize().getWidth());
		int ySize = ((int) tk.getScreenSize().getHeight());
		f.setSize(xSize,ySize);*/
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		f.setLocation(0,0);
		
		north=new JPanel();
		west=new JPanel();
		south=new JPanel();
		center=new JPanel();
		
		admin=new JButton("Administrator");
		neu=new JButton("New Entry");
		view=new JButton("View Record");
		fees=new JButton("Fees Deposit");
		admin=new JButton("Administrator");
		logout=new JButton("Logout");
		exit=new JButton("Exit");
		
		north.setLayout(new BorderLayout());
		wintitle=new JLabel("Welcome");
		wintitle.setFont(new Font("Arial",Font.BOLD,50));
		ncenter=new JPanel();
		wintitle.setForeground(Color.RED);
		ncenter.setBackground(new Color(142,139,5));
		ncenter.add(wintitle,"Center");
		
		nsouth=new JPanel();
		nsouth.setLayout(new BorderLayout());
		nswest=new JPanel();
		nscenter=new JPanel();
		nscenter.setBackground(Color.BLUE);
		nseast=new JPanel();
		
		date=new JLabel();
		time=new JLabel();
		nswest.setBackground(new Color(182,190,140));
		nseast.setBackground(new Color(182,190,140));
		Timer.startTime(time,date);
		
		nswest.add(date);
		nseast.add(time);
		
		nsouth.add(nswest,"West");
		nsouth.add(nseast,"East");
		nsouth.add(nscenter,"Center");
		nsouth.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		north.add(ncenter,"Center");
		north.add(nsouth,"South");
		//north.setBackground(Color.ORANGE);
		
		wnorth=new JPanel();
		wcenter=new JPanel();
		wsouth=new JPanel();
		
		wnl=new JLabel("   ");
		wnl.setFont(new Font("Arial",Font.BOLD,72));
		wnorth.add(wnl);
		wnorth.setBackground(Color.ORANGE);
		
		wsl=new JLabel("   ");
		wsl.setFont(new Font("Arial",Font.BOLD,72));
		wsouth.add(wsl);
		wsouth.setBackground(Color.ORANGE);
		
		west.setLayout(new BorderLayout());
		GridLayout dl=new GridLayout(6,1);
		wcenter.setLayout(dl);
		dl.setVgap(2);
		dl.setHgap(2);
		wcenter.add(admin);
		wcenter.add(neu);
		wcenter.add(view);
		wcenter.add(fees);
		wcenter.add(logout);
		wcenter.add(exit);
		wcenter.setBackground(Color.ORANGE);
		
		west.add(wcenter);
		west.add(wsouth,"South");
		west.add(wnorth,"North");
		
		admin.addActionListener(this);
		neu.addActionListener(this);
		view.addActionListener(this);
		fees.addActionListener(this);
		logout.addActionListener(this);
		exit.addActionListener(this);
	
		center.setLayout(new BorderLayout());
		//center.add(ConnectMysql.dp);
		
		south.setBackground(Color.ORANGE);
		center.setBackground(Color.GRAY);
		
		if(!(ConnectMysql.admin))
		{
			admin.setEnabled(false);
		}
		
		f.add(north,"North");
		f.add(south,"South");
		f.add(west,"West");
		f.add(center,"Center");
		f.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==admin)
		{
			if(d!=null)
			{
				d.dispose();
			}
	
			d=new JDialog(f);
			d.setUndecorated(true);
			Point p=admin.getLocation();
			Dimension dim=admin.getSize();
			Point pn1=f.getLocation();
			Point pn2=wcenter.getLocation();
			Point pn3=west.getLocation();
			d.setLocation(pn3.x+pn2.x+pn1.x+p.x+dim.width+10,pn3.y+pn2.y+pn1.y+p.y+dim.height-38);
			
			JButton b[]=new JButton[5];
			
			d.setSize(b.length*70,150);
			
			b[0]=new JButton("Add Course");
			b[1]=new JButton("Add New Batch");
			b[2]=new JButton("Fees Record");
			b[3]=new JButton("Add New User");
			b[4]=new JButton("Close");
			
			d.setLayout(new GridLayout(5,1));
			
			for(int i=0;i<b.length;i++)
			{
				b[i].addActionListener(this);
				d.add(b[i]);
			}
			
			d.setVisible(true);
		}
		
		else if(e.getSource()==neu)
		{
			if(d!=null)
			{
				d.dispose();
			}
			d=new JDialog((JFrame)f);
			d.setUndecorated(true);
			Point p=neu.getLocation();
			Dimension dim=neu.getSize();
			
			Point pn1=f.getLocation();
			Point pn2=wcenter.getLocation();
			Point pn3=west.getLocation();
			d.setLocation(pn3.x+pn2.x+pn1.x+p.x+dim.width+10,pn3.y+pn2.y+pn1.y+p.y+dim.height-38);
			
			JButton b[]=new JButton[3];
			d.setSize(b.length*70,150);
			
			b[0]=new JButton("Entry");
			b[1]=new JButton("Query");
			b[2]=new JButton("Close");
			
			d.setLayout(new GridLayout(3,1));
			
			for(int i=0;i<b.length;i++)
			{
				b[i].addActionListener(this);
				d.add(b[i]);
			}
			d.setVisible(true);
		}
		
		else if(e.getSource()==view)
		{
			if(d!=null)
			{
				d.dispose();
			}
			
			d=new JDialog((JFrame)f);
			d.setUndecorated(true);
			Point p=view.getLocation();
			Dimension dim=view.getSize();
			
			Point pn1=f.getLocation();
			Point pn2=wcenter.getLocation();
			Point pn3=west.getLocation();
			d.setLocation(pn3.x+pn2.x+pn1.x+p.x+dim.width+10,pn3.y+pn2.y+pn1.y+p.y+dim.height-38);
			JButton b[]=new JButton[3];
			
			d.setSize(b.length*70,150);
			
			d.setLayout(new GridLayout(3,1));
			b[0]=new JButton("View Student Record");
			b[1]=new JButton("View Query Record");
			b[2]=new JButton("Close");
		
			for(int i=0;i<b.length;i++)
			{
				b[i].addActionListener(this);
				d.add(b[i]);
			}
			
			d.setVisible(true);
		}
		
		else if(e.getActionCommand()=="Close")
		{
			d.dispose();
		}
		
		else if(e.getActionCommand()=="Add Course")
		{
			AddCourse obj=new AddCourse();
			center.add(ConnectMysql.jif);
			d.dispose();
		}
		
		else if(e.getActionCommand()=="Add New Batch")
		{
			AddBatch obj=new AddBatch();
			center.add(ConnectMysql.jif);
			d.dispose();
		}
		
		else if(e.getActionCommand()=="Add New User")
		{
			AddUser obj=new AddUser();
			center.add(ConnectMysql.jif);
			d.dispose();
		}
		
		else if(e.getActionCommand()=="Entry")
		{
			StudentEntry obj=new StudentEntry();
			center.add(ConnectMysql.jif);
			d.dispose();
		}
		
		else if(e.getActionCommand()=="Query")
		{
			StudentQuery obj=new StudentQuery();
			center.add(ConnectMysql.jif);
			d.dispose();
		}
		
		else if(e.getActionCommand()=="View Student Record")
		{
			ViewStudentRecord obj=new ViewStudentRecord();
			center.add(ConnectMysql.jif);
			d.dispose();
		}
	
		else if(e.getActionCommand()=="View Query Record")
		{
			ViewStudentQuery obj=new ViewStudentQuery();
			ViewStudentQuery.location=this.center;
			center.add(ConnectMysql.jif);
			d.dispose();
		}
		
		else if(e.getSource()==fees)
		{
			FeesDeposit obj=new FeesDeposit();
			center.add(ConnectMysql.jif);
			if(d!=null)
			{
				d.dispose();
			}
		}
		
		else if(e.getSource()==logout)
		{
			LoginWindow obj=new LoginWindow();
			f.dispose();
			
			if(d!=null)
			{
				d.dispose();
			}
		}
		
		else if(e.getSource()==exit)
		{
			System.exit(0);
		}
	}
	
	public static void main(String arg[])
	{
		MainWindow obj=new MainWindow();
	}
}