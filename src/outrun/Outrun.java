package outrun;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Find path in outrun!
 * @author verne_000
 */
public class Outrun
{

    private static Node currentMaxNode;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        System.out.println("Hit it! tidididimdimdim-dim-dim");
        List<Node> outrun = BuildTree("tree.txt");
        currentMaxNode = outrun.get(0);
        TurboTurbo(outrun, null, 0);
        System.out.print("\nReitti: ");
        PrintTurbo(outrun, currentMaxNode);
        System.out.println("\nTykkäyksien lukumäärä yhteensä: " + currentMaxNode.Weight);
        System.out.println("The end...");
    }
    

    /**
     * Build a heapish tree thing from the input file
     * @return 
     */
    private static List<Node> BuildTree(String filepath)
    {
        List<Node> list = new ArrayList<>();
        int i = 1;
        try (BufferedReader br = new BufferedReader(new FileReader(filepath)))
        {
            String line = br.readLine();    // Skip header...    
            System.out.println(line);
            while (true)
            {
                line = br.readLine();
                if (line == null)
                {
                    break;
                }

                String[] values = line.split(" ");
                for (String value : values)
                {
                    Node node = new Node();
                    node.Value = Integer.parseInt(value);
                    node.Level = i;
                    list.add(node);
                }
                i ++;
            }
            System.out.println("\nLevels in tree: " + (i - 1) + " total nodes: " + list.size());
        } catch (IOException ex)
        {
            Logger.getLogger(Outrun.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    
    /**
     * Recursively find the heaviest path through the tree
     * @param outrun
     * @param parent
     * @param index 
     */
    private static void TurboTurbo(List<Node> outrun, Node parent, int index)
    {
        if (outrun.size() <= index)
        {
            return; // Reached bottom
        }
        Node node = outrun.get(index);
        int parentweight = parent == null ? 0 : parent.Weight;

        // No point in checking nodes which already have a greater weight. Without this it would take around 700 trillion years on my laptop
        if (node.Weight >= parentweight + node.Value)
        {
            return;
        }

        node.Weight = parentweight + node.Value;
        if (node.Weight > currentMaxNode.Weight)
        {
            currentMaxNode = node;
        }

        node.Parent = parent;

        TurboTurbo(outrun, node, index + node.Level);
        TurboTurbo(outrun, node, index + node.Level + 1);
    }

    
    /**
     * Recursively reconstruct the longest path
     * @param outrun
     * @param node 
     */
    private static void PrintTurbo(List<Node> outrun, Node node)
    {
        if (node.Parent != null)
        {
            PrintTurbo(outrun, node.Parent);
        }
        System.out.print(node.Value + " ");
    }
}
