package ucc.utils;


public class ICVConferenceDataTable {
	public String description;
	public String end_date;
	public String name;
	public String start_date;
	public String state;
	public String timezone;
	
	
	public ICVConferenceDataTable (String desp, String end_D, String nme, String start_D, String st, String tz) 
	{
		description = desp;
		end_date = end_D;
		name = nme;
		start_date = start_D;
		state = st;
		timezone = tz;
	}
}
