/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexao.FabricaConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author 01494237210
 */
public class UsuarioDao {
    
    private PreparedStatement pstm;
    private ResultSet rs;
    private Connection con;
    private String sql;
    
       public Map<String, Object> authenticate2(String login, String senha) {
    try {
        con = FabricaConexao.conexao();
        sql = "SELECT usucod, usunome,usuemail FROM `usuario` WHERE usuemail = ? AND ususenha = MD5(?)";

        pstm = con.prepareStatement(sql);
        pstm.setString(1, login);
        pstm.setString(2, senha);

        rs = pstm.executeQuery();

        if (rs.next()) {
            Map<String, Object> result = new HashMap<>();
            result.put("idUsuario", rs.getInt("usucod"));
            result.put("nomeUsuario", rs.getString("usunome"));
            result.put("email", rs.getString("usuemail"));
            return result;
        } else {
            return null;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    } finally {
        try {
            if (rs != null) rs.close();
            if (pstm != null) pstm.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("Erro ao fechar recursos: " + e.getMessage());
        }
    }
}
       
       

    
}
