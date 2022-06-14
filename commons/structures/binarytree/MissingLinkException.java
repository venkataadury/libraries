package commons.structures.binarytree;
import commons.X;
public class MissingLinkException extends RuntimeException
{
	public MissingLinkException()
	{
		X.sopln("Sorry: Data seems to be missing link labels. Please check for version mismatches.","red");
	}
}
