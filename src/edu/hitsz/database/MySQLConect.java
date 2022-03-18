package edu.hitsz.database;
import com.mysql.cj.protocol.Resultset;
import java.sql.*;

public class MySQLConect implements TagDao{
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name?useSSL=false&serverTimezone=UTC";
    static final String USER="your name";
    static final String PASS="your password";
    private static Connection conn=null;
    private static MySQLConect mySQLConect=new MySQLConect();
    private static int whichTable;

    private MySQLConect(){
        try {
            //注册JDBC驱动
            Class.forName(JDBC_DRIVER);
            //打开链接
            conn= DriverManager.getConnection(DB_URL,USER,PASS);

            initTable();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initTable(){
        initTableTool("easy");
        initTableTool("common");
        initTableTool("difficult");
        addData("源哥我的超人","10000","100d",1);
    }

    private void initTableTool(String tableName){
        try {
            Statement stmt=conn.createStatement();
            String sql="CREATE TABLE IF NOT EXISTS "+tableName+
                    "("+
                    "name VARCHAR(30),"+
                    "score VARCHAR(30),"+
                    "time VARCHAR(30)" +
                    ")"+
                    "ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static MySQLConect getInstance(){
        return mySQLConect;
    }

    private String getTable(int whichTable){
        String tableName;
        switch (whichTable){
            case 1:tableName="easy";break;
            case 2:tableName="common";break;
            case 3:tableName="difficult";break;
            default:tableName="easy";break;
        }
        return tableName;
    }

    public void addData(String name,String score,String time,int whichTable){
        String tableName=getTable(whichTable);
        String sql="insert into "+tableName+" (name,score,time) values(?,?,?)";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,name);
            ps.setString(2,score);
            ps.setString(3,time);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteData(String name,String score,String time,int whichTable){
        String sql="delete from "+getTable(whichTable)+" where name=\""+name+"\" and "+"score=\""+score+"\" and "+"time=\""+time+"\"";
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement(sql);
            int i=ps.executeUpdate();
            if(i>0){
            }else{
                System.out.println("error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
//        MySQLConect.getInstance().addData("gua","www.wa.aa");
    }

    @Override
    public void addTag(Tag tag) {
        addData(tag.getName(),tag.getScore()+"",tag.getTime(),tag.getDifficult());
    }

    @Override
    public void deleteTag(Tag tag) {

    }

    @Override
    public String[][] getAllTags(int whichTable){
        String[][] ans = null;
        //执行查询
        Statement stmt=null;
        Statement stmt2=null;
        try {
            stmt = conn.createStatement();
            String sql;
            String tableName;
            sql = "SELECT name,score,time FROM "+getTable(whichTable);
            ResultSet rs = stmt.executeQuery(sql);

            int i=0;

            while(rs.next()){
                i++;
            }
            rs=stmt.executeQuery(sql);
            ans=new String[i][];
            i=0;
            //展开结果集
            while (rs.next()) {
                String name= rs.getString("name");
                String score= rs.getString("score");
                String time= rs.getString("time");

                String[] a=new String[]{name,score,time};
                ans[i]=a;
                i++;
            }

            //完成后关闭
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            try{
                if(stmt!=null){
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ans;
    }
}
