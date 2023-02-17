package com.hr.springboot.service_module.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.docx4j.Docx4J;
import org.docx4j.XmlUtils;
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
import com.hr.springboot.userData_module.repositories.TypeRepo;
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
	
	@Autowired
	TypeRepo tr;
	
	private final String secret="why_me";
	
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
				ret = ret + " " +ty.getId();
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
	
	public List<Document> getDocList(User u){
		List<Document> ret = new ArrayList<Document>();
		String y = "";
		String z = "";
		Set<Role> s = u.getRole();
		Set<Type_personnel> tp = u.getType_personnel();
		for(Role r : s) {
			y = r.getRoleName();
		}
		if(y.equals("User")) {
			for(Type_personnel t:tp) {
				z = t.getId();
			}
			
			List<Document> temp = dr.findByRolePers(y, z);
			for(Document d : temp) {
				if(d.isShowable()) {
					ret.add(d);
				}
			}
			return ret;
		}
		List<Document> temp = dr.findByRole(y);
		for(Document d:temp) {
			ret.add(d);
		}
		return ret;
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
	  
	  public ArrayList<String> generate_doc(User u,Document d, HashMap<String, String> mappings) throws Exception {
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
			
			//documentPart.variableReplace(mappings);
			
			// unmarshallFromTemplate requires string input
			String xml = XmlUtils.marshaltoString(documentPart.getJaxbElement(), true);
			// Do it...
			Object obj = XmlUtils.unmarshallFromTemplate(xml, mappings);
			// Inject result into docx
			documentPart.setJaxbElement((org.docx4j.wml.Document) obj);
						
			
			SaveToZipFile saver = new SaveToZipFile(wordMLPackage);
			saver.save(outputfilepath);
			/*FileOutputStream os = new FileOutputStream(outputfilepath);
            Docx4J.toPDF(wordMLPackage,os);
            os.flush();
            os.close();*/
			ArrayList<String> ret = new ArrayList<String>();
			ret.add(filename);
			System.out.println(mappings);
			for(String key:mappings.keySet()) {
				if(key.contains("_CO")) {
					ret.add(mappings.get(key));
					break;
				}
			}
			return ret;
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
		  System.out.println(inp);
		  HashMap<String,String> ret = new HashMap<String,String>();
		  for(HashMap<String,String> a : inp) {
			  ret.put(a.get("varname"), input_to_var(u, a.get("varname")));
		  }
		  System.out.println("getDbMappings over successfully");
		  return ret;
	  }
	  
	  public void initTestDocs() {
		  Document a,b,c,d,e,f,g,h,i,j;
		  a = new Document();
		  b = new Document();
		  c = new Document();
		  d = new Document();
		  e = new Document();
		  f = new Document();
		  g = new Document();
		  h = new Document();
		  i = new Document();
		  j = new Document();
		  
		  HashSet<Type_personnel> t1 = new HashSet<Type_personnel>();
		  HashSet<Type_personnel> t2 = new HashSet<Type_personnel>();
		  HashSet<Type_personnel> t3 = new HashSet<Type_personnel>();
		  
		  
		  t1.add(tr.findById("Enseignant").get());
		  t2.add(tr.findById("Administratif").get());
		  t3.add(tr.findById("Enseignant").get());
		  t3.add(tr.findById("Administratif").get());
		  
		  HashSet<Role> r = new HashSet<Role>();
		  r.add(rs.getRole(CONSTS.USER_ROLE));
		  
		  HashSet<Role> r2 = new HashSet<Role>();
		  r2.add(rs.getRole(CONSTS.USER_ROLE));
		  r2.add(rs.getRole(CONSTS.L1_ROLE));
		  r2.add(rs.getRole(CONSTS.L2_ROLE));
		  r2.add(rs.getRole(CONSTS.L3_ROLE));
		  
		  HashSet<Role> rlayer3only = new HashSet<Role>();
		  rlayer3only.add(rs.getRole(CONSTS.L3_ROLE));
		  
		  HashSet<Role> r3 = new HashSet<Role>();
		  r3.add(rs.getRole(CONSTS.L1_ROLE));
		  r3.add(rs.getRole(CONSTS.L2_ROLE));
		  r3.add(rs.getRole(CONSTS.L3_ROLE));
		  
		  a.setTitle("Attestation de Travail");
		  a.setAllowed_roles(r2);
		  a.setNeeds_form(false);
		  a.setRequires_approval(true);
		  a.setAllowed_personnel(t3);
		  dr.save(a);
		  
		  b.setTitle("Ordre de mission");
		  b.setAllowed_roles(r);
		  b.setNeeds_form(true);
		  b.setRequires_approval(true);
		  b.setAllowed_personnel(t3);
		  dr.save(b);
		  
		  c.setTitle("Autorisation de Quitter le Territoire National");
		  c.setAllowed_roles(r);
		  c.setNeeds_form(true);
		  c.setRequires_approval(true);
		  c.setAllowed_personnel(t3);
		  dr.save(c);
		  
		  d.setTitle("Decision de congé");
		  d.setAllowed_roles(r);
		  d.setNeeds_form(true);
		  d.setRequires_approval(true);
		  d.setAllowed_personnel(t2);
		  dr.save(d);
		  
		  e.setTitle("Situation Administrative");
		  e.setAllowed_roles(r);
		  e.setNeeds_form(false);
		  e.setRequires_approval(false);
		  e.setAllowed_personnel(t3);
		  dr.save(e);
		  
		  f.setTitle("Autorisation d'absence hors periode de vacances");
		  f.setAllowed_roles(r);
		  f.setNeeds_form(true);
		  f.setRequires_approval(true);
		  f.setAllowed_personnel(t1);
		  dr.save(f);
		  
		  g.setTitle("استفسار");
		  g.setAllowed_roles(rlayer3only);
		  g.setNeeds_form(true);
		  g.setRequires_approval(false);
		  g.setAllowed_personnel(t3);
		  dr.save(g);
		  
		  h.setTitle("Fichier plat administratif");
		  h.setAllowed_roles(r3);
		  h.setNeeds_form(false);
		  h.setRequires_approval(false);
		  h.setAllowed_personnel(t3);
		  dr.save(h);
		  
		  i.setTitle("Fichier plat enseignant");
		  i.setAllowed_roles(r3);
		  i.setNeeds_form(false);
		  i.setRequires_approval(false);
		  i.setAllowed_personnel(t3);
		  dr.save(i);
		  
		  j.setTitle("Vie communal");
		  j.setAllowed_roles(r3);
		  j.setNeeds_form(false);
		  j.setRequires_approval(false);
		  j.setAllowed_personnel(t3);
		  dr.save(j);
	  }
	  
	  public String generateUniqueFileName(User u, Document d) {
		  return encryptThisString(u.getNom()+"_"+u.getPrenom()+"_"+d.getTitle()+"_"+LocalDateTime.now().toString()+secret);
	  }
	  
	  public String encryptThisString(String input)
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
	  
	  public String genererFichierPlatAdministratif() throws IOException {
		  List<User> users = ur.findAllAdministratif();
		  String filename = "Fichier plat administratif";
		  
		  XSSFWorkbook workbook = new XSSFWorkbook();
	      XSSFSheet sheet = workbook.createSheet("Fichier plat administratif");
	 
	        int rowCount = -1;
	        int columnCount = -1;
	        
	        Row row = sheet.createRow(++rowCount);
	        Cell cell = row.createCell(++columnCount);
	        cell.setCellValue("Code_etablissement");
	        Cell cell1 = row.createCell(++columnCount);
	        cell1.setCellValue("Code Annee");
	        Cell cell2 = row.createCell(++columnCount);
	        cell2.setCellValue("PPR");
	        Cell cell3 = row.createCell(++columnCount);
	        cell3.setCellValue("Nom");
	        Cell cell4 = row.createCell(++columnCount);
	        cell4.setCellValue("Prenom");
	        Cell cell5 = row.createCell(++columnCount);
	        cell5.setCellValue("Genre");
	        Cell cell6 = row.createCell(++columnCount);
	        cell6.setCellValue("Date_Naissance");
	        Cell cell7 = row.createCell(++columnCount);
	        cell7.setCellValue("Lieu de Naissance");
	        Cell cell8 = row.createCell(++columnCount);
	        cell8.setCellValue("Code_Nationalité");
	        Cell cell9 = row.createCell(++columnCount);
	        cell9.setCellValue("Code_Grade");
	        Cell cell10 = row.createCell(++columnCount);
	        cell10.setCellValue("Type_personnel");
	        Cell cell11 = row.createCell(++columnCount);
	        cell11.setCellValue("Date_recrutement");
	        Cell cell12 = row.createCell(++columnCount);
	        cell12.setCellValue("Date_affectation_MESRSFC");
	        Cell cell13 = row.createCell(++columnCount);
	        cell13.setCellValue("Nombre de diplôme");
	        Cell cell14 = row.createCell(++columnCount);
	        cell14.setCellValue("Diplome");
	        Cell cell15 = row.createCell(++columnCount);
	        cell15.setCellValue("Specialite");
	        Cell cell16 = row.createCell(++columnCount);
	        cell16.setCellValue("Universite_etablissement_diplomante");
	        Cell cell17 = row.createCell(++columnCount);
	        cell17.setCellValue("Autres diplômes");
	        Cell cell18 = row.createCell(++columnCount);
	        cell18.setCellValue("service_affectation");
	         
	        for (User u : users) {
	        	columnCount = -1;
	        	String z = "";
	        	Set<Type_personnel>tp = u.getType_personnel();
	        	for(Type_personnel t:tp) {
					z = t.getId();
				}
	        	Row row2 = sheet.createRow(++rowCount);
	        	Cell cell0 = row2.createCell(++columnCount);
	        	cell0.setCellValue(u.getCode_etablissement());
		        Cell cell100 = row2.createCell(++columnCount);
		        cell100.setCellValue(u.getCode_annee());
		        Cell cell20 = row2.createCell(++columnCount);
		        cell20.setCellValue(u.getPpr());
		        Cell cell30 = row2.createCell(++columnCount);
		        cell30.setCellValue(u.getNom());
		        Cell cell40 = row2.createCell(++columnCount);
		        cell40.setCellValue(u.getPrenom());
		        Cell cell50 = row2.createCell(++columnCount);
		        cell50.setCellValue(u.getGenre());
		        Cell cell60 = row2.createCell(++columnCount);
		        cell60.setCellValue(u.getDate_naissance().toString());
		        Cell cell70 = row2.createCell(++columnCount);
		        cell70.setCellValue(u.getLieu_naissance());
		        Cell cell80 = row2.createCell(++columnCount);
		        cell80.setCellValue(u.getCode_nationalite());
		        Cell cell90 = row2.createCell(++columnCount);
		        cell90.setCellValue(u.getCode_grade());
		        Cell cell1000 = row2.createCell(++columnCount);
		        cell1000.setCellValue(z);
		        Cell cell110 = row2.createCell(++columnCount);
		        cell110.setCellValue(u.getDate_recrutement().toString());
		        Cell cell120 = row2.createCell(++columnCount);
		        cell120.setCellValue(u.getDate_affectation_MESRSFC().toString());
		        Cell cell130 = row2.createCell(++columnCount);
		        cell130.setCellValue(u.getNombre_diplomes());
		        Cell cell140 = row2.createCell(++columnCount);
		        cell140.setCellValue(u.getDiplome());
		        Cell cell150 = row2.createCell(++columnCount);
		        cell150.setCellValue(u.getSpecialite());
		        Cell cell160 = row2.createCell(++columnCount);
		        cell160.setCellValue(u.getUniv_etablissement_diplomate());
		        Cell cell170 = row2.createCell(++columnCount);
		        cell170.setCellValue("");
		        Cell cell180 = row2.createCell(++columnCount);	
		        cell180.setCellValue(u.getService_affectation());
	        }
	         
	        String filepath = System.getProperty("user.dir") + CONSTS.DOC_DIR;
	         
	        try (FileOutputStream outputStream = new FileOutputStream(filepath+filename+".xlsx")) {
	            workbook.write(outputStream);
	        }
	        workbook.close();
	        return filename+".xlsx";
	  }
	  
	  public String genererFichierPlatEnseignant() throws IOException {
		  List<User> users = ur.findAllEnseignant();
		  String filename = "Fichier plat enseignant";
		  
		  XSSFWorkbook workbook = new XSSFWorkbook();
	      XSSFSheet sheet = workbook.createSheet("Fichier plat enseignant");
	 
	        int rowCount = -1;
	        int columnCount = -1;
	        
	        Row row = sheet.createRow(++rowCount);
	        Cell cell = row.createCell(++columnCount);
	        cell.setCellValue("Code_etablissement");
	        Cell cell1 = row.createCell(++columnCount);
	        cell1.setCellValue("Code Annee");
	        Cell cell2 = row.createCell(++columnCount);
	        cell2.setCellValue("PPR");
	        Cell cell3 = row.createCell(++columnCount);
	        cell3.setCellValue("Nom");
	        Cell cell4 = row.createCell(++columnCount);
	        cell4.setCellValue("Prenom");
	        Cell cell5 = row.createCell(++columnCount);
	        cell5.setCellValue("Genre");
	        Cell cell6 = row.createCell(++columnCount);
	        cell6.setCellValue("Date_Naissance");
	        Cell cell7 = row.createCell(++columnCount);
	        cell7.setCellValue("Lieu de Naissance");
	        Cell cell8 = row.createCell(++columnCount);
	        cell8.setCellValue("Code_Nationalité");
	        Cell cell9 = row.createCell(++columnCount);
	        cell9.setCellValue("Code_Grade");
	        Cell cell10 = row.createCell(++columnCount);
	        cell10.setCellValue("Type_personnel");
	        Cell cell11 = row.createCell(++columnCount);
	        cell11.setCellValue("Date_recrutement");
	        Cell cell12 = row.createCell(++columnCount);
	        cell12.setCellValue("Date_affectation_MESRSFC");
	        Cell cell13 = row.createCell(++columnCount);
	        cell13.setCellValue("Nombre de diplôme");
	        Cell cell14 = row.createCell(++columnCount);
	        cell14.setCellValue("Diplome");
	        Cell cell15 = row.createCell(++columnCount);
	        cell15.setCellValue("Specialite");
	        Cell cell16 = row.createCell(++columnCount);
	        cell16.setCellValue("Universite_etablissement_diplomante");
	        Cell cell17 = row.createCell(++columnCount);
	        cell17.setCellValue("Autres diplômes");
	        Cell cell18 = row.createCell(++columnCount);
	        cell18.setCellValue("Fonction exercee");
	         
	        for (User u : users) {
	        	columnCount = -1;
	        	String z = "";
	        	Set<Type_personnel>tp = u.getType_personnel();
	        	for(Type_personnel t:tp) {
					z = t.getId();
				}
	        	Row row2 = sheet.createRow(++rowCount);
	        	Cell cell0 = row2.createCell(++columnCount);
	        	cell0.setCellValue(u.getCode_etablissement());
		        Cell cell100 = row2.createCell(++columnCount);
		        cell100.setCellValue(u.getCode_annee());
		        Cell cell20 = row2.createCell(++columnCount);
		        cell20.setCellValue(u.getPpr());
		        Cell cell30 = row2.createCell(++columnCount);
		        cell30.setCellValue(u.getNom());
		        Cell cell40 = row2.createCell(++columnCount);
		        cell40.setCellValue(u.getPrenom());
		        Cell cell50 = row2.createCell(++columnCount);
		        cell50.setCellValue(u.getGenre());
		        Cell cell60 = row2.createCell(++columnCount);
		        cell60.setCellValue(u.getDate_naissance().toString());
		        Cell cell70 = row2.createCell(++columnCount);
		        cell70.setCellValue(u.getLieu_naissance());
		        Cell cell80 = row2.createCell(++columnCount);
		        cell80.setCellValue(u.getCode_nationalite());
		        Cell cell90 = row2.createCell(++columnCount);
		        cell90.setCellValue(u.getCode_grade());
		        Cell cell1000 = row2.createCell(++columnCount);
		        cell1000.setCellValue(z);
		        Cell cell110 = row2.createCell(++columnCount);
		        cell110.setCellValue(u.getDate_recrutement().toString());
		        Cell cell120 = row2.createCell(++columnCount);
		        cell120.setCellValue(u.getDate_affectation_MESRSFC().toString());
		        Cell cell130 = row2.createCell(++columnCount);
		        cell130.setCellValue(u.getNombre_diplomes());
		        Cell cell140 = row2.createCell(++columnCount);
		        cell140.setCellValue(u.getDiplome());
		        Cell cell150 = row2.createCell(++columnCount);
		        cell150.setCellValue(u.getSpecialite());
		        Cell cell160 = row2.createCell(++columnCount);
		        cell160.setCellValue(u.getUniv_etablissement_diplomate());
		        Cell cell170 = row2.createCell(++columnCount);
		        cell170.setCellValue("");
		        Cell cell180 = row2.createCell(++columnCount);	
		        cell180.setCellValue(u.getFonction_exerce());
	        }
	         
	        String filepath = System.getProperty("user.dir") + CONSTS.DOC_DIR;
	         
	        try (FileOutputStream outputStream = new FileOutputStream(filepath+filename+".xlsx")) {
	            workbook.write(outputStream);
	        }
	        workbook.close();
	        return filename+".xlsx";
	  }
	  
	  //todo later
	  public String genererVieCommunal() throws FileNotFoundException, IOException {
		  List<User> users = ur.findAllEnseignant();
		  String filename = "Vie communal";
		  
		  XSSFWorkbook workbook = new XSSFWorkbook();
	      XSSFSheet sheet = workbook.createSheet("Vie communal");
	 
	        int rowCount = -1;
	        int columnCount = -1;
	        
	        Row row = sheet.createRow(++rowCount);
	        Cell cell = row.createCell(++columnCount);
	        cell.setCellValue("DOTI");
	        Cell cell1 = row.createCell(++columnCount);
	        cell1.setCellValue("NOM PRENOM");
	        Cell cell2 = row.createCell(++columnCount);
	        cell2.setCellValue("CIN");
	        Cell cell3 = row.createCell(++columnCount);
	        cell3.setCellValue("ADRESSE");
	        Cell cell4 = row.createCell(++columnCount);
	        cell4.setCellValue("POSITION");
	         
	        for (User u : users) {
	        	columnCount = -1;
	        	String z = "";
	        	Set<Type_personnel>tp = u.getType_personnel();
	        	for(Type_personnel t:tp) {
					z = t.getId();
				}
	        	Row row2 = sheet.createRow(++rowCount);
	        	Cell cell0 = row2.createCell(++columnCount);
	        	cell0.setCellValue(u.getPpr());
		        Cell cell100 = row2.createCell(++columnCount);
		        cell100.setCellValue(u.getNom()+" "+u.getPrenom());
		        Cell cell20 = row2.createCell(++columnCount);
		        cell20.setCellValue(u.getCin());
		        Cell cell30 = row2.createCell(++columnCount);
		        cell30.setCellValue("no adresse");
		        Cell cell40 = row2.createCell(++columnCount);
		        cell40.setCellValue("A");
	        }
	         
	        String filepath = System.getProperty("user.dir") + CONSTS.DOC_DIR;
	         
	        try (FileOutputStream outputStream = new FileOutputStream(filepath+filename+".xlsx")) {
	            workbook.write(outputStream);
	        }
	        workbook.close();
	        return filename+".xlsx";
	  }
}
