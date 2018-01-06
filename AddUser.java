import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class AddUser implements ActionListener
{
	JInternalFrame f;
	JLabel usernamel,passwordl;
	JTextField usernamet,passwordt;
	JRadioButton admin,user;
	JButton submit,cancel;
	
	AddUser()
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
		
		usernamel=new JLabel("UserName:");
		passwordl=new JLabel("Password");
		
		usernamet=new JTextField(15);
		passwordt=new JTextField(15);

		admin=new JRadioButton("Admin");
		user=new JRadioButton("User");
		//user.setSelected();
		
		ButtonGroup bg=new ButtonGroup();
		bg.add(admin);
		bg.add(user);
		
		submit=new JButton("Submit");
		cancel=new JButton("Cancel");
		
		submit.addActionListener(this);
		cancel.addActionListener(this);
								
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.insets=new Insets(10,10,10,10);
		gbc.fill=GridBagConstraints.BOTH;
		
		gbc.gridx=0;
		gbc.gridy=0;
		f.add(usernamel,gbc);
		
		gbc.gridx=1;
		gbc.gridy=0;
		gbc.gridwidth=2;
		f.add(usernamet,gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.gridwidth=1;
		f.add(passwordl,gbc);
		
		gbc.gridx=1;
		gbc.gridy=1;
		gbc.gridwidth=2;
		f.add(passwordt,gbc);
		
		gbc.fill=GridBagConstraints.NONE;
		
		gbc.gridx=1;
		gbc.gridy=2;
		gbc.gridwidth=1;
		f.add(admin,gbc);
		
		gbc.gridx=2;
		gbc.gridy=2;
		gbc.gridwidth=1;
		f.add(user,gbc);
		
		gbc.gridx=0;
		gbc.gridy=3;
		f.add(submit,gbc);
		
		gbc.gridx=1;
		gbc.gridy=3;
		f.add(cancel,gbc);
		
		f.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==submit)
		{
			String name=usernamet.getText();
			String password=passwordt.getText();
			
			int ad,id;
			
			if(admin.isSelected())
			{
				ad=1;
			}
			else
			{
				ad=0;
			}
			
			if(name==null)
			{
				JOptionPane.showMessageDialog(f,"please enter a UserName");
				return;
			}
			else if(password==null)
			{
				JOptionPane.showMessageDialog(f,"please enter Password");
				return;
			}       
			else
			{
				try
				{
				ResultSet rs=ConnectMysql.stmt.executeQuery("select user_id from login order by user_id desc");
				if(rs.next())
				{
					id=rs.getInt("user_id");
					id++;
				}
				else
				{
					id=1;
				}
				
				ConnectMysql.stmt.execute("insert into login(username,password,user_id,admin_user) values("+"'"+name+"'"+","+"'"+password+"'"+","+id+","+ad+")");
				JOptionPane.showMessageDialog(f,"User Successfully added");
				}
				catch(SQLException ex)
				{
					int code=ex.getErrorCode();
					System.out.println(code);
					System.out.println("Exception occured");
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
		AddUser obj=new AddUser();
	}
}