/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class FabricaConexao {

    public static Connection conexao() {
        Connection con = null;
        String usuario = "gerso5537_links";
            String senha = "S@mel123456";
        String url = "jdbc:mysql://localhost:3306/gerso5537_links?useUnicode=true&characterEncoding=UTF-8";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,usuario,senha);
            System.out.println("Conectado com sucesso");
        } catch (ClassNotFoundException ex) {
            System.out.println("Arquivo não encontrado "+ex.getMessage());
        } catch (SQLException ex){
            System.out.println("Usuario ou senha invalida "+ex.getMessage());
        }
        
        return con;
    
    }
     
    public static void main(String[] args) {
        System.out.println("Iniciando aplicação.");

        Connection conexao = conexao();

        if (conexao != null) {
            System.out.println("Conexão bem-sucedida!");

            // Restante do código...

            // Não se esqueça de fechar a conexão quando terminar de usá-la.
            try {
                conexao.close();
                System.out.println("Conexão fechada.");
            } catch (SQLException ex) {
                System.out.println("Erro ao fechar a conexão: " + ex.getMessage());
            }
        } else {
            System.out.println("Falha na conexão. Verifique o console para mensagens de erro.");
        }

        System.out.println("Encerrando aplicação.");
    }
    
}

