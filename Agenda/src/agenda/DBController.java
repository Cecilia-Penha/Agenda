package agenda;

import java.io.File;
import java.sql.*;
public class DBController {
    private String dbName;
    Connection dbConn;
    
    public DBController(String dbName){
        this.dbName = dbName;
    }
    public void conectar()throws Exception{
        File f = new File(this.dbName);
        if(!f.exists()){
            throw new Exception("Arquivo inexistente");
        }
        Class.forName("org.sqlite.JDBC");
        this.dbConn = DriverManager.getConnection("jdbc:sqlite:"+this.dbName);
    }
    public void desconectar()throws SQLException{
        this.dbConn.close();
    }
    public void salvarContato(String nome,String tel,String end)throws Exception{
        String ins = "INSERT INTO agenda (nome,tel,end) VALUES(?,?,?)";
        PreparedStatement stmt;
        
        try{
            stmt = this.dbConn.prepareStatement(ins);
            stmt.setString(1,nome);
            stmt.setString(2,tel);
            stmt.setString(3,end);
            stmt.executeUpdate();
        }catch(SQLException e){
            throw new Exception("Erro ao inserir dados "+e.getMessage());
        }
    }
    public ResultSet carregarContatos()throws Exception{
        String sel = "SELECT nome,tel,end FROM agenda";
        ResultSet rset = null;
        try{
            Statement stmt = this.dbConn.createStatement();
            rset = stmt.executeQuery(sel);
        }catch(SQLException e){
            throw new Exception("Erro ao buscar dados: "+e.getMessage());
        }
        return rset;
    }
}