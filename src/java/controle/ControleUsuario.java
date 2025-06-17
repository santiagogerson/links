/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controle;

import bean.Usuario;
import conexao.Metodos;
import dao.UsuarioDao;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author 01494237210
 */
@WebServlet(name = "ControleUsuario", urlPatterns = {"/ControleUsuario"})
@MultipartConfig
public class ControleUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Metodos acao = Metodos.valueOf(request.getParameter("metodo").toUpperCase());
        Usuario usu;

        switch (acao) {

            case VALIDAR -> {
                UsuarioDao usuarioDao = new UsuarioDao();
                usu = new Usuario();
                usu.setUsuemail(request.getParameter("txtemail"));
                usu.setUsusenha(request.getParameter("txtsenha"));

                try {
                    Map<String, Object> authResult = usuarioDao.authenticate2(usu.getUsuemail(), usu.getUsusenha());

                    if (authResult != null) {
                        int idUsuario = (int) authResult.get("idUsuario");
                        String nomeUsuario = (String) authResult.get("nomeUsuario");
                        String funcao = (String) authResult.get("funcao");
                        String telefone = (String) authResult.get("telefone");
                        String email = (String) authResult.get("email");

                        usu.setUsucod(idUsuario);
                        usu.setUsunome(nomeUsuario);
                        usu.setUsuemail(email);

                        HttpSession session = request.getSession();
                        session.setMaxInactiveInterval(60 * 60); // Tempo em segundos (neste exemplo, 1 hora)
                        session.setAttribute("UsuarioLogado", usu);
                        session.setAttribute("senha", usu.getUsusenha());

                        response.setHeader("HX-Redirect", "editar_links.jsp");
                        response.setStatus(HttpServletResponse.SC_OK);
                    } else {
                        response.getWriter().write("<div class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\">\n"
                                + "Dados incorretos, tente novamente!\n"
                                + "<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\n"
                                + "</div>");
                    }
                } catch (Exception e) {
                    response.getWriter().write("<div class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\">\n"
                            + "Ocorreu um erro durante a autenticação. Tente novamente mais tarde.\n"
                            + "<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\n"
                            + "</div>");
                }
            }
            
 
            

        }
    }
        private void Irpara(HttpServletRequest request, HttpServletResponse response, String pagina) throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/" + pagina);
        dispatcher.forward(request, response);

    }

    private String getFileExtension(Part part) {
        if (part != null) {
            String fileName = getSubmittedFileName(part);
            if (fileName != null && fileName.contains(".")) {
                return fileName.substring(fileName.lastIndexOf(".") + 1);
            }
        }
        return "";
    }

    private String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1)
                        .substring(fileName.lastIndexOf('\\') + 1);
            }
        }
        return null;
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
