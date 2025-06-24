import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

/**
 * Created by s13006 on 14/11/25.
 */
public class Delete1 {
    private String _user = "s13006";
    private String _pass = "password";
    private String _host = "172.16.40.4";
    private String _sid = "db11";

    public static void main(String[] args){

        Delete1 sample = new Delete1();
        try {
            sample.select();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void select() throws Exception{
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@" + _host + ":1521:" + _sid, _user, _pass);

            st = conn.createStatement();

            PreparedStatement pre = conn.prepareStatement("select e.empno, e.ename, e.job, m.ename, d.dname, d.loc, e.sal from EMPLOYEES E " +
                    "left join EMPLOYEES M on (e.mgr = m.empno)" +
                    "left join DEPARTMENTS D on (e.deptno = d.deptno)" +
                    "order by e.empno");
            rs = pre.executeQuery();

            while (rs.next()){
                String empno = rs.getString(1);
                String ename1 = rs.getString(2);
                String job = rs.getString(3);
                String ename2 = rs.getString(4);
                String dname = rs.getString(5);
                String loc = rs.getString(6);
                String sal = rs.getString(7);

                System.out.println(empno + "   " + ename1 + "  " + job + "  " + ename2 + "  " + dname + "  " + loc + "  " + sal);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("empno を入力してください。");
            String str = br.readLine();
            int prefCd = Integer.parseInt(str);

            pre = conn.prepareStatement("select empno, ename from employees where empno = ? ");
            pre.setInt(1,prefCd);
            rs = pre.executeQuery();

            rs.next();
            String empno = rs.getString(1);
            String ename = rs.getString(2);
            System.out.printf("社員番号：" + empno + "\n" + "社員名　：" + ename + "\n" + "部下　　：");

            pre = conn.prepareStatement("SELECT ename FROM employees where mgr = ?");
            pre.setInt(1,prefCd);
            rs = pre.executeQuery();
            boolean flag = true;

            while(rs.next()) {
                flag = false;
                String sub = rs.getString(1);
                System.out.print(sub + ",");
            }

            if (flag) {
                System.out.println("部下はいません");
            } else {
                System.out.println();
            }

            System.out.print("この社員の情報を削除しますか？(yes or no)");
            String bool = br.readLine();

            if (bool.equals("yes")) {
                if (flag) {
                    pre = conn.prepareStatement("DELETE FROM employees WHERE empno = ?");
                    pre.setInt(1,prefCd);
                    pre.executeUpdate();
                    System.out.println("削除しました。");
                } else {
                    System.out.println("部下がいる場合は削除できません。");
                }
            }


        }catch(ClassNotFoundException e){
            throw e;
        }catch(SQLException e){
            throw e;
        }catch(Exception e){
            throw e;
        }finally{
            if(conn != null){
                conn.close();
            }
            if(st != null){
                st.close();
                st = null;
            }
            if(rs != null){
                rs.close();
                rs = null;
            }
        }
    }
}
