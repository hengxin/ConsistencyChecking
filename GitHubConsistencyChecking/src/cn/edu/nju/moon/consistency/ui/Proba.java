package cn.edu.nju.moon.consistency.ui;

import java.io.File;

public class Proba
{
   public static void main(String[] args)
   {
      Proba p = new Proba();
      p.start();
   }

   /**
    * Construct a DOT graph in memory, convert it
    * to image and store the image in the file system.
    */
   private void start()
   {
      GraphViz gv = new GraphViz();
      gv.addln(gv.start_graph());
      gv.addln("A -> B;");
      gv.addln("A -> C;");
      gv.addln(gv.end_graph());
      System.out.println(gv.getDotSource());
      
      String type = "pdf";
      File out = new File("D:/eclipse/thirdjars/graphviz-java-api/tmp/simple." + type);    // Windows
      gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
   }
   
}
