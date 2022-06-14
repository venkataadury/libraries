package commons.readers;

public interface LineStream
{
	public String getNextLine();
	public boolean isBackNavigable();
	public String getPreviousLine()throws OneWayStreamException;
	public void gotoLine(int L)throws OneWayStreamException;
	public void skipLines(int L);
	public void gotoStart()throws OneWayStreamException;
}
