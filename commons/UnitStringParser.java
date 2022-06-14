package commons;

public class UnitStringParser
{
    private String input;
    private int pos=0,len=0;
    
    public UnitStringParser(String i) 
    {
        input=i;
        pos=0; len=i.length()-1;
    }
    
    public char getNextChar()
    {
        if(pos<=len)
            return input.charAt(pos++);
        else
            return (char)0;
    }
    public int getPos()
    {
        return pos; //Next letter
    }
    public boolean end()
		{
			return pos>len;
		}
}