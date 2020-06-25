import java.io.*;
public class Read {
	static BufferedReader br=null;
	static String file1="D:/学习/读取文件/Process1_Manufacturing_Program_1.aptsource";
	float[] x=new float[1000];
	float[] y=new float[1000];
	float[] z=new float[1000];
	float[] f=new float[1000];
	float[] s=new float[1000];
	static String a=null;
	static String b=null;
	static String line=null;
	int n=0;
	public void read() {
		File f1=new File(file1);
			try {
				br = new BufferedReader(new FileReader(f1));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		try {
			while((line=br.readLine())!=null){
				if(line.length()>6) {
					a=line.substring(0, 4);
					b=line.substring(0, 6);
					if(a.equals("GOTO")) {
						a=line.substring(9, 42);
						String[] datas=a.split(",");
						x[n]=Float.parseFloat(datas[0]);
						y[n]=Float.parseFloat(datas[1]);
						z[n]=Float.parseFloat(datas[2]);
						f[n+1]=f[n];
						s[n+1]=s[n];
						n++;
					}
					if(b.equals("FEDRAT")) {
						b=line.substring(8, 17);
						f[n]=Float.parseFloat(b);
					}
					if(b.equals("SPINDL")) {
						b=line.substring(8, 17);
						s[n]=Float.parseFloat(b);
					}
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
