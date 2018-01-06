import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

class QueryConfirmationWindow implements ActionListener
{
	JInternalFrame f;
	JLabel l,batchl;
	JComboBox batchcb;
	JTable t;
	JScrollPane sp;
	DefaultTableModel dtm;
	JButton proceed,cancel;
	JPanel south,north,nsouth;
	static JPanel location;
	
	QueryConfirmationWindow(JTable temp)
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
		f.setSize(500,500);
		f.setLocation(50,50);
		f.setLayout(new BorderLayout());
		
		north=new JPanel();
		north.setLayout(new BorderLayout());
		l=new JLabel();
		l.setFont(new Font("Arial",Font.BOLD,40));
		l.setText("These Students Will Be Added In Student Record");
		nsouth=new JPanel();
		batchl=new JLabel("Batches");
		batchcb=new JComboBox();
		
		try
		{
			ResultSet rs=ConnectMysql.stmt.executeQuery("select * from batch where course_id="+Integer.parseInt((String)temp.getModel().getValueAt(0,4)));
			if(rs.next())
			{
				java.sql.Time time;
				java.sql.Date date;
				int batchid;
				batchid=rs.getInt("batch_id");
				time=rs.getTime("timing");
				date=rs.getDate("start_date");
				batchcb.addItem(batchid+" "+date+" "+time);
			}
			else
			{
				return;
			}
		}
		catch(SQLException ex)
		{
				int code=ex.getErrorCode();
				System.out.println(code);
				System.out.println("Exception occured");
		}
		
		
		nsouth.add(batchl);
		nsouth.add(batchcb);
		
		north.add(l,"Center");
		north.add(nsouth,"South");
		
		dtm=new DefaultTableModel();
	
		int selected[]=temp.getSelectedRows();
		String rowobj[]=new String[temp.getColumnCount()]; 
		
		String cols[]={"Name","Contact","Query_id","Comments","Course_Id"};
		
		for(String st:cols)
		{
			dtm.addColumn(st);
		}
		
		for(int row : selected)
		{
			for(int col=0;col<(temp.getColumnCount());col++)
			{
				rowobj[col]=(String)temp.getModel().getValueAt(row,col);
			}
			dtm.addRow(rowobj);
		}
		
		t=new JTable(dtm);
		sp=new JScrollPane(t);
		
		proceed=new JButton("Proceed");
		cancel=new JButton("Cancel");
		
		south=new JPanel();
		south.add(proceed);
		south.add(cancel);
		
		f.add(north,"North");
		f.add(sp,"Center");
		f.add(south,"South");
		
		proceed.addActionListener(this);
		cancel.addActionListener(this);
		f.setVisible(true);
	}
	
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==proceed)
		{
			try
			{
				String data[]=new String[t.getColumnCount()];
			
				int batchid=Integer.parseInt(((String)batchcb.getSelectedItem()).substring(0,4));
				
				int id;
				ResultSet rs=ConnectMysql.stmt.executeQuery("select student_id from studentrecord order by student_id desc");
				rs.next();
				id=rs.getInt("student_id");
				
			
			
				if(e.getSource()==proceed)
				{
					for(int i=0;i<t.getRowCount();i++)
					{
						for(int j=0;j<t.getColumnCount();j++)
						{
							data[j]=(String)t.getModel().getValueAt(i,j);
						}
						
						id++;
						ConnectMysql.stmt.execute("insert into studentrecord(name,contact,batch_id,comments,student_id) values("+"'"+data[0]+"'"+","+data[1]+","+batchid+","+"'"+data[3]+"'"+","+id+")");
						ConnectMysql.stmt.execute("delete from studentquery where query_id="+data[2]);
						
						JOptionPane.showMessageDialog(f,"Records has been sucssesfully added");
						ViewStudentQuery obj=new ViewStudentQuery();
						location.add(ConnectMysql.jif);
					}
				}
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
	
	public static void main(String arg[])
	{
		//QueryConfirmationWindow obj=new QueryConfirmationWindow();
	}
}