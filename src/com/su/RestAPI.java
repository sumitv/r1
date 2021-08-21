package com.su;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.vaya.assetmanager.util.FileHandlerUtil;

/**
 *
 */
@Path("/")
public class RestAPI {

	@GET
	@Path("/projects/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getProjects(@PathParam("param") String type) throws IOException {
		String response=ocb+sqt+type+sqt+cln+osb;
			List<String> list = getDirList(FileHandlerUtil.getRootDir()+"/"+type);
			for (String s : list) {
				response += ocb;
					response+=sqt+"project"+sqt+cln+sqt+s+sqt;
				response += ccb;
				if(list.indexOf(s)<(list.size()-1))
				response += ",";
			}
		response+=csb+ccb;

		return response;
	}

	String sqt="\"";
	String cln=":";
	String osb="[";
	String csb="]";
	String ocb="{";
	String ccb="}";
	
	@GET
	@Path("/data/{prjType}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getData(@PathParam("prjType") String prjType) {
		String response=ocb;
		System.out.println(response);
		
		response+=sqt+prjType+sqt+cln+ocb;
		List<String> prjList = getDirList(FileHandlerUtil.getRootDir()+"/"+prjType);
		for(String prjName:prjList)
		{
			response+=sqt+prjName+sqt+cln+ocb;
			System.out.println(response);
				String prjPath = FileHandlerUtil.getRootDir()+"/"+prjType+"/"+prjName;
				List<String> list = getDirList(prjPath);
				for(String folder: list) {
					response=response+ sqt+ folder+sqt+cln+osb;
					System.out.println(response);
						List<String> fileList = getFilesList(prjPath+"/"+folder);
						for(String file: fileList) {
							response+=ocb+sqt+"url"+sqt+cln+sqt+file+sqt+ccb;
							System.out.println(response);
							if(fileList.indexOf(file)<(fileList.size()-1)){
								response+=",";
								System.out.println(response);
							}
						}
					response+=csb;
					System.out.println(response);
					if(list.indexOf(folder)<(list.size()-1)){
					response+=",";
					System.out.println(response);
					}
				}
			response+=ccb;
			if(prjList.indexOf(prjName)<(prjList.size()-1)){
				response+=",";
			}
		}	
		response+=ccb+ccb;
		System.out.println(response);
		return response;
	}

	@GET
	@Path("/verify")
	@Produces(MediaType.TEXT_PLAIN)
	public Response verifyRESTService(InputStream incomingData) {
		String result = "Successfully started..";
		return Response.status(200).entity(result).build();
	}

	public List<String> getDirList(String path) {
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});
		List<String> projects = new ArrayList<String>();
		for (int i = 0; i < listOfFiles.length; i++) {
			projects.add(listOfFiles[i].getName());
		}
		return projects;
	}

	public List<String> getPath() throws UnsupportedEncodingException {

		String path = this.getClass().getClassLoader().getResource("")
				.getPath();
		String dataPath = URLDecoder.decode(path, "UTF-8");
		System.out.println(dataPath);
		dataPath = dataPath.replace("/WEB-INF/classes", "/data");
		return getDirList(dataPath);
	}

	public static List<String> getFilesList(String path) {
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isFile();
			}
		});
		List<String> projects = new ArrayList<String>();
		for (int i = 0; i < listOfFiles.length; i++) {
			projects.add(listOfFiles[i].getName());
		}
		return projects;
	}
	
}