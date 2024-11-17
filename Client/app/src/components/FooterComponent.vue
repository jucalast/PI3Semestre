<template>
  <footer>
    <div class="footer-container">
      <div class="footer-links">
        <div class="footer-section">
          <h4>Sobre Nós</h4>
          <p><a href="/about" class="footer-link">Nossa História</a></p>
          <p><a href="/faq" class="footer-link">Perguntas Frequentes</a></p>
        </div>
        <div class="footer-section">
          <h4>Contato</h4>
          <p><a href="/contact" class="footer-link">Fale Conosco</a></p>
          <p><a href="/support" class="footer-link">Suporte</a></p>
        </div>
        <div class="footer-section">
          <h4>Redes Sociais</h4>
          <p><a href="https://www.facebook.com" target="_blank" class="footer-link">Facebook</a></p>
          <p><a href="https://www.instagram.com" target="_blank" class="footer-link">Instagram</a></p>
          <p><a href="https://www.twitter.com" target="_blank" class="footer-link">Twitter</a></p>
        </div>
      </div>
      <div class="footer-bottom">
        <p>&copy; 2024 Let's Coffe. Todos os direitos reservados.</p>
      </div>
    </div>
  </footer>
</template>

<script>
  export default {
    name: 'FooterComponent',
  };
</script>

<style scoped>
  footer {
    background-color: #212121;
    color: white;
    text-align: center;
    padding: 20px;
    width: 100vw;
  }

  .footer-container {
    max-width: 1200px;
    margin: 0 auto;
  }

  .footer-links {
    display: flex;
    justify-content: space-around;
    flex-wrap: wrap;
    border-bottom: 1px solid #333;
    padding-bottom: 20px;
  }

  .footer-section {
    flex: 1;
    min-width: 150px;
    margin: 10px;
  }

  .footer-section h4 {
    margin-bottom: 10px;
    color: #ffa500;
  }

  .footer-link {
    color: white;
    text-decoration: none;
  }

  .footer-link:hover {
    text-decoration: underline;
  }

  .footer-bottom {
    margin-top: 20px;
  }

  footer p {
    margin: 0;
    background: transparent;
  }
</style>
