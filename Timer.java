import javax.swing.*;
import java.util.*;

class Timer extends Thread
{
	private Calendar rightnow;
	private JLabel date,time;
	private int dd,mm,yy;
	private int hr,min,sec,am_pm;
	
	Timer(JLabel t,JLabel d)
	{
		date=d;
		time=t;
		//setDaemon(true);
	}
	
	public void run()
	{
		while(true)
		{
			rightnow=Calendar.getInstance();
			hr=rightnow.get(Calendar.HOUR);
			min=rightnow.get(Calendar.MINUTE);
			sec=rightnow.get(Calendar.SECOND);
			am_pm=rightnow.get(Calendar.AM_PM);
			
			//time.setText("Time:"+hr+":"+min+":"+sec);
			
			if(am_pm==Calendar.AM)
			{
				time.setText(hr+":"+min+":"+sec+"  "+"AM");
			}
			else
			{
				time.setText(hr+":"+min+":"+sec+"  "+"PM");
			}
			
			if(hr==0&&min==0&&sec==0)
			{
				setDate();
			}
			
			try
			{
				Thread.sleep(1000);
			}
			catch(Exception ex)
			{
				System.out.println("Exception Occured");
			}
		}
	}
	
	void setDate()
	{
		rightnow=Calendar.getInstance();
		mm=rightnow.get(Calendar.MONTH);
		yy=rightnow.get(Calendar.YEAR);
		dd=rightnow.get(Calendar.DATE);
		
		date.setText(dd+"/"+(mm+1)+"/"+(yy));
	}

	public static void startTime(JLabel time,JLabel date)
	{
		Timer obj=new Timer(time,date);
		obj.setDate();
		obj.start();
	}
}