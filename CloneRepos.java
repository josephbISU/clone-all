

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;



public class CloneRepos
{

	public static void main(String[] args)
	{
		File repolistfile = new File("SuperSmallRepoNames1.txt"); //OSX
		//File repolistfile = new File("/GitRepos/RepoNames.txt"); //Linux
		String accesstoken = "0746cf2da8f93f169aee98a1c080811188e7b009";
		String baseurl ="https://github.com/";
		Scanner sc = null;
		PrintWriter doneout = null;
		PrintWriter errorout = null;
		PrintWriter oomeout = null;
		try {
			doneout = new PrintWriter(new BufferedWriter(new FileWriter("Done.txt", true)));
			errorout = new PrintWriter(new BufferedWriter(new FileWriter("ErrorNames.txt", true)));
			oomeout = new PrintWriter(new BufferedWriter(new FileWriter("OOMENames.txt", true)));
			sc = new Scanner(repolistfile);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(sc.hasNextLine()) {
			String reponame = sc.nextLine();
			String localfilepath = "/Users/josephb/Documents/workspace/Clone/GitRepos/"+reponame; //OSX
			//String localfilepath = "/GitRepos/" + reponame; //Linux
			String url = baseurl + reponame + ".git";
		
			try
			{
				File gitloc = new File(localfilepath+"/.git");
				if(!gitloc.exists())
				{
				
				File localPath = new File(localfilepath);
				Git.cloneRepository()
					.setBare(true)
			        .setURI(url)
			        .setDirectory(localPath)
			        .call();
				System.out.println(reponame);
				//add name to done file
				try(PrintWriter out2 = new PrintWriter(new BufferedWriter(new FileWriter("Done.txt", true)))) {
				    doneout.println(reponame);
				}catch (IOException e) {
				   	e.printStackTrace();
				   	break;
				}
				//print name and number with pretty formatting
				}
				
			} catch (GitAPIException e)
			{
				//add to error list and continue
				System.out.println(e.getMessage());
				errorout.println(reponame);
//				try(PrintWriter out2 = new PrintWriter(new BufferedWriter(new FileWriter("ErrorNames.txt", true)))) {
//				    out2.println(reponame);
//				    out2.close();
//				    continue;
//				}catch (IOException e2) {
//				   	e2.printStackTrace();
//				   	break;
//				}
				
			} catch (JGitInternalException e){
				//ignore and continue
				if(e.getMessage().contains("already exists"))
					continue;
				else {
					System.out.println(e.getMessage());
					errorout.println(reponame);
				}
			} catch (OutOfMemoryError e){
				System.out.println(e.getMessage());
				oomeout.println(reponame);
			} catch (Exception e){
				//This is a real error so break
				e.printStackTrace();
				break;
			}
			
		}
		sc.close();
		doneout.close();
		oomeout.close();
		errorout.close();
	}

}
