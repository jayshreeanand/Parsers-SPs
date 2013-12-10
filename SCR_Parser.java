
import java.util.*;
import java.util.regex.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.*;

public class SCR_Parser 
{
	public static final ResourceBundle bundle_dataFile = ResourceBundle.getBundle("DataFileSCR");
	public static final ResourceBundle  bundle_counter= ResourceBundle.getBundle("Counters");
	public static final ResourceBundle bundle_attri = ResourceBundle.getBundle("MP_attri");	

	public static final String  InputDir =bundle_dataFile.getString("InputDir");
	public static final String  OutpurDir =bundle_dataFile.getString("OutpurDir");
	public static final String BackupDir=bundle_dataFile.getString("BackupDir");
	public static final String zipDir=bundle_dataFile.getString("ZipDir");
	
	

	public void getData(String filename) throws IOException
	{
		
		int start=0;
		int index2=0,index3=0,index4=0,index5=0,index6=0,index7=0,index8=0,index9=0,index10=0,index11=0,index12=0,index13=0,index14=0,index15=0,index16=0,index17=0,index18=0,index19=0,index20=0;
		int index21=0,index22=0,index23=0,index24=0,index25=0,index26=0,index27=0,index28=0,index29=0,index30=0,index31=0,index32=0,index33=0,index34=0,index35=0,index36=0,index37=0,index38=0,index39=0,index40=0,index41=0,index42=0,index43=0,index44=0,index45=0,index46=0,index47=0,index48=0,index49=0,index50=0,index51=0,index52=0,index53=0,index54=0,index55=0,index56=0,index57=0,index58=0,index59=0,index60=0,index61=0,index62=0,index63=0,index64=0,index65=0,index66=0;
		String text="";
		String data="";
		
		try
		{
			String circle=filename.substring(0,filename.indexOf("_"));
			if(circle.equalsIgnoreCase("DEL"))circle="DELHI";
			String outName=filename.substring(0,filename.lastIndexOf("."));
			String ftime=outName.substring(outName.lastIndexOf("_")+1);
			outName=outName.substring(0,filename.lastIndexOf("_"));
			String mscName=outName.substring(0,outName.lastIndexOf("_"));
			
			mscName=mscName.substring(mscName.lastIndexOf("_")+1);
			mscName=circle+"-"+mscName;
			String fdate=outName.substring(outName.lastIndexOf("_")+1);
			fdate=fdate.substring(4,8)+fdate.substring(2,4)+fdate.substring(0,2);
			outName=outName.substring(0,filename.lastIndexOf("_"));
			File outfile = new File(OutpurDir+"\\"+outName+"_["+fdate+ftime+"]_[OBJTYPE].csv");
			FileWriter writer= new FileWriter(outfile);
			writer.append("DATETIME,NODE,MP,STATE,NROP,ROPL,ROPN,GRN,FCODE,ACC,QTA,NTOTCALLS,CTYPE,CRITERION,OTYPE,OBJECT,EVENTS,NCALLS,N1DIG,NSEIZED,NTHCON,NBANS,NABEFD,NADURD,NAAFTD,NABEFA1,NABEFA2,NTOBEFD,NTODURD,NTOBEFA,NBBUSY,NBOUT,NBNOEX,NCAWNOA,NTOOT,NTOCS,NFSIGIT,NFSIGCR,NFSIGOT,NFSIGCS,NRESSF,NHWFIT,NHWFCR,NHWFOT,NHWFCS,NHWFTS,NHWFJT,NRESPP,NRESHF,NCONGOT,NCONGTS,NCONGGS,NCONGJT,NCONGNW,NNMBLO,NEOSNOSE,NSPARE1,NSPARE2,NSPARE3,NSPARE4,NSPARE5,NSPARE6,NSPARE7,NSPARE8,NSPARE9,NSPARE10,NSPARE11,NSPARE12,NSPARE13,NSPARE14,NSPARE15,NSPARE16,NPDD1,NPDD2,NPDD3,NPDD4,NBANST1,NBANST2,NCONVT1,NCONVT2");
			writer.append("\n");
		 BufferedReader reader = null;
		 File file = new File(InputDir+"\\"+filename);
		 reader = new BufferedReader(new FileReader(file)); 
		 while ((text = reader.readLine()) != null) 
		 {
			 if(text.trim().length()>0)
			 {
				
				 text=text.toUpperCase();
				 if(text.contains("<APLOC;")&&start==0)
				 {
					 
					 start=1;
				 }				 
				 else if(text.contains("END")&&text.trim().length()==3&&start==1)
				 { 
					 writer.append(data);
					 writer.append("\n");
					 start=0;
					 //break;
				 }
				 else if(start==1)  
				 { 
					 if(text.contains("MP")&&text.contains("STATE")&&text.contains("NROP")&&text.contains("ROPL")&&text.contains("ROPN")&&text.contains("GRN")&&text.contains("DATE"))
					 {                 
						 index2=text.indexOf("STATE");
						 index3=text.indexOf("NROP");
						 index4=text.indexOf("ROPL");
						 index5=text.indexOf("ROPN");
						 index6=text.indexOf("GRN");
						 index7=text.indexOf("DATE");
						 index8=text.indexOf("TIME");
						 index9=text.indexOf("FCODE");
					 }
					 else if(index2!=0&&index3!=0&&index4!=0)
					 {
						 String date="",time="";
						 String datetime="";
						 if(text.length()>index9)
						 {
							 date=text.substring(index7,index8).trim();
							 date="20"+date.substring(0,2)+"-"+date.substring(2,4)+"-"+date.substring(4,6);
							 time=text.substring(index8,index9).trim();
							 time=time.substring(0,2)+":"+time.substring(2,4)+":00";
							 datetime=date+" "+time;
						 }
						 else
						 {
							 date=text.substring(index7,index8).trim();
							 date="20"+date.substring(0,2)+"-"+date.substring(2,4)+"-"+date.substring(4,6);
							 time=text.substring(index8,text.length()).trim();
							 time=time.substring(0,2)+":"+time.substring(2,4)+":00";
							 datetime=date+" "+time;
						 }
						 
						 //System.out.println(datetime);
							 if(text.trim().length()>index9)
								 data=datetime+","+mscName+","+text.substring(0,index2).trim()+","+text.substring(index2,index3).trim()+","+text.substring(index3,index4).trim()+","+text.substring(index4,index5).trim()+","+text.substring(index5,index6).trim()+","+text.substring(index6,index7).trim()+","+text.substring(index9,text.length()).trim(); 
							 else if(text.trim().length()>index8) 
								 data=datetime+","+mscName+","+text.substring(0,index2).trim()+","+text.substring(index2,index3).trim()+","+text.substring(index3,index4).trim()+","+text.substring(index4,index5).trim()+","+text.substring(index5,index6).trim()+","+text.substring(index6,index7).trim()+","; 
							 else if(text.trim().length()>index7)
								 data=datetime+","+mscName+","+text.substring(0,index2).trim()+","+text.substring(index2,index3).trim()+","+text.substring(index3,index4).trim()+","+text.substring(index4,index5).trim()+","+text.substring(index5,index6).trim()+",,"; 
							
							 data=data+",";
							 index2=0;
							 index3=0;
							 index4=0;
							 index5=0;
							 index6=0;
							 index7=0;
							 index8=0;
							 index9=0;
					 }                      
					 else if(text.contains("ACC")&&text.contains("QTA")&&text.contains("NTOTCALLS")&&text.contains("CTYPE"))
					 {
						 
						 index10=text.indexOf("QTA");
						 index11=text.indexOf("NTOTCALLS");
						 index12=text.indexOf("CTYPE");
						 index13=text.indexOf("CRITERION");
						 index14=text.indexOf("OTYPE");
					 }
					 else if(index10!=0&&index11!=0&&index12!=0)
					 {
						  	
						 if(text.trim().length()>index14)
							 data=data+text.substring(0,index10).trim()+","+text.substring(index10,index11).trim()+","+text.substring(index11,index12).trim()+","+text.substring(index12,index13).trim()+","+text.substring(index13,index14).trim()+","+text.substring(index14,text.length()).trim();
						 else if(text.trim().length()>index13)
							 data=data+text.substring(0,index10).trim()+","+text.substring(index10,index11).trim()+","+text.substring(index11,index12).trim()+","+text.substring(index12,index13).trim()+","+text.substring(index13,text.length()).trim()+",";
						 else if(text.trim().length()>index12)
							 data=data+text.substring(0,index10).trim()+","+text.substring(index10,index11).trim()+","+text.substring(index11,index12).trim()+","+text.substring(index12,text.length()).trim()+",,";
						 else if(text.trim().length()>index11)
							 data=data+text.substring(0,index10).trim()+","+text.substring(index10,index11).trim()+","+text.substring(index11,text.length()).trim()+",,,"; 	
						  	
							 data=data+",";
							 index10=0;
							 index11=0;
							 index12=0;
							 index13=0;
							 index14=0;
					 }    
					 else if(text.contains("OBJECT")&&text.contains("EVENTS"))
					 {                 
						 index15=text.indexOf("EVENTS");
						 
					 }
					 else if(index15!=0)
					 {
						  	
						 if(text.trim().length()>index15)
							 data=data+text.substring(0,index15).trim()+","+text.substring(index15,text.length()).trim();
						 else
							 data=data+text.substring(0,text.length()).trim()+",";
						  	
							 data=data+",";
							 index15=0;
					 }                      
					 else if(text.contains("NCALLS")&&text.contains("N1DIG")&&text.contains("NSEIZED")&&text.contains("NTHCON"))
					 {                 
						 index16=text.indexOf("N1DIG");
						 index17=text.indexOf("NSEIZED");
						 index18=text.indexOf("NTHCON");
						 index19=text.indexOf("NBANS");
					 }
					 else if(index16!=0&&index17!=0&&index18!=0)
					 {
						 if(text.length()>index19)
							 data=data+text.substring(0,index16).trim()+","+text.substring(index16,index17).trim()+","+text.substring(index17,index18).trim()+","+text.substring(index18,index19).trim()+","+text.substring(index19,text.length()).trim();
						 else if(text.length()>index18)
							 data=data+text.substring(0,index16).trim()+","+text.substring(index16,index17).trim()+","+text.substring(index17,index18).trim()+","+text.substring(index18,text.length()).trim()+",";
						 else if(text.length()>index17)
							 data=data+text.substring(0,index16).trim()+","+text.substring(index16,index17).trim()+","+text.substring(index17,text.length()).trim()+",,";
						
							 data=data+",";
							 index16=0;
							 index17=0;
							 index18=0;
							 index19=0;
					 }    

					 else if(text.contains("NABEFD")&&text.contains("NADURD")&&text.contains("NAAFTD")&&text.contains("NABEFA1"))
					 {                 
						 index20=text.indexOf("NADURD");
						 index21=text.indexOf("NAAFTD");
						 index22=text.indexOf("NABEFA1");
						 index23=text.indexOf("NABEFA2");
					 }
					 else if(index20!=0&&index21!=0&&index22!=0)
					 {
						 if(text.length()>index23)
							 data=data+text.substring(0,index20).trim()+","+text.substring(index20,index21).trim()+","+text.substring(index21,index22).trim()+","+text.substring(index22,index23).trim()+","+text.substring(index23,text.length()).trim();
						 else if(text.length()>index22)
							 data=data+text.substring(0,index20).trim()+","+text.substring(index20,index21).trim()+","+text.substring(index21,index22).trim()+","+text.substring(index22,text.length()).trim()+",";
						 else if(text.length()>index21)
							 data=data+text.substring(0,index20).trim()+","+text.substring(index20,index21).trim()+","+text.substring(index21,text.length()).trim()+",,";
						
							 data=data+",";
							 index20=0;
							 index21=0;
							 index22=0;
							 index23=0;
					 }    
					 else if(text.contains("NTOBEFD")&&text.contains("NTODURD")&&text.contains("NTOBEFA"))
					 {
						 
						 index24=text.indexOf("NTODURD");
						 index25=text.indexOf("NTOBEFA");
					 }
					 else if(index24!=0&&index25!=0)
					 {
						 if(text.length()>index25)
							 data=data+text.substring(0,index24).trim()+","+text.substring(index24,index25).trim()+","+text.substring(index25,text.length()).trim();
						 else if(text.length()>index24)
							 data=data+text.substring(0,index24).trim()+","+text.substring(index24,text.length()).trim()+",";
						 else 
							 data=data+text.substring(0,text.length()).trim()+",,";
						
							 data=data+",";
							 index24=0;
							 index25=0;
					 }    
					 else if(text.contains("NBBUSY")&&text.contains("NBOUT")&&text.contains("NBNOEX"))
					 {                 
						 index26=text.indexOf("NBOUT");
						 index27=text.indexOf("NBNOEX");
						 index28=text.indexOf("NCAWNOA");
					 }
					 else if(index26!=0&&index27!=0&&index28!=0)
					 {
						 if(text.length()>index28)
							 data=data+text.substring(0,index26).trim()+","+text.substring(index26,index27).trim()+","+text.substring(index27,index28).trim()+","+text.substring(index28,text.length()).trim();
						 else if(text.length()>index27)
							 data=data+text.substring(0,index26).trim()+","+text.substring(index26,index27).trim()+","+text.substring(index27,text.length()).trim()+",";
						 else if(text.length()>index26)
							 data=data+text.substring(0,index26).trim()+","+text.substring(index26,text.length()).trim()+",,";
						
							 data=data+",";
							 index26=0;
							 index27=0;
							 index28=0;
					 }    
					              
					 else if(text.contains("NTOOT")&&text.contains("NTOCS")&&text.contains("NFSIGIT"))
					 {                 
						 index29=text.indexOf("NTOCS");
						 index30=text.indexOf("NFSIGIT");
						 index31=text.indexOf("NFSIGCR");
					 }
					 else if(index29!=0&&index30!=0&&index31!=0)
					 {
						 if(text.length()>index31)
							 data=data+text.substring(0,index29).trim()+","+text.substring(index29,index30).trim()+","+text.substring(index30,index31).trim()+","+text.substring(index31,text.length()).trim();
						 else if(text.length()>index30)
							 data=data+text.substring(0,index29).trim()+","+text.substring(index29,index30).trim()+","+text.substring(index30,text.length()).trim()+",";
						 else if(text.length()>index29)
							 data=data+text.substring(0,index29).trim()+","+text.substring(index29,text.length()).trim()+",,";
						
							 data=data+",";
							 index29=0;
							 index30=0;
							 index31=0;
					 }          
					 else if(text.contains("NFSIGOT")&&text.contains("NFSIGCS")&&text.contains("NRESSF"))
					 {                 
						 index32=text.indexOf("NFSIGCS");
						 index33=text.indexOf("NRESSF");
						 
					 }
					 else if(index32!=0&&index33!=0)
					 {
						 if(text.trim().length()>index32)
							 data=data+text.substring(0,index32).trim()+","+text.substring(index32,index33).trim()+","+text.substring(index33,text.length()).trim();
						 else if(text.trim().length()>index30)
							 data=data+text.substring(0,index32).trim()+","+text.substring(index32,text.length()).trim()+",";
						 else if(text.trim().length()>index29)
							 data=data+text.substring(0,text.length()).trim()+",,";
						
							 data=data+",";
							 index32=0;
							 index33=0;
					 }         
					 else if(text.contains("NHWFIT")&&text.contains("NHWFCR")&&text.contains("NHWFOT"))
					 {                 
						 index34=text.indexOf("NHWFCR");
						 index35=text.indexOf("NHWFOT");
						 index36=text.indexOf("NHWFCS");
					 }
					 else if(index34!=0&&index35!=0&&index36!=0)
					 {
						 if(text.length()>index36)
							 data=data+text.substring(0,index34).trim()+","+text.substring(index34,index35).trim()+","+text.substring(index35,index36).trim()+","+text.substring(index36,text.length()).trim();
						 else if(text.length()>index35)
							 data=data+text.substring(0,index34).trim()+","+text.substring(index34,index35).trim()+","+text.substring(index35,text.length()).trim()+",";
						 else if(text.length()>index34)
							 data=data+text.substring(0,index34).trim()+","+text.substring(index34,text.length()).trim()+",,";
						
							 data=data+",";
							 index34=0;
							 index35=0;
							 index36=0;
					 }     					 
					 else if(text.contains("NHWFTS")&&text.contains("NHWFJT")&&text.contains("NRESPP"))
					 {
						 
						 index37=text.indexOf("NHWFJT");
						 index38=text.indexOf("NRESPP");
						 index39=text.indexOf("NRESHF");
					 }
					 else if(index37!=0&&index38!=0&&index39!=0)
					 {
						 if(text.length()>index39)
							 data=data+text.substring(0,index37).trim()+","+text.substring(index37,index38).trim()+","+text.substring(index38,index39).trim()+","+text.substring(index39,text.length()).trim();
						 else if(text.length()>index38)
							 data=data+text.substring(0,index37).trim()+","+text.substring(index37,index38).trim()+","+text.substring(index38,text.length()).trim()+",";
						 else if(text.length()>index37)
							 data=data+text.substring(0,index37).trim()+","+text.substring(index37,text.length()).trim()+",,";
						
							 data=data+",";
							 index39=0;
							 index38=0;
							 index37=0;
					 }  
					 else if(text.contains("NCONGOT")&&text.contains("NCONGTS")&&text.contains("NCONGGS")&&text.contains("NCONGJT"))
					 {
						 
						 index40=text.indexOf("NCONGTS");
						 index41=text.indexOf("NCONGGS");
						 index42=text.indexOf("NCONGJT");
						 index43=text.indexOf("NCONGNW");
					 }
					 else if(index40!=0&&index41!=0&&index42!=0)
					 {
						 if(text.length()>index43)
							 data=data+text.substring(0,index40).trim()+","+text.substring(index40,index41).trim()+","+text.substring(index41,index42).trim()+","+text.substring(index42,index43).trim()+","+text.substring(index43,text.length()).trim();
						 else if(text.length()>index42)
							 data=data+text.substring(0,index40).trim()+","+text.substring(index40,index41).trim()+","+text.substring(index41,index42).trim()+","+text.substring(index42,text.length()).trim()+",";
						 else if(text.length()>index41)
							 data=data+text.substring(0,index40).trim()+","+text.substring(index40,index41).trim()+","+text.substring(index41,text.length()).trim()+",,";
						 else if(text.length()>index40)
							 data=data+text.substring(0,index40).trim()+","+text.substring(index40,text.length()).trim()+",,,";
						 
							 data=data+",";
							 index40=0;
							 index41=0;
							 index42=0;
							 index43=0;
					 }     
					 else if(text.contains("NNMBLO"))
					 {   
						 
						 index44=text.indexOf("NNMBLO")+5;
					 }
					 else if(index44!=0)
					 {
						 
						if(text.trim().length()>0)
							 data=data+text.substring(0,text.length()).trim();
							 data=data+",";
							 index44=0;
					 }    
					 else if(text.contains("NEOSNOSE"))
					 {  
						 
						 index45=text.indexOf("NEOSNOSE")+2;
					 }
					 else if(index45!=0)
					 {
						if(text.trim().length()>0)
							 data=data+text.substring(index45,text.length()).trim();
							 data=data+",";
							 index45=0;
					 }
					 else if(text.contains("NSPARE1")&&text.contains("NSPARE2")&&text.contains("NSPARE3")&&text.contains("NSPARE4"))
					 {
						 
						 index46=text.indexOf("NSPARE2");
						 index47=text.indexOf("NSPARE3");
						 index48=text.indexOf("NSPARE4");
						 index49=text.indexOf("NSPARE5");
						 index50=text.indexOf("NSPARE6");
					 }
					 else if(index46!=0&&index47!=0&&index48!=0)
					 {
						 if(text.length()>index50)
							 data=data+text.substring(0,index46).trim()+","+text.substring(index46,index47).trim()+","+text.substring(index47,index48).trim()+","+text.substring(index48,index49).trim()+","+text.substring(index49,index50).trim()+","+text.substring(index50,text.length()).trim();
						 else if(text.length()>index49)
							 data=data+text.substring(0,index46).trim()+","+text.substring(index46,index47).trim()+","+text.substring(index47,index48).trim()+","+text.substring(index48,index49).trim()+","+text.substring(index49,text.length()).trim()+",";
						 else if(text.length()>index48)
							 data=data+text.substring(0,index46).trim()+","+text.substring(index46,index47).trim()+","+text.substring(index47,index48).trim()+","+text.substring(index48,text.length()).trim()+",,";
						 else if(text.length()>index47)
							 data=data+text.substring(0,index46).trim()+","+text.substring(index46,index47).trim()+","+text.substring(index47,text.length()).trim()+",,,";
						 else if(text.length()>index46)
							 data=data+text.substring(0,index46).trim()+","+text.substring(index46,text.length()).trim()+",,,,";
						 
							 data=data+",";
							 index46=0;
							 index47=0;
							 index48=0;
							 index49=0;
							 index50=0;
					 }      
					 else if(text.contains("NSPARE7")&&text.contains("NSPARE8")&&text.contains("NSPARE9")&&text.contains("NSPARE10"))
					 {                 
						 index51=text.indexOf("NSPARE8");
						 index52=text.indexOf("NSPARE9");
						 index53=text.indexOf("NSPARE10");
						 index54=text.indexOf("NSPARE11");
						 index55=text.indexOf("NSPARE12");
					 }
					 else if(index51!=0&&index52!=0&&index53!=0)
					 {
						 if(text.length()>index55)
						 {
							 data=data+text.substring(0,index51).trim()+","+text.substring(index51,index52).trim()+","+text.substring(index52,index53).trim()+","+text.substring(index53,index54-2).trim()+","+text.substring(index54,index55-1).trim()+","+text.substring(index55-1,text.length()).trim();
						 }
						 else if(text.length()>index54)
						 {
							 data=data+text.substring(0,index51).trim()+","+text.substring(index51,index52).trim()+","+text.substring(index52,index53).trim()+","+text.substring(index53,index54).trim()+","+text.substring(index54+1,text.length()).trim()+",";
						 }
						 else if(text.length()>index53)
						 {
							 data=data+text.substring(0,index51).trim()+","+text.substring(index51,index52).trim()+","+text.substring(index52,index53).trim()+","+text.substring(index53,text.length()).trim()+",,";
						 }
						 else if(text.length()>index52)
						 {
							 data=data+text.substring(0,index51).trim()+","+text.substring(index51,index52).trim()+","+text.substring(index52,text.length()).trim()+",,,";
						 }
						 else if(text.length()>index51)
						 {
							 data=data+text.substring(0,index51).trim()+","+text.substring(index51,text.length()).trim()+",,,,";
						 }
						 
							 data=data+",";
							 index51=0;
							 index52=0;
							 index53=0;
							 index54=0;
							 index55=0;
					 }             
					 else if(text.contains("NSPARE13")&&text.contains("NSPARE14")&&text.contains("NSPARE15")&&text.contains("NSPARE16"))
					 {  
						 
						 index56=text.indexOf("NSPARE14");
						 index57=text.indexOf("NSPARE15");
						 index58=text.indexOf("NSPARE16");
					 }
					 else if(index56!=0&&index57!=0&&index58!=0)
					 {
						 if(text.length()>index58)
							 data=data+text.substring(0,index56).trim()+","+text.substring(index56,index57).trim()+","+text.substring(index57,index58-2).trim()+","+text.substring(index58,text.length()).trim();
						 else if(text.length()>index57)
							 data=data+text.substring(0,index56).trim()+","+text.substring(index56,index57).trim()+","+text.substring(index57,text.length()).trim()+",";
						 else if(text.length()>index56)
							 data=data+text.substring(0,index56).trim()+","+text.substring(index56,text.length()).trim()+",,";

						 	 data=data+",";
							 index56=0;
							 index57=0;
							 index58=0;
							
					 }
					 else if(text.contains("NPDD1")&&text.contains("NPDD2")&&text.contains("NPDD3")&&text.contains("NPDD4"))
					 {  
						 
						 index59=text.indexOf("NPDD2");
						 index60=text.indexOf("NPDD3");
						 index61=text.indexOf("NPDD4");
					 }
					 else if(index59!=0&&index60!=0&&index61!=0)
					 {
						 if(text.length()>index61)
							 data=data+text.substring(0,index59).trim()+","+text.substring(index59,index60).trim()+","+text.substring(index60,index61).trim()+","+text.substring(index61,text.length()).trim();
						 else if(text.length()>index60)
							 data=data+text.substring(0,index59).trim()+","+text.substring(index59,index60).trim()+","+text.substring(index60,text.length()).trim()+",";
						 else if(text.length()>index59)
							 data=data+text.substring(0,index59).trim()+","+text.substring(index59,text.length()).trim()+",,";
						 
						 	data=data+",";
						 	index59=0;
						 	index60=0;
						 	index61=0;
					 }
					 else if(text.contains("NBANST1")&&text.contains("NBANST2")&&text.contains("NCONVT1")&&text.contains("NCONVT2"))
					 {  
						 
						 index62=text.indexOf("NBANST2");
						 index63=text.indexOf("NCONVT1");
						 index64=text.indexOf("NCONVT2");
					 }
					 else if(index62!=0&&index63!=0&&index64!=0)
					 {
						 if(text.length()>index64)
							 data=data+text.substring(0,index62).trim()+","+text.substring(index62,index63).trim()+","+text.substring(index63,index64).trim()+","+text.substring(index64,text.length()).trim();
						 else if(text.length()>index63)
							 data=data+text.substring(0,index62).trim()+","+text.substring(index62,index63).trim()+","+text.substring(index63,text.length()).trim()+",";
						 else if(text.length()>index62)
							 data=data+text.substring(0,index62).trim()+","+text.substring(index62,text.length()).trim()+",,";

						 	index62=0;
						 	index63=0;
						 	index64=0;
							
					 }

					 
				 }
			 }
			 //System.out.println("data>>>>>>>>>>"+data);
			 
			 }
		 writer.close();
	}catch(Exception e)
	{
		System.out.println("Exception : "+e);
	}	
	//return map;
	
	
	}

	
	public  void UnzipFiles () {
		
		   try {
	    	  final int BUFFER = 2048;
	    	  File directory = new File(zipDir);
	 		  String filename[] = directory.list();
	 		
	 		 for(int i=0;i<filename.length;i++)
	 		 {	
	 			 //System.out.println("LLLLLLLLLLLLLL:::::::::::::;;"+filename[i]);
		         BufferedOutputStream dest = null;
		         String sourceFIle=zipDir+filename[i];         
		         String destFilePath=BackupDir+filename[i];
		         
		         File destFile=new File(destFilePath);
				 File sourceFile= new File(sourceFIle);
				 
		         FileInputStream fis = new   FileInputStream(sourceFIle);
		         ZipInputStream zis = new    ZipInputStream(new BufferedInputStream(fis));
		         ZipEntry entry;
	         while((entry = zis.getNextEntry()) != null) 
	         {
	            System.out.println("Extracting: " +entry);
	            int count;
	            byte data[] = new byte[BUFFER];

	            FileOutputStream fos = new   FileOutputStream(InputDir+entry.getName());
	            dest = new  BufferedOutputStream(fos, BUFFER);
	            	while ((count = zis.read(data, 0, BUFFER)) != -1) 
	            	{
	            		dest.write(data, 0, count);
	            	}
	            dest.flush();
	            dest.close();
	            
	            
	         }
	         	zis.close();
	        	copyFile(sourceFile, destFile);
	        	sourceFile.deleteOnExit();
	 		 }
	 	
	        
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
	   }
	
	public  void copyFile(File source, File dest) throws IOException{
		 
		   if(!dest.exists())
		   {		  
			   dest.createNewFile();
		  
		   }
		 InputStream in = null;
		 OutputStream out = null;
		    try{
		     in = new FileInputStream(source);
	         out = new FileOutputStream(dest);
		     byte[] buf = new byte[1024];

		        int len;
		  
		      while((len = in.read(buf)) > 0){
		        out.write(buf, 0, len);
		            }
		      System.out.println(source.getName()+" File Moved Sucessfully");
		      
		     }catch(Exception e)
		     {
		     }
		  finally{
			     in.close();
			     out.close();
		        }
		  }
	
	
	public static void main(String[] args) throws IOException
	{
		Long t1=System.currentTimeMillis();
		List<String> fileList=new ArrayList<String>();
		
		SCR_Parser obj=new SCR_Parser();
		obj.UnzipFiles();
		
		File f1=new File(InputDir);
		
		System.out.println("dir: "+InputDir);
		String s[]=f1.list();

		if(s.length==0)
		{
			System.out.println("No Files Found... Exiting!!!!");
			System.exit(0);
		}

		for(int j=0;j<s.length;j++)
		{
			File f=new File(InputDir+"\\"+s[j]);
			if(f.isFile())
			{
					fileList.add(s[j]);
			}
		}	

		
		for(int j=0;j<fileList.size();j++)
		{
			obj.getData(	fileList.get(j));

			File afile =new File(InputDir+"/"+fileList.get(j));
			afile.deleteOnExit();
		}

		Long t2=System.currentTimeMillis();
		System.out.println("TIME taken: "+((t2-t1))+" millisecs");
	}
}

