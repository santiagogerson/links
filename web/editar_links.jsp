<%@page import="java.util.List"%>
<%@page import="bean.Url"%>
<%@page import="bean.Perfil"%>
<%@page import="dao.PerfilDao"%>
<%@page import="bean.Usuario"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <title>Edição de Perfil</title>
        <link href="img/6.ico" rel="icon">
        <link href="img/6.ico" rel="apple-touch-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
        <script src="https://unpkg.com/htmx.org@1.9.2"></script>
        <style>
            .preview-img {
                width: 100px;
                height: 100px;
                border-radius: 50%;
                object-fit: cover;
                margin-top: 10px;
            }

            .perfil-wrapper {
                display: flex;
                flex-direction: column;
                align-items: center;
            }

            .botao-camera {
                margin-top: 10px;
                border: none;
                background: transparent;
                cursor: pointer;
                font-size: 1.5rem;
                color: #0d6efd;
            }

            .botao-camera:hover {
                color: #0a58ca;
            }

            .preview-img-banner {
                width: 100%;
                max-width: 700px;
                height: auto;
                border-radius: 8px;
                object-fit: cover;
                margin-top: 5px;
            }
        </style>
    </head>
    <body class="bg-light py-4">

        <%
            Usuario usu = (Usuario) session.getAttribute("UsuarioLogado");
            if (usu == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            PerfilDao perfilDao = new PerfilDao();
            Perfil perfil = perfilDao.getPerfil();
            List<Url> listaUrls = (perfil != null && perfil.getUrls() != null) ? perfil.getUrls() : new java.util.ArrayList<>();
        %>

        <div class="container">
            <h2 class="mb-4 text-center">Editar Perfil</h2>
            <form action="ControlePerfil?metodo=alterar" method="post" enctype="multipart/form-data">

                <!-- Foto de perfil -->
                <div class="mb-4 perfil-wrapper">
                    <%
                        String fotoPerfilSrc = (perfil != null && perfil.getFotocaminho() != null && !perfil.getFotocaminho().isEmpty())
                                ? perfil.getFotocaminho()
                                : "img/perfil-padrao.png";
                    %>
                    <input type="hidden" name="fotoPerfilAtual" value="<%= fotoPerfilSrc%>">
                    <img id="fotoPerfilPreview" src="<%= fotoPerfilSrc%>" class="preview-img" alt="Prévia da Foto de Perfil" />

                    <input type="file" id="inputFotoPerfil" name="fotoPerfil" accept="image/*" style="display: none;"
                           onchange="previewImagem(this, '#fotoPerfilPreview')">

                    <button type="button" class="botao-camera" onclick="document.getElementById('inputFotoPerfil').click()" title="Alterar foto">
                        <i class="bi bi-camera-fill"></i>
                    </button>
                </div>

                <!-- Nome -->
                <div class="mb-3">
                    <label class="form-label">Nome</label>
                    <input type="text" class="form-control" name="nome" value="<%= (perfil != null) ? perfil.getNome() : ""%>" required>
                </div>

                <!-- Bio -->
                <div class="mb-3">
                    <label class="form-label">Bio</label>
                    <textarea class="form-control" name="bio" rows="3" maxlength="300"><%= (perfil != null) ? perfil.getBio() : ""%></textarea>
                </div>

                <!-- Banners -->
                <div class="mb-3">
                    <label class="form-label">Banners com URLs</label>
                    <div id="banners-container">
                        <%
                            int count = 0;
                            for (Url url : listaUrls) {
                                String imgId = "previewBanner" + count;
                        %>
                        <div class="banner-group border rounded p-3 mb-3">
                            <div class="d-flex justify-content-between align-items-center">
                                <label class="form-label mb-0">Imagem do Banner</label>
                                <button type="button" class="btn btn-outline-danger btn-sm"
                                        hx-post="ControlePerfil?metodo=excluir&idBanner=<%= url.getId()%>" 
                                        hx-target="closest .banner-group" 
                                        hx-swap="outerHTML"
                                        title="Excluir este banner">
                                    <i class="bi bi-trash"></i> Excluir
                                </button>
                            </div>
                            <input type="file" class="form-control mt-2" name="bannerImagem" accept="image/*" onchange="previewImagem(this, '#<%= imgId%>')">
                            <input type="hidden" name="bannerImagemAtual" value="<%= url.getBannercaminho()%>">
                            <img id="<%= imgId%>" src="<%= url.getBannercaminho()%>" class="preview-img-banner mt-2" alt="Prévia do Banner">

                            <div class="mt-2">
                                <label class="form-label">URL do Banner</label>
                                <input type="url" class="form-control" name="bannerUrl" value="<%= url.getUrl()%>" placeholder="https://exemplo.com">
                            </div>
                        </div>
                        <%
                                count++;
                            }
                        %>
                    </div>
                    <button type="button" class="btn btn-outline-primary btn-sm" onclick="adicionarBanner()">+ Adicionar outro banner</button>
                </div>

                <!-- Botão -->
                <div class="d-grid">
                    <button type="submit" class="btn btn-success">Salvar Alterações</button>
                </div>
            </form>
        </div>

        <script>
            let bannerCount = <%= listaUrls.size()%>;

            function previewImagem(input, previewSelector) {
                const preview = document.querySelector(previewSelector);
                const file = input.files[0];
                if (file && preview) {
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        preview.src = e.target.result;
                        preview.style.display = 'block';
                    };
                    reader.readAsDataURL(file);
                }
            }

            function adicionarBanner() {
                const container = document.getElementById('banners-container');
                const idUnico = 'previewBannerNovo' + bannerCount;

                const novoBanner = document.createElement('div');
                novoBanner.classList.add('banner-group', 'border', 'rounded', 'p-3', 'mb-3');
                novoBanner.innerHTML = `
        <div class="d-flex justify-content-between align-items-center">
            <label class="form-label mb-0">Imagem do Banner</label>
            <button type="button" class="btn btn-outline-danger btn-sm" onclick="this.closest('.banner-group').remove()" title="Excluir este banner">
                <i class="bi bi-x-circle"></i> Remover
            </button>
        </div>
        <input type="file" class="form-control mt-2" name="bannerImagem" accept="image/*"
               onchange="previewImagem(this, '#${idUnico}')">
        <img id="${idUnico}" class="preview-img-banner mt-2" style="display:none;" alt="Prévia do Banner">
        <div class="mt-2">
            <label class="form-label">URL do Banner</label>
            <input type="url" class="form-control" name="bannerUrl" placeholder="https://exemplo.com">
            <input type="hidden" name="bannerImagemAtual" value="">
        </div>
    `;
                container.appendChild(novoBanner);
                bannerCount++;
            }

        </script>
    </body>
</html>
