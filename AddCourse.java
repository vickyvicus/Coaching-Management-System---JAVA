import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class AddCourse implements ActionListener
{
	JInternalFrame f;
	JLabel namel,durationl,feesl;
	JTextField namet,durationt,feest;
	JButton submit,cancel;
	
	AddCourse()
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
		durationl=new JLabel("Duration");
		feesl=new JLabel("Fees:(In Rupees)");
		
		namet=new JTextField(15);
		durationt=new JTextField(15);
		feest=new JTextField(15);
		
		submit=new JButton("Submit");
		cancel=new JButton("Cancel");
		
		submit.addActionListener(this);
		cancel.addActionListener(this);
		
		feest.addKeyListener(new KeyListener()
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
		gbc.fill=GridBagConstraints.BOTH;
		
		gbc.gridx=0;
		gbc.gridy=0;
		f.add(namel,gbc);
		
		gbc.gridx=1;
		gbc.gridy=0;
		gbc.gridwidth=2;
		f.add(namet,gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.gridwidth=1;
		f.add(durationl,gbc);
		
		gbc.gridx=1;
		gbc.gridy=1;
		gbc.gridwidth=2;
		f.add(durationt,gbc);
		
		gbc.gridx=0;
		gbc.gridy=2;
		gbc.gridwidth=1;
		f.add(feesl,gbc);
		
		gbc.gridx=1;
		gbc.gridy=2;
		gbc.gridwidth=2;
		f.add(feest,gbc);
		
		gbc.fill=GridBagConstraints.NONE;
		
		gbc.gridx=0;
		gbc.gridy=3;
		gbc.gridwidth=1;
		f.add(submit,gbc);
		
		gbc.gridx=1;
		gbc.gridy=3;
		gbc.gridwidth=1;
		f.add(cancel,gbc);
		
		f.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==submit)
		{
			String name=namet.getText();
			String duration=durationt.getText();
			String feestemp=feest.getText();
			int fees;			
			int id;
			
			if(name==null)
			{
				JOptionPane.showMessageDialog(f,"please enter a course name");
			}
			else if(duration==null)
			{
				JOptionPane.showMessageDialog(f,"please enter Duration of the Course");
			}
			
			else if(feestemp==null || (feestemp.length()<4) || (feestemp.length()>5))
			{
				JOptionPane.showMessageDialog(f,"please enter valid Fees of the course");
			}
			else
			{
				fees=Integer.parseInt(feestemp);
				
				try
				{
				ResultSet rs=ConnectMysql.stmt.executeQuery("select course_id from course order by course_id desc");
				System.out.println("i Was here");
				if(rs.next())
				{
					id=rs.getInt("course_id");
					id++;
				}
				else
				{
					id=1;
				}
				System.out.println("i Was here");
				ConnectMysql.stmt.execute("insert into course(coursename,course_id,coursedur,fees) values("+"'"+name+"'"+","+id+","+"'"+duration+"'"+","+fees+")");
				JOptionPane.showMessageDialog(f,"Course Successfully added");
				}
				catch(SQLException ex)
				{
					int code=ex.getErrorCode();
					System.out.println(code);
					System.out.println("Exception occured in Adding course");
				}
			}
		}
		
		else if(e.getSource()==cancel)
		{
			f.dispose();
		}
	}
	
	public static void main(String arg[])
	{
		AddCourse obj=new AddCourse();
	}
}