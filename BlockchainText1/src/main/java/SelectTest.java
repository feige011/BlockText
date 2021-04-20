import com.mysql.jdbc.TimeUtil;
import org.apache.commons.codec.binary.Hex;
import javax.xml.crypto.Data;
import java.security.MessageDigest;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class SelectTest {
    static String[] say={"feige012 is back","print(\"feige011 is start\")\n" +
            "i=0\n" +
            "while(i<=3):\n" +
            "    i=i+1\n" +
            "    print(\"feige011 is ready\")\n" +
            "print(\"feige011 is end\")","feige011 is good","feige011 is ready","feige011 is fly","feige011 very good"};
    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = DruidFactory2.getConnection();
            //构建数据库执行者
            Statement stmt = conn.createStatement();
            //执行SQL语句并返回结果到ResultSet
            ResultSet rs = stmt.executeQuery("select * from Block1");
            rs.next();
            new Thread(new Druid(rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(6),stmt)).start();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String jdkMD5(String src) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md.digest(src.getBytes());
            System.out.println("JDK MD5: " + Hex.encodeHexString(md5Bytes));
            return Hex.encodeHexString(md5Bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

class Druid implements Runnable {

    String head = "";
    String body = "";
    String hash = "";
    int other;
    Statement stmt;


    public Druid(String head, String body, String hash,int other,Statement stmt) {
        this.head = head;
        this.body = body;
        this.hash = hash;
        this.other=other;
        this.stmt=stmt;
    }

    @Override
    public void run() {
        try {
            String myHashSrc=head+body;
            String myHash=SelectTest.jdkMD5(myHashSrc);
            if(myHash.equals(hash)){
                String newBody=SelectTest.say[other%SelectTest.say.length];
                String newHead=myHash;
                String newHash=newHead+newBody;
                newHash=SelectTest.jdkMD5(newHash);
                Date date = new Date(System.currentTimeMillis());
                other++;
                String sql="insert into Block1(head,body,hash,Data ,other) values('"+newHead+"','"+newBody+"','"+newHash+"','"+date+"',"+other+")";
                System.out.println("添加: "+sql); 
                stmt.executeUpdate(sql);
                System.out.println("添加成功: "+sql);
                TimeUnit.SECONDS.sleep(3);//新的睡眠写法,睡眠3s
                new Thread(new Druid(newHead,newBody,newHash,other,stmt)).start();

            }else{
                System.out.println("body="+body+" head="+head);
                System.out.println("myHash="+myHash+"  hash="+hash);
                System.out.println("账本问题");
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }
}
