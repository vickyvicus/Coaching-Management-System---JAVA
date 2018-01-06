import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.sql.*;
import java.awt.event.*;

class LoginWindow implements ActionListener
{
	JFrame f;
	JLabel userl,passl;
	JTextField usert;
	JPasswordField passt;
	JRadioButton admin,user;
	JButton login,cancel;
	JPanel top,middle;
	
	LoginWindow()
	{
		f=new JFrame();
		f.setSize(500,300);
		f.setLocation(300,300);
		f.setResizable(false);
		
		top=new JPanel();
		middle=new JPanel();
		
		admin=new JRadioButton("Admin");
		user=new JRadioButton("User");
		
		ButtonGroup bg=new ButtonGroup();
		bg.add(admin);
		bg.add(user);
		
		user.setSelected(true);
		
		top.setBorder(new TitledBorder((Border)(new BevelBorder(BevelBorder.RAISED)),"Welcome"));
		top.add(admin);
		top.add(user);
		
		login=new JButton("Login");
		cancel=new JButton("Cancel");
		
		//bottom.add(login);
		//bottom.add(cancel);
		
		middle.setLayout(new GridBagLayout());
		
		userl=new JLabel("Username:");
		passl=new JLabel("Password:");
		                         
		usert=new JTextField( 25);
		passt=new JPasswordField(25);
		
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.insets=new Insets(10,10,10,10);
		gbc.gridx=0;
		gbc.gridy=0;
		middle.add(userl,gbc);
		
		gbc.gridx=1;
		gbc.gridy=0;
		gbc.gridwidth=2;
		gbc.fill=GridBagConstraints.BOTH;
		
		middle.add(usert,gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.gridwidth=1;
		middle.add(passl,gbc);
		
		gbc.gridx=1;
		gbc.gridy=1;
		gbc.gridwidth=2;
		middle.add(passt,gbc);
		
		gbc.gridx=0;
		gbc.gridy=2;
		gbc.gridwidth=1;
		middle.add(login,gbc);
		
		gbc.gridx=1;
		gbc.gridy=2;
		gbc.gridwidth=2;
		gbc.fill=GridBagConstraints.NONE;
		middle.add(cancel,gbc);

		middle.setBackground(new Color(195,119,172));
		top.setBackground(new Color(218,117,55));
		
		login.addActionListener(this);
		cancel.addActionListener(this);
		
		f.add(top,"North");
		f.add(middle,"Center");
		f.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==login)
		{
			String username,password;
			boolean b=false;
			
			int ad;
			
			if(admin.isSelected())
			{
				ad=1;
			}
			else
			{
				ad=0;
			}
			
			username=usert.getText();
			password=passt.getText();
			//System.out.println(username);
			//System.out.println(password);
			try
			{
			
			ResultSet rs=ConnectMysql.stmt.executeQuery("select * from login where admin_user='"+ad+"'");
			
			while(rs.next())
			{
				if(rs.getString("username").equals(username))
				{
					if(rs.getString("password").equals(password))
					{
						b=true;
						if(rs.getBoolean("admin_user")==true)
						{
							JOptionPane.showMessageDialog(f,"WELCOME AS A ADMIN");
							ConnectMysql.admin=true;
							MainWindow obj=new MainWindow();
							f.dispose();
						}
						else
						{
							JOptionPane.showMessageDialog(f,"WELCOME AS A USER");
							ConnectMysql.admin=false;
							MainWindow obj=new MainWindow();
							f.dispose();
						}
					}
				}
			}
			
			}
			catch(SQLException ex)
			{
				int code=ex.getErrorCode();
				System.out.println(code);
				System.out.println("Exception occured");
			}
			
			if(!b)
			{
				JOptionPane.showMessageDialog(f,"INCORRECT USERNAME OR PASSWORD");
			}
		}
		
		else if(e.getSource()==cancel)
		{
			f.setVisible(false);
			f.dispose();
		}
	}
	
	public static void main(String arg[])
	{
		LoginWindow lw=new LoginWindow();
	}
}