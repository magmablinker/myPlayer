package model;

public interface IDataHandler  {

	public void load();
	
	public void save();
	
	public boolean isAlreadySaved(String item);
	
}
