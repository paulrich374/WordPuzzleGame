import java.util.*;

class TrieNode
{
    final int ALPHABET_SIZE = 26;
	public TrieNode[] children = new TrieNode[ALPHABET_SIZE];
	public String item ="";
}
/*
* Class Trie
* Implement a Trie tree: prefix tree (as they can be searched by prefixes), 
* is an ordered tree data structure that is used to store dictionary array 
*/
class Trie{
	
	public TrieNode root =  new TrieNode();
	
	/* Function: add a new word into trie tree
	* @parameters: Strign word 
	*  @return: void
	*/ 
	public void insert(String word)
	{
	   TrieNode node = root;
	   for (char c: word.toCharArray())
	   {
	   	   if (node.children[c-'A'] == null)
	   	      node.children[c - 'A'] = new TrieNode();
	   	   node = node.children[c-'A'];
	   }
	   node.item = word;	
	}	
	
	/* Function: serach whole word exist or not
	* @parameters: Strign word 
	* @return: boolean, word exist = true , not exist = false
	*/	
	public boolean search(String word)
	{
		TrieNode node = root;
		for (char c: word.toCharArray())
		{
		    if (node.children[c-'A']== null)
		        return false;
		    node = node.children[c-'A'];	
		}
		if (node.item.equals(word))
			return true;
		else
			return false;
	}
	
	/* Function: search prefix exist or not
	* @parameters: Strign prefix 
	* @return: boolean, prefix exist = true, not exist = false	
	*/ 
	public boolean startWith(String prefix)
	{
		TrieNode node = root;
		for (char c:prefix.toCharArray())
		{
		    if (node.children[c-'A']== null)
		        return false;	
		    node = node.children[c-'A'];
		}	
		return true;
	}
	

}

public class PuzzleSolver{


    public static String[] DICTIONARY = {"OX","CAT","TOY","AT","DOG","CATAPULT","T"};


    // NOTE: if we use contains to check if the string under explored exist in the dictionary or not,
    //       we will waste a lot of time on check. So we can use prefix to skip some check
    //static bool IsWord(string testWord)
    //{
    //    if (DICTIONARY.Contains(testWord))
    //        return true;
    //    return false;
    //}     
     
     
    /* Fucntion: To find the number of all non-distinct occurrences of the words found int the array, horizontally, vertically or diagonally, and also the reverse in each direction 
    * @parameters: char[][]puzzle, a 2D char array
    * @return: int, the number of occurrences
    */ 
    public static int FindWords(char[][] puzzle)
    {
         
         List<String> res = new ArrayList<String>();
         
    	//vlaidate the input
   	    if (puzzle == null || puzzle.length ==0 || puzzle[0].length ==0)
   	        return res.size();
   	
   	
   	   
   	    // create a trie tree as a dictioanry
   	    Trie trie = new Trie();
   	    for (String word:DICTIONARY){
   	    	trie.insert(word);
   	    }
   	    
	    
     
        boolean[][] visited = new boolean[puzzle.length][puzzle[0].length];
   	    for (int i = 0; i < puzzle.length; i++)
   	    {
   	       for (int j = 0 ;j < puzzle[0].length; j++)
   	       {
   	             dfs(puzzle, visited, "", i , j, trie,res,0);
   	       }	
   	    }        
         
        //System.out.println(res); // Debug to print out the list of string
        
        return res.size(); 
         
     }
     
     
    /* Fucniton: To find if certain word occur in the dicttionary
	* @parameters:
	*char[][] puzzle, 
	*boolean[][] visited, to judge if this cell have been visited
	*String str, the traversed string so far
	*int i, row index
	*int j, column index
	*Trie trie, a Trie tree holds the dictionary
	*List<String> res, the result of matched words so far
	*int direction, traverse direction
	*  @return: void
	*/
    private static void dfs (char[][] puzzle, boolean[][] visited, String str, int i, int j, Trie trie, List<String> res, int direction)
    {
       	
       	
       	if ( i < 0 || j < 0 || i >= puzzle.length || j >= puzzle[0].length || visited[i][j])
       	    return;
       	    
       	    
       	str = str + puzzle[i][j];
       	if (! trie.startWith(str) )
       	    return;
       	if ( trie.search(str)  )
       	    res.add(str);
       	    
       	    
       	visited[i][j] = true;
       	switch(direction)
       	{
       	    case 0:
       	        dfs(puzzle, visited, str, i, j-1, trie, res,1); // horizontal
       	        dfs(puzzle, visited, str, i, j+1, trie, res,1);// horizontal
       	        dfs(puzzle, visited, str, i+1, j, trie, res,2);//vertvial
       	        dfs(puzzle, visited, str, i-1, j, trie, res,2);//vertical
       	        dfs(puzzle, visited, str, i+1,j+1,trie,res,3);// diagonal
       	        dfs(puzzle, visited, str, i-1, j+1, trie, res,4);//diagonal
       	        dfs(puzzle, visited, str, i-1,j-1,trie, res,5);//diagonal
       	        dfs(puzzle, visited, str, i+1, j-1,trie, res,6);//diagonal       	    
       	       break;
       	    case 1:
       	        dfs(puzzle, visited, str, i, j-1, trie, res,1); // horizontal
       	        dfs(puzzle, visited, str, i, j+1, trie, res,1);// horizontal             	    
       	       break;    
       	    case 2:
         	    dfs(puzzle, visited, str, i+1, j, trie, res,2);//vertvial
       	        dfs(puzzle, visited, str, i-1, j, trie, res,2);//vertical         	    
       	       break;
       	    case 3:
       	        dfs(puzzle, visited, str, i+1,j+1,trie,res,3);// diagonal
       	       break;
       	    case 4:
                dfs(puzzle, visited, str, i-1, j+1, trie, res,4);//diagonal
       	       break;
       	    case 5:
                dfs(puzzle, visited, str, i-1,j-1,trie, res,5);//diagonal
       	       break;
       	    case 6:
                dfs(puzzle, visited, str, i+1, j-1,trie, res,6);//diagonal
       	       break;
       	    default:
       	       return;
       	}
       	visited[i][j] = false;   
       	
    }       
     
     
     public static void main(String []args){
        PuzzleSolver sol1 = new PuzzleSolver();
        char[][] puzzle1= {{'C','A','T'},{'X','Z','T'},{'Y','O','T'}};
        System.out.println("Number of all non-distinct occurrences: "+FindWords(puzzle1));
        char[][] puzzle2={{'C','A','T','A','P','U','L','T'},{'X','Z','T','T','O','Y','O','O'},{'Y','O','T','O','X','T','X','X'}};
        System.out.println("Number of all non-distinct occurrences: "+FindWords(puzzle2));
     }
}

