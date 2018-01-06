import java.sql.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.awt.event.*;

class ViewStudentRecord implements ActionListener
{
	JInternalFrame f;
	GridBagConstraints gbc;
	JTable t;
	JComboBox coursescb,batchescb;
	JPanel north;
	JLabel coursesl,batchesl;
	DefaultTableModel dtm;
	JScrollPane sp;
	
	ViewStudentRecord()
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
		batchesl=new JLabel("Batches:");
		coursescb=new JComboBox();
		batchescb=new JComboBox();
		
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

		gbc.gridx=0;
		gbc.gridy=2;
		gbc.gridwidth=1;
		north.add(batchesl,gbc);
		
		gbc.gridx=2;
		gbc.gridy=2;
		gbc.gridwidth=2;
		north.add(batchescb,gbc);
		
		dtm=new DefaultTableModel()
		{
			public boolean isCellEditable(int r,int c)
			{
				return false;
			}
		};
		
		String col[]={"Name","Contact","Batch_id","Comments","Course_Id"};
		
		for(String temp:col)
		{
			dtm.addColumn(temp);
		}
		
		t=new JTable(dtm);
		sp=new JScrollPane(t);
		
		coursescb.addActionListener(this);
		batchescb.addActionListener(this);
		
		f.add(north,"North");
		f.add(sp);
		f.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==coursescb)
		{
			batchescb.removeAllItems();
			try
			{
				batchescb.addItem("Choose");
				String selected=(String)coursescb.getSelectedItem();
				
				if(selected=="Choose")
				{
					return;
				}
				
				//for(int i=0;i<dtm.getRowCount();i++)
				while(dtm.getRowCount()!=0)
				{
					dtm.removeRow(0);
				}
				
				if(selected=="All Cources")
				{
					setAllCourseView();
					return;
				}
				
				ResultSet rs=ConnectMysql.stmt.executeQuery("select course_id from course where coursename='"+selected+"'");
				rs.next();
				int id=rs.getInt("course_id");
				
				rs=ConnectMysql.stmt.executeQuery("select * from batch where course_id='"+id+"'");
				
				while(rs.next())
				{
					id=rs.getInt("batch_id");
					java.sql.Date date=rs.getDate("start_date");
					java.sql.Time time=rs.getTime("timing");
					
					batchescb.addItem(id+"  "+date+"   "+time);
				}
			}
			catch(SQLException ex)
			{
				int code=ex.getErrorCode();
				System.out.println(code);
				System.out.println("Exception occured");
			}
		}
		
		else if(e.getSource()==batchescb)
		{
			String selected=(String)batchescb.getSelectedItem();
			
			if(selected=="Choose" || selected==null)
			{
				return;
			}
			
			int id=Integer.parseInt(selected.substring(0,4));
			
			try
			{
				while(dtm.getRowCount()!=0)
				{
					dtm.removeRow(0);
				}
				
				ResultSet rs=ConnectMysql.stmt.executeQuery("select * from studentrecord where batch_id='"+id+"'");
				String data[]=new String[5];
				
				while(rs.next())
				{
					data[0]=rs.getString("name");
					data[1]=""+rs.getString("contact");
					data[2]=""+rs.getInt("batch_id");
					data[3]=rs.getString("comments");
					data[4]=""+rs.getInt("student_id");
					
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
	}
	
	void setAllCourseView()
	{
		try
			{
				for(int i=0;i<dtm.getRowCount();i++)
				{
					dtm.removeRow(i);
				}
				
				ResultSet rs=ConnectMysql.stmt.executeQuery("select * from studentrecord");
				String data[]=new String[5];
				
				while(rs.next())
				{
					data[0]=rs.getString("name");
					data[1]=""+rs.getString("contact");
					data[2]=""+rs.getInt("batch_id");
					data[3]=rs.getString("comments");
					data[4]=""+rs.getInt("student_id");
					
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
		ViewStudentRecord obj=new ViewStudentRecord();
	}
}
