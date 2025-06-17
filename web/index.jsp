<%@page import="java.util.List"%>
<%@page import="bean.Url"%>
<%@page import="bean.Perfil"%>
<%@page import="dao.PerfilDao"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Karol Salvatore - Links e Redes Sociais</title>
        <link href="img/6.ico" rel="icon">
        <link href="img/6.ico" rel="apple-touch-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Great+Vibes&display=swap" rel="stylesheet">

        <style>
            body {
                background-color: #000;
                color: #fff;
                font-family: Arial, sans-serif;
            }

            .hero {
                padding: 60px 0;
                text-align: center;
            }

            .hero h1 {
                font-size: 2.5rem;
                font-weight: bold;
            }

            .hero p {
                font-size: 1.2rem;
                color: #ccc;
            }

            .link-card {
                background-color: #111;
                border: 1px solid #222;
                border-radius: 10px;
                padding: 20px;
                margin-bottom: 20px;
                transition: transform 0.3s, box-shadow 0.3s;
            }

            .link-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 8px 16px rgba(255, 255, 255, 0.1);
            }

            .link-card h5 {
                color: #fff;
                font-size: 1.25rem;
                margin-bottom: 10px;
            }

            .link-card p {
                color: #aaa;
                font-size: 1rem;
            }

            .btn-primary {
                background-color: #ff4081;
                border: none;
            }

            .btn-primary:hover {
                background-color: #e73370;
            }

            .banner-wrapper {
                max-width: 900px;
                margin: 15px auto;
                padding: 0 10px;
            }

            .banner-img {
                width: 100%;
                height: auto;
                border-radius: 12px;
                object-fit: cover;
                transition: transform 0.3s ease;
            }

            .banner-wrapper:hover .banner-img {
                transform: scale(1.03);
            }
            .preview-img {
                width: 100px;
                height: 100px;
                border-radius: 50%;
                object-fit: cover;
                margin-top: 10px;
            }

        </style>
    </head>
    <body>
        <!-- Botão de Login no canto superior direito -->
        <div class="position-absolute top-0 end-0 p-3">
            <a href="editar_links.jsp" class="text-white text-decoration-none" title="Login">
                <i class="bi bi-person-circle fs-4"></i>
            </a>
        </div>

        <%
            PerfilDao perfilDao = new PerfilDao();
            Perfil perfil = perfilDao.getPerfil();
            List<Url> listaUrls = perfil != null ? perfil.getUrls() : null;
        %>

        <!-- Hero Section -->
        <section class="hero">
            <div class="container text-center">
                <h1>
                    <img class="preview-img" src="<%= perfil.getFotocaminho()%>" alt="perfil" />
                </h1>
                <h4><%= perfil.getNome()%></h4>
                <div style="white-space: pre-line; margin: 0 auto; max-width: 400px;">
                    <%= perfil.getBio()%>
                </div>
            </div>
        </section>

        <!-- Banners Section -->
        <section class="banners-section py-4">
            <div class="container-fluid px-2">
                <% if (listaUrls != null && !listaUrls.isEmpty()) { %>
                <% for (Url url : listaUrls) {%>
                <div class="banner-wrapper">
                    <a href="<%= (url.getUrl() == null || url.getUrl().isEmpty()) ? "https://karolsalvatore.com.br/links" : url.getUrl()%>" target="_blank">
                        <img src="<%= url.getBannercaminho()%>" alt="Banner" class="banner-img">
                    </a>
                </div>
                <% } %>
                <% } else { %>
                <p class="text-muted text-center">Nenhum link encontrado.</p>
                <% }%>
            </div>
        </section>

        <!-- Rodapé -->
        <footer class="bg-dark text-center text-white py-3">
            <div class="container">
                <a href="https://karolsalvatore.com.br" target="_blank" class="d-block mb-2">
                    <img src="img/7.png" alt="Banner Rodapé" class="img-fluid mx-auto" style="max-height: 80px;">
                </a>
                <p class="mb-0">© 2025 Karol Salvatore. Todos os direitos reservados.</p>
            </div>
        </footer>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
