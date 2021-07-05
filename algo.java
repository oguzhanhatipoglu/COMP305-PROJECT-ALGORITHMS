//import java.io.File;  
//import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

class Test{
    public int totalBoxes;
    public int bigPrize;
    public List<String> preferences;
}


class Graph
{
    private int vertex;
    protected ArrayList<LinkedList<Integer>> list;
    
    public Graph(int vertex) {
        this.vertex = vertex;
        list = new ArrayList<LinkedList<Integer>>(vertex);
        //list = new LinkedList[vertex];
        for (int i = 0; i <vertex ; i++) {
            list.add(new LinkedList<>());
        }
    } 
    public void addEdge(int source, int destination){
        //add edge
        list.get(source).addFirst(destination);
    }

    String iterativeDFS(int startNode){
        //set all the vertices as not-visited at the start
        boolean visited[] = new boolean[vertex];
        for(int i = 0; i < vertex; i++ ){
            visited[i] = false;
        }
        Stack<Integer> dfsStack = new Stack<>();
        Stack<Integer> resultStack= new Stack<>();

        dfsStack.push(startNode);
        while(dfsStack.empty() == false){
            startNode = dfsStack.peek();
            dfsStack.pop();
            if(visited[startNode] == false){
                resultStack.push(startNode); // add to the path
                visited[startNode] = true; //set as visited
            }

            Iterator<Integer> iterator = list.get(startNode).iterator();
            while(iterator.hasNext()){
                int v = iterator.next();
                //push to the stack if the vertex is not visited
                if(visited[v] == false){
                    dfsStack.push(v);
                }
            }
        }
        //if the path doesn't contain all the vertices, not possible
        if(resultStack.size() != vertex){
            return "IMPOSSIBLE";
        }
        StringBuilder str = new StringBuilder();
        // reverse the order of the stack for the correct solution
        while(!resultStack.empty()){
            int element = resultStack.pop();
            str.append(String.valueOf(element));
            str.append(" ");
        }
        return str.toString();
    }
}

public class algo{
    public static void main(String[] args) throws IOException{
        final String OUTPUTFILE = "small-testdata.out";
        String inputFile = args[0];
        //read all the file context
        String[] content = new String(Files.readAllBytes(Paths.get(inputFile))).split("\n"); //read contents of the file to string

        int totalTest = Integer.parseInt(content[0]);
        int cur = 1;
        List<Test> testCases = new ArrayList<Test>(); 

        for (int i =0; i < totalTest; i++){
            Test t = new Test();
            int totalBoxes = Integer.parseInt(content[cur].split(" ")[0]);
            int bigPrize = Integer.parseInt(content[cur].split(" ")[1]);
            cur++;
            t.totalBoxes = totalBoxes;
            t.bigPrize = bigPrize;
            t.preferences = new ArrayList<String>();
            for(int j =0; j < totalBoxes; j++){
                t.preferences.add(content[j+cur]);
            }
            testCases.add(t); 
            cur += totalBoxes;
        }
        String result;
        StringBuilder builder;
        for (int i =1; i <= totalTest; i++){
            builder = new StringBuilder();
            builder.append("Case #" + i + ": ");
            Graph g = constructGraph(testCases.get(i-1));
            result = g.iterativeDFS(testCases.get(i-1).bigPrize);
            builder.append(result);
            builder.append("\n");
            writeToFile(OUTPUTFILE, builder.toString());
        }

    }
    public static void writeToFile(String fileName, String content){
        try{
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch(IOException e){
            System.out.println("Error writing to a file");
        }
    }
    public static Graph constructGraph(Test test){
       // List<Integer> seen = new ArrayList<Integer>();
        int totalBoxes = test.totalBoxes;
        Graph graph = new Graph(totalBoxes);
        for(int i = 0; i < totalBoxes; i++ )
            for(int j = 0; j < test.preferences.get(i).length(); j++){
                if(test.preferences.get(i).charAt(j) == 'Y'){
                    graph.addEdge(i , j);
                }
            }
        
        return graph;
    }

}


