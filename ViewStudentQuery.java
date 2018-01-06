import java.sql.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.awt.event.*;

class ViewStudentQuery implements ActionListener
{
	JInternalFrame f;
	GridBagConstraints gbc;
	JTable t;
	JComboBox coursescb;
	JPanel north,south;
	JLabel coursesl;
	DefaultTableModel dtm;
	JScrollPane sp;
	JButton export;
	static JPanel location;
	
	ViewStudentQuery()
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
		
		north=new JPanel();
		north.setLayout(new GridBagLayout());
		coursesl=new JLabel("Courses:");
		coursescb=new JComboBox();
		
		coursescb.addItem("Choose");
		coursescb.addItem("All Cources");
		try
		{
			ResultSet rs=ConnectMysql.stmt.executeQuery("select coursename from course");
		
			while(rs.next())
			{
				coursescb.addItem(rs.getString("coursename"));
			}
		}
		catch(SQLException ex)
		{
			int code=ex.getErrorCode();
				System.out.println(code);
			System.out.println("Excption Occured in adding in batch");
		}
		
		gbc=new GridBagConstraints();
		gbc.insets=new Insets(20,20,20,20);
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.fill=GridBagConstraints.BOTH;
		north.add(coursesl,gbc);
		
		gbc.gridx=2;
		gbc.gridy=0;
		gbc.gridwidth=2;
		north.add(coursescb,gbc);
		
		dtm=new DefaultTableModel();
		
		String col[]={"Name","Contact","Query_id","Comments","Course_Id"};
		
		for(String temp:col)
		{
			dtm.addColumn(temp);
		}
		
		t=new JTable(dtm);
		sp=new JScrollPane(t);
		
		coursescb.addActionListener(this);
		south=new JPanel();
		export=new JButton("Export");
		south.add(export);
		export.addActionListener(this);
		
		f.add(north,"North");
		f.add(sp);
		f.add(south,"South");
		f.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==coursescb)
		{
			try
			{
				String selected=(String)coursescb.getSelectedItem();
				
				while(dtm.getRowCount()!=0)
				{
					dtm.removeRow(0);
				}
				
				if(selected=="Choose")
				{
					return;
				}
				
				if(selected=="All Cources")
				{
					setAllCourseView();
					return;
				}
				
				ResultSet rs=ConnectMysql.stmt.executeQuery("select course_id from course where coursename='"+selected+"'");
				rs.next();
				int id=rs.getInt("course_id");
				
				rs=ConnectMysql.stmt.executeQuery("select * from studentquery where course_id='"+id+"'");
				String data[]=new String[5];
			
				while(rs.next())
				{
					data[2]=""+rs.getInt("query_id");
					data[0]=rs.getString("name");
					data[1]=rs.getString("contact");
					data[3]=rs.getString("Comments");
					data[4]=""+rs.getInt("course_id");
					
					dtm.addRow(data);
				}
			}
			catch(SQLException ex)
			{
				int code=ex.getErrorCode();
				System.out.println(code);
				System.out.println("Exception occured");
			}
		}
		
		else if(e.getSource()==export)
		{
			QueryConfirmationWindow obj=new QueryConfirmationWindow(t);
			location.add(ConnectMysql.jif);
			QueryConfirmationWindow.location=location;
		}
	}
	
	void setAllCourseView()
	{
		try
			{
				while(dtm.getRowCount()!=0)
				{
					dtm.removeRow(0);
				}
				
				ResultSet rs=ConnectMysql.stmt.executeQuery("select * from studentquery");
				String data[]=new String[5];
				
				while(rs.next())
				{
					data[0]=rs.getString("name");
					data[1]=rs.getString("contact");
					data[2]=""+rs.getInt("query_id");
					data[3]=rs.getString("comments");
					data[4]=""+rs.getInt("course_id");
					
					dtm.addRow(data);
				}
			}
			catch(SQLException ex)
			{
				int code=ex.getErrorCode();
				System.out.println(code);
				System.out.println("Exception occured");
			}
	}
	
	public static void main(String arg[])
	{
		ViewStudentQuery obj=new ViewStudentQuery();
	}
}
