import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class StudentQuery implements ActionListener
{
	JInternalFrame f;
	JLabel datel,namel,contactl,coursel,remarkl;
	JTextField datet,namet,contactt;
	JTextArea remarka;
	JComboBox coursec;
	JButton submit,cancel;
	Calendar rightnow;
	ResultSet rs;
	
	StudentQuery()
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
		f.setLayout(new GridBagLayout());
		
		datel=new JLabel("Date");
		namel=new JLabel("*Name:");
		contactl=new JLabel("*Contact No.");
		coursel=new JLabel("*Couse:");
		remarkl=new JLabel("Comments:");
		
		submit=new JButton("Submit");
		cancel=new JButton("Cancel");
		
		remarka=new JTextArea(4,1);
		
		datet=new JTextField(8);
		namet=new JTextField(15);
		contactt=new JTextField(15);
	
		coursec=new JComboBox();
		
		contactt.addKeyListener(new KeyListener()
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
		
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.insets=new Insets(10,10,10,10);
		
		gbc.gridx=0;
		gbc.gridy=0;
		f.add(datel,gbc);
		
		gbc.gridx=1;
		gbc.gridy=0;
		gbc.gridwidth=2;
		gbc.anchor=GridBagConstraints.WEST;
		f.add(datet,gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.gridwidth=1;
		f.add(namel,gbc);
		
		gbc.gridx=1;
		gbc.gridy=1;
		gbc.gridwidth=2;
		gbc.fill=GridBagConstraints.BOTH;
		f.add(namet,gbc);
		
		gbc.gridx=0;
		gbc.gridy=2;
		gbc.gridwidth=1;
		f.add(contactl,gbc);
		
		gbc.gridx=1;
		gbc.gridy=2;
		gbc.gridwidth=2;
		f.add(contactt,gbc);
		
		gbc.gridx=0;
		gbc.gridy=3;
		gbc.gridwidth=1;
		f.add(coursel,gbc);
		
		gbc.gridx=1;
		gbc.gridy=3;
		gbc.gridwidth=2;
		f.add(coursec,gbc);
		
		gbc.gridx=0;
		gbc.gridy=4;
		gbc.gridwidth=1;
		f.add(remarkl,gbc);
		
		gbc.gridx=1;
		gbc.gridy=4;
		gbc.gridwidth=2;
		f.add(remarka,gbc);
		
		gbc.fill=GridBagConstraints.NONE;
		
		gbc.gridx=1;
		gbc.gridy=6;
		gbc.gridwidth=1;
		//f.anchor=GridBagConstraints.EAST;
		f.add(submit,gbc);
		
		gbc.gridx=2;
		gbc.gridy=6;
		gbc.gridwidth=1;
		//f.anchor=GridBagConstraints.WEST;
		f.add(cancel,gbc);
		
		setFormValues();
		submit.addActionListener(this);
		cancel.addActionListener(this);
		f.setVisible(true);
	}
	
	public static void main(String arg[])
	{
		StudentQuery se=new StudentQuery();
	}
	
	void setFormValues()
	{
		datet.setEditable(false);
		rightnow=Calendar.getInstance();
		int dd=rightnow.get(Calendar.DATE);
		int mm=rightnow.get(Calendar.MONTH);
		int yy=rightnow.get(Calendar.YEAR);
		datet.setText(dd+"/"+mm+"/"+yy);
		
		coursec.addItem("Choose");
		
		try
		{
			rs=ConnectMysql.stmt.executeQuery("select coursename from course");
			while(rs.next())
			{
				coursec.addItem(rs.getString("coursename"));
			}
		}
		catch(Exception ex)
		{
			System.out.println("AN Exception Occured");
		}
		
		coursec.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
		if(e.getSource()==submit)
		{
			String name=namet.getText();
			String contact=contactt.getText();
			String course=(String)coursec.getSelectedItem();
			String remarks=remarka.getText();
			int id;
			int courseid;
			
			if(name==null)
			{
				JOptionPane.showMessageDialog(f,"Please enter a Name");
				return;
			}
			else if(contact==null || (contact.length()!=10))
			{
				JOptionPane.showMessageDialog(f,"Please enter a Valid phone number");
				return;
			}
			else if(course=="Choose")
			{
				JOptionPane.showMessageDialog(f,"Please choose a Course");
				return;
			}
			
			if(remarks==null)
			{
				remarks="";
			}
			
			try
			{
				rs=ConnectMysql.stmt.executeQuery("select course_id from course where coursename="+"'"
				+course+"'");
				
				if(rs.next())
				{
					courseid=rs.getInt("course_id");
				}
				else
				{courseid=0;
					JOptionPane.showMessageDialog(f,"Please tell administrator to add a course");
					return;
				}
				
				rs=ConnectMysql.stmt.executeQuery("select query_id from studentquery order by query_id desc");
				if(rs.next())
				{
					id=rs.getInt("query_id");
					id++;
				}
				else
				{
					id=1;
				}
				ConnectMysql.stmt.execute("insert into studentquery(name,contact,query_id,comments,course_id) values("+"'"+name+"'"+","+contact+","+id+","+"'"+remarks+"'"+","+courseid+")");
				JOptionPane.showMessageDialog(f,"your query number is:"+id);
			}
			catch(SQLException ex)
			{
				int code=ex.getErrorCode();
				System.out.println(code);
				System.out.println("Exception in Submit");
			}
		}
		
		else if(e.getSource()==cancel)
		{
			f.dispose();
		}
	}
}