/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controle;

import bean.Perfil;
import bean.Url;
import bean.Usuario;
import conexao.Metodos;
import dao.PerfilDao;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author 01494237210
 */
@WebServlet(name = "ControlePerfil", urlPatterns = {"/ControlePerfil"})
@MultipartConfig
public class ControlePerfil extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        Metodos acao = Metodos.valueOf(request.getParameter("metodo").toUpperCase());
        Usuario usu;

        switch (acao) {

            case ALTERAR -> {
    String nome = request.getParameter("nome");
    String bio = request.getParameter("bio");

    Part fotoPerfilPart = request.getPart("fotoPerfil");
    String fotoAtual = request.getParameter("fotoPerfilAtual");
    
String caminhoFoto = (fotoPerfilPart != null && fotoPerfilPart.getSize() > 0)
        ? salvarImagem(fotoPerfilPart, "fotoPerfil")
        : fotoAtual;

if (fotoPerfilPart != null && fotoPerfilPart.getSize() > 0) {
    apagarImagemAntiga(fotoAtual);  // <-- Apaga a imagem anterior do perfil
}

    Perfil perfil = new Perfil();
    perfil.setNome(nome);
    perfil.setBio(bio);
    perfil.setFotocaminho(caminhoFoto);

    List<Url> listaUrls = new ArrayList<>();
    Collection<Part> partes = request.getParts();

    List<Part> imagens = new ArrayList<>();
    for (Part p : partes) {
        if ("bannerImagem".equals(p.getName())) {
            imagens.add(p);
        }
    }

    String[] urls = request.getParameterValues("bannerUrl");
    String[] caminhosAtuais = request.getParameterValues("bannerImagemAtual");

    if (urls != null) {
        for (int i = 0; i < urls.length; i++) {
            String link = urls[i];
            if (link == null || link.trim().isEmpty()) {
                // Substitui URL vazia pelo padrão
                link = "https://karolsalvatore.com.br/links";
            }

            Url url = new Url();
            url.setUrl(link);

            String caminhoBanner;
            
            if (i < imagens.size() && imagens.get(i) != null && imagens.get(i).getSize() > 0) {
                 apagarImagemAntiga(caminhosAtuais[i]); // <-- Apaga a anterior
                caminhoBanner = salvarImagem(imagens.get(i), "banners");
            } else {
                caminhoBanner = (caminhosAtuais != null && i < caminhosAtuais.length) ? caminhosAtuais[i] : null;
            }

            url.setBannercaminho(caminhoBanner);
            listaUrls.add(url);
        }
    }

    perfil.setUrls(listaUrls);

    PerfilDao dao = new PerfilDao();
    dao.salvarOuAtualizarPerfil(perfil);

    response.sendRedirect("index.jsp");
}

    
case EXCLUIR -> {
    int idBanner = Integer.parseInt(request.getParameter("idBanner"));

    PerfilDao dao = new PerfilDao();

    // Buscar caminho da imagem antes de excluir
    String caminhoImagem = dao.getCaminhoImagemBanner(idBanner);
    boolean excluido = dao.excluir(idBanner);

    if (excluido) {
        apagarImagemAntiga(caminhoImagem);  // <-- Apaga do disco
        response.getWriter().write("");
    } else {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write("Erro ao excluir o banner.");
    }
}

        }
    }

    private String salvarImagem(Part imagem, String pasta) throws IOException {
        if (imagem != null && imagem.getSize() > 0) {
            String nomeArquivo = Paths.get(imagem.getSubmittedFileName()).getFileName().toString();
            String caminhoUpload = getServletContext().getRealPath("/") + "img/" + pasta;
            Files.createDirectories(Paths.get(caminhoUpload));
            String caminhoCompleto = caminhoUpload + File.separator + nomeArquivo;

            imagem.write(caminhoCompleto);
            return "img/" + pasta + "/" + nomeArquivo;
        }
        return null;
    }
    private void apagarImagemAntiga(String caminhoRelativo) {
    if (caminhoRelativo != null && !caminhoRelativo.contains("perfil-padrao.png")) {
        String caminhoAbsoluto = getServletContext().getRealPath("/") + caminhoRelativo;
        File arquivo = new File(caminhoAbsoluto);
        if (arquivo.exists()) {
            arquivo.delete();
        }
    }
}


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
