import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class FeesDeposit implements ActionListener
{
	JInternalFrame f;
	JLabel receiptl,datel,coursel,batchl,namel,contactl,amtl,depl,duel,todepl;
	JComboBox coursecb,batchcb,namecb;
	JTextField receiptt,datet,contactt,amtt,dept,duet,todept;
	JButton submit,cancel;
	Calendar rightnow;
	private String st[]=new String[100];
	private int i=0;
	
	FeesDeposit()
	{
		if(ConnectMysql.jif!=null)
		{
			ConnectMysql.jif.dispose();
			ConnectMysql.jif=new JInternalFrame("",false,true,false,false);
		}
		else
		{
			ConnectMysql.jif=new JInternalFrame("",false,true,false,false);
		}
		f=ConnectMysql.jif;
		f.setSize(1000,1000);
		f.setLocation(50,50);
		f.setResizable(false);
		f.setLayout(new GridBagLayout());
		
		receiptl=new JLabel("Receipt No:");
		datel=new JLabel("Date");
		coursel=new JLabel("Course:");
		batchl=new JLabel("Batch:");
		namel=new JLabel("Student Name:");
		contactl=new JLabel("Contact No:");
		amtl=new JLabel("Fees Amt:");
		depl=new JLabel("Deposited:");
		duel=new JLabel("Due:");
		todepl=new JLabel("To Deposite:");
		
		coursecb=new JComboBox();
		try
		{
			ResultSet rs=ConnectMysql.stmt.executeQuery("Select * from course");
			
			while(rs.next())
			{
				coursecb.addItem(rs.getString("coursename"));
			}
		}
		catch(SQLException ex)
		{
			int code=ex.getErrorCode();
			System.out.println(code);
			System.out.println("Exception occured in Adding Course");
		}
		
		batchcb=new JComboBox();
		namecb=new JComboBox();
		
		coursecb.addActionListener(this);
		batchcb.addActionListener(this);
		namecb.addActionListener(this);
		
		receiptt=new JTextField(5);
		receiptt.setEditable(false);
		datet=new JTextField(12);
		datet.setEditable(false);
		contactt=new JTextField(12);
		contactt.setEditable(false);
		amtt=new JTextField(6);
		amtt.setEditable(false);
		dept=new JTextField(6);
		dept.setEditable(false);
		duet=new JTextField(6);
		duet.setEditable(false);
		todept=new JTextField(6);
		
		submit=new JButton("Submit");
		cancel=new JButton("Cancel");
		submit.addActionListener(this);
		cancel.addActionListener(this);
		
		rightnow=Calendar.getInstance();
		int dd=rightnow.get(Calendar.DATE);
		int mm=rightnow.get(Calendar.MONTH);
		int yy=rightnow.get(Calendar.YEAR);
		mm++;
		datet.setText(dd+"/"+mm+"/"+yy);
		
		todept.addKeyListener(new KeyListener()
								{
									public void keyTyped(KeyEvent e)
									{
										int c=e.getKeyChar();
										if(!((Character.isDigit(c)) || (c==KeyEvent.VK_BACK_SPACE)||(c==KeyEvent.VK_DELETE)))
										{
											e.consume();
										}
									}
									
									public void keyPressed(KeyEvent e)
									{
										
									}
									
									public void keyReleased(KeyEvent e)
									{
										
									}
								}
								
								);
		
		try
		{
			ResultSet rs=ConnectMysql.stmt.executeQuery("select receiptno from fees order by receiptno desc");
			int number;
			if(rs.next())
			{
				number=rs.getInt("receiptno");
				number++;
			}
			else
			{
				number=1;
			}
			
			receiptt.setText(""+(number));
		}
		catch(SQLException ex)
		{
			int code=ex.getErrorCode();
			System.out.println(code);
			System.out.println("Exception occured in Adding Course");
		}
		
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.insets=new Insets(10,10,10,10);
		gbc.fill=GridBagConstraints.BOTH;
		
		gbc.gridx=0;
		gbc.gridy=0;
		f.add(receiptl,gbc);
		
		gbc.gridx=1;
		gbc.gridy=0;
		f.add(receiptt,gbc);
		
		gbc.gridx=2;
		gbc.gridy=0;
		f.add(datel,gbc);
		
		gbc.gridx=3;
		gbc.gridy=0;
		f.add(datet,gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		f.add(coursel,gbc);
		
		gbc.gridx=1;
		gbc.gridy=1;
		f.add(coursecb,gbc);
		
		gbc.gridx=2;
		gbc.gridy=1;
		f.add(batchl,gbc);
		
		gbc.gridx=3;
		gbc.gridy=1;
		f.add(batchcb,gbc);
		
		gbc.gridx=0;
		gbc.gridy=2;
		f.add(namel,gbc);
		
		gbc.gridx=1;
		gbc.gridy=2;
		f.add(namecb,gbc);
		
		gbc.gridx=2;
		gbc.gridy=2;
		f.add(contactl,gbc);
		
		gbc.gridx=3;
		gbc.gridy=2;
		f.add(contactt,gbc);
		
		gbc.gridx=0;
		gbc.gridy=3;
		f.add(amtl,gbc);
		
		gbc.gridx=1;
		gbc.gridy=3;
		f.add(amtt,gbc);
		
		gbc.gridx=2;
		gbc.gridy=3;
		f.add(depl,gbc);
		
		gbc.gridx=3;
		gbc.gridy=3;
		f.add(dept,gbc);
		
		gbc.gridx=0;
		gbc.gridy=4;
		f.add(duel,gbc);
		
		gbc.gridx=1;
		gbc.gridy=4;
		f.add(duet,gbc);
		
		gbc.gridx=2;
		gbc.gridy=4;
		f.add(todepl,gbc);
		
		gbc.gridx=3;
		gbc.gridy=4;
		f.add(todept,gbc);
		
		gbc.gridx=1;
		gbc.gridy=6;
		f.add(submit,gbc);
		
		gbc.gridx=2;
		gbc.gridy=6;
		f.add(cancel,gbc);
		
		f.setVisible(true);
	}
	
	public static void main(String arg[])
	{
		FeesDeposit obj=new FeesDeposit();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==coursecb)
		{
			batchcb.removeAllItems();
			try
			{
				String selected=(String)coursecb.getSelectedItem();
				ResultSet rs=ConnectMysql.stmt.executeQuery("select course_id from course where coursename='"+selected+"'");
				rs.next();
				int id=rs.getInt("Course_id");
				
				rs=ConnectMysql.stmt.executeQuery("Select * from batch where course_id="+id);
				while(rs.next())
				{
					st[i++]=(""+rs.getInt("batch_id")+" "+rs.getDate("start_date")+"  "+rs.getTime("timing"));
					//System.out.println(st[i-1]);
				}
				for(int t=0;t<i;t++)
				{
					batchcb.addItem(st[t]);
				}
				i=0;
			}
			catch(SQLException ex)
			{
				int code=ex.getErrorCode();
				System.out.println(code);
				System.out.println("Exception occured in adding batch");
			}
		}
		
		else if(e.getSource()==batchcb)
		{
				try
				{
					namecb.removeAllItems();
					String selected=((String)(batchcb.getSelectedItem()));
					if(selected==null)
					{
						return;
					}
					
					selected=selected.substring(0,4);
					int id=Integer.parseInt(selected);
					
					ResultSet rs=ConnectMysql.stmt.executeQuery("Select * from studentrecord where batch_id="+id);
					i=0;
					while(rs.next())
					{
						st[i++]=rs.getString("name");
						contactt.setText(rs.getString("contact"));
						//System.out.println("hey i am here((((((((");
					}
					for(int t=0;t<i;t++)
					{
						namecb.addItem(st[t]);
					}
					i=0;
				}
				catch(SQLException ex)
				{
					int code=ex.getErrorCode();
					System.out.println(code);
					System.out.println("Exception occured in adding names");
				}
		}
		
		else if(e.getSource()==namecb)
		{
			try
			{
				String selected=(String)namecb.getSelectedItem();
				if(selected==null)
				{
					return;
				}
				
				String select=((String)(batchcb.getSelectedItem()));
					if(select==null)
					{
						return;
					}
					System.out.println("WE were here");
					select=select.substring(0,4);
					int bid=Integer.parseInt(select);
					
				
				ResultSet rs=ConnectMysql.stmt.executeQuery("select * from studentrecord where name='"+selected+"' And batch_id="+bid);
				rs.next();
				int sid=rs.getInt("student_id");
				contactt.setText(rs.getString("contact"));
				int fee,due,deposited;
				
				System.out.println(sid);
				
				rs=ConnectMysql.stmt.executeQuery("select * from fees where student_id="+sid+" And batch_id="+bid+" order by receiptno desc");
				
				if(rs.next())
				{
					fee=rs.getInt("fee");
					due=rs.getInt("due");
					System.out.println("WE were here in else");
				}
				else
				{
					int id=Integer.parseInt(((String)batchcb.getSelectedItem()).substring(0,4));
					
					ResultSet temp=ConnectMysql.stmt.executeQuery("select course_id from batch where batch_id="+id);
					temp.next();
					id=temp.getInt("course_id");
					
					temp=ConnectMysql.stmt.executeQuery("select fees from course where course_id="+id);
					temp.next();
					fee=temp.getInt("fees");
					due=fee;
				}
				
				deposited=fee-due;
				amtt.setText(""+fee);
				dept.setText(""+deposited);
				duet.setText(""+due);
			}
			catch(SQLException ex)
			{
				int code=ex.getErrorCode();
				System.out.println(code);
				System.out.println("Exception occured in adding fees");
			}
		}
		
		else if(e.getSource()==submit)
		{
			try
			{
				int todeposite=Integer.parseInt(todept.getText());
				if(todept.getText()==null)
				{
					JOptionPane.showMessageDialog(f,"please enter a amount to deposit");
				}
				
				int number=0;
				ResultSet rs=ConnectMysql.stmt.executeQuery("select receiptno from fees order by receiptno desc");
				if(rs.next())
				{
					number=rs.getInt("receiptno");
					number++;
				}
				else
				{
					number=1;
				}
				
				rightnow=Calendar.getInstance();
				int dd=rightnow.get(Calendar.DATE);
				int mm=rightnow.get(Calendar.MONTH);
				int yy=rightnow.get(Calendar.YEAR);
				mm++;
				String date=yy+"-"+mm+"-"+dd;
				
				String selected=(String)namecb.getSelectedItem();
				if(selected==null)
				{
					return;
				}
				int bid=Integer.parseInt(((String)batchcb.getSelectedItem()).substring(0,4));

				System.out.println("Hello");
				rs=ConnectMysql.stmt.executeQuery("select * from studentrecord where name='"+selected+"' And batch_id="+bid);
				rs.next();
				int sid=rs.getInt("student_id");
				System.out.println("Bye");
				
				int fee=Integer.parseInt(amtt.getText());
				int due=Integer.parseInt(duet.getText());
				
				if(todeposite>due)
				{
					JOptionPane.showMessageDialog(f,"TO be deposit amount can't be greater than due");
					return;
				}
				due=due-todeposite;
				
				ConnectMysql.stmt.execute("insert into fees(receiptno,receipt_date,student_id,batch_id,due,fee) values("+number+","+"'"+date+"'"+","+sid+","+bid+","+due+","+fee+")");
				
				JOptionPane.showMessageDialog(f,("your receipt number is:"+number));
				receiptt.setText(""+(number+1));
				actionPerformed(new ActionEvent(namecb,0,"Student Name"));
			}
			catch(SQLException ex)
			{
				int code=ex.getErrorCode();
				System.out.println(code);
				System.out.println("Exception occured in updating fees");
			}
		}
		
		else if(e.getSource()==cancel)
		{
			f.dispose();
		}
	}
}