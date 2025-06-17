    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package dao;

    import bean.Perfil;
    import bean.Url;
    import conexao.FabricaConexao;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.logging.Level;
    import java.util.logging.Logger;

    public class PerfilDao {

        private PreparedStatement pstm;
        private ResultSet rs;
        private Connection con;
        private String sql;

public void salvarOuAtualizarPerfil(Perfil perfil) {
    Connection con = FabricaConexao.conexao();
    try {
        // Verifica se já existe perfil
        PreparedStatement checkPs = con.prepareStatement("SELECT id FROM perfil LIMIT 1");
        ResultSet rsCheck = checkPs.executeQuery();

        int perfilId = 0;

        if (rsCheck.next()) {
            perfilId = rsCheck.getInt("id");

            // Atualiza perfil existente
            PreparedStatement psUpdate = con.prepareStatement("UPDATE perfil SET nome=?, bio=?, fotocaminho=? WHERE id=?");
            psUpdate.setString(1, perfil.getNome());
            psUpdate.setString(2, perfil.getBio());
            psUpdate.setString(3, perfil.getFotocaminho());
            psUpdate.setInt(4, perfilId);
            psUpdate.executeUpdate();

            // Remove as URLs antigas do perfil
            PreparedStatement psDeleteUrls = con.prepareStatement("DELETE FROM url WHERE perfil_id = ?");
            psDeleteUrls.setInt(1, perfilId);
            psDeleteUrls.executeUpdate();

        } else {
            // Inserir novo perfil
            PreparedStatement psInsert = con.prepareStatement("INSERT INTO perfil (nome, bio, fotocaminho) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            psInsert.setString(1, perfil.getNome());
            psInsert.setString(2, perfil.getBio());
            psInsert.setString(3, perfil.getFotocaminho());
            psInsert.executeUpdate();

            ResultSet rs = psInsert.getGeneratedKeys();
            if (rs.next()) {
                perfilId = rs.getInt(1);
            }
        }

        // Inserir URLs
        if (perfil.getUrls() != null && perfilId > 0) {
            for (Url u : perfil.getUrls()) {
                String url = u.getUrl();
if (url == null || url.trim().isEmpty() || url.trim().equalsIgnoreCase("null")) {
    url = "https://karolsalvatore.com.br/links";
}


                PreparedStatement psUrl = con.prepareStatement("INSERT INTO url (perfil_id, url, bannercaminho) VALUES (?, ?, ?)");
                psUrl.setInt(1, perfilId);
                psUrl.setString(2, url);
                psUrl.setString(3, u.getBannercaminho());
                psUrl.executeUpdate();
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(PerfilDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}



       public Perfil getPerfil() {
        Perfil perfil = null;

        try (Connection conn = FabricaConexao.conexao()){

            // 1. Buscar o perfil (assumindo que só existe 1)
            String sqlPerfil = "SELECT * FROM perfil LIMIT 1";
            PreparedStatement psPerfil = conn.prepareStatement(sqlPerfil);
            ResultSet rsPerfil = psPerfil.executeQuery();

            if (rsPerfil.next()) {
                perfil = new Perfil();
                perfil.setId(rsPerfil.getInt("id"));
                perfil.setNome(rsPerfil.getString("nome"));
                perfil.setBio(rsPerfil.getString("bio"));
                perfil.setFotocaminho(rsPerfil.getString("fotocaminho"));

                // 2. Buscar as URLs desse perfil
                String sqlUrls = "SELECT * FROM url WHERE perfil_id = ?";
                PreparedStatement psUrls = conn.prepareStatement(sqlUrls);
                psUrls.setInt(1, perfil.getId());
                ResultSet rsUrls = psUrls.executeQuery();

                List<Url> listaUrls = new ArrayList<>();

                while (rsUrls.next()) {
                    Url url = new Url();
                    url.setId(rsUrls.getInt("id"));
                    url.setUrl(rsUrls.getString("url"));
                    url.setBannercaminho(rsUrls.getString("bannercaminho"));
                    listaUrls.add(url);
                }

                perfil.setUrls(listaUrls);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return perfil;
    }
public boolean excluir(int idUrl) {
    String sql = "DELETE FROM url WHERE id = ?";
    try (Connection conn = FabricaConexao.conexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idUrl);
        int linhasAfetadas = stmt.executeUpdate();
        return linhasAfetadas > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
public String getCaminhoImagemBanner(int id) {
    String sql = "SELECT bannercaminho FROM url WHERE id = ?";
    try (Connection conn = FabricaConexao.conexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getString("bannercaminho");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}



    }
