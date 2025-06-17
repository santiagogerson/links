<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Login</title>
  <link href="img/6.ico" rel="icon">
  <link href="img/6.ico" rel="apple-touch-icon">
  <!-- Bootstrap 5 CDN -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://unpkg.com/htmx.org@2.0.0" integrity="sha384-wS5l5IKJBvK6sPTKa2WZ1js3d947pvWXbPJ1OmWfEuxLgeHcEbjUUA5i9V5ZkpCw" crossorigin="anonymous"></script>
  <style>
    body {
      background-color: #000;
      color: white; /* Isso deixa os textos legíveis */
    }
  </style>
</head>
<body class="d-flex align-items-center" style="height: 100vh;">

  <div class="container">
    <div class="row justify-content-center">
      <div class="col-md-4">
        <div class="card shadow-lg rounded-4">
          <div class="card-body p-4">
              
            <h3 class="card-title text-center mb-4">Entrar</h3>
            <div id="errorMessage"></div>
            <form hx-post="ControleUsuario?metodo=validar" hx-target="#errorMessage" hx-swap="innerHTML">
              <div class="mb-3">
                <label for="email" class="form-label">Usuário ou E-mail</label>
                <input type="text" class="form-control" id="email" name="txtemail" required>
              </div>
              <div class="mb-3">
                <label for="senha" class="form-label">Senha</label>
                <input type="password" class="form-control" id="senha" name="txtsenha" required>
              </div>
              <div class="d-grid">
                <button type="submit" class="btn btn-primary">Entrar</button>
              </div>
            </form>
            <div class="mt-3 text-center">
              <small><a href="#" class="text-white">Esqueci minha senha</a></small>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- Bootstrap JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
