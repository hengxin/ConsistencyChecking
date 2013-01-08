package cn.edu.nju.moon.consistency.ui;

import java.io.File;

import cn.edu.nju.moon.consistency.checker.ReadIncChecker;
import cn.edu.nju.moon.consistency.datastructure.GlobalActiveWritesMap;
import cn.edu.nju.moon.consistency.model.GlobalData;
import cn.edu.nju.moon.consistency.model.observation.BasicObservation;
import cn.edu.nju.moon.consistency.model.operation.BasicOperation;
import cn.edu.nju.moon.consistency.model.operation.ReadIncOperation;
import cn.edu.nju.moon.consistency.model.process.BasicProcess;

/**
 * @description UI for {@link ReadIncChecker}; visualization of the data structures 
 * 	used in checking algorithm
 * 
 *  The UI is based on GraphViz (with .dot).
 *  
 * @author hengxin
 * @date 2013-1-2
 * @design_pattern Singleton
 */
public class DotUI
{
	private GraphViz viz = null;
	private final String type = "pdf";
	private File out = null;
	private int counter = 0;	// counter for W'WR order edges

	private static DotUI instance = null;
	private DotUI()
	{
		this.viz = new GraphViz();
		this.viz.addln(viz.start_graph());
	}

	public static DotUI getInstance()
	{
		if (instance == null)
			instance = new DotUI();

		return instance;
	}

	/**
	 * execute external GraphViz program
	 * @param out file for output
	 */
	public void execute(String out)
	{
		if (GlobalData.VISUALIZATION)
		{
			// add title
			this.viz.addln("labelloc = \"t\";");
			this.viz.add("label = \"" + out + "\"");
			this.viz.addln(";");
			
			// finish the graph
			this.viz.addln(this.viz.end_graph());
			
			System.out.println(viz.getDotSource());	/** for test **/

			// generate the graph
			this.out = new File("data/" + out + "." + this.type);
			viz.writeGraphToFile(viz.getGraph(viz.getDotSource(), type), this.out);

			// reset for reuse
			DotUI.instance = null;
			this.counter = 0;
		}
	}

	/**
	 * visualize {@link BasicObservation}
	 * 
	 * @param riob {@link BasicObservation} to visualize
	 */
	public void visual_ob(BasicObservation ob)
	{
		if (GlobalData.VISUALIZATION)
		{
			int size = ob.getSize();

			viz.addln("ranksep = 1.0; size = \"10,10\";");	// drawing with constrained ranks
			viz.addln("{");	// pids
			viz.addln("node [shape = plaintext, fontsize = 20];");
			viz.add("GAWM1 -> GAWM2 -> ");
			for (int i = 0; i < size - 1; i++)
				viz.add(i + " -> ");
			viz.addln((size - 1) + ";");
			viz.addln("}");

			viz.addln("node [shape = box];");	// shape of nodes

			for (int pid : ob.getProcMap().keySet())
			{
				this.visual_proc(ob.getProcess(pid));
			}
		}
	}

	/**
	 * visualize {@link BasicProcess}
	 * 
	 * @param ripro {@link BasicProcess} to visualize
	 */
	private void visual_proc(BasicProcess ripro)
	{
		viz.add("{");
		viz.add("rank = same;");	// in the same process
		viz.add(ripro.getPid() + ";");
		for (BasicOperation op : ripro.getOpList())
			this.visual_op(op);
		viz.addln("}");
	}

	/**
	 * visualize {@link BasicOperation}
	 * @param op {@link BasicOperation} to visualize
	 */
	private void visual_op(BasicOperation op)
	{
		viz.add(op.toString() + ";");
	}

	/**
	 * add program order edge from @param from_op to @param to_op
	 * 
	 * @param from_op source of edge
	 * @param to_op   target of edge
	 */
	public void addPOEdge(BasicOperation from_op, BasicOperation to_op)
	{
		//    	this.viz.addComment("Program Order:");
		if(GlobalData.VISUALIZATION)
			this.viz.addln(from_op.toString() + " -> " + to_op.toString() + ";");
	}

	/**
	 * add WriteTo order edge from @param from_op to @param to_op
	 * @param from_op source of edge
	 * @param to_op   target of edge
	 */
	public void addWritetoEdge(BasicOperation from_op, BasicOperation to_op)
	{
		//    	this.viz.addComment("Write to Order:");
		if (GlobalData.VISUALIZATION)
			this.viz.addln(from_op.toString() + " -> " + to_op.toString() + "[color = blue];");
	}

	/**
	 * add W'WR edge from @param from_op to @param to_op
	 * @param from_op source of edge
	 * @param to_op	  target of edge
	 */
	public void addWprimeWREdge(BasicOperation from_op, BasicOperation to_op)
	{
		if (GlobalData.VISUALIZATION)
		{
			this.viz.addComment("W'WR Order:");
			this.viz.addln(from_op.toString() + " -> " + to_op.toString() + 
					"[style = dashed, color = red, label = \"" + (++this.counter) + "\"" + "];");
		}
	}

	/**
	 * visualization for {@link GlobalActiveWritesMap} @param gawm
	 * 
	 * @param gawm {@link GlobalActiveWritesMap} to visualized
	 * @param name string form of READ {@link ReadIncOperation} being checked
	 * @param id   1: after computing interval; 2: after applying W'WR order	 	
	 */
	public void addGAWM(GlobalActiveWritesMap gawm, String name, int id)
	{
		if (GlobalData.VISUALIZATION)
		{
			if (id == 1)
				this.viz.addln("{rank = same; GAWM1; \"" + this.constructGAWMRecord(gawm, name, id) + "\";}");
			else
				this.viz.addln("{rank = same; GAWM2; \"" + this.constructGAWMRecord(gawm, name, id) + "\";}");
		}
	}

	/**
	 * construct struct (i.e., shape = record) for @param gawm 
	 * @param gawm {@link GlobalActiveWritesMap} to be visualized
	 * @param name
	 * @param id
	 * 
	 * @param identifier for struct
	 */
	private String constructGAWMRecord(GlobalActiveWritesMap gawm, String name, int id)
	{
		String identifier = name + id;
		String newline = " \\" + "n ";
		String structLbl = gawm.toString().replace(";", newline);

		this.viz.addln("subgraph struct");
		this.viz.addln("{");
		this.viz.addln("\t" + "node [shape = record];");
		this.viz.addln("\t" + identifier + 
				" [shape = record, label = \"" + name + " | " + structLbl + "\"];");
		this.viz.addln("}");

		return identifier;
	}
}
