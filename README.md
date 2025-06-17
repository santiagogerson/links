# 🔗 Projeto de Links Pessoais

Este projeto é uma aplicação web desenvolvida em **Java Web (JSP + Servlet + DAO)** para criar uma **landing page personalizada de links**, semelhante ao Linktree. Foi pensado para profissionais que desejam centralizar seus links em uma única página com visual moderno e responsivo.

## 🚀 Tecnologias Utilizadas

- Java EE (JSP + Servlet)
- HTML, CSS e Bootstrap
- Banco de dados MySQL
- HTMX (para interações dinâmicas sem recarregar a página)
- NetBeans IDE
- Git + GitHub

## 📸 Funcionalidades

- 📌 Cadastro e edição de perfil
- 🖼️ Upload de imagem de perfil
- 🔗 Adição de múltiplos links com banners/imagens
- 🗑️ Exclusão individual de links
- 📱 Página final responsiva para dispositivos móveis

## 🛠️ Como rodar o projeto

1. Clone o repositório:

```bash
git clone https://github.com/santiagogerson/projeto-links.git


Importe no NetBeans:

File > Open Project

Selecione a pasta projeto-links

Configure seu banco de dados MySQL:

Crie o banco com as tabelas necessárias

Adicione um arquivo config.properties com os dados de conexão:

properties
Copiar
Editar
db.url=jdbc:mysql://seu-host:3306/nome-do-banco
db.user=seuUsuario
db.password=suaSenha
Execute o projeto no NetBeans com um servidor compatível (como Tomcat ou Payara).

🧠 Organização do Projeto
src/bean: classes Java Bean (Perfil, Url, etc.)

src/dao: acesso ao banco de dados

src/servlet: controladores

web: arquivos JSP e estáticos

👨‍💻 Autor
Gerson Santiago
Desenvolvedor Java Web com experiência em soluções empresariais e sistemas personalizados.
🔗 Meu LinkedIn
🌐 Meu Portfólio (ou outro link)

📄 Licença
Este projeto está sob a licença MIT. Sinta-se à vontade para usar, melhorar e compartilhar!
