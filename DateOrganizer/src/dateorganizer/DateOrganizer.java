package dateorganizer;

/**
 * A testbed for a binary heap implementation of a priority queue using 
 * various comparators to sort Gregorian dates
 * @author Duncan, Jackson Descant
 * @see Date, PQueueAPI, PQueue
 * <pre>
 * Date: 09-24-2023
 * Course: csc 3102
 * File: DateOrganizer.java
 * Instructor: Dr. Duncan
 * </pre>
 */
 
import java.io.IOException;
import java.util.Scanner;
import java.io.FileReader;
import java.util.Comparator; 

public class DateOrganizer
{
    /**
     * Gives the integer value equivalent to the day of the
     * week of the specified date 
     * @param d a date on the Gregorian Calendar
     * @return 0->Sunday, 1->Monday, 2->Tuesday, 3->Wednesday,
     * 4->Thursday, 5->Friday, 6->Saturday; otherwise, -1
     */
    public static int getDayNum(Date d)
    {
        switch (d.getDayOfWeek()) {
        
        case "Sunday":
        	return 0;
        case "Monday":
        	return 1;
        case "Tuesday":
        	return 2;
        case "Wednesday":
        	return 3;
        case "Thursday":
        	return 4;
        case "Friday":
        	return 5;
        case "Saturday":
        	return 6;
        default:
        	return -1;
        }
        
    }
	public static void main(String[] args) throws IOException, PQueueException
    {
        String usage = "DateOrganizer <date-file-name> <sort-code>%n";
        usage += "sort-code: -2 -month-day-year%n";
        usage += "           -1 -year-month-day%n";
        usage += "            0 +weekDayNumber+monthName+day+year%n";
        usage += "            1 +year+month+day%n";
        usage += "            2 +month+day+year";
        if (args.length != 2)
        {
            System.out.println("Invalid number of command line arguments");
            System.out.printf(usage+"%n%");
            System.exit(1);
        }
        PQueue<Date> dates = new PQueue<Date>();
        
        String fileName = args[0];
        int sortCode = Integer.parseInt(args[1]);
        
        switch (sortCode) {
        
        case -2:
       
        	Comparator<Date> mDate = (a, b) ->
        	{
        		if (a.getMonth() > b.getMonth())
        			return 1;
        		if (a.getMonth() < b.getMonth())
        			return -1;
        		if (a.getDay() > b.getDay())
        			return 1;
        		if (a.getDay() < b.getDay())
        			return -1;
        		if (a.getYear() > b.getYear())
        			return 1;
        		if (a.getYear() < b.getYear())
        			return -1;
        		return 0;
        		
        	};
        
        	dates = new PQueue<Date>(mDate);
        	usage = "-month-day-year";
        	break;
        
        case -1:
        
        	Comparator<Date> yDate = (a, b) ->
        	{
        		if (a.getYear() > b.getYear())
        			return 1;
        		if (a.getYear() < b.getYear())
        			return -1;
        		if (a.getMonth() > b.getMonth())
        			return 1;
        		if (a.getMonth() < b.getMonth())
        			return -1;
        		if (a.getDay() > b.getDay())
        			return 1;
        		if (a.getDay() < b.getDay())
        			return -1;
        		return 0;
        	};
        	dates = new PQueue<Date>(yDate);
        	usage = "-year-month-day";
        	break;
        
        	
        case 0:
        
        	Comparator<Date> dayNumDate = (a, b) ->
        	{
        		if (getDayNum(a) < getDayNum(b))
        			return 1;
        		if (getDayNum(a) > getDayNum(b))
        			return -1;
        		if (a.getMonthName().compareTo(b.getMonthName()) < 0)
        			return 1;
        		if (a.getMonthName().compareTo(b.getMonthName()) > 0)
        			return -1;
        		if (a.getDay() < b.getDay())
        			return 1;
        		if (a.getDay() > b.getDay())
        			return -1;
        		if (a.getYear() < b.getYear())
        			return 1;
        		if (a.getYear() > b.getYear())
        			return -1;
        		return 0;
			};
			dates = new PQueue<Date>(dayNumDate);
			usage = "+weekDayNumber+monthName+day+year";
			break;
        
        case 1:
        
        	Comparator<Date> dyDate = (a, b) ->
        	{
        		if (a.getYear() < b.getYear())
        			return 1;
        		if (a.getYear() > b.getYear())
        			return -1;
        		if (a.getMonth() < b.getMonth())
        			return 1;
        		if (a.getMonth() > b.getMonth())
        			return -1;
        		if (a.getDay() < b.getDay())
        			return 1;
        		if (a.getDay() > b.getDay())
        			return -1;
        		return 0;
        	};
        	dates = new PQueue<Date>(dyDate);
        	usage = "+year+month+day";
        	break;
        
        case 2:
        
        	Comparator<Date> dmDate = (Date a,Date b) ->
        	{
        		if (a.getMonth() < b.getMonth())
        			return 1;
        		if (a.getMonth() > b.getMonth())
        			return -1;
        		if (a.getDay() < b.getDay())
        			return 1;
        		if (a.getDay() > b.getDay())
        			return -1;
        		if (a.getYear() < b.getYear())
        			return 1;
        		if (a.getYear() > b.getYear())
        			return -1;
        		return 0;
        	};
        	dates = new PQueue<Date>(dmDate);
        	usage = "+month+day+year";
        	break;
        }
        	
        
        try(FileReader reader = new FileReader(fileName)){
        	Scanner fileScan = new Scanner(reader);
        	
        	while (fileScan.hasNextLine()) {
        		String fullDate = fileScan.nextLine();
        		String[] splitter = fullDate.split("/");
        		
        		int mm = Integer.parseInt(splitter[0]);
        		int dd = Integer.parseInt(splitter[1]);
        		int yyyy = Integer.parseInt(splitter[2]);
        		
        		dates.insert(new Date(mm, dd, yyyy));
        	}
        	reader.close();
        }
        catch (IOException e) {
        	e.printStackTrace();
	}
        
        System.out.printf("Dates from %s in %s Order:%n%n", fileName, usage );
        while (dates.isEmpty() == false) {
        	Date date = dates.peek();
        	System.out.printf("%s, %s %d, %d%n",date.getDayOfWeek(), date.getMonthName(), date.getDay(), date.getYear());
        	dates.remove();
        }
        
        
        
    }
}
