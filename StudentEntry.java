import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class StudentEntry implements ActionListener
{
	JInternalFrame f;
	JLabel datel,namel,contactl,coursel,batchl,remarkl;
	JTextField datet,namet,contactt;
	JTextArea remarka;
	JComboBox coursec,batchc;
	JButton submit,cancel;
	Calendar rightnow;
	ResultSet rs;
	JScrollPane sp;
	
	StudentEntry()
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
		batchl=new JLabel("*Batch:");
		remarkl=new JLabel("Remarks:");
		
		submit=new JButton("Submit");
		cancel=new JButton("Cancel");
		
		remarka=new JTextArea(4,1);
		sp=new JScrollPane(remarka);
		
		datet=new JTextField(8);
		namet=new JTextField(15);
		contactt=new JTextField(15);
	
		coursec=new JComboBox();
		batchc=new JComboBox();
		
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
		f.add(batchl,gbc);
		
		gbc.gridx=1;
		gbc.gridy=4;
		gbc.gridwidth=2;
		f.add(batchc,gbc);
		
		gbc.gridx=0;
		gbc.gridy=5;
		gbc.gridwidth=1;
		f.add(remarkl,gbc);
		
		gbc.gridx=1;
		gbc.gridy=5;
		gbc.gridwidth=2;
		f.add(sp,gbc);
		
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
		StudentEntry se=new StudentEntry();
	}
	
	void setFormValues()
	{
		datet.setEditable(false);
		rightnow=Calendar.getInstance();
		int dd=rightnow.get(Calendar.DATE);
		int mm=rightnow.get(Calendar.MONTH);
		int yy=rightnow.get(Calendar.YEAR);
		mm++;
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
		batchc.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==coursec)
		{
			batchc.removeAllItems();
			String course=(String)coursec.getSelectedItem();
			
			try
			{
				if(course=="Choose")
				{
					return;
				}
				rs=ConnectMysql.stmt.executeQuery("select course_id from course where coursename='"+course+"'");
				//System.out.println("ok Till here");
				rs.next();
				int id=rs.getInt("course_id");
				rs=ConnectMysql.stmt.executeQuery("select * from batch where course_id="+id);
				
				batchc.addItem("Choose");
				while(rs.next())
				{
					int batchid=rs.getInt("batch_id");
					java.sql.Date d=rs.getDate("start_date");
					java.sql.Time t=rs.getTime("timing");
					batchc.addItem(""+batchid+" "+d+" "+t);
				}
				
			}
			catch(Exception ex)
			{
				System.out.println("Exception OCCured");
			}
		}
		
		else if(e.getSource()==submit)
		{
			String name=namet.getText();
			String contact=contactt.getText();
			String course=(String)coursec.getSelectedItem();
			String batch=(String)batchc.getSelectedItem();
			String remarks=remarka.getText();
			int id;
			int batchid;
			
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
			else if(batch=="Choose")
			{
				JOptionPane.showMessageDialog(f,"Please choose a batch");
				return;
			}
			if(remarks==null)
			{
				remarks="";
			}
			
			try
			{
				String sub=batch.substring(0,4);
				batchid=Integer.parseInt(sub);
				
				rs=ConnectMysql.stmt.executeQuery("select student_id from studentrecord order by student_id desc");
				//rs.next();
				if(rs.next())
				{
						//rs.next();
					id=rs.getInt("student_id");
					id++;
				}
				else
				{
					id=1;
				}
				ConnectMysql.stmt.execute("insert into studentrecord(name,contact,batch_id,comments,student_id) values("+"'"+name+"'"+","+contact+","+batchid+","+"'"+remarks+"'"+","+id+")");
				
				JOptionPane.showMessageDialog(f,"Record has been Added");
			}
			catch(SQLException ex)
			{
				System.out.println(ex.getCause());
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