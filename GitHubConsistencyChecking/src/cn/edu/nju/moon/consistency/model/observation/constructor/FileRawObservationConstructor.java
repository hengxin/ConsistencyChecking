package cn.edu.nju.moon.consistency.model.observation.constructor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.StringTokenizer;

import cn.edu.nju.moon.consistency.model.GlobalData;
import cn.edu.nju.moon.consistency.model.observation.BasicObservation;
import cn.edu.nju.moon.consistency.model.operation.BasicOperation;
import cn.edu.nju.moon.consistency.model.process.BasicProcess;

/**
 * construct {@link BasicObservation} from file
 *
 * @author hengxin
 * @date 2012-12-8
 */
public class FileRawObservationConstructor implements IRawObservationConstructor
{
	private String fileName = null;
	private String ob_id = null;	// id of generated observation
	
	/**
	 * construct {@link BasicObservation} from file
	 * 
	 * @param fileName file to be read
	 */
	public FileRawObservationConstructor(String fileName)
	{
		this.fileName = fileName;
		GlobalData.VARSET = new HashSet<String>();
	}

	@Override
	public BasicObservation construct()
	{
		File file = new File(fileName);
		this.ob_id = file.getName();
		
		BufferedReader reader = null;

		String process = null;
		StringTokenizer st = null;
		String token = null;

		BasicObservation rob = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));

			rob = new BasicObservation();
			int pid = 0;
			while((process = reader.readLine()) != null)
			{
				BasicProcess rp = new BasicProcess(pid);
				st = new StringTokenizer(process);
				while(st.hasMoreTokens())
				{
					token = st.nextToken();
					rp.addOperation(new BasicOperation(token));
				}
				rob.addProcess(pid, rp);
				pid++;
			}
			reader.close();
		}
		catch(IOException ioe)
		{
			System.err.println("Failed to read this file: " + fileName);
			ioe.printStackTrace();
			System.exit(1);  
		}

		return rob;
	}

	@Override
	public String get_ob_id()
	{
		return this.ob_id;
	}

}
