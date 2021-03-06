import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.*;
public class while_four
{
    public static String[] step, i, C, S, c, k, p;//输入
    public static List<String[]> qt = new ArrayList<String[]>();//存四元式
    public static int n=0, now=0, startn;                 //n:临时变量tn序号，now：token串序号，startn：暂存step_on开始token串位置

    public static void main(String[] args) throws Exception
    {
        String path_in = "./z.while代码.txt";
        String path_out = "./z.token序列.txt";

        //执行了分析器程序，它将根据"z.while代码.txt"中输入的代码更新"z.token序列.txt"
        new analyzer().answer(path_in);

        //这一段从"z.token序列.txt"文件读取了需要用到的输入，即token序列(我把它取名为step)和i, C, S, c, k, p表
        try
        {
            File filename = new File(path_out);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            line = br.readLine().substring(1);
            step = line.split(" ");
            line = br.readLine();
            i = line.substring(3, line.length() - 1).replace(" ", "").split(",");
            line = br.readLine();
            C = line.substring(3, line.length() - 1).replace(" ", "").split(",");
            line = br.readLine();
            S = line.substring(3, line.length() - 1).replace(" ", "").split(",");
            line = br.readLine();
            c = line.substring(3, line.length() - 1).replace(" ", "").split(",");
            line = br.readLine();
            k = line.substring(3, line.length() - 1).replace(" ", "").split(",");
            line = br.readLine();
            p = line.substring(3, line.length() - 1).replace(" ", "").split(",");
            br.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //读取完毕

        //执行while语法分析程序
        List<String[]> r1=new while_four().answer(step, i, C, S, c, k, p);
    }


    public List<String[]> answer(String[] step1, String[] i1, String[] C1, String[] S1, String[] c1, String[] k1, String[] p1)
    {
        step=step1;//token序列
        i=i1;//变量
        C=C1;//字符
        S=S1;//字符串
        c=c1;//数字常量
        k=k1;//关键字
        p=p1;//符号
        String t;           //Token的值
        String[] step_son;  //各阶段的子token序列
        List<String[]> qtt; //各阶段生成的四元式
        String T1,T2,P;     //保存代表左式的临时变量，保存代表右式的临时变量，保存比较的符号
        String tn;          //临时变量
        int braceNum=1;     //"{"个数，用来统计while{}的结束
        
        switch (step[now].substring(1, 2))
        {
        case "k":
            t = k[Integer.parseInt(step[now].substring(3, 4))];
            if(t.equals("while"))
            {
                startn=now+2;//暂存左式step_on首Token串的序号
                while(true)
                {
                    now++;
                    switch (step[now].substring(1, 2))
                    {
                    case "p":
                        t = p[Integer.parseInt(step[now].substring(3, 4))];
                        break;
                    case "i":
                        break;
                    case "c":
                        break;
                    case "k":
                        break;
                    case "C":
                        break;
                    case "S":
                        break;
                    default:
                        break;
                    }
                    if(t.equals(">=")||t.equals("<=")||t.equals("==")||t.equals("!=")||t.equals("<")||t.equals(">"))
                    {
                        P=t;//暂存比较符号
                        break;
                    }
                }
                step_son = Arrays.copyOfRange(step, startn, now);

                qtt=new exp_four().answer(step_son,i,C,S,c,k,p);
                n=reset_t(qtt,n);           //获得当前临时变量tn的n值
                T1=qtt.get(qtt.size()-1)[3];//暂存比较的左式的临时变量
                for(int j=0; j<qtt.size(); j++)//将比较的左式四元式序列送入四元式区
                {
                    qt.add(qtt.get(j));
                }


                startn=now+1;//暂存右式step_on首Token串的序号
                while(true)
                {
                    now++;
                    switch (step[now].substring(1, 2))
                    {
                    case "p":
                        t = p[Integer.parseInt(step[now].substring(3, 4))];
                        break;
                    case "i":
                        break;
                    case "c":
                        break;
                    case "k":
                        break;
                    case "C":
                        break;
                    case "S":
                        break;
                    default:
                        break;
                    }
                    if(t.equals("{"))
                        break;
                }
                step_son=Arrays.copyOfRange(step, startn, now-1);
                qtt=new exp_four().answer(step_son,i,C,S,c,k,p);
                n=reset_t(qtt,n)+1;//获得当前临时变量tn的n值
                T2=qtt.get(qtt.size()-1)[3];//暂存比较的右式的临时变量
                for(int j=0; j<qtt.size(); j++)//将比较的右式四元式序列送入四元式区
                {
                    qt.add(qtt.get(j));
                }
                tn="t".concat(String.valueOf(n));
                n=0;                   //进入下一个基本块，n置零
                addqt(P,T1,T2,tn);     //生成比较四元式
                addqt("wh",tn,"_","_");//生成while四元式



                startn=now+1;//暂存while{}内程序开始位置
                t="zhaozhiyi";//强制给t赋予某个值，使其不为“{”，防止下面判断while{}内首个token发生错误
                while(true)
                {
                    now++;
                    switch (step[now].substring(1, 2))
                    {
                    case "p":
                        t = p[Integer.parseInt(step[now].substring(3, 4))];
                        break;
                    case "i":
                        break;
                    case "c":
                        break;
                    case "k":
                        break;
                    case "C":
                        break;
                    case "S":
                        break;
                    default:
                        break;
                    }
                    if(t.equals("{"))
                    {
                        braceNum++;
                    }
                    if(t.equals("}"))
                    {
                        braceNum--;
                        if(braceNum==0)
                            break;
                    }
                }
                step_son=Arrays.copyOfRange(step, startn, now);
                qtt=new block().answer(step_son,i,C,S,c,k,p);
                reset_t(qtt,n);
                for(int j=0; j<qtt.size(); j++)
                {
                    qt.add(qtt.get(j));
                }
            }
        case "i":
            break;
        case "c":
            break;
        case "p":
            break;
        case "C":
            break;
        case "S":
            break;
        default:
            break;
        }
        //这一段代码是将你生成的四元式写入文件，给老师检查方便
        String result = "";
        for (int aa = 0; aa < qt.size(); aa++)
        {
            result = result.concat("[");
            for (int aaa = 0; aaa < 4; aaa++)
            {
                result = result.concat(qt.get(aa)[aaa]);
                if (aaa != 3)
                    result = result.concat(",");
            }
            result = result.concat("] ");
        }
        try
        {
            File writename = new File("./z.四元式.txt");
            writename.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            out.write(result);
            out.flush();
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //写入完毕
        printqt();
        return qt;
    }

    static void addqt(String a1,String a2,String a3,String a4)
    {
        String[] in=new String[4];
        in[0]=a1;
        in[1]=a2;
        in[2]=a3;
        in[3]=a4;
        qt.add(in);
    }

    static void printqt()
    {
        for(String[] a:qt)
        {
            for(String aa:a)
            {
                System.out.print(aa);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
    static int reset_t(List<String[]> qtt,int n) //避免临时变量的冲突，在每次往qt加qtt的时候都要重置所有t后面的值
    {
        String[] inqtt;
        int num,max=0;
        for(int j=0; j<qtt.size(); j++)
        {
            inqtt=qtt.get(j);
            for(int jj=1; jj<4; jj++)
            {
                if(inqtt[jj].length()>=2)
                {
                    if(inqtt[jj].substring(0,1).equals("t")&&is_c(inqtt[jj].substring(1)))
                    {
                        num=Double.valueOf(inqtt[jj].substring(1)).intValue();
                        if(max<num)
                            max=num;
                        inqtt[jj]="t".concat(Integer.toString(num+n));
                    }
                }
            }
        }
        return max+n;
    }
    static boolean is_c(String c)
    {
        try
        {
            Double.valueOf(c);
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
    }
    void printLS(List<String[]> r){
		for(String[] a:r){
			for(String aa:a){
				System.out.print(aa);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
}


