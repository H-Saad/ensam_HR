package com.hr.springboot.service_module.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.binary.Hex;
import org.docx4j.jaxb.Context;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.io.SaveToZipFile;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.springboot.config.CONSTS;
import com.hr.springboot.service_module.models.Document;
import com.hr.springboot.service_module.models.Var;
import com.hr.springboot.service_module.repositories.DocRepo;
import com.hr.springboot.userData_module.models.Role;
import com.hr.springboot.userData_module.models.Type_personnel;
import com.hr.springboot.userData_module.models.User;
import com.hr.springboot.userData_module.repositories.UserRepo;
import com.hr.springboot.userData_module.services.RoleService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

@Service
public class DocService {
	
	@Autowired
	UserRepo ur;
	
	@Autowired
	DocRepo dr;
	
	@Autowired
	RoleService rs;
	
	public String input_to_var(User u, String input) {
		
		User director = ur.findByRole(CONSTS.L1_ROLE).get();
		User sec = ur.findByRole(CONSTS.L2_ROLE).get();
		User rh = ur.findByRole(CONSTS.L3_ROLE).get();
		
		switch(input.toLowerCase()) {
		case "db_codeetab":
			return ""+u.getCode_etablissement();
		case "db_codeannee":
			return ""+u.getCode_annee();
		case "db_ppr":
			return ""+u.getPpr();
		case "db_nom":
			return u.getNom();
		case "db_prenom":
			return u.getPrenom();
		case "db_fullname":
			return u.getNom() + " " + u.getPrenom();
		case "db_genre":
			return u.getGenre();
		case "db_email":
			return u.getEmail();
		case "db_cin":
			return u.getCin();
		case "db_daten":
			return u.getDate_naissance().toString();
		case "db_lieun":
			return u.getLieu_naissance();
		case "db_coden":
			return u.getCode_nationalite();
		case "db_codeg":
			return ""+u.getCode_grade();
		case "db_typepers":
			Set<Type_personnel> t = u.getType_personnel();
			String ret = "";
			for(Type_personnel ty : t) {
				ret = ret + " " +ty.getType();
			}
			return ret;
		case "db_dater":
			return u.getDate_recrutement().toString();
		case "db_dateaffm":
			return u.getDate_affectation_MESRSFC().toString();
		case "db_dateaffe":
			return u.getDate_affectation_enseignement().toString();
		case "db_coded":
			return ""+u.getCode_departement();
		case "db_dept":
			return u.getDepartement();
		case "db_ndip":
			return ""+u.getNombre_diplomes();
		case "db_dip":
			return u.getDiplome();
		case "db_spe":
			return u.getSpecialite();
		case "db_univ":
			return u.getUniv_etablissement_diplomate();
		case "db_fcte":
			return u.getFonction_exerce();
		case "db_serva":
			return u.getService_affectation();
		case "db_grade":
			return u.getGrade();
		case "db_echelon":
			return u.getEchelon();
		case "db_dateech":
			return u.getDate_effet_echelon().toString();
		case "db_arnom":
			return u.getAR_nom();
		case "db_arprenom":
			return u.getAR_prenom();
		case "db_arfullname":
			return u.getAR_nom() + " " +u.getAR_prenom();
		case "db_directnom":
			return director.getNom();
		case "db_directprenom":
			return director.getPrenom();
		case "db_directfull":
			return director.getNom() + " " + director.getPrenom();
		case "db_ardirectnom":
			return director.getAR_nom();
		case "db_ardirectprenom":
			return director.getAR_prenom();
		case "db_ardirectfull":
			return director.getAR_nom() + " " + director.getAR_prenom();
		case "db_secnom":
			return sec.getNom();
		case "db_secprenom":
			return sec.getPrenom();
		case "db_secfull":
			return sec.getNom() + " " + sec.getPrenom();
		case "db_arsecnom":
			return sec.getAR_nom();
		case "db_arsecprenom":
			return sec.getAR_prenom();
		case "db_arsecfull":
			return sec.getAR_nom() + " " + sec.getAR_prenom();
		case "db_rhnom":
			return rh.getNom();
		case "db_rhprenom":
			return rh.getPrenom();
		case "db_rhfull":
			return rh.getNom() + " " + rh.getPrenom();
		case "db_arrhnom":
			return rh.getAR_nom();
		case "db_arrhprenom":
			return rh.getAR_prenom();
		case "db_arrhfull":
			return rh.getAR_nom() + " " + rh.getAR_prenom();
		case "db_pronom":
			if(u.getGenre().toLowerCase().equals("masculin")) return "Mr";
			else return "Mme";
		case "db_numtel":
			return u.getNum_tel();
		case "db_currdate":
			return LocalDate.now().toString();
		default:
			return "Null";
		}
	}
	
	public List<Document> getDocList(Set<Role> s){
		String y = "";
		for(Role r : s) {
			y = r.getRoleName();
		}
		return dr.findByRole(y);
	}
	
	public ArrayList<Var> getDocVars(Document d) throws CsvValidationException, IOException{
		ArrayList<Var> ret = new ArrayList<Var>();
		String src = System.getProperty("user.dir")+CONSTS.TEMPLATES_DIR+d.getTitle()+"/var.csv";
		removeBom(Paths.get(src));
		CSVReader reader = new CSVReader(new FileReader(src, StandardCharsets.UTF_8));
		String[] nxtLine;
		while((nxtLine = reader.readNext()) != null) {
			Var temp = new Var();
			temp.setVarname(nxtLine[0]);
			temp.setVartype(nxtLine[1]);
			temp.setDescription(nxtLine[2]);
			temp.setNeeds_form(Boolean.parseBoolean(nxtLine[3]));
			//System.out.println(nxtLine[0]+"\t"+nxtLine[1]+"\t"+nxtLine[2]+"\t"+nxtLine[3]);
			ret.add(temp);
			
		}
		System.out.println("getDocVars over successfully");
		return ret;
	}
	
	private static boolean isContainBOM(Path path) throws IOException {

	      if (Files.notExists(path)) {
	          throw new IllegalArgumentException("Path: " + path + " does not exists!");
	      }

	      boolean result = false;

	      byte[] bom = new byte[3];
	      try (InputStream is = new FileInputStream(path.toFile())) {

	          // read 3 bytes of a file.
	          is.read(bom);

	          // BOM encoded as ef bb bf
	          String content = new String(Hex.encodeHex(bom));
	          if ("efbbbf".equalsIgnoreCase(content)) {
	              result = true;
	          }

	      }

	      return result;
	  }

	  private static void removeBom(Path path) throws IOException {

	      if (isContainBOM(path)) {

	          byte[] bytes = Files.readAllBytes(path);

	          ByteBuffer bb = ByteBuffer.wrap(bytes);

	          System.out.println("Found BOM!");

	          byte[] bom = new byte[3];
	          // get the first 3 bytes
	          bb.get(bom, 0, bom.length);

	          // remaining
	          byte[] contentAfterFirst3Bytes = new byte[bytes.length - 3];
	          bb.get(contentAfterFirst3Bytes, 0, contentAfterFirst3Bytes.length);

	          System.out.println("Remove the first 3 bytes, and overwrite the file!");

	          // override the same path
	          Files.write(path, contentAfterFirst3Bytes);

	      } else {
	          System.out.println("This file doesn't contains UTF-8 BOM!");
	      }

	  }
	  
	  public String generate_doc(User u,Document d, HashMap<String, String> mappings) throws Exception {
		  org.docx4j.wml.ObjectFactory foo = Context.getWmlObjectFactory();

			// Input docx has variables in it: ${colour}, ${icecream}
			String inputfilepath = System.getProperty("user.dir") + CONSTS.TEMPLATES_DIR + d.getTitle() + "/" + d.getTitle() + ".docx";

			boolean save = true;
			String filename = generateUniqueFileName(u, d)+".docx";
			String outputfilepath = System.getProperty("user.dir") + CONSTS.DOC_DIR + filename;

			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
					.load(new java.io.File(inputfilepath));
			MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
			
			VariablePrepare.prepare(wordMLPackage);
			
			long start = System.currentTimeMillis();
			
			documentPart.variableReplace(mappings);
			
			long end = System.currentTimeMillis();
			long total = end - start;
			
			SaveToZipFile saver = new SaveToZipFile(wordMLPackage);
			saver.save(outputfilepath);
			return filename;
	  }
	  
	  public ArrayList<HashMap<String,String>> getFillableVars(Document d) throws CsvValidationException, IOException{
		  ArrayList<Var> a = getDocVars(d);
		  ArrayList<HashMap<String,String>> ret = new ArrayList<HashMap<String,String>>();
		  for(Var var : a) {
			  if(var.isNeeds_form()) {
				  HashMap<String,String> temp = new HashMap<String,String>();
				  temp.put("varname",var.getVarname());
				  temp.put("vartype",var.getVartype());
				  temp.put("description",var.getDescription());
				  ret.add(temp);
			  }
		  }
		  return ret;
	  }
	  
	  public ArrayList<HashMap<String,String>> getDbVars(Document d) throws CsvValidationException, IOException{
		  ArrayList<Var> a = getDocVars(d);
		  ArrayList<HashMap<String,String>> ret = new ArrayList<HashMap<String,String>>();
		  for(Var var : a) {
			  if(!var.isNeeds_form()) {
				  HashMap<String,String> temp = new HashMap<String,String>();
				  temp.put("varname",var.getVarname());
				  ret.add(temp);
			  }
		  }
		  System.out.println("getDbVars over successfully");
		  return ret;
	  }
	  
	  public HashMap<String,String> getDbMappings(User u, Document d) throws CsvValidationException, IOException{
		  ArrayList<HashMap<String,String>> inp = getDbVars(d);
		  HashMap<String,String> ret = new HashMap<String,String>();
		  for(HashMap<String,String> a : inp) {
			  ret.put(a.get("varname"), input_to_var(u, a.get("varname")));
		  }
		  System.out.println("getDbMappings over successfully");
		  return ret;
	  }
	  
	  public void initTestDocs() {
		  Document a,b;
		  a = new Document();
		  b = new Document();
		  
		  HashSet<Role> r = new HashSet<Role>();
		  r.add(rs.getRole(CONSTS.USER_ROLE));
		  
		  HashSet<Role> r2 = new HashSet<Role>();
		  r2.add(rs.getRole(CONSTS.USER_ROLE));
		  r2.add(rs.getRole(CONSTS.L1_ROLE));
		  r2.add(rs.getRole(CONSTS.L2_ROLE));
		  r2.add(rs.getRole(CONSTS.L3_ROLE));
		  
		  a.setTitle("Attestation de Travail");
		  a.setAllowed_roles(r2);
		  a.setNeeds_form(false);
		  a.setRequires_approval(true);
		  dr.save(a);
		  
		  b.setTitle("Ordre de mission");
		  b.setAllowed_roles(r);
		  b.setNeeds_form(true);
		  b.setRequires_approval(true);
		  dr.save(b);
	  }
	  
	  public String generateUniqueFileName(User u, Document d) {
		  return encryptThisString(u.getNom()+"_"+u.getPrenom()+"_"+d.getTitle()+"_"+LocalDateTime.now().toString());
	  }
	  
	  private static String encryptThisString(String input)
	    {
	        try {
	            // getInstance() method is called with algorithm SHA-1
	            MessageDigest md = MessageDigest.getInstance("SHA-1");
	 
	            // digest() method is called
	            // to calculate message digest of the input string
	            // returned as array of byte
	            byte[] messageDigest = md.digest(input.getBytes());
	 
	            // Convert byte array into signum representation
	            BigInteger no = new BigInteger(1, messageDigest);
	 
	            // Convert message digest into hex value
	            String hashtext = no.toString(16);
	 
	            // Add preceding 0s to make it 32 bit
	            while (hashtext.length() < 32) {
	                hashtext = "0" + hashtext;
	            }
	 
	            // return the HashText
	            return hashtext;
	        }
	 
	        // For specifying wrong message digest algorithms
	        catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException(e);
	        }
	    }
}
