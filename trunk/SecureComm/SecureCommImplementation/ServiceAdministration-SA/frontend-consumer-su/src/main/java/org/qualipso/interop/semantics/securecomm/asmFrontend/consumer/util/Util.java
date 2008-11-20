package org.qualipso.interop.semantics.securecomm.asmFrontend.consumer.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.activation.DataHandler;
import org.qualipso.interop.semantics.securecomm.asmFrontend.consumer.services.Constants;
import org.apache.servicemix.jbi.util.FileUtil;

/** utilities **/
public class Util implements Constants {
	
	private static final int BUFF_SIZE = 1024;
	private static final byte [] buffer = new byte[BUFF_SIZE];
	
	/** Get the path where the security-mapping ontologies should be saved
	 * 
	 * @param serviceName: service name
	 * @return
	 */
	public static String getFilePathForOWLFiles(String serviceName) {
    	String filePath = "";
    	if(serviceName != null && !"".equals(serviceName)){
    		filePath = File.separator + PATH_TO_SERVICES_DIR + serviceName + "/ontologies/";    	
    	}
    	return filePath;
    }
    
	/** Get the path where the security-mapping jar file (classes implementing the mapping)
	 *  should be saved
	 * 
	 * @param serviceName: service name
	 * @return
	 */
    public static String getFilePathForJARFiles(String serviceName) {
    	String filePath = "";
    	if(serviceName != null && !"".equals(serviceName)){
    		filePath = File.separator + PATH_TO_SERVICES_DIR + serviceName + "/JARs/";    	
    	}
    	return filePath;
    }
    
    /** Get the path where the lifting-grounding stylesheets should be saved
	 * 
	 * @param serviceName: service name
	 * @return
	 */
    public static String getFilePathForXSLFiles(String serviceName) {
    	String filePath = "";
    	if(serviceName != null && !"".equals(serviceName)){
    		filePath = File.separator + PATH_TO_SERVICES_DIR + serviceName + "/XSLTs/";    	
    	}
    	return filePath;
    }
    
    
    /** Get the path where the security-mapping other files (eg. property files)
	 *  should be saved
     * 
     * @param serviceName: service name
     * @return
     */
    public static String getFilePathForOtherFiles(String serviceName) {
    	String filePath = "";
    	if(serviceName != null && !"".equals(serviceName)){
    		filePath = File.separator + PATH_TO_SERVICES_DIR + serviceName + "/Other/";    	
    	}
    	return filePath;
    }
    
    public static String getPathOfJarLibrary(String baseFilePath) {
    	
    	int maxVersion = getLatestDirectory(baseFilePath);
    	return  baseFilePath + File.separator + "data/smx/service-assemblies/" + ASM_SERVICE_ASSEMBLY_NAME +"/version_" 
				+ maxVersion + "/sus/" + ASM_SERVICE_UNIT_TYPE + "/" + ASM_SERVICE_UNIT_NAME + "/lib/";
    }
    
    /** Get the full path to the property file that holds the security-mapping description i.e. name,
     *  implementing class, ontologies etc.
     * 
     * @param basepath: ASM home
     * @param servicename: service name
     * @return: full path and file
     */
    public static String getServiceDescriptionFile(String basepath, String servicename) {
    	return basepath + File.separator + PATH_TO_SERVICES_DIR + servicename 
				+ File.separator + SERVICE_DESCRIPTION_FILE;
    }
    
    /** Get the properties file that contains the available endpoints/services as they are
     *  defined by the ASM administrator.
     * 
     * @param basepath: asm home
     * @return: full path and filename
     */
    public static String getAvailableEndpointsFile(String basepath) {
    	return basepath + File.separator + PATH_TO_COMMON_PROPERTIES + SERVICE_ENDPOINTS;
    }
    
    /** Delete the JAR files that implement the security-mapping identified by "servicename" from the 
     *  securecomm service-assembly library.
     * 
     * @param basepath: ASM home
     * @param servicename: service name
     * @throws Exception
     */
    public static void deleteServiceJarFromASMLib (String basepath, String servicename) throws Exception {
    	
    	FileInputStream fis = new FileInputStream(getServiceDescriptionFile(basepath, servicename));
    	Properties pro = new Properties();
    	pro.load(fis);
    	fis.close();
    	
    	String path = getPathOfJarLibrary(basepath);
    	
    	Enumeration keys = pro.keys();
    	while (keys.hasMoreElements()) {
    		String field = (String)keys.nextElement();
    		String fieldValue = pro.getProperty(field);
    		if (fieldValue.trim().endsWith(".jar")) {
    			File tmp = new File(path + fieldValue);
    			tmp.delete();
    		}
    	}
    }
    
    /** Deletes the Service Directory.
     * 
     * @param basepath: path to dir
     * @param servicename: the name of the service directory.
     */
    public static void deleteServiceDirectory(String basepath, String servicename) {
    	File servicedir = new File(basepath + "/Files/" + servicename);
    	deleteDirectory(servicedir);
    }
    
    
    /** Recursively deletes a directory tree
     * 
     * @param path: <code>File</code> path of the directory to delete
     * @return
     */
    private static boolean deleteDirectory(File path) {

		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}
    
    
    public static void readAndWriteToFile(String serviceAndClassName, String filaPath) throws Exception {
		try {
			BufferedReader in = new BufferedReader(new FileReader(filaPath));
			String str = "";
			boolean isExist = false;
			int count = 0;
			String s1[] = serviceAndClassName.split("=");
			StringBuffer sb = new StringBuffer();
			if (s1 != null && s1.length == 2) {
				while ((str = in.readLine()) != null) {
					if (!"".equals(str) && (str.indexOf("=") != -1)) {
						String s2[] = str.split("=");
						if (s1[0].equals(s2[0])) {
							isExist = true;
							s2[1] = s1[1];
							if (count > 0) {
								sb.append("\n" + s2[0] + "=" + s2[1]);
							} else {
								sb.append(s2[0] + "=" + s2[1]);
							}
						} else {
							if (count > 0) {
								sb.append("\n" + str);
							} else {
								sb.append(str);
							}
						}
						count++;
					}
				}
				in.close();

				if (!isExist) {
					if (count > 0) {
						sb.append("\n" + serviceAndClassName);
						writeFile(sb.toString(), filaPath);
					} else {
						sb.append(serviceAndClassName);
						writeFile(sb.toString(), filaPath);
					}
				} else {
					writeFile(sb.toString(), filaPath);
				}
			}
		} catch (IOException e) {
			throw e;
		}
	}
    
	private static void writeFile(String keyValuePairs, String filaPath) throws Exception{
		try {
	        BufferedWriter out = new BufferedWriter(new FileWriter(filaPath));
	        out.write(keyValuePairs);
	        out.close();
	    } catch (IOException e) {
	    	throw e;
	    }
	}
	
	public static void uploadOtherFiles(String baseFilePath, String fileName, String serviceName, DataHandler dh)
		throws Exception{
    	
		File dir = new File(baseFilePath + Util.getFilePathForOtherFiles(serviceName));
		dir.mkdirs();
		
		File newFile = new File(baseFilePath + Util.getFilePathForOtherFiles(serviceName) + fileName);
		if(newFile.exists()){
			if(newFile.delete()){
				if(newFile.createNewFile()){
					writeFiles(newFile, dh);
				}
			}
		}
		else if(newFile.createNewFile()){
			writeFiles(newFile, dh);
		}
		
		String fileExtension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		if (fileExtension.equalsIgnoreCase(".zip")) {
			System.err.println("uploadOtherFiles Unziping");
			extractZipFile(newFile.getAbsolutePath(), dir.getAbsolutePath());
			newFile.delete();
		}
    }
    
	/** Save all the characteristics/data of the service as a property file inside its directory.
	 * 
	 * @param baseFilePath: path to file
	 * @param serviceName: the name of the service
	 * @param prop: service properties
	 */
	public static void writeServiceDescription(String baseFilePath, String serviceName, Properties prop)
		throws Exception {
		
		String path = baseFilePath + File.separator + PATH_TO_SERVICES_DIR + serviceName + File.separator;
		FileOutputStream fos = new FileOutputStream(path + SERVICE_DESCRIPTION_FILE);
		prop.store(fos, "Service Description");
	}
	
	/** Uploads the ontology files. Unzips them if they are bundled in a zip file. 
	 * 
	 * @param baseFilePath: ASM home
	 * @param fileName: name of the ontology file
	 * @param serviceName: service name
	 * @param dh: <code>DataHandler</code>
	 * @throws Exception
	 */
    public static void uploadSourceOntologyFiles(String baseFilePath, String fileName, String serviceName, DataHandler dh)
    	throws Exception {
    	
    	File dir = new File(baseFilePath + Util.getFilePathForOWLFiles(serviceName));
		dir.mkdirs();
		File newFile = new File(baseFilePath + Util.getFilePathForOWLFiles(serviceName) + fileName);
		if(newFile.exists()){
			if(newFile.delete()){
				if(newFile.createNewFile()){
					writeFiles(newFile, dh);
				}
			}
		}
		else if(newFile.createNewFile()){
			writeFiles(newFile, dh);
		}
		
		String fileExtension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		if (fileExtension.equalsIgnoreCase(".zip")) {
			System.err.println("uploadOtherFiles Unziping");
			extractZipFile(newFile.getAbsolutePath(), dir.getAbsolutePath());
			newFile.delete();
		}
    }
    
    
    public static void uploadJarFilesToRestectedDirectory(String baseFilePath, String fileName, String serviceName, DataHandler dh) 
    	throws Exception {
    	
    	String sourceFilePath = writeToServiceDirectory(baseFilePath, fileName, serviceName, dh);
    	String destinationFilePath = getPathOfJarLibrary(baseFilePath);
    	copy(sourceFilePath, destinationFilePath + fileName);
    }
    
    
    /** Writes the content of a DataHandler as a file in the security-mapping directory.
     * 
     * @param baseFilePath: ASM home
     * @param fileName: name of the file
     * @param serviceName: service name
     * @param dh: <code>DataHandler</code>
     * @return: the full path to the written file
     * @throws Exception
     */
    public static String writeToServiceDirectory(String baseFilePath, String fileName, String serviceName, DataHandler dh)
    	throws Exception {
    	
    	String filePath = "";
    	File dir = new File(baseFilePath+Util.getFilePathForJARFiles(serviceName));
		dir.mkdirs();
		File newFile = new File(baseFilePath+Util.getFilePathForJARFiles(serviceName)+fileName);
		if(newFile.exists()){
			if(newFile.delete()){
				if(newFile.createNewFile()){
					writeFiles(newFile, dh);
				}
			}
		}
		else if(newFile.createNewFile()){
			writeFiles(newFile, dh);
		}
		filePath = newFile.getAbsolutePath();
		return filePath;
    }
    
    
    /** Writes the content of a XSLT DataHandler as a file in the security-mapping directory.
     * 
     * @param baseFilePath: ASM home
     * @param fileName: name of the file
     * @param serviceName: service name
     * @param dh: <code>DataHandler</code>
     * @return: the full path to the written file
     * @throws Exception
     */
    public static String writeXSLTToServiceDirectory(String baseFilePath, String fileName, String serviceName, DataHandler dh)
    	throws Exception {
    	
    	String filePath = "";
    	File dir = new File(baseFilePath+Util.getFilePathForXSLFiles(serviceName));
		dir.mkdirs();
		File newFile = new File(baseFilePath+Util.getFilePathForXSLFiles(serviceName)+fileName);
		if(newFile.exists()){
			if(newFile.delete()){
				if(newFile.createNewFile()){
					writeFiles(newFile, dh);
				}
			}
		}
		else if(newFile.createNewFile()){
			writeFiles(newFile, dh);
		}
		filePath = newFile.getAbsolutePath();
		return filePath;
    }
    
    
    public static void writeFiles(File f, DataHandler dh)throws Exception{
    	FileOutputStream fos = new FileOutputStream(f);
        FileUtil.copyInputStream(dh.getDataSource().getInputStream(), fos);
        fos.close();
    }
    
    public static int getLatestDirectory(String filePath) {
		int maxCount = 0;
		File f1 = new File(filePath + "/" + "data/smx/service-assemblies/" + ASM_SERVICE_ASSEMBLY_NAME + "/");
		File files[] = f1.listFiles();
		if(files != null && files.length != 0){
			for(int index = 0; index<files.length; index++){
				File f2 =(File)files[index];
				boolean isExist = f2.isDirectory();
				if(isExist== true) {						
					String fileName[] = f2.getName().split("_");
					int temp = Integer.parseInt(fileName[1]);
					if(temp>maxCount){
						maxCount = temp;
					}
				}
			}
		}	
		return maxCount;
	}
    
    //Copies files from one location to another
    public static void copy(String sourceFilePath, String destFilePath) throws IOException {
     InputStream in = null;
     OutputStream out = null;
     try {
        
        in = new FileInputStream(sourceFilePath);
        out = new FileOutputStream(destFilePath);
        while (true) {
        	synchronized (buffer) {
		        int amountRead = in.read(buffer);
		        if (amountRead == -1) {
		            break;
		         }
		        out.write(buffer, 0, amountRead);
        	}
        }
     }
     catch(IOException ex){
    	 throw ex;
     }
     finally {
        if (in != null) {
           in.close();
        }
        if (out != null) {
           out.close();
        }
     }
   }  
    
    
    /** Extracts the content of a zip file. If some of files are included within a/some directory/es
     *  those are created and then the file are extracted.
     * 
     * @param fullPathOfZipFile: the location of the zip file to extract
     * @param locationToExtractTo: where to extract
     * @throws Exception
     */
    public static void extractZipFile(String fullPathOfZipFile, String locationToExtractTo) throws Exception {
		
		ZipFile zipper = new ZipFile(fullPathOfZipFile);
		Enumeration enumer = zipper.entries();
		while (enumer.hasMoreElements()) {
			 ZipEntry entry = (ZipEntry)enumer.nextElement();
			 String entryName = entry.getName();

			 if (entryName.contains(File.separator)) {
				 // has a (hopefully) relative path inside the zip file. so create that path/directory
				 String dirPath = entryName.substring(0, entryName.lastIndexOf(File.separator));
				 File tmpFile = new File(locationToExtractTo + File.separator + dirPath);
				 tmpFile.mkdirs();
			 } 
			 
			 
			 if (! entryName.endsWith(File.separator)) {
				 FileOutputStream fos = new FileOutputStream(locationToExtractTo + File.separator + entryName);
				 
				 BufferedInputStream is = new BufferedInputStream(zipper.getInputStream(entry));
				 int count;
				 BufferedOutputStream dest = new BufferedOutputStream(fos, BUFF_SIZE);
				 
				 byte data[] = new byte[BUFF_SIZE];
				 while ((count = is.read(data, 0, BUFF_SIZE)) != -1) {
					 dest.write(data, 0, count);
				 }
				 
				 dest.flush();
				 fos.flush();
				 fos.close();
				 is.close();
			 }
		 }
	}
    
     
	/** Return the service properties of type File in a specific order. as a List.
	 * 
	 * @return <code>List</code>
	 */
	/*public static List serviceFileFieldProperties() {
		
		List pro = new ArrayList(7);
		
		pro.add(TRANSFORMATION_CODE);
		pro.add(SOURCE_ONTOLOGY);
		pro.add(TARGET_ONTOLOGY);
		pro.add(ONTOLOGY_MAPPING);
		pro.add(COLLATERAL_FILES);
		pro.add(MAPPING_RULES_ONT);
		pro.add(MAPPING_RULES_CODE);
		
		return pro;
	}*/
	
	/** Return the service properties of type String in a specific order. as a List.
	 * 
	 * @return
	 */
	/*public static List serviceStringFieldProperties() {
		
		List pro = new ArrayList(2);
		
		pro.add(SERVICE_NAME);
		pro.add(CLASS_NAME);
		
		return pro;
	}*/
	
	
	/** Get the endpoints defined by the ASM administrator.
	 * 
	 * @param basepath: ASM home
	 * @return: <code>Set</code> of available endpoints
	 * @throws Exception
	 */
	public static Set getServicesDefinedByAdmin(String basepath) throws Exception {
		
		String tmp = getAvailableEndpointsFile(basepath);
		FileInputStream fis = new FileInputStream(tmp);
		Properties pr = new Properties();
		pr.load(fis);
		fis.close();
		return pr.keySet();
	}
	
	
}
