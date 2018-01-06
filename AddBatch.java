import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class AddBatch implements ActionListener,KeyListener
{
	JInternalFrame f;
	JLabel namel,datel,timel;
	JTextField ddt,mmt,yyt,mt,ht;
	JComboBox namecb;
	JPanel datep,timep;
	JButton submit,cancel;
	
	AddBatch()
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
		f.setSize(500,300);
		f.setLocation(300,300);
		f.setResizable(false);
		f.setLayout(new GridBagLayout());
		
		namel=new JLabel("Course Name:");
		datel=new JLabel("Batch Date:");
		timel=new JLabel("Batch Timing:");
		
		ddt=new JTextField(2);
		mmt=new JTextField(2);
		yyt=new JTextField(4);
		//st=new JTextField(2);
		mt=new JTextField(2);
		ht=new JTextField(2);
		//user.setSelected();
		
		ddt.addKeyListener(this);
		mmt.addKeyListener(this);
		yyt.addKeyListener(this);
		mt.addKeyListener(this);
		ht.addKeyListener(this);
		
		namecb=new JComboBox();
		
		try
		{
			ResultSet rs=ConnectMysql.stmt.executeQuery("select coursename from course");
			
			while(rs.next())
			{
				namecb.addItem(rs.getString("coursename"));
			}
		}
		catch(SQLException ex)
		{
			int code=ex.getErrorCode();
			System.out.println(code);
			System.out.println("Exception occured");
		}
		
		datep=new JPanel();
		datep.add(ddt);
		datep.add(mmt);
		datep.add(yyt);
		
		timep=new JPanel();
		timep.add(ht);
		timep.add(mt);
		
		submit=new JButton("Submit");
		cancel=new JButton("Cancel");
		
		submit.addActionListener(this);
		cancel.addActionListener(this);
								
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.insets=new Insets(10,10,10,10);
		gbc.fill=GridBagConstraints.BOTH;
		
		gbc.gridx=0;
		gbc.gridy=0;
		f.add(namel,gbc);
		
		gbc.gridx=1;
		gbc.gridy=0;
		gbc.gridwidth=2;
		f.add(namecb,gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.gridwidth=1;
		f.add(datel,gbc);
		
		gbc.gridx=1;
		gbc.gridy=1;
		gbc.gridwidth=2;
		f.add(datep,gbc);
		
		gbc.gridx=0;
		gbc.gridy=2;
		gbc.gridwidth=1;
		f.add(timel,gbc);
		
		gbc.gridx=1;
		gbc.gridy=2;
		gbc.gridwidth=2;
		f.add(timep,gbc);
		
		gbc.fill=GridBagConstraints.NONE;
		
		gbc.gridx=1;
		gbc.gridy=3;
		f.add(submit,gbc);
		
		gbc.gridx=2;
		gbc.gridy=3;
		f.add(cancel,gbc);
		
		f.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==submit)
		{
			String course=(String)namecb.getSelectedItem();
			int day=Integer.parseInt(ddt.getText());
			int month=Integer.parseInt(mmt.getText());
			int year=Integer.parseInt(yyt.getText());
			int minute=Integer.parseInt(mt.getText());
			int hour=Integer.parseInt(ht.getText());
			int cid,bid;
			java.sql.Date currentd;
			currentd=new java.sql.Date(System.currentTimeMillis());
			
			if(day>31)
			{
				JOptionPane.showMessageDialog(f,"Invalid date");
				return;
			}
			else if(month>12)
			{
				JOptionPane.showMessageDialog(f,"Invalid Month");
				return;
			}
			else if(year<2016)
			{
				JOptionPane.showMessageDialog(f,"Invalid year");
				return;
			}
			else if(minute>=60)
			{
				JOptionPane.showMessageDialog(f,"Invalid Minutes");
				return;
			}
			else if(hour>24)
			{
				JOptionPane.showMessageDialog(f,"Invalid Hours");
				return;
			}
			
			try
			{
				Calendar rightnow;
				rightnow=Calendar.getInstance();
				int dd=rightnow.get(Calendar.DATE);
				int mm=rightnow.get(Calendar.MONTH);
				int yy=rightnow.get(Calendar.YEAR);
				
				currentd=currentd.valueOf(year+"-"+month+"-"+day);
				java.sql.Date startdate=new java.sql.Date(System.currentTimeMillis());
				startdate=startdate.valueOf(yy+"-"+mm+"-"+dd);
				
				System.out.println("i was here");
				
				boolean b=startdate.after(currentd);
		
				if(b)
				{
					JOptionPane.showMessageDialog(f,"You Can't Start Batch On This Date");
					return;
				}
			}
			catch(Exception ex)
			{
				System.out.println("Exception in Checkin Date");
			}
			
			java.sql.Time currentt=new java.sql.Time(System.currentTimeMillis());
			currentt=currentt.valueOf(hour+":"+minute+":00");
				
			try
			{
				ResultSet rs=ConnectMysql.stmt.executeQuery("select course_id from course where coursename='"+course+"'");
				
				rs.next();
				cid=rs.getInt("course_id");
				System.out.println("ok till here");
				
				rs=ConnectMysql.stmt.executeQuery("select batch_id from batch order by batch_id desc");
				if(rs.next())
				{
					bid=rs.getInt("batch_id");
					bid++;
				}
				else
				{
					bid=1000;
				}
				System.out.println("ok till here");
				
				ConnectMysql.stmt.execute("insert into batch(course_id,batch_id,timing,start_date) values("+cid+","+bid+","+"'"+currentt+"'"+","+"'"+currentd+"'"+")");
				JOptionPane.showMessageDialog(f,"Batch Sucssessfully added");
			}
			catch(SQLException ex)
			{
				int code=ex.getErrorCode();
				System.out.println(code);
				System.out.println("Exception occured");
			}
			
		}
		
		else if(e.getSource()==cancel)
		{
			f.dispose();
		}
	}
	
	public void keyTyped(KeyEvent e)
	{
		int limit=100;
		Object s=e.getSource();
		
		if(s==ddt)
		{
			limit=2;
		}
		else if(s==mmt)
		{
			limit=2;
		}
		else if(s==yyt)
		{
			limit=4;
		}
		else if(s==ht)
		{
			limit=2;
		}
		else if(s==mt)
		{
			limit=2;
		}
		
		String temp=((JTextField)s).getText();
		
		if(temp.length()>=limit)
		{
			e.consume();
		}
		
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

	
	public static void main(String arg[])
	{
		AddBatch obj=new AddBatch();
	}
}